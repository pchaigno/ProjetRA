package ra.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ra.algo.Itemset;

public class Database {
	private File file;
	
	/**
	 * Constructor
	 * @param filePath Path to the database file containing the transactions.
	 */
	public Database(String filePath) {
		this.file = new File(filePath);
	}
	
	/**
	 * Computes the supports for a list of itemset with the transactions in this database.
	 * @param itemsets The itemsets
	 * @return The support value for each itemset.
	 * @throws IOException If the database file can't be opened or read.
	 */
	public Map<Itemset, Double> calcSupport(List<Itemset> itemsets) throws IOException {
		Map<Itemset, Double> supports = new HashMap<Itemset, Double>();
		int databaseSize = 0;
		
		BufferedReader in = new BufferedReader(new FileReader(this.file));
		// Iterate on the transactions:
		String line;
		while((line = in.readLine()) != null) {
			if(!"".equals(line)) {
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
						supports.put(itemset, supports.get(itemset)+1);
					}
				}
			}
		}
		in.close();
		
		for(double support: supports.values()) {
			support = support / databaseSize;
		}
		return supports;
	}
}
