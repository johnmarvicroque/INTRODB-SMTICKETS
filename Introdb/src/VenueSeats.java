
public class VenueSeats {
	private String VenueID;
	private String SeatNumber;
	
	public static final String TABLE = "venueseats";
	public static final String COL_VID = "Venue_VenueID";
	public static final String COL_SN = "SeatNumber";
	
	public String getVenueID() {
		return VenueID;
	}
	public void setVenueID(String venueID) {
		VenueID = venueID;
	}
	public String getSeatNumber() {
		return SeatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		SeatNumber = seatNumber;
	}
	
	
}
