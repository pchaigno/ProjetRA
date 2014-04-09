package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Transaction;

public class ClosAPriori extends APriori {

	/**
	 * Constructor
	 * @param transactions The transactions.
	 */
	public ClosAPriori(List<Transaction> transactions) {
		super(transactions);
	}
	
	/**
	 * Algoritm to retrieve the closed itemsets.
	 * @param minSupport Minimum support.
	 * @return List of itemsets ordered by ranks.
	 */
	@Override
	public List<List<Itemset>> aPriori(double minSupport) {
		// Updates the itemsets.
		super.aPriori(minSupport);
		
		// Iterates on itemsets by ranks:
		for(int i = 1 ; i < this.itemsets.size() ; i++) {
			for(Itemset itemset: this.itemsets.get(i)) {
				double support = itemset.calcSupport(this.transactions);
				ArrayList<Itemset> removeList = new ArrayList<>();
				for(Itemset filsItemset: this.itemsets.get(i-1))
					// If the k-1-itemset is included in the k-itemset and his support is equal to his father's support:
					if(filsItemset.isIncludedIn(itemset) && filsItemset.calcSupport(this.transactions) == support)
						removeList.add(filsItemset);	
				
				for(Itemset toRemove: removeList)
					this.itemsets.get(i-1).remove(toRemove);
			}
		}

		return this.itemsets;
	}
	
	/**
	 * Optimized version of the algorithm to retrieve the closed itemsets.
	 * Values computed are stored to avoid repeated calculations.
	 * @param minSupport Minimum support.
	 * @return List fo itemsets ordered by ranks.
	 */
	public List<List<Itemset>> OptimizedAPriori(double minSupport) {
		// Updates the itemsets.
		super.aPriori(minSupport);
		
		ArrayList<ArrayList<Double>> supports = new ArrayList<>();
		// Compute supports:
		for(int i = 0 ; i < this.itemsets.size() ; i++) {
			supports.add(new ArrayList<Double>());
			for(int j = 0 ; j < this.itemsets.get(i).size() ; j++)
				supports.get(i).add(this.itemsets.get(i).get(j).calcSupport(this.transactions));
		}
		
		// Iterates on itemsets by ranks:
		for(int i = 1 ; i < this.itemsets.size() ; i++) {
			for(int j = 0 ; j < this.itemsets.get(i).size() ; j++) {
				Itemset itemset = this.itemsets.get(i).get(j);
				ArrayList<Itemset> removeList = new ArrayList<>();
				for(int k = 0 ; k < this.itemsets.get(i-1).size() ; k++) {
					if(this.itemsets.get(i-1).get(k).isIncludedIn(itemset) && supports.get(i).get(j) == supports.get(i-1).get(k))
						removeList.add(this.itemsets.get(i-1).get(k));
				}
				for(Itemset toRemove: removeList)
					this.itemsets.get(i-1).remove(toRemove);
			}
		}
		return this.itemsets;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction() {{
			addItem(1); addItem(3); addItem(4);
		}});
		transactions.add(new Transaction() {{
			addItem(2); addItem(3); addItem(5);
		}});
		transactions.add(new Transaction() {{
			addItem(1); addItem(2); addItem(3); addItem(5);
		}});
		transactions.add(new Transaction() {{
			addItem(2); addItem(5);
		}});
		/*
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(1); add(3); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(2); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(4); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(2); add(3); add(4); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(2); add(3); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(2); add(3); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(1); add(2); add(3); add(4); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(1); add(3); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(1); add(2); add(3); }}));
			transactions.add(new Transaction(new ArrayList<Integer>() {{ add(1); add(2); add(3); }}));
		 */
		
		APriori apriori = new ClosAPriori(transactions);
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