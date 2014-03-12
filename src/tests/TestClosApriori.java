package tests;

import java.util.List;

import junit.framework.Assert;
import ra.APriori;
import ra.ClosAPriori;
import ra.Itemset;
import ra.Transaction;

public class TestClosApriori extends TestAPriori {

	@Override
	public void testAPriori() {
		List<Transaction> transactions = getTransactions();
		APriori apriori = new ClosAPriori(transactions);
		List<List<Itemset>> itemsets = apriori.aPriori(0.5);
		Assert.assertEquals(3, itemsets.size());
		
		Assert.assertEquals(1, itemsets.get(0).size());
		Assert.assertEquals(1, itemsets.get(0).get(0).size());
		
		Assert.assertEquals(2, itemsets.get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(0).size());
		Assert.assertEquals(2, itemsets.get(1).get(1).size());

		Assert.assertEquals(1, itemsets.get(2).size());
		Assert.assertEquals(3, itemsets.get(2).get(0).size());
	}
}
