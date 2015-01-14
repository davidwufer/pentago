package transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import board.Board;
import board.BoardFactory;

public class HashMapTranspositionTableTest extends TestCase {
	
	private BoardDatabase transTable;
	
	@Override
	public void setUp() {
		transTable = BoardDatabaseFactory.createHashMapTT();
	}
	
	public void testAddAndContains() {
		Board sixBySixBoardBlank = BoardFactory.createSixBySixBoardBlank();
		Board sixBySixBoardFilled = BoardFactory.createSixBySixBoard("[xxooxxooxxooxxooxxxxooxxooxxooxxooxx]");
		
		assertFalse(transTable.contains(sixBySixBoardBlank,  false).isResultFound());
		assertFalse(transTable.contains(sixBySixBoardFilled, false).isResultFound());
		
		transTable.add(sixBySixBoardFilled, GameValueFactory.getWin());
		assertFalse(transTable.contains(sixBySixBoardBlank, false).isResultFound());
		assertTrue(transTable.contains(sixBySixBoardFilled, false).isResultFound());
		
		transTable.add(sixBySixBoardBlank, GameValueFactory.getWin());
		assertTrue(transTable.contains(sixBySixBoardBlank, false).isResultFound());
		assertTrue(transTable.contains(sixBySixBoardFilled, false).isResultFound());
	}
	
	public void testGetResultingValue() {
		BoardDatabaseResult foundResult = BoardDatabaseResult.WIN_RESULT;
		BoardDatabaseResult noResult = BoardDatabaseResult.NO_RESULT;
		
		assertTrue(foundResult.isResultFound());
		assertFalse(noResult.isResultFound());
	}
	
	public void testAddAndGetResultingValue() {
		BoardDatabaseResult result;
		Board sixBySixBoardBlank = BoardFactory.createSixBySixBoardBlank();
		Board sixBySixBoardFilled = BoardFactory.createSixBySixBoard("[xxooxxooxxooxxooxxxxooxxooxxooxxooxx]");
		
		assertFalse(transTable.contains(sixBySixBoardBlank, false).isResultFound());
		assertFalse(transTable.contains(sixBySixBoardFilled, false).isResultFound());
		
		transTable.add(sixBySixBoardFilled, GameValueFactory.getWin());
		result = transTable.contains(sixBySixBoardFilled, false);
		assertTrue(result.isResultFound());
		assertEquals(result.getResult(), GameValueFactory.getWin());
		
		transTable.add(sixBySixBoardBlank, GameValueFactory.getLoss());
		result = transTable.contains(sixBySixBoardBlank, false);
		assertTrue(result.isResultFound());
		assertEquals(result.getResult(), GameValueFactory.getLoss());
	}
	
	public void testAddShouldOnlyWorkForTerminalValues() {
		Board sixBySixBoardBlank = BoardFactory.createSixBySixBoardBlank();
		GameValue nonTerminalValue = GameValueFactory.createGameValue(500);
		try {
			assertTrue(nonTerminalValue.isValidGameValue());
			assertFalse(nonTerminalValue.isTerminalValue());
			transTable.add(sixBySixBoardBlank, nonTerminalValue);
			fail("Non-terminal values should not be added to a TT");
		} catch (IllegalArgumentException e) {
			// We want to catch the exception
		}
	}
	
	public void testIsSymmetryForSixBySixBoard() {
		final Board board = BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]");
		@SuppressWarnings("unused")
		Board symmetryBoard;
		BoardDatabaseResult result;
		
		transTable.add(BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]"), GameValueFactory.getWin());
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals(result.getResult(), GameValueFactory.getWin());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ooo      xxxxxxoooooo      xxxxxxooo]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Flip X", result.getResult(), GameValueFactory.getWin());

		symmetryBoard = BoardFactory.createSixBySixBoard("[oooxxxxxx      ooooooxxxxxx      ooo]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Flip Y", result.getResult(), GameValueFactory.getWin());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[o xo xo xo xo xo x xo xo xo xo xo xo]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Rotate 90 clockwise", result.getResult(), GameValueFactory.getWin());

		symmetryBoard = BoardFactory.createSixBySixBoard("[   oooxxx   oooxxx   oooxxx   oooxxx]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Rotate 180 clockwise", result.getResult(), GameValueFactory.getWin());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ox ox ox ox ox ox x ox ox ox ox ox o]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Rotate 270 clockwise", result.getResult(), GameValueFactory.getWin());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[x ox ox ox ox ox oox ox ox ox ox ox ]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Flip top left to bottom right", result.getResult(), GameValueFactory.getWin());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ xo xo xo xo xo xoo xo xo xo xo xo x]");
		result = transTable.contains(board, true);
		assertTrue(result.isResultFound());
		assertEquals("Flip bottom left to top right", result.getResult(), GameValueFactory.getWin());
	}
}
