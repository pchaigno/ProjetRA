package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.ClosAPriori;
import ra.algo.Itemset;
import ra.algo.Rule;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
import ra.data.MemoryDatabase;

public class TestClosApriori extends TestCase {

	public static void testClosAPrioriMemory() {
		File file = new File("res/unit_tests/simple.trans");
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
	
	/**
	 * Tests the Closed APriori algorithm on a real file.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testClosAPrioriRealFile() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori ap = new ClosAPriori(database);
		List<List<Itemset>> itemsets = ap.aPriori(200);
		int totalSize = 0;
		for(int i=0; i<itemsets.size(); i++) {
			totalSize += itemsets.get(i).size();
		}
		Assert.assertEquals(608, totalSize);
	}
	
	/**
	 * Tests the Closed APriori algorithm on a real file using the concurrent memory database.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testClosAPrioriRealFileConcurrent() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori ap = new ClosAPriori(database);
		List<List<Itemset>> itemsets = ap.aPriori(200);
		int totalSize = 0;
		for(int i=0; i<itemsets.size(); i++) {
			totalSize += itemsets.get(i).size();
		}
		Assert.assertEquals(608, totalSize);
	}
	
	/**
	 * Tests the closed A Priori algorithm on tickets.
	 */
	public static void testClosAPrioriConcurrentMemoryTickets() {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, Runtime.getRuntime().availableProcessors());
		APriori apriori = new ClosAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.65);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		List<Rule> rules = apriori.generateRules(0.9);/*
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println(i+1+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}*/
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(1, itemsets.get(0).size());
		Assert.assertEquals(11, itemsets.get(1).size());
		Assert.assertEquals(6, itemsets.get(2).size());
	}
}
