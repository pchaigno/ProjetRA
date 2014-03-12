package ra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Itemset {
	private ArrayList<Integer> data;

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

	@Override
	@SuppressWarnings("unchecked")
	public Itemset clone() {
	    return new Itemset((ArrayList<Integer>)this.data.clone());
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
	 * Computes the support of the itemset on some transactions.
	 * @param transactions The transactions.
	 * @return The support.
	 */
	public double calcSupport(List<Transaction> transactions) {
		double support = 0;
		for(Transaction transaction: transactions) {
			if(transaction.contains(this)) {
				support++;
			}
		}
		return support / transactions.size();
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
	public ArrayList<Itemset> calcSubItemsets() {
		ArrayList<Itemset> subItemsets = new ArrayList<Itemset>();
		for(int i=0; i<this.size(); i++) {
			Itemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
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
	
	/**
	 * 
	 * @return
	 */
	public List<Rule> generateAllRules() {
		List<Rule> rules = new ArrayList<Rule>();
		
		for(int i=0; i<data.size(); i++) {
			Rule rule = new Rule();
			rule.addRightPart(data.get(i));
			
			for(int j=0; j<data.size(); j++) {					
				if(j != i)
					rule.addLeftPart(data.get(j));
			}
			
			rules.add(rule);
		}
		
		return rules;
	}
	
	public List<Integer> getItems() {
		return this.data;
	}
	
	/**
	 * 
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itemset other = (Itemset) obj;
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
		for(int item: this.data) {
			result += item+" ";
		}
		return result;
	}
	
	/**
	 * Tests
	 * @param args
	 */
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		
		ArrayList<Integer> t1 = new ArrayList<Integer>() {{
			add(1); add(3); add(4);
		}};
		
		ArrayList<Integer> t2 = new ArrayList<Integer>() {{
			add(1); add(3); add(4); add(5);
		}};
		
		Itemset itemset1 = new Itemset(t1);
		Itemset itemset2 = new Itemset(t2);
		
		List<Rule> rules1 = itemset1.generateAllRules();
		List<Rule> rules2 = itemset2.generateAllRules();
		
		System.out.println("Rules from itemset1");
		for(Rule rule : rules1)
			System.out.println(rule.toString());
		
		System.out.println("Rules from itemset2");
		for(Rule rule : rules2)
			System.out.println(rule.toString());
		
		Itemset itemset3 = itemset1.getUnion(itemset2);
		System.out.println("Union of itemset1 and itemset2");
		System.out.println(itemset3.toString());
		
		
	}
}