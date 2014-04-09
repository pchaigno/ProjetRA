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
		this.transaction1 = new Transaction() {{
			addItem(1);	addItem(3);	addItem(4);
		}};
		this.transaction2 = new Transaction() {{
			addItem(2);	addItem(3);	addItem(5);
		}};
	}

	/**
	 * Tests the contains method.
	 */
	public void testContains() {
		Itemset itemset3A = new Itemset() {{
			add(1); add(3);	add(4);
		}};
		Assert.assertTrue(this.transaction1.contains(itemset3A));
		Assert.assertFalse(this.transaction2.contains(itemset3A));
		
		Itemset itemset3B = new Itemset() {{
			add(1);	add(3);	add(5);
		}};
		Assert.assertFalse(this.transaction1.contains(itemset3B));
		Assert.assertFalse(this.transaction2.contains(itemset3B));
	}
}