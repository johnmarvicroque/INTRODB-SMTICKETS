
public class Customer {
	private String CustomerID;
	private String Firstname;
	private String Lastname;
	private String custCity;
	private String Email;
	private String ContactNumber;
	private String Username;
	private String Password;
	

	public static final String TABLE = "customer";
	public static final String COL_ID = "CustomerID";
	public static final String COL_NAME = "FirstName";
	public static final String COL_LASTNAME = "LastName";
	public static final String COL_custCITY = "CustomerCity";
	public static final String COL_Email = "CustomerEmail";
	public static final String COL_MOBILE = "ContactNumber";
	public static final String COL_USER = "Username";
	public static final String COL_PASS = "Password";
	
//	public Customer(String CustomerID, String Firstname, String Lastname, String custCity, String Email, String Username,String Password) {
//		this.CustomerID = CustomerID;
//		this.Firstname = Firstname;
//		this.Lastname = Lastname;
//		this.custCity = custCity;
//		this.Email = Email;
//		this.Username = Username;
//		this.Password = Password;
//	}

	public String getCustomerID() {
		return CustomerID;
	}

	public String getFirstname() {
		return Firstname;
	}

	public String getLastname() {
		return Lastname;
	}

	public String getCustCity() {
		return custCity;
	}

	public String getEmail() {
		return Email;
	}

	public String getUsername() {
		return Username;
	}

	public String getPassword() {
		return Password;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public void setFirstname(String firstname) {
		Firstname = firstname;
	}

	public void setLastname(String lastname) {
		Lastname = lastname;
	}

	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}
	
	
	
//	public String toString() {
//		return "Contact [id=" + CustomerID + ", FirstName=" + this.Firstname + ", mobile=" + mobile + ", email=" + email + ", group=" + group
//				+ "]";
//	}
	
	
	
}
