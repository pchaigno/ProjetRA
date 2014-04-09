package ra.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInterpreter {
	public static final String TXT_SEPARATOR = "[\\s\\t]+";
	
	private static Map<String, Map<String, Integer>> valueOf = new HashMap<String, Map<String, Integer>>();
	
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
		int n = 0;
		while((line = in.readLine()) != null) {
			if(n == 0)
				initValueOf(line);
			if(!"".equals(line)) {
				String[] values = line.split(TXT_SEPARATOR);
				Transaction transaction = new Transaction();
				for(int i=0; i<values.length; i++) {
					transaction.addItem(Integer.valueOf(values[i]));
				}
				res.add(transaction);
			}
			n++;
		}
		in.close();
		return res;
	}
	
	private static void initValueOf(String line) {
		String[] keys = line.split(TXT_SEPARATOR);
		for(int i = 0 ; i < keys.length ; i++) {
			valueOf.put(keys[0], null);
		}
	}
}