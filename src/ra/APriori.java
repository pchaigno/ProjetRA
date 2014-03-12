package ra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class APriori {
	protected List<Transaction> transactions;
	protected List<List<Itemset>> itemsets;
	
	/**
	 * Constructor
	 * @param transactions The transactions.
	 */
	public APriori(List<Transaction> transactions) {
		this.transactions = transactions;
		this.itemsets = new ArrayList<List<Itemset>>();
	}
	
	/**
	 * A Priori algorithm to compute the k-itemsets.
	 * @param minSupport The minimum support to keep an itemset.
	 * @return All the computed k-itemsets.
	 */
	public List<List<Itemset>> aPriori(double minSupport) {
		this.init1Itemset(minSupport);
		boolean noMoreItemsets = false;
		for(int i=1; !noMoreItemsets; i++) {
			List<Itemset> newItemsets = this.calcK1Itemset(itemsets.get(i-1), minSupport);
			if(newItemsets.size() == 0) {
				noMoreItemsets = true;
			} else {
				this.itemsets.add(newItemsets);
			}
		}
		return this.itemsets;
	}
	
	/**
	 * Computes the 1-itemsets from the transactions.
	 */
	private void init1Itemset(double minSupport) {
		Set<Integer> items = new HashSet<Integer>();
		for(Transaction transaction: this.transactions) {
			for(int item: transaction.getItems()) {
				items.add(item);
			}
		}

		List<Itemset> itemsets = new ArrayList<Itemset>();
		for(int item: items) {
			Itemset itemset = new Itemset();
			itemset.add(item);
			if(itemset.calcSupport(this.transactions) >= minSupport) {
				itemsets.add(itemset);
			}
		}
		
		this.itemsets.add(itemsets);
	}
	
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
	protected List<Itemset> calcK1Itemset(List<Itemset> itemsets, double minSupport) {
		List<Itemset> itemsetsK1 = new ArrayList<Itemset>();
		for(int i=0; i<itemsets.size(); i++) {
			for(int j=i+1; j<itemsets.size(); j++) {
				List<Itemset> someItemsetsK1 = itemsets.get(i).calcItemsetsK1(itemsets.get(j));
				for(Itemset itemsetK1: someItemsetsK1) {
					if(allSubItemsetsFrequent(itemsets, itemsetK1)) {
						if(itemsetK1.calcSupport(this.transactions) >= minSupport) {
							itemsetsK1.add(itemsetK1);
						}
					}
				}
			}
		}
		return itemsetsK1;
	}
	
	/**
	 * Checks that all k-itemsets of a k+1-itemset are frequent.
	 * @param itemsets The k-itemsets.
	 * @param itemset The k+1-itemsets.
	 * @return True if all k-itemsets of the k+1-itemsets are frequent.
	 */
	protected static boolean allSubItemsetsFrequent(List<Itemset> itemsets, Itemset itemset) {
		List<Itemset> subItemsets = itemset.calcSubItemsets();
		for(Itemset subItemset: subItemsets) {
			if(!itemsets.contains(subItemset)) {
				System.out.println("search: "+subItemset);
				for(Itemset s: subItemsets) {
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
