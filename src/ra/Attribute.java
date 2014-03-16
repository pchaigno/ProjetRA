package ra;

import java.util.HashSet;
import java.util.Set;

public class Attribute {

	private String name;
	private Set<String> symbols;
	
	/**
	 * Attribute without symbols
	 * @param name denomination of the attribute
	 */
	public Attribute(String name) {
		this.name = name;
		this.symbols = new HashSet<String>();
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
	
	public void update(String symbol) {
		symbols.add(symbol);
	}

	@Override
	public String toString() {
		return name;
	}
}