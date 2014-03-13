package ra.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ra.algo.Itemset;
import ra.exception.DifferentSizeException;

public class SymbolicTransaction {
	
	private Map<Attribute, String> data;

	/**
	 * Constructor
	 * if a value is not in the domain of its corresponding attribute,
	 * the value is included in the domain
	 * @param attributes list of attributes
	 * @param values list of values of given attributes
	 * @throws DifferentSizeException if the two lists have not the same size
	 */
	public SymbolicTransaction(List<Attribute> attributes, List<String> values) 
			throws DifferentSizeException{
		if(attributes.size() != values.size())
			throw new DifferentSizeException();
		this.data = new HashMap<Attribute, String>();
		for(int i = 0 ; i < attributes.size() ; i++) {
			Attribute a = attributes.get(i);
			String s = values.get(i);
			a.add(s);
			data.put(a, s);
		}
	}
	
	/**
	 * @return The items of the transaction as a map.
	 */
	public Map<Attribute, String> getItems() {
		return this.data;
	}
	
	/**
	 * Checks that an itemset is contained in a transaction.
	 * @param itemset The itemset.
	 * @return True if it is.
	 *//*
	public boolean contains(Itemset itemset) {
		for(int i=0; i<itemset.size(); i++) {
			if(!this.data.contains(itemset.get(i))) {
				return false;
			}
		}
		return true;
	}
	*/
}
