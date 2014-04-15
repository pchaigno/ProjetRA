package ra.data;

import java.util.ArrayList;
import java.util.List;


public class Rule {
	private Itemset antecedent;
	private Itemset consequent;
	private Itemset numerator;

	/**
	 * Constructor
	 * @param antecedent The antecedent of the rule
	 * @param consequent The consequent of the rule
	 */
	public Rule(Itemset antecedent, Itemset consequent) {
		this.antecedent = antecedent;
		this.consequent = consequent;
		this.numerator = this.consequent.getUnion(this.antecedent);
	}
	
	/**
	 * Accessor to the antecedent.
	 * @return The antecedent.
	 */
	public Itemset getAntecedent() {
		return this.antecedent;
	}
	
	/**
	 * Accessor to the consequent.
	 * @return The consequent.
	 */
	public Itemset getConsequent() {
		return this.consequent;
	}
	
	/**
	 * Accessor to the numerator.
	 * @return The numerator.
	 */
	public Itemset getNumerator() {
		return this.numerator;
	}

	/**
	 * Computes the confidence of the rule.
	 * @return The confidence.
	 * @throws ItemsetWithoutSupportException If one of the itemsets doesn't have asupport value.
	 */
	public double getConfidence() throws ItemsetWithoutSupportException {
		if(this.numerator.getSupport()==0) {
			throw new ItemsetWithoutSupportException(this.numerator+" doesn't have a support value.");
		}
		if(this.antecedent.getSupport()==0) {
			throw new ItemsetWithoutSupportException(this.antecedent+" doesn't have a support value.");
		}
		return 1.0 * numerator.getSupport() / this.antecedent.getSupport();
	}

	/**
	 * Derive the k+1 consequent rules from a given k consequent rule by transferring
	 * each antecedent item as consequent
	 * @return The derived rules
	 */
	public List<Rule> deriveRules() {
		List<Rule> derivedRules = new ArrayList<Rule>();

		// The rule must have at least an antecedent of size two to derive rules
		if(this.antecedent.size() >= 2) {
			for(Item i: this.antecedent.getItems()) {
				Itemset derivedAntecedent = this.antecedent.clone();
				Itemset derivedConsequent = this.consequent.clone();
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
		for(Item item: this.antecedent.getItems()) {
			rule += item+" ";
		}
		rule += "-> ";
		for(Item item: this.consequent.getItems()) {
			rule += item+" ";
		}
		return rule;
	}
}
