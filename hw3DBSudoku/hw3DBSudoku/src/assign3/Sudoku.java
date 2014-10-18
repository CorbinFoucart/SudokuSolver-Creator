package assign3;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {

	private int[][] grid; 
	
	// Provided Main
	public static void main(String[] args) {
//		Sudoku sudoku;
//		sudoku = new Sudoku(hardGrid);
		
//		System.out.println(sudoku); // print the raw problem
//		System.out.println(grid);
//		int count = sudoku.solve();
//		System.out.println("solutions:" + count);
//		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
//		System.out.println(sudoku.getSolutionText());
	}
	
	
	

	/**
	 * Sets up based on the given ints.
	 * Assume client has passed in valid input
	 */
	public Sudoku(int[][] ints) {
		initGrid(ints);
	}
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		return 0; // YOUR CODE HERE
	}
	
	public 
	
	public String getSolutionText() {
		return ""; // YOUR CODE HERE
	}
	
	public long getElapsed() {
		return 0; // YOUR CODE HERE
	}
	
	// Fills the game grid with correct starter information
	public void initGrid(int[][] ints) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				grid[i][j] = ints[i][j];
			}
		}
	}
	
    // -------------------------------- Spot Code ---------------------------------- //
	
	public class Spot {
		private int row;
		private int col;
		private int sqNum;
		
		private void Spot(int rowNum, int colNum) {
			this.row = rowNum;
			this.col = colNum;
			//Set sqNum
			
		}
		
		private void set(int value) {
			grid[row][col] = value;
		}
		
		// returns an int array of the spot's row
		private int[] getRow() {
			// TODO
		}
		
		// returns an int array of the spot's column
		private int[] getColumn() {
			// TODO
		}
		
		// returns true if value can be placed in spot
		private boolean isLegal(int value) {
			// TODO
		}
		
			private boolean rowLegal(int value) {
				// TODO
			}
			
			private boolean colLegal(int value) {
				// TODO
			}
			
			// checks if the number can be palced in its square
			private boolean sqLegal(int value) {
				// TODO
			}
		
		// checks what square number the piece is in
		private int findSqNum() {
			// TODO
		}

	}
	
	// ------------------------ Provided Helper Methods ---------------------------- //
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}
	
	// ------------------------------ Constant Data ------------------------------- //
	
	// TODO move to top
	
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;

}
