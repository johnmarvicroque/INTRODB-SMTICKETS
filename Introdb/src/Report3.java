
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
public class Report3 extends JFrame {
	
	private JPanel contentPane;

	MySqlConnect msc = new MySqlConnect();
	ArrayList<report3Att> r3List = new ArrayList<report3Att>();
	report3Att r3 = new report3Att();
	
	private JTable table;
	
	
	private report3Att toR3(ResultSet rs) throws SQLException{
		report3Att r3 = new report3Att();
		
		r3.setEID(rs.getString(report3Att.COL_EID));
		r3.setEN(rs.getString(report3Att.COL_EN));
		r3.setED(rs.getString(report3Att.COL_ED));
		r3.setNoofAtendees(rs.getString(report3Att.COL_NOT));
		
		return r3;
	}
	
	public ArrayList<report3Att> getAll() {
		ArrayList <report3Att> r3List = new ArrayList <report3Att> ();
		report1Att r3s;
		Connection connect = msc.getConnection();
		String query = 	"SELECT e.EventID, e.EventName, e.EventDate, count(cht.Customer_CustomerID)as NoofAtendees " + 
				"FROM event e inner join ticket t " + 
				"on e.EventID = t.Event_EventID " + 
				"inner join customer_has_ticket cht " + 
				"on cht.Ticket_TicketNumber = t.TicketNumber " + 
				"Group BY e.EventID "
				+ "ORDER BY e.EventID";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				r3List.add(toR3(rs));
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
		
		return r3List;
	}

	
	public void showTable(ArrayList<report3Att> r3List) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[4];
		for(int i = 0; i < r3List.size(); i++) {
			tRows[0] = r3List.get(i).getEID();
			tRows[1] = r3List.get(i).getEN();
			tRows[2] = r3List.get(i).getED();
			tRows[3] = r3List.get(i).getNoofAtendees();
			tModel.addRow(tRows);
		}
	}

	
	public Report3() {
		setResizable(false);
		
		
		r3List = this.getAll();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1058, 664);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomers = new JLabel("Expected Number of Attendees for Each Event");
		lblCustomers.setBounds(190, 0, 653, 81);
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
				"EventID", "EventName", "EventDate", "NoofAtendees"
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
		
		showTable(r3List);
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
