package exceptions;

public class ExceptionOwnerExist extends Exception{
	/**
	 * Error generated when no exist Club Id
	 * @param msg
	 */
	public ExceptionOwnerExist(String msg) {
		super(msg);
	}
}

