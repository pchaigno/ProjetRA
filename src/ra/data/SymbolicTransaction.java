package ra.data;

import java.util.List;

import ra.exception.DifferentSizeException;

public class SymbolicTransaction extends AbstractTransaction<Attribute> {
	
	/**
	 * Constructor
	 * if a value is not in the domain of its corresponding attribute,
	 * the value is included in the domain
	 * @param attributes list of attributes
	 * @param values list of values of given attributes
	 * @throws DifferentSizeException if the two lists have not the same size
	 */
	public SymbolicTransaction(List<Attribute> attributes, String[] values) 
			throws DifferentSizeException{
		super();
		if(attributes.size() != values.length)
			throw new DifferentSizeException();
		for(int i = 0 ; i < attributes.size() ; i++) {
			Attribute a = attributes.get(i);
			String s = values[i];
			a.setValue(this, s);
			data.add(a);
		}
	}

	@Override
	public String toString() {
		return "SymbolicTransaction [data=" + data + "]";
	}
	
}
