package search.alphabeta.transposition;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import search.alphabeta.transposition.BoardDatabaseResult;
import search.alphabeta.transposition.TwoTierTranspositionTable;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import board.Board;
import board.BoardFactory;

public class TwoTierTranspositionTableTest extends TestCase {
	public void testCreateTranspositionTable() {
		TwoTierTranspositionTable firstTable = TwoTierTranspositionTable.createTwoTierTranspositionTable(10);
		TwoTierTranspositionTable secondTable = TwoTierTranspositionTable.createTwoTierTranspositionTable(10);
		assertEquals(firstTable.getTableContents().length, secondTable.getTableContents().length);
	}
	
	public void testAddAndContainsEndToEnd() {
		TwoTierTranspositionTable table = TwoTierTranspositionTable.createTwoTierTranspositionTable(2);
		TwoTierTranspositionTable tableSpy = spy(table);
		
		Board board1 = BoardFactory.createSixBySixBoard("[oxxox                               ]");
		Board board2 = BoardFactory.createSixBySixBoard("[xxoxoo                              ]");
		Board board3 = BoardFactory.createSixBySixBoard("[oxxo                                ]");
		Board board4 = BoardFactory.createSixBySixBoard("[oxx                                 ]");
		Board board5 = BoardFactory.createSixBySixBoard("[ox                                  ]");
		Board board6 = BoardFactory.createSixBySixBoard("[xxoxoxo                             ]");
		
		GameValue gameValue1 = GameValueFactory.getWin();
		GameValue gameValue2 = GameValueFactory.getLoss();
		GameValue gameValue3 = GameValueFactory.getDraw();
		GameValue gameValue4 = GameValueFactory.getWin();
		GameValue gameValue5 = GameValueFactory.getLoss();
		GameValue gameValue6 = GameValueFactory.getDraw();
		
		doReturn(1).when(tableSpy).getHashIndex(board1);
		doReturn(1).when(tableSpy).getHashIndex(board2);
		doReturn(1).when(tableSpy).getHashIndex(board6);
		doReturn(0).when(tableSpy).getHashIndex(board3);
		doReturn(0).when(tableSpy).getHashIndex(board4);
		doReturn(0).when(tableSpy).getHashIndex(board5);
		
		// For index 1
		assertFalse(tableSpy.add(board6, gameValue6));
		assertTrue(tableSpy.add(board2, gameValue2));
		assertTrue(tableSpy.add(board1, gameValue1));
		
		assertEquals(BoardDatabaseResult.NO_RESULT, tableSpy.contains(board6, false));
		BoardDatabaseResult result2 = tableSpy.contains(board2, false);
		BoardDatabaseResult result1 = tableSpy.contains(board1, false);
		assertEquals(gameValue2, result2.getResult());
		assertEquals(gameValue1, result1.getResult());
		
		// For index 0
		assertFalse(tableSpy.add(board5, gameValue5));
		assertFalse(tableSpy.add(board3, gameValue3));
		assertFalse(tableSpy.add(board4, gameValue4));
		
		assertEquals(BoardDatabaseResult.NO_RESULT, tableSpy.contains(board3, false));
		BoardDatabaseResult result5 = tableSpy.contains(board5, false);
		BoardDatabaseResult result4 = tableSpy.contains(board4, false);
		assertEquals(gameValue5, result5.getResult());
		assertEquals(gameValue4, result4.getResult());
	}
	
	public void testSymmetriesInFirstAndSecondLevel() {
		TwoTierTranspositionTable table = TwoTierTranspositionTable.createTwoTierTranspositionTable(1);
		GameValue gameValue = GameValueFactory.getWin();
		
		final Board board = BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]");
		Board copiedBoard;
		Board symmetryBoard;
		
		table.add(board, gameValue);
		
		BoardDatabaseResult result;
		result = table.contains(BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]"), true);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result);
		
		copiedBoard = BoardFactory.createBoardClone(board);
		assertEquals(copiedBoard, board);
		result = table.contains(copiedBoard, true);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result);
		assertEquals(gameValue, result.getResult()); 
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ooo      xxxxxxoooooo      xxxxxxooo]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip X", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[oooxxxxxx      ooooooxxxxxx      ooo]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip Y", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[o xo xo xo xo xo x xo xo xo xo xo xo]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Rotate 90 clockwise", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[   oooxxx   oooxxx   oooxxx   oooxxx]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Rotate 180 clockwise", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ox ox ox ox ox ox x ox ox ox ox ox o]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Rotate 270 clockwise", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[x ox ox ox ox ox oox ox ox ox ox ox ]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip top left to bottom right", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ xo xo xo xo xo xoo xo xo xo xo xo x]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip bottom left to top right", gameValue, result.getResult());

		// pushing the first board to the second level and then checking for symmetries again;
		final Board secondBoard = BoardFactory.createSixBySixBoardBlank();
		table.add(secondBoard, GameValueFactory.getWin()); //we don't really care about the gameValue since it's never read
		
		result = table.contains(BoardFactory.createSixBySixBoard("[xxxooo   xxxooo   xxxooo   xxxooo   ]"), true);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result);
		
		copiedBoard = BoardFactory.createBoardClone(board);
		assertEquals(copiedBoard, board);
		result = table.contains(copiedBoard, true);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result);
		assertEquals(gameValue, result.getResult()); 
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ooo      xxxxxxoooooo      xxxxxxooo]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip X", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[oooxxxxxx      ooooooxxxxxx      ooo]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip Y", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[o xo xo xo xo xo x xo xo xo xo xo xo]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Rotate 90 clockwise", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[   oooxxx   oooxxx   oooxxx   oooxxx]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Rotate 180 clockwise", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ox ox ox ox ox ox x ox ox ox ox ox o]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Rotate 270 clockwise", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[x ox ox ox ox ox oox ox ox ox ox ox ]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip top left to bottom right", gameValue, result.getResult());
		
		symmetryBoard = BoardFactory.createSixBySixBoard("[ xo xo xo xo xo xoo xo xo xo xo xo x]");
		result = table.contains(symmetryBoard, true);
		assertEquals("Flip bottom left to top right", gameValue, result.getResult());
	}
	
//	public void testMaxTTSize() {
//		try {
//			TwoTierTranspositionTable.createTwoTierTranspositionTable(Config.TTSIZE);
//		} catch (OutOfMemoryError e) {
//			fail("Out of memory");
//		}
//	}
	
	public void testGetHashIndex() {
		final int NUM_ITERATIONS = 500000;
		final TwoTierTranspositionTable table = TwoTierTranspositionTable.createTwoTierTranspositionTable(80000);
		for (int iteration = 0; iteration < NUM_ITERATIONS; iteration++) {
			final Board board = BoardFactory.createSixBySixBoardRandom();
			final int hashIndex = table.getHashIndex(board);
			if (hashIndex < 0) {
				System.out.println(board.toPrettyString());
				fail("Hash index should be greater than 0");
			}
		}
	}
}
