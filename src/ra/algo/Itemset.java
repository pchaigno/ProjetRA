package ra.algo;

import java.util.ArrayList;
import java.util.List;

public class Itemset extends AbstractItemset<Integer> {
	
	/**
	 * Empty constructor
	 */
	public Itemset() {
		super();
	}
	
	/**
	 * Constructor
	 * @param itemset The itemset as a list.
	 */
	public Itemset(ArrayList<Integer> itemset) {
		super(itemset);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Itemset clone() {
	    return new Itemset((ArrayList<Integer>)this.data.clone());
	}
	
	@Override
	public Itemset getBase() {
		ArrayList<Integer> base = new ArrayList<Integer>();
		for(int i=0; i<this.size()-1; i++) {
			base.add(this.get(i));
		}
		return new Itemset(base);
	}
	
	@Override
	public List<Itemset> calcItemsetsK1(AbstractItemset<Integer> itemset) {
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
	
	@Override
	public ArrayList<Itemset> calcSubItemsets() {
		ArrayList<Itemset> subItemsets = new ArrayList<Itemset>();
		for(int i=0; i<this.size(); i++) {
			Itemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
	}
}