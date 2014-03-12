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
		String line = "";
		while((line = in.readLine()) != null) {
			
		}
		return res;
	}
	
	//public List<Attributes> getAttributes()
	
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
