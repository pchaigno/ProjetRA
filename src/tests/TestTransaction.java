package tests;

import ra.algo.Itemset;
import ra.data.Transaction;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestTransaction extends TestCase {
	private Transaction transaction1;
	private Transaction transaction2;
	
	@Override
	public void setUp() {
		this.transaction1 = new Transaction();
		this.transaction2.addItem(1);
		this.transaction2.addItem(3);
		this.transaction2.addItem(4);
		this.transaction2 = new Transaction();
		this.transaction2.addItem(2);
		this.transaction2.addItem(3);
		this.transaction2.addItem(5);
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