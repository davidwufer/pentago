package board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

/**
 * The board manipulation methods (flip over stuff, rotate) are tested in BoardTest
 * @author David Wu
 *
 */
public class BoardArrayTest {

//	@Test
//	public void invalidateBoardShouldSetZeroIndexToInvalidPiece() {
//		final BoardArray boardArray = BoardArray.createNewBoardArray();
//		boardArray.invalidateBoard();
//		assertEquals("The 0th index should be an invalid piece", boardArray.getZeroIndex(), Piece.valueOf(Piece.INVALID));
//	}
	
	private static final byte[] INTERNAL_BOARD_ARRAY = new byte[] {'1', '1', '1', '1', '1', '1', '1', '1', '1'};
	private static final byte[] INTERNAL_BOARD_ARRAY_MODIFIED = new byte[] {'0', '1', '1', '1', '1', '1', '1', '1', '1'};
	
	@Test
	public void createNewBoardArrayShouldReturnABoardArraySingleton() {
		final BoardArray newBoardArray = BoardArray.createNewBoardArray();
		assertTrue("BoardArray.createNewBoardArray() should always return the NEW_BOARD_ARRAY singleton",
				BoardArray.NEW_BOARD_ARRAY == newBoardArray);
	}
	
	@Test
	public void newBoardArrayShouldBeAllZeros() {
		final byte[] EXPECTED_NEW_BOARD_ARRAY = new byte[] {'0', '0', '0', '0', '0', '0', '0', '0', '0'};
		
		assertTrue(Arrays.equals(EXPECTED_NEW_BOARD_ARRAY, BoardArray.NEW_BOARD_ARRAY.getInternalBoardArray()));
	}
	
	@Test
	public void cloneBoardArrayShouldReturnABoardArrayWithANewAndClonedInternalBoardArray() {
		final BoardArray boardArray = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY);
		final BoardArray boardArrayCopy = BoardArray.cloneBoardArray(boardArray);
		assertDifferentBoardArraysButContentsEqual(boardArray, boardArrayCopy);
	}
	
	@Test
	public void createInternalBoardArrayCopyShouldCreateNewInternalBoardArray() {
		final byte[] internalBoardArray = new byte[] {'0', '3', '0', '1', '1', '3', '0', '0', '0'};
		final byte[] internalBoardArrayCopy = BoardArray.createInternalBoardArrayCopy(internalBoardArray);
		assertFalse(internalBoardArray == internalBoardArrayCopy);
	}
	
	@Test
	public void createInternalBoardArrayCopyShouldCopyTheArray() {
		final byte[] internalBoardArray = new byte[] {'0', '3', '0', '1', '1', '3', '0', '0', '0'};
		final byte[] internalBoardArrayCopy = BoardArray.createInternalBoardArrayCopy(internalBoardArray);
		assertTrue(Arrays.equals(internalBoardArray, internalBoardArrayCopy));
	}
	
	@Test
	public void getInternalBoardArrayShouldReturnANewInternalBoardArray() {
		final BoardArray boardArray = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY);
		final byte[] internalBoardArray = boardArray.getInternalBoardArray();
		
		assertDifferentInternalBoardArraysButContentsEqual(INTERNAL_BOARD_ARRAY, internalBoardArray);
	}
	
	@Test
	public void setInternalBoardArrayDoesNotModifyExistingBoardArray() {
		final BoardArray boardArray = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY);
		boardArray.setInternalBoardArray(INTERNAL_BOARD_ARRAY_MODIFIED);
		
		assertDifferentInternalBoardArraysButContentsEqual(INTERNAL_BOARD_ARRAY, boardArray.getInternalBoardArray());
	}
	
	@Test
	public void setInternalBoardArrayCreatesANewBoardArrayWithTheCorrectInternalBoardArray() {
		final BoardArray boardArray = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY);
		final BoardArray newBoardArray = boardArray.setInternalBoardArray(INTERNAL_BOARD_ARRAY_MODIFIED);
		
		assertDifferentInternalBoardArraysButContentsEqual(INTERNAL_BOARD_ARRAY_MODIFIED, newBoardArray.getInternalBoardArray());
	}
	
	@Test
	public void testEquals() {
		final BoardArray boardArray1 = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY);
		final BoardArray boardArray2 = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY.clone());
		assertEquals("Two BoardArray objects containing internalBoardArrays with the same contents should be equal",
				boardArray1, boardArray2);
	}
	
	@Test
	public void testHashCode() {
		final BoardArray boardArray1 = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY);
		final BoardArray boardArray2 = BoardArray.createNewBoardArray(INTERNAL_BOARD_ARRAY.clone());
		assertEquals("Two BoardArray objects containing internalBoardArrays with the same contents should have the same hashCode",
				boardArray1.hashCode(), boardArray2.hashCode());
	}
	
	@Test
	public void testGetPieceAt() {
		final byte xPieceInFifthSpot = (byte) (Piece.valueOf(Piece.X) << 2);
		final byte[] internalBoardArray = new byte[] {'0', xPieceInFifthSpot, '0', '0', '0', '0', '0', '0', '0'};
		
		final BoardArray boardArray = BoardArray.createNewBoardArray(internalBoardArray);
		assertEquals(Piece.X, boardArray.getPieceAt(5));
	}
	
	@Test
	public void placePieceAtShouldReturnANewBoardArray() {
		final int index = 10;
		final Piece piece = Piece.O;
		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY;
		final BoardArray boardArrayAfterPiecePlacement = boardArray.placePieceAt(index, piece);
		
		assertFalse(boardArray == boardArrayAfterPiecePlacement);
	}
	
	@Test
	public void testPlacePieceAt() {
		final int index = 10;
		final Piece piece = Piece.O;
		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY.placePieceAt(index, piece);
		
		assertEquals(piece, boardArray.getPieceAt(index));
	}
	
	@Test
	public void swapPieceAtShouldReturnANewBoardArrayIfPiecesAreDifferent() {
		final int index1 = 5;
		final Piece piece1 = Piece.X;
		
		final int index2 = 10;
		final Piece piece2 = Piece.O;

		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY.
				placePieceAt(index1, piece1).
				placePieceAt(index2, piece2);
				
		final BoardArray newBoardArray = boardArray.swapPiecesAt(index1, index2);
		
		assertFalse(boardArray == newBoardArray);
	}
	
	@Test
	public void testSwapPieceAt() {
		final int index1 = 5;
		final Piece piece1 = Piece.X;
		
		final int index2 = 10;
		final Piece piece2 = Piece.O;

		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY.
				placePieceAt(index1, piece1).
				placePieceAt(index2, piece2).
				swapPiecesAt(index1, index2);
		
		assertEquals(piece1, boardArray.getPieceAt(index2));
		assertEquals(piece2, boardArray.getPieceAt(index1));
	}
	
	private void assertDifferentInternalBoardArraysButContentsEqual(byte[] b1, byte[] b2) {
		assertTrue(b1 != null);
		assertTrue(Arrays.equals(b1, b2));
		assertTrue(b1 != b2);
	}
	
	private void assertDifferentBoardArraysButContentsEqual(BoardArray b1, BoardArray b2) {
		assertTrue(b1 != null);
		assertTrue(b1.equals(b2));
		assertTrue(b1 != b2);
	}
	
	
	
}
