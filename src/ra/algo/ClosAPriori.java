package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Database;
import ra.data.Transaction;

public class ClosAPriori extends APriori {

	/**
	 * Constructor
	 * @param database The database containing the transactions.
	 */
	public ClosAPriori(Database database) {
		super(database);
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
			for(int j = 0 ; j < this.itemsets.get(i).size() ; j++) {
				supports.get(i).add(this.itemsets.get(i).get(j).calcSupport(this.transactions));
			}
		}
		
		// Iterates on itemsets by ranks:
		for(int i = 1 ; i < this.itemsets.size() ; i++) {
			for(int j = 0 ; j < this.itemsets.get(i).size() ; j++) {
				Itemset itemset = this.itemsets.get(i).get(j);
				ArrayList<Itemset> removeList = new ArrayList<>();
				for(int k = 0 ; k < this.itemsets.get(i-1).size() ; k++) {
					if(this.itemsets.get(i-1).get(k).isIncludedIn(itemset) && supports.get(i).get(j) == supports.get(i-1).get(k)) {
						removeList.add(this.itemsets.get(i-1).get(k));
					}
				}
				for(Itemset toRemove: removeList) {
					this.itemsets.get(i-1).remove(toRemove);
				}
			}
		}
		return this.itemsets;
	}
}