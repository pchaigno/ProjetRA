package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Transaction;

public class ClosAPriori extends APriori {

	public ClosAPriori(List<Transaction> transactions) {
		super(transactions);
	}
	
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