package ra;

import java.util.List;

public class ClosAPriori extends APriori {

	public ClosAPriori(List<Transaction> transactions) {
		super(transactions);
	}
	
	@Override
	public List<List<Itemset>> aPriori(double minSupport) {
		// Mise a jour de l'attribut itemsets
		super.aPriori(minSupport);
		
		// Parcours des itemsets par rang
		for(int i = 1 ; i < this.itemsets.size() ; i++) {
			for(Itemset itemset: this.itemsets.get(i)) {
				double support = itemset.calcSupport(this.transactions);
				for(Itemset filsItemset: this.itemsets.get(i-1)){
					// Si l'itemset de rang i-1 est inclus dans l'interset de rang i et son support est egal au spport de son pere
					if(filsItemset.isIncludedIn(itemset) && filsItemset.calcSupport(this.transactions) == support)
						this.itemsets.get(i-1).remove(filsItemset);
				}
			}
		}

		return this.itemsets;
	}
}
