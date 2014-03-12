package ra;

import java.util.ArrayList;

public class Rule {
	private ArrayList<Integer> leftPart;
	private ArrayList<Integer> rightPart;
	
	public Rule() {
		this.leftPart = new ArrayList<Integer>();
		this.rightPart = new ArrayList<Integer>();
	}
	
	/**
	 * Constructor
	 * @param Rule The rule parts as a lists.
	 */
	public Rule(ArrayList<Integer> leftPart, ArrayList<Integer> rightPart) {
		this.leftPart = leftPart;
		this.rightPart = rightPart;
	}

	/*public double getConfidence() {
		return null;
	}*/
	
	public void addLeftPart(int value) {
		this.leftPart.add(value);
	}
	
	public void addRightPart(int value) {
		this.rightPart.add(value);
	}
	
	public String toString() {
		String rule = "Rule: ";
		for(int item: this.leftPart) {
			rule += item+" ";
		}
		
		rule += "-> ";
		
		for(int item: this.rightPart) {
			rule += item+" ";
		}
		
		return rule;
	}
}
