import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.json.simple.*;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Application class has the main method to control the flow of the program.
 * It also implements the Hash and Password interfaces for it's use 
 * @author Luv Khanna
 *
 */
public class Application implements Hash,Password
{
	private static ArrayList<Object> user_records= new ArrayList<Object>();
	private static Application a=new Application();
	private static Scanner sc=new Scanner(System.in);
	public static void main(String[] args) throws IOException
	{
		File file=new File("User.txt");
		JSONArray jsonarray = new JSONArray();
		if(file.exists())
		{
			JSONParser parser = new JSONParser();
			 try 
			 {
				Object obj = parser.parse(new FileReader("User.txt"));
		        JSONObject jsonObject = (JSONObject) obj;
		        JSONArray user_array= (JSONArray) jsonObject.get("user_array");
		        for(int i=0;i<user_array.size();i++)
		        {
		        	JSONObject usero= (JSONObject)user_array.get(i);
		        	String username=(String)usero.get("username");
		        	username=username.trim();
		        	String hashp=(String)usero.get("hash_password");
		        	String fname=(String)usero.get("Full Name");
		        	String email=(String)usero.get("Email");
		        	long pn=(Long)usero.get("Phone number");
		        	long fc_long=(Long)usero.get("Fail count");
		        	int fc=(int)fc_long;
		        	String lld=(String)usero.get("Last Login Date");
		        	boolean al=(Boolean)usero.get("Account locked");
		        	user_records.add(new User(username,hashp,fname,email,pn,fc,lld,al));
		        }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		else
		{
			
		}
		
		
		a.displaymenu();
		System.out.print("Please enter your command (1-4, or 0 to terminate the system): ");
		boolean flag=true;
		while(flag)
		{
			int choice=sc.nextInt();
			if(choice==1)
			{
				a.authenticateUser();
			}
			else if(choice==2)
			{
				a.addUser();
			}
			else if(choice==3)
			{
				a.editUser();
			}
			else if(choice==4)
			{
				a.resetPassword();
			}
			else if(choice==0)
			{
				flag=false;
			}
			if(flag) 
			{
				System.out.print("Please enter your command (1-4, or 0 to terminate the system): ");
			}
		}
		file.createNewFile();
		int recordlength=user_records.size();
		for(int i=0;i<recordlength;i++)
		{
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("username",((User)user_records.get(i)).get_username());
			jsonobject.put("hash_password",((User)user_records.get(i)).get_hash_password());
			jsonobject.put("Full Name",((User)user_records.get(i)).get_fullname());
			jsonobject.put("Email",((User)user_records.get(i)).get_email());
			jsonobject.put("Phone number",((User)user_records.get(i)).get_phone_number());
			jsonobject.put("Fail count",((User)user_records.get(i)).get_fail_count());
			jsonobject.put("Last Login Date",((User)user_records.get(i)).get_last_login());
			jsonobject.put("Account locked",((User)user_records.get(i)).get_account_locked());
			jsonarray.add(jsonobject);
		}
		JSONObject jsonobject2 = new JSONObject();
		jsonobject2.put("user_array",jsonarray);
		StringWriter out=new StringWriter();
		jsonobject2.writeJSONString(out);
		String jsonText = out.toString();
		PrintStream printwriter=new PrintStream(new FileOutputStream(file,false));
		printwriter.println(jsonText);
		printwriter.close();
		//FileWriter filewriter = new FileWriter(file);
		//filewriter.write(jsonText);
		//filewriter.flush();
		//filewriter.close();
	}
	/**
	 * A method to display the initial menu to the user
	 */
	public void displaymenu()
	{
		System.out.println("Welcome to the COMP2396 Authentication system!");
		System.out.println("1. Authenticate user");
		System.out.println("2. Add user record");
		System.out.println("3. Edit user record");
		System.out.println("4. Reset user password");
		System.out.println("What would you like to perform?");
	}
	/**
	 * A method to add a new user to the system
	 */
	public void addUser()
	{
		sc.nextLine(); 
		System.out.print("Please enter your username: ");
		String username=sc.nextLine();
		username=username.trim();
		String password="",repassword="";
		boolean flag=false;
		while(!flag)
		{
			boolean validpassword=false;
			while(!validpassword)
			{
				System.out.print("Please enter your password: ");
				password=sc.nextLine();
				validpassword=a.validitycheck(password);
				if(!validpassword)
				{
					System.out.println("Your password has to fulfil: at least 1 small letter, 1 capital letter, 1 digit!");
				}
			}
			System.out.print("Please re-enter your password: ");
			repassword=sc.nextLine();
			flag=a.matchingpasswords(password,repassword);
			if(!flag)
			{
				System.out.print("Password not match! ");
			}
		}
		String hash_password=hashfunction(password);  
		System.out.print("Please enter your full name: ");
		String fullname=sc.nextLine();
		System.out.print("Please enter your email address: ");
		String email=sc.nextLine();
		System.out.print("Please enter your Phone number: ");
		long phone_number=sc.nextLong();
		String last_login=java.time.LocalDate.now().toString();
		user_records.add(new User(username,hash_password,fullname,email,phone_number,0,last_login,false));
		System.out.println("Record added successfully!");
	}
	public boolean validitycheck(String password)
	{
		boolean validity=false;
		boolean digit=false;
		boolean uppercase=false;
		boolean lowercase=false;
		boolean lflag=false;
		int length=password.length();
		if(length>=6)
		{
			lflag=true;
		}
		for(int i=0;i<length;i++)
		{
			if(Character.isDigit(password.charAt(i)))
			{
				digit=true;
			}
			if(Character.isUpperCase(password.charAt(i)))
			{
				uppercase=true;
			}
			if(Character.isLowerCase(password.charAt(i)))
			{
				lowercase=true;
			}
		}
		if(digit && uppercase && lowercase && lflag)
		{
			validity=true;
		}
		return validity;
	}
	public boolean matchingpasswords(String password, String repassword)
	{
		boolean match=false;
		if(password.equals(repassword))
		{
			match=true;
		}
		return match;
	}
	//reference for the hashfunction() code was taken from online
	public String hashfunction(String password)
	{
		
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(password.getBytes());
		byte[] hash=md.digest();
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[hash.length * 2];
	    for ( int j = 0; j < hash.length; j++ ) 
	    {
	        int v = hash[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    String hash_password=new String(hexChars);
	    hash_password=hash_password.toLowerCase();
	    return hash_password;
	}
	/**
	 * A method to allow the user to edit his details like password, full name and email
	 */
	public void editUser()
	{
		sc.nextLine();
		int index=loginattempt();
		if(index==-1)
		{
			
		}
		else
		{
			String password="",repassword="";
			boolean flag=false;
			while(!flag)
			{
				boolean validpassword=false;
				while(!validpassword)
				{
					System.out.print("Please enter your new password: ");
					password=sc.nextLine();
					validpassword=a.validitycheck(password);
					if(!validpassword)
					{
						System.out.println("Your password has to fulfil: at least 1 small letter, 1 capital letter, 1 digit!");
					}
				}
				System.out.print("Please re-enter your new password: ");
				repassword=sc.nextLine();
				flag=a.matchingpasswords(password,repassword);
				if(!flag)
				{
					System.out.print("Password not match! ");
				}
			}
			String hash_password=hashfunction(password);  
			System.out.print("Please enter your new full name: ");
			String fullname=sc.nextLine();
			System.out.print("Please enter your new email address: ");
			String email=sc.nextLine();
			((User)user_records.get(index)).set_hash_password(hash_password);
			((User)user_records.get(index)).set_fullname(fullname);
			((User)user_records.get(index)).set_email(email);
			System.out.println("Record update successfully!");
		}
	}
	/**
	 * A method to allow the user to login into his account. 
	 * This method locks the account if the user cannot login with in 3 attempts
	 * If successfully logged in it updates the login date of the user as well
	 * @return returns the index position of the user account in the user_records array list 
	 */
	public int loginattempt()
	{
		String uname="";
		String upass="";
		String uhash="";
		int index=-1;
		boolean flag=false;
		while(!flag)
		{
			System.out.print("Please enter your username: ");
			uname=sc.nextLine();
			int unamesearch=user_index(uname);
			if(unamesearch==-1)
			{
				System.out.println("username not found!");
				break;
			}
			uname=uname.trim();
			System.out.print("Please enter your password: ");
			upass=sc.nextLine();
			uhash=a.hashfunction(upass);
			index=user_index(uname);
			boolean passwordmatch=checkpassword(uname,uhash);
			if(passwordmatch && ((User)user_records.get(index)).get_fail_count()<=2)
			{
				System.out.println("Login success! Hello "+((User)user_records.get(index)).get_username()+"!");
				int count=0;
				((User)user_records.get(index)).set_fail_count(count);
				String lastlogin=java.time.LocalDate.now().toString();
				((User)user_records.get(index)).set_last_login(lastlogin);
				flag=true;
			}
			else if(!passwordmatch && ((User)user_records.get(index)).get_fail_count()<2)
			{
				System.out.println("Login failed!");
				int count=((User)user_records.get(index)).get_fail_count()+1;
				((User)user_records.get(index)).set_fail_count(count);
			}
			else if(!passwordmatch && ((User)user_records.get(index)).get_fail_count()>=2)
			{
				int count=((User)user_records.get(index)).get_fail_count()+1;
				((User)user_records.get(index)).set_fail_count(count);
				((User)user_records.get(index)).set_account_locked(true);
				System.out.println("Login failed! Your account has been locked!");
				flag=true;
				index=-1;
			}
			else if(((User)user_records.get(index)).get_fail_count()>=3)
			{
				int count=((User)user_records.get(index)).get_fail_count()+1;
				((User)user_records.get(index)).set_fail_count(count);
				System.out.println("Login failed! Your account has been locked!");
				((User)user_records.get(index)).set_account_locked(true);
				flag=true;
				index=-1;
				
			}
		}
		return index;
	}
	/**
	 * This method validates if the user can login successfully or not by calling the login attempt method
	 */
	public void authenticateUser()
	{
		sc.nextLine(); 
		int index=loginattempt();
	}
	/**
	 * A method to give the index position of the user account in the user_records array list
	 * @param uname
	 * 				the user name to be found
	 * @return the index position of the user account in the user_records array list
	 */
	public int user_index(String uname)
	{
		uname=uname.trim();
		int index=-1;
		int length=user_records.size();
		for(int i=0;i<length;i++)
		{
			if(((User)user_records.get(i)).get_username().equals(uname))
			{
				index=i;
				break;
			}
		}
		return index;
	}
	/**
	 * A method to check if the user enters the correct password or not
	 * @param uname
	 * 				the user name 
	 * @param uhash
	 * 				the hash code of the passworded entered by the user 
	 * @return a boolean value indicating whether the user enters the correct password or not
	 */
	public boolean checkpassword(String uname,String uhash)
	{
		boolean attempt=false;
		int index=user_index(uname);
		if(((User)user_records.get(index)).get_hash_password().equals(uhash))
		{
			attempt=true;
		}
		return attempt;
	}
	/**
	 * A method to allow the administrator to reset the password of a user 
	 * and also reset the fail count and account locked flag to the appropriate values
	 */
	public void resetPassword()
	{
		sc.nextLine(); 
		boolean admin=a.checkAdmin();
		if(admin)
		{
			boolean passwordmatch=false;
			while(!passwordmatch)
			{
				System.out.print("Please enter the password of administrator: ");
				String upass=sc.nextLine();
				String uhash=a.hashfunction(upass);
				passwordmatch=checkpassword("administrator",uhash);
				if(!passwordmatch)
				{
					System.out.println("Incorrect administrator password!");
				}
			}
			int userindex=-1;
			boolean userfound=false;
			while(!userfound)
			{
				System.out.print("Please enter the user account need to reset: ");
				String useraccount=sc.nextLine();
				useraccount=useraccount.trim();
				userindex=a.user_index(useraccount);
				if(userindex==-1)
				{
					System.out.println("user account not found!");
				}
				else
				{
					userfound=true;
				}
			}
			String password="",repassword="";
			boolean flag=false;
			while(!flag)
			{
				boolean validpassword=false;
				while(!validpassword)
				{
					System.out.print("Please enter the new password: ");
					password=sc.nextLine();
					validpassword=a.validitycheck(password);
					if(!validpassword)
					{
						System.out.println("Your password has to fulfil: at least 1 small letter, 1 capital letter, 1 digit!");
					}
				}
				System.out.print("Please re-enter the new password: ");
				repassword=sc.nextLine();
				flag=a.matchingpasswords(password,repassword);
				if(!flag)
				{
					System.out.print("Password not match! ");
				}
			}
			String hash_password=hashfunction(password);  
			((User)user_records.get(userindex)).set_hash_password(hash_password);
			((User)user_records.get(userindex)).set_fail_count(0);
			((User)user_records.get(userindex)).set_account_locked(false);
			System.out.println("Password update successfully!");
		}
		else 
		{
			a.createAdmin();
		}
	}
	/**
	 * A method to check whether an administrator account exists or not
	 * @return a boolean value to indicate whether an administrator account exists or not 
	 */
	public boolean checkAdmin()
	{
		boolean admin=false;
		int length=user_records.size();
		for(int i=0;i<length;i++)
		{
			if(((User)user_records.get(i)).get_username().equals("administrator"))
			{
				admin=true;
				break;
			}
		}
		return admin;
	}
	/**
	 * A method to create an administrator account
	 */
	public void createAdmin()
	{
		System.out.println("Administrator account not exist, please create the administrator account by setting up a password for it.");
		String password="",repassword="";
		boolean flag=false;
		while(!flag)
		{
			boolean validpassword=false;
			while(!validpassword)
			{
				System.out.print("Please enter the password: ");
				password=sc.nextLine();
				validpassword=a.validitycheck(password);
				if(!validpassword)
				{
					System.out.println("Your password has to fulfil: at least 1 small letter, 1 capital letter, 1 digit!");
				}
			}
			System.out.print("Please re-enter the password: ");
			repassword=sc.nextLine();
			flag=a.matchingpasswords(password,repassword);
			if(!flag)
			{
				System.out.print("Password not match! ");
			}
		}
		String hash_password=hashfunction(password);  
		String last_login=java.time.LocalDate.now().toString();
		user_records.add(new User("administrator",hash_password,"administrator","",0,0,last_login,false));
		System.out.println("Administrator account created successfully!");
	}
}
