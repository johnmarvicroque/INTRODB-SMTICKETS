
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
public class Report2 extends JFrame {
	
	private JPanel contentPane;

	MySqlConnect msc = new MySqlConnect();
	ArrayList<report2Att> r2List = new ArrayList<report2Att>();
	report2Att r2 = new report2Att();
	
	private JTable table;
	
	
	private report2Att toR2(ResultSet rs) throws SQLException{
		report2Att r2 = new report2Att();
		
		r2.setCID(rs.getString(report2Att.COL_CID));
		r2.setFN(rs.getString(report2Att.COL_NAME));
		r2.setLN(rs.getString(report2Att.COL_LASTNAME));
		r2.setTotal(rs.getString(report2Att.COL_TOTAL));
		r2.setNOT(rs.getString(report2Att.COL_NOT));
		
		return r2;
	}
	
	public ArrayList<report2Att> getAll() {
		ArrayList <report2Att> r2List = new ArrayList <report2Att> ();
		report1Att r2s;
		Connection connect = msc.getConnection();
		String query = 	"Select c.CustomerID, c.FirstName, c.LastName, sum(t.TicketPrice) as TotalSpendings, count(cht.Ticket_TicketNumber) as NoofTickets " + 
				"FROM customer c inner join customer_has_ticket cht " + 
				"on c.CustomerID = cht.Customer_CustomerID " + 
				"inner join ticket t " + 
				"on cht.Ticket_TicketNumber = t.TicketNumber " + 
				"GROUP BY c.CustomerID "
				+ "ORDER BY c.FirstName, c.LastName";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				r2List.add(toR2(rs));
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
		
		return r2List;
	}

	
	public void showTable(ArrayList<report2Att> r2List) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[5];
		for(int i = 0; i < r2List.size(); i++) {
			tRows[0] = r2List.get(i).getCID();
			tRows[1] = r2List.get(i).getFN();
			tRows[2] = r2List.get(i).getLN();
			tRows[3] = r2List.get(i).getTotal();
			tRows[4] = r2List.get(i).getNOT();
			tModel.addRow(tRows);
		}
	}

	
	public Report2() {
		setResizable(false);
		
		
		r2List = this.getAll();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1058, 664);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomers = new JLabel("List of Customers and their  Total Spendings");
		lblCustomers.setBounds(211, 0, 615, 81);
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
				"CustomerID", "Firstname", "Lastname", "TotalSpendings", "NoofTickets"
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
		
		showTable(r2List);
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
