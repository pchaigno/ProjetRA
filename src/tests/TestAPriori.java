package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.Itemset;
import ra.algo.Rule;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
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
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);/*
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println(i+1+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}*/
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
	 * Tests the A Priori algorithm with the concurrent database.
	 */
	public static void testSimpleConcurrentMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new ConcurrentMemoryDatabase(file, 4);
		APriori apriori = new APriori(database);
		int absoluteSupport = database.calcAbsoluteSupport(0.5);
		List<List<Itemset>> itemsets = apriori.aPriori(absoluteSupport);/*
		for(int i=0; i<itemsets.size(); i++) {
			System.out.println(i+1+"-itemsets:");
			for(Itemset itemset: itemsets.get(i)) {
				System.out.println(itemset);
			}
			System.out.println();
		}*/
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
	 * Tests the rules generation
	 */
	public static void testRulesGeneration() {
		File file = new File("res/unit_tests/simple.trans");
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
		
		double minConfidence = 0.8;
		List<Rule> generatedRules = apriori.generateRules(minConfidence);
		Assert.assertFalse(generatedRules.isEmpty());
		for(Rule rule : generatedRules) {
			System.out.println(rule);
		}
		// 1 -> 3
		Assert.assertEquals((Integer) 1, generatedRules.get(0).getAntecedent().get(0));
		Assert.assertEquals((Integer) 3, generatedRules.get(0).getConsequent().get(0));
		// 5 -> 2
		Assert.assertEquals((Integer) 5, generatedRules.get(1).getAntecedent().get(0));
		Assert.assertEquals((Integer) 2, generatedRules.get(1).getConsequent().get(0));
		// 2 -> 5
		Assert.assertEquals((Integer) 2, generatedRules.get(2).getAntecedent().get(0));
		Assert.assertEquals((Integer) 5, generatedRules.get(2).getConsequent().get(0));
		// 3 5 -> 2
		Assert.assertEquals((Integer) 3, generatedRules.get(3).getAntecedent().get(0));
		Assert.assertEquals((Integer) 5, generatedRules.get(3).getAntecedent().get(1));
		Assert.assertEquals((Integer) 2, generatedRules.get(3).getConsequent().get(0));
		// 2 3 -> 5
		Assert.assertEquals((Integer) 2, generatedRules.get(4).getAntecedent().get(0));
		Assert.assertEquals((Integer) 3, generatedRules.get(4).getAntecedent().get(1));
		Assert.assertEquals((Integer) 5, generatedRules.get(4).getConsequent().get(0));
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
		APriori ap = new APriori(database);
		int support = database.calcAbsoluteSupport(0.2);
		List<List<Itemset>> itemsets = ap.aPriori(support);
		Assert.assertEquals(1, itemsets.size());
		Assert.assertEquals(8, itemsets.get(0).size());
		int[] supports =  new int[] {1061, 1220, 1639, 1259, 1000, 1359, 1249, 1023};
		for(int i=0; i<itemsets.get(0).size(); i++) {
			Assert.assertEquals(supports[i], itemsets.get(0).get(i).getSupport());
		}
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
		APriori ap = new APriori(database);
		List<List<Itemset>> itemsets = ap.aPriori(200);
		int[] nbItemsets = new int[] {135, 360, 106, 7};
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbItemsets[i], itemsets.get(i).size());
		}
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
		APriori ap = new APriori(database);
		int support = database.calcAbsoluteSupport(0.2);
		List<List<Itemset>> itemsets = ap.aPriori(support);
		Assert.assertEquals(1, itemsets.size());
		Assert.assertEquals(8, itemsets.get(0).size());
		int[] supports =  new int[] {1061, 1220, 1639, 1259, 1000, 1359, 1249, 1023};
		for(int i=0; i<itemsets.get(0).size(); i++) {
			Assert.assertEquals(supports[i], itemsets.get(0).get(i).getSupport());
		}
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
		APriori ap = new APriori(database);
		List<List<Itemset>> itemsets = ap.aPriori(200);
		int[] nbItemsets = new int[] {135, 360, 106, 7};
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbItemsets[i], itemsets.get(i).size());
		}
	}
}