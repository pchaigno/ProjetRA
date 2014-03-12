package ra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataInterpreter {
	
	/**
	 * Computes the transaction list referring to some data
	 * @param data File in which the data are stored
	 * @return initial transactions
	 * @throws IOException fail if the file cannot be read
	 */
	public List<Transaction> interpret(File data) throws IOException {
		List<Transaction> res = new ArrayList<Transaction>();
		BufferedReader in = new BufferedReader(new FileReader(data));
		String line = in.readLine();
		List<Attribute> attributes = initAttributes(line);
		System.out.println(attributes);
		while((line = in.readLine()) != null) {
			
		}
		return res;
	}
	
	//TODO : revoir les separateur
	public List<Attribute> initAttributes(String attributes) {
		List<Attribute> res = new ArrayList<Attribute>();
		String[] table = attributes.split("\b\t");
		for(int i = 0 ; i < table.length ; i++)
			res.add(new Attribute(table[i]));
		return res;
	}
	
	public static void main(String[] args) {
		File abs = new File("");
		String path = abs.getAbsolutePath();
		System.out.println(path);
		DataInterpreter di = new DataInterpreter();
		File data  = new File(path + "/res/tickets_de_caisse.txt");
		try {
			di.interpret(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
