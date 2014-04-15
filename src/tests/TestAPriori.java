package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.Rule;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
import ra.data.Item;
import ra.data.Itemset;
import ra.data.MemoryDatabase;

public class TestAPriori extends TestCase {
	public static final int nbCores = Runtime.getRuntime().availableProcessors();
	
	/**
	 * Tests the A Priori algorithm.
	 */
	public static void testSimpleMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
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
	 * Tests the A Priori algorithm.
	 */
	public static void testSimpleMemoryWords() {
		File file = new File("res/unit_tests/simple_words.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
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
	 * Tests the A Priori algorithm with the concurrent database.
	 */
	public static void testSimpleConcurrentMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new ConcurrentMemoryDatabase(file, 4);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
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
	 * Tests the A Priori algorithm.
	 */
	public static void testTicketsConcurrentMemory() {
		File file = new File("res/real_tests/tickets_caisse.trans");
		Database database = new ConcurrentMemoryDatabase(file, Runtime.getRuntime().availableProcessors());
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.65);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);
		List<Rule> rules = apriori.generateRules(0.9);
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println(i+1+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}
		Assert.assertEquals(3, itemsets.size());
		Assert.assertEquals(12, itemsets.get(0).size());
		Assert.assertEquals(17, itemsets.get(1).size());
		Assert.assertEquals(6, itemsets.get(2).size());
		Assert.assertTrue(rules.size() >= 10);
	}
	
	/**
	 * Tests the rules generation
	 */
	public static void testRulesGeneration() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		apriori.aPriori(absoluteSupport);
		
		double minConfidence = 0.8;
		List<Rule> generatedRules = apriori.generateRules(minConfidence);
		Assert.assertFalse(generatedRules.isEmpty());
		for(Rule rule : generatedRules) {
			System.out.println(rule);
		}
		// 1 -> 3
		Assert.assertEquals(new Item(1), generatedRules.get(0).getAntecedent().get(0));
		Assert.assertEquals(new Item(3), generatedRules.get(0).getConsequent().get(0));
		// 5 -> 2
		Assert.assertEquals(new Item(5), generatedRules.get(1).getAntecedent().get(0));
		Assert.assertEquals(new Item(2), generatedRules.get(1).getConsequent().get(0));
		// 2 -> 5
		Assert.assertEquals(new Item(2), generatedRules.get(2).getAntecedent().get(0));
		Assert.assertEquals(new Item(5), generatedRules.get(2).getConsequent().get(0));
		// 3 5 -> 2
		Assert.assertEquals(new Item(3), generatedRules.get(3).getAntecedent().get(0));
		Assert.assertEquals(new Item(5), generatedRules.get(3).getAntecedent().get(1));
		Assert.assertEquals(new Item(2), generatedRules.get(3).getConsequent().get(0));
		// 2 3 -> 5
		Assert.assertEquals(new Item(2), generatedRules.get(4).getAntecedent().get(0));
		Assert.assertEquals(new Item(3), generatedRules.get(4).getAntecedent().get(1));
		Assert.assertEquals(new Item(5), generatedRules.get(4).getConsequent().get(0));
	}
	
	/**
	 * Tests the rules generation
	 */
	public static void testRulesGenerationWords() {
		File file = new File("res/unit_tests/simple_words.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		apriori.aPriori(absoluteSupport);
		
		double minConfidence = 0.8;
		List<Rule> generatedRules = apriori.generateRules(minConfidence);
		Assert.assertFalse(generatedRules.isEmpty());
		for(Rule rule : generatedRules) {
			System.out.println(rule);
		}
		// info -> eii
		Assert.assertEquals("info", generatedRules.get(0).getAntecedent().get(0).toString());
		Assert.assertEquals("eii", generatedRules.get(0).getConsequent().get(0).toString());
		// mnt -> gma
		Assert.assertEquals("mnt", generatedRules.get(1).getAntecedent().get(0).toString());
		Assert.assertEquals("gma", generatedRules.get(1).getConsequent().get(0).toString());
		// gma -> mnt
		Assert.assertEquals("gma", generatedRules.get(2).getAntecedent().get(0).toString());
		Assert.assertEquals("mnt", generatedRules.get(2).getConsequent().get(0).toString());
		// eii mnt -> gma
		Assert.assertEquals("eii", generatedRules.get(3).getAntecedent().get(0).toString());
		Assert.assertEquals("mnt", generatedRules.get(3).getAntecedent().get(1).toString());
		Assert.assertEquals("gma", generatedRules.get(3).getConsequent().get(0).toString());
		// eii gma -> mnt
		Assert.assertEquals("eii", generatedRules.get(4).getAntecedent().get(0).toString());
		Assert.assertEquals("gma", generatedRules.get(4).getAntecedent().get(1).toString());
		Assert.assertEquals("mnt", generatedRules.get(4).getConsequent().get(0).toString());
	}
	
	/**
	 * Tests the APriori algorithm on a real file.
	 * The support is of 20%.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles20Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		int support = database.calcAbsoluteSupport(0.2);
		List<List<Itemset>> itemsets = apriori.aPriori(support);
		Assert.assertEquals(1, itemsets.size());
		Assert.assertEquals(8, itemsets.get(0).size());
		int[] supports =  new int[] {1061, 1220, 1639, 1259, 1359, 1000, 1249, 1023};
		for(int i=0; i<itemsets.get(0).size(); i++) {
			Assert.assertEquals(supports[i], itemsets.get(0).get(i).getSupport());
		}
		
		List<Rule> rules = apriori.generateRules(0);
		Assert.assertEquals(0, rules.size());
	}
	
	/**
	 * Tests the APriori algorithm on a real file.
	 * The support is of 200 (approximately 4%).
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles4Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database);
		List<List<Itemset>> itemsets = apriori.aPriori(200);
		Assert.assertEquals(4, itemsets.size());
		int[] nbkItemsets = new int[] {135, 360, 106, 7};
		int nbItemsets = 0;
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
			nbItemsets += nbkItemsets[i];
		}
		Assert.assertEquals(608, nbItemsets);
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(7, rules.size());
	}
	
	/**
	 * Tests the APriori algorithm on a real file using the concurrent memory database.
	 * The support is of 20%.
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles20ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new APriori(database);
		int support = database.calcAbsoluteSupport(0.2);
		List<List<Itemset>> itemsets = apriori.aPriori(support);
		Assert.assertEquals(1, itemsets.size());
		Assert.assertEquals(8, itemsets.get(0).size());
		int[] supports =  new int[] {1061, 1220, 1639, 1259, 1359, 1000, 1249, 1023};
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(supports[i], itemsets.get(0).get(i).getSupport());
		}
		
		List<Rule> rules = apriori.generateRules(0.0);
		Assert.assertEquals(0, rules.size());
	}
	
	/**
	 * Tests the APriori algorithm on a real file using the concurrent memory database.
	 * The support is of 200 (approximately 4%).
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void testArticles4ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new APriori(database);
		List<List<Itemset>> itemsets = apriori.aPriori(200);
		Assert.assertEquals(4, itemsets.size());
		int[] nbkItemsets = new int[] {135, 360, 106, 7};
		int nbItemsets = 0;
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
			nbItemsets += nbkItemsets[i];
		}
		Assert.assertEquals(608, nbItemsets);
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(7, rules.size());
	}
}