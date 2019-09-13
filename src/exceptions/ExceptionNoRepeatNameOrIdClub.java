package exceptions;

public class ExceptionNoRepeatNameOrIdClub extends Exception{
	/**
	 * Error generated when name or id of club already exist
	 * @param msg
	 */
	public ExceptionNoRepeatNameOrIdClub(String msg) {
		super(msg);
	}
}
