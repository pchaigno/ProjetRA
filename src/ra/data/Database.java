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
	 * @throws IOException If the database file can't be opened or read.
	 */
	public abstract void calcSupport(List<Itemset> itemsets, double minSupport);
	
	/**
	 * Extracts the itemsets with the minimum support from a list of itemset.
	 * Uses calcSupport.
	 * @see calcSupport.
	 * @param itemsets The itemsets which will be updated with their support.
	 * Those which don't have the minimum support will be removed.
	 * @param minSupport The minimum support.
	 */
	public void withMinSupport(List<Itemset> itemsets, double minSupport) {
		this.calcSupport(itemsets, minSupport);/*
		for(int i=0; i<itemsets.size(); i++) {
			if(itemsets.get(i).getSupport() < minSupport) {
				itemsets.remove(i);
				i--;
			}
		}*/
	}
	
	/**
	 * Retrieves the items from the database.
	 * @return A set of items.
	 */
	public abstract Set<Integer> retrieveItems();
}
