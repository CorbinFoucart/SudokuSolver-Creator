import java.awt.*;
import javax.swing.*;

import assign3.SudokuFrame;

import java.awt.event.*;
import java.io.*;

public class MetropolisFrame extends JFrame {

	//ivars
	private MetropolisTableModel model;
	private JTable table;
	
	JComponent content; 
	
	// ctor
	public MetropolisFrame() {
		super("Metropolis Viewer");
		
		content = (JComponent)getContentPane();
		content.setLayout(new BorderLayout(6,6));
		
		// Create a table model
		model = new MetropolisTableModel();
		
		// Create a table using that model
		table = new JTable(model);
		
		
		// Non Table Components
		
		// Search Bar
		JPanel searchBar = new JPanel();
		
		JLabel MetLabel = new JLabel("Metropolis: "); 
		searchBar.add(MetLabel);
		JTextField MetSearch = new JTextField();
		MetSearch.setPreferredSize( new Dimension( 200, 24 ) );
		searchBar.add(MetSearch);
		
		JLabel ConLabel = new JLabel("Continent: "); 
		searchBar.add(ConLabel);
		JTextField ConSearch = new JTextField();
		ConSearch.setPreferredSize( new Dimension( 200, 24 ) );
		searchBar.add(ConSearch);
		
		JLabel PopLabel = new JLabel("Population: "); 
		searchBar.add(PopLabel);
		JTextField PopSearch = new JTextField();
		PopSearch.setPreferredSize( new Dimension( 200, 24 ) );
		searchBar.add(PopSearch);
		
		this.add(searchBar, BorderLayout.NORTH);
		
		// Side Panel
		JPanel sideBar = new JPanel();
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

	}
	
	
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
