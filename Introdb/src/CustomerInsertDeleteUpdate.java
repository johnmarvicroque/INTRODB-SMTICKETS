
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.*;
public class CustomerInsertDeleteUpdate extends JFrame {
	ArrayList<Customer> customers = new ArrayList<Customer>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Customer customer = new Customer();
	private JTextField tfCustID;
	private JTextField tfFN;
	private JTextField tfLN;
	private JTextField tfCCity;
	private JTextField tfEmail;
	private JTextField tfUN;
	private JTextField tfPass;
	private JTextField tfCNum;
	
	
	private Customer toCustomer(ResultSet rs) throws SQLException{
		Customer customer = new Customer();
		
		customer.setCustomerID(rs.getString(Customer.COL_ID));
		customer.setFirstname(rs.getString(Customer.COL_NAME));
		customer.setLastname(rs.getString(Customer.COL_LASTNAME));
		customer.setCustCity(rs.getString(Customer.COL_custCITY));
		customer.setEmail(rs.getString(Customer.COL_Email));
		customer.setContactNumber(rs.getString(Customer.COL_MOBILE));
		customer.setUsername(rs.getString(Customer.COL_USER));
		customer.setPassword(rs.getString(Customer.COL_PASS));
		
		
		return customer;
	}
	

	public boolean deleteCustomer() {
		customer.setCustomerID(tfCustID.getText());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						Customer.TABLE + " WHERE " + Customer.COL_ID + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, customer.getCustomerID());
			System.out.println(query);
			statement.executeUpdate();
			
			statement.close();
			connect.close();
			
			System.out.println("[CUSTOMER/s] DELETE SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[CUSTOMER/s] DELETE FAILED!");
			ev.printStackTrace();
			return false;
		}
		
		customers = this.getAll();
		
		showTable(customers);
		return true;
	}
	
	public ArrayList<Customer> getAll() {
		ArrayList <Customer> customers = new ArrayList <Customer> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + Customer.TABLE;
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				customers.add(toCustomer(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[CUSTOMER/S] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[CUSTOMER/S] SELECT FAILED!");
			return null;
		}	
		
		return customers;
	}
	
	public boolean addCustomer() {
		Connection connect = msc.getConnection();
		
		customer.setCustomerID(tfCustID.getText());
		customer.setFirstname(tfFN.getText());
		customer.setLastname(tfLN.getText());
		customer.setCustCity(tfCCity.getText());
		customer.setContactNumber(tfCNum.getText());
		customer.setEmail(tfEmail.getText());
		customer.setUsername(tfUN.getText());
		customer.setPassword(tfPass.getText());
		
		String query = 	"INSERT INTO " + 
						Customer.TABLE + "(`CustomerID`, `FirstName`, `LastName`, `CustomerCity`, `CustomerEmail`, `ContactNumber`, `Username`, `Password`)" +
						" VALUES(?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, tfCustID.getText());
			statement.setString(2, tfFN.getText());
			statement.setString(3, tfLN.getText());
			statement.setString(4, tfCCity.getText());
			statement.setString(5, tfEmail.getText());
			statement.setString(6, tfCNum.getText());
			statement.setString(7, tfUN.getText());
			statement.setString(8, tfPass.getText());
			
			System.out.println(query);
			
			statement.executeUpdate();
			System.out.println("[CUSTOMER/s] INSERT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[CUSTOMER/s] INSERT FAILED!");
			return false;
		}
		customers = this.getAll();
		
		showTable(customers);
		return true;
	}
	
	
	public void showTable(ArrayList<Customer> customers) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[8];
		for(int i = 0; i < customers.size(); i++) {
			tRows[0] = customers.get(i).getCustomerID();
			tRows[1] = customers.get(i).getFirstname();
			tRows[2] = customers.get(i).getLastname();
			tRows[3] = customers.get(i).getCustCity();
			tRows[4] = customers.get(i).getEmail();		
			tRows[5] = customers.get(i).getContactNumber();
			tRows[6] = customers.get(i).getUsername();
			tRows[7] = customers.get(i).getPassword();
			
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateCustomer() {
		
		try {	
			Connection connect = msc.getConnection();
			customer.setCustomerID(tfCustID.getText());
			customer.setFirstname(tfFN.getText());
			customer.setLastname(tfLN.getText());
			customer.setCustCity(tfCCity.getText());
			customer.setContactNumber(tfCNum.getText());
			customer.setEmail(tfEmail.getText());
			customer.setUsername(tfUN.getText());
			customer.setPassword(tfPass.getText());
			
			
			String query = 	"UPDATE " +  Customer.TABLE + 
					" SET " +
					Customer.COL_NAME + " = ?, " +
					Customer.COL_LASTNAME + " = ?, " +
					Customer.COL_custCITY + " = ?, " +
					Customer.COL_Email + " = ?, " +
					Customer.COL_MOBILE + " = ?, " +
					Customer.COL_USER + " = ?, " +
					Customer.COL_PASS + " = ? " +
					" WHERE " + Customer.COL_ID + " = ?";
							
			
			PreparedStatement statement = connect.prepareStatement(query);

			
			statement.setString(1, customer.getFirstname());
			statement.setString(2, customer.getLastname());
			statement.setString(3, customer.getCustCity());
			statement.setString(4, customer.getEmail());
			statement.setString(5, customer.getContactNumber());
			statement.setString(6, customer.getUsername());
			statement.setString(7, customer.getPassword());
			statement.setString(8, customer.getCustomerID());
			System.out.println(query);
			statement.executeUpdate();
			System.out.println("[CONTACTS] UPDATE SUCCESS! ");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[CONTACTS] UPDATE FAILED! ");
			return false;
		}
		
		customers = this.getAll();
		
		showTable(customers);
		return true;
	}
	
	public static boolean validatePhone(String input) {
		return input.charAt(0) == '0' && input.charAt(1) == '9' && input.length() == 11 && input.matches("[0-9]+");
	}
	
	 public static boolean validateEmail(String email) {
		 boolean status=false;    
		 String EMAIL_PATTERN = 
		 "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		 + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		 Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		 Matcher matcher=pattern.matcher(email);
		 if(matcher.matches())
		 {
			 status=true;
		 }
		 else{
			 status=false;
		 }
		 return status;
        
	    }
	
	public CustomerInsertDeleteUpdate() {
		setResizable(false);
		
		
		customers = this.getAll();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setBounds(10, 27, 790, 393);
		contentPane.add(scrollPane);
		
		
		table = new JTable();
		table.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"CustomerID", "FirstName", "LastName", "custCity", "Email", "ContactNo.", "Username", "Password"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteCustomer()) {
					JOptionPane.showMessageDialog(null, "Invalid Admin Username!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Administrator Deleted!!!");
				}
				
			}
		});
		btnDelete.setBounds(865, 359, 107, 37);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!validateEmail(tfEmail.getText()) && !validatePhone(tfCNum.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email and contact number", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(!validateEmail(tfEmail.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(!validatePhone(tfCNum.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid contact number", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(updateCustomer()) {
						JOptionPane.showMessageDialog(null, "Customer Updated!!!");
					}
					else {
						JOptionPane.showMessageDialog(null, "CustomerID/Email/ContactNumberUsername is taken", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnUpdate.setBounds(865, 407, 107, 37);
		contentPane.add(btnUpdate);
		
		JLabel label_1 = new JLabel("CustomerID:");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(10, 431, 90, 18);
		contentPane.add(label_1);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstname.setBounds(119, 431, 81, 18);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname:");
		lblLastname.setForeground(Color.WHITE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastname.setBounds(219, 431, 81, 18);
		contentPane.add(lblLastname);
		
		JLabel lblCustomercity = new JLabel("custCity:");
		lblCustomercity.setForeground(Color.WHITE);
		lblCustomercity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomercity.setBounds(322, 431, 71, 18);
		contentPane.add(lblCustomercity);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(429, 431, 45, 18);
		contentPane.add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(619, 431, 81, 18);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(719, 431, 81, 18);
		contentPane.add(lblPassword);
		
		tfCustID = new JTextField();
		tfCustID.setColumns(10);
		tfCustID.setBounds(10, 472, 90, 20);
		contentPane.add(tfCustID);
		
		tfFN = new JTextField();
		tfFN.setColumns(10);
		tfFN.setBounds(110, 472, 90, 20);
		contentPane.add(tfFN);
		
		tfLN = new JTextField();
		tfLN.setColumns(10);
		tfLN.setBounds(210, 472, 90, 20);
		contentPane.add(tfLN);
		
		tfCCity = new JTextField();
		tfCCity.setColumns(10);
		tfCCity.setBounds(310, 472, 90, 20);
		contentPane.add(tfCCity);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(410, 472, 90, 20);
		contentPane.add(tfEmail);
		
		tfUN = new JTextField();
		tfUN.setColumns(10);
		tfUN.setBounds(610, 472, 90, 20);
		contentPane.add(tfUN);
		
		tfPass = new JTextField();
		tfPass.setColumns(10);
		tfPass.setBounds(710, 472, 90, 20);
		contentPane.add(tfPass);
		
		tfCNum = new JTextField();
		tfCNum.setColumns(10);
		tfCNum.setBounds(510, 472, 90, 20);
		contentPane.add(tfCNum);
		
		JLabel lblContactno = new JLabel("ContactNo.");
		lblContactno.setForeground(Color.WHITE);
		lblContactno.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContactno.setBounds(510, 431, 90, 18);
		contentPane.add(lblContactno);
		
		showTable(customers);

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 135, 150, 165);
		contentPane.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!validateEmail(tfEmail.getText()) && !validatePhone(tfCNum.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email and contact number", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(!validateEmail(tfEmail.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(!validatePhone(tfCNum.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid contact number", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(addCustomer()) {
						JOptionPane.showMessageDialog(null, "Customer Inserted!!!");
					}
					else {
						JOptionPane.showMessageDialog(null, "CustomerID/Email/ContactNumberUsername is taken", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnAdd.setBounds(865, 311, 107, 37);
		contentPane.add(btnAdd);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu m = new MainMenu();
				m.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(865, 455, 107, 37);
		contentPane.add(backButton);
		
		JLabel lblCustomers = new JLabel("CUSTOMERS");
		lblCustomers.setForeground(Color.WHITE);
		lblCustomers.setFont(new Font("Tempus Sans ITC", Font.BOLD, 29));
		lblCustomers.setBounds(823, 43, 185, 81);
		contentPane.add(lblCustomers);
		
		
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
