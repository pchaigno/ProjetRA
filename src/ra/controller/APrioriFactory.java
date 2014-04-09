package ra.controller;

import java.util.List;

import ra.algo.APriori;
import ra.algo.MaxAPriori;
import ra.algo.OptimizedClosAPriori;
import ra.data.Transaction;

/**
 * Factory for the APriori algorithms.
 */
public class APrioriFactory {

	/**
	 * Return algorithm corresponding to the type asked.
	 * @param type The type of APriori algorithm.
	 * @param transactions The transactions.
	 * @return The APriori algorithm.
	 */
	public static APriori makeAPriori(String type, List<Transaction> transactions) {
		switch(type) {
			case "frequent" :
				return new APriori(transactions);
			case "maximal" :
				return new MaxAPriori(transactions);
			case "closed" :
				return new OptimizedClosAPriori(transactions);
			default:
				return null;
		}
	}
}
