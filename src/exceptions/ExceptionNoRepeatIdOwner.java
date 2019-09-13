package exceptions;

public class ExceptionNoRepeatIdOwner extends Exception {
	/**
	 * Error generated where id owner already exist
	 * @param msg
	 */
	public ExceptionNoRepeatIdOwner(String msg) {
		super(msg);
	}
}
