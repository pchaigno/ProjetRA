package ra.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ra.algo.Itemset;

public abstract class Database {
	protected Set<Integer> items;
	
	/**
	 * Computes the supports for a list of itemset with the transactions in this database.
	 * @param itemsets The itemsets
	 * @return The support value for each itemset.
	 * @throws IOException If the database file can't be opened or read.
	 */
	public abstract Map<Itemset, Double> calcSupport(List<Itemset> itemsets);
	
	/**
	 * Extracts the itemsets with the minimum support from a list of itemset.
	 * Uses calcSupport.
	 * @see calcSupport.
	 * @param itemsets The itemsets.
	 * @param minSupport The minimum support.
	 * @return The itemsets with the minimum support.
	 */
	public List<Itemset> withMinSupport(List<Itemset> itemsets, double minSupport) {
		Map<Itemset, Double> supports = this.calcSupport(itemsets);
		List<Itemset> itemsetsWithMinSupport = new ArrayList<Itemset>();
		for(Itemset itemset: supports.keySet()) {
			if(supports.get(itemset) >= minSupport) {
				itemsetsWithMinSupport.add(itemset);
			}
		}
		return itemsetsWithMinSupport;
	}
	
	/**
	 * Retrieves the items from the database.
	 * @return A set of items.
	 */
	public abstract Set<Integer> retrieveItems();
}
