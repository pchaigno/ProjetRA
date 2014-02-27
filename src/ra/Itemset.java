package ra;

import java.util.ArrayList;
import java.util.List;

public class Itemset implements Cloneable {
	private ArrayList<Integer> data;

	public Itemset() {
		this.data = new ArrayList<Integer>();
	}
	
	public Itemset(ArrayList<Integer> itemset) {
		this.data = itemset;
	}

	@SuppressWarnings("unchecked")
	public Itemset clone() {
	    return new Itemset((ArrayList<Integer>)this.data.clone());
	}
	
	public int size() {
		return this.data.size();
	}
	
	public int get(int i) {
		return this.data.get(i);
	}
	
	public boolean add(int item) {
		if(this.data.contains(item)) {
			return false;
		}
		this.data.add(item);
		return true;
	}
	
	public Itemset getBase() {
		ArrayList<Integer> base = new ArrayList<Integer>();
		for(int i=0; i<this.size()-1; i++) {
			base.add(this.get(i));
		}
		return new Itemset(base);
	}
	
	public double calcSupport(List<Transaction> transactions) {
		double support = 0;
		for(Transaction transaction: transactions) {
			if(transaction.contains(this)) {
				support++;
			}
		}
		return support / transactions.size();
	}
	
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
	
	public ArrayList<Itemset> calcSubItemsets() {
		ArrayList<Itemset> subItemsets = new ArrayList<Itemset>();
		for(int i=0; i<this.size(); i++) {
			Itemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
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
	
	public String toString() {
		String result = "Itemset: ";
		for(int item: this.data) {
			result += item+" ";
		}
		return result;
	}
}