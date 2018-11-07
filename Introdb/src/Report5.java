
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
public class Report5 extends JFrame {
	
	private JPanel contentPane;

	MySqlConnect msc = new MySqlConnect();
	ArrayList<report5Att> r5List = new ArrayList<report5Att>();
	report5Att r5 = new report5Att();
	
	private JTable table;
	
	
	private report5Att toR5(ResultSet rs) throws SQLException{
		report5Att r5 = new report5Att();
		
		r5.setEID(rs.getString(report5Att.COL_EID));
		r5.setEN(rs.getString(report5Att.COL_EN));
		r5.setTIC(rs.getString(report5Att.COL_TIC));
		
		return r5;
	}
	
	public ArrayList<report5Att> getAll() {
		ArrayList <report5Att> r5List = new ArrayList <report5Att> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT e.EventID, e.EventName, sum(t.TicketPrice) as TotalIncome " + 
				"FROM event e inner join ticket t " + 
				"ON e.EventID = t.Event_EventID " + 
				"inner join customer_has_ticket cht " + 
				"on cht.Ticket_TicketNumber = t.TicketNumber " + 
				"group by e.EventID";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				r5List.add(toR5(rs));
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
		
		return r5List;
	}

	
	public void showTable(ArrayList<report5Att> r5List) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[3];
		for(int i = 0; i < r5List.size(); i++) {
			tRows[0] = r5List.get(i).getEID();
			tRows[1] = r5List.get(i).getEN();
			tRows[2] = r5List.get(i).getTIC();
			tModel.addRow(tRows);
		}
	}

	
	public Report5() {
		setResizable(false);
		
		
		r5List = this.getAll();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1058, 664);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomers = new JLabel("No. of Tickets that are still Available}");
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
				"EventID", "EventName", "TotalIncome"
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
		
		showTable(r5List);
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
