package exceptions;

public class ExceptionExistClub extends Exception{
	/**
	 * Error generated when no exist Club Id
	 * @param msg
	 */
	public ExceptionExistClub(String msg) {
		super(msg);
	}
}

