
public class Events {
	private String EventID;
	private String EventName;
	private String EventDate;
	private String VenueID;
	
	public static final String TABLE = "event";
	public static final String COL_ID = "EventID";
	public static final String COL_NAME = "EventName";
	public static final String COL_Date = "EventDate";
	public static final String COL_VID = "Venue_VenueID";
	
	public String getEventID() {
		return EventID;
	}
	public void setEventID(String eventID) {
		EventID = eventID;
	}
	public String getEventName() {
		return EventName;
	}
	public void setEventName(String eventName) {
		EventName = eventName;
	}
	public String getEventDate() {
		return EventDate;
	}
	public void setEventDate(String eventDate) {
		EventDate = eventDate;
	}
	public String getVenueID() {
		return VenueID;
	}
	public void setVenueID(String venueID) {
		VenueID = venueID;
	}
}
