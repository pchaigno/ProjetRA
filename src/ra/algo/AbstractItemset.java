package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Transaction;

public abstract class AbstractItemset<T> {

	private ArrayList<T> data;
	
	/**
	 * Empty constructor
	 */
	public AbstractItemset() {
		this.data = new ArrayList<T>();
	}
	
	/**
	 * Constructor
	 * @param itemset The itemset as a list.
	 */
	public AbstractItemset(ArrayList<T> itemset) {
		this.data = itemset;
	}
	
	/**
	 * Accessor to the size of the itemset.
	 * @return The number of item in the itemset.
	 */
	public int size() {
		return this.data.size();
	}
	
	/**
	 * Accessor to the items of the itemset.
	 * @param i The number of the item to get.
	 * @return The item.
	 */
	public T get(int i) {
		return this.data.get(i);
	}
	
	/**
	 * Adds an item to the itemsets.
	 * @param item The item.
	 * @return True if the item was not already part of the itemset.
	 */
	public boolean add(T item) {
		if(this.data.contains(item)) {
			return false;
		}
		this.data.add(item);
		return true;
	}
	
	/**
	 * Returns the base of the itemset, an itemset made of all the items but the last.
	 * @return The base of the itemset.
	 */
	public abstract AbstractItemset<T> getBase(); /* {
		ArrayList<Integer> base = new ArrayList<Integer>();
		for(int i=0; i<this.size()-1; i++) {
			base.add(this.get(i));
		}
		return new Itemset(base);
	}*/
	
	//TODO : corriger
	/**
	 * Computes the support of the itemset on some transactions.
	 * @param transactions The transactions.
	 * @return The support.
	 */
	public double calcSupport(List<Transaction> transactions) {
		double support = 0;/*
		for(Transaction transaction: transactions) {
			if(transaction.contains(this)) {
				support++;
			}
		}*/
		return support / transactions.size();
	}
	
	/**
	 * Checks if two itemsets have a common base.
	 * @see calcSupport for a definition of the base.
	 * @param itemset The second itemset.
	 * @return True if the two itemsets have the same base.
	 */
	public boolean commonBase(AbstractItemset<T> itemset) {
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
	
	//TODO : corriger
	/**
	 * Computes the k+1-itemsets from a k-itemset.
	 * @param itemset The itemset.
	 * @return The k+1-itemsets.
	 */
	public List<AbstractItemset<T>> calcItemsetsK1(AbstractItemset<T> itemset) {
		List<AbstractItemset<T>> itemsetsK1 = new ArrayList<AbstractItemset<T>>();
		if(this.commonBase(itemset)) {
			AbstractItemset<T> base = this.getBase();
			T a = this.get(this.size()-1);
			T b = itemset.get(itemset.size()-1); /*
			if(a < b) {
				base.add(a);
				base.add(b);
			} else {
				base.add(b);
				base.add(a);
			}
			itemsetsK1.add(base);*/
		}
		return itemsetsK1;
	}
	
	//TODO : corriger
	/**
	 * Computes the k-itemsets from a k+1-itemset.
	 * @return The k-itemsets.
	 */
	public ArrayList<AbstractItemset<T>> calcSubItemsets() {
		ArrayList<AbstractItemset<T>> subItemsets = new ArrayList<AbstractItemset<T>>();/*
		for(int i=0; i<this.size(); i++) {
			AbstractItemset<T> subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}*/
		return subItemsets;
	}
	
	/**
	 * finds out if the current k-itemset is included in a k+1-itemset
	 * @param k1Itemset the superior-itemset
	 * @return true if the current itemset is included in k1Itemset
	 */
	public boolean isIncludedIn(AbstractItemset<T> k1Itemset) {
		boolean res = true;
		for(int i = 0 ; i < data.size() && res ; i++) {
			T item = data.get(i);
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractItemset<T> other = (AbstractItemset<T>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String result = "Itemset: ";
		for(T item: this.data) {
			result += item+" ";
		}
		return result;
	}
	
}
