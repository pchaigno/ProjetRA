package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
import ra.data.Item;
import ra.data.Itemset;
import ra.data.MemoryDatabase;
import ra.data.Rule;

public class TestAPriori extends TestCase {
	public static final int nbCores = Runtime.getRuntime().availableProcessors();
	
	public static void testSimpleMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database, false);
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
	
	public static void testSimpleMemoryWords() {
		File file = new File("res/unit_tests/simple_words.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database, false);
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
	
	public static void testSimpleConcurrentMemory() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new ConcurrentMemoryDatabase(file, 4);
		APriori apriori = new APriori(database, false);
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
	
	public static void testTicketsConcurrentMemory() {
		File file = new File("res/real_tests/tickets_caisse.trans");
		Database database = new ConcurrentMemoryDatabase(file, Runtime.getRuntime().availableProcessors());
		APriori apriori = new APriori(database, false);
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
	
	public static void testRulesGeneration() {
		File file = new File("res/unit_tests/simple.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database, false);
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
	
	public static void testRulesGenerationWords() {
		File file = new File("res/unit_tests/simple_words.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database, false);
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
	
	public static void testArticles20Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database, true);
		int support = database.calcAbsoluteSupport(0.2);
		List<List<Itemset>> itemsets = apriori.aPriori(support);
		Assert.assertEquals(1, itemsets.size());
		Assert.assertEquals(8, itemsets.get(0).size());
		@SuppressWarnings("serial")
		List<Integer> supports = new ArrayList<Integer>() {{
			add(1061); add(1220); add(1639); add(1259); add(1359); add(1000); add(1249); add(1023);
		}};
		// All supports need to be found once:
		for(int i=0; i<itemsets.get(0).size(); i++) {
			boolean supportFound = false;
			for(int j=0; j<supports.size(); j++) {
				if(itemsets.get(0).get(i).getSupport() == supports.get(j)) {
					supportFound = true;
					supports.remove(j);
					j--;
				}
			}
			Assert.assertTrue(supportFound);
		}
		
		List<Rule> rules = apriori.generateRules(0);
		Assert.assertEquals(0, rules.size());
	}
	
	public static void testArticles4Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new APriori(database, false);
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
	
	public static void testArticles20ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new APriori(database, true);
		int support = database.calcAbsoluteSupport(0.2);
		List<List<Itemset>> itemsets = apriori.aPriori(support);
		Assert.assertEquals(1, itemsets.size());
		Assert.assertEquals(8, itemsets.get(0).size());
		@SuppressWarnings("serial")
		List<Integer> supports = new ArrayList<Integer>() {{
			add(1061); add(1220); add(1639); add(1259); add(1359); add(1000); add(1249); add(1023);
		}};
		// All supports need to be found once:
		for(int i=0; i<itemsets.get(0).size(); i++) {
			boolean supportFound = false;
			for(int j=0; j<supports.size(); j++) {
				if(itemsets.get(0).get(i).getSupport() == supports.get(j)) {
					supportFound = true;
					supports.remove(j);
					j--;
				}
			}
			Assert.assertTrue(supportFound);
		}
		
		List<Rule> rules = apriori.generateRules(0.0);
		Assert.assertEquals(0, rules.size());
	}
	
	public static void testArticles4ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new APriori(database, false);
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