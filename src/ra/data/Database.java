package ra.data;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import ra.algo.Itemset;

public abstract class Database {
	protected Set<Integer> items;
	
	/**
	 * Computes the absolute support from the relative one.
	 * absolute_support = nb_transactions * relative_support.
	 * @param relativeSupport The relative support.
	 * @return The absolute support.
	 */
	public int calcAbsoluteSupport(double relativeSupport) {
		int support = -1;
		try {
			support = Math.round(Math.round(relativeSupport * this.getNbTransactions()));
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		return support;
	}
	
	/**
	 * @return The number of transactions.
	 */
	protected abstract int getNbTransactions() throws IOException;
	
	/**
	 * Computes the supports for a list of itemset with the transactions in this database.
	 * @param itemsets The itemsets which will be updated with their supports.
	 */
	public abstract void calcSupport(List<Itemset> itemsets);

	/**
	 * Updates the supports for a list of itemsets which weren't completly computed.
	 * @param itemsets The itemsets which will be updated with their supports.
	 */
	public abstract void updateSupport(List<Itemset> itemsets);
	
	/**
	 * Starts computing the supports for a list of itemset with the transactions in this database.
	 * The computation for an item will stop if the item has the minimum support.
	 * Therefore, it will need to be completed later, before the rule's generation.
	 * This can be usefull for closed and maximum itemsets.
	 * @param itemsets The itemsets. This list will be updated to contain only the non-frequent itemsets.
	 * @param minSupport The minimum support.
	 * @return The frequent itemsets.
	 */
	protected abstract List<Itemset> calcSupportIncomplete(List<Itemset> itemsets, int minSupport);
	
	/**
	 * Extracts the itemsets with the minimum support from a list of itemset.
	 * Uses calcSupport.
	 * @see calcSupport.
	 * @param itemsets The itemsets which will be updated with their support.
	 * Those which don't have the minimum support will be removed.
	 * @param completeSupportCalc If set to true, the support will be completly computed.
	 * If not, the calculations will stop once the support is equal to the minimum support.
	 * @param minSupport The minimum support.
	 */
	public List<Itemset> withMinSupport(List<Itemset> itemsets, int minSupport, boolean completeSupportCalc) {
		if(completeSupportCalc) {
			this.calcSupport(itemsets);
			for(int i=0; i<itemsets.size(); i++) {
				if(itemsets.get(i).getSupport() < minSupport) {
					itemsets.remove(i);
					i--;
				}
			}
			return itemsets;
		}
		return this.calcSupportIncomplete(itemsets, minSupport);
	}
	
	/**
	 * Retrieves the items from the database.
	 * @return A set of items.
	 */
	public abstract Set<Integer> retrieveItems();
}
