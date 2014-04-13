package tests;

import ra.data.Item;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestItem extends TestCase {

	public static void testEquals() {
		Item itemA = new Item(1);
		Item itemB = new Item(1);
		Item itemC = new Item(3);

		Assert.assertEquals(itemA, itemB);
		Assert.assertEquals(itemA, itemA);
		Assert.assertFalse(itemA.equals(null));
		Assert.assertFalse(itemA.equals(itemC));
	}
}
