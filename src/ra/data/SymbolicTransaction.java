package ra.data;

import java.util.List;

public class SymbolicTransaction extends AbstractTransaction<String> {
	
	/**
	 * Constructor
	 * if a value is not in the domain of its corresponding attribute,
	 * the value is included in the domain
	 * @param attributes list of attributes
	 * @param values list of values of given attributes
	 * @throws IllegalArgumentException if the two lists have not the same size
	 */
	public SymbolicTransaction(List<Attribute> attributes, String[] values) 
			throws IllegalArgumentException{
		super();
		if(attributes.size() != values.length)
			throw new IllegalArgumentException("the number of attributes and the number of values id different");
		for(int i = 0 ; i < attributes.size() ; i++) {
			Attribute a = attributes.get(i);
			String s = values[i];
			data.add(a + "=" + s);
		}
	}

	@Override
	public String toString() {
		return "SymbolicTransaction [data=" + data + "]";
	}
	
}
