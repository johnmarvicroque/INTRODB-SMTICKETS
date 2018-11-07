
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
import javax.swing.border.LineBorder;
public class Report1 extends JFrame {
	
	private JPanel contentPane;

	MySqlConnect msc = new MySqlConnect();
	ArrayList<report1Att> r1List = new ArrayList<report1Att>();
	report1Att r1 = new report1Att();
	
	private JTable table;
	
	
	private report1Att toR1(ResultSet rs) throws SQLException{
		report1Att r1 = new report1Att();
		
		r1.setEID(rs.getString(report1Att.COL_EID));
		r1.setEname(rs.getString(report1Att.COL_EN));
		r1.setCity(rs.getString(report1Att.COL_VC));
		r1.setVenuename(rs.getString(report1Att.COL_VN));
		r1.setCID(rs.getString(report1Att.COL_CID));
		r1.setFirstname(rs.getString(report1Att.COL_NAME));
		r1.setLastname(rs.getString(report1Att.COL_LASTNAME));
		
		return r1;
	}
	
	public ArrayList<report1Att> getAll() {
		ArrayList <report1Att> r1List = new ArrayList <report1Att> ();
		report1Att r1s;
		Connection connect = msc.getConnection();
		String query = 	"SELECT e.EventID, e.EventName, v.VenueCity, v.VenueName, c.CustomerID, c.Firstname, c.Lastname " + 
						"FROM event as e inner join venue as v " + 
						"on e.Venue_VenueID = v.VenueID " + 
						"inner join customer as c " + 
						"on v.VenueCity = c.CustomerCity " +
						"ORDER BY VenueName;";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				r1List.add(toR1(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("Query SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Query FAILED!");
			return null;
		}	
		
		return r1List;
	}

	
	public void showTable(ArrayList<report1Att> r1List) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[7];
		for(int i = 0; i < r1List.size(); i++) {
			tRows[0] = r1List.get(i).getEID();
			tRows[1] = r1List.get(i).getEname();
			tRows[2] = r1List.get(i).getCity();
			tRows[3] = r1List.get(i).getVenuename();
			tRows[4] = r1List.get(i).getCID();
			tRows[5] = r1List.get(i).getFirstname();
			tRows[6] = r1List.get(i).getLastname();
			
			tModel.addRow(tRows);
		}
	}
	
	
	public Report1() {
		setResizable(false);
		
		
		r1List = this.getAll();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1058, 664);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomers = new JLabel("List Of Venues Near Customer's City");
		lblCustomers.setBounds(264, 0, 563, 81);
		lblCustomers.setForeground(Color.WHITE);
		lblCustomers.setFont(new Font("Tempus Sans ITC", Font.BOLD, 29));
		contentPane.add(lblCustomers);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new LineBorder(Color.BLACK, 10));
		scrollPane_1.setBackground(Color.DARK_GRAY);
		scrollPane_1.setBounds(22, 197, 1009, 412);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EventID", "EventName", "VenueName", "City", "CustomerID", "Firstname", "Lastname"
			}
		));
		table.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		table.setAutoCreateRowSorter(true);
		scrollPane_1.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(366, 62, 300, 108);
		lblNewLabel.setIcon(new ImageIcon(Report1.class.getResource("/pics/sm-tickets-footer.png")));
		contentPane.add(lblNewLabel);
		
		JButton button = new JButton("Back");
		button.setFont(new Font("Tahoma", Font.PLAIN, 23));
		button.setAutoscrolls(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainMenu m = new MainMenu();
				m.setVisible(true);
				dispose();
			}
		});
		button.setBounds(838, 76, 140, 64);
		contentPane.add(button);
		
		showTable(r1List);
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
