package tests;

import java.util.List;

import ra.data.Item;
import ra.data.Itemset;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestItemset extends TestCase {
	private Itemset itemset1A;
	private Itemset itemset3A;
	private Itemset itemset3B;
	
	@Override
	public void setUp() {
		this.itemset1A = new Itemset() {{
			add(new Item(1));
		}};
		this.itemset3A = new Itemset() {{
			add(new Item(1));
			add(new Item(3));
			add(new Item(4));
		}};
		this.itemset3B = new Itemset() {{
			add(new Item(1));
			add(new Item(3));
			add(new Item(5));
		}};
	}
	
	/**
	 * Tests the method that checks the common base.
	 */
	public void testCommonBase() {
		Assert.assertTrue(this.itemset3A.commonBase(this.itemset3B));
		
		Itemset test = new Itemset();
		test.add(new Item(1));
		test.add(new Item(2));
		test.add(new Item(5));
		Assert.assertFalse(this.itemset3B.commonBase(test));
		
		// The common base can only be computed for itemsets that are the same size:
		try {
			Assert.assertTrue(this.itemset3A.commonBase(this.itemset1A));
		} catch(IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}
	
	/**
	 * Tests the method that computes the k+1-itemsets.
	 */
	public void testCalcItemsetK1() {
		List<Itemset> itemsetsK1 = this.itemset3A.calcItemsetsK1(itemset3B);
		Assert.assertEquals(1, itemsetsK1.size());
		Assert.assertEquals(4, itemsetsK1.get(0).size());
	}
	
	/**
	 * Tests the method that computes the sub-itemsets.
	 */
	public void testCalcSubItemsets() {
		Assert.assertEquals(1, this.itemset1A.calcSubItemsets().size());
		Assert.assertEquals(3, this.itemset3A.calcSubItemsets().size());
		Assert.assertEquals(3, this.itemset3B.calcSubItemsets().size());
	}
	
	/**
	 * Tests the equals method.
	 */
	public void testEquals() {
		Itemset test = new Itemset();
		test.add(new Item(1));
		Assert.assertEquals(this.itemset1A, test);
		test.add(new Item(4));
		Assert.assertFalse(this.itemset1A.equals(test));
	}
}