package tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.data.Item;
import ra.data.Rule;

public class TestRule extends TestCase {
	
	@SuppressWarnings("serial")
	public void testDeriveRules() {
		ArrayList<Item> antecedent = new ArrayList<Item>() {{
			add(new Item(1)); add(new Item(2)); add(new Item(3));
		}};

		ArrayList<Item> consequent = new ArrayList<Item>() {{
			add(new Item(4));
		}};
		Rule r = new Rule(antecedent, consequent);
		List<Rule> derivedRules = r.deriveRules();

		// 2, 3 -> 4, 1
		Assert.assertEquals(new Item(2), derivedRules.get(0).getAntecedent().get(0));
		Assert.assertEquals(new Item(3), derivedRules.get(0).getAntecedent().get(1));
		Assert.assertEquals(new Item(4), derivedRules.get(0).getConsequent().get(0));
		Assert.assertEquals(new Item(1), derivedRules.get(0).getConsequent().get(1));
		
		// 1, 3 -> 4, 2
		Assert.assertEquals(new Item(1), derivedRules.get(1).getAntecedent().get(0));
		Assert.assertEquals(new Item(3), derivedRules.get(1).getAntecedent().get(1));
		Assert.assertEquals(new Item(4), derivedRules.get(1).getConsequent().get(0));
		Assert.assertEquals(new Item(2), derivedRules.get(1).getConsequent().get(1));
		
		// 1, 2 -> 4, 3
		Assert.assertEquals(new Item(1), derivedRules.get(2).getAntecedent().get(0));
		Assert.assertEquals(new Item(2), derivedRules.get(2).getAntecedent().get(1));
		Assert.assertEquals(new Item(4), derivedRules.get(2).getConsequent().get(0));
		Assert.assertEquals(new Item(3), derivedRules.get(2).getConsequent().get(1));
	}
}
