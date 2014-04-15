package ra.algo;

import java.util.ArrayList;
import java.util.List;

import ra.data.Database;
import ra.data.Item;
import ra.data.Itemset;

public class Rule {
	private ArrayList<Item> antecedent;
	private ArrayList<Item> consequent;

	/**
	 * Empty-parameter constructor
	 */
	public Rule() {
		this.antecedent = new ArrayList<Item>();
		this.consequent = new ArrayList<Item>();
	}

	/**
	 * Constructor
	 * @param antecedent The antecedent of the rule
	 * @param consequent The consequent of the rule
	 */
	public Rule(ArrayList<Item> antecedent, ArrayList<Item> consequent) {
		this.antecedent = antecedent;
		this.consequent = consequent;
	}

	/**
	 * 
	 * @param transactions The transactions associated with the rule
	 * @return The confidence of the rule
	 */
	public double calcConfidence(Database database) {
		List<Itemset> itemsets = new ArrayList<Itemset>();
		
		// Itemset formation to compute rule confidence
		Itemset numerator = (new Itemset(this.antecedent)).getUnion(new Itemset(this.consequent));
		Itemset denominator = new Itemset(this.antecedent);
		
		// Support confidence
		itemsets.add(numerator);
		itemsets.add(denominator);
		database.calcSupport(itemsets);
		
		return numerator.getSupport()*1.0/denominator.getSupport();
	}
	
	/**
	 * Accessor to the antecedent
	 * @return The antecedent
	 */
	public ArrayList<Item> getAntecedent() {
		return this.antecedent;
	}
	
	/**
	 * Accessor to the consequent
	 * @return The consequent
	 */
	public ArrayList<Item> getConsequent() {
		return this.consequent;
	}

	/**
	 * @param value The value to add to the rule as antecedent
	 */
	public void addToAntecedent(Item value) {
		this.antecedent.add(value);
	}

	/**
	 * @param value The value to add to the rule as consequent
	 */
	public void addToConsequent(Item value) {
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
			for(Item i: this.antecedent) {
				ArrayList<Item> derivedAntecedent = new ArrayList<Item>(this.antecedent);
				ArrayList<Item> derivedConsequent = new ArrayList<Item>(this.consequent);
				derivedAntecedent.remove(i);
				derivedConsequent.add(i);

				derivedRules.add(new Rule(derivedAntecedent, derivedConsequent));
			}
		}

		return derivedRules;
	}

	@Override
	public String toString() {
		String rule = "Rule: ";
		for(Item item: this.antecedent) {
			rule += item+" ";
		}
		rule += "-> ";
		for(Item item: this.consequent) {
			rule += item+" ";
		}
		return rule;
	}
}
