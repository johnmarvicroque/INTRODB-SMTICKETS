
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.*;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

public class ReservationsInsertUpdateDelete extends JFrame {

	ArrayList<Reservations> reserveList = new ArrayList<Reservations>();
	ArrayList<AvailableTickets> aTicketList = new ArrayList<AvailableTickets>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Reservations reservations = new Reservations();
	AvailableTickets aTicket = new AvailableTickets();
	private JComboBox cbTE;
	JComboBox cbCID;
	JComboBox cbPT;
	JDateChooser dateChooser;

	private AvailableTickets toAvailable(ResultSet rs) throws SQLException{
		AvailableTickets aTicket = new AvailableTickets();
		
		aTicket.setTicketNumber(rs.getString(AvailableTickets.COL_TN));
		
		return aTicket;
	}
	
	
	private Reservations toReserve(ResultSet rs) throws SQLException{
		Reservations reservations = new Reservations();
		
		reservations.setCustomerID(rs.getString(Reservations.COL_CustID));
		reservations.setTicketNumber(rs.getString(Reservations.COL_TNumber));
		reservations.setPaymentType(rs.getString(Reservations.COL_PType));
		reservations.setTransactionDate(rs.getString(Reservations.COL_TDate));
	
		return reservations;
	}
	

	public boolean deleteReservation() {
		reservations.setTicketNumber(cbTE.getSelectedItem().toString());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						Reservations.TABLE + " WHERE " + Reservations.COL_TNumber + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, reservations.getTicketNumber());
			System.out.println(query);
			statement.executeUpdate();
			
			statement.close();
			connect.close();
			System.out.println("[Rervation] DELETE SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[Reservation] DELETE FAILED!");
			ev.printStackTrace();
			return false;
		}
		
		reserveList = this.getAll();
		
		showTable(reserveList);
		return true;
	}
	
	public ArrayList<Reservations> getAll() {
		ArrayList <Reservations> reserveList = new ArrayList <Reservations> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + Reservations.TABLE;

		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				reserveList.add(toReserve(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[Reservation/s] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[Reservation/s] SELECT FAILED!");
			return null;
		}	
		
		return reserveList;
	}
	
	public ArrayList<AvailableTickets> getAllAvailable() {
		ArrayList <AvailableTickets> aTicketList = new ArrayList <AvailableTickets> ();
		Connection connect = msc.getConnection();
		String query = "SELECT t.TicketNumber " + 
				"FROM ticket t " + 
				"WHERE t.TicketNumber NOT IN " + 
				"(select chs.Ticket_TicketNumber " + 
				"from customer_has_ticket chs)";
				
		System.out.println(query);


		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				aTicketList.add(toAvailable(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[Reservation/s] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[Reservation/s] SELECT FAILED!");
			return null;
		}	
		
		return aTicketList;
	}
	
	public boolean addReservation() {
		Connection connect = msc.getConnection();
		
		reservations.setCustomerID(cbCID.getSelectedItem().toString());
		reservations.setTicketNumber(cbTE.getSelectedItem().toString());
		reservations.setPaymentType(cbPT.getSelectedItem().toString());
		reservations.setTransactionDate(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
		
		String query = 	"INSERT INTO " + 
						Reservations.TABLE + "(`Customer_CustomerID`, `Ticket_TicketNumber`, `PaymentType`, `TransactionDate`)" +
						" VALUES(?,?,?,?)";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, cbCID.getSelectedItem().toString());
			statement.setString(2, cbTE.getSelectedItem().toString());
			statement.setString(3, cbPT.getSelectedItem().toString());
			statement.setString(4, ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
			
			statement.executeUpdate();
			System.out.println("[CUSTOMER/s] INSERT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[CUSTOMER/s] INSERT FAILED!");
			return false;
		}
		reserveList = this.getAll();
		
		showTable(reserveList);
		return true;
	}
	
	public void showTable(ArrayList<Reservations> reserveList) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[4];
		for(int i = 0; i < reserveList.size(); i++) {
			tRows[0] = reserveList.get(i).getCustomerID();
			tRows[1] = reserveList.get(i).getTicketNumber();
			tRows[2] = reserveList.get(i).getPaymentType();
			tRows[3] = reserveList.get(i).getTransactionDate();
			
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateReservation() {
		
		try {	
			Connection connect = msc.getConnection();
			
			reservations.setCustomerID(cbCID.getSelectedItem().toString());
			reservations.setTicketNumber(cbTE.getSelectedItem().toString());
			reservations.setPaymentType(cbPT.getSelectedItem().toString());
			reservations.setTransactionDate(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
			
			String query = 	"UPDATE " +  Reservations.TABLE + 
					" SET " +
					Reservations.COL_CustID + " = ?, " +
					Reservations.COL_PType + " = ?, " +
					Reservations.COL_TDate + " = ? " +
					" WHERE " + Reservations.COL_TNumber + " = ?";
							
			
			PreparedStatement statement = connect.prepareStatement(query);

			
			statement.setString(1, reservations.getCustomerID());
			statement.setString(2, reservations.getPaymentType());
			statement.setString(3, reservations.getTransactionDate());
			statement.setString(4, reservations.getTicketNumber());
			System.out.println(query);
			statement.executeUpdate();
			System.out.println("[Reservation] UPDATE SUCCESS! ");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Reservation] UPDATE FAILED! ");
			return false;
		}
		
		reserveList = this.getAll();
		
		showTable(reserveList);
		return true;
	}
	
	public ReservationsInsertUpdateDelete() {
		setAlwaysOnTop(false);
		setResizable(false);
		
		
		reserveList = this.getAll();
		
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
				"CustomerID", "TicketNumber", "PaymentType", "DateTransaction"
			}
		));
		table.getColumnModel().getColumn(2).setPreferredWidth(97);
		table.getColumnModel().getColumn(3).setPreferredWidth(106);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteReservation()) {
					JOptionPane.showMessageDialog(null, "Invalid Admin Username!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Administrator Deleted!!!");
				}
			}
		});
		btnDelete.setBounds(865, 355, 107, 37);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateReservation()) {
					JOptionPane.showMessageDialog(null, "Reservation Updated!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Ticket Number is taken / Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		btnUpdate.setBounds(865, 403, 107, 37);
		contentPane.add(btnUpdate);
		
		JLabel label_1 = new JLabel("CustomerID:");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(141, 431, 90, 18);
		contentPane.add(label_1);
		
		JLabel lblFirstname = new JLabel("TicketNumber:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstname.setBounds(271, 431, 107, 18);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("PaymentType:");
		lblLastname.setForeground(Color.WHITE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastname.setBounds(407, 431, 107, 18);
		contentPane.add(lblLastname);
		
		JLabel lblCustomercity = new JLabel("DateTransaction\r\n:");
		lblCustomercity.setForeground(Color.WHITE);
		lblCustomercity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomercity.setBounds(547, 431, 120, 18);
		contentPane.add(lblCustomercity);
		
		showTable(reserveList);
//		showATable(aTicketList);
		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 131, 150, 165);
		contentPane.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(addReservation()) {
					JOptionPane.showMessageDialog(null, "Reservation Inserted!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Ticket Number is taken / Invalid Date", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnAdd.setBounds(865, 307, 107, 37);
		contentPane.add(btnAdd);
		
		JButton Back = new JButton("Back");
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu m = new MainMenu();
				m.setVisible(true);
				dispose();
			}
		});
		Back.setBounds(865, 451, 107, 37);
		contentPane.add(Back);
		
		JLabel lblReservations = new JLabel("RESERVATIONS");
		lblReservations.setForeground(Color.WHITE);
		lblReservations.setFont(new Font("Tempus Sans ITC", Font.BOLD, 26));
		lblReservations.setBounds(810, 39, 205, 81);
		contentPane.add(lblReservations);
		
		cbCID = new JComboBox();
		cbCID.setBounds(130, 460, 114, 20);
		contentPane.add(cbCID);		
		
		cbTE = new JComboBox();
		cbTE.setBounds(271, 460, 107, 20);
		contentPane.add(cbTE);
		
		FillComboBoxCID(cbCID);
		FillComboBoxCTE(cbTE);
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(547, 460, 120, 20);
		contentPane.add(dateChooser);
		
		cbPT = new JComboBox();
		cbPT.setBounds(407, 460, 107, 18);
		contentPane.add(cbPT);
		cbPT.addItem("OnlineBanking");
		cbPT.addItem("CreditCard");
		
	}
	
	private void FillComboBoxCID(JComboBox cBox) {
		Connection connect = msc.getConnection();
		
		String query = "SELECT CustomerID FROM customer";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				cBox.addItem(rs.getString(1));
			}
			rs.close();
			statement.close();
			connect.close();
		}
		catch(SQLException ev) {
			ev.printStackTrace();
		}
	}
	
	private void FillComboBoxCTE(JComboBox cBox) {
		Connection connect = msc.getConnection();
		
		String query = "SELECT TicketNumber FROM ticket";
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				cBox.addItem(rs.getString(1));
			}
			rs.close();
			statement.close();
			connect.close();
		}
		catch(SQLException ev) {
			ev.printStackTrace();
		}
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
