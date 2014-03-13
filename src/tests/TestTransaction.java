package tests;

import java.util.ArrayList;
import java.util.List;

import ra.algo.Itemset;
import ra.data.Transaction;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestTransaction extends TestCase {
	private Transaction transaction1;
	private Transaction transaction2;
	
	@Override
	@SuppressWarnings("serial")
	public void setUp() {
		List<Integer> t1 = new ArrayList<Integer>() {{
			add(1); add(3); add(4);
		}};
		this.transaction1 = new Transaction(t1);
		List<Integer> t2 = new ArrayList<Integer>() {{
			add(2); add(3); add(5);
		}};
		this.transaction2 = new Transaction(t2);
	}

	/**
	 * Tests the contains method.
	 */
	public void testContains() {
		Itemset itemset3A = new Itemset();
		itemset3A.add(1);
		itemset3A.add(3);
		itemset3A.add(4);
		Assert.assertTrue(this.transaction1.contains(itemset3A));
		Assert.assertFalse(this.transaction2.contains(itemset3A));
		
		Itemset itemset3B = new Itemset();
		itemset3B.add(1);
		itemset3B.add(3);
		itemset3B.add(5);
		Assert.assertFalse(this.transaction1.contains(itemset3B));
		Assert.assertFalse(this.transaction2.contains(itemset3B));
	}
}