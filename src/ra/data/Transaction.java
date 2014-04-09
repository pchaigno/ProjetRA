package ra.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ra.algo.Itemset;

public class Transaction {
	protected Set<Integer> items;
	protected List<Integer> data;

	/**
	 * Empty constructor.
	 */
	public Transaction() {
		this.data = new ArrayList<Integer>();
		this.items = new HashSet<Integer>();
	}
	
	/**
	 * Add an item to the transaction.
	 * @param i The value.
	 */
	public void addItem(int i) {
		this.data.add(i);
		this.items.add(i);
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
		for(int i=0; i<itemset.size(); i++) {
			if(!this.data.contains(itemset.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "Transaction: size="+this.data.size();
	}
}