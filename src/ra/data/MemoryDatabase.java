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
		
		for(Transaction transaction: this.transactions) {
			for(Itemset itemset: itemsets) {
				if(transaction.contains(itemset)) {
					supports.put(itemset, supports.get(itemset)+1);
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
