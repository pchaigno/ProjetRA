package ra.data;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import ra.algo.Itemset;

public abstract class Database {
	protected Set<Integer> items;
	
	/**
	 * Computes the supports for a list of itemset with the transactions in this database.
	 * @param itemsets The itemsets which will be updated with their supports.
	 * @throws IOException If the database file can't be opened or read.
	 */
	public abstract void calcSupport(List<Itemset> itemsets);
	
	/**
	 * Extracts the itemsets with the minimum support from a list of itemset.
	 * Uses calcSupport.
	 * @see calcSupport.
	 * @param itemsets The itemsets which will be updated with their support.
	 * Those which don't have the minimum support will be removed.
	 * @param minSupport The minimum support.
	 */
	public void withMinSupport(List<Itemset> itemsets, double minSupport) {
		this.calcSupport(itemsets);
		for(int i=0; i<itemsets.size(); i++) {
			if(itemsets.get(i).getSupport() < minSupport) {
				itemsets.remove(i);
			}
		}
	}
	
	/**
	 * Retrieves the items from the database.
	 * @return A set of items.
	 */
	public abstract Set<Integer> retrieveItems();
}
