package ra.data;

import java.util.ArrayList;
import java.util.List;

import ra.algo.Itemset;

public class Transaction {
	protected List<Integer> data;

	/**
	 * Empty constructor.
	 */
	public Transaction() {
		this.data = new ArrayList<Integer>();
	}
	
	/**
	 * Add an item to the transaction.
	 * @param i The value.
	 */
	public void addItem(int i) {
		this.data.add(i);
	}
	
	/**
	 * @return The items of the transaction as a list.
	 */
	public List<Integer> getData() {
		return this.data;
	}
	
	/**
	 * Checks that an itemset is contained in a transaction.
	 * @param itemset The itemset.
	 * @return True if it is.
	 */
	public boolean contains(Itemset itemset) {
		return this.data.containsAll(itemset.getItems());
	}
	
	@Override
	public String toString() {
		return "Transaction: size="+this.data.size();
	}
}