package ra.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ra.algo.APriori;
import ra.data.DataInterpreter;
import ra.data.Transaction;

/**
 * 
 * @author gwlemoul
 * 
 * Main program
 * usage : java Main <source_file> [options]
 * options :
 * -support <double value>
 * -confidence <double value>
 * -type <frequent | maximal | closed>
 */
public class Main {

	public static void main(String[] args) {
		
		//Dies if case of wrong usage
		if(args.length != 1)
			System.out.println("Usage : java Main source_file");
		else {
			Interpretor interpretor = new Interpretor();
			DataInterpreter di = new DataInterpreter();
			try {
				interpretor.interpret(args);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			File srcFile = interpretor.getSource();
			double support  = interpretor.getSupport();
			double confidence = interpretor.getConfidence();
			String type = interpretor.getType();
			
			//Data interpretation
			List<Transaction> transactions = null;
			try {
				transactions =  di.interpret(srcFile);
			} catch (IllegalArgumentException | IOException e) {
				System.out.println("The indicated file cannot be read");
				e.printStackTrace();
			}
			
			
			if(transactions != null) {
				APriori ap = APrioriFactory.makeAPriori(type, transactions);
			}
			
		}
	}
}
