package be.uantwerpen.adrem.fim.model;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Transaction implements Iterable<Item> {
	private final Set<Item> items = new HashSet<Item>();

	public Transaction(Item... initialItems) {
		items.addAll(asList(initialItems));
	}

	public void add(Item item) {
		items.add(item);
	}

	public boolean contains(Item item) {
		return items.contains(item);
	}

	public Collection<Item> asCollection() {
		return unmodifiableSet(items);
	}

	@Override
	public Iterator<Item> iterator() {
		return asCollection().iterator();
	}

	public int size() {
		return items.size();
	}

	@Override
	public String toString() {
		return items.toString();
	}
}
