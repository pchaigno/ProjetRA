package tests;

import ra.data.Item;
import ra.data.Itemset;
import ra.data.Transaction;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestTransaction extends TestCase {
	private Transaction transaction1;
	private Transaction transaction2;
	
	@Override
	public void setUp() {
		this.transaction1 = new Transaction() {{
			addItem(new Item(1)); addItem(new Item(3)); addItem(new Item(4));
		}};
		this.transaction2 = new Transaction() {{
			addItem(new Item(2)); addItem(new Item(3)); addItem(new Item(5));
		}};
	}

	/**
	 * Tests the contains method.
	 */
	public void testContains() {
		Itemset itemset3A = new Itemset() {{
			add(new Item(1)); add(new Item(3)); add(new Item(4));
		}};
		Assert.assertTrue(this.transaction1.contains(itemset3A));
		Assert.assertFalse(this.transaction2.contains(itemset3A));
		
		Itemset itemset3B = new Itemset() {{
			add(new Item(1)); add(new Item(3)); add(new Item(5));
		}};
		Assert.assertFalse(this.transaction1.contains(itemset3B));
		Assert.assertFalse(this.transaction2.contains(itemset3B));
	}
}