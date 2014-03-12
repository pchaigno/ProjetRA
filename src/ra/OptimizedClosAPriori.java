package ra;

import java.util.ArrayList;
import java.util.List;

public class OptimizedClosAPriori extends APriori {

	public OptimizedClosAPriori(List<Transaction> transactions) {
		super(transactions);
	}

	@Override
	public List<List<Itemset>> aPriori(double minSupport) {
		// Mise a jour de l'attribut itemsets
		super.aPriori(minSupport);
		
		ArrayList<ArrayList<Double>> supports = new ArrayList<>();
		// Calcul des supports
		for(int i = 0 ; i < this.itemsets.size() ; i++) {
			supports.add(new ArrayList<Double>());
			for(int j = 0 ; j < this.itemsets.get(i).size() ; j++)
				supports.get(i).add(this.itemsets.get(i).get(j).calcSupport(this.transactions));
		}
		
		// Parcours des itemsets par rang
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
