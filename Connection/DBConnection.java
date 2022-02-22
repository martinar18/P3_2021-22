package Connection;


import java.sql.*;

public class DBConnection {

	public DBConnection() {
		
	}

	public static Connection openConnection()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection c=null;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_rentit2?user=root&password=");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	public void closeConnection(Connection c) {
		
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
