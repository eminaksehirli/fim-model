package be.uantwerpen.adrem.fim.model;

import java.util.BitSet;

public class Item implements Comparable<Item> {
	int id;
	protected BitSet tids;
	private String name;

	public Item(int id) {
		this.id = id;
		tids = new BitSet();
		name = String.valueOf(id);
	}

	public Item(int id, BitSet tids) {
		this(id);
		this.tids.or(tids);
	}

	public Item(int id, String name) {
		this(id);
		setName(name);
	}

	public void setTID(int txCounter) {
		tids.set(txCounter);
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

	public boolean supports(int tid) {
		return tids.get(tid);
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
