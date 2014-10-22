import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;

import assign3.SudokuFrame;

import java.awt.event.*;
import java.io.*;

public class MetropolisFrame extends JFrame{

	//ivars
	private MetropolisTableModel model;
	private JTable table;
	private JButton add;
	private JButton search;
	private JTextField MetSearch;
	private JTextField ConSearch;
	private JTextField PopSearch;
	private JLabel errorMessage;
	private boolean exact;
	private boolean greater;
	private JComboBox searchList;
	private JComboBox popList;
	
	
	JComponent content; 
	
	// ctor
	public MetropolisFrame() {
		super("Metropolis Viewer");
		
		content = (JComponent)getContentPane();
		content.setLayout(new BorderLayout(10,10));
		
		// Create a table model
		model = new MetropolisTableModel();
		
		// Create a table using that model
		makeTable();		
		
		// Non Table Components
		makeSearchBar();
		makeSidePanel();
		
		
		// Display the Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

	}
	
	// ----------------------------------- Initialization of GUI -------------------------------------- //
	
	public void makeTable() {
		table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		this.add(pane, BorderLayout.CENTER);
		
	}

	public void makeSearchBar() {
		JPanel searchBar = new JPanel();
		
		JLabel MetLabel = new JLabel("Metropolis: "); 
		searchBar.add(MetLabel);
		MetSearch = new JTextField();
		MetSearch.setPreferredSize( new Dimension( 200, 24 ) );
		searchBar.add(MetSearch);
		
		JLabel ConLabel = new JLabel("Continent: "); 
		searchBar.add(ConLabel);
		ConSearch = new JTextField();
		ConSearch.setPreferredSize( new Dimension( 200, 24 ) );
		searchBar.add(ConSearch);
		
		JLabel PopLabel = new JLabel("Population: "); 
		searchBar.add(PopLabel);
		PopSearch = new JTextField();
		PopSearch.setPreferredSize( new Dimension( 200, 24 ) );
		searchBar.add(PopSearch);
		
		this.add(searchBar, BorderLayout.NORTH);
	}
	
	public void makeSidePanel() {
		
		JPanel sideBar = new JPanel(new GridLayout(3,1,20,20));
		
		// Buttons
		JPanel sideBarButtons = new JPanel(new GridLayout(2,1));
		JButton add = new JButton("Add");
		add.setPreferredSize( new Dimension(80, 30) );
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String met = MetSearch.getText();
				String con = ConSearch.getText();
				String pop = PopSearch.getText();
				
				// in case of incomplete entry
				if (fieldEmpty()) {
					errorMessage.setText("Enter a value in all fields!");
				}else {
					model.add(met, con, pop);
					errorMessage.setText("");
				}
				
				clearSearchBar();
				
			}
		});
		
		// search button
		JButton search = new JButton("Search");
		search.setPreferredSize( new Dimension(80, 30) );
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fieldsEmpty()) {
					model.getDatabase();
				}else {
					String met = MetSearch.getText();
					String con = ConSearch.getText();
					String pop = PopSearch.getText();
					if (exact) {
						model.exactSearch(met, con, pop, greater);
					} else {
//						model.partialSearch();
					}
				}
				clearSearchBar();
			}
		});
			
		// add buttons to sidebar
		sideBarButtons.add(add);
		sideBarButtons.add(search);
		sideBar.add(sideBarButtons);
		
		// Dropdowns
		final String[] popStrings = { "Population larger than", "Population smaller than or equal to"};
		final String[] searchStrings = {"Exact Search", "Partial Search"};

		//Create the dropdowns
		JPanel sideBarDropDowns = new JPanel(new GridLayout(3,1));
		
			popList = new JComboBox(popStrings);
			popList.setSelectedIndex(0);
			popList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String str = popList.getSelectedItem().toString();
					if (str.equals(popStrings[0])) {
						greater = true;
					}else {
						greater = false;
					}
				}			
			});
			greater = true;
			
			
			searchList = new JComboBox(searchStrings);
			searchList.setSelectedIndex(0);
			searchList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String str = searchList.getSelectedItem().toString();
					if (str.equals(searchStrings[0])) {
						exact = true;
					}else {
						exact = false;
					}
				}			
			});
			exact = true;
		
		sideBarDropDowns.add(popList, BorderLayout.NORTH);
		sideBarDropDowns.add(searchList, BorderLayout.SOUTH);
		sideBarDropDowns.setBorder(new TitledBorder("Search Options"));
		sideBar.add(sideBarDropDowns);
		
		
		// error message space
		errorMessage = new JLabel("");
		errorMessage.setPreferredSize( new Dimension(175, 30) );
		sideBar.add(errorMessage);
		
		// add our beautiful panel to GUI
		this.add(sideBar, BorderLayout.EAST);
	}
	
	// returns true if at least one textfield is empty
	public boolean fieldEmpty() {
		String met = MetSearch.getText();
		String con = ConSearch.getText();
		String pop = PopSearch.getText();
		return (met.equals("") || con.equals("") || pop.equals(""));
	}
	
	// returns true if all textfields are empty
	public boolean fieldsEmpty() {
		String met = MetSearch.getText();
		String con = ConSearch.getText();
		String pop = PopSearch.getText();
		return (met.equals("") && con.equals("") && pop.equals(""));
	}
	
	public void clearSearchBar() {
		// make the search bar blank again
		MetSearch.setText("");
		ConSearch.setText("");
		PopSearch.setText("");
	}
	
	
	// ----------------------------------- main ------------------------------------- //
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		MetropolisFrame frame = new MetropolisFrame();
	}
	
	
	
	
	
	
}
