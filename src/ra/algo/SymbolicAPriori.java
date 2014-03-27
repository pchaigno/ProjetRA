package ra.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ra.data.AbstractTransaction;
import ra.data.Symbol;
import ra.data.SymbolicTransaction;

public class SymbolicAPriori extends AbstractAPriori<SymbolicTransaction, SymbolicItemset> {

	public SymbolicAPriori(List<SymbolicTransaction> transactions) {
		super(transactions);
	}

	@Override
	public List<List<SymbolicItemset>> aPriori(double minSupport) {
		this.init1Itemset(minSupport);
		boolean noMoreItemsets = false;
		for(int i=1; !noMoreItemsets; i++) {
			List<SymbolicItemset> newItemsets = this.calcK1Itemset(itemsets.get(i-1), minSupport);
			if(newItemsets.size() == 0) {
				noMoreItemsets = true;
			} else {
				this.itemsets.add(newItemsets);
			}
		}
		return this.itemsets;
	}

	@Override
	protected void init1Itemset(double minSupport) {
		Set<Symbol> items = new HashSet<Symbol>();
		for(SymbolicTransaction transaction: this.transactions) {
			for(Symbol item: transaction.getItems()) {
				items.add(item);
			}
		}

		List<SymbolicItemset> itemsets = new ArrayList<SymbolicItemset>();
		for(Symbol item: items) {
			SymbolicItemset itemset = new SymbolicItemset();
			itemset.add(item);
			if(itemset.calcSupport(this.transactions) >= minSupport) {
				itemsets.add(itemset);
			}
		}
		
		this.itemsets.add(itemsets);
	}

	@Override
	protected List<SymbolicItemset> calcK1Itemset(
			List<SymbolicItemset> itemsets, double minSupport) {
		List<SymbolicItemset> itemsetsK1 = new ArrayList<SymbolicItemset>();
		for(int i=0; i<itemsets.size(); i++) {
			for(int j=i+1; j<itemsets.size(); j++) {
				List<SymbolicItemset> someItemsetsK1 = itemsets.get(i).calcItemsetsK1(itemsets.get(j));
				for(SymbolicItemset itemsetK1: someItemsetsK1) {
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
	protected static boolean allSubItemsetsFrequent(List<SymbolicItemset> itemsets, SymbolicItemset itemset) {
		List<SymbolicItemset> subItemsets = itemset.calcSubItemsets();
		for(SymbolicItemset subItemset: subItemsets) {
			if(!itemsets.contains(subItemset)) {
				System.out.println("search: "+subItemset);
				for(SymbolicItemset s: subItemsets) {
					System.out.println(s);
				}
				return false;
			}
		}
		return true;
	}

}
