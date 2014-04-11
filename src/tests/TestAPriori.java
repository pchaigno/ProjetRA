package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.Itemset;
import ra.data.Database;
import ra.data.MemoryDatabase;

public class TestAPriori extends TestCase {
	
	/**
	 * Tests the A Priori algorithm.
	 */
	public static void testAPrioriMemory() {
		File file = new File("res/unit_tests/transactions.txt");
		System.out.println(file);
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
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
	
	/**
	 * Tests the APriori algorithm on a real file.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testAPrioriRealFile() throws IllegalArgumentException {
		File file = new File("res/fichiers_entree/5027_articles.txt");
		Database database = new MemoryDatabase(file);
		APriori ap = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.0413650465);
		List<List<Itemset>> itemsets = ap.aPriori(absoluteSupport);
		int totalSize = 0;
		for(int i=0; i<itemsets.size(); i++) {
			totalSize += itemsets.get(i).size();
		}
		Assert.assertEquals(5378, totalSize);
	}
}