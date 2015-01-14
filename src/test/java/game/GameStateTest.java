/**
 * 
 */
package game;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;

import org.junit.Test;

import board.Board;
import board.BoardFactory;
/**
 * @author David Wu
 *
 */
public class GameStateTest extends TestCase {
	public void testGameStateHorizontalOLossAtTopOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xxxxxo   o o  o                     ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be LOSS for O on top of board",
				GameValueFactory.getLoss(), g.getGameState());
	}
	
	public void testGameStateHorizontalXLossAtTopOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[oooooxx   x x  x                    ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be LOSS for X on top of board",
				GameValueFactory.getLoss(), g.getGameState());
	}
	
	public void testGameStateHorizontalXWinAtTopOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xxxxxo   o o  o     o               ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be WIN for X on top of board",
				GameValueFactory.getWin(), g.getGameState());
	}
	
	public void testGameStateHorizontalOWinAtTopOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[ooooox   x x  x     x      x        ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be WIN for O on top of board",
				GameValueFactory.getWin(), g.getGameState());
	}
	
	public void testGameStateHorizontalXWinInMiddleOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[ooo o  xxxxx                        ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be LOSS for O in middle",
				GameValueFactory.getLoss(), g.getGameState());
	}
	
	public void testGameStateHorizontalOWinInMiddleOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xxx xx ooooo                    x   ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be WIN for O in middle",
				GameValueFactory.getWin(), g.getGameState());
	}
	
	public void testGameStateVerticalOLossInMiddleOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xo    xo    xo    xo    x           ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be LOSS for O in middle",
				GameValueFactory.getLoss(), g.getGameState());
	}
	
	public void testGameStateVerticalXLossInMiddleOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xo    xo    xo    xo     o         x]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be LOSS for X in middle",
				GameValueFactory.getLoss(), g.getGameState());
	}
	
	public void testGameStateTopLeftToBottomRightDiagonalXWinInMiddleOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xo     x   o  x  o   x o    xo      ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be WIN for X in middle",
				GameValueFactory.getWin(), g.getGameState());
	}
	
	public void testGameStateTopRightToBottomLeftDiagonalOWinInMiddleOfBoard() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[x        xo   xo   xo    ox   o  x  ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be WIN for O in middle",
				GameValueFactory.getWin(), g.getGameState());
	}
	
	public void testGameStateDrawDoubleWin() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xxxxxo    o    o    o    o          ]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be DRAW",
				GameValueFactory.getDraw(), g.getGameState());
	}
	
	public void testGameStateDrawBoardFilled() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xxxxooooooxxxxxxooooooxxxxxxooooooxx]");
		Game g = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		assertEquals("Game state should be DRAW",
				GameValueFactory.getDraw(), g.getGameState());
	}
	
	@Test
	public void testGameStateWithFiveBlanksInARow() {
		final Board gameBoard = BoardFactory.createSixBySixBoardBlank();
		final Game game = GameFactory.createGame(gameBoard);

		final GameValue gameState = game.getGameState();
		assertFalse(gameState.isTerminalValue());
	}
}
