
public class Reservations {
	private String CustomerID;
	private String TicketNumber;
	private String PaymentType;
	private String TransactionDate;
	
	public static final String TABLE = "customer_has_ticket";
	public static final String COL_CustID = "Customer_CustomerID";
	public static final String COL_TNumber = "Ticket_TicketNumber";
	public static final String COL_PType = "PaymentType";
	public static final String COL_TDate = "TransactionDate";
	
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
	public String getPaymentType() {
		return PaymentType;
	}
	public void setPaymentType(String paymentType) {
		PaymentType = paymentType;
	}
	public String getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}
	
}