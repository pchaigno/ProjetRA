package ra.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ra.algo.Itemset;

public class MemoryDatabase extends Database {
	private List<Transaction> transactions;
	
	/**
	 * Constructor
	 * @param file Database file containing the transactions.
	 */
	public MemoryDatabase(File file) {
		DataInterpreter dataInterpreter = new DataInterpreter();
		try {
			dataInterpreter.interpret(file);
			this.transactions = dataInterpreter.getTransactions();
			this.items = dataInterpreter.getItems();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	
	@Override
	public Map<Itemset, Double> calcSupport(List<Itemset> itemsets) {
		Map<Itemset, Double> supports = new HashMap<Itemset, Double>();
		System.out.println("calcsupport itemset size = " + itemsets.size());
		for(Transaction transaction: this.transactions) {
			for(Itemset itemset: itemsets) {
				if(transaction.contains(itemset)) {
					Double value = supports.get(itemset);
					if(value == null) {
						System.out.println("value == null");
						supports.put(itemset, 1.0);
					}
					else {
						System.out.println("value != null");
						supports.put(itemset, 1.0);
					}
					
				}
			}
		}
		
		for(double support: supports.values()) {
			support = support / this.transactions.size();
		}
		return supports;
	}

	@Override
	public Set<Integer> retrieveItems() {
		return this.items;
	}
}
