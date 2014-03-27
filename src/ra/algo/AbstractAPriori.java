package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.AbstractTransaction;
import ra.data.Transaction;

public abstract class AbstractAPriori<T> {
	protected List<AbstractTransaction<T>> transactions;
	protected List<List<AbstractItemset<T>>> itemsets;
	
	/**
	 * Constructor
	 * @param transactions The transactions.
	 */
	public AbstractAPriori(List<AbstractTransaction<T>> transactions) {
		this.transactions = transactions;
		this.itemsets = new ArrayList<List<AbstractItemset<T>>>();
	}
	
	/**
	 * A Priori algorithm to compute the k-itemsets.
	 * @param minSupport The minimum support to keep an itemset.
	 * @return All the computed k-itemsets.
	 */
	public abstract List<List<AbstractItemset<T>>> aPriori(double minSupport);/* {
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
	protected abstract List<? extends AbstractItemset<T>> calcK1Itemset(List<AbstractItemset<T>> itemsets, double minSupport);/* {
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
	
	/**
	 * Checks that all k-itemsets of a k+1-itemset are frequent.
	 * @param itemsets The k-itemsets.
	 * @param itemset The k+1-itemsets.
	 * @return True if all k-itemsets of the k+1-itemsets are frequent.
	 */
	protected static <E> boolean allSubItemsetsFrequent(List<? extends AbstractItemset<E>> itemsets, AbstractItemset<E> itemset) {
		List<? extends AbstractItemset<E>> subItemsets = itemset.calcSubItemsets();
		for(AbstractItemset<E> subItemset: subItemsets) {
			if(!itemsets.contains(subItemset)) {
				System.out.println("search: "+subItemset);
				for(AbstractItemset<E> s: subItemsets) {
					System.out.println(s);
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * Tests
	 * @param args
	 */
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		List<Integer> t1 = new ArrayList<Integer>() {{
			add(1); add(3); add(4);
		}};
		transactions.add(new Transaction(t1));
		List<Integer> t2 = new ArrayList<Integer>() {{
			add(2); add(3); add(5);
		}};
		transactions.add(new Transaction(t2));
		List<Integer> t3 = new ArrayList<Integer>() {{
			add(1); add(2); add(3); add(5);
		}};
		transactions.add(new Transaction(t3));
		List<Integer> t4 = new ArrayList<Integer>() {{
			add(2); add(5);
		}};
		transactions.add(new Transaction(t4));
		
		APriori apriori = new APriori(transactions);
		List<List<Itemset>> itemsets = apriori.aPriori(0.5);
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println((i+1)+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}
	}
}
