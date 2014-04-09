package tests;

import ra.controller.Interpretor;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestInterpretor extends TestCase {

	private static final String[] args = {"res/fichiers_entree/tickets_de_caisse.txt",
		  "-support",
		  "0.4"};
	
	public void testInterpretor() {
		Interpretor inter = new Interpretor(args);
		Assert.assertEquals(0.4, inter.getSupport());
		Assert.assertEquals(Interpretor.DEFAULT_CONFIDENCE, inter.getConfidence());
		Assert.assertEquals(Interpretor.DEFAULT_TYPE, inter.getType());
	}

}
