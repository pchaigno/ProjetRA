package ra.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Itemset implements Comparable<Itemset> {
	protected ArrayList<Integer> data;
	protected int support;
	// The stop point, ie the last transaction read when computing the support.
	// Used for update of the support.
	public int stopPoint;
	
	/**
	 * Empty constructor
	 */
	public Itemset() {
		this.data = new ArrayList<Integer>();
		this.support = 0;
		this.stopPoint = 0;
	}
	
	/**
	 * Constructor
	 * @param itemset The itemset as a list.
	 */
	public Itemset(ArrayList<Integer> itemset) {
		this.data = itemset;
		this.support = 0;
		this.stopPoint = 0;
	}
	
	/**
	 * Getter for the size of the itemset.
	 * @return The number of item in the itemset.
	 */
	public int size() {
		return this.data.size();
	}
	
	/**
	 * @return The support of the itemset.
	 */
	public int getSupport() {
		return this.support;
	}
	
	/**
	 * Increments the support.
	 */
	public void incrementSupport() {
		this.support++;
	}
	
	/**
	 * Getter for the items of the itemset.
	 * @param i The number of the item to get.
	 * @return The item.
	 */
	public int get(int i) {
		return this.data.get(i);
	}
	
	/**
	 * Adds an item to the itemsets.
	 * @param item The item.
	 * @return True if the item was not already part of the itemset.
	 */
	public boolean add(int item) {
		if(this.data.contains(item)) {
			return false;
		}
		this.data.add(item);
		return true;
	}
	
	/**
	 * Checks if two itemsets have a common base.
	 * @see calcSupport for a definition of the base.
	 * @param itemset The second itemset.
	 * @return True if the two itemsets have the same base.
	 */
	public boolean commonBase(Itemset itemset) {
		if(itemset.size() != this.size()) {
			throw new IllegalArgumentException("The two itemset must have the same size ("+itemset.size()+" != "+this.size()+").");
		}
		for(int i=0; i<this.size()-1; i++) {
			if(itemset.get(i) != this.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the current k-itemset is included in a k+1-itemset
	 * @param k1Itemset the superior-itemset.
	 * @return True if the current itemset is included in k1Itemset.
	 */
	public boolean isIncludedIn(Itemset k1Itemset) {
		if(k1Itemset.size() != this.size()+1) {
			return false;
		}
		for(int i=0; i<this.data.size(); i++) {
			if(!k1Itemset.data.contains(this.data.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Generates all the simple rules associated with the itemset
	 * @return the associated rules
	 */
	public List<Rule> generateSimpleRules() {
		List<Rule> rules = new ArrayList<Rule>();
		
		for(int i=0; i<data.size(); i++) {
			Rule rule = new Rule();
			rule.addToConsequent(data.get(i));
			
			for(int j=0; j<data.size(); j++) {					
				if(j != i)
					rule.addToAntecedent(data.get(j));
			}
			
			rules.add(rule);
		}
		
		return rules;
	}
	
	public List<Integer> getItems() {
		return this.data;
	}
	
	/**
	 * Makes the union of two itemsets
	 * @return an itemset which the union
	 */
	public Itemset getUnion(Itemset itemset) {
		Set<Integer> result = new HashSet<Integer>();

        result.addAll(this.data);
        result.addAll(itemset.getItems());

        return new Itemset(new ArrayList<Integer>(result));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Itemset other = (Itemset) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String result = "Itemset: ";
		for(int i=0; i<this.data.size()-1; i++) {
			result += this.data.get(i) + " ";
		}
		return result + this.data.get(this.data.size()-1);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Itemset clone() {
	    return new Itemset((ArrayList<Integer>)this.data.clone());
	}
	
	/**
	 * Returns the base of the itemset, an itemset made of all the items but the last.
	 * @return The base of the itemset.
	 */
	public Itemset getBase() {
		ArrayList<Integer> base = new ArrayList<Integer>();
		for(int i=0; i<this.size()-1; i++) {
			base.add(this.get(i));
		}
		return new Itemset(base);
	}
	
	/**
	 * Computes the k+1-itemsets from a k-itemset.
	 * @param itemset The itemset.
	 * @return The k+1-itemsets.
	 */
	public List<Itemset> calcItemsetsK1(Itemset itemset) {
		List<Itemset> itemsetsK1 = new Vector<Itemset>();
		if(this.commonBase(itemset)) {
			Itemset base = this.getBase();
			int a = this.get(this.size()-1);
			int b = itemset.get(itemset.size()-1);
			if(a < b) {
				base.add(a);
				base.add(b);
			} else {
				base.add(b);
				base.add(a);
			}
			itemsetsK1.add(base);
		}
		return itemsetsK1;
	}
	
	/**
	 * Computes the k-itemsets from a k+1-itemset.
	 * @return The k-itemsets.
	 */
	public List<Itemset> calcSubItemsets() {
		Vector<Itemset> subItemsets = new Vector<Itemset>();
		for(int i=0; i<this.size(); i++) {
			Itemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
	}

	@Override
	public int compareTo(Itemset itemset) {
		int minSize = Math.min(itemset.size(), this.size());
		int compare;
		for(int i=0; i<minSize; i++) {
			compare = this.data.get(i).compareTo(itemset.get(i));
			if(compare != 0) {
				return compare;
			}
		}
		return 0;
	}

}