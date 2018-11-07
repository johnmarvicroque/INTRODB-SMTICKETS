
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
public class ListofEmployees extends JFrame {
	ArrayList<Employees> employeeList = new ArrayList<Employees>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Employees employees = new Employees();
	private JTextField tfFN;
	private JTextField tfLN;
	private JTextField tfEmail;
	private JTextField tfUN;
	private JTextField tfPass;
	private JTextField tfCNum;
	
	
	private Employees toEmployee (ResultSet rs) throws SQLException{
		Employees employees = new Employees();
		
		employees.setUsername(rs.getString(Employees.COL_USER));
		employees.setPassword(rs.getString(Employees.COL_PASS));
		employees.setEmail(rs.getString(Employees.COL_Email));
		employees.setContactnumber(rs.getString(Employees.COL_MOBILE));
		employees.setFirstname(rs.getString(Employees.COL_NAME));
		employees.setLastname(rs.getString(Employees.COL_LASTNAME));
				
		return employees;
	}
	

	public boolean deleteEmployee() {
		employees.setUsername(tfUN.getText());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						Employees.TABLE + " WHERE " + Employees.COL_USER + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, employees.getUsername());
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
		
		employeeList = this.getAll();
		
		showTable(employeeList);
		return true;
	}
	
	public ArrayList<Employees> getAll() {
		ArrayList<Employees> employeeList = new ArrayList<Employees>();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + Employees.TABLE;
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				employeeList.add(toEmployee(rs));
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
		
		return employeeList;
	}
	
	public void showTable(ArrayList<Employees> employeeList) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[6];
		for(int i = 0; i < employeeList.size(); i++) {
			tRows[0] = employeeList.get(i).getUsername();
			tRows[1] = employeeList.get(i).getPassword();
			tRows[2] = employeeList.get(i).getEmail();		
			tRows[3] = employeeList.get(i).getContactnumber();
			tRows[4] = employeeList.get(i).getFirstname();
			tRows[5] = employeeList.get(i).getLastname();
		
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateEmployee() {
		
		try {	
			Connection connect = msc.getConnection();
			
			employees.setFirstname(tfFN.getText());
			employees.setLastname(tfLN.getText());
			employees.setContactnumber(tfCNum.getText());
			employees.setEmail(tfEmail.getText());
			employees.setUsername(tfUN.getText());
			employees.setPassword(tfPass.getText());
			
			
			String query = 	"UPDATE " +  Employees.TABLE + 
					" SET " +
					Employees.COL_PASS + " = ?, " +
					Employees.COL_Email + " = ?, " +
					Employees.COL_MOBILE + " = ?, " +
					Employees.COL_NAME + " = ?, " +
					Employees.COL_LASTNAME + " = ? " +
					" WHERE " + Employees.COL_USER + " = ?";
							
			
			PreparedStatement statement = connect.prepareStatement(query);

			statement.setString(1, employees.getPassword());
			statement.setString(2, employees.getEmail());
			statement.setString(3, employees.getContactnumber());
			statement.setString(4, employees.getFirstname());
			statement.setString(5, employees.getLastname());
			statement.setString(6, employees.getUsername());
			statement.executeUpdate();
			System.out.println("[CONTACTS] UPDATE SUCCESS! ");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[CONTACTS] UPDATE FAILED! ");
			return false;
		}
		
		employeeList = this.getAll();
		
		showTable(employeeList);
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
	
	
	public ListofEmployees() {
		setResizable(false);
		
		
		employeeList = this.getAll();
		
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
				"Username", "Password", "Email", "ContactNo.", "FirstName", "LastName"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteEmployee()) {
					JOptionPane.showMessageDialog(null, "Invalid Admin Username!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Administrator Deleted!!!");
				}
				
			}
		});
		btnDelete.setBounds(865, 311, 107, 37);
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
					if(updateEmployee()) {
						JOptionPane.showMessageDialog(null, "Customer Inserted!!!");
					}
					else {
						JOptionPane.showMessageDialog(null, "CustomerID/Email/ContactNumberUsername is taken", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnUpdate.setBounds(865, 359, 107, 37);
		contentPane.add(btnUpdate);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstname.setBounds(420, 431, 81, 18);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname:");
		lblLastname.setForeground(Color.WHITE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastname.setBounds(520, 431, 81, 18);
		contentPane.add(lblLastname);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(231, 431, 45, 18);
		contentPane.add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(20, 431, 81, 18);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(120, 431, 81, 18);
		contentPane.add(lblPassword);
		
		tfFN = new JTextField();
		tfFN.setColumns(10);
		tfFN.setBounds(411, 472, 90, 20);
		contentPane.add(tfFN);
		
		tfLN = new JTextField();
		tfLN.setColumns(10);
		tfLN.setBounds(511, 472, 90, 20);
		contentPane.add(tfLN);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(211, 472, 90, 20);
		contentPane.add(tfEmail);
		
		tfUN = new JTextField();
		tfUN.setColumns(10);
		tfUN.setBounds(10, 472, 90, 20);
		contentPane.add(tfUN);
		
		tfPass = new JTextField();
		tfPass.setColumns(10);
		tfPass.setBounds(111, 472, 90, 20);
		contentPane.add(tfPass);
		
		tfCNum = new JTextField();
		tfCNum.setColumns(10);
		tfCNum.setBounds(311, 472, 90, 20);
		contentPane.add(tfCNum);
		
		JLabel lblContactno = new JLabel("ContactNo.");
		lblContactno.setForeground(Color.WHITE);
		lblContactno.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContactno.setBounds(311, 431, 90, 18);
		contentPane.add(lblContactno);
		
		showTable(employeeList);

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 135, 150, 165);
		contentPane.add(label);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu m = new MainMenu();
				m.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(865, 407, 107, 37);
		contentPane.add(backButton);
		
		JLabel lblEmployees = new JLabel("Administrators");
		lblEmployees.setForeground(Color.WHITE);
		lblEmployees.setFont(new Font("Tempus Sans ITC", Font.BOLD, 26));
		lblEmployees.setBounds(823, 43, 185, 81);
		contentPane.add(lblEmployees);
		
		
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
