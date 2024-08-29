package zip.luzey.chatty.api.event;

import zip.luzey.chatty.api.packet.AbstractPacket;
import zip.luzey.tinyevents.AbstractEvent;
import zip.luzey.tinyevents.AbstractEvent.Cancellable;

import java.util.Objects;
import java.util.StringJoiner;

public final class PacketEvent {
	public static final class Send<P extends AbstractPacket>
		 extends AbstractEvent
		 implements Cancellable {
		private final P packet;
		private boolean cancelled = false;

		public Send(final P packet) {
			this.packet = packet;
		}

		public P getPacket() {
			return this.packet;
		}

		@Override
		public int hashCode() {
			return Objects.hash(
				 this.packet,
				 this.cancelled
			);
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Receive<?> && equals(o);
		}

		public boolean equals(Receive<P> receive) {
			return this.hashCode() == receive.hashCode() && (
				 this.getPacket() == receive.getPacket() &&
				 this.cancelled() == receive.cancelled
			);
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Send.class.getSimpleName() + "[", "]")
				 .add("packet=" + packet)
				 .add("cancelled=" + cancelled)
				 .toString();
		}

		@Override
		public void cancel(boolean state) {
			this.cancelled = state;
		}

		@Override
		public boolean cancelled() {
			return this.cancelled;
		}
	}

	public static final class Receive<P extends AbstractPacket>
		 extends AbstractEvent
		 implements Cancellable {
		private final AbstractPacket packet;
		private boolean cancelled = false;

		public Receive(final AbstractPacket packet) {
			this.packet = packet;
		}

		public AbstractPacket getPacket() {
			return this.packet;
		}

		@Override
		public int hashCode() {
			return Objects.hash(
				 this.packet,
				 this.cancelled
			);
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Receive<?> && equals(o);
		}

		public boolean equals(Receive<P> receive) {
			return this.hashCode() == receive.hashCode() && (
				 this.getPacket() == receive.getPacket() &&
				 this.cancelled() == receive.cancelled
			);
		}

		@Override
		public String toString() {
			return new StringJoiner(", ", Send.class.getSimpleName() + "[", "]")
				 .add("packet=" + packet)
				 .add("cancelled=" + cancelled)
				 .toString();
		}

		@Override
		public void cancel(boolean state) {
			this.cancelled = state;
		}

		@Override
		public boolean cancelled() {
			return this.cancelled;
		}
	}
}
