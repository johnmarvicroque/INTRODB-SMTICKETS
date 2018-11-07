
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.*;
import javax.swing.JPasswordField;
public class Signup extends JFrame {
	ArrayList<Employees> employeeList = new ArrayList<Employees>();
	private JPanel contentPane;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Employees employees = new Employees();
	private JTextField tfFN;
	private JTextField tfLN;
	private JTextField tfEmail;
	private JTextField tfUN;
	private JTextField tfCNum;
	private JPasswordField tfPass;
	
	
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
	
	public boolean addEmployee() {
		Connection connect = msc.getConnection();
		
		employees.setFirstname(tfFN.getText());
		employees.setLastname(tfLN.getText());
		employees.setContactnumber(tfCNum.getText());
		employees.setEmail(tfEmail.getText());
		employees.setUsername(tfUN.getText());
		employees.setPassword(tfPass.getText());
		
		String query = 	"INSERT INTO " + 
						Employees.TABLE + "(Username, Password, Email, Contactnumber, Firstname, Lastname)" +
						" VALUES(?,?,?,?,?,?)";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, employees.getUsername());
			statement.setString(2, employees.getPassword());
			statement.setString(3, employees.getEmail());
			statement.setString(4, employees.getContactnumber());
			statement.setString(5, employees.getFirstname());
			statement.setString(6, employees.getLastname());
			
			statement.executeUpdate();
			System.out.println("[Employee SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Employee] INSERT FAILED!");
			return false;
		}
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
	
	
	public Signup() {
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 484);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validateEmail(tfEmail.getText()) && !validatePhone(tfCNum.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email and contact number", "Error", JOptionPane.ERROR_MESSAGE);
					}
				else if(!validateEmail(tfEmail.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(!validatePhone(tfCNum.getText())) {
					JOptionPane.showMessageDialog(null, "Please enter a valid contact number", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
	            	if(addEmployee()) {
		                JOptionPane.showMessageDialog(null, "Signup Successful");
		                login l = new login();
		                l.setVisible(true);
		                dispose();
	            	}
	            	else {
	            		JOptionPane.showMessageDialog(null, "Username/Email/ContactNumber is taken", "Error", JOptionPane.ERROR_MESSAGE);
	            	}
	            }
			}
		});
		btnSignup.setBounds(227, 273, 107, 37);
		contentPane.add(btnSignup);
		
		JLabel lblFirstname = new JLabel("Firstname:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstname.setBounds(29, 292, 81, 18);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname:");
		lblLastname.setForeground(Color.WHITE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastname.setBounds(29, 352, 81, 18);
		contentPane.add(lblLastname);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(42, 167, 45, 18);
		contentPane.add(lblEmail);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(29, 47, 81, 18);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(29, 107, 81, 18);
		contentPane.add(lblPassword);
		
		tfFN = new JTextField();
		tfFN.setColumns(10);
		tfFN.setBounds(20, 321, 90, 20);
		contentPane.add(tfFN);
		
		tfLN = new JTextField();
		tfLN.setColumns(10);
		tfLN.setBounds(20, 381, 90, 20);
		contentPane.add(tfLN);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(20, 197, 90, 20);
		contentPane.add(tfEmail);
		
		tfUN = new JTextField();
		tfUN.setColumns(10);
		tfUN.setBounds(20, 76, 90, 20);
		contentPane.add(tfUN);
		
		tfCNum = new JTextField();
		tfCNum.setColumns(10);
		tfCNum.setBounds(20, 261, 90, 20);
		contentPane.add(tfCNum);
		
		JLabel lblContactno = new JLabel("ContactNo.");
		lblContactno.setForeground(Color.WHITE);
		lblContactno.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContactno.setBounds(29, 232, 90, 18);
		contentPane.add(lblContactno);
		
		

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(206, 85, 150, 165);
		contentPane.add(label);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login l = new login();
				l.setVisible(true);
				dispose();
			}
		});
		backButton.setBounds(227, 345, 107, 37);
		contentPane.add(backButton);
		
		JLabel lblCustomers = new JLabel("SIGNUP");
		lblCustomers.setForeground(Color.WHITE);
		lblCustomers.setFont(new Font("Tempus Sans ITC", Font.BOLD, 34));
		lblCustomers.setBounds(218, 11, 185, 81);
		contentPane.add(lblCustomers);
		
		tfPass = new JPasswordField();
		tfPass.setBounds(20, 136, 90, 20);
		contentPane.add(tfPass);
		
		
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
