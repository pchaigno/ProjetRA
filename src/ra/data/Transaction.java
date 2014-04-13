package ra.data;

import java.util.ArrayList;
import java.util.List;


public class Transaction {
	protected List<Item> items;

	/**
	 * Empty constructor.
	 */
	public Transaction() {
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * Add an item to the transaction.
	 * @param i The item.
	 */
	public void addItem(Item i) {
		this.items.add(i);
	}
	
	/**
	 * @return The items of the transaction as a list.
	 */
	public List<Item> getData() {
		return this.items;
	}
	
	/**
	 * Checks that an itemset is contained in a transaction.
	 * @param itemset The itemset.
	 * @return True if it is.
	 */
	public boolean contains(Itemset itemset) {
		return this.items.containsAll(itemset.getItems());
	}
	
	@Override
	public String toString() {
		return "Transaction: size="+this.items.size();
	}
}