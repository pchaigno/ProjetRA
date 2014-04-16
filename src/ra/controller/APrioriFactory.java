package ra.controller;

import ra.algo.APriori;
import ra.algo.ClosAPriori;
import ra.algo.MaxAPriori;
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
	public static APriori makeAPriori(String type, Database database, boolean calcSupportComplitely) {
		switch(type) {
			case "frequent" :
				return new APriori(database, calcSupportComplitely);
			case "maximal" :
				return new MaxAPriori(database, calcSupportComplitely);
			case "closed" :
				return new ClosAPriori(database, calcSupportComplitely);
			default:
				return null;
		}
	}
}
