
/**
 * Hash interface allows classes to convert a password into a unique hash code
 * @author Luv Khanna
 */
public interface Hash 
{
	/**
	 * A method to convert a password into a unique hash code
	 * @param password
	 * 					the password that has to be hashed
	 * @return the unique hash code of the password
	 */
	public abstract String hashfunction(String password);
}
