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
	public static final String TXT_SEPARATOR = "[,?\\s\\t]+";
	
	private static Map<String, Map<String, Integer>> valueOf = new HashMap<String, Map<String, Integer>>();
	private static int code = 1;
	
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
		String[] keys = null;
		while((line = in.readLine()) != null) {
			if(!"".equals(line) && keys != null) {
				String[] values = line.split(TXT_SEPARATOR);
				Transaction transaction = new Transaction();
				for(int i=0; i<Math.min(values.length, keys.length); i++) {
					setValueOf(
							keys[i],
							values[i]);
					transaction.addItem(valueOf.get(keys[i]).get(values[i]));
				}
				res.add(transaction);
			} else if(keys == null)
				keys = initValueOf(line);
		}
		in.close();
		return res;
	}
	
	/**
	 * Defines the attributes labels
	 * @param line line which defines attributes labels
	 * @return splitted line argument
	 */
	public static String[] initValueOf(String line) {
		code = 1;
		String[] keys = line.split(TXT_SEPARATOR);
		for(int i = 0 ; i < keys.length ; i++) {
			valueOf.put(keys[i], new HashMap<String, Integer>());
		}
		return keys;
	}
	
	/**
	 * Sets the integer value of an instanciated attribute
	 * @param attribute attribute label
	 * @param value value of the given attribute
	 */
	public static void setValueOf(String attribute, String value) {
		if(!valueOf.get(attribute).containsKey(value)) {
			valueOf.get(attribute).put(value, code);
			code++;
		}
	}
	
	/**
	 * Gets the value of a given attribute
	 * @param attribute attribute label
	 * @param value value of the given attribute
	 * @return integer representation of the given attribute, or -1 if it doesn't exist
	 */
	public static int getValueOf(String attribute, String value) {
		if(valueOf.get(attribute).containsKey(value))
			return valueOf.get(attribute).get(value);
		return -1;
	}
}