import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import java.sql.ResultSetMetaData;

import assign3.MyDBInfo;


public class MetropolisTableModel extends AbstractTableModel {
	
	private ArrayList<String> colNames;
//	private ArrayList<ArrayList> data;
//	private ArrayList<String> nameData;
//	private ArrayList<String> contData;
//	private ArrayList<Long> popData;
	private Connection con;
	private ResultSet rs;
	
	// ctor
	public MetropolisTableModel() {

		// construct column names
		colNames = new ArrayList<String>(
				Arrays.asList("Metropolis", "Continent", "Population"));
		
		// connection to database
		try {
			// make sure JDBC driver is loaded
			Class.forName("com.mysql.jdbc.Driver");
			
			// create connection between our account and the database
			con = DriverManager.getConnection
					("jdbc:mysql://" + server, account, password);
						
			Statement stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.executeQuery("SELECT * FROM metropolises WHERE 1=2;");
					
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	// adds data from the text fields to the database
	public void add(String metropolis, String continent, String population) {
		System.out.println(metropolis);
		System.out.println(continent);
		System.out.println(population);
		
		try {
			Statement stmt;
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			String insStr = buildInsertString(metropolis, continent, population);
			System.out.println(insStr);
			stmt.executeUpdate(insStr);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.fireTableDataChanged();
	}
	
		private String buildInsertString(String met, String con, String pop) {
			String insertString = "";
			String values = " VALUES(\"" + met + "\", \"" + con + "\", \"" + pop + "\");";
			insertString += "INSERT INTO " + dbase_name + values; 
			return insertString;
		}
	
	// gets entire database, saves it to model ivars
	public void getDatabase() {
		try {
			Statement stmt;
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.executeQuery("SELECT * FROM metropolises ORDER BY metropolis;");
			
			while(rs.next()) {
				String name = rs.getString("metropolis");
				String conName = rs.getString("continent");
				long pop = rs.getLong("population");
				
				System.out.println(name + "\t" + conName + "\t" + pop);
			}
			this.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void exactSearch(String metr, String cont, Long popu, boolean greater) {
		String query = buildExactString(metr, cont, popu, greater);
		System.out.println(query);
	}
	
		public String buildExactString(String metr, String cont, Long popu, boolean greater) {
			boolean first = true;
			String qString = "SELECT * FROM " + dbase_name + " ";
			if (!metr.equals("")) {
				first = false;
				qString += "WHERE metropolis = \"" + metr + "\"";
			}
			
			if (!cont.equals("")) {
				if (first) {
					first = false;
					qString += " WHERE continent = \"" + cont + "\"";
				}else  {
					qString += " AND WHERE continent = \"" + cont + "\"";
				}
			}
			
			if (popu != null) {
				if (first) {
					first = false;
					qString += " WHERE population = \"" + popu + "\"";
				}else  {
					qString += " AND WHERE continent = \"" + popu + "\"";
				}
			}
			
			qString += ";";
			
			return qString;
		}
	
	// ----------------------------- Overridden Methods ---------------------------------//
	
	@Override
	public int getRowCount() {
		try {
			rs.last();
			int rows = rs.getRow();
			rs.beforeFirst();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return 0;
	}

	@Override
	public int getColumnCount() {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNum = rsmd.getColumnCount();
			return colNum;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public String getColumnName(int i) {
		return colNames.get(i);
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		try {
			rs.absolute(arg0 + 1);
			return rs.getObject(arg1 + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	// ---------------------------------- Login Constants ------------------------- //
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	static String dbase_name = "metropolises";
	
	
}
