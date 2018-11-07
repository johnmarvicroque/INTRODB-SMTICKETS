
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUN;
	private JPasswordField txtPW;
	
	MySqlConnect msc = new MySqlConnect();
	Employees employee = new Employees();
	Connection conn ;
	ResultSet RS;
	PreparedStatement PST;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public boolean loginToSql() {
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM employees WHERE Username = ?	and Password = ?";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
		
			
			statement.setString(1, txtUN.getText());
			statement.setString(2, txtPW.getText());
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				return true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Username or Password is wrong!!!");
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[Employees] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[Employees] SELECT FAILED!");
			return false;
		}
		return false;
	}
	
	public login() {
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 470);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblUsername.setBounds(156, 196, 109, 23);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblPassword.setBounds(156, 230, 109, 23);
		contentPane.add(lblPassword);
		
		txtUN = new JTextField();
		txtUN.setBounds(275, 196, 156, 26);
		contentPane.add(txtUN);
		txtUN.setColumns(10);
		
		txtPW = new JPasswordField();
		txtPW.setBounds(275, 230, 156, 26);
		contentPane.add(txtPW);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup s = new Signup();
				s.setVisible(true);
				dispose();
			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSignUp.setBounds(293, 295, 103, 31);
		contentPane.add(btnSignUp);
		JButton btnLogin = new JButton("Log In");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(loginToSql()) {
					MainMenu m = new MainMenu();
					m.setVisible(true);
					dispose();
				}
			}
			
		});
		btnLogin.setBounds(167, 295, 103, 31);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(login.class.getResource("/pics/sm-tickets-footer.png")));
		lblNewLabel.setBounds(131, 46, 300, 139);
		contentPane.add(lblNewLabel);
		
	}
}
