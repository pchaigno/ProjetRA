package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Database;
import ra.data.Transaction;

public class OptimizedClosAPriori extends APriori {

	public OptimizedClosAPriori(Database database) {
		super(database);
	}

	@Override
	public List<List<Itemset>> aPriori(double minSupport) {
		// Update the itemsets.
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
}