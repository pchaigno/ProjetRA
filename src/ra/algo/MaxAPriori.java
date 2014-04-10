package ra.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ra.data.Database;
import ra.data.Transaction;

public class MaxAPriori extends APriori {

	/**
	 * Constructor
	 * @param database The database containing the transactions.
	 */
	public MaxAPriori(Database database) {
		super(database);
	}

	@Override
	public List<List<Itemset>> aPriori(double minSupport) {
		this.itemsets = super.aPriori(minSupport);
		for(int i = 0 ; i < this.itemsets.size()-1 ; i++) {
			List<Itemset> kItemsetList = new ArrayList<Itemset>(this.itemsets.get(i));
			for(Itemset itemsetInf : kItemsetList) {
				for(int k = 0 ; k < this.itemsets.get(i+1).size() ; k++) {
					Itemset itemsetSup = this.itemsets.get(i+1).get(k);
					if(itemsetInf.isIncludedIn(itemsetSup)) {
						this.itemsets.get(i).remove(itemsetInf);
					}
				}
			}
		}
		return this.itemsets;
	}


	/**
	 * Removes itemsets from the list of K-itemsets if there are not maximal.
	 * There aren't maximal if they can produce frequent K+1-itemset.
	 */
	@Override
	protected List<Itemset> calcK1Itemset(List<Itemset> kItemsets, double minSupport) {
		List<Itemset> itemsetsK1 = new ArrayList<Itemset>();
		// K-itemsets to remove from the list.
		Set<Itemset> kItemsetsNonMaximal = new HashSet<Itemset>();
		for(int i=0; i<kItemsets.size(); i++) {
			for(int j=i+1; j<kItemsets.size(); j++) {
				// True if the k-itemsets above are maximal.
				boolean maximal = true;
				List<Itemset> someItemsetsK1 = kItemsets.get(i).calcItemsetsK1(kItemsets.get(j));
				for(Itemset itemsetK1: someItemsetsK1) {
					if(allSubItemsetsFrequent(kItemsets, itemsetK1)) {
						if(itemsetK1.calcSupport(this.transactions) >= minSupport) {
							itemsetsK1.add(itemsetK1);
							maximal = false;
						}
					}
				}
				if(!maximal) {
					kItemsetsNonMaximal.add(kItemsets.get(i));
					kItemsetsNonMaximal.add(kItemsets.get(j));
				}
			}
		}
		
		// Removes the k-itemsets that are not maximal.
		kItemsets.removeAll(kItemsetsNonMaximal);
		
		return itemsetsK1;
	}
}