package ra.data;

import java.util.ArrayList;
import java.util.List;

import ra.algo.Itemset;

public class Transaction {
	
	protected List<Integer> data;

	public Transaction() {
		data = new ArrayList<Integer>();
	}
	
	/**
	 * Constructor
	 * @param transaction The transaction as a list.
	 */
	public Transaction(List<Integer> transaction) {
		this.data = transaction;
	}
	
	/**
	 * Constructor
	 * @param attributes attributes labels
	 * @param values values of the corresponding attributes
	 * @throws IllegalArgumentException threw if the two parameters don't have the same length
	 */
	public Transaction(String[] attributes, String[] values) throws IllegalArgumentException {
		if(attributes.length != values.length)
			throw new IllegalArgumentException("the two arrays must have the same length");
		data = new ArrayList<Integer>();
		for(int i = 0 ; i < attributes.length ; i++) {
			Symbol sym = new Symbol(attributes[i], values[i]);
			SymbolTable.INSTANCE.add(sym);
			data.add(SymbolTable.INSTANCE.get(sym));
		}
	}
	
	/**
	 * @return The items of the transaction as a list.
	 */
	public List<Integer> getItems() {
		return this.data;
	}
	
	/**
	 * Checks that an itemset is contained in a transaction.
	 * @param itemset The itemset.
	 * @return True if it is.
	 */
	public boolean contains(Itemset itemset) {
		for(int i=0; i<itemset.size(); i++) {
			if(!this.data.contains(itemset.get(i))) {
				return false;
			}
		}
		return true;
	}
}