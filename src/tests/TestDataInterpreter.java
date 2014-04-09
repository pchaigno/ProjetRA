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
	/*
	@Test
	public static void testTickets() {
		File tickets = new File("res/fichiers_entree/tickets_de_caisse.txt");
		try {
			List<Transaction> transactions = DataInterpreter.interpret(tickets);
			Assert.assertEquals(1000, transactions.size());
		} catch (IllegalArgumentException | IOException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public static void testInitValueOf() {
		String attributes = "Je Tu Il";
		String[] splitted = DataInterpreter.initValueOf(attributes);
		
		Assert.assertEquals(3, splitted.length);
		Assert.assertEquals("Je", splitted[0]);
		Assert.assertEquals("Tu", splitted[1]);
		Assert.assertEquals("Il", splitted[2]);
	}
	
	@Test
	public static void testValueOf() {
		String attributes = "Je Tu Il";
		
		DataInterpreter.initValueOf(attributes);
		
		DataInterpreter.setValueOf("Je", "Lundi");
		DataInterpreter.setValueOf("Tu", "Mardi");
		DataInterpreter.setValueOf("Il", "Mercredi");
		
		DataInterpreter.setValueOf("Je", "Lundi");
		DataInterpreter.setValueOf("Tu", "Fevrier");
		DataInterpreter.setValueOf("Il", "Mars");
		
		Assert.assertEquals(1, DataInterpreter.getValueOf("Je", "Lundi"));
		Assert.assertEquals(2, DataInterpreter.getValueOf("Tu", "Mardi"));
		Assert.assertEquals(3, DataInterpreter.getValueOf("Il", "Mercredi"));
		Assert.assertEquals(4, DataInterpreter.getValueOf("Tu", "Fevrier"));
		Assert.assertEquals(5, DataInterpreter.getValueOf("Il", "Mars"));
		
	}*/
	
}
