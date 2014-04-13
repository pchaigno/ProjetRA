package tests;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import ra.data.DataInterpreter;

public class TestDataInterpreter extends TestCase {

	@Test
	public static void testSimpleTransactions() {
		File tickets = new File("res/unit_tests/simple.trans");
		DataInterpreter dataInterpreter = new DataInterpreter();
		try {
			dataInterpreter.interpret(tickets);
			Assert.assertEquals(4, dataInterpreter.getTransactions().size());
			Assert.assertEquals(5, dataInterpreter.getItems().size());
		} catch (IllegalArgumentException | IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public static void testSimpleWordTransactions() {
		File tickets = new File("res/unit_tests/simple_words.trans");
		DataInterpreter dataInterpreter = new DataInterpreter();
		try {
			dataInterpreter.interpret(tickets);
			Assert.assertEquals(4, dataInterpreter.getTransactions().size());
			Assert.assertEquals(5, dataInterpreter.getItems().size());
		} catch (IllegalArgumentException | IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public static void testInterpret() {
		File tickets = new File("res/real_tests/articles_grand_100_pourcent.trans");
		DataInterpreter dataInterpreter = new DataInterpreter();
		try {
			dataInterpreter.interpret(tickets);
			Assert.assertEquals(4903, dataInterpreter.getTransactions().size());
			Assert.assertEquals(189, dataInterpreter.getItems().size());
		} catch (IllegalArgumentException | IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
