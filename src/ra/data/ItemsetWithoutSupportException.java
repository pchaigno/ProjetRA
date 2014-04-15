package ra.data;

public class ItemsetWithoutSupportException extends Exception {
	private static final long serialVersionUID = -6226788061846745023L;

	/**
	 * Constructor
	 * @param message The exception message.
	 */
	public ItemsetWithoutSupportException(String message) {
		super(message);
	}
}
