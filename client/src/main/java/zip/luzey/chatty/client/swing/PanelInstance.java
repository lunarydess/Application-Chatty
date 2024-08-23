package zip.luzey.chatty.client.swing;

import javax.swing.*;
import java.awt.*;

public class PanelInstance {
	private final Builder builder;

	public PanelInstance(final Builder builder) {
		this.builder = builder;
	}

	public void event(final AWTEvent event) {
		this.getBuilder().jPanel.dispatchEvent(event);
	}

	public void hide() {
		SwingUtilities.invokeLater(() -> this.getBuilder().getJPanel().setVisible(false));
	}

	public void show() {
		SwingUtilities.invokeLater(() -> this.getBuilder().getJPanel().setVisible(true));
	}

	public Builder getBuilder() {
		return this.builder;
	}

	public static final class Builder {
		private final PanelInstance panelInstance = new PanelInstance(this);
		private final JPanel jPanel = new JPanel();
		private boolean built = false;


		@Deprecated
		public JPanel getJPanel() {
			return this.jPanel;
		}

		public Builder layout(final LayoutManager layout) {
			this.jPanel.setLayout(layout);
			return this;
		}

		public Builder doubleBuffered(final boolean state) {
			this.jPanel.setDoubleBuffered(state);
			return this;
		}

		public Builder add(final Component component) {
			this.jPanel.add(component);
			return this;
		}

		public Builder add(final PopupMenu menu) {
			this.jPanel.add(menu);
			return this;
		}

		public Builder add(
			 final Component component,
			 final Object constraints
		) {
			this.jPanel.add(component, constraints);
			return this;
		}

		public Builder add(
			 final Component component,
			 final Object constraints,
			 final int index
		) {
			this.jPanel.add(component, constraints, index);
			return this;
		}

		public Builder add(
			 final Component component,
			 final int index
		) {
			this.jPanel.add(component, index);
			return this;
		}

		public Builder add(
			 final String name,
			 final Component component
		) {
			this.jPanel.add(name, component);
			return this;
		}

		public PanelInstance build() {
			if (this.built) {
				return this.panelInstance;
			}
			this.built = true;
			return this.panelInstance;
		}
	}
}
