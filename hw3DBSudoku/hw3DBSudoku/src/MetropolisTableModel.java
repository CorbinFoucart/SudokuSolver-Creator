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
	
	/**
	 * Adds an entry to the database with the JTextField data, passed
	 * in here as parameters from the view. 
	 * Assumes the data is complete and conforms to requirements.
	 * Updates the view once the change to the model has been made.
	 * @param metropolis - string of city name
	 * @param continent - string of continent name
	 * @param population - string of Long representing population
	 */
	public void add(String metropolis, String continent, String population) {
		
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
	
		/**
		 * Helper method for add(); constructs a valid execute string in mySQL
		 * consisting of the command to add a city (along with continent & population).
		 * @param met - string of metropolis name
		 * @param con - string of continent name
		 * @param pop - string of population (Long) type
		 * @return mySQL executeUpdate string
		 */
		private String buildInsertString(String met, String con, String pop) {
			String insertString = "";
			String values = " VALUES(\"" + met + "\", \"" + con + "\", \"" + pop + "\");";
			insertString += "INSERT INTO " + dbase_name + values; 
			return insertString;
		}
	
	/**
	 * Queries for the entire database; sets internal model to reflect the query.
	 * Used when a user enters a balnk search query.
	 * Updates the view once query has succesfully been completed. 
	 */
	public void getDatabase() {
		try {
			Statement stmt;
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.executeQuery("SELECT * FROM metropolises ORDER BY metropolis;");
			this.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Allows the user to search the database using the JTextFields. Method 
	 * builds a query string based on the JTextField strings and the JComboBox options
	 * (exact or partial search / greater than or less than or equal to (for population))
	 * 
	 * Once the query string is built, Search() queries the database and updates the model
	 * to reflect the results of the search. Lastly, Search() notifies the view of the changes.
	 * @param metr - string of metropolis name
	 * @param cont - string of continent name
	 * @param popu - string of population (Long)
	 * @param greater - boolean denoting whether the population search should
	 * 					return greater than / less than or equal to the population value. 
	 * @param exact - boolean denoting whether an exact or partial search is preferred.
	 */
	public void Search(String metr, String cont, String popu, 
			boolean greater, boolean exact) {
		String query = buildSearchString(metr, cont, popu, greater, exact);
//		System.out.println(query);
		
		try {
			Statement stmt;
			stmt = con.createStatement();
			stmt.executeQuery("USE " + database);
			rs = stmt.executeQuery(query);
			this.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
		/**
		 *  Helper method for Search() that constructs the mySQL query string, given the 
		 *  inputs to Search().
		 *   
		 *  EX. Input: ("Rome", "Europe", "", true, true)
		 *  Output: "SELECT * FROM metropolises WHERE metropolis = "Boston" AND continent = Europe;"
		 *   
	     * @param metr - string of metropolis name
	     * @param cont - string of continent name
	     * @param popu - string of population (Long)
	     * @param greater - boolean denoting whether the population search should
	     * 					return greater than / less than or equal to the population value. 
	     * @param exact - boolean denoting whether an exact or partial search is preferred.
		 * @return - mySQL query string
		 */
		public String buildSearchString(String metr, String cont, String popu, 
				boolean greater, boolean exact) {
			String qString = "SELECT * FROM " + dbase_name + " WHERE ";
			
			ArrayList<String> queries = new ArrayList<String>();
			if (!metr.equals("")) {
				String str = "";
				if (exact) {
					str += "metropolis = \"" + metr + "\" ";
				}else {
					str += "metropolis LIKE \"" + "%" + metr + "%" + "\" ";
				}				
				queries.add(str);
			}
			
			if (!cont.equals("")) {
				String str = "";
				if (exact) {
					str += " continent = \"" + cont + "\" ";
				} else {
					str += " continent LIKE \"" + "%" + cont + "%" + "\" ";
				}
				queries.add(str);
			}
			
			if (!popu.equals("")) {
				String str = "population ";
				if (greater) {
					str += "> ";
				}else {
					str += "< " + popu + " OR " + "population = ";
				}
				str += popu;
				queries.add(str);
			}
			
			boolean first = true;
			for (int i = 0; i < queries.size(); i++) {
				if (!first) qString += " AND ";
				qString += queries.get(i);
				first = false;
			}
			
			qString += ";";
			
			return qString;
		}
	
	// ----------------------------- Overridden Methods ---------------------------------//
	
	/**
	 * Overridden getRowCount() method of AbstractTableModel.
	 * Returns the number of rows stored in the ResultSet object
	 */
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
	
	/**
	 * Overridden getColumnCount() method of AbstractTableModel.
	 * Returns integer of the number of columns in the ResultSet object
	 * after a database query.
	 */
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
	
	/**
	 * Overridden getColumnName() method of AbstractTableModel.
	 * Returns the string of the name from the instance variable array
	 * of column names, which we know to be the same throughout the 
	 * runtime of the class.
	 */
	@Override
	public String getColumnName(int i) {
		return colNames.get(i);
	}

	/**
	 * Overridden getValueAt() method of AbstractTableModel.
	 * Manipulates the cursor of the ResultSet object to return 
	 * the correct table value. Note that we add one to indexes
	 * because mySQL indexes from 1 rather than 0.
	 */
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
