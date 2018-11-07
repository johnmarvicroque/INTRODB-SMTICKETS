
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnect {
	private final static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://127.0.0.1:3306/";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "789033541";
	private final static String DATABASE = "introdbsmtickets";
	
	public Connection getConnection () {
		try {
			Class.forName(DRIVER_NAME);
			Connection connection = DriverManager.getConnection(
					URL + 
					DATABASE + "?autoReconnect=true&useSSL=false", 
					USERNAME, 
					PASSWORD);
			System.out.println("[MYSQL] Connection successful!");
			return connection;
		} catch (SQLException e) {
			System.out.println("[MYSQL] Did not able to connect");
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("[MYSQL] Did not able to connect");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main (String [] args) {
		MySqlConnect db = new MySqlConnect();
		db.getConnection();
	}
}
