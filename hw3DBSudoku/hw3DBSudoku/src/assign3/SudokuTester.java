package assign3;

import static org.junit.Assert.*;

import org.junit.*;

public class SudokuTester {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated, stick1;
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(4, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		stick1 = new Piece(Piece.STICK_STR);
		
	}
	
	//Check Board.place() results
	@Test
	public void PlaceCheck() {
		// Out of Bounds Testing
		assertEquals(PLACE_OUT_BOUNDS, b.place(pyr1, -2, -2));
		assertEquals(PLACE_OUT_BOUNDS, b.place(pyr1, -2, 0));
		assertEquals(PLACE_OUT_BOUNDS, b.place(pyr1, 0, -2));
		assertEquals(PLACE_OUT_BOUNDS, b.place(pyr1, 0, 5));
		assertEquals(PLACE_OUT_BOUNDS, b.place(pyr1, 2, 0));
		
		// Piece Collision Testing
		b.place(pyr1, 0, 0);
		b.commit();
		assertEquals(PLACE_BAD, b.place(pyr1, 0, 0));
		assertEquals(PLACE_BAD, b.place(pyr1, 0, 1));
		assertEquals(PLACE_BAD, b.place(pyr2, 0, 0));
		assertEquals(PLACE_OK, b.place(pyr2, 1, 1));
	}
	
}