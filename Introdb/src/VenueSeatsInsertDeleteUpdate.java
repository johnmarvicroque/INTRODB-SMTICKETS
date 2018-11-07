
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
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
public class VenueSeatsInsertDeleteUpdate extends JFrame {
	
	ArrayList<VenueSeats> venueSeatList = new ArrayList<VenueSeats>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	VenueSeats venueSeats = new VenueSeats();
	private JTextField tfVID;
	private JTextField tfSN;
	
	
	private VenueSeats toVenueSeats(ResultSet rs) throws SQLException{
		VenueSeats venueSeats = new VenueSeats();
		
		venueSeats.setVenueID(rs.getString(VenueSeats.COL_VID));
		venueSeats.setSeatNumber(rs.getString(VenueSeats.COL_SN));
		
		return venueSeats;
	}
	

	public boolean deleteVenueSeats() {
		venueSeats.setSeatNumber(tfSN.getText());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						VenueSeats.TABLE + " WHERE " + VenueSeats.COL_SN + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, venueSeats.getSeatNumber());
			statement.executeUpdate();
			
			statement.close();
			connect.close();
			System.out.println("[VENUESeats] DELETE SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[VENUESeats] DELETE FAILED!");
			ev.printStackTrace();
			return false;
		}
		
		venueSeatList = this.getAll();
		
		showTable(venueSeatList);
		return true;
	}
	
	public ArrayList<VenueSeats> getAll() {
		ArrayList <VenueSeats> venueSeatList = new ArrayList <VenueSeats> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + VenueSeats.TABLE;
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				venueSeatList.add(toVenueSeats(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[VenueSeats] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[VenueSeats] SELECT FAILED!");
			return null;
		}	
		
		return venueSeatList;
	}
	
	public boolean addVenueSeats() {
		Connection connect = msc.getConnection();
		
		venueSeats.setVenueID(tfVID.getText());
		venueSeats.setSeatNumber(tfSN.getText());
		
		String query = 	"INSERT INTO " + 
						VenueSeats.TABLE + "(`Venue_VenueID`, `SeatNumber`)" +
						" VALUES(?,?)";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, tfVID.getText());
			statement.setString(2, tfSN.getText());
			
			
			System.out.println(query);
			
			statement.executeUpdate();
			System.out.println("[VenueSeats] INSERT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[VenueSeats] INSERT FAILED!");
			return false;
		}
		venueSeatList= this.getAll();
		
		showTable(venueSeatList);
		return true;
	}
	
	
	public void showTable(ArrayList<VenueSeats> venueSeatList) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[2];
		for(int i = 0; i < venueSeatList.size(); i++) {
			tRows[0] = venueSeatList.get(i).getVenueID();
			tRows[1] = venueSeatList.get(i).getSeatNumber();
			
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateVenueSeats() {
		
		try {	
			Connection connect = msc.getConnection();
			
			venueSeats.setVenueID(tfVID.getText());
			venueSeats.setSeatNumber(tfSN.getText());
			
			String query = 	"UPDATE " +  VenueSeats.TABLE + 
					" SET " +
					VenueSeats.COL_VID + " = ?, " +
					VenueSeats.COL_SN + " = ?, " +
					" WHERE " + VenueSeats.COL_SN + " = ?";
							
			
			PreparedStatement statement = connect.prepareStatement(query);

			statement.setString(1, tfVID.getText());
			statement.setString(2, tfSN.getText());
			statement.setString(3, tfSN.getText());
			System.out.println(query);
			statement.executeUpdate();
			
			System.out.println("[VenueSeats] UPDATE SUCCESS! ");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[VenueSeats] UPDATE FAILED! ");
			return false;
		}
		
		venueSeatList = this.getAll();
		
		showTable(venueSeatList);
		return true;
	}
	
	public VenueSeatsInsertDeleteUpdate() {
		setResizable(false);
		
		
		venueSeatList = this.getAll();
		
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
				"VenueID", "SeatNumber"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteVenueSeats()) {
					JOptionPane.showMessageDialog(null, "Invalid SeatNumber!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Seat Deleted!!!");
				}
				
			}
		});
		btnDelete.setBounds(865, 359, 107, 37);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(updateVenueSeats()) {
					JOptionPane.showMessageDialog(null, "Seat Updated!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong Input!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnUpdate.setBounds(865, 407, 107, 37);
		contentPane.add(btnUpdate);
		
		showTable(venueSeatList);

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 135, 150, 165);
		contentPane.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(addVenueSeats()) {
					JOptionPane.showMessageDialog(null, "Seat Inserted!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Wrong Input", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		JLabel lblCustomers = new JLabel("VenueSeats\r\n");
		lblCustomers.setForeground(Color.WHITE);
		lblCustomers.setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));
		lblCustomers.setBounds(833, 43, 185, 81);
		contentPane.add(lblCustomers);
		
		JLabel VENUEIDLBL = new JLabel("VenueID:");
		VENUEIDLBL.setForeground(Color.WHITE);
		VENUEIDLBL.setFont(new Font("Tahoma", Font.BOLD, 15));
		VENUEIDLBL.setBounds(266, 440, 77, 18);
		contentPane.add(VENUEIDLBL);
		
		JLabel lblSeatnumber = new JLabel("SeatNumber:");
		lblSeatnumber.setForeground(Color.WHITE);
		lblSeatnumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSeatnumber.setBounds(450, 440, 100, 18);
		contentPane.add(lblSeatnumber);
		
		tfVID = new JTextField();
		tfVID.setColumns(10);
		tfVID.setBounds(256, 472, 100, 20);
		contentPane.add(tfVID);
		
		tfSN = new JTextField();
		tfSN.setColumns(10);
		tfSN.setBounds(450, 472, 100, 20);
		contentPane.add(tfSN);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			Connection connect = msc.getConnection();
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				String temp = (String) comboBox.getSelectedItem();
				String query = "select * from venue where VenueName = ?";
				try {
					PreparedStatement statement = connect.prepareStatement(query);
					statement.setString(1, temp);
					ResultSet rs = statement.executeQuery();
					rs = statement.executeQuery();
					if(rs.next()) {
						String add1 = rs.getString("VenueID");
						tfVID.setText(add1);
					}
				}
				catch(SQLException ev) {
					ev.printStackTrace();
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		comboBox.setBounds(323, 536, 156, 25);
		contentPane.add(comboBox);
		FillComboBox(comboBox);
		
		JLabel lblVenuename = new JLabel("VenueName:");
		lblVenuename.setForeground(Color.WHITE);
		lblVenuename.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblVenuename.setBounds(357, 507, 100, 18);
		contentPane.add(lblVenuename);
	}

	private void FillComboBox(JComboBox cBox) {
		Connection connect = msc.getConnection();
		
		String query = "SELECT VenueName FROM venue";
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
