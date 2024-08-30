package zip.luzey.chatty.client;

import de.schipplock.gui.swing.svgicon.SvgIconManager;
import de.schipplock.gui.swing.svgicon.SvgIcons;
import net.miginfocom.swing.MigLayout;
import zip.luzey.chatty.client.swing.FrameInstance;
import zip.luzey.chatty.client.swing.PanelInstance;
import zip.luzey.chatty.client.swing.SwingBuilder;
import zip.luzey.chatty.client.swing.Themes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/*

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
 */
public final class ChattyClient {
	public static void main(final String... args) {
		SwingBuilder.init();

		PanelInstance mainPanel = new PanelInstance.Builder()
			 .build();

		FrameInstance mainFrame = new FrameInstance.Builder()
			 .defaultSize()
			 .locationDefault()
			 .locationByPlatform()
			 .resizable(true)
			 .layout(new MigLayout())
			 .panel(mainPanel)
			 .build();

		JMenuBar menuBar = new JMenuBar();
		MouseAdapter backgroundListener = new MouseAdapter() {
			private static final Color
				 HOVERED = new Color(255, 255, 255, 120),
				 EXITED = new Color(0, 0, 0, 0);

			@Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().setBackground(HOVERED);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.getComponent().setBackground(EXITED);
				super.mouseExited(e);
			}
		};

		JButton settings = new JButton(SvgIconManager.getBuiltinIcon(
			 SvgIcons.SVGICON_GEAR,
			 new Dimension(16, 16),
			 "#FFFFFF"
		));
		settings.setBackground(new Color(0, 0, 0, 0));
		settings.setBorderPainted(false);
		settings.addMouseListener(backgroundListener);

		JButton themes = new JButton(SvgIconManager.getBuiltinIcon(
			 SvgIcons.SVGICON_BRUSH,
			 new Dimension(16, 16),
			 "#FFFFFF"
		));
		themes.setBackground(new Color(0, 0, 0, 0));
		themes.setBorderPainted(false);
		JPopupMenu menu = new JPopupMenu();
		menu.setBorderPainted(false);
		List<JMenuItem> items = List.of(
			 Arrays.stream(Themes.values())
			       .map(theme -> {
				       JMenuItem item = new JMenuItem(theme.getTheme().getName());
				       item.addMouseListener(new MouseAdapter() {
					       public @Override void mouseReleased(MouseEvent e) {
						       super.mouseReleased(e);
						       Themes.select(Objects.requireNonNull(
							        Arrays.stream(Themes.values())
							              .filter(theme -> theme.getTheme().getName().equalsIgnoreCase(item.getText()))
							              .findFirst()
							              .orElse(null)
						       ));
					       }
				       });
				       return item;
			       })
			       .toArray(JMenuItem[]::new)
		);
		items.forEach(menu::add);
		menu.setSelected(
			 items.stream()
			      .filter(item -> item.getText().equalsIgnoreCase(Themes.getGlobalTheme().getTheme().getName()))
			      .findFirst().orElse(null)
		);
		menu.addMouseListener(new MouseAdapter() {
			public @Override void mouseReleased(MouseEvent e) {
				System.out.println(e.paramString());
				super.mouseReleased(e);
			}
		});
		themes.setComponentPopupMenu(menu);
		themes.addMouseListener(new MouseAdapter() {
			private boolean hovering = false;

			public @Override void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if (!this.hovering) return;
				menu.setPopupSize(new Dimension(200, mainFrame.getBuilder().getJFrame().getHeight()));
				themes.getComponentPopupMenu().show(themes, e.getX(), e.getY());
			}

			public @Override void mouseEntered(MouseEvent e) {
				this.hovering = true;
				super.mouseEntered(e);
				backgroundListener.mouseEntered(e);
			}

			public @Override void mouseExited(MouseEvent e) {
				this.hovering = false;
				super.mouseExited(e);
				backgroundListener.mouseExited(e);
			}
		});

		menuBar.add(settings);
		menuBar.add(themes);
		menuBar.add(new JLabel("~ Chatty"));

		mainFrame.getBuilder().getJFrame().setJMenuBar(menuBar);

		SwingBuilder builder = new SwingBuilder()
			 .name("Chatty")
			 .theme(Themes.MATERIAL_DRACULA)
			 .frame(mainFrame);
		SwingUtilities.invokeLater(builder::build);
	}
}
