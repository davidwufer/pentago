package hash;

import junit.framework.TestCase;
import board.Board;
import board.BoardFactory;

public class SymmetryCheckerTest extends TestCase {
	
	public void testContainsDoesNotModifyBoard() {
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
		final Board board = BoardFactory.createSixBySixBoardBlank();
		
		final boolean isSymmetriesEnabled = true;
		final boolean result = boardSet.contains(board, isSymmetriesEnabled);
		
		assertFalse("We want the result to be false so it goes through all of the code", result);
		assertEquals(board, BoardFactory.createSixBySixBoardBlank());
	}
	
	public void testIsSymmetryForSixBySixBoard() {
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
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
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
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
	
	public void testGetNumberOfSymmetriesReturnsWithAllUniqueSymmetries() {
		final Board board = BoardFactory.createFourByFourBoard("[xxooooxxxoxooxox]");
		assertEquals(8, SymmetryChecker.getNumberOfUniqueSymmetricBoardsIncludingItself(board));
	}
	
	public void testGetNumberOfSymmetriesWithSymmetries() {
		final Board board = BoardFactory.createFourByFourBoard("[x               ]");
		assertEquals(4, SymmetryChecker.getNumberOfUniqueSymmetricBoardsIncludingItself(board));
	}
}
