package tests;

import java.io.File;
import java.util.ArrayList;
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
		APriori apriori = new MaxAPriori(database, false);
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
		APriori apriori = new MaxAPriori(database, false);
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
		APriori apriori = new MaxAPriori(database, false);
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
	
	public static void testArticles20Memory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new MemoryDatabase(file);
		APriori apriori = new MaxAPriori(database, true);
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
		APriori apriori = new MaxAPriori(database, false);
		List<List<Itemset>> itemsets = apriori.aPriori(200);
		int[] nbkItemsets = new int[]{63, 272, 91, 7}; // total size of 433.
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}
		
		List<Rule> rules = apriori.generateRules(0.9);
		Assert.assertEquals(6, rules.size());
	}
	
	public static void testArticles20ConcurrentMemory() throws IllegalArgumentException {
		File file = new File("res/real_tests/articles_grand_100_pourcent.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new MaxAPriori(database, true);
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
		APriori ap = new MaxAPriori(database, false);
		List<List<Itemset>> itemsets = ap.aPriori(200);
		Assert.assertEquals(4, itemsets.size());
		int[] nbkItemsets = new int[]{63, 272, 91, 7}; // total size of 433.
		for(int i=0; i<itemsets.size(); i++) {
			Assert.assertEquals(nbkItemsets[i], itemsets.get(i).size());
		}
		
		List<Rule> rules = ap.generateRules(0.9);
		Assert.assertEquals(6, rules.size());
	}
	
	public static void testTicketsConcurrentMemory() {
		File file = new File("res/real_tests/tickets_caisse.trans");
		Database database = new ConcurrentMemoryDatabase(file, TestAPriori.nbCores);
		APriori apriori = new MaxAPriori(database, false);
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
