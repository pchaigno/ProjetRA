package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.AbstractTransaction;

/**
 * 
 * @author gwendal
 *
 * @param <T> type of transactions
 * @param <I> type of itemsets
 */
public abstract class AbstractAPriori<T extends AbstractTransaction<?>, I extends AbstractItemset<?>> {
	protected List<T> transactions;
	protected List<List<I>> itemsets;
	
	/**
	 * Constructor
	 * @param transactions The transactions.
	 */
	public AbstractAPriori(List<T> transactions) {
		this.transactions = transactions;
		this.itemsets = new ArrayList<List<I>>();
	}
	
	/**
	 * A Priori algorithm to compute the k-itemsets.
	 * @param minSupport The minimum support to keep an itemset.
	 * @return All the computed k-itemsets.
	 */
	public abstract List<List<I>> aPriori(double minSupport);/* {
		this.init1Itemset(minSupport);
		boolean noMoreItemsets = false;
		for(int i=1; !noMoreItemsets; i++) {
			List<AbstractItemset<T>> newItemsets = this.calcK1Itemset(itemsets.get(i-1), minSupport);
			if(newItemsets.size() == 0) {
				noMoreItemsets = true;
			} else {
				this.itemsets.add(newItemsets);
			}
		}
		return this.itemsets;
	}*/
	
	/**
	 * Computes the 1-itemsets from the transactions.
	 */
	protected abstract void init1Itemset(double minSupport);/* {
		Set<T> items = new HashSet<T>();
		for(AbstractTransaction<T> transaction: this.transactions) {
			for(T item: transaction.getItems()) {
				items.add(item);
			}
		}

		List<AbstractItemset<T>> itemsets = new ArrayList<AbstractItemset<T>>();
		for(T item: items) {
			AbstractItemset<T> itemset = new AbstractItemset<T>();
			itemset.add(item);
			if(itemset.calcSupport(this.transactions) >= minSupport) {
				itemsets.add(itemset);
			}
		}
		
		this.itemsets.add(itemsets);
	}*/
	
	/**
	 * Computes the k+1-itemsets from the k-itemsets.
	 * @param itemsets The k-itemsets.
	 * @param minSupport The minimum support to keep a k+1-itemset.
	 * @return The k+1-itemsets.
	 */
	 /**
	  * decomposer en : 
	  * 1)generate candidates of size k+1 from k itemsets
	  * 2)Check that all subsets of each candidate are frequent. If not, delete candidate
	  * 3)Go through database and count support :
	  * 	for each data row
	  * 		if candidate in data row
	  * 			increment support
	  * 4)Keep only itemsets with minimum support
	  */
	protected abstract List<I> calcK1Itemset(List<I> itemsets, double minSupport);/* {
		List<AbstractItemset<T>> itemsetsK1 = new ArrayList<AbstractItemset<T>>();
		for(int i=0; i<itemsets.size(); i++) {
			for(int j=i+1; j<itemsets.size(); j++) {
				List<AbstractItemset<T>> someItemsetsK1 = itemsets.get(i).calcItemsetsK1(itemsets.get(j));
				for(AbstractItemset<T> itemsetK1: someItemsetsK1) {
					if(allSubItemsetsFrequent(itemsets, itemsetK1)) {
						if(itemsetK1.calcSupport(this.transactions) >= minSupport) {
							itemsetsK1.add(itemsetK1);
						}
					}
				}
			}
		}
		return itemsetsK1;
	}*/
	
}
