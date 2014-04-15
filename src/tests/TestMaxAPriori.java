package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import ra.algo.APriori;
import ra.algo.MaxAPriori;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
import ra.data.Itemset;
import ra.data.MemoryDatabase;
import ra.data.Rule;

public class TestMaxAPriori extends TestCase {

	public static void testSimpleMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new MaxAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(1, itemsets.get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(0).size());
		Assert.assertEquals(1, itemsets.get(2).size());
		Assert.assertEquals(3, itemsets.get(2).get(0).size());
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(3, rules.size());
	}

	public static void testSimpleMemoryWords() {
		File file = new File("res/unit_tests/simple_words.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new MaxAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(1, itemsets.get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(0).size());
		Assert.assertEquals(1, itemsets.get(2).size());
		Assert.assertEquals(3, itemsets.get(2).get(0).size());
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(3, rules.size());
	}

	public static void testSimpleConcurrentMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new MaxAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(1, itemsets.get(1).size());
		Assert.assertEquals(2, itemsets.get(1).get(0).size());
		Assert.assertEquals(1, itemsets.get(2).size());
		Assert.assertEquals(3, itemsets.get(2).get(0).size());
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(3, rules.size());
	}
	
	/**
	 * Tests the Max APriori algorithm on a real file.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles4Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new MaxAPriori(database);
		List<List<Itemset>> itemsets = apriori.aPriori(200);
		int[] nbkItemsets = new int[]{63, 272, 91, 7}; // total size of 433.
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(6, rules.size());
	}
	
	/**
	 * Tests the Max APriori algorithm on a real file using the concurrent memory database.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles4ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori ap = new MaxAPriori(database);
		List<List<Itemset>> itemsets = ap.aPriori(200);
		Assert.assertEquals(4, itemsets.size());
		int[] nbkItemsets = new int[]{63, 272, 91, 7}; // total size of 433.
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}
		
		List<Rule> rules = ap.generateRules(0.9);
		Assert.assertEquals(6, rules.size());
	}
	
	/**
	 * Tests the max A Priori algorithm on tickets.
	 */
	public static void testTicketsConcurrentMemory() {
		File file = new File("res/real_tests/tickets_caisse.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new MaxAPriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.65);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		Assert.assertEquals(3, itemsets.size());
		int[] nbkItemsets = new int[]{0, 7, 6};
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}

		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(13, rules.size());
	}
}
