package hash;

import game.Game;
import game.GameFactory;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveFactory;
import board.Board;
import board.BoardFactory;

public class BoardHashSetTest extends TestCase {
	
	/* Symmetries are turned off because they are explicitly tested
	 * in H
	 */
	private BoardSet boardSet;
	
	
	@Override
	protected void setUp() {
		boardSet = BoardSetFactory.createBoardHashSetBasic();
	}
	
	public void testAdd() {
		final int initialBoardSetSize = boardSet.size();
		Board board = BoardFactory.createSixBySixBoardBlank();
		boardSet.add(board);
		assertEquals("Set size should increase by 1 when adding a board to a new set", initialBoardSetSize + 1, boardSet.size());
	
		
		try {
			boardSet.add(board);
			fail();
		} catch (OutOfHashSpaceException e) {
			// expected
		}
		
		try {
			boardSet.add(BoardFactory.createSixBySixBoardBlank());
			fail();
		} catch (OutOfHashSpaceException e) {
			// expected
		}
		
		assertEquals("Set size should remain the same when a board that has already been added is added again", initialBoardSetSize + 1, boardSet.size());
	}
	
	public void testAddUsingDoMove() {
		final int initialBoardSetSize = boardSet.size();
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final Game game = GameFactory.createGame(board);
		final Move move = MoveFactory.createMove(0, 2, false, GameValueFactory.getUndetermined());
		
		final Game gameAfterDoMove = game.doMove(move);
		final Board boardAfterDoMove = gameAfterDoMove.getBoard();
		
		boardSet.add(boardAfterDoMove);
		assertEquals("Set size should increase by 1 when adding a board to a new set", initialBoardSetSize + 1, boardSet.size());

		
		assertTrue("getBoard and board should be the same", boardSet.contains(boardAfterDoMove, false));
		
		final Board testBoard = BoardFactory.createSixBySixBoard("[x                                   ]");
		assertTrue(boardSet.contains(testBoard, false));
		
	}
	
	public void testContains() {
		Board board = BoardFactory.createSixBySixBoardBlank();
		assertFalse("Board should be in hash", boardSet.contains(board, false));
		
		boardSet.add(board);
		assertTrue("Board should be in hash", boardSet.contains(board, false));
	}
}
