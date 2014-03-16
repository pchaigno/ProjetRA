package ra.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Attribute {

	private String name;
	private Set<String> symbols;
	private Map<Object, String> values;
	
	/**
	 * Attribute without symbols
	 * @param name denomination of the attribute
	 */
	public Attribute(String name) {
		this.name = name;
		this.symbols = new HashSet<String>();
		values = new HashMap<Object, String>();
	}
	
	/**
	 * Attribute initialization constructor
	 * @param name denomination of the attribute
	 * @param symbols possible values of the attribute
	 */
	public Attribute(String name, Set<String> symbols) {
		this.name = name;
		this.symbols = symbols;
	}
	
	public void add(String symbol) {
		symbols.add(symbol);
	}

	@Override
	public String toString() {
		return name;
	}

	public String getValue(Object o) {
		return values.get(o);
	}

	public void setValue(Object o, String value) {
		values.put(o, value);
	}
}