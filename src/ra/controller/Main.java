package ra.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ra.algo.APriori;
import ra.algo.Itemset;
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


		Interpretor interpretor = new Interpretor();
		DataInterpreter di = new DataInterpreter();
		try {
			interpretor.interpret(args);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Usage : java Main source_file [options]");
			System.out.println("options:");
			System.out.println("-support <double value>");
			System.out.println("-confidence <double value>");
			System.out.println("-type <frequent | maximal | closed>");
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
			List<List<Itemset>> itemsets = ap.aPriori(support);
			for(int i=0; i<itemsets.size(); i++) {
				System.out.println((i+1)+"-itemsets:");
				for(Itemset itemset: itemsets.get(i)) {
					System.out.println(itemset);
				}
				System.out.println();
			}
		}

	}
}
