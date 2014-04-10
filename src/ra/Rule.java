package ra;

import java.util.ArrayList;
import java.util.List;

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
	public double getConfidence(List<Transaction> transactions) {
		double nom, denom;
		nom = denom = 0;
		
		Itemset numerator = (new Itemset(this.antecedent)).getUnion(new Itemset(this.consequent));
		Itemset denominator = new Itemset(this.antecedent);

		for(Transaction transaction : transactions) {
			if(transaction.contains(numerator))
				nom++;
			if(transaction.contains(denominator))
				denom++;
		}

		return (nom/denom);
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

	/**
	 * Tests
	 * @param args
	 */
	@SuppressWarnings("serial")
	public static void main(String[] args) {
		
		// Transaction definitions
		List<Transaction> transactions = new ArrayList<Transaction>();
		List<Integer> t1 = new ArrayList<Integer>() {{
			add(1); add(3); add(4);
		}};
		transactions.add(new Transaction(t1));
		List<Integer> t2 = new ArrayList<Integer>() {{
			add(2); add(3); add(5);
		}};
		transactions.add(new Transaction(t2));
		List<Integer> t3 = new ArrayList<Integer>() {{
			add(1); add(2); add(3); add(5);
		}};
		transactions.add(new Transaction(t3));
		List<Integer> t4 = new ArrayList<Integer>() {{
			add(2); add(5);
		}};
		transactions.add(new Transaction(t4));
		
		// Rule antecedent and consequent definitions
		ArrayList<Integer> leftPart = new ArrayList<Integer>() {{
			add(1); add(2); add(3); 
		}};
		ArrayList<Integer> rightPart = new ArrayList<Integer>() {{
			add(4);
		}};
		
		// Rule definition
		Rule rule = new Rule(leftPart, rightPart);
		System.out.println(rule);
		
		// Confidence test
		double confidence = rule.getConfidence(transactions);
		System.out.println("Rule confidence: "+confidence);

		// deriveRules() test
		System.out.println(rule.deriveRules().isEmpty());
		
		// deriveRules() test
		for(Rule r : rule.deriveRules()) {
			System.out.println(r);
		}

	}
}
