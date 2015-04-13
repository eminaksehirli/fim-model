package be.uantwerpen.adrem.fim.model;

import static java.util.Collections.unmodifiableSet;

import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class PlainItemSet implements Collection<PlainItem> {

	private final SortedSet<PlainItem> items;

	public PlainItemSet() {
		items = new TreeSet<PlainItem>();
	}

	public PlainItemSet(PlainItem... newItems) {
		this();
		for (PlainItem newItem : newItems) {
			items.add(newItem);
		}
	}

	public PlainItemSet(PlainItemSet aPlainItemSet) {
		items = new TreeSet<PlainItem>(aPlainItemSet.items);
	}

	public PlainItemSet(Iterable<PlainItem> items) {
		this();
		for (PlainItem item : items) {
			this.items.add(item);
		}
	}

	public BitSet getTIDs() {
		// TODO check if BitSet can stay
		BitSet tids = new BitSet();
		Iterator<PlainItem> it = items.iterator();
		if (it.hasNext()) {
			tids.or(it.next().getTIDs()); // setup

			for (; it.hasNext();) {
				tids.and(it.next().getTIDs());
			}
		}
		return tids;
	}

	@Override
	public boolean add(PlainItem newItem) {
		return items.add(newItem);
	}

	@Override
	public boolean addAll(Collection<? extends PlainItem> plainItemSet) {
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

	public boolean contains(PlainItem item) {
		return items.contains(item);
	}

	public Set<PlainItem> asCollection() {
		return unmodifiableSet(items);
	}

	public boolean containsAll(PlainItemSet head) {
		return items.containsAll(head.items);
	}

	@Override
	public String toString() {
		return items.toString();
	}

	@Override
	public Iterator<PlainItem> iterator() {
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
		PlainItemSet other = (PlainItemSet) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (other.items.size() != items.size()) {
			return false;
		} else {
			for (PlainItem plainItem : other.items) {
				if (!items.contains(plainItem)) {
					return false;
				}
			}
		}
		return true;
	}

	public final static class ItemSetSizeComparator implements
			Comparator<PlainItemSet> {
		@Override
		public int compare(PlainItemSet o1, PlainItemSet o2) {
			int overSize = o2.size() - o1.size();
			if (overSize == 0) {
				int overCardinality = o2.getTIDs().cardinality()
						- o1.getTIDs().cardinality();
				if (overCardinality == 0) {
					if (o1.equals(o2)) {
						return 0;
					}

					Iterator<PlainItem> firstIterator = o1.iterator();
					Iterator<PlainItem> secondIterator = o2.iterator();

					for (; firstIterator.hasNext();) {
						PlainItem item2 = secondIterator.next();
						PlainItem item1 = firstIterator.next();
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

	public final static class SupportComparator implements
			Comparator<PlainItemSet> {

		@Override
		public int compare(PlainItemSet o1, PlainItemSet o2) {
			return o2.getTIDs().cardinality() - o1.getTIDs().cardinality();
		}
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
