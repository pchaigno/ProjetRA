package tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.Itemset;
import ra.data.Transaction;

public class TestAPriori extends TestCase {

	protected List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction() {{
			addItem(1); addItem(3); addItem(4);
		}});
		transactions.add(new Transaction() {{
			addItem(2); addItem(3); addItem(5);
		}});
		transactions.add(new Transaction() {{
			addItem(1); addItem(2); addItem(3); addItem(5);
		}});
		transactions.add(new Transaction() {{
			addItem(2); addItem(5);
		}});
		return transactions;
	}
	
	/**
	 * Tests the A Priori algorithm.
	 */
	public void testAPriori() {
		List<Transaction> transactions = getTransactions();
		APriori apriori = new APriori(transactions);
		List<List<Itemset>> itemsets = apriori.aPriori(0.5);
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println(i+1+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(4, itemsets.get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(0).size());
		Assert.assertEquals(2, itemsets.get(1).get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(2).size());
		Assert.assertEquals(2, itemsets.get(1).get(3).size());
		Assert.assertEquals(1, itemsets.get(2).size());
		Assert.assertEquals(3, itemsets.get(2).get(0).size());
	}
}