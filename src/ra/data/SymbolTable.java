package ra.data;

import java.util.ArrayList;
import java.util.List;

public final class SymbolTable {

	public static final SymbolTable INSTANCE = new SymbolTable();
	
	private List<Symbol> table;
	
	private SymbolTable() {
		table = new ArrayList<Symbol>();
	}
	
	public boolean contains(Symbol sym) {
		for(Symbol s : table) {
			if(s.equals(sym))
				return true;
		}
		return false;
	}
	
	public void add(Symbol sym) {
		if(!contains(sym))
			table.add(sym);
	}
	
	public int get(Symbol sym) throws IllegalArgumentException {
		if(!contains(sym))
			throw new IllegalArgumentException("the symbol doesn't exist");
		for(int i = 0 ; i < table.size() ; i++) {
			if(table.get(i).equals(sym))
				return i;
		}
		return -1;
	}
	
	public Symbol get(int code) {
		return table.get(code);
	}
}
