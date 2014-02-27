package ra;

import java.util.List;

public class Transaction {
	private List<Integer> data;

	public Transaction(List<Integer> transaction) {
		this.data = transaction;
	}
	
	public List<Integer> getItems() {
		return this.data;
	}
	
	public boolean contains(Itemset itemset) {
		for(int i=0; i<itemset.size(); i++) {
			if(!this.data.contains(itemset.get(i))) {
				return false;
			}
		}
		return true;
	}
}