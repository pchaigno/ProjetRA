package tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.APriori;
import ra.Itemset;
import ra.Transaction;

public class TestAPriori extends TestCase {

	@SuppressWarnings("serial")
	protected List<Transaction> getTransactions() {
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
		return transactions;
	}
	
	protected APriori makeAPriori(List<Transaction> transactions) {
		return new APriori(transactions);
	}
	
	/**
	 * Tests the A Priori algorithm.
	 */
	public void testAPriori() {
		List<Transaction> transactions = getTransactions();
		APriori apriori = makeAPriori(transactions);
		List<List<Itemset>> itemsets = apriori.aPriori(0.5);
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