package ra;

import java.util.ArrayList;
import java.util.List;

public class MaxAPriori extends APriori {

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
		System.out.println("t1 : " + t1);
		System.out.println("t2 : " + t2);
		System.out.println("t3 : " + t3);
		System.out.println("t4 : " + t4);
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
