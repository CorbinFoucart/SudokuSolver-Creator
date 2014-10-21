import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import assign3.MyDBInfo;


public class MetropolisTableModel extends AbstractTableModel {
	
	private ArrayList<String> colNames;
	private ArrayList<ArrayList> data;
	
	// ctor
	public MetropolisTableModel() {
		colNames = new ArrayList<String>();
		data = new ArrayList<ArrayList>();
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}


	// ---------------------------------- Login Constants ------------------------- //
	static String account = MyDBInfo.MYSQL_USERNAME;
	static String password = MyDBInfo.MYSQL_PASSWORD;
	static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
	static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	
	
}
