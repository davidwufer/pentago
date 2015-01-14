/**
 * 
 */
package game;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveFactory;
import board.Board;
import board.BoardFactory;
import board.Piece;

/**
 * @author David Wu
 *
 */
public class GameTest extends TestCase {
	
	/* Should:
	 * 1) Have the right player turn
	 * 2) Put the right piece
	 * 3) Turn the right subBoard
	 * 4) Have the right pieces on board
	 * 5) Have the right player turn afterwards
	 */
	public void testDoMove() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Game game = GameFactory.createGame(gameBoard);
		final Move move = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		// 1
		assertEquals("Player should be O", Piece.O, gameBoard.currPlayerPiece());
		// 4a
		assertEquals("Pieces on board should be 1", 1, gameBoard.getPiecesOnBoard());
		// 2, 3		
		final Game gameAfterMove = game.doMove(move);
		final Board gameBoardAfterMove = gameAfterMove.getBoard();
		assertEquals("Game board should be correct", "[  x     o                           ]", gameBoardAfterMove.toString());	
		// 4b
		assertEquals("Pieces on board should now be 2", 2, gameBoardAfterMove.getPiecesOnBoard());
		// 5
		assertEquals("Player should be X", Piece.X, gameBoardAfterMove.currPlayerPiece());
	}
	
	/* Should:
	 * 1) Have the right player turn
	 * 2) Reverse the right subBoard
	 * 3) Remove the right piece
	 * 4) Have the right pieces on board 
	 * 5) Have the right player turn
	 */
	public void testUndoMove() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xo xo ox ox o xo xxo xo  ox ox xo xo]");
		final Game game = GameFactory.createGame(gameBoard);
		final Move move = MoveFactory.createMove(6, 1, true, GameValueFactory.getDraw());
		
		// 1
		assertEquals("Player should be X", Piece.X, gameBoard.currPlayerPiece());
		// 4a
		assertEquals("Pieces on board should be 24", 24, gameBoard.getPiecesOnBoard());
		// 2, 3
		final Game gameAfterUndoMove = game.undoMove(move);
		final Board gameBoardAfterUndoMove = gameAfterUndoMove.getBoard();
		assertEquals("Game board should be correct", "[  xxo  x ox xooo xxo xo  ox ox xo xo]", gameBoardAfterUndoMove.toString());	
		// 4b
		assertEquals("Pieces on board should now be 23", 23, gameBoardAfterUndoMove.getPiecesOnBoard());
		// 5
		assertEquals("Player should be O", Piece.O, gameBoardAfterUndoMove.currPlayerPiece());
	}
	
	public void testGetMaxNumChildrenWithBlankBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoardBlank();
		final Game g = GameFactory.createGame(gameBoard);
		assertEquals("Blank 6x6 board should have the right number of children",
				324, g.getMaxNumChildren());
	}
	
	public void testGetMaxNumChildrenWithPartiallyFilledBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xoxoxo                              ]");
		final Game g = GameFactory.createGame(gameBoard);
		assertEquals("Partially filled 6x6 board should have the right number of children",
				270, g.getMaxNumChildren());
	}
	
	public void testGetMaxNumChildrenWithCompletelyFilledBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxo]");
		final Game g = GameFactory.createGame(gameBoard);
		assertEquals("Completely filled 6x6 board should have the right number of children",
				0, g.getMaxNumChildren());
	}
	
	public void testSymmetriesStateAfterCreation() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxo]");
		final Game g = GameFactory.createGame(gameBoard);
		assertTrue("Symmetries should be on by default", g.isSymmetriesOn());
	}
	
	public void testSymmetriesStateAfterSetToOff() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxo]");
		final Game g = GameFactory.createGame(gameBoard);
		g.setSymmetriesOff();
		assertFalse("Symmetries should be on by default", g.isSymmetriesOn());
	}
	
	public void testSymmetriesStateAfterSetToOffThenOn() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxo]");
		final Game g = GameFactory.createGame(gameBoard);
		g.setSymmetriesOff();
		g.setSymmetriesOn();
		assertTrue("Symmetries should be on by default", g.isSymmetriesOn());
	}
	
	public void testDoMoveWithSubBoardZero() {
		final Board gameBoard = BoardFactory.createFourByFourBoard("[xx  oo  oo  xx  ]");
		final Game game = GameFactory.createGame(gameBoard);
		final Move move = MoveFactory.createMove(14, 0, false, GameValueFactory.getUndetermined());
		assertEquals("[xx  oo  oo  xx  ]", game.getBoard().toString());
		final Game gameAfterDoMove = game.doMove(move);
		assertEquals("[xx  oo  oo  xxx ]", gameAfterDoMove.getBoard().toString());
	}
}
