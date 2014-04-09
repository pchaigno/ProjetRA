package ra.controller;

import java.io.IOException;
import java.util.List;

import ra.algo.APriori;
import ra.algo.Itemset;
import ra.data.DataInterpreter;
import ra.data.Transaction;

/**
 * Main program
 * usage : java Main <source_file> [options]
 * options :
 * -support <double value>
 * -confidence <double value>
 * -type <frequent | maximal | closed>
 */
public class Main {

	public static void main(String[] args) {
		Interpretor interpretor = null;
		try {
			interpretor = new Interpretor(args);
		} catch (IllegalArgumentException e) {
			System.out.println("Usage : java Main source_file [options]");
			System.out.println("options:");
			System.out.println("-support <double value>");
			System.out.println("-confidence <double value>");
			System.out.println("-type <frequent | maximal | closed>");
			return;
		}

		// Data interpretation
		List<Transaction> transactions;
		try {
			transactions =  DataInterpreter.interpret(interpretor.getSource());
		} catch (IllegalArgumentException | IOException e) {
			System.err.println("The indicated file cannot be read:");
			e.printStackTrace();
			return;
		}

		if(transactions != null) {
			APriori ap = APrioriFactory.makeAPriori(interpretor.getType(), transactions);
			if(ap != null) {
				List<List<Itemset>> itemsets = ap.aPriori(interpretor.getSupport());
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
}
