package ra.data;


public class SymbolicTransaction extends AbstractTransaction<Symbol> {
	
	/**
	 * Constructor
	 * if a value is not in the domain of its corresponding attribute,
	 * the value is included in the domain
	 * @param attributes list of attributes
	 * @param values list of values of given attributes
	 * @throws IllegalArgumentException if the two lists have not the same size
	 */
	public SymbolicTransaction(String[] attributes, String[] values) 
			throws IllegalArgumentException{
		super();
		if(attributes.length != values.length)
			throw new IllegalArgumentException("the number of attributes and the number of values id different");
		for(int i = 0 ; i < attributes.length ; i++) {
			String label = attributes[i];
			String value = values[i];
			data.add(new Symbol(label, value));
		}
	}

	@Override
	public String toString() {
		return "SymbolicTransaction [data=" + data + "]";
	}
}