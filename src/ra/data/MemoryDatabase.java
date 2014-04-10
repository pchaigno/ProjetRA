package ra.data;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
	public void calcSupport(List<Itemset> itemsets, double minSupport) {
		for(int j = 0 ; j < this.transactions.size() ; j++) {
			Transaction transaction = this.transactions.get(j);
			for(int i = 0 ; i < itemsets.size() ;i++) {
				if(transaction.contains(itemsets.get(i))) {
					itemsets.get(i).incrementSupport();
				}
				if(j == this.transactions.size()-1 && itemsets.get(i).getSupport() < minSupport) {
					itemsets.remove(i);
					i--;
				}
			}
		}
	}

	@Override
	public Set<Integer> retrieveItems() {
		return this.items;
	}

	@Override
	protected int getNbTransactions() {
		return this.transactions.size();
	}
}
