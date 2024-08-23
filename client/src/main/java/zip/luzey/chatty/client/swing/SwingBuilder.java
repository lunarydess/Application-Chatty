package zip.luzey.chatty.client.swing;

import org.jetbrains.annotations.NotNull;
import zip.luzey.chatty.client.utility.OperatingSystem;

import javax.swing.*;

public final class SwingBuilder {
	static {
		switch (OperatingSystem.get()) {
			case LINUX -> {
				JFrame.setDefaultLookAndFeelDecorated(true);
				JDialog.setDefaultLookAndFeelDecorated(true);
			}

			case MAC -> {
				System.setProperty("apple.laf.useScreenMenuBar", "true");
				System.setProperty("apple.awt.application.name", "Swing Builder");
				System.setProperty("apple.awt.application.appearance", "system");
			}
		}
	}

	private FrameInstance[] instances = new FrameInstance[0];

	public void build() {
		for (FrameInstance instance : instances)
			instance.show();
	}

	public SwingBuilder name(final @NotNull String applicationName) {
		if (OperatingSystem.get() == OperatingSystem.MAC)
			System.setProperty("apple.awt.application.name", applicationName);
		return this;
	}

	public SwingBuilder theme(final Themes theme) {
		Themes.select(theme);
		return this;
	}

	public SwingBuilder frame(final FrameInstance... instances) {
		this.instances = instances;
		return this;
	}

	public FrameInstance[] getInstances() {
		return this.instances;
	}
}
