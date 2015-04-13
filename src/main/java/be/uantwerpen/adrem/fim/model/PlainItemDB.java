package be.uantwerpen.adrem.fim.model;

import static java.lang.Integer.parseInt;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlainItemDB implements Iterable<PlainItem> {
	private final Map<Integer, PlainItem> items;

	public PlainItemDB() {
		this.items = new HashMap<Integer, PlainItem>();
	}

	public PlainItem get(int itemId) {
		PlainItem item = this.items.get(itemId);
		if (item == null) {
			item = new PlainItem(itemId);
			this.items.put(itemId, item);
		}

		return item;
	}

	public PlainItem get(int itemId, BitSet tids) {
		PlainItem item = this.items.get(itemId);
		if (item == null) {
			item = new PlainItem(itemId, tids);
			this.items.put(itemId, item);
		} else {
			throw new IllegalStateException(
					"Item is already in the DB, cannot set TIds!");
		}

		return item;
	}

	public PlainItem get(String itemId) {
		return get(parseInt(itemId.trim()));
	}

	public int size() {
		return this.items.size();
	}

	@Override
	public Iterator<PlainItem> iterator() {
		return this.items.values().iterator();
	}
}
