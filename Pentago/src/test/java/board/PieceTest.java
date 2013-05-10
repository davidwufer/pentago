package board;

import junit.framework.TestCase;
/*
 * @author David Wu
 */
public class PieceTest extends TestCase {
	
	public void testXConstructor() {
		Piece X = Piece.X;
		assertEquals("X should have a value of 1", 1, Piece.valueOf(X));
	}
	
	public void testOConstructor() {
		Piece O = Piece.O;
		assertEquals("O should have a value of 2", 2, Piece.valueOf(O));
	}
	
	public void testBlankConstructor() {
		Piece BLANK = Piece.BLANK;
		assertEquals("BLANK should have a value of 0", 0, Piece.valueOf(BLANK));
	}
	
	public void testXPieceToInt() {
		assertEquals(1, Piece.X.toInt());
	}
	
	public void testOPieceToInt() {
		assertEquals(2, Piece.O.toInt());
	}
	/*
	public void testInvalidPiece() {
		assertFalse("If the piece is not in {0,3}, it is not a valid piece", Piece.isValidPiece(4));
	}
	
	public void testValidPiece() {
		assertFalse("If the piece is in {0,3}, it is a valid piece", Piece.isValidPiece(Piece.X));
	}
	*/
}
