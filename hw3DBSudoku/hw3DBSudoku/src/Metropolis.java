import  java.sql.*;

import javax.swing.table.AbstractTableModel;

import assign3.MyDBInfo;

public class Metropolis {

	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	
	public static void main(String[] args) {
		try {
			// make sure JDBC driver is loaded
			Class.forName("com.mysql.jdbc.Driver");
			
			// create connection between our account and the database
			Connection con = DriverManager.getConnection
					("jdbc:mysql://" + server, account, password);
			
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			ResultSet rs = stmt.executeQuery("SELECT * FROM metropolises;");
			
			while(rs.next()) {
				String name = rs.getString("metropolis");
				long pop = rs.getLong("population");
				System.out.println(name + "\t" + pop);
			}
			
			con.close();			
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
}
