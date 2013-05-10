package board;

import gamevalue.GameValueFactory;
import heuristic.FourByFourBoardHeuristicCalculator;
import heuristic.SixBySixBoardHeuristicCalculator;
import junit.framework.TestCase;
import move.Move;
import move.MoveFactory;

import org.junit.Test;



/**
 * @author David Wu
 *
 */

// TODO: separate 6x6 and 4x4 board tests
public class BoardTest extends TestCase {
	
	public void testSixBySixBoardWidth() {
		final Board b = BoardFactory.createSixBySixBoardBlank();
		assertEquals("Should have a width of 6", 6, b.getWidth());
	}
	
	public void testSixBySixBoardHeight() {
		final Board b = BoardFactory.createSixBySixBoardBlank();
		assertEquals("Should have a height of 6", 6, b.getHeight());
	}
	
	public void testFourByFourBoardWidth() {
		final Board b = BoardFactory.createFourByFourBoardBlank();
		assertEquals("Should have a width of 4", 4, b.getWidth());
	}
	
	public void testFourByFourBoardHeight() {
		final Board b = BoardFactory.createFourByFourBoardBlank();
		assertEquals("Should have a width of 4", 4, b.getHeight());
	}
	
	
	public void testCreateSixBySixBoard() {
		final Board b = BoardFactory.createSixBySixBoardBlank();
//		assertEquals("Should have a dimension of 6", 6, b.getDimension());
		assertEquals("Should have n-in-a-row of 5",  5, b.getNInARow());
	}
	
	public void testCreateFourByFourBoard() {
		final Board b = BoardFactory.createFourByFourBoardBlank();
//		assertEquals("Should have a dimension of 4", 4, b.getDimension());
		assertEquals("Should have n-in-a-row of 3",  3, b.getNInARow());
	}
	
	/* AbstractBoard Tests */
	public void testGetPieceForNewBoard() {
		final Board  b          = BoardFactory.createSixBySixBoardBlank();
		
		for (int index = 0; index < b.getNumberOfSpotsOnBoard(); index++) {
			assertEquals("Board should be initialized to all blanks", b.getPieceAt(index), Piece.BLANK);
		}
	}
	
	@Test
	public void testGetPieceReturnsTheCorrectValueWhenAPieceIsOverwritten() {
		final Board  b          = BoardFactory.createSixBySixBoardBlank();
		
		final int index = 11;
		final Piece piece1 = Piece.O;
		final Piece piece2 = Piece.BLANK;
		final BoardArray boardArray = b.getBoardArray();
		final Board newBoard = BoardFactory.createSixBySixBoardWithPiecesAndBoardArray(0, boardArray.placePieceAt(index, piece1).placePieceAt(index, piece2));
		
		final Piece p2 = newBoard.getPieceAt(index);
		assertEquals("O Piece first, then BLANK Piece manual assignment", piece2, p2);
	}
	
	@Test
	public void testGetPieceO() {
		final Board  b          = BoardFactory.createSixBySixBoardBlank();
		
		final int index = 11;
		final Piece piece = Piece.O;
		final BoardArray boardArray = b.getBoardArray();
		final Board newBoard = BoardFactory.createSixBySixBoardWithPiecesAndBoardArray(0, boardArray.placePieceAt(index, piece));
		
		final Piece p2 = newBoard.getPieceAt(index);
		assertEquals("O Piece after manual assignment", piece, p2);
	}
	
	@Test
	public void testGetPieceX() {
		final Board  b          = BoardFactory.createSixBySixBoardBlank();
		
		final int index = 5;
		final Piece piece = Piece.X;
		final BoardArray boardArray = b.getBoardArray();
		final Board newBoard = BoardFactory.createSixBySixBoardWithPiecesAndBoardArray(0, boardArray.placePieceAt(index, piece));
		
		final Piece p2 = newBoard.getPieceAt(index);
		assertEquals("X Piece after manual assignment", piece, p2);
	}
	
	public void testPlacePieceX() {
		Board  board          = BoardFactory.createSixBySixBoardBlank();
		byte[] internalBoardArray = board.getBoardArray().getInternalBoardArray();
		
		final Piece piece = Piece.X;
		board = board.placePieceAt(0, piece);
		internalBoardArray = board.getBoardArray().getInternalBoardArray();
		assertEquals("X Piece should be set", Piece.valueOf(piece), internalBoardArray[0]);
	}
	
	public void testPlacePieceBLANK() {
		Board  board          = BoardFactory.createSixBySixBoardBlank();
		byte[] internalBoardArray = board.getBoardArray().getInternalBoardArray();
		
		final Piece piece = Piece.BLANK;
		board = board.placePieceAt(0, piece);
		internalBoardArray = board.getBoardArray().getInternalBoardArray();
		assertEquals("BLANK Piece should be set", Piece.valueOf(piece), internalBoardArray[0]);
	}
	
	public void testPlacePieceO() {
		Board  board          = BoardFactory.createSixBySixBoardBlank();
		byte[] internalBoardArray = board.getBoardArray().getInternalBoardArray();
		
		final Piece piece = Piece.O;
		board = board.placePieceAt(9, piece);
		internalBoardArray = board.getBoardArray().getInternalBoardArray();
		assertEquals("O Piece should be set", Piece.valueOf(piece), Piece.valueOf(Piece.O) << 2, internalBoardArray[2]);
	}
	
	public void testGetAndSetPieceInConjunction() {
		Board board = BoardFactory.createSixBySixBoardBlank();
		
		for (int index = 0; index < board.getNumberOfSpotsOnBoard(); index++) {
			assertEquals("BLANK Piece should be set", Piece.BLANK, board.getPieceAt(index));
			board = board.placePieceAt(index, Piece.O);
			assertEquals("O Piece should be set", Piece.O, board.getPieceAt(index));
		}
		
	}
	
	public void testInitializedVariablesForSixBySixBoard() {
		Board sixBoard  = BoardFactory.createSixBySixBoardBlank();
		assertEquals("N_IN_A_ROW should be 5 for 6x6 board", 5,   sixBoard.getNInARow());
		assertEquals("NUMBER_OF_SPOTS_ON_BOARD should be 36 for 6x6 board", 
				36, sixBoard.getNumberOfSpotsOnBoard());
	}
	
	public void testInitializedVariablesForFourByFourBoard() {
		Board fourBoard  = BoardFactory.createFourByFourBoardBlank();
		assertEquals("N_IN_A_ROW should be 5 for 6x6 board", 3, fourBoard.getNInARow());
		assertEquals("NUMBER_OF_SPOTS_ON_BOARD should be 36 for 6x6 board", 
				16, fourBoard.getNumberOfSpotsOnBoard());
	}
	
//	public void testInvalidateBoard() {
//		Board sixBoard = BoardFactory.createSixBySixBoardBlank();
//		assertTrue("Should be valid upon creation", sixBoard.isValidBoard());
//		sixBoard.invalidateBoard();
//		assertFalse("Should be invalid", sixBoard.isValidBoard());
//	}
	
	public void testInitializedBoardToString() {
		Board sixBoard = BoardFactory.createSixBySixBoardBlank();
		assertEquals("String form of a board just initialized should be right", 
				                        "[                                    ]",
				                                          sixBoard.toString());
	}
	
	public void testBoardToString() {
		final Board sixBoard = BoardFactory.createSixBySixBoardBlank().
												placePieceAt(5,  Piece.O).
												placePieceAt(35, Piece.X);
		assertEquals("String form of a board just initialized should be right", 
				                        "[     o                             x]",
				                                          sixBoard.toString());
	}
	
	public void testBoardToPrettyStringForFourByFourBoard() {
		StringBuilder testingString = new StringBuilder();
		Board fourByFourBoard = BoardFactory.createFourByFourBoard("[xxooxxooxxooxxo ]");
		testingString.append("  +------+------+\n");
		testingString.append(" 4| x  x | o  o |\n");
		testingString.append(" 3| x  x | o  o |\n");
		testingString.append("  +------+------+\n");
		testingString.append(" 2| x  x | o  o |\n");
		testingString.append(" 1| x  x | o    |\n");
		testingString.append("  +------+------+\n");
		assertEquals("Board to pretty string for 4x4",
				testingString.toString(),
				fourByFourBoard.toPrettyString());
	}
	
	public void testBoardToPrettyStringForSixBySixBoard() {
		StringBuilder testingString = new StringBuilder();
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xoxoxo      xxxxxxooooooxoxoxooxoxox]");
		testingString.append("  +---------+---------+\n");
		testingString.append(" 6| x  o  x | o  x  o |\n");
		testingString.append(" 5|         |         |\n");
		testingString.append(" 4| x  x  x | x  x  x |\n");
		testingString.append("  +---------+---------+\n");
		testingString.append(" 3| o  o  o | o  o  o |\n");
		testingString.append(" 2| x  o  x | o  x  o |\n");
		testingString.append(" 1| o  x  o | x  o  x |\n");
		testingString.append("  +---------+---------+\n");
		assertEquals("Board to pretty string for 6x6",
				testingString.toString(),
				sixBySixBoard.toPrettyString());
	}
	
	public void testConfigureBoardForSixBySixBoard() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoardBlank();
		assertEquals("Board string should be configured for a 6x6 board with no configuration passed in",
				sixBySixBoard.toString(), "[                                    ]");
	}
	
	public void testConfigureBoardForFourByFourBoard() {
		Board fourByFourBoard = BoardFactory.createFourByFourBoardBlank();
		assertEquals("Board string should be configured for a 4x4 board with no configuration passed in",
				fourByFourBoard.toString(), "[                ]");
	}
	
	public void testPiecesOnBoardForBlankBoard() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoardBlank();
		assertEquals("Pieces on board for 6x6 initial board should be 0", 0, sixBySixBoard.getPiecesOnBoard());
	}
	
	public void testPiecesOnBoardForConfiguredBoard() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		assertEquals("Pieces on board for 6x6 configured board", 15, sixBySixBoard.getPiecesOnBoard());
	}
	
	public void testGettingPlayerPiece() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoardBlank();
		assertEquals("Initial board should have X as the current player", 
				Piece.X, sixBySixBoard.currPlayerPiece());
	}
	
	public void testGettingWaitingPlayerPiece() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[x                                   ]");
		assertEquals("Board with one piece should have O as the current player", 
				Piece.X, sixBySixBoard.waitingPlayerPiece());
	}
	
	public void testRotateSixBySixBoardSubBoardFourTimes() {
		Board sixBySixBoard = BoardFactory.
				createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").
									rotateSubBoard(1, true).
									rotateSubBoard(1, true).
									rotateSubBoard(1, true).
									rotateSubBoard(1, true);
		
		assertEquals("Four rotations of 1st subBoard should return to original board", 
				                             "[xo xo x     xo xo x     xo xo x     ]", 
				                                            sixBySixBoard.toString());
	}
	
	@Test
	public void testRotateSixBySixBoardSubBoardBy90ClockwiseOnce() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(1, true);
//		System.out.println(BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(1, true).toPrettyString());
//		System.out.println(sixBySixBoard.toPrettyString());
		assertEquals("Rotation of 1st subBoard should be right", 
				       "[xxxxo o o      xo x     xo xo x     ]", 
				                      sixBySixBoard.toString());
	}
	
	@Test
	public void testRotateSixBySixBoardSubBoard90ClockwiseWithZeroSubBoard() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(0, true);
		
		assertEquals("Rotation of subBoard 0 should yield the same board", 
                "[xo xo x     xo xo x     xo xo x     ]", 
                               sixBySixBoard.toString());
	}
	
	@Test
	public void testRotateSixBySixBoardSubBoard90ClockwiseWithFourthSubBoard() {
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(4, true);
		assertEquals("Rotation of 4th subBoard should be right", 
				       "[xo xo x     xo xo x   x xo  o x     ]", 
				                      sixBySixBoard.toString());
	}
	
	@Test
	public void testRotateSixBySixBoardSubBoard90FourTimesShouldYieldOriginalBoard() {
		final int subBoard = 0;
		final boolean isClockwise = false;
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").
				rotateSubBoard(subBoard, isClockwise).
				rotateSubBoard(subBoard, isClockwise).
				rotateSubBoard(subBoard, isClockwise).
				rotateSubBoard(subBoard, isClockwise);
		assertEquals("Rotation of subBoard 0 should not change the board", 
						"[xo xo x     xo xo x     xo xo x     ]", 
				                      sixBySixBoard.toString());
	}	
	
	@Test
	public void testRotateSixBySixBoardSubBoard90CounterClockwiseWithZeroSubBoard() {
		final int subBoard = 0;
		final boolean isClockwise = false;
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(subBoard, isClockwise);
		assertEquals("Rotation of subBoard 0 should not change the board", 
						"[xo xo x     xo xo x     xo xo x     ]", 
				                      sixBySixBoard.toString());
	}
	
	@Test
	public void testRotateSixBySixBoardSubBoard90CounterClockwiseOnce() {
		final int subBoard = 1;
		final boolean isClockwise = false;
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(subBoard, isClockwise);
		assertEquals("Rotation of subBoard" + subBoard + "counter-clockwise should be correct", 
						"[   xo o o   xxxxo x     xo xo x     ]", 
				                      sixBySixBoard.toString());
	}
	
	@Test
	public void testRotateSixBySixBoardSubBoard90CounterClockwiseWithSecondSubboard() {
		final int subBoard = 4;
		final boolean isClockwise = false;
		Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]").rotateSubBoard(subBoard, isClockwise);
		assertEquals("Rotation of subBoard" + subBoard + "counter-clockwise should be correct", 
						"[xo xo x     xo xo x     xo  o x   x ]", 
				                      sixBySixBoard.toString());
	}
	
	public void testFlipOverX() {
		final Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		final Board sixBySixBoardFlipped = sixBySixBoard.flipOverX();
		assertEquals("Flipping over X should work",
				"[x     xo xo x     xo xo x     xo xo ]",
				sixBySixBoardFlipped.toString());
		
		final Board sixBySixBoardFlippedAgain = sixBySixBoardFlipped.flipOverX();
		assertEquals("Flipping over X twice should return the original board",
				                     "[xo xo x     xo xo x     xo xo x     ]",
				                                    sixBySixBoardFlippedAgain.toString());
	}
	
	public void testFlipOverY() {
		final Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		final Board sixBySixBoardFlipped = sixBySixBoard.flipOverY();
		
		assertEquals("Flipping over X should work",
				"[ ox ox     x ox ox     x ox ox     x]",
				sixBySixBoardFlipped.toString());
		
		final Board sixBySixBoardFlippedAgain = sixBySixBoardFlipped.flipOverY();
		assertEquals("Flipping over Y twice should return the original board",
				                     "[xo xo x     xo xo x     xo xo x     ]",
				                                    sixBySixBoardFlippedAgain.toString());
	}
	
	public void testFlipOverDiagFromBottomLeftToTopRight() {
		final Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		System.out.println(sixBySixBoard.toPrettyString());
		final Board sixBySixBoardFlipped = sixBySixBoard.flipOverBottomLeftToTopRightDiag();
		System.out.println(sixBySixBoardFlipped.toPrettyString());
		assertEquals("Flipping over y = -x should work",
				"[       o o o x x x       o o oxxxxxx]",
				sixBySixBoardFlipped.toString());
		
		final Board sixBySixBoardFlippedAgain = sixBySixBoardFlipped.flipOverBottomLeftToTopRightDiag();
		System.out.println(sixBySixBoardFlippedAgain.toPrettyString());
		assertEquals("Flipping over y = -x twice should return the original board",
				                     "[xo xo x     xo xo x     xo xo x     ]",
				                                    sixBySixBoardFlippedAgain.toString());
	}
	
	public void testFlipOverDiagFromTopLeftToBottomRight() {
		final Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		final Board sixBySixBoardFlipped = sixBySixBoard.flipOverTopLeftToBottomRightDiag();
		assertEquals("Flipping over y = x should work",
				"[xxxxxxo o o       x x x o o o       ]",
				sixBySixBoardFlipped.toString());
		
		final Board sixBySixBoardFlippedAgain = sixBySixBoardFlipped.flipOverTopLeftToBottomRightDiag();
		assertEquals("Flipping over y = x twice should return the original board",
				                     "[xo xo x     xo xo x     xo xo x     ]",
				                                    sixBySixBoardFlippedAgain.toString());
	}
	
	public void testRotateNinetyDegreesClockwise() {
		final Board sixBySixBoard = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		final Board sixBySixBoardRotatedOnce = sixBySixBoard.rotateNinetyDegreesClockwise();
		assertEquals("Rotating 90 degrees once",
				"[xxxxxx o o o       x x x o o o      ]",
				sixBySixBoardRotatedOnce.toString());
		
		final Board sixBySixBoardRotatedFourTimes = sixBySixBoardRotatedOnce.rotateNinetyDegreesClockwise().
																			 rotateNinetyDegreesClockwise().
																		     rotateNinetyDegreesClockwise();
		assertEquals("Rotating 90 degrees four times should return the original board",
				                     "[xo xo x     xo xo x     xo xo x     ]",
				                                    sixBySixBoardRotatedFourTimes.toString());
	}
	
	private void verifyBoardClone(Board board) {
		Board boardCopy = BoardFactory.createBoardClone(board);
		
		assertTrue("A copied board should not point to the original board", board != boardCopy);
		assertEquals("WIDTH should be equal", board.getWidth(), boardCopy.getWidth());
		assertEquals("HEIGHT should be equal", board.getHeight(), boardCopy.getHeight());
		assertEquals("NInARow should be equal", board.getNInARow(), boardCopy.getNInARow());
		assertEquals("Number of spots on board should be equal", board.getNumberOfSpotsOnBoard(), boardCopy.getNumberOfSpotsOnBoard());
		assertEquals("Number of spots on board should be equal", board.getPiecesOnBoard(), boardCopy.getPiecesOnBoard());
		assertTrue("Internal board should be the same", board.getBoardArray().equals(boardCopy.getBoardArray()));
	}
	
	public void testCopyBoardSixBySixBoard() {
		Board board = BoardFactory.createSixBySixBoardBlank();
		verifyBoardClone(board);
	}
	
	public void testCopyBoardFourByFourBoard() {
		Board board = BoardFactory.createFourByFourBoardBlank();
		verifyBoardClone(board);
	}
	
	public void testBoardEqualsForSixBySixBoard() {
		Board board;
		board = BoardFactory.createSixBySixBoardBlank();
//		assertEquals("Board should be equal to itself", board, board);
		assertTrue("Board should be equal to itself", board.equals(board));
//		assertEquals("Blank board should be equal to a newly created blank board", board, BoardFactory.createBlankSixBySixBoard());
		assertTrue("Blank board should be equal to a newly created blank board", board.equals(BoardFactory.createSixBySixBoardBlank()));
		
		Board board1, board2;
		board1 = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		board2 = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		assertTrue("A created board should be equal to a created board with the same configuration", board1.equals(board2));
	}

	public void testBoardEqualsForFourByFourBoard() {
		Board board;
		board = BoardFactory.createFourByFourBoardBlank();
		assertTrue("Board should be equal to itself", board.equals(board));
		assertTrue("Blank board should be equal to a newly created blank board", board.equals(BoardFactory.createFourByFourBoardBlank()));
		
		Board board1, board2;
		board1 = BoardFactory.createFourByFourBoard("[xxooxxoo        ]");
		board2 = BoardFactory.createFourByFourBoard("[xxooxxoo        ]");
		assertTrue("A created board should be equal to a created board with the same configuration", board1.equals(board2));
		
		// TODO: test properties of equals
	}
	
	public void testHashCodeForSixBySixBoard() {
		Board board1, board2;
		board1 = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		board2 = BoardFactory.createSixBySixBoard("[xo xo x     xo xo x     xo xo x     ]");
		
		assertEquals("Hash code of a board should be equal to the hash code of a board with the same configuration",
				board1.hashCode(),
				board2.hashCode());
	}
	
	public void testHashCodeForFourByFourBoard() {
		Board board1, board2;
		board1 = BoardFactory.createFourByFourBoard("[xxooxxoo        ]");
		board2 = BoardFactory.createFourByFourBoard("[xxooxxoo        ]");
		
		assertEquals("Hash code of a board should be equal to the hash code of a board with the same configuration",
				board1.hashCode(),
				board2.hashCode());
	}
	
	public void testCreatingABoardMatchesBoardArrayInternal() {
		Board boardEmpty     = BoardFactory.createFourByFourBoard("[                ]");
		Board boardWithPiece = BoardFactory.createFourByFourBoard("[x               ]");
		
//		System.out.println(boardWithPiece.toString());
//		for (int index = 0; index < 9; index++) {
//			System.out.printf("%3d ", boardWithPiece.getBoardArray().getInternalBoardArray()[index] - 0);
//		}
//		System.out.println();
		
		assertFalse(boardEmpty.getBoardArray().equals(boardWithPiece.getBoardArray()));
		
//		System.out.println(boardWithPiece.getBoardArray());
	}
	
	public void testIsSixBySixBoardFromBoardArrayInternal() {
		final Board emptyBoard = BoardFactory.createSixBySixBoardBlank();
		assertFalse(BoardArrayInternalUtil.getUtil().isFourByFourBoard(emptyBoard.getBoardArray().getInternalBoardArray()));
	}
	
	public void testIsSixBySixBoardPartiallyFilledFromBoardArrayInternal() {
		final Board board = BoardFactory.createSixBySixBoard("[xo xo xo    xo xo xo    xo xo xo   x]");
		assertFalse(BoardArrayInternalUtil.getUtil().isFourByFourBoard(board.getBoardArray().getInternalBoardArray()));
	}
	
//	public void testIsFourByFourBoardFromBoardArrayInternal() {
//		final Board emptyBoard = BoardFactory.createFourByFourBoardBlank();
//		assertTrue(BoardArrayInternalUtil.getUtil().isFourByFourBoard(emptyBoard.getBoardArray().getInternalBoardArray()));
//	}
//	
//	public void testIsFourByFourBoardPartiallyFilledFromBoardArrayInternal() {
//		final Board board = BoardFactory.createFourByFourBoard("[x              o]");
//		assertTrue(BoardArrayInternalUtil.getUtil().isFourByFourBoard(board.getBoardArray().getInternalBoardArray()));
//	}
//	
//	@Test
//	public void testIsFourByFourForSixBySixBoardArrayInternal() {
//		final Board board = BoardFactory.createSixBySixBoardBlank();
//		assertFalse(BoardArrayInternalUtil.getUtil().isFourByFourBoard(board.getBoardArray().getInternalBoardArray()));
//	}
	
	/* Should:
	 * 1) Have the right player turn
	 * 2) Put the right piece
	 * 3) Turn the right subBoard
	 * 4) Have the right pieces on board
	 * 5) Have the right player turn afterwards
	 */
	@Test
	public void testDoMove() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Move move = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		// 1
		assertEquals("Player should be O", Piece.O, gameBoard.currPlayerPiece());
		// 4a
		assertEquals("Pieces on board should be 1", 1, gameBoard.getPiecesOnBoard());
		// 2, 3		
		final Board gameBoardAfterMove = gameBoard.doMove(move);
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
	@Test
	public void testUndoMove() {
		final Board gameBoard = BoardFactory.createSixBySixBoard("[xo xo ox ox o xo xxo xo  ox ox xo xo]");
		final Move move = MoveFactory.createMove(6, 1, true, GameValueFactory.getDraw());
		
		// 1
		assertEquals("Player should be X", Piece.X, gameBoard.currPlayerPiece());
		// 4a
		assertEquals("Pieces on board should be 24", 24, gameBoard.getPiecesOnBoard());
		// 2, 3
		final Board gameBoardAfterUndoMove = gameBoard.undoMove(move);
		assertEquals("Game board should be correct", "[  xxo  x ox xooo xxo xo  ox ox xo xo]", gameBoardAfterUndoMove.toString());	
		// 4b
		assertEquals("Pieces on board should now be 23", 23, gameBoardAfterUndoMove.getPiecesOnBoard());
		// 5
		assertEquals("Player should be O", Piece.O, gameBoardAfterUndoMove.currPlayerPiece());
	}
	
	@Test
	public void testIncrementPiecesOnBoardValue() {
		final Board board = BoardFactory.createSixBySixBoardBlank();
		
		assertEquals("Number of spots on board should initially be 0", 0, board.getPiecesOnBoard());
		
		final Board boardAfterIncrementPieces = board.incrementPiecesOnBoard();
		
		assertEquals("Number of spots on board should initially be 1", 1, boardAfterIncrementPieces.getPiecesOnBoard());
	}
	
	@Test
	public void testIncrementPiecesOnBoardCreatesNewBoard() {
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final Board boardAfterIncrementPieces = board.incrementPiecesOnBoard();

		assertTrue("Board after incrementing pieces should be a new board", board != boardAfterIncrementPieces);
	}
	
	@Test
	public void testIncrementPiecesOnBoardShouldNotChangeOriginalBoard() {
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final BoardArray boardArray = board.getBoardArray();
		final int piecesOnBoard = board.getPiecesOnBoard();
		
		// increment the pieces on board
		board.incrementPiecesOnBoard();

		assertEquals(boardArray, board.getBoardArray());
		assertEquals(piecesOnBoard, board.getPiecesOnBoard());
	}
	
	@Test
	public void testDecrementPiecesOnBoardValue() {
		final Board board = BoardFactory.createSixBySixBoard("[x                                   ]");
		
		assertEquals("Number of spots on board should initially be 1", 1, board.getPiecesOnBoard());
		
		final Board boardAfterDecrementPieces = board.decrementPiecesOnBoard();
		
		assertEquals("Number of spots on board should initially be 0", 0, boardAfterDecrementPieces.getPiecesOnBoard());
	}
	
	@Test
	public void testDecrementPiecesOnBoardCreatesNewBoard() {
		final Board board = BoardFactory.createSixBySixBoard("[x                                   ]");
		final Board boardAfterDecrementPieces = board.decrementPiecesOnBoard();

		assertTrue("Board after incrementing pieces should be a new board", board != boardAfterDecrementPieces);
	}
	
	@Test
	public void testDecrementPiecesOnBoardShouldNotChangeOriginalBoard() {
		final Board board = BoardFactory.createSixBySixBoard("[x                                   ]");
		final BoardArray boardArray = board.getBoardArray();
		final int piecesOnBoard = board.getPiecesOnBoard();
		
		// increment the pieces on board
		board.decrementPiecesOnBoard();

		assertEquals(boardArray, board.getBoardArray());
		assertEquals(piecesOnBoard, board.getPiecesOnBoard());
	}
	
	@Test
	public void testGetHeuristicCalculatorForFourByFourBoard() {
		final Board board = BoardFactory.createFourByFourBoardBlank();
		assertTrue(board.getHeuristicCalculator() instanceof FourByFourBoardHeuristicCalculator);
	}
	
	@Test
	public void testGetHeuristicCalculatorForSixBySixBoard() {
		final Board board = BoardFactory.createSixBySixBoardBlank();
		assertTrue(board.getHeuristicCalculator() instanceof SixBySixBoardHeuristicCalculator);
	}
	
	@Test
	public void testDoMoveWithSubBoardZero() {
		final Board board = BoardFactory.createFourByFourBoard("[xx  oo  oo  xx  ]");
		final Move move = MoveFactory.createMove(14, 0, false, GameValueFactory.getUndetermined());
		assertEquals("[xx  oo  oo  xx  ]", board.toString());
		final Board boardAfterDoMove = board.doMove(move);
		assertEquals("[xx  oo  oo  xxx ]", boardAfterDoMove.toString());
	}
}
