
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	JComboBox cbReports;

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 483);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Customers");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerInsertDeleteUpdate c = new CustomerInsertDeleteUpdate();
				c.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnNewButton.setBounds(36, 95, 131, 42);
		contentPane.add(btnNewButton);
		
		JButton btnEvent = new JButton("Tickets");
		btnEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TicketInsertDeleteUpdate t = new TicketInsertDeleteUpdate();
				t.setVisible(true);
				dispose();
			}
		});
		btnEvent.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnEvent.setBounds(36, 201, 131, 42);
		contentPane.add(btnEvent);
		
		JButton btnVenue = new JButton("Venue");
		btnVenue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VenueInsertDeleteUpdate v = new VenueInsertDeleteUpdate();
				v.setVisible(true);
				dispose();
			}
		});
		btnVenue.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnVenue.setBounds(36, 307, 131, 42);
		contentPane.add(btnVenue);
		
		JButton btnEvents = new JButton("Events");
		btnEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventsInsertDeleteUpdate e = new EventsInsertDeleteUpdate();
				e.setVisible(true);
				dispose();
			}
		});
		btnEvents.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnEvents.setBounds(36, 254, 131, 42);
		contentPane.add(btnEvents);
		
		JButton btnReservations = new JButton("Reservations");
		btnReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservationsInsertUpdateDelete r = new ReservationsInsertUpdateDelete();
				r.setVisible(true);
				dispose();
			}
		});
		btnReservations.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnReservations.setBounds(36, 148, 131, 42);
		contentPane.add(btnReservations);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(MainMenu.class.getResource("/pics/sm-tickets-footer.png")));
		label.setBounds(237, 42, 300, 111);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel("Created by: GROUP 4");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Trajan Pro", Font.PLAIN, 18));
		lblNewLabel.setBounds(277, 164, 226, 61);
		contentPane.add(lblNewLabel);
		
		JButton vSeats = new JButton("VenueSeats");
		vSeats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VenueSeatsInsertDeleteUpdate vs = new VenueSeatsInsertDeleteUpdate();
				vs.setVisible(true);
				dispose();
			}
		});
		vSeats.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		vSeats.setBounds(36, 360, 131, 42);
		contentPane.add(vSeats);
		
		JButton btnEmployee = new JButton("Administrators");
		btnEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListofEmployees admin = new ListofEmployees();
				admin.setVisible(true);
				dispose();
			}
		});
		btnEmployee.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnEmployee.setBounds(36, 42, 131, 42);
		contentPane.add(btnEmployee);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login l = new login();
				l.setVisible(true);
				dispose();
			}
		});
		btnExit.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
		btnExit.setBounds(295, 360, 131, 42);
		contentPane.add(btnExit);
		
		cbReports = new JComboBox();
		cbReports.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				if(cbReports.getSelectedItem().toString().equals("List Of Venues Within Customer's City")) {
					Report1 r1 = new Report1();
					r1.setVisible(true);
					dispose();
				}
				else if(cbReports.getSelectedItem().toString().equals("List of Customers and Their Total Spendings")) {
					Report2 r2 = new Report2();
					r2.setVisible(true);
					dispose();
				}
				else if(cbReports.getSelectedItem().toString().equals("Expected Number of Attendees for Each Event")) {
					Report3 r3 = new Report3();
					r3.setVisible(true);
					dispose();
				}
				else if(cbReports.getSelectedItem().toString().equals("No. of Tickets that are still Available")) {
					Report4 r4 = new Report4();
					r4.setVisible(true);
					dispose();
				}
				else if(cbReports.getSelectedItem().toString().equals("Total Income of Each Event")) {
					Report5 r5 = new Report5();
					r5.setVisible(true);
					dispose();
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}
		});
		cbReports.setModel(new DefaultComboBoxModel(new String[] {"List Of Venues Within Customer's City", "List of Customers and Their Total Spendings", "Expected Number of Attendees for Each Event", "No. of Tickets that are still Available", "Total Income of Each Event"}));
		cbReports.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 16));
		cbReports.setBounds(243, 279, 294, 50);
		contentPane.add(cbReports);
		
		JLabel lblTransactionReports = new JLabel("TRANSACTION REPORTS");
		lblTransactionReports.setFont(new Font("Tempus Sans ITC", Font.BOLD, 14));
		lblTransactionReports.setForeground(Color.WHITE);
		lblTransactionReports.setBounds(287, 223, 185, 45);
		contentPane.add(lblTransactionReports);
	}
}
