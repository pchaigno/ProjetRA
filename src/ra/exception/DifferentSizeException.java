package ra.exception;

public class DifferentSizeException extends Exception {

	public DifferentSizeException() {
		
	}

	@Override
	public String getMessage() {
		return "Two lists have not the same size";
	}
	
	
}
