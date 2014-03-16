package ra.algo;

import java.util.ArrayList;
import java.util.List;

public class SymbolicItemset extends AbstractItemset<String> {

	/**
	 * Empty constructor
	 */
	public SymbolicItemset() {
		super();
	}
	
	/**
	 * Constructor
	 * @param itemset The itemset as a list.
	 */
	public SymbolicItemset(ArrayList<String> itemset) {
		super(itemset);
	}

	@Override
	@SuppressWarnings("unchecked")
	public SymbolicItemset clone() {
	    return new SymbolicItemset((ArrayList<String>)this.data.clone());
	}
	
	@Override
	public AbstractItemset<String> getBase() {
		ArrayList<String> base = new ArrayList<String>();
		for(int i=0; i<this.size()-1; i++) {
			base.add(this.get(i));
		}
		return new SymbolicItemset(base);
	}

	@Override
	public List<SymbolicItemset> calcItemsetsK1(
			AbstractItemset<String> itemset) {
		List<SymbolicItemset> itemsetsK1 = new ArrayList<SymbolicItemset>();
		if(this.commonBase(itemset)) {
			AbstractItemset<String> base = this.getBase();
			String a = this.get(this.size()-1);
			String b = itemset.get(itemset.size()-1);
			if(a.compareToIgnoreCase(b) < 0) {
				base.add(a);
				base.add(b);
			} else {
				base.add(b);
				base.add(a);
			}
			itemsetsK1.add((SymbolicItemset)base);
		}
		return itemsetsK1;
	}

	@Override
	public ArrayList<SymbolicItemset> calcSubItemsets() {
		ArrayList<SymbolicItemset> subItemsets = new ArrayList<SymbolicItemset>();
		for(int i=0; i<this.size(); i++) {
			SymbolicItemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
	}
}