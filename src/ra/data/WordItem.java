package ra.data;

public class WordItem extends Item {
	private static int nbWordItems = 0;
	private String word;
	
	/**
	 * Constructor
	 * Assign an id to the item depending on the number of word items created.
	 * @param word The word.
	 */
	public WordItem(String word) {
		super(nbWordItems + 1);
		nbWordItems++;
		this.word = word;
	}

	@Override
	public String toString() {
		return this.word;
	}
}
