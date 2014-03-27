package ra.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import ra.data.DataInterpreter;
import ra.data.SymbolicTransaction;

/**
 * 
 * @author gwlemoul
 * 
 * Main program
 * usage : java Main source_file
 */
public class Main {

	public static void main(String[] args) {
		
		//Dies if case of wrong usage
		if(args.length != 1)
			System.out.println("Usage : java Main source_file");
		else {
			
			//DataInterpreter and File object setUp
			String source = args[0];
			DataInterpreter di = new DataInterpreter();
			File abs = new File("");
			String path = abs.getAbsolutePath();
			File srcFile = new File(path + "/" + source);
			List<SymbolicTransaction> transactions = null;
			
			try {
				transactions =  di.interpret(srcFile);
			} catch (IllegalArgumentException | IOException e) {
				System.out.println("The indicated file cannot be read");
				e.printStackTrace();
			}
		}
	}
}
