package be.uantwerpen.adrem.fim.model;

import static java.util.Collections.unmodifiableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlainTransactionDB {
	private static final String Delimiter = " ";

	private String databaseName;

	private final List<PlainTransaction> transactions = new ArrayList<PlainTransaction>();

	private final PlainItemDB itemsDB;

	public PlainTransactionDB() {
		itemsDB = new PlainItemDB();
	}

	public PlainTransactionDB(String fileName) {
		this();
		populateFromFile(fileName);
	}

	public PlainTransactionDB(PlainItemDB itemsDB) {
		this.itemsDB = itemsDB;
		Map<Integer, PlainTransaction> txMap = new HashMap<Integer, PlainTransaction>();
		for (PlainItem item : itemsDB) {
			final BitSet tids = item.getTIDs();
			for (int i = tids.nextSetBit(0); i >= 0; i = tids.nextSetBit(i + 1)) {
				PlainTransaction tx = txMap.get(i);
				if (tx == null) {
					tx = new PlainTransaction();
					txMap.put(i, tx);
				}
				tx.add(item);
			}
		}

		// Keep the order of transactions
		for (int i = 0; !txMap.isEmpty(); i++) {
			PlainTransaction tx = txMap.remove(i);
			if (tx == null) {
				tx = new PlainTransaction();
			}
			transactions.add(tx);
		}
	}

	private void populateFromFile(String fileName)
			throws IllegalArgumentException {
		extractDatabaseName(fileName);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] splittedLine = line.split(Delimiter);

				PlainTransaction tx = new PlainTransaction();
				for (String itemStr : splittedLine) {
					PlainItem item = itemsDB.get(itemStr);
					item.setTID(transactions.size());
					tx.add(item);
				}
				transactions.add(tx);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void extractDatabaseName(String fileName) {
		int beginIndex = 0;
		int endIndex = fileName.length();
		if (fileName.contains("/")) {
			beginIndex = fileName.lastIndexOf("/") + 1;
		}
		if (fileName.contains(".") && fileName.lastIndexOf('.') > beginIndex) {
			endIndex = fileName.lastIndexOf('.');
		}
		databaseName = fileName.substring(beginIndex, endIndex);
	}

	public void setDatabaseName(String fileName) {
		extractDatabaseName(fileName);
	}

	public String getName() {
		return databaseName;
	}

	public PlainItem getItem(int itemId) {
		return itemsDB.get(itemId);
	}

	public PlainItem getItem(String itemId) {
		return itemsDB.get(itemId);
	}

	public void add(PlainTransaction plainTransaction) {
		for (PlainItem item : plainTransaction) {
			item.setTID(transactions.size());
		}
		transactions.add(plainTransaction);
	}

	public List<PlainTransaction> getTransactions() {
		return unmodifiableList(transactions);
	}

	public PlainItemDB getItemDB() {
		return itemsDB;
	}

	public int getNumberOfTransactions() {
		return transactions.size();
	}

	public int getNumberOfItems() {
		return itemsDB.size();
	}
}
