
public class Employees {
	public String Username;
	public String Password;
	public String Email;
	public String Firstname;
	public String Lastname;
	public String Contactnumber;
	

	public static final String TABLE = "employees";
	public static final String COL_USER = "Username";
	public static final String COL_PASS = "Password";
	public static final String COL_NAME = "FirstName";
	public static final String COL_LASTNAME = "LastName";
	public static final String COL_Email = "Email";
	public static final String COL_MOBILE = "ContactNumber";
	
	
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getFirstname() {
		return Firstname;
	}
	public void setFirstname(String firstname) {
		Firstname = firstname;
	}
	public String getLastname() {
		return Lastname;
	}
	public void setLastname(String lastname) {
		Lastname = lastname;
	}
	public String getContactnumber() {
		return Contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		Contactnumber = contactnumber;
	}
}
