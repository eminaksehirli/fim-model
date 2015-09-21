package be.uantwerpen.adrem.fim.model;

import static java.lang.Integer.parseInt;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlainItemDB implements Iterable<Item> {
	private final Map<Integer, Item> items;

	public PlainItemDB() {
		this.items = new HashMap<Integer, Item>();
	}

	public Item get(int itemId) {
		Item item = this.items.get(itemId);
		if (item == null) {
			item = new Item(itemId);
			this.items.put(itemId, item);
		}

		return item;
	}

	public Item get(int itemId, BitSet tids) {
		Item item = this.items.get(itemId);
		if (item == null) {
			item = new Item(itemId, tids);
			this.items.put(itemId, item);
		} else {
			throw new IllegalStateException(
					"Item is already in the DB, cannot set TIds!");
		}

		return item;
	}

	public Item get(String itemId) {
		return get(parseInt(itemId.trim()));
	}

	public int size() {
		return this.items.size();
	}

	@Override
	public Iterator<Item> iterator() {
		return this.items.values().iterator();
	}
}
