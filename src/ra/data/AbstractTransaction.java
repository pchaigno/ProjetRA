package ra.data;

import java.util.ArrayList;
import java.util.List;

import ra.algo.Itemset;

public abstract class AbstractTransaction<T> {

	protected List<T> data;

	public AbstractTransaction() {
		data = new ArrayList<T>();
	}
	
	/**
	 * Constructor
	 * @param transaction The transaction as a list.
	 */
	public AbstractTransaction(List<T> transaction) {
		this.data = transaction;
	}
	
	/**
	 * @return The items of the transaction as a list.
	 */
	public List<T> getItems() {
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
