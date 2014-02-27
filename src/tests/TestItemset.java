package tests;

import java.util.ArrayList;
import java.util.List;

import ra.Itemset;
import ra.Transaction;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestItemset extends TestCase {
	private Itemset itemset1A;
	private Itemset itemset3A;
	private Itemset itemset3B;
	
	@Override
	public void setUp() {
		this.itemset1A = new Itemset();
		this.itemset1A.add(1);
		this.itemset3A = new Itemset();
		this.itemset3A.add(1);
		this.itemset3A.add(3);
		this.itemset3A.add(4);
		this.itemset3B = new Itemset();
		this.itemset3B.add(1);
		this.itemset3B.add(3);
		this.itemset3B.add(5);
	}
	
	@SuppressWarnings("serial")
	public void testCalcSupport() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		List<Integer> t1 = new ArrayList<Integer>() {{
			add(1); add(3); add(4);
		}};
		transactions.add(new Transaction(t1));
		List<Integer> t2 = new ArrayList<Integer>() {{
			add(2); add(3); add(5);
		}};
		transactions.add(new Transaction(t2));
		List<Integer> t3 = new ArrayList<Integer>() {{
			add(1); add(2); add(3); add(5);
		}};
		transactions.add(new Transaction(t3));
		List<Integer> t4 = new ArrayList<Integer>() {{
			add(2); add(5);
		}};
		transactions.add(new Transaction(t4));

		Assert.assertEquals(0.5, this.itemset1A.calcSupport(transactions));
		Assert.assertEquals(0.25, this.itemset3A.calcSupport(transactions));
		Assert.assertEquals(0.25, this.itemset3B.calcSupport(transactions));
	}
	
	public void testCommonBase() {
		Assert.assertTrue(this.itemset3A.commonBase(this.itemset3B));
		
		Itemset test = new Itemset();
		test.add(1);
		test.add(2);
		test.add(5);
		Assert.assertFalse(this.itemset3B.commonBase(test));
		
		try {
			Assert.assertTrue(this.itemset3A.commonBase(this.itemset1A));
		} catch(IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}
	
	public void testCalcItemsetK1() {
		List<Itemset> itemsetsK1 = this.itemset3A.calcItemsetsK1(itemset3B);
		Assert.assertEquals(1, itemsetsK1.size());
		Assert.assertEquals(4, itemsetsK1.get(0).size());
	}
	
	public void testCalcSubItemsets() {
		Assert.assertEquals(1, this.itemset1A.calcSubItemsets().size());
		Assert.assertEquals(3, this.itemset3A.calcSubItemsets().size());
		Assert.assertEquals(3, this.itemset3B.calcSubItemsets().size());
	}
	
	public void testEquals() {
		Itemset test = new Itemset();
		test.add(1);
		Assert.assertEquals(this.itemset1A, test);
		test.add(4);
		Assert.assertFalse(this.itemset1A.equals(test));
	}
}
