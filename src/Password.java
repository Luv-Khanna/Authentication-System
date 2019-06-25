
/**
 * Password interface allows classes to check the validity of the password and 
 * whether the two passowrds enter match or not
 * @author Luv Khanna
 *
 */
public interface Password 
{
	/**
	 * A method to check if the password is valid or not
	 * @param password
	 * 					the password to be checked 
	 * @return the validity of the password as a boolean value 
	 */
	public abstract boolean validitycheck(String password);
	
	/**
	 * A method to check if the two passwords match or not
	 * @param password
	 * 				the 1st password entered by the user
	 * @param repassword
	 * 				the re-entered password by the user
	 * @return boolean value representing whether the passwords match or not
	 */
	public abstract boolean matchingpasswords(String password, String repassword);
}
