package transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import board.Board;
import board.BoardArray;
import board.BoardFactory;
import board.Piece;
import config.Config;

public class TwoTierElementTest extends TestCase {
	
	@Override
	public void setUp() {
	}
	
	/* The following are aliased from TwoTierElement.java */ 
	private final byte INVALID_DEPTH = -1;
	private boolean isInvalidBoardArrayInternal(byte[] boardArrayInternal) {
		return boardArrayInternal[0] == Piece.valueOf(Piece.INVALID);
	}
	
	public void testCreationOfTT() {
		final TwoTierElement element = new TwoTierElement();
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		assertTrue(isInvalidBoardArrayInternal(element.getSecondLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getSecondLevelGameValueShort());
		assertEquals(INVALID_DEPTH, element.getFirstLevelDepth());
	}
	
	public void testInsertIntoFirstLevelAfterCreation() {
		final TwoTierElement element = new TwoTierElement();
		final Board board = BoardFactory.createSixBySixBoardBlank();
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		assertFalse(element.add(board, GameValueFactory.getWin()));
		assertEquals(BoardArray.cloneBoardArray(board.getBoardArray()), BoardArray.createNewBoardArray(element.getFirstLevelBoardArrayInternal()));
		assertEquals(GameValueFactory.getWin().getValue(), element.getFirstLevelGameValueShort());
	}	
	
	public void testInsertTwoThingsWithSecondGoingToSecondLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		final Board board2 = BoardFactory.createSixBySixBoard("[xxooxo                              ]");
		final GameValue gameValue1 = GameValueFactory.createGameValue(1);
		final GameValue gameValue2 = GameValueFactory.createGameValue(2);
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		assertFalse(element.add(board1, gameValue1));
		assertFalse(element.add(board2, gameValue2));
		
		assertEquals(BoardArray.cloneBoardArray(board1.getBoardArray()), BoardArray.createNewBoardArray(element.getFirstLevelBoardArrayInternal()));
		assertEquals(gameValue1.getValue(), element.getFirstLevelGameValueShort());
		assertEquals(BoardArray.cloneBoardArray(board2.getBoardArray()), BoardArray.createNewBoardArray(element.getSecondLevelBoardArrayInternal()));
		assertEquals(gameValue2.getValue(), element.getSecondLevelGameValueShort());
	}
	
	public void testInsertTwoThingsWithSecondGoingToFirstLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoard("[xxooxo                              ]");
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		final GameValue gameValue1 = GameValueFactory.createGameValue(1);
		final GameValue gameValue2 = GameValueFactory.createGameValue(2);
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		assertFalse(element.add(board1, gameValue1));
		assertTrue(element.add(board2, gameValue2));
		
		assertEquals(BoardArray.cloneBoardArray(board2.getBoardArray()), BoardArray.createNewBoardArray(element.getFirstLevelBoardArrayInternal()));
		assertEquals(gameValue2.getValue(), element.getFirstLevelGameValueShort());
		assertEquals(BoardArray.cloneBoardArray(board1.getBoardArray()), BoardArray.createNewBoardArray(element.getSecondLevelBoardArrayInternal()));
		assertEquals(gameValue1.getValue(), element.getSecondLevelGameValueShort());		
	}
	
	public void testInsertThreeThingsAndBootFirstOneToSecondLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoard("[xxooxo                              ]");
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		final Board board3 = BoardFactory.createSixBySixBoard("[xxoo                                ]");
		
		final GameValue gameValue1 = GameValueFactory.createGameValue(1);
		final GameValue gameValue2 = GameValueFactory.createGameValue(2);
		final GameValue gameValue3 = GameValueFactory.createGameValue(3);
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		assertFalse(element.add(board1, gameValue1));
		assertTrue(element.add(board2, gameValue2));
		assertTrue(element.add(board3, gameValue3));
		
		assertEquals(BoardArray.cloneBoardArray(board3.getBoardArray()), BoardArray.createNewBoardArray(element.getFirstLevelBoardArrayInternal()));
		assertEquals(gameValue3.getValue(), element.getFirstLevelGameValueShort());
		assertEquals(BoardArray.cloneBoardArray(board2.getBoardArray()), BoardArray.createNewBoardArray(element.getSecondLevelBoardArrayInternal()));
		assertEquals(gameValue2.getValue(), element.getSecondLevelGameValueShort());
	}
	
	public void testInsertThreeThingsAndBootSecondOneOut() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		final Board board2 = BoardFactory.createSixBySixBoard("[xxooxo                              ]");
		final Board board3 = BoardFactory.createSixBySixBoard("[xxooxox                             ]");
		
		final GameValue gameValue1 = GameValueFactory.createGameValue(1);
		final GameValue gameValue2 = GameValueFactory.createGameValue(2);
		final GameValue gameValue3 = GameValueFactory.createGameValue(3);
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		assertFalse(element.add(board1, gameValue1));
		assertFalse(element.add(board2, gameValue2));
		assertFalse(element.add(board3, gameValue3));
		
		assertEquals(BoardArray.cloneBoardArray(board1.getBoardArray()), BoardArray.createNewBoardArray(element.getFirstLevelBoardArrayInternal()));
		assertEquals(gameValue1.getValue(), element.getFirstLevelGameValueShort());
		assertEquals(BoardArray.cloneBoardArray(board3.getBoardArray()), BoardArray.createNewBoardArray(element.getSecondLevelBoardArrayInternal()));
		assertEquals(gameValue3.getValue(), element.getSecondLevelGameValueShort());
	}
	
	public void testContainsFirstLevelWhenThereIsNoFirstLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board = BoardFactory.createSixBySixBoardBlank();
		assertEquals(BoardDatabaseResult.NO_RESULT, element.contains(board));
	}
	
	public void testContainsFirstLevelWhenThereIsNoSecondLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoardBlank();
		
		final GameValue gameValue1 = GameValueFactory.getWin();
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		element.add(board1, gameValue1);
		BoardDatabaseResult result1 = element.contains(board1);
		assertEquals(gameValue1, result1.getResult());
	}
	
	public void testContainsFirstLevelWhenThereIsASecondLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoardBlank();
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		
		final GameValue gameValue1 = GameValueFactory.getLoss();
		final GameValue gameValue2 = GameValueFactory.getWin();
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		element.add(board1, gameValue1);
		element.add(board2, gameValue2);
		
		BoardDatabaseResult result1 = element.contains(board1);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result1);
		assertEquals(gameValue1, result1.getResult());
	}
	
	public void testContainsSecondLevelWhenThereIsNoSecondLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoardBlank();
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		
		final GameValue gameValue1 = GameValueFactory.createGameValue(1);
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		element.add(board1, gameValue1);
		
		assertEquals(BoardDatabaseResult.NO_RESULT, element.contains(board2));
	}

	public void testContainsSecondLevelWhenThereIsASecondLevel() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoardBlank();
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		
		final GameValue gameValue1 = GameValueFactory.getLoss();
		final GameValue gameValue2 = GameValueFactory.getWin();
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		element.add(board1, gameValue1);
		element.add(board2, gameValue2);
		
		BoardDatabaseResult result2 = element.contains(board2);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result2);
		assertEquals(gameValue2, result2.getResult());
	}
	
	public void testDoesNotContainEvenWhenBothLevelsAreThere() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoardBlank();
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		final Board board3 = BoardFactory.createSixBySixBoard("[xxooxo                              ]");
		
		final GameValue gameValue1 = GameValueFactory.createGameValue(1);
		final GameValue gameValue2 = GameValueFactory.createGameValue(2);
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		element.add(board1, gameValue1);
		element.add(board2, gameValue2);
		
		assertEquals(BoardDatabaseResult.NO_RESULT, element.contains(board3));
	}
	
	public void testContainWithDifferentBoardObjects() {
		final TwoTierElement element = new TwoTierElement();
		final Board board1 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		final Board board2 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
		
		final GameValue gameValue1 = GameValueFactory.getWin();
		
		assertTrue(isInvalidBoardArrayInternal(element.getFirstLevelBoardArrayInternal()));
		assertEquals(Config.UNDETERMINED_VALUE, element.getFirstLevelGameValueShort());
		
		element.add(board1, gameValue1);
		
		BoardDatabaseResult result2 = element.contains(board2);
		assertNotSame(BoardDatabaseResult.NO_RESULT, result2);
		assertEquals(gameValue1, result2.getResult());
	}
	
//	public void testToStringAndBack() {
//		final TwoTierElement element = new TwoTierElement();
//		final Board board1 = BoardFactory.createSixBySixBoard("[xxoox                               ]");
//		final Board board2 = BoardFactory.createSixBySixBoard("[xxooxo                              ]");
//		
//		element.add(board1, GameValueFactory.createWin());
//		element.add(board2, GameValueFactory.createLoss());
//		
//		final String configuration = element.toString();
//		
//		final TwoTierElement newElement = new TwoTierElement();
//		newElement.configure(configuration);
//		
//		assertEquals(element.getFirstLevelDepth(), newElement.getFirstLevelDepth());
//		assertEquals(element.getFirstLevelGameValueShort(), newElement.getFirstLevelGameValueShort());
//		assertEquals(element.getSecondLevelGameValueShort(), newElement.getSecondLevelGameValueShort());
//		assertTrue(Arrays.equals(element.getFirstLevelBoardArrayInternalCopy(), newElement.getFirstLevelBoardArrayInternalCopy()));
//		assertTrue(Arrays.equals(element.getSecondLevelBoardArrayInternalCopy(), newElement.getSecondLevelBoardArrayInternalCopy()));
//	}
}
