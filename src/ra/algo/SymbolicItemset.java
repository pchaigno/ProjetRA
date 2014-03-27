package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Symbol;

public class SymbolicItemset extends AbstractItemset<Symbol> {

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
	public SymbolicItemset(ArrayList<Symbol> itemset) {
		super(itemset);
	}

	@Override
	@SuppressWarnings("unchecked")
	public SymbolicItemset clone() {
	    return new SymbolicItemset((ArrayList<Symbol>)this.data.clone());
	}
	
	@Override
	public AbstractItemset<Symbol> getBase() {
		ArrayList<Symbol> base = new ArrayList<Symbol>();
		for(int i=0; i<this.size()-1; i++) {
			base.add(this.get(i));
		}
		return new SymbolicItemset(base);
	}

	@Override
	public List<SymbolicItemset> calcItemsetsK1(
			AbstractItemset<Symbol> itemset) {
		List<SymbolicItemset> itemsetsK1 = new ArrayList<SymbolicItemset>();
		if(this.commonBase(itemset)) {
			AbstractItemset<Symbol> base = this.getBase();
			Symbol a = this.get(this.size()-1);
			Symbol b = itemset.get(itemset.size()-1);
			if(a.toString().compareToIgnoreCase(b.toString()) < 0) {
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
	public List<SymbolicItemset> calcSubItemsets() {
		ArrayList<SymbolicItemset> subItemsets = new ArrayList<SymbolicItemset>();
		for(int i=0; i<this.size(); i++) {
			SymbolicItemset subItemset = this.clone();
			subItemset.data.remove(i);
			subItemsets.add(subItemset);
		}
		return subItemsets;
	}
}