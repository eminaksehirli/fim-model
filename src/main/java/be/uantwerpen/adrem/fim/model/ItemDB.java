package be.uantwerpen.adrem.fim.model;

import static java.lang.Integer.parseInt;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Database of {@link Item}s.
 * 
 * @author Emin Aksehirli
 * @author Sandy Moens
 */
public class ItemDB implements Iterable<Item> {
	private final Map<Integer, Item> items;

	public ItemDB() {
		this.items = new HashMap<Integer, Item>();
	}

	/**
	 * Returns the item if it is already in the database. If the {@code itemId}
	 * does not exist, this method creates a new Item and returns it.
	 * 
	 * @param itemId
	 * @return The existing item if it is in the DB, a new item if it does not.
	 */
	public Item get(int itemId) {
		Item item = this.items.get(itemId);
		if (item == null) {
			item = new Item(itemId);
			this.items.put(itemId, item);
		}

		return item;
	}

	/**
	 * Helper method that creates a new item with the tids. See {@link #get(int)}.
	 * 
	 * @throws IllegalStateException
	 *           if itemId already exists in the database.
	 * @param itemId
	 * @param tids
	 * @return
	 */
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

	/**
	 * Helper method that parses integer string to int.
	 * 
	 * @param itemId
	 *          itemId should be an integer.
	 * @return
	 */
	public Item get(String itemId) {
		return get(parseInt(itemId.trim()));
	}

	/**
	 * 
	 * @return Number of items in the DB.
	 */
	public int size() {
		return this.items.size();
	}

	@Override
	public Iterator<Item> iterator() {
		return this.items.values().iterator();
	}
}
