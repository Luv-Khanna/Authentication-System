
/**
 * User class is the class whose objects are created to store the data of the user
 * The data is retrived using setter and getter methods
 * @author Luv Khanna
 *
 */
public class User 
{
	private String username,hash_password,fullname,email,last_login;
	private long phone_number;
	private int fail_count;
	private boolean account_locked;
	
	/**
	 * A constructor to initialize all the variables of the object of each User
	 * @param un
	 * 			The user name of the user
	 * @param hp
	 * 			The hashed password of the user
	 * @param fn
	 * 			The full name of the user
	 * @param e
	 * 			The email of the user
	 * @param pn
	 * 			The phone number of the user
	 * @param fc
	 * 			A counter to check the failed login attempts of the user
	 * @param ll
	 * 			The last login date of the user
	 * @param al
	 * 			A flag to check whether the user account is locked or not
	 */
	public User(String un,String hp,String fn,String e,long pn,int fc,String ll,boolean al)
	{
		username=un;
		hash_password=hp;
		fullname=fn;
		email=e;
		phone_number=pn;
		fail_count=fc;
		last_login=ll;
		account_locked=al;
	}
	/**
	 * A getter method 
	 * @return the user name of the user
	 */
	public String get_username()
	{
		return username;
	}
	/**
	 * A setter method
	 * @param un
	 * 			the user name of the user
	 */
	public void set_username(String un)
	{
		username=un; 
	}
	/**
	 * A getter method 
	 * @return the hash password of the user
	 */
	public String get_hash_password()
	{
		return hash_password;
	}
	
	/**
	 * A setter method
	 * @param hp
	 * 			the hash password of the user
	 */
	public void set_hash_password(String hp)
	{
		hash_password=hp; 
	}
	/**
	 * A getter method
	 * @return the full name of the user
	 */
	public String get_fullname()
	{
		return fullname;
	}
	/**
	 * A setter method
	 * @param fn
	 * 			the full name of the user
	 */
	public void set_fullname(String fn)
	{
		fullname=fn; 
	}
	/**
	 * A getter method
	 * @return the email of the user
	 */
	public String get_email()
	{
		return email;
	}
	/**
	 * A setter method
	 * @param e
	 * 			the email of the user
	 */
	public void set_email(String e)
	{
		email=e; 
	}
	/**
	 * A getter method
	 * @return the phone number of the user
	 */
	public long get_phone_number()
	{
		return phone_number;
	}
	/**
	 * A setter method
	 * @param pn
	 * 			the phone number of the user
	 */
	public void set_phone_number(long pn)
	{
		phone_number=pn; 
	}
	/**
	 * A getter method
	 * @return the count of failed login attempts of the user
	 */
	public int get_fail_count()
	{
		return fail_count;
	}
	/**
	 * A setter method
	 * @param fc
	 * 			the count of failed login attempts of the user
	 */
	public void set_fail_count(int fc)
	{
		fail_count=fc; 
	}
	/**
	 * A getter method
	 * @return the last login date of the user
	 */
	public String get_last_login()
	{
		return last_login;
	}
	/**
	 * A setter method
	 * @param ll
	 * 			the last login date of the user
	 */
	public void set_last_login(String ll)
	{
		last_login=ll; 
	}
	/**
	 * A getter method
	 * @return a flag representing whether the user account is locked or not 
	 */
	public boolean get_account_locked()
	{
		return account_locked;
	}
	/**
	 * A setter method
	 * @param al
	 * 			a flag representing whether the user account is locked or not
	 */
	public void set_account_locked(boolean al)
	{
		account_locked=al; 
	}
}
