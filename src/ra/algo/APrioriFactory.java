package ra.algo;

import java.util.List;

import ra.data.Transaction;

/**
 * 
 * @author gwendal
 * factory
 * Constructs a factory of the given type
 *
 */
public class APrioriFactory {

	public APriori makeAPriori(String type, List<Transaction> transactions) {
		switch(type) {
		case "frequent" :
			return new APriori(transactions);
		case "maximal" :
			return new MaxAPriori(transactions);
		case "closed" :
			return new OptimizedClosAPriori(transactions);
		default :
			return null;
		}
	}
}
