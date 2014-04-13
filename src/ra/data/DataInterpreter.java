package ra.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataInterpreter {
	public static final String TXT_SEPARATOR = "[\\s\\t]+";
	private List<Transaction> transactions;
	private Set<Item> items;
	
	public DataInterpreter() {
		this.transactions = new ArrayList<Transaction>();
		this.items = new HashSet<Item>();
	}
	
	/**
	 * Determines the type of data that the file contains.
	 * It can be numbers or words.
	 * @param data
	 * @return True if the transaction are composed of numbers.
	 * @throws IOException
	 */
	public static boolean areNumberTransactions(File data) throws IOException {
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line;
		while((line = in.readLine()) != null) {
			if(!"".equals(line)) {
				String[] values = line.split(TXT_SEPARATOR);
				try {
					Integer.valueOf(values[0]);
				} catch(NumberFormatException e) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Computes the transaction list referring to some data.
	 * @param data File in which the data are stored
	 * @return initial transactions
	 * @throws IOException fail if the file cannot be read
	 * @throws DifferentSizeException fail if at least one of the transaction size is different from the number of attributes
	 */
	public void interpret(File data) throws IllegalArgumentException, IOException {
		if(areNumberTransactions(data)) {
			this.interpretNumbers(data);
		} else {
			this.interpretWords(data);
		}
	}
	
	/**
	 * Computes the transaction list referring to some data.
	 * The data are integers.
	 * @param data File in which the data are stored
	 * @return initial transactions
	 * @throws IOException fail if the file cannot be read
	 * @throws DifferentSizeException fail if at least one of the transaction size is different from the number of attributes
	 */
	private void interpretNumbers(File data) throws IOException, IllegalArgumentException {
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line;
		while((line = in.readLine()) != null) {
			if(!"".equals(line)) {
				String[] values = line.split(TXT_SEPARATOR);
				Transaction transaction = new Transaction();
				for(int i=0; i<values.length; i++) {
					Item item = new Item(Integer.valueOf(values[i]));
					transaction.addItem(item);
					this.items.add(item);
				}
				this.transactions.add(transaction);
			}
		}
		in.close();
	}
	
	/**
	 * Computes the transaction list referring to some data.
	 * The data are words.
	 * @param data File in which the data are stored
	 * @return initial transactions
	 * @throws IOException fail if the file cannot be read
	 * @throws DifferentSizeException fail if at least one of the transaction size is different from the number of attributes
	 */
	private void interpretWords(File data) throws IOException, IllegalArgumentException {
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(data));
		Map<String, Item> words = new HashMap<String, Item>();
		String line;
		while((line = in.readLine()) != null) {
			if(!"".equals(line)) {
				String[] values = line.split(TXT_SEPARATOR);
				Transaction transaction = new Transaction();
				for(int i=0; i<values.length; i++) {
					String word = values[i];
					Item item = words.get(word);
					if(item == null) {
						item = new WordItem(word);
						words.put(word, item);
					}
					transaction.addItem(item);
					this.items.add(item);
				}
				this.transactions.add(transaction);
			}
		}
		in.close();
	}
	
	/**
	 * @return The items.
	 */
	public Set<Item> getItems() {
		return this.items;
	}
	
	/**
	 * @return The transactions.
	 */
	public List<Transaction> getTransactions() {
		return this.transactions;
	}
}