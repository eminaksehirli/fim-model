package be.uantwerpen.adrem.fim.model;

import java.util.BitSet;

/**
 * Represents an Item for frequent itemset mining. It has an integer id, a
 * BitSet of Transaction ID (TID) list, and a String name.
 * 
 * @author M. Emin Aksehirli
 * @author Sandy Moens
 * 
 */
public class Item implements Comparable<Item> {
	int id;
	protected BitSet tids;
	private String name;

	/**
	 * Default name is the id.
	 * 
	 * @param id
	 */
	public Item(int id) {
		this.id = id;
		tids = new BitSet();
		name = String.valueOf(id);
	}

	/**
	 * Default name is the id.
	 * 
	 * @param id
	 * @param tids Bitset of TIDs.
	 */
	public Item(int id, BitSet tids) {
		this(id);
		this.tids.or(tids);
	}

	/**
	 * Initialize the object with an ID and a name.
	 * @param id
	 * @param name
	 */
	public Item(int id, String name) {
		this(id);
		setName(name);
	}

	/**
	 * Add this transaction id (TID) to the list of TIDs. 
	 * @param tid
	 */
	public void setTID(int tid) {
		tids.set(tid);
	}

	/**
	 * Check whether this item is supported by this single transaction.
	 * @param tid ID of the transaction.
	 * @return <code>true</code> if <code>tid</code> is in the list of TIDs.
	 */
	public boolean supports(int tid) {
		return tids.get(tid);
	}

	@Override
	public int compareTo(Item o) {
		return id - o.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == other.id)
			return true;
		return false;
	}

	public BitSet getTIDs() {
		return tids;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	public void setName(String name) {
		this.name = name;
	}
}
