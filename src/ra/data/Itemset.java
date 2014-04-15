package ra.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;


public class Itemset implements Comparable<Itemset> {
	protected ArrayList<Item> items;
	protected int support;
	// The stop point, ie the last transaction read when computing the support.
	// Used for update of the support.
	public int stopPoint;
	
	/**
	 * Empty constructor
	 */
	public Itemset() {
		this.items = new ArrayList<Item>();
		this.support = 0;
		this.stopPoint = 0;
	}
	
	/**
	 * Constructor
	 * @param itemset The itemset as a list.
	 */
	public Itemset(ArrayList<Item> itemset) {
		this.items = itemset;
		this.support = 0;
		this.stopPoint = 0;
	}
	
	/**
	 * Getter for the size of the itemset.
	 * @return The number of item in the itemset.
	 */
	public int size() {
		return this.items.size();
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
	public Item get(int i) {
		return this.items.get(i);
	}
	
	/**
	 * Adds an item to the itemsets.
	 * @param item The item.
	 * @return True if the item was not already part of the itemset.
	 */
	public boolean add(Item item) {
		if(this.items.contains(item)) {
			return false;
		}
		this.items.add(item);
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
			if(!itemset.get(i).equals(this.get(i))) {
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
		for(int i=0; i<this.items.size(); i++) {
			if(!k1Itemset.items.contains(this.items.get(i))) {
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
		
		for(int i=0; i<this.items.size(); i++) {
			Rule rule = new Rule();
			rule.addToConsequent(this.items.get(i));
			
			for(int j=0; j<this.items.size(); j++) {					
				if(j != i)
					rule.addToAntecedent(this.items.get(j));
			}
			
			rules.add(rule);
		}
		
		return rules;
	}
	
	public List<Item> getItems() {
		return this.items;
	}
	
	/**
	 * Makes the union of two itemsets
	 * @return an itemset which the union
	 */
	public Itemset getUnion(Itemset itemset) {
		Set<Item> result = new HashSet<Item>();

        result.addAll(this.items);
        result.addAll(itemset.getItems());

        return new Itemset(new ArrayList<Item>(result));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		if (items == null) {
			if (other.items != null) {
				return false;
			}
		} else if (!items.equals(other.items)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String result = "Itemset: ";
		for(int i=0; i<this.items.size()-1; i++) {
			result += this.items.get(i) + " ";
		}
		return result + this.items.get(this.items.size()-1);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Itemset clone() {
	    return new Itemset((ArrayList<Item>)this.items.clone());
	}
	
	/**
	 * Returns the base of the itemset, an itemset made of all the items but the last.
	 * @return The base of the itemset.
	 */
	public Itemset getBase() {
		ArrayList<Item> base = new ArrayList<Item>();
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
			Item a = this.get(this.size()-1);
			Item b = itemset.get(itemset.size()-1);
			if(a.compareTo(b) == -1) {
			// a < b
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
			subItemset.items.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
	}

	@Override
	public int compareTo(Itemset itemset) {
		int minSize = Math.min(itemset.size(), this.size());
		int compare;
		for(int i=0; i<minSize; i++) {
			compare = this.items.get(i).compareTo(itemset.get(i));
			if(compare != 0) {
				return compare;
			}
		}
		return 0;
	}
}