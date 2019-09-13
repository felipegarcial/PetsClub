package exceptions;

public class ExceptionNoRepeatNamePetOfOwner extends Exception{

	/**
	 * Error generated where name of pet of owner already exist
	 * @param msg
	 */
	public ExceptionNoRepeatNamePetOfOwner(String msg) {
		super(msg);
	}
}
