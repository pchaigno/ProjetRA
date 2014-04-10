package tests;


import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import ra.algo.APriori;
import ra.algo.Itemset;
import ra.algo.MaxAPriori;
import ra.algo.Rule;
import ra.data.Database;

public class TestRule extends TestCase {

	public void testDeriveRules() {
		ArrayList<Integer> antecedent = new ArrayList<Integer>() {{
			add(1); add(2); add(3);
		}};

		ArrayList<Integer> consequent = new ArrayList<Integer>() {{
			add(4);
		}};

		Rule r = new Rule(antecedent, consequent);
		List<Rule> derivedRules = r.deriveRules();

		// 2, 3 -> 4, 1
		Assert.assertEquals(new Integer(2), derivedRules.get(0).getAntecedent().get(0));
		Assert.assertEquals(new Integer(3), derivedRules.get(0).getAntecedent().get(1));
		Assert.assertEquals(new Integer(4), derivedRules.get(0).getConsequent().get(0));
		Assert.assertEquals(new Integer(1), derivedRules.get(0).getConsequent().get(1));
		
		// 1, 3 -> 4, 2
		Assert.assertEquals(new Integer(1), derivedRules.get(1).getAntecedent().get(0));
		Assert.assertEquals(new Integer(3), derivedRules.get(1).getAntecedent().get(1));
		Assert.assertEquals(new Integer(4), derivedRules.get(1).getConsequent().get(0));
		Assert.assertEquals(new Integer(2), derivedRules.get(1).getConsequent().get(1));
		
		// 1,2 -> 4, 3
		Assert.assertEquals(new Integer(1), derivedRules.get(2).getAntecedent().get(0));
		Assert.assertEquals(new Integer(2), derivedRules.get(2).getAntecedent().get(1));
		Assert.assertEquals(new Integer(4), derivedRules.get(2).getConsequent().get(0));
		Assert.assertEquals(new Integer(3), derivedRules.get(2).getConsequent().get(1));


	}

}
