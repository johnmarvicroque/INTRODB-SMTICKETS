
public class report2Att {
	public static final String COL_CID = "CustomerID";
	public static final String COL_NAME = "FirstName";
	public static final String COL_LASTNAME = "LastName";
	public static final String COL_TOTAL = "TotalSpendings";
	public static final String COL_NOT = "NoofTickets";
	
	private String CID;
	private String FN;
	private String LN;
	private String Total;
	private String NOT;
	public String getCID() {
		return CID;
	}
	public void setCID(String cID) {
		CID = cID;
	}
	public String getFN() {
		return FN;
	}
	public void setFN(String fN) {
		FN = fN;
	}
	public String getLN() {
		return LN;
	}
	public void setLN(String lN) {
		LN = lN;
	}
	public String getTotal() {
		return Total;
	}
	public void setTotal(String total) {
		Total = total;
	}
	public String getNOT() {
		return NOT;
	}
	public void setNOT(String nOT) {
		NOT = nOT;
	}
}
