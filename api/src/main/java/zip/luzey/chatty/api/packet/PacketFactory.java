package zip.luzey.chatty.api.packet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zip.luzey.chatty.api.packet.AbstractPacket.State;

import java.util.*;

public abstract class PacketFactory {
	private static final HashMap<State, Int2ObjectMap<Class<? extends AbstractPacket>>>
		 PACKETS = new HashMap<>(State.values().length);

	private final HashMap<
		 Class<? extends AbstractPacket>,
		 List<Class<Object>[]>[]
		 > factoryDict = new HashMap<>();

	@SuppressWarnings("unchecked")
	public PacketFactory(Object... values) {
		if (values.length < 3) throw new RuntimeException(
			 "%s lacks args".formatted(getClass().getSimpleName())
		);
		for (int i = 0 ; i < values.length ; i += 3) {
			if (i == values.length - 2) break;
			final Int2ObjectMap<Class<? extends AbstractPacket>> map = new Int2ObjectMap<>();
			map.put(
				 (int) values[i + 1],
				 (Class<? extends AbstractPacket>) values[i + 2]
			);
			PACKETS.put((State) values[i], map);
		}
	}

	public @Nullable Class<? extends AbstractPacket> byStateId(
		 State state,
		 int id
	) {
		Int2ObjectMap<Class<? extends AbstractPacket>> map = PACKETS.get(state);
		return map == null ? null : map.get(id);
	}

	public <P extends AbstractPacket> void register(
		 Class<P> clazz,
		 List<Class<Object>[]>[] params
	) {
		this.factoryDict.put(clazz, params);
	}

	@SuppressWarnings("unchecked")
	public <P extends AbstractPacket>
	@NotNull P create(
		 Class<P> clazz,
		 Object... params
	) throws Throwable {
		List<Class<Object>[]>[] factory = this.getFactoryDict().get(clazz);
		if (Objects.isNull(factory))
			throw new NoSuchMethodException(
				 "No class-entry for '%s' found".formatted(clazz.getSimpleName())
			);

		Class<Object>[] objClasses = Arrays.stream(params)
		                                   .map(Object::getClass)
		                                   .toArray(Class[]::new);

		boolean found = false;
		Throwable cause = null;
		P value = null;
		for (List<Class<Object>[]> list : factory) {
			Class<Object>[] paramsResolved =
				 list.stream()
				     .filter(paramsClazz -> Arrays.equals(objClasses, list.toArray(Class[]::new)))
				     .findFirst().orElse(null);
			if (paramsResolved == null) continue;
			try {
				value = clazz.getDeclaredConstructor(paramsResolved).newInstance(params);
				found = true;
			} catch (Throwable throwable) {
				cause = throwable;
			}
			break;
		}

		if (!found)
			throw new Throwable(
				 "couldn't find class \"%s\" with params %s".formatted(
					  clazz.getSimpleName(),
					  Arrays.toString(objClasses)
				 ), cause
			);

		return value;
	}

	public HashMap<
		 Class<? extends AbstractPacket>,
		 List<Class<Object>[]>[]
		 > getFactoryDict() {
		return this.factoryDict;
	}

	@SuppressWarnings("unchecked")
	public static final class Int2ObjectMap<V> {
		private static final int INITIAL_CAPACITY = 16;
		private static final float LOAD_FACTOR = 0.75f;

		private LinkedList<Int2ObjectMap.Entry<V>>[] table;
		private int size;

		public Int2ObjectMap() {
			this(INITIAL_CAPACITY);
		}

		public Int2ObjectMap(final int capacity) {
			this.table = new LinkedList[capacity];
			this.size = 0;
		}

		public void put(
			 final int key,
			 final V value
		) {
			if (size >= table.length * LOAD_FACTOR) {
				int newCapacity = table.length * 2;
				LinkedList<Int2ObjectMap.Entry<V>>[] newTable = new LinkedList[newCapacity];
				for (LinkedList<Int2ObjectMap.Entry<V>> entries : table) {
					if (entries == null) continue;
					for (Int2ObjectMap.Entry<V> entry : entries) {
						int index = hash(entry.value) & (newCapacity - 1);
						(newTable[index] == null ?
						 (newTable[index] = new LinkedList<>()) :
						 newTable[index]).add(entry);
					}
				}
				table = newTable;
			}

			int index = hash(key) & (table.length - 1);
			if (table[index] == null)
				table[index] = new LinkedList<>();

			for (Int2ObjectMap.Entry<V> entry : table[index]) {
				if (!Objects.equals(entry.key, key)) continue;
				entry.value = value;
				return;
			}

			table[index].add(new Int2ObjectMap.Entry<>(key, value));
			size++;
		}

		/**
		 * Gets the int-value of the given key.
		 *
		 * @param key The key we want our int-value from.
		 *
		 * @return The int-value or -1 if null or not found.
		 */
		public V get(int key) {
			int index = hash(key) & (table.length - 1);
			if (table[index] == null) return null;

			for (Int2ObjectMap.Entry<V> entry : table[index]) {
				if (!Objects.equals(entry.key, key)) continue;
				return entry.value;
			}

			return null;
		}

		/**
		 * Removes the int-value of the given key.
		 *
		 * @param key The key of our int-value we want to remove.
		 */
		public void remove(int key) {
			int index = hash(key) & (table.length - 1);
			if (table[index] != null) {
				table[index].removeIf(entry -> Objects.equals(entry.key, key));
				size--;
			}
		}

		/**
		 * @param key The key we want the hash-value from.
		 *
		 * @return A hash-value from our key.
		 */
		private int hash(Object key) {
			return key == null ? 0 : key.hashCode();
		}

		private static class Entry<V> {
			private final int key;
			private V value;

			Entry(
				 final int key,
				 final V value
			) {
				this.key = key;
				this.value = value;
			}

			public int getKey() {
				return this.key;
			}

			public V getValue() {
				return this.value;
			}

			public void setValue(final V value) {
				this.value = value;
			}
		}
	}
}
