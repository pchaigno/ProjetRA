package ra.algo;

import java.util.ArrayList;
import java.util.List;

public class Itemset {
	protected ArrayList<Integer> data;
	
	/**
	 * Empty constructor
	 */
	public Itemset() {
		this.data = new ArrayList<Integer>();
	}
	
	/**
	 * Constructor
	 * @param itemset The itemset as a list.
	 */
	public Itemset(ArrayList<Integer> itemset) {
		this.data = itemset;
	}
	
	/**
	 * Getter for the size of the itemset.
	 * @return The number of item in the itemset.
	 */
	public int size() {
		return this.data.size();
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
	 * finds out if the current k-itemset is included in a k+1-itemset
	 * @param k1Itemset the superior-itemset
	 * @return true if the current itemset is included in k1Itemset
	 */
	public boolean isIncludedIn(Itemset k1Itemset) {
		boolean res = true;
		for(int i = 0 ; i < data.size() && res ; i++) {
			int item = data.get(i);
			res = k1Itemset.data.contains(item);	
		}
		return res;
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
		for(int item: this.data) {
			result += item +" ";
		}
		return result;
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
		List<Itemset> itemsetsK1 = new ArrayList<Itemset>();
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
		ArrayList<Itemset> subItemsets = new ArrayList<Itemset>();
		for(int i=0; i<this.size(); i++) {
			Itemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
	}
}