import java.sql.Date;

public class Ticket {
	private String TicketNumber;
	private String TicketPrice;
	private String TicketType;
	private String SeatingType;
	private String EventID;
	private String VenueID;
	private String VenueSeats;
	
	public static final String TABLE = "ticket";
	public static final String COL_TN = "TicketNumber";
	public static final String COL_TP = "TicketPrice";
	public static final String COL_TT = "TicketType";
	public static final String COL_ST = "SeatingType";
	public static final String COL_EID = "Event_EventID";
	public static final String COL_VID = "Event_Venue_VenueID";
	public static final String COL_SN = "VenueSeats_SeatNumber";
	
	public String getTicketNumber() {
		return TicketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		TicketNumber = ticketNumber;
	}
	public String getTicketPrice() {
		return TicketPrice;
	}
	public void setTicketPrice(String ticketPrice) {
		TicketPrice = ticketPrice;
	}
	public String getTicketType() {
		return TicketType;
	}
	public void setTicketType(String ticketType) {
		TicketType = ticketType;
	}
	public String getSeatingType() {
		return SeatingType;
	}
	public void setSeatingType(String seatingType) {
		SeatingType = seatingType;
	}
	public String getEventID() {
		return EventID;
	}
	public void setEventID(String eventID) {
		EventID = eventID;
	}
	public String getVenueID() {
		return VenueID;
	}
	public void setVenueID(String venueID) {
		VenueID = venueID;
	}
	public String getVenueSeats() {
		return VenueSeats;
	}
	public void setVenueSeats(String venueSeats) {
		VenueSeats = venueSeats;
	}
	
	
}
