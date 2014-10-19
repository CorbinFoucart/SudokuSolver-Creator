package assign3;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {

	private int[][] startergrid;
	private int[][] grid; 
	private int solutionCount;
	private boolean printed;
	private ArrayList<Spot> emptySpots;
	
	// Provided Main
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		

		
		
//		System.out.println(sudoku); // print the raw problem
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
		this.startergrid = ints;
		this.grid = ints;
		this.solutionCount = 0;
		this.printed = false;
		
		// initialize empty spots
		emptySpots = getEmptySpots();
		Collections.sort(emptySpots);
		
		// testing
		printGrid();
		System.out.println();
		
		// empty spots
		// validated
		for (int i = 0; i < emptySpots.size(); i++) {
			String str = emptySpots.get(i).getCandidates().toString();
			System.out.println(str);
		}

//		// candidate testing
//		Spot sp = new Spot(0,3);
//		String Cstr = printCandidates(sp);
//		System.out.println(Cstr);

		// verified
//		// get row 
//		for (int i = 0; i < SIZE; i++) {
//			Spot sp = new Spot(i,i);
//			System.out.println(i2s(sp.getRow()));
//		}
//		
//		System.out.println();
//		
//		//get col
//		for (int i = 0; i < SIZE; i++) {
//			Spot sp = new Spot(i,i);
//			System.out.println(i2s(sp.getColumn()));
//		}		
//		// get Square
//		// VALIDATED
//		Spot sp = new Spot(2,8);
//		System.out.println(i2s(sp.getSq()));
		
//		// check legality
//		// VALIDATED
//		Spot sp = new Spot(7,0);
//		int value = 1;
//		System.out.println(sp.rowLegal(value));
//		System.out.println(sp.colLegal(value));
//		System.out.println(sp.sqLegal(value));
		
		
	}
	
	public ArrayList<Spot> getEmptySpots() {
		ArrayList<Spot> eSpots = new ArrayList<Spot>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (grid[i][j] == 0) {
					Spot sp = new Spot(i,j);
					eSpots.add(sp);
				}
			}
		}
		return eSpots;
	}
	
	
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		return 0;
	}
	
	public String getSolutionText() {
		return ""; // YOUR CODE HERE
	}
	
	public long getElapsed() {
		return 0; // YOUR CODE HERE
	}
	

	
	
    // -------------------------------- Spot Code ---------------------------------- //
	
	public class Spot implements Comparable<Spot> {
		private int value;
		private int row;
		private int col;
		private ArrayList<Integer> candidates;
		
		public Spot(int rowNum, int colNum) {
			this.row = rowNum;
			this.col = colNum;
			this.value = grid[rowNum][colNum];
			this.candidates = setCandidates();
			
		}
		
		// method that allows the spot to set the value
		public void set(int value) {
			grid[row][col] = value;
		}
		
		public ArrayList<Integer> getCandidates() {
			return candidates;
		}
		
		@Override
		public int compareTo(Spot sp) {
			if (candidates.size() < sp.getCandidates().size()) return -1;
			if (candidates.size() == sp.getCandidates().size()) return 0;
			return 1;
		}
		
		// checks each 1-9 number and returns an array list of possible matches
		private ArrayList<Integer> setCandidates() {			
			ArrayList<Integer> allowables = new ArrayList<Integer>();
			for (int i = 1; i <= SIZE; i++) {
				if (isLegal(i)) allowables.add(i);
			}
			return allowables;
		}
		
		// returns true if value can be placed in spot
		private boolean isLegal(int val) {
			return (rowLegal(val) && colLegal(val) && sqLegal(val));
		}
		
		// returns an int array of the spot's row
		private int[] getRow() {
			int[] rRow = grid[row];
			return rRow;
		}
		
		// returns an int array of the spot's column
		private int[] getColumn() {
			int[] rCol = new int[SIZE];
			for (int j = 0; j < SIZE; j++) {
				rCol[j] = grid[j][col];
			}
			return rCol;
		}
		
		// returns the integer array of numbers in the square of the spot
		private int[] getSq() {
			int startRow = row/SQ_SIZE * SQ_SIZE;
			int startCol = col/SQ_SIZE * SQ_SIZE;
			
			int[] sqNumbers = new int[SIZE];
			int  count = 0;
			for (int i = 0; i < SQ_SIZE; i++) {
				for (int j = 0; j < SQ_SIZE; j++) {
					sqNumbers[count] = grid[startRow + i][startCol + j];
					count++;
				}
			}
			return sqNumbers;
		}
			
			// checks if value can legally be placed in spot's row
			private boolean rowLegal(int value) {
				return (intArrayNotContains(getRow(), value));
			}
			
			// checks if value can legally be placed in spot's column
			private boolean colLegal(int value) {
				return (intArrayNotContains(getColumn(), value));
			}
			
			// checks if value can legally be palced in spot's square
			private boolean sqLegal(int value) {
				return (intArrayNotContains(getSq(), value));
			}
				// returns true if an int array does not contain some int value
				private boolean intArrayNotContains(int[] array, int value) {
					for (int i = 0; i < array.length; i++) {
						if (array[i] == value) return false;
					}
					return true;
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
	
	// Debugging Methods ------- not really necessary
	
	public void printGrid() {
		for (int i = 0; i < SIZE; i++) {
			printRow(i);
		}
	}
	
	public void printRow(int i) {
		String row = "";
		for (int j = 0; j < SIZE; j++) {
			row += " " + grid[i][j] + " ";
		}
		System.out.println(row);
	}
	
	public void printCol(int i) {
		String col = "";
		for (int j = 0; j < SIZE; j++) {
			col += " " + grid[j][i] + " ";
		}
		System.out.println(col);
	}
	
	public String i2s(int[] array) {
		String str = "";
		for (int i = 0; i < array.length; i++) {
			str += " " + array[i] + " ";
		}
		return str;
	}
	
	public String printCandidates(Spot sp) {
		String str = "";
		ArrayList<Integer> candidates = sp.getCandidates();
		for (int i = 0; i < candidates.size(); i++) {
			str += candidates.get(i).toString() + " ";
			
		}
		return str;
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
	public static final int SQ_SIZE = 3; // side length of each square
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;

}
