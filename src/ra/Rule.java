package ra;

import java.util.ArrayList;
import java.util.List;

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

	public double getConfidence(List<Transaction> transactions) {
		double nom, denom;
		nom = denom = 0;
		Itemset numerator = (new Itemset(this.leftPart)).getUnion(new Itemset(this.rightPart));
		Itemset denominator = new Itemset(this.leftPart);
		
		for(Transaction transaction : transactions) {
			if(transaction.contains(numerator))
				nom++;
			if(transaction.contains(denominator))
				denom++;
		}
		
		return (nom/denom);
	}

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
	
	/**
	 * Tests
	 * @param args
	 */
	@SuppressWarnings("serial")
	public static void main(String[] args) {
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
		
		ArrayList<Integer> leftPart = new ArrayList<Integer>() {{
			add(1);
		}};
		ArrayList<Integer> rightPart = new ArrayList<Integer>() {{
			add(2);
		}};
		Rule rule = new Rule(leftPart, rightPart);
		double confidence = rule.getConfidence(transactions);
		
		System.out.println("Rule confidence: "+confidence);

	}
}
