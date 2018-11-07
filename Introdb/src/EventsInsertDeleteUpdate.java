
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
import javax.swing.ComboBoxEditor;
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
import com.toedter.calendar.JDateChooser;
public class EventsInsertDeleteUpdate extends JFrame {
	ArrayList<Events> eventList = new ArrayList<Events>();
	private JPanel contentPane;
	private JTable table;
	private JLabel label;

	MySqlConnect msc = new MySqlConnect();
	
	Events events = new Events();
	private JTextField tfEID;
	private JTextField tfEN;
	private JTextField tfVID;
	JDateChooser dateChooser;
	
	
	private Events toEvents(ResultSet rs) throws SQLException{
		Events events = new Events();
		
		events.setEventID(rs.getString(Events.COL_ID));
		events.setEventName(rs.getString(Events.COL_NAME));
		events.setEventDate(rs.getString(Events.COL_Date));
		events.setVenueID(rs.getString(Events.COL_VID));
		
		return events;
	}
	

	public boolean deleteEvent() {
		events.setEventID(tfEID.getText());
		
		Connection connect = msc.getConnection();
		String query = 	"DELETE FROM " + 
						Events.TABLE + " WHERE " + Events.COL_ID + " = ?";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, events.getEventID());
			System.out.println(query);
			statement.executeUpdate();
			
			statement.close();
			connect.close();
			System.out.println("[Event/s] DELETE SUCCESS!");
		} catch (SQLException ev) {
			System.out.println("[Event/s] DELETE FAILED!");
			ev.printStackTrace();
			return false;
		}
		
		eventList = this.getAll();
		
		showTable(eventList);
		return true;
	}
	
	public ArrayList<Events> getAll() {
		ArrayList <Events> eventList = new ArrayList <Events> ();
		Connection connect = msc.getConnection();
		String query = 	"SELECT * " +
						" FROM " + Events.TABLE;
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				eventList.add(toEvents(rs));
			}
			
			rs.close();
			statement.close();
			connect.close();
			
			System.out.println("[EVENT/S] SELECT SUCCESS!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[EVENT/S] SELECT FAILED!");
			return null;
		}	
		
		return eventList;
	}
	
	public boolean addEvent() {
		Connection connect = msc.getConnection();
		
		events.setEventID(tfEID.getText());
		events.setEventName(tfEN.getText());
		events.setEventDate(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
		events.setVenueID(tfVID.getText());
		
		String query = 	"INSERT INTO " + 
						Events.TABLE + "(`EventID`, `EventName`, `EventDate`, `Venue_VenueID`)" +
						" VALUES(?,?,?,?)";
		
		try {
			PreparedStatement statement = connect.prepareStatement(query);
			
			statement.setString(1, tfEID.getText());
			statement.setString(2, tfEN.getText());
			statement.setString(3, ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
			statement.setString(4, tfVID.getText());
			
			System.out.println(query);
			
			statement.executeUpdate();
			System.out.println("[Venue/s] INSERT SUCCESS!");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Venue/s] INSERT FAILED!");
			return false;
		}
		eventList = this.getAll();
		
		showTable(eventList);
		return true;
	}
	
	
	public void showTable(ArrayList<Events> eventList) {
		DefaultTableModel tModel = (DefaultTableModel)table.getModel();
		
		tModel.setRowCount(0);
		
		Object[] tRows = new Object[4];
		for(int i = 0; i < eventList.size(); i++) {
			tRows[0] = eventList.get(i).getEventID();
			tRows[1] = eventList.get(i).getEventName();
			tRows[2] = eventList.get(i).getEventDate();
			tRows[3] = eventList.get(i).getVenueID();
			
			tModel.addRow(tRows);
		}
	}
	
	public boolean updateEvents() {
		
		try {	
			Connection connect = msc.getConnection();
			
			events.setEventID(tfEID.getText());
			events.setEventName(tfEN.getText());
			events.setEventDate(((JTextField)dateChooser.getDateEditor().getUiComponent()).getText());
			events.setVenueID(tfVID.getText());
			
			
			
			String query = 	"UPDATE " +  Events.TABLE + 
					" SET " +
					Events.COL_NAME + " = ?, " +
					Events.COL_Date + " = ?, " +
					Events.COL_VID + " = ? " +
					" WHERE " + Events.COL_ID + " = ?";
							
			
			PreparedStatement statement = connect.prepareStatement(query);

			statement.setString(1, events.getEventName());
			statement.setString(2, events.getEventDate());
			statement.setString(3, events.getVenueID());
			statement.setString(4, events.getEventID());
			
			System.out.println(query);
			statement.executeUpdate();
			System.out.println("[Events] UPDATE SUCCESS! ");
		} catch (SQLException ev) {
			ev.printStackTrace();
			System.out.println("[Events] UPDATE FAILED! ");
			return false;
		}
		
		eventList = this.getAll();
		
		showTable(eventList);
		return true;
	}

	
	public EventsInsertDeleteUpdate() {
		setResizable(false);
		
		Connection connect = msc.getConnection();
		eventList = this.getAll();
		
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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int rowIndex = table.getSelectedRow();
				
				tfEID.setText(model.getValueAt(rowIndex, 0).toString());
				tfEN.setText(model.getValueAt(rowIndex, 1).toString());
				((JTextField)dateChooser.getDateEditor().getUiComponent()).setText(model.getValueAt(rowIndex, 2).toString());
				tfVID.setText(model.getValueAt(rowIndex, 3).toString());
			}
		});
		table.setFont(new Font("Tempus Sans ITC", Font.BOLD, 11));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EventID", "EventName", "EventDate", "VenueID"
			}
		));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteEvent()) {
					JOptionPane.showMessageDialog(null, "Invalid Admin Username!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Administrator Deleted!!!");
				}
				
			}
		});
		btnDelete.setBounds(865, 359, 107, 37);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(updateEvents()) {
					JOptionPane.showMessageDialog(null, "Event Updated!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Event ID / VenueID is wrong", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnUpdate.setBounds(865, 407, 107, 37);
		contentPane.add(btnUpdate);
		
		JLabel lblEventid = new JLabel("EventID:");
		lblEventid.setForeground(Color.WHITE);
		lblEventid.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEventid.setBounds(102, 431, 62, 18);
		contentPane.add(lblEventid);
		
		JLabel lblFirstname = new JLabel("EventName:");
		lblFirstname.setForeground(Color.WHITE);
		lblFirstname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstname.setBounds(272, 431, 90, 18);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("EventDate:");
		lblLastname.setForeground(Color.WHITE);
		lblLastname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastname.setBounds(428, 431, 93, 18);
		contentPane.add(lblLastname);
		
		JLabel lblCustomercity = new JLabel("VenueID:");
		lblCustomercity.setForeground(Color.WHITE);
		lblCustomercity.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomercity.setBounds(617, 431, 71, 18);
		contentPane.add(lblCustomercity);
		
		tfEID = new JTextField();
		tfEID.setColumns(10);
		tfEID.setBounds(89, 472, 90, 20);
		contentPane.add(tfEID);
		
		tfEN = new JTextField();
		tfEN.setColumns(10);
		tfEN.setBounds(272, 472, 90, 20);
		contentPane.add(tfEN);
		
		tfVID = new JTextField();
		tfVID.setColumns(10);
		tfVID.setBounds(610, 472, 90, 20);
		contentPane.add(tfVID);
		
		showTable(eventList);

		label = new JLabel("");
		label.setIcon(new ImageIcon(CustomerInsertDeleteUpdate.class.getResource("/pics/sm-tickets-logo.png")));
		label.setBounds(842, 135, 150, 165);
		contentPane.add(label);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(addEvent()) {
					JOptionPane.showMessageDialog(null, "Event Inserted!!!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Event ID / VenueID is wrong", "Error", JOptionPane.ERROR_MESSAGE);
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
		
		JLabel lblCustomers = new JLabel("Events");
		lblCustomers.setForeground(Color.WHITE);
		lblCustomers.setFont(new Font("Tempus Sans ITC", Font.BOLD, 32));
		lblCustomers.setBounds(856, 54, 116, 70);
		contentPane.add(lblCustomers);
		
		JComboBox cBox = new JComboBox();
		cBox.addPopupMenuListener(new PopupMenuListener() {
			
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				String temp = (String) cBox.getSelectedItem();
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
		cBox.setBounds(318, 524, 156, 26);
		contentPane.add(cBox);
		FillComboBox(cBox);
		
		JLabel label_1 = new JLabel("VenueName:");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_1.setBounds(346, 503, 100, 18);
		contentPane.add(label_1);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setBounds(430, 472, 91, 20);
		contentPane.add(dateChooser);
		
	}

	public void setConnection(MySqlConnect connection) {
		this.msc = msc;
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
}
