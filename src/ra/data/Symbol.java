package ra.data;


public class Symbol {

	private String label;
	private String value;
	
	public Symbol(String lbl, String val) {
		label = lbl;
		value = val;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		Symbol symbol = (Symbol)obj;
		return (label.equals(symbol.label) && value.equals(symbol.value));
	}

	@Override
	public String toString() {
		return label+"="+value;
	}

	@Override
	public int hashCode() {
		return label.hashCode() * value.hashCode();
	}
	
}
