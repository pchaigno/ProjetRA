package ra.controller;

import ra.algo.APriori;
import ra.algo.MaxAPriori;
import ra.algo.OptimizedClosAPriori;
import ra.data.Database;

/**
 * Factory for the APriori algorithms.
 */
public class APrioriFactory {

	/**
	 * Return algorithm corresponding to the type asked.
	 * @param type The type of APriori algorithm.
	 * @param database The database containing the transactions.
	 * @return The APriori algorithm.
	 */
	public static APriori makeAPriori(String type, Database database) {
		switch(type) {
			case "frequent" :
				return new APriori(database);
			case "maximal" :
				return new MaxAPriori(database);
			case "closed" :
				return new OptimizedClosAPriori(database);
			default:
				return null;
		}
	}
}
