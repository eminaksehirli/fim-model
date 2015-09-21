package be.uantwerpen.adrem.fim.model;

import static java.util.Collections.unmodifiableSet;

import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A set of {@link Item}s. Uses a {@link TreeSet} as collection.
 * 
 * Provides two more comparators for convenience: {@link SupportComparator} and
 * {@link ItemSetSizeComparator}.
 * 
 * @author Emin Aksehirli
 * @author Sandy Moens
 * 
 */
public class Itemset implements Collection<Item> {

	private final SortedSet<Item> items;

	/**
	 * Compares {@link Itemset}s according to their sizes.
	 */
	public final static class ItemSetSizeComparator implements
			Comparator<Itemset> {
		@Override
		public int compare(Itemset o1, Itemset o2) {
			int overSize = o2.size() - o1.size();
			if (overSize == 0) {
				int overCardinality = o2.getTIDs().cardinality()
						- o1.getTIDs().cardinality();
				if (overCardinality == 0) {
					if (o1.equals(o2)) {
						return 0;
					}

					Iterator<Item> firstIterator = o1.iterator();
					Iterator<Item> secondIterator = o2.iterator();

					for (; firstIterator.hasNext();) {
						Item item2 = secondIterator.next();
						Item item1 = firstIterator.next();
						int overItems = item1.compareTo(item2);
						if (overItems != 0) {
							return overItems;
						}
					}
					return 0;
				}
				return overCardinality;
			}
			return overSize;
		}
	}

	/**
	 * Compares {@link Itemset}s according to their support.
	 */
	public final static class SupportComparator implements Comparator<Itemset> {
		@Override
		public int compare(Itemset o1, Itemset o2) {
			return o2.getTIDs().cardinality() - o1.getTIDs().cardinality();
		}
	}

	public Itemset() {
		items = new TreeSet<Item>();
	}

	public Itemset(Item... newItems) {
		this();
		for (Item newItem : newItems) {
			items.add(newItem);
		}
	}

	public Itemset(Itemset anItemSet) {
		items = new TreeSet<Item>(anItemSet.items);
	}

	public Itemset(Iterable<Item> items) {
		this();
		for (Item item : items) {
			this.items.add(item);
		}
	}

	/**
	 * Returns the TIDs of the itemset. They are not cached and computed on the
	 * fly.
	 * 
	 * @return
	 */
	public BitSet getTIDs() {
		// TODO check if BitSet can be cached
		BitSet tids = new BitSet();
		Iterator<Item> it = items.iterator();
		if (it.hasNext()) {
			tids.or(it.next().getTIDs()); // setup

			for (; it.hasNext();) {
				tids.and(it.next().getTIDs());
			}
		}
		return tids;
	}

	public boolean contains(Item item) {
		return items.contains(item);
	}

	public Set<Item> asSet() {
		return unmodifiableSet(items);
	}

	public boolean containsAll(Itemset head) {
		return items.containsAll(head.items);
	}

	@Override
	public boolean add(Item newItem) {
		return items.add(newItem);
	}

	@Override
	public boolean addAll(Collection<? extends Item> plainItemSet) {
		return items.addAll(plainItemSet);
	}

	@Override
	public boolean remove(Object itemToRemove) {
		return items.remove(itemToRemove);
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public String toString() {
		return items.toString();
	}

	@Override
	public Iterator<Item> iterator() {
		return items.iterator();
	}

	@Override
	public int hashCode() {
		return items.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itemset other = (Itemset) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (other.items.size() != items.size()) {
			return false;
		} else {
			for (Item item : other.items) {
				if (!items.contains(item)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return items.contains(o);
	}

	@Override
	public Object[] toArray() {
		return items.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return items.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return items.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return items.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return items.retainAll(c);
	}

	@Override
	public void clear() {
		items.clear();
	}
}
