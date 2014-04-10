package ra.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ra.algo.Itemset;

public class FileDatabase extends Database {
	private File file;
	
	/**
	 * Constructor
	 * @param file Database file containing the transactions.
	 */
	public FileDatabase(File file) {
		this.file = file;
	}

	@Override
	public Map<Itemset, Double> calcSupport(List<Itemset> itemsets) {
		Map<Itemset, Double> supports = new HashMap<Itemset, Double>();
		int databaseSize = 0;
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		// Iterate on the transactions:
		String line;
		try {
			while((line = in.readLine()) != null) {
				if(!"".equals(line)) {
					// New transaction.
					databaseSize++;
					
					String[] values = line.split(DataInterpreter.TXT_SEPARATOR);
					// Compute the new support for each itemset with this transaction:
					for(Itemset itemset: itemsets) {
						// Checks that the whole itemset is present in this transaction:
						boolean itemsetPresent = true;
						for(int i=0; i<itemset.size(); i++) {
							// Checks that the item is present in this transaction:
							boolean itemPresent = false;
							for(String value: values) {
								if(value.equals(""+itemset.get(i))) {
									itemPresent = true;
									break;
								}
							}
							// If this item is not present then the whole itemset isn't present:
							if(!itemPresent) {
								itemsetPresent = false;
								break;
							}
						}
						// If the whole itemset is present we increment the support:
						if(itemsetPresent) {
							Double value = supports.get(itemset);
							if(value == null) {
								supports.put(itemset, 1.0);
							} else {
								supports.put(itemset, value+1);
							}
						}
					}
				}
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		for(double support: supports.values()) {
			support = support / databaseSize;
		}
		return supports;
	}

	@Override
	public Set<Integer> retrieveItems() {
		// If we already retrieved them:
		if(this.items != null) {
			return this.items;
		}
		
		this.items = new HashSet<Integer>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		String line;
		try {
			while((line = in.readLine()) != null) {
				if(!"".equals(line)) {
					String[] values = line.split(DataInterpreter.TXT_SEPARATOR);
					for(String value: values) {
						this.items.add(Integer.valueOf(value));
					}
				}
			}
			in.close();
		} catch (NumberFormatException | IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		return this.items;
	}
}
