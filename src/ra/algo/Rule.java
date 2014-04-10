package ra.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ra.data.Database;
import ra.data.Transaction;

public class Rule {
	private ArrayList<Integer> antecedent;
	private ArrayList<Integer> consequent;

	/**
	 * Empty-parameter constructor
	 */
	public Rule() {
		this.antecedent = new ArrayList<Integer>();
		this.consequent = new ArrayList<Integer>();
	}

	/**
	 * Constructor
	 * @param antecedent The antecedent of the rule
	 * @param consequent The consequent of the rule
	 */
	public Rule(ArrayList<Integer> antecedent, ArrayList<Integer> consequent) {
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	/**
	 * 
	 * @param transactions The transactions associated with the rule
	 * @return The confidence of the rule
	 */
	public double getConfidence(Database database) {
		
		Itemset numerator = (new Itemset(this.antecedent)).getUnion(new Itemset(this.consequent));
		Itemset denominator = new Itemset(this.antecedent);
		
		return numerator.getSupport()/denominator.getSupport();
	}
	
	/**
	 * Accessor to the antecedent
	 * @return The antecedent
	 */
	public ArrayList<Integer> getAntecedent() {
		return this.antecedent;
	}
	
	/**
	 * Accessor to the consequent
	 * @return The consequent
	 */
	public ArrayList<Integer> getConsequent() {
		return this.consequent;
	}

	/**
	 * @param value The value to add to the rule as antecedent
	 */
	public void addToAntecedent(int value) {
		this.antecedent.add(value);
	}

	/**
	 * @param value The value to add to the rule as consequent
	 */
	public void addToConsequent(int value) {
		this.consequent.add(value);
	}

	/**
	 * Derive the k+1 consequent rules from a given k consequent rule by transferring
	 * each antecedent item as consequent
	 * @return The derived rules
	 */
	public ArrayList<Rule> deriveRules() {
		ArrayList<Rule> derivedRules = new ArrayList<Rule>();

		// The rule must have at least an antecedent of size two to derive rules
		if(this.antecedent.size() >= 2) {
			for(Integer i : this.antecedent) {
				ArrayList<Integer> derivedAntecedent = new ArrayList<Integer>(this.antecedent);
				ArrayList<Integer> derivedConsequent = new ArrayList<Integer>(this.consequent);
				derivedAntecedent.remove(i);
				derivedConsequent.add(i);

				derivedRules.add(new Rule(derivedAntecedent, derivedConsequent));
			}
		}

		return derivedRules;
	}

	public String toString() {
		String rule = "Rule: ";
		for(int item: this.antecedent) {
			rule += item+" ";
		}

		rule += "-> ";

		for(int item: this.consequent) {
			rule += item+" ";
		}

		return rule;
	}
}
