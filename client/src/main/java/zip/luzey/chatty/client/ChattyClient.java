package zip.luzey.chatty.client;

import net.miginfocom.swing.MigLayout;
import zip.luzey.chatty.client.swing.FrameInstance;
import zip.luzey.chatty.client.swing.PanelInstance;
import zip.luzey.chatty.client.swing.SwingBuilder;
import zip.luzey.chatty.client.swing.Themes;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class ChattyClient {
	public static void main(final String... args) {
		final SwingBuilder builder = new SwingBuilder()
			 .name("Chatty")
			 .theme(Themes.ARC_DARK)
			 .frame(new FrameInstance.Builder()
				         .defaultSize()
				         .locationDefault()
				         .locationByPlatform()
				         .resizable(true)
				         .layout(new MigLayout())
				         .panel(new PanelInstance.Builder()
						              .add(new JButton("Test"))
						              .build())
				         .build());
		SwingUtilities.invokeLater(builder::build);
		CompletableFuture.runAsync(() -> {
			try (Socket socket = new Socket()) {
				socket.setTcpNoDelay(true);
				socket.connect(new InetSocketAddress("127.0.0.1", 1337), 3000);

				final DataInputStream input = new DataInputStream(socket.getInputStream());
				final DataOutputStream output = new DataOutputStream(socket.getOutputStream());

				output.writeUTF("User1");
				UUID uuid;
				output.writeUTF(String.valueOf(uuid = UUID.fromString(input.readUTF())));
				for ( ; ; )
					output.writeByte(0);
			} catch (IOException exception) {
				final ByteArrayOutputStream output = new ByteArrayOutputStream();
				exception.printStackTrace(new PrintStream(output, true));
				final String error = output.toString(StandardCharsets.UTF_8);
				System.err.println(error);

				SwingUtilities.invokeLater(() -> {
					if (JOptionPane.showConfirmDialog(
						 builder.getInstances()[0].getBuilder().getJFrame(),
						 "An exception occurred.\nDo you want to see the error?",
						 "Connection lost",
						 JOptionPane.OK_CANCEL_OPTION
					) != JOptionPane.YES_OPTION) return;

					final JTextArea textArea = new JTextArea(error.substring(0, error.length() - 1));
					textArea.setEditable(false);
					JOptionPane.showMessageDialog(
						 builder.getInstances()[0].getBuilder().getJFrame(),
						 new JScrollPane(textArea),
						 exception.getMessage(),
						 JOptionPane.ERROR_MESSAGE
					);
				});
			}
		});
	}
}
