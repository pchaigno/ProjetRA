package ra.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import ra.data.Database;
import ra.data.Item;
import ra.data.Itemset;

public class APriori {
	protected Database database;
	protected List<List<Itemset>> itemsets;
	protected boolean completeSupportCalc;
	
	/**
	 * Constructor
	 * @param database The database containing the transactions.
	 */
	public APriori(Database database) {
		this.database = database;
		this.itemsets = new ArrayList<List<Itemset>>();
		this.completeSupportCalc = true;
	}
	
	/**
	 * A Priori algorithm to compute the k-itemsets.
	 * @param minSupport The minimum support to keep an itemset.
	 * @return All the computed k-itemsets.
	 */
	public List<List<Itemset>> aPriori(int minSupport) {
		this.init1Itemset(minSupport);
		boolean noMoreItemsets = false;
		for(int i=1; !noMoreItemsets; i++) {
			List<Itemset> newItemsets = this.calcK1Itemset(itemsets.get(i-1), minSupport);
			if(newItemsets.size() == 0) {
				noMoreItemsets = true;
			} else {
				this.itemsets.add(newItemsets);
			}
		}
		
		// If the support calculation was incomplete we need to update it:
		if(!this.completeSupportCalc) {
			updateSupport();
		}
		
		return this.itemsets;
	}
	
	/**
	 * Updates the support of the itemsets found.
	 * This can be usefull for maximum and closed itemsets because we don't compute completly the support the first time.
	 * Indeed, some of the itemsets on which we compute the support will then be removed (non-closed or non-maximum).
	 */
	private void updateSupport() {
		for(int level=0; level<this.itemsets.size(); level++) {
			this.database.updateSupport(this.itemsets.get(level));
		}
	}
	
	/**
	 * Computes the 1-itemsets from the transactions.
	 * @param minSupport The minimum support to keep an itemset.
	 */
	protected void init1Itemset(int minSupport) {
		Set<Item> items = this.database.retrieveItems();
		
		// Generates the 1-itemsets:
		List<Itemset> itemsets = new Vector<Itemset>();
		for(Item item: items) {
			Itemset itemset = new Itemset();
			itemset.add(item);
			itemsets.add(itemset);
		}
		
		// Checks the support of all itemsets:
		List<Itemset> frequentItemsets = this.database.withMinSupport(itemsets, minSupport, this.completeSupportCalc);
		
		this.itemsets.add(frequentItemsets);
	}
	
	/**
	 * Computes the k+1-itemsets from the k-itemsets.
	 * @param itemsets The k-itemsets.
	 * @param minSupport The minimum support to keep a k+1-itemset.
	 * @return The k+1-itemsets.
	 */
	protected List<Itemset> calcK1Itemset(List<Itemset> itemsets, int minSupport) {
		List<Itemset> candidates = new Vector<Itemset>();
		
		// Generates candidates of size k+1 for k-itemsets:
		for(int i=0; i<itemsets.size(); i++) {
			for(int j=i+1; j<itemsets.size(); j++) {
				candidates.addAll(itemsets.get(i).calcItemsetsK1(itemsets.get(j)));
			}
		}
		
		// Checks that all subsets of each candidate are frequent:
		for(int i=0; i<candidates.size(); i++) {
			if(!allSubItemsetsFrequent(itemsets, candidates.get(i))) {
				candidates.remove(i);
			}
		}
		
		// Checks support for all candidates:
		List<Itemset> frequentItemsets = this.database.withMinSupport(candidates, minSupport, this.completeSupportCalc);
		
		return frequentItemsets;
	}
	
	/**
	 * Checks that all k-itemsets of a k+1-itemset are frequent.
	 * @param itemsets The k-itemsets.
	 * @param itemset The k+1-itemsets.
	 * @return True if all k-itemsets of the k+1-itemsets are frequent.
	 */
	protected static boolean allSubItemsetsFrequent(List<Itemset> itemsets, Itemset itemset) {
		List<Itemset> subItemsets = itemset.calcSubItemsets();
		for(Itemset subItemset: subItemsets) {
			if(!itemsets.contains(subItemset)) {
				return false;
			}
		}
		return true;
	}

		/**
	 * Generates the rules associated with itemsets generated by the first step of Apriori
	 * @param minConfidence The required minimum confidence to keep rules during the process
	 * @return The generated rules
	 */
	public List<Rule> generateRules(double minConfidence) {
		List<Rule> generatedRules = new ArrayList<Rule>();

		// Generates every possible rule for each itemset:
		// Computes confidence for each one and add it if greater than minConfidence.
		for(int level=1; level<this.itemsets.size(); level++) {
			for(Itemset itemset: this.itemsets.get(level)) {
				for(Rule rule: itemset.generateSimpleRules()) {
					if(rule.calcConfidence(this.database) >= minConfidence) {
						generatedRules.add(rule);
						
						// step 3 of rules generation : recursive rule generation
						ArrayList<Rule> derivedRules = rule.deriveRules();
						ArrayList<Rule> next = new ArrayList<Rule>();
						Boolean started = false;

						while(!next.isEmpty() || !started) {
							// The first time we want to keep the original derived rules
							if(started) {
								derivedRules = new ArrayList<Rule>(next);
							}
							
							// Executed at the first iteration
							if(!started) {
								started = true;
							}
							
							next.clear();
							
							for(Rule derivedRule: derivedRules) {
								if(derivedRule.calcConfidence(this.database) >= minConfidence) {
									generatedRules.add(derivedRule);
									
									for(Rule newDerivedRule: derivedRule.deriveRules()) {
										next.add(newDerivedRule);
									}
								}
							}
						}
					}
				}
			}
		}
		return generatedRules;
	}
	
}