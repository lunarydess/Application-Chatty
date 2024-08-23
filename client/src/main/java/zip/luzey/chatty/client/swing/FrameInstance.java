package zip.luzey.chatty.client.swing;


import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.function.Consumer;

public final class FrameInstance {
	private final Builder builder;

	private FrameInstance(final Builder builder) {
		this.builder = builder;
	}

	public Builder getBuilder() {
		return this.builder;
	}

	public void event(final AWTEvent event) {
		this.getBuilder().jFrame.dispatchEvent(event);
	}

	public void hide() {
		SwingUtilities.invokeLater(() -> this.getBuilder().getJFrame().setVisible(false));
	}

	public void show() {
		SwingUtilities.invokeLater(() -> this.getBuilder().getJFrame().setVisible(true));
	}

	public static final class Builder {
		@SuppressWarnings("rawtypes")
		private static final Consumer EMPTY = obj -> {};

		private final JFrame jFrame = new JFrame();
		private final FrameInstance instance = new FrameInstance(this);

		private final IdentityHashMap<Window, WindowAdapter> windowEvents = new IdentityHashMap<>();
		private final IdentityHashMap<Component, ComponentAdapter> componentEvents = new IdentityHashMap<>();

		private boolean built = false;

		public Builder title(final String title) {
			this.jFrame.setTitle(title);
			return this;
		}

		public Builder locale(final Locale locale) {
			this.jFrame.setLocale(locale);
			return this;
		}

		public Builder location(final int x, final int y) {
			this.jFrame.setLocation(x, y);
			return this;
		}

		public Builder location(final Point point) {
			this.jFrame.setLocation(point);
			return this;
		}

		public Builder locationDefault() {
			this.jFrame.setLocationRelativeTo(null);
			this.jFrame.setLocationByPlatform(true);
			return this;
		}


		public Builder locationByPlatform() {
			return this.locationByPlatform(true);
		}

		public Builder locationByPlatform(final boolean atDefault) {
			this.jFrame.setLocationByPlatform(atDefault);
			return this;
		}

		public Builder locationRelativeTo(final java.awt.Component component) {
			this.jFrame.setLocationRelativeTo(component);
			return this;
		}

		public Builder defaultSize() {
			Dimension dimension;
			try {
				dimension = Toolkit.getDefaultToolkit().getScreenSize();
				dimension.setSize(Math.round(dimension.getWidth() * 0.4D), dimension.getHeight() * 0.35D);
			} catch (final Throwable ignored) {
				dimension = new Dimension(240, 180);
			}
			this.jFrame.setSize(dimension);
			return this;
		}

		public Builder resizable(final boolean resizable) {
			this.jFrame.setResizable(resizable);
			return this;
		}

		public Builder icon(final InputStream inputStream) {
			return this.icon(inputStream, true);
		}

		public Builder icon(
			 final InputStream inputStream,
			 final boolean flushImage
		) {
			try {
				final BufferedImage image = ImageIO.read(inputStream);
				return this.icon(image, flushImage);
			} catch (final Throwable throwable) {
				throwable.printStackTrace();
				return this;
			}
		}

		public Builder icon(final Image image) {
			return this.icon(image, true);
		}

		public Builder icon(final Image image, final boolean flush) {
			this.jFrame.setIconImage(image);
			if (flush) image.flush();
			return this;
		}

		public Builder layout(final LayoutManager layout) {
			this.jFrame.setLayout(layout);
			return this;
		}

		public Builder addWindowEvent(final Window window) {
			final WindowAdapter windowAdapter = new WindowAdapter() {
				// @formatter:off
				public @Override void windowOpened      (final WindowEvent event) { window.whenOpened      .accept(event); }
				public @Override void windowClosing     (final WindowEvent event) { window.whenClosing     .accept(event); }
				public @Override void windowClosed      (final WindowEvent event) { window.whenClosed      .accept(event); }
				public @Override void windowIconified   (final WindowEvent event) { window.whenIconApplied .accept(event); }
				public @Override void windowDeiconified (final WindowEvent event) { window.whenIconRemoved .accept(event); }
				public @Override void windowActivated   (final WindowEvent event) { window.whenActivated   .accept(event); }
				public @Override void windowDeactivated (final WindowEvent event) { window.whenDeactivated .accept(event); }
				public @Override void windowStateChanged(final WindowEvent event) { window.whenStateChanged.accept(event); }
				public @Override void windowGainedFocus (final WindowEvent event) { window.whenGainedFocus .accept(event); }
				public @Override void windowLostFocus   (final WindowEvent event) { window.whenLostFocus   .accept(event); }
				// @formatter:on
			};
			this.jFrame.addWindowListener(windowAdapter);
			this.windowEvents.put(window, windowAdapter);
			return this;
		}

		public Builder removeWindowEvent(final Window window) {
			this.jFrame.removeWindowListener(this.windowEvents.get(window));
			this.windowEvents.remove(window);
			return this;
		}

		public Builder addComponentEvent(final Component component) {
			final ComponentAdapter componentAdapter = new ComponentAdapter() {
				// @formatter:off
				public @Override void componentResized(ComponentEvent event) { component.whenResized.accept(event); }
				public @Override void componentMoved  (ComponentEvent event) { component.whenMoved  .accept(event); }
				public @Override void componentShown  (ComponentEvent event) { component.whenShown  .accept(event); }
				public @Override void componentHidden (ComponentEvent event) { component.whenHidden .accept(event); }
				// @formatter:on
			};
			this.jFrame.addComponentListener(componentAdapter);
			this.componentEvents.put(component, componentAdapter);
			return this;
		}

		public Builder removeComponentEvent(final Component component) {
			this.jFrame.removeComponentListener(this.componentEvents.get(component));
			this.componentEvents.remove(component);
			return this;
		}

		public Builder size(final int width, final int height) {
			this.jFrame.setSize(width, height);
			return this;
		}

		public Builder panel(final PanelInstance instance) {
			return this.panel(instance, null);
		}

		public Builder panel(final PanelInstance instance, final @Nullable String constraints) {
			this.jFrame.add(instance.getBuilder().getJPanel(), constraints);
			return this;
		}

		public FrameInstance build() {
			if (this.built) {
				return this.instance;
			}
			this.built = true;
			return this.instance;
		}

		/**
		 * @return the original frame
		 */
		@Deprecated
		public JFrame getJFrame() {
			return this.jFrame;
		}

		public static final class Window {
			// @formatter:off
			@SuppressWarnings("unchecked")
			private Consumer<WindowEvent>
				 whenOpened       = EMPTY,
				 whenClosing      = EMPTY,
				 whenClosed       = EMPTY,
				 whenIconApplied  = EMPTY,
				 whenIconRemoved  = EMPTY,
				 whenActivated    = EMPTY,
				 whenDeactivated  = EMPTY,
				 whenStateChanged = EMPTY,
				 whenGainedFocus  = EMPTY,
				 whenLostFocus    = EMPTY;
			// @formatter:on

			public Window opened(final Consumer<WindowEvent> consumer) {
				this.whenOpened = consumer;
				return this;
			}

			public Window closing(final Consumer<WindowEvent> consumer) {
				this.whenClosing = consumer;
				return this;
			}

			public Window closed(final Consumer<WindowEvent> consumer) {
				this.whenClosed = consumer;
				return this;
			}

			public Window iconApplied(final Consumer<WindowEvent> consumer) {
				this.whenIconApplied = consumer;
				return this;
			}

			public Window iconRemoved(final Consumer<WindowEvent> consumer) {
				this.whenIconRemoved = consumer;
				return this;
			}

			public Window deactivated(final Consumer<WindowEvent> consumer) {
				this.whenDeactivated = consumer;
				return this;
			}

			public Window activated(final Consumer<WindowEvent> consumer) {
				this.whenActivated = consumer;
				return this;
			}

			public Window stateChanged(final Consumer<WindowEvent> consumer) {
				this.whenStateChanged = consumer;
				return this;
			}

			public Window focusGained(final Consumer<WindowEvent> consumer) {
				this.whenGainedFocus = consumer;
				return this;
			}

			public Window focusLost(final Consumer<WindowEvent> consumer) {
				this.whenLostFocus = consumer;
				return this;
			}
		}

		public static class Component {
			// @formatter:off
			@SuppressWarnings("unchecked")
			private Consumer<ComponentEvent>
				 whenResized = EMPTY,
				 whenMoved   = EMPTY,
				 whenShown   = EMPTY,
				 whenHidden  = EMPTY;
			// @formatter:on

			public Component resized(final Consumer<ComponentEvent> consumer) {
				this.whenResized = consumer;
				return this;
			}

			public Component moved(final Consumer<ComponentEvent> consumer) {
				this.whenMoved = consumer;
				return this;
			}

			public Component shown(final Consumer<ComponentEvent> consumer) {
				this.whenShown = consumer;
				return this;
			}

			public Component hidden(final Consumer<ComponentEvent> consumer) {
				this.whenHidden = consumer;
				return this;
			}
		}
	}
}
