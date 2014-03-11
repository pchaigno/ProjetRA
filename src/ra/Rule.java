package ra;

import java.util.ArrayList;

public class Rule {
	private ArrayList<Integer> leftPart;
	private ArrayList<Integer> rightPart;
	
	/**
	 * Constructor
	 * @param Rule The rule parts as a lists.
	 */
	public Rule(ArrayList<Integer> leftPart, ArrayList<Integer> rightPart) {
		this.leftPart = leftPart;
		this.rightPart = rightPart;
	}
}
