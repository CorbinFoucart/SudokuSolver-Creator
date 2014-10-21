package assign3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	
	private JPanel panel;
	private JPanel panel2;
	private JButton checkButton;
	private JCheckBox autoCheck;
	private JTextArea editor;
	private JTextArea display;
	private String input;
	 
	public SudokuFrame() {
		super("Sudoku Solver");

		// Button, checkbox
		panel = new JPanel();
		autoCheck = new JCheckBox("Auto Check");
		autoCheck.setSelected(true);
		checkButton = new JButton("Check");
		
		checkButton.addActionListener(new ActionListener() {
			//Execute when button is pressed
	        public void actionPerformed(ActionEvent e){
				input = editor.getText();
				
				try {
					int[][] suGrid = Sudoku.textToGrid(input);
					Sudoku su = new Sudoku(suGrid);
					su.solve();
					String soln = su.getSolutionText();
					display.setText(soln);
					display.append("\n");
					display.append("solutions:" + su.count);
					display.append("\n");
					display.append("elapsed:" + su.getElapsed() + " ms");
				} catch (RuntimeException e1) {
					display.setText(e1.getMessage());
				}            
	        }
	     });      
		
		panel.add(checkButton);
		panel.add(autoCheck);
		
		
		// Editor, solution display
		panel2 = new JPanel();
		
		editor = new JTextArea(15,20);
		editor.setLineWrap(true);
		
		final Document doc = editor.getDocument();
		doc.addDocumentListener(
				
				// create new documentListener on the fly
				new DocumentListener() {
			@ Override
			public void changedUpdate(DocumentEvent e) {
				parsePuzzle(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				parsePuzzle(e);
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				parsePuzzle(e);
				
			}
			
			public void parsePuzzle(DocumentEvent docEvent) {
				if (autoCheck.isSelected()) {
					input = editor.getText();
					
					try {
						int[][] suGrid = Sudoku.textToGrid(input);
						Sudoku su = new Sudoku(suGrid);
						su.solve();
						String soln = su.getSolutionText();
						display.setText(soln);
						display.append("\n");
						display.append("solutions:" + su.count);
						display.append("\n");
						display.append("elapsed:" + su.getElapsed() + " ms");
					} catch (RuntimeException e) {
						display.setText(e.getMessage());
					}
				}
			}
		});
		
		
		
		
		panel2.add(editor, BorderLayout.WEST);
		editor.setBorder(new TitledBorder("Puzzle"));
		
		display = new JTextArea(15,20);
		display.setEditable(false);
		display.setBorder(new TitledBorder("Solution"));
		panel2.add(display, BorderLayout.EAST);
		
		this.add(panel, BorderLayout.SOUTH);
		this.add(panel2, BorderLayout.NORTH);
		
		
		
		// Could do this:
		// setLocationByPlatform(true);
		
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
		
		SudokuFrame frame = new SudokuFrame();
	}

}
