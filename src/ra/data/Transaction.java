package ra.data;

import java.util.List;

public class Transaction extends AbstractTransaction<Integer> {
	
	/**
	 * Constructor
	 * @param transaction The transaction as a list.
	 */
	public Transaction(List<Integer> transaction) {
		super(transaction);
	}
	
}