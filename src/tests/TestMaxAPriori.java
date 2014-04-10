package tests;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import ra.algo.APriori;
import ra.algo.Itemset;
import ra.algo.MaxAPriori;
import ra.data.Database;
import ra.data.MemoryDatabase;

public class TestMaxAPriori extends TestAPriori {

	public void testMaxAPrioriMemory() {
		File file = new File("res/unit_tests/transactions.txt");
		Database database = new MemoryDatabase(file);
		APriori apriori = new MaxAPriori(database);
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
