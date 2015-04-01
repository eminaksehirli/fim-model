package be.uantwerpen.adrem.fim.base;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PlainTransaction implements Iterable<PlainItem> {
	private final Set<PlainItem> items = new HashSet<PlainItem>();

	public PlainTransaction(PlainItem... initialItems) {
		items.addAll(asList(initialItems));
	}

	public void add(PlainItem item) {
		items.add(item);
	}

	public boolean contains(PlainItem item) {
		return items.contains(item);
	}

	public Collection<PlainItem> asCollection() {
		return items;
	}

	@Override
	public Iterator<PlainItem> iterator() {
		return asCollection().iterator();
	}

	public int size() {
		return items.size();
	}
}
