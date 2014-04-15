package ra.controller;

import java.io.FileNotFoundException;
import java.util.List;

import ra.algo.APriori;
import ra.data.ConcurrentMemoryDatabase;
import ra.data.Database;
import ra.data.FileDatabase;
import ra.data.Itemset;
import ra.data.Rule;

/**
 * Main program
 * usage : java Main <source_file> [options]
 * options :
 * -support <double value>
 * -confidence <double value>
 * -type <frequent | maximal | closed>
 * -memory The transactions will be saved to the memory
 * -output <file name>
 */
public class Main {

	@SuppressWarnings("null")
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
			System.out.println("-nomemory The transactions won't be saved to the memory");
			System.out.println("-output <file name>");
			return;
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		// Data interpretation
		Database database;
		if(interpretor.useMemory()) {
			int nbCores = Runtime.getRuntime().availableProcessors();
			database = new ConcurrentMemoryDatabase(interpretor.getSource(), nbCores);
		} else {
			database = new FileDatabase(interpretor.getSource());
		}

		// APriori algorithm:
		APriori ap = APrioriFactory.makeAPriori(interpretor.getType(), database);
		if(ap != null) {
			System.out.println("Performing apriori...");
			int support = database.calcAbsoluteSupport(interpretor.getSupport());
			List<List<Itemset>> itemsets = ap.aPriori(support);
			List<Rule> rules = ap.generateRules(interpretor.getConfidence());
			System.out.println("Done");
			for(int i=0; i<itemsets.size(); i++) {
				interpretor.getOutput().println((i+1)+"-itemsets:");
				for(Itemset itemset: itemsets.get(i)) {
					interpretor.getOutput().println(itemset);
				}
				interpretor.getOutput().println();
			}
			for(Rule rule : rules) {
				interpretor.getOutput().println(rule);
			}
		}
	}
}
