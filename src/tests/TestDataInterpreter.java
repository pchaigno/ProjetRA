package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import ra.data.DataInterpreter;
import ra.data.Transaction;

public class TestDataInterpreter extends TestCase {

	@Test
	public static void testInterpret() {
		File tickets = new File("res/fichiers_entree/5027_articles.txt");
		try {
			List<Transaction> transactions = DataInterpreter.interpret(tickets);
			Assert.assertEquals(4835, transactions.size());
		} catch (IllegalArgumentException | IOException e) {
			Assert.fail(e.getMessage());
		}
	}
}
