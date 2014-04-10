package ra.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataInterpreter {
	public static final String TXT_SEPARATOR = "[\\s\\t]+";
	private List<Transaction> transactions;
	private Set<Integer> items;
	
	public DataInterpreter() {
		this.transactions = new ArrayList<Transaction>();
		this.items = new HashSet<Integer>();
	}
	
	/**
	 * Computes the transaction list referring to some data
	 * @param data File in which the data are stored
	 * @return initial transactions
	 * @throws IOException fail if the file cannot be read
	 * @throws DifferentSizeException fail if at least one of the transaction size is different from the number of attributes
	 */
	public void interpret(File data) throws IOException, IllegalArgumentException {
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line;
		while((line = in.readLine()) != null) {
			if(!"".equals(line)) {
				String[] values = line.split(TXT_SEPARATOR);
				Transaction transaction = new Transaction();
				for(int i=0; i<values.length; i++) {
					int item = Integer.valueOf(values[i]);
					transaction.addItem(item);
					items.add(item);
				}
				this.transactions.add(transaction);
			}
		}
		in.close();
	}
	
	/**
	 * @return The items.
	 */
	public Set<Integer> getItems() {
		return this.items;
	}
	
	/**
	 * @return The transactions.
	 */
	public List<Transaction> getTransactions() {
		return this.transactions;
	}
}