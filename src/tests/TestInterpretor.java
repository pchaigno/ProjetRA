package tests;

import java.io.FileNotFoundException;

import ra.controller.Interpretor;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestInterpretor extends TestCase {

	private static final String[] args1 = {"res/real_tests/tickets_caisse.trans",
		  "-support",
		  "0.4"};
	
	private static final String[] args2 = {"res/real_tests/tickets_caisse.trans",
		  "-support",
		  "0.4",
		  "-confidence",
		  "1.0",
		  "-type",
		  "frequent",
		  "-output",
		  "result.txt"};
	
	public static void testInterpretorArgs1() {
		Interpretor inter;
		try {
			inter = new Interpretor(args1);
			Assert.assertEquals(0.4, inter.getSupport());
			Assert.assertEquals(Interpretor.DEFAULT_CONFIDENCE, inter.getConfidence());
			Assert.assertEquals(Interpretor.DEFAULT_TYPE, inter.getType());
		} catch (IllegalArgumentException | FileNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void testInterpretorArgs2() {
		Interpretor inter;
		try {
			inter = new Interpretor(args2);
			Assert.assertEquals(0.4, inter.getSupport());
			Assert.assertEquals(1.0, inter.getConfidence());
			Assert.assertEquals("frequent", inter.getType());
			Assert.assertNotSame(System.out, inter.getOutput());
		} catch (IllegalArgumentException | FileNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
	}
}
