
public class AvailableTickets {

	private String CustomerID;
	private String TicketNumber;
	
	public static final String cTABLE = "customer c";
	public static final String COL_ID = "c.CustomerID";
	public static final String tTABLE = "ticket t";
	public static final String COL_TN = "t.TicketNumber";
	public static final String rTABLE = "customer_has_ticket cht";
	public static final String COL_TNumber = "cht.Ticket_TicketNumber";
	
	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getTicketNumber() {
		return TicketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		TicketNumber = ticketNumber;
	}

	
}
