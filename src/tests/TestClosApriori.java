package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.ClosAPriori;
import ra.algo.Rule;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
import ra.data.Itemset;
import ra.data.MemoryDatabase;

public class TestClosApriori extends TestCase {

	public static void testSimpleMemory() {
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
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(5, rules.size());
	}

	public static void testSimpleMemoryWords() {
		File file = new File("res/unit_tests/simple_words.trans");
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
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(5, rules.size());
	}

	public static void testSimpleConcurrentMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
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
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(5, rules.size());
	}
	
	/**
	 * Tests the Closed APriori algorithm on a real file.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles4Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new ClosAPriori(database);
		List<List<Itemset>> itemsets = apriori.aPriori(200);
		Assert.assertEquals(4, itemsets.size());
		int[] nbkItemsets = new int[] {135, 360, 106, 7}; // total size of 608.
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(7, rules.size());
	}
	
	/**
	 * Tests the Closed APriori algorithm on a real file using the concurrent memory database.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles4ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new ClosAPriori(database);
		List<List<Itemset>> itemsets = apriori.aPriori(200);
		Assert.assertEquals(4, itemsets.size());
		int[] nbkItemsets = new int[] {135, 360, 106, 7}; // total size of 608.
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(7, rules.size());
	}
	
	/**
	 * Tests the closed A Priori algorithm on tickets.
	 */
	public static void testTicketsConcurrentMemory() {
		File file = new File("res/real_tests/tickets_caisse.trans");
		Database database = new ConcurrentMemoryDatabase(file, Runtime.getRuntime().availableProcessors());
		APriori apriori = new ClosAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.65);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(1, itemsets.get(0).size());
		Assert.assertEquals(11, itemsets.get(1).size());
		Assert.assertEquals(6, itemsets.get(2).size());

		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(17, rules.size());
	}
}
