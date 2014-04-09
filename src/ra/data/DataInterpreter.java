package ra.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataInterpreter {
	public static final String TXT_SEPARATOR = "[\\s\\t]+";
	
	/**
	 * Computes the transaction list referring to some data
	 * @param data File in which the data are stored
	 * @return initial transactions
	 * @throws IOException fail if the file cannot be read
	 * @throws DifferentSizeException fail if at least one of the transaction size is different from the number of attributes
	 */
	public static List<Transaction> interpret(File data) throws IOException, IllegalArgumentException {
		List<Transaction> res = new ArrayList<Transaction>();
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line;
		while((line = in.readLine()) != null) {
			if(!"".equals(line)) {
				String[] values = line.split(TXT_SEPARATOR);
				Transaction transaction = new Transaction();
				for(int i=0; i<values.length; i++) {
					transaction.addItem(Integer.valueOf(values[i]));
				}
				res.add(transaction);
			}
		}
		in.close();
		return res;
	}
}