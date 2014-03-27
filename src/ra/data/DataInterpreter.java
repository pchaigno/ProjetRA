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
	public static List<SymbolicTransaction> interpret(File data) throws IOException, IllegalArgumentException {
		List<SymbolicTransaction> res = new ArrayList<SymbolicTransaction>();
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line = in.readLine();
		String[] attributes = line.split(TXT_SEPARATOR);
		while((line = in.readLine()) != null) {
			String[] values = line.split(TXT_SEPARATOR);
			SymbolicTransaction t = new SymbolicTransaction(attributes, values);
			res.add(t);
		}
		in.close();
		return res;
	}
	
	public void enrich(List<Attribute> la, String transaction) {
		
	}
	
	public static void main(String[] args) {
		File abs = new File("");
		String path = abs.getAbsolutePath();
		System.out.println(path);
		File data  = new File(path + "/res/tickets_de_caisse.txt");
		List<SymbolicTransaction> transactions = null;
		try {
			transactions = DataInterpreter.interpret(data);
		} catch (IOException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		System.out.println(transactions);
		System.out.println(transactions.size());
	}
}