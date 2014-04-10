package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Database;

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
		// Computes the itemsets.
		super.aPriori(minSupport);
		
		// Iterates on itemsets by ranks:
		for(int i = 1 ; i < this.itemsets.size() ; i++) {
			for(Itemset itemset: this.itemsets.get(i)) {
				ArrayList<Itemset> removeList = new ArrayList<>();
				for(Itemset filsItemset: this.itemsets.get(i-1)) {
					// If the k-1-itemset is included in the k-itemset and his support is equal to his father's support:
					if(filsItemset.getSupport()==itemset.getSupport() && filsItemset.isIncludedIn(itemset)) {
						removeList.add(filsItemset);
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