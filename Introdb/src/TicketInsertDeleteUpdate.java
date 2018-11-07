
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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
public class TicketInsertDeleteUpdate extends JFrame {
	ArrayList<Ticket> ticketList = new ArrayList<Ticket>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Ticket ticket = new Ticket();
	private JTextField tfTN;
	private JTextField tfTP;
	JComboBox cbTT;
	JComboBox cbST;
	JComboBox cbEID;
	JComboBox cbVS;
	private JTextField tfVID;
	
	
	private Ticket toTicket(ResultSet rs) throws SQLException{
		Ticket ticket = new Ticket();
		
		ticket.setTicketNumber(rs.getString(Ticket.COL_TN));
		ticket.setTicketPrice(rs.getString(Ticket.COL_TP));
		ticket.setTicketType(rs.getString(Ticket.COL_TT));
		ticket.setSeatingType(rs.getString(Ticket.COL_ST));
		ticket.setEventID(rs.getString(Ticket.COL_EID));
		ticket.setVenueID(rs.getString(Ticket.COL_VID));
		ticket.setVenueSeats(rs.getString(Ticket.COL_SN));
		
		return ticket;
	}
	

	public boolean deleteTicket() {
		ticket.setTicketNumber(tfTN.getText());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						Ticket.TABLE + " WHERE " + Ticket.COL_TN + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, ticket.getTicketNumber());
			System.out.println(query);
			statement.executeUpdate();
			
			statement.close();
			connect.close();
			System.out.println("[Ticket] DELETE SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[Ticket] DELETE FAILED!");
			ev.printStackTrace();
			return false;
		}
		
		ticketList = this.getAll();
		
		showTable(ticketList);
		return true;
	}
	
	public ArrayList<Ticket> getAll() {
		ArrayList <Ticket> ticketList = new ArrayList <Ticket> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + Ticket.TABLE;

		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				ticketList.add(toTicket(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[Ticket] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[Ticket] SELECT FAILED!");
			return null;
		}	
		
		return ticketList;
	}
	
	public boolean addTicket() {
		Connection connect = msc.getConnection();
		
		ticket.setTicketNumber(tfTN.getText());
		ticket.setTicketPrice(tfTP.getText());
		ticket.setTicketType(cbTT.getSelectedItem().toString());
		ticket.setSeatingType(cbST.getSelectedItem().toString());
		ticket.setEventID(cbEID.getSelectedItem().toString());
		ticket.setVenueID(tfVID.getText());
		ticket.setVenueSeats(cbVS.getSelectedItem().toString());
		
		for(int i = 0; i < ticketList.size(); i++) {
			if(ticketList.get(i).getEventID().equals(cbEID.getSelectedItem().toString()) && ticketList.get(i).getVenueSeats().equals(cbVS.getSelectedItem().toString())) {
				return false;
			}
		}
		
	
		if(cbVS.getSelectedItem().toString().equals("NONE")) {
			String query = 	
					"INSERT INTO " + 
					Ticket.TABLE + "(TicketNumber, TicketPrice, TicketType, SeatingType, Event_EventID, Event_Venue_VenueID)" + 
					" VALUES(?,?,?,?,?,?)";
			try {
				PreparedStatement statement = connect.prepareStatement(query);
				System.out.println(query);
				statement.setString(1, tfTN.getText());
				statement.setString(2, tfTP.getText());
				statement.setString(3, cbTT.getSelectedItem().toString());
				statement.setString(4, cbST.getSelectedItem().toString());
				statement.setString(5, cbEID.getSelectedItem().toString());
				statement.setString(6, tfVID.getText());
				
				statement.executeUpdate();
				System.out.println("[Ticket] INSERT SUCCESS!");
			} catch (SQLException ev) {
				ev.printStackTrace();
				System.out.println("[Ticket] INSERT FAILED!");
				return false;
			}
		}
		else {
			String query = 	
					"INSERT INTO " + 
					Ticket.TABLE + "(TicketNumber, TicketPrice, TicketType, SeatingType, Event_EventID, Event_Venue_VenueID, VenueSeats_SeatNumber)" + 
					" VALUES(?,?,?,?,?,?,?)";
			try {
				PreparedStatement statement = connect.prepareStatement(query);
				System.out.println(query);
				statement.setString(1, tfTN.getText());
				statement.setString(2, tfTP.getText());
				statement.setString(3, cbTT.getSelectedItem().toString());
				statement.setString(4, cbST.getSelectedItem().toString());
				statement.setString(5, cbEID.getSelectedItem().toString());
				statement.setString(6, tfVID.getText());
				statement.setString(7, cbVS.getSelectedItem().toString());
				
				statement.executeUpdate();
				System.out.println("[Ticket] INSERT SUCCESS!");
			} catch (SQLException ev) {
				ev.printStackTrace();
				System.out.println("[Ticket] INSERT FAILED!");
				return false;
			}
		}
		
		ticketList = this.getAll();
		
		showTable(ticketList);
		return true;
	}
	
	
	public void showTable(ArrayList<Ticket> ticket) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[7];
		for(int i = 0; i < ticket.size(); i++) { 
			tRows[0] = ticket.get(i).getTicketNumber();
			tRows[1] = ticket.get(i).getTicketPrice();
			tRows[2] = ticket.get(i).getTicketType();
			tRows[3] = ticket.get(i).getSeatingType();
			tRows[4] = ticket.get(i).getEventID();
			tRows[5] = ticket.get(i).getVenueID();
			tRows[6] = ticket.get(i).getVenueSeats();
			
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateTicket() {
		Connection connect = msc.getConnection();
		
		
		String query = 	"UPDATE " +  Ticket.TABLE + 
				" SET " +
				Ticket.COL_TP + " = ?, " +
				Ticket.COL_TT + " = ?, " +
				Ticket.COL_ST + " = ?, " +
				Ticket.COL_EID+ " = ?, " +
				Ticket.COL_VID+ " = ?, "+
				Ticket.COL_SN + " = ?" +
				
				" WHERE " + Ticket.COL_TN + " = ?"; 

		try {
			PreparedStatement statement = connect.prepareStatement(query);
			System.out.println(query);
			
			statement.setString(1, tfTP.getText());
			statement.setString(2, cbTT.getSelectedItem().toString());
			statement.setString(3, cbST.getSelectedItem().toString());
			statement.setString(4, cbEID.getSelectedItem().toString());
			statement.setString(5, tfVID.getText());
			statement.setString(6, cbVS.getSelectedItem().toString());
			statement.setString(7, tfTN.getText());
			
			statement.executeUpdate();
			System.out.println(query);
			System.out.println("[Ticket] INSERT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Ticket] INSERT FAILED!");
			return false;
		}
		
		
		ticketList = this.getAll();
		
		showTable(ticketList);
		return true;
	}
	
	public TicketInsertDeleteUpdate() {
		setAlwaysOnTop(false);
		setResizable(false);
		
		Connection connect = msc.getConnection();
		
		ticketList = this.getAll();
		
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
				"TicketNumber", "TicketPrice", "TicketType", "SeatingType", "EventID", "VenueID", "SeatNumber"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteTicket()) {
					JOptionPane.showMessageDialog(null, "Invalid TicketNumber!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Ticket Deleted!!!");
				}
				
			}
		});
		btnDelete.setBounds(865, 359, 107, 37);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateTicket()) {
					JOptionPane.showMessageDialog(null, "Ticket Updated!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Invalid Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnUpdate.setBounds(865, 407, 107, 37);
		contentPane.add(btnUpdate);
		
		JLabel lblTicketnumber = new JLabel("TicketNumber:");
		lblTicketnumber.setForeground(Color.WHITE);
		lblTicketnumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTicketnumber.setBounds(10, 431, 113, 18);
		contentPane.add(lblTicketnumber);
		
		JLabel lblFirstname = new JLabel("TicketPrice:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstname.setBounds(141, 431, 81, 18);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("TicketType:");
		lblLastname.setForeground(Color.WHITE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastname.setBounds(241, 431, 81, 18);
		contentPane.add(lblLastname);
		
		JLabel lblCustomercity = new JLabel("SeatingType:");
		lblCustomercity.setForeground(Color.WHITE);
		lblCustomercity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomercity.setBounds(332, 431, 100, 18);
		contentPane.add(lblCustomercity);
		
		JLabel lblEmail = new JLabel("EventID:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(442, 431, 71, 18);
		contentPane.add(lblEmail);
		
		JLabel lblUsername = new JLabel("VenueSeat:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(641, 431, 81, 18);
		contentPane.add(lblUsername);
		
		tfTN = new JTextField();
		tfTN.setColumns(10);
		tfTN.setBounds(10, 472, 113, 20);
		contentPane.add(tfTN);
		
		tfTP = new JTextField();
		tfTP.setColumns(10);
		tfTP.setBounds(132, 472, 90, 20);
		contentPane.add(tfTP);
		
		JLabel lblContactno = new JLabel("VenueID:");
		lblContactno.setForeground(Color.WHITE);
		lblContactno.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContactno.setBounds(538, 431, 71, 18);
		contentPane.add(lblContactno);
		
		showTable(ticketList);;

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 135, 150, 165);
		contentPane.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(addTicket()) {
					JOptionPane.showMessageDialog(null, "Ticket Inserted!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		JLabel lblTickets = new JLabel("Tickets");
		lblTickets.setForeground(Color.WHITE);
		lblTickets.setFont(new Font("Tempus Sans ITC", Font.BOLD, 36));
		lblTickets.setBounds(865, 43, 127, 81);
		contentPane.add(lblTickets);
		
		cbTT = new JComboBox();
		cbTT.setBounds(232, 472, 90, 20);
		contentPane.add(cbTT);
		cbTT.addItem("VIP");
		cbTT.addItem("GeneralAdmission");
		cbTT.addItem("UpperBox");
		cbTT.addItem("LowerBox");
		cbTT.addItem("Patron");
		cbTT.addItem("Others");
		
		cbST = new JComboBox();
		cbST.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if(cbST.getSelectedItem().toString().equals("FreeSeating")) {
					cbVS.removeAllItems();
					cbVS.addItem("NONE");
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		cbST.setModel(new DefaultComboBoxModel(new String[] {"ReservedSeating", "FreeSeating", "NONE"}));
		cbST.setBounds(332, 472, 90, 18);
		contentPane.add(cbST);
		
		cbEID = new JComboBox();
		cbEID.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				String temp = (String) cbEID.getSelectedItem();
				String query = "select * from event where EventID = ?";
				try {
					PreparedStatement statement = connect.prepareStatement(query);
					statement.setString(1, temp);
					ResultSet rs = statement.executeQuery();
					rs = statement.executeQuery();
					if(rs.next()) {
						String add1 = rs.getString("Venue_VenueID");
						tfVID.setText(add1);
					}
				}
				catch(SQLException ev) {
					ev.printStackTrace();
				}
				cbVS.removeAllItems();
				FillComboBoxCVS(cbVS);
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		cbEID.setBounds(432, 472, 90, 20);
		contentPane.add(cbEID);
		
		cbVS = new JComboBox();
		cbVS.setBounds(632, 472, 90, 18);
		contentPane.add(cbVS);
		
		tfVID = new JTextField();
		tfVID.setBounds(532, 472, 90, 20);
		contentPane.add(tfVID);
		tfVID.setColumns(10);
		
		FillComboBoxCID(cbEID);
	}
	
	private void FillComboBoxCID(JComboBox cBox) {
		Connection connect = msc.getConnection();
		
		String query = "SELECT EventID FROM event";
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
	
	private void FillComboBoxCVS(JComboBox cBox) {
		Connection connect = msc.getConnection();

		String temp = (String) cbEID.getSelectedItem();
		String query = "SELECT vs.SeatNumber FROM venueseats vs "
				+ "WHERE vs.Venue_VenueID IN "
				+ " (Select e.Venue_VenueID "
				+ "from event e WHERE e.EventID = " + temp + ")";	
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
