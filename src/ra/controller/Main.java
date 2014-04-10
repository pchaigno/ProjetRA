package ra.controller;

import java.util.List;

import ra.algo.APriori;
import ra.algo.Itemset;
import ra.data.Database;
import ra.data.FileDatabase;
import ra.data.MemoryDatabase;

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
			System.out.println("-memory The transactions will be saved to the memory");
			return;
		}

		// Data interpretation
		Database database;
		if(interpretor.useMemory()) {
			database = new MemoryDatabase(interpretor.getSource());
		} else {
			database = new FileDatabase(interpretor.getSource());
		}

		// APriori algorithm:
		APriori ap = APrioriFactory.makeAPriori(interpretor.getType(), database);
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
