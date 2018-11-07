
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

public class VenueInsertDeleteUpdate extends JFrame {
	ArrayList<Venues> venueList = new ArrayList<Venues>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Venues venues = new Venues();
	private JTextField tfCustID;
	private JTextField tfVN;
	private JTextField tfVC;
	
	
	private Venues toVenues(ResultSet rs) throws SQLException{
		Venues venues = new Venues();
		
		venues.setVenueID(rs.getString(Venues.COL_ID));
		venues.setVenueName(rs.getString(Venues.COL_VN));
		venues.setVenueCity(rs.getString(Venues.COL_VC));
		
		return venues;
	}
	

	public boolean deleteVenues() {
		venues.setVenueID(tfCustID.getText());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						Venues.TABLE + " WHERE " + Venues.COL_ID + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, venues.getVenueID());
			System.out.println(query);
			statement.executeUpdate();
			
			statement.close();
			connect.close();
			System.out.println("[Venue/s] DELETE SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[Venue/s] DELETE FAILED!");
			ev.printStackTrace();
			return false;
		}
		
		venueList = this.getAll();
		
		showTable(venueList);
		return true;
	}
	
	public ArrayList<Venues> getAll() {
		ArrayList <Venues> venueList = new ArrayList <Venues> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + Venues.TABLE;

		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				venueList.add(toVenues(rs));
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
		
		return venueList;
	}
	
	public boolean addVenues() {
		Connection connect = msc.getConnection();
		
		venues.setVenueID(tfCustID.getText());
		venues.setVenueName(tfVN.getText());
		venues.setVenueName(tfVC.getText());
		
		String query = 	"INSERT INTO " + 
						Venues.TABLE + "(VenueID, VenueName, VenueCity)" +
						" VALUES(?,?,?)";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, tfCustID.getText());
			statement.setString(2, tfVN.getText());
			statement.setString(3, tfVC.getText());
			
			statement.executeUpdate();
			System.out.println("[Venue] INSERT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Venue] INSERT FAILED!");
			return false;
		}
		venueList = this.getAll();
		
		showTable(venueList);
		return true;
	}
	
	public void showTable(ArrayList<Venues> venueList) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[3];
		for(int i = 0; i < venueList.size(); i++) {
			tRows[0] = venueList.get(i).getVenueID();
			tRows[1] = venueList.get(i).getVenueName();
			tRows[2] = venueList.get(i).getVenueCity();
			
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateVenue() {
		
		try {	
			Connection connect = msc.getConnection();
			venues.setVenueID(tfCustID.getText());
			venues.setVenueName(tfVN.getText());
			venues.setVenueCity(tfVC.getText());
						
			String query = 	"UPDATE " +  Venues.TABLE + 
					" SET " +
					Venues.COL_VN + " = ?, " +
					Venues.COL_VC + " = ? " +
					" WHERE " + Venues.COL_ID + " = ?";
							
			
			PreparedStatement statement = connect.prepareStatement(query);

			
			statement.setString(1, venues.getVenueName());
			statement.setString(2, tfVC.getText());
			statement.setString(3, venues.getVenueID());
			System.out.println(query);
			
			statement.executeUpdate();
			System.out.println("[venue/s] UPDATE SUCCESS! ");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Venue/s] UPDATE FAILED! ");
			return false;
		}
		
		venueList = this.getAll();
		
		showTable(venueList);
		return true;
	}
	
	public VenueInsertDeleteUpdate() {
		setResizable(false);
		
		
		venueList = this.getAll();
		
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
		table.setFont(new Font("Tempus Sans ITC", Font.BOLD, 13));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"VenueID", "VenueName", "VenueCity"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteVenues()) {
					JOptionPane.showMessageDialog(null, "Invalid Venue ID!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Venue Deleted!!!");
				}
				
			}
		});
		btnDelete.setBounds(865, 359, 107, 37);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateVenue()) {
					JOptionPane.showMessageDialog(null, "Venue Updated!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnUpdate.setBounds(865, 407, 107, 37);
		contentPane.add(btnUpdate);
		
		JLabel Venuenamelbl = new JLabel("VenueID:");
		Venuenamelbl.setForeground(Color.WHITE);
		Venuenamelbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		Venuenamelbl.setBounds(236, 443, 77, 18);
		contentPane.add(Venuenamelbl);
		
		JLabel lblFirstname = new JLabel("VenueName:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstname.setBounds(386, 443, 100, 18);
		contentPane.add(lblFirstname);
		
		tfCustID = new JTextField();
		tfCustID.setColumns(10);
		tfCustID.setBounds(225, 472, 100, 20);
		contentPane.add(tfCustID);
		
		tfVN = new JTextField();
		tfVN.setColumns(10);
		tfVN.setBounds(377, 472, 109, 20);
		contentPane.add(tfVN);
		
		showTable(venueList);

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 135, 150, 165);
		contentPane.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(addVenues()) {
					JOptionPane.showMessageDialog(null, "Venue Inserted!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Error Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		JLabel lblCustomers = new JLabel("Venues");
		lblCustomers.setForeground(Color.WHITE);
		lblCustomers.setFont(new Font("Tempus Sans ITC", Font.BOLD, 34));
		lblCustomers.setBounds(842, 43, 125, 81);
		contentPane.add(lblCustomers);
		
		JLabel tfVClbl = new JLabel("VenueCity:");
		tfVClbl.setForeground(Color.WHITE);
		tfVClbl.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfVClbl.setBounds(548, 443, 100, 18);
		contentPane.add(tfVClbl);
		
		tfVC = new JTextField();
		tfVC.setColumns(10);
		tfVC.setBounds(539, 472, 109, 20);
		contentPane.add(tfVC);
		
		
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
	}
}
