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
	public List<Transaction> interpret(File data) throws IOException, IllegalArgumentException {
		List<Transaction> res = new ArrayList<Transaction>();
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line = in.readLine();
		String[] attributes = line.split(TXT_SEPARATOR);
		while((line = in.readLine()) != null) {
			String[] values = line.split(TXT_SEPARATOR);
			Transaction t = new Transaction(attributes, values);
			res.add(t);
		}
		in.close();
		return res;
	}
	
	public static void main(String[] args) {
		File abs = new File("");
		DataInterpreter di = new DataInterpreter();
		String path = abs.getAbsolutePath();
		System.out.println(path);
		File data  = new File(path + "/res/tickets_de_caisse.txt");
		List<Transaction> transactions = null;
		try {
			transactions = di.interpret(data);
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		System.out.println(transactions);
		System.out.println(transactions.size());
	}
}