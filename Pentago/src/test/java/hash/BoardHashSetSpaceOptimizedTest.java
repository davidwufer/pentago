package hash;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.Arrays;

import junit.framework.TestCase;
import board.Board;
import board.BoardFactory;
import board.Piece;

public class BoardHashSetSpaceOptimizedTest extends TestCase {
	// TODO: this is duplicate code
	private boolean isInvalidBoardArrayInternal(byte[] boardArrayInternal) {
		return boardArrayInternal[0] == Piece.valueOf(Piece.INVALID);
	}
	
	public void testAddWithNoCollision() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(5);
		final byte[][] hashArray = boardSet.getHashArray();
		
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final int hashIndex = boardSet.calculateHashIndex(board);
		final byte[] internalBoardArray = hashArray[hashIndex];
		boolean result;
		
		assertTrue(isInvalidBoardArrayInternal(internalBoardArray));
		
		boardSet.add(board);
		
		assertFalse(isInvalidBoardArrayInternal(internalBoardArray));
		
		assertTrue(Arrays.equals(board.getBoardArray().getInternalBoardArray(), internalBoardArray));
	}

//	public void testAddWithSameBoard() {
//		final BoardHashSetCustom boardSet = BoardSetFactory.createBoardHashSetCustom(5);
//		final Board board = BoardFactory.createSixBySixBoardBlank();
//		boolean result;
//		
//		result = boardSet.add(board);
//		
//		assertTrue(result);
//		
//		result = boardSet.add(board);
//		
//		assertFalse(result);
//	}
	
	public void testAddWithOutOfSpace() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
		
		final Board board1 = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Board board2 = BoardFactory.createSixBySixBoardBlank();
		
		boardSet.add(board1);
		
		try {
			boardSet.add(board2);
			fail();
		} catch (OutOfHashSpaceException e) {
			// expected
		}
	}
	
	public void testAddWithCollision() {
		//I'm going to use board1 and board2 for their internalBoardArrays
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(3);
		final BoardHashSetSpaceOptimized boardSetSpy = spy(boardSet);
		final byte[][] hashArray = boardSet.getHashArray();
		
		final int stubbedHashCode = 0;
		
		final Board board1 = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Board board2 = BoardFactory.createSixBySixBoardBlank();
		
		doReturn(stubbedHashCode).when(boardSetSpy).getBoardHashCode(any(Board.class));
		
		boardSetSpy.add(board1);
		boardSetSpy.add(board2);
		
		assertTrue(Arrays.equals(board1.getBoardArray().getInternalBoardArray(), hashArray[0]));
		assertTrue(Arrays.equals(board2.getBoardArray().getInternalBoardArray(), hashArray[1]));
	}
	
	public void testContainsWithCollision() {
		//I'm going to use board1 and board2 for their internalBoardArrays
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(3);
		final BoardHashSetSpaceOptimized boardSetSpy = spy(boardSet);
		final byte[][] hashArray = boardSet.getHashArray();
		
		final int stubbedHashCode = 0;
		
		final Board board1 = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Board board2 = BoardFactory.createSixBySixBoardBlank();
		
		doReturn(stubbedHashCode).when(boardSetSpy).getBoardHashCode(any(Board.class));
		
		hashArray[0] = board1.getBoardArray().getInternalBoardArray();
		hashArray[1] = board2.getBoardArray().getInternalBoardArray();
		
		assertTrue(boardSet.contains(board2, false));
	}
	
	public void testContainsWithNoCollision() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
		final byte[][] hashArray = boardSet.getHashArray();
		final Board board = BoardFactory.createSixBySixBoardBlank();

		hashArray[0] = board.getBoardArray().getInternalBoardArray();
		
		assertTrue(boardSet.contains(board, false));
	}
	
	public void testContainsReturnsFalseWithEmptyHashSet() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
		final Board board = BoardFactory.createSixBySixBoardBlank();

		assertFalse(boardSet.contains(board, false));
	}
	
	public void testContainsReturnsFalseWithPartiallyFilledHashSet() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(5);
		final byte[][] hashArray = boardSet.getHashArray();
		final Board board1 = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Board board2 = BoardFactory.createSixBySixBoardBlank();

		hashArray[0] = board1.getBoardArray().getInternalBoardArray();
		
		assertFalse(boardSet.contains(board2, false));
	}
	
	public void testEndToEndWithClonedInternalBoardArray() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(5);
		final Board board1 = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Board board2 = BoardFactory.createSixBySixBoardBlank();

		final Board board3 = BoardFactory.createBoardClone(board1);
		final Board board4 = BoardFactory.createBoardClone(board2);
		
		boardSet.add(board1);
		boardSet.add(board2);
		
		assertTrue(boardSet.contains(board1, false));
		assertTrue(boardSet.contains(board2, false));
		assertTrue(boardSet.contains(board3, false));
		assertTrue(boardSet.contains(board4, false));
	}
	
	public void testContainsWithSymmetry() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
		final byte[][] hashArray = boardSet.getHashArray();
		final Board board = BoardFactory.createSixBySixBoardBlank();

		hashArray[0] = board.getBoardArray().getInternalBoardArray();
		
		board.rotateNinetyDegreesClockwise();
		
		assertTrue(boardSet.contains(board, true));
	}
	
	public void testSizeAfterInitialization() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
		assertEquals("boardSet should start with a size of 0", 0, boardSet.size());
	}
	
	public void testSizeAfterAddingOne() {
		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
		final Board board = BoardFactory.createSixBySixBoardBlank();
		boardSet.add(board);
		assertEquals("boardSet should have a size of 1 after inserting one element", 1, boardSet.size());
	}
	
	// Collisions are not handled, so this test is invalid
//	public void testSizeDoesNotChangeAfterAddingCollision() {
//		final BoardHashSetSpaceOptimized boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(1);
//		final Board board = BoardFactory.createSixBySixBoardBlank();
//		boardSet.add(board);
//		boardSet.add(board);
//		assertEquals("boardSet should have a size of 1 after inserting one element and one conflicting element", 1, boardSet.size());
//	}
	
	public void testIsSymmetryForSixBySixBoard() {
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(50);
		final Board board = BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]");
		final Board copiedBoard;
		Board symmetryBoard;
		
		boardSet.add(board);
		assertTrue(boardSet.contains(BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]"), false));
		copiedBoard = BoardFactory.createBoardClone(board);
		assertTrue(boardSet.contains(copiedBoard, false));
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ooo      xxxxxxoooooo      xxxxxxooo]");
		assertTrue("Flip X", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[oooxxxxxx      ooooooxxxxxx      ooo]");
		assertTrue("Flip Y", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[o xo xo xo xo xo x xo xo xo xo xo xo]");
		assertTrue("Rotate 90 clockwise", boardSet.contains(symmetryBoard, true));

		symmetryBoard = BoardFactory.createSixBySixBoard("[   oooxxx   oooxxx   oooxxx   oooxxx]");
		assertTrue("Rotate 180 clockwise", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ox ox ox ox ox ox x ox ox ox ox ox o]");
		assertTrue("Rotate 270 clockwise", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[x ox ox ox ox ox oox ox ox ox ox ox ]");
		assertTrue("Flip top left to bottom right", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ xo xo xo xo xo xoo xo xo xo xo xo x]");
		assertTrue("Flip bottom left to top right", boardSet.contains(symmetryBoard, true));
	}
	
	public void testIsSymmetryForFourByFourBoard() {
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized(50);
		final Board board = BoardFactory.createFourByFourBoard("[xxoo  xxoo  ooxx]");
		final Board copiedBoard;
		Board symmetryBoard;
		
		boardSet.add(board);
		assertTrue(boardSet.contains(BoardFactory.createFourByFourBoard("[xxoo  xxoo  ooxx]"), false));
		copiedBoard = BoardFactory.createBoardClone(board);
		assertTrue(boardSet.contains(copiedBoard, false));
		
		symmetryBoard = BoardFactory.createFourByFourBoard("[ooxxoo    xxxxoo]");
		assertTrue("Flip X", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createFourByFourBoard("[ooxxxx    ooxxoo]");
		assertTrue("Flip Y", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createFourByFourBoard("[oo xoo xx xox xo]");
		assertTrue("Rotate 90 clockwise", boardSet.contains(symmetryBoard, true));

		symmetryBoard = BoardFactory.createFourByFourBoard("[xxoo  ooxx  ooxx]");
		assertTrue("Rotate 180 clockwise", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createFourByFourBoard("[ox xox xx oox oo]");
		assertTrue("Rotate 270 clockwise", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createFourByFourBoard("[x xox xooo xoo x]");
		assertTrue("Flip top left to bottom right", boardSet.contains(symmetryBoard, true));
		
		symmetryBoard = BoardFactory.createFourByFourBoard("[x oox ooox xox x]");
		assertTrue("Flip bottom left to top right", boardSet.contains(symmetryBoard, true));
	}
}
