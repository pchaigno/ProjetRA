package tests;

import java.io.File;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.ClosAPriori;
import ra.algo.Itemset;
import ra.data.Database;
import ra.data.MemoryDatabase;

public class TestClosApriori extends TestCase {

	public void testClosAPrioriMemory() {
		File file = new File("res/unit_tests/transactions.txt");
		Database database = new MemoryDatabase(file);
		APriori apriori = new ClosAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
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
