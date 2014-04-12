package ra.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ra.algo.Itemset;

public class MemoryDatabase extends Database {
	protected List<Transaction> transactions;
	
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
	public void calcSupport(List<Itemset> itemsets) {
		for(Transaction transaction: this.transactions) {
			for(Itemset itemset: itemsets) {
				if(transaction.contains(itemset)) {
					itemset.incrementSupport();
				}
			}
		}
	}
	
	@Override
	public void updateSupport(List<Itemset> itemsets) {
		for(Itemset itemset: itemsets) {
			for(int i=itemset.stopPoint+1; i<this.transactions.size(); i++) {
				if(transactions.get(i).contains(itemset)) {
					itemset.incrementSupport();
				}
			}
		}
	}

	@Override
	protected List<Itemset> calcSupportIncomplete(List<Itemset> itemsets, int minSupport) {
		List<Itemset> frequentItemsets = new ArrayList<Itemset>();
		Itemset itemset;
		for(int j=0; j<this.transactions.size(); j++) {
			for(int i=0; i<itemsets.size(); i++) {
				itemset = itemsets.get(i);
				if(this.transactions.get(j).contains(itemset)) {
					itemset.incrementSupport();
					if(itemset.getSupport() >= minSupport) {
						// Save the last transaction read to update later.
						itemset.stopPoint = j;
						frequentItemsets.add(itemset);
						itemsets.remove(i);
						i--;
					}
				}
			}
		}
		Collections.sort(frequentItemsets);
		return frequentItemsets;
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
