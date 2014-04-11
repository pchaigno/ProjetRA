package ra.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
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

	@SuppressWarnings("null")
	@Override
	public void calcSupport(List<Itemset> itemsets) {
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
					String[] values = line.split(DataInterpreter.TXT_SEPARATOR);
					// Compute the new support for each itemset with this transaction:
					for(int j = 0 ; j < itemsets.size() ; j++) {
						Itemset itemset = itemsets.get(j);
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
							itemset.incrementSupport();
						}
					}
				}
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

	@Override
	protected List<Itemset> calcSupportIncomplete(List<Itemset> itemsets, int minSupport) {
		// TODO
		return itemsets;
	}

	@SuppressWarnings("null")
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

	@Override
	protected int getNbTransactions() throws IOException {
	    @SuppressWarnings("resource")
		InputStream is = new BufferedInputStream(new FileInputStream(this.file));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
			    empty = false;
			    for (int i = 0; i < readChars; ++i) {
			        if (c[i] == '\n') {
			            ++count;
			        }
			    }
			}
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}

	@Override
	public void updateSupport(List<Itemset> itemsets) {
		// TODO Optimize?
		this.calcSupport(itemsets);
	}
}
