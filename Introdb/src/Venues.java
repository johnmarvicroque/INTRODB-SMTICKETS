
public class Venues {
	private String VenueID;
	private String VenueName;
	private String VenueCity;
	
	public static final String TABLE = "venue";
	public static final String COL_ID = "VenueID";
	public static final String COL_VN = "VenueName";
	public static final String COL_VC = "VenueCity";
	
	public String getVenueID() {
		return VenueID;
	}
	public void setVenueID(String venueID) {
		VenueID = venueID;
	}
	public String getVenueName() {
		return VenueName;
	}
	public void setVenueName(String venueName) {
		VenueName = venueName;
	}
	public String getVenueCity() {
		return VenueCity;
	}
	public void setVenueCity(String venueCity) {
		VenueCity = venueCity;
	}
}
