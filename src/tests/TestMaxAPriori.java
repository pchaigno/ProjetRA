package tests;

import java.util.List;

import junit.framework.Assert;

import ra.APriori;
import ra.Itemset;
import ra.MaxAPriori;
import ra.Transaction;

public class TestMaxAPriori extends TestAPriori {

	@Override
	public void testAPriori() {
		List<Transaction> transactions = getTransactions();
		APriori apriori = new MaxAPriori(transactions);
		List<List<Itemset>> itemsets = apriori.aPriori(0.5);
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println(i+1+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(1, itemsets.get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(0).size());
		Assert.assertEquals(1, itemsets.get(2).size());
		Assert.assertEquals(3, itemsets.get(2).get(0).size());
	}
}
