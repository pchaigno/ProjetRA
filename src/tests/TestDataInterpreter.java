package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import ra.data.DataInterpreter;
import ra.data.Transaction;

public class TestDataInterpreter extends TestCase {

	private DataInterpreter di;
	private File tickets;
	
	@Before
	public void setUp() throws Exception {
		di = new DataInterpreter();
		File abs = new File("");
		String path = abs.getAbsolutePath();
		tickets = new File(path + "/res/tickets_de_caisse.txt");
	}

	@Test
	public void testInterpret() {
		try {
			List<Transaction> transactions =  di.interpret(tickets);
			Assert.assertEquals(1000, transactions.size());
			for(Transaction st : transactions)
				Assert.assertEquals(18, st.getData().size());
		} catch (IllegalArgumentException | IOException e) {
			Assert.fail();
			e.printStackTrace();
		}
	}

}
