package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Database;

public class MaxAPriori extends APriori {

	/**
	 * Constructor
	 * @param database The database containing the transactions.
	 */
	public MaxAPriori(Database database) {
		super(database);
		this.completeSupportCalc = false;
	}

	@Override
	public List<List<Itemset>> aPriori(int minSupport) {
		this.itemsets = super.aPriori(minSupport);
		for(int i=0; i<this.itemsets.size()-1; i++) {
			List<Itemset> kItemsetList = new ArrayList<Itemset>(this.itemsets.get(i));
			for(Itemset itemsetInf : kItemsetList) {
				for(int k=0; k<this.itemsets.get(i+1).size(); k++) {
					Itemset itemsetSup = this.itemsets.get(i+1).get(k);
					if(itemsetInf.isIncludedIn(itemsetSup)) {
						this.itemsets.get(i).remove(itemsetInf);
					}
				}
			}
		}
		return this.itemsets;
	}
}