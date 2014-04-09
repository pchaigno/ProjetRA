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
		data = new ArrayList<Integer>();
	}
	
	/**
	 * Constructor
	 * @param transaction The transaction as a list.
	 */
	public Transaction(List<Integer> transaction) {
		this.data = transaction;
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
	public List<Integer> getItems() {
		return this.data;
	}
	
	/**
	 * Checks that an itemset is contained in a transaction.
	 * @param itemset The itemset.
	 * @return True if it is.
	 */
	public boolean contains(Itemset itemset) {
		for(int i=0; i<itemset.size(); i++) {
			if(!this.data.contains(itemset.get(i))) {
				return false;
			}
		}
		return true;
	}
}