package ra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaxAPriori extends APriori {

	/**
	 * Constructor
	 * @param transactions The transactions.
	 */
	public MaxAPriori(List<Transaction> transactions) {
		super(transactions);
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

	/**
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
		
		APriori apriori = new MaxAPriori(transactions);
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