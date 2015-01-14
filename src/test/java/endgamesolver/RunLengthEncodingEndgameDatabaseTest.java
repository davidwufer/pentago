package endgamesolver;

import junit.framework.TestCase;

import org.junit.Test;

import board.Board;
import board.BoardFactory;
import board.Piece;

public class RunLengthEncodingEndgameDatabaseTest extends TestCase {

	private final BoardConverter boardConverter = BoardConverter.getBoardConverter();
	
	public void testBoardToLongToBoardTest() {
		final int iterations = 10000;
		for (int currentIteration = 0; currentIteration < iterations; currentIteration++) {
			final Board board = BoardFactory.createSixBySixBoardRandom();
//			final Board board = BoardFactory.createFourByFourBoardRandom();
//			System.out.println(board);
			
			final Long boardAsLong = boardConverter.boardToLong(board);
			
//			System.out.println(boardAsLong);
			
			final Board boardFromLong = boardConverter.longToBoard(boardAsLong, true);
			
//			System.out.println(board.toString());
			assertEquals(board.toString() + " vs " + boardFromLong.toString(), board, boardFromLong);
		}
	}
	
	public void testBoardToLong() {
		final Board board = BoardFactory.createSixBySixBoardBlank().placePieceAt(0, Piece.X).placePieceAt(35, Piece.O);
		final Long longValue = boardConverter.boardToLong(board);
		
		final Long expectedValue = 100063090197999415L; 
		assertEquals(expectedValue, longValue);
	}
	
	@Test
	public void testLongToBoardWithLeadingZeroes() {
		final Long longValue = new Long(Piece.X.toInt());
		final Board board = boardConverter.longToBoard(longValue, true);
		
		final String expected = "[" + Piece.X.toChar() + "                                   ]";
		assertEquals(expected, board.toString());
		assertEquals(1, board.getPiecesOnBoard());
	}
	
	/* boardToBaseThreeStringRepresentation */
	@Test
	public void testBoardToBaseThreeStringRepresentationBlank() {
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final String expected = "000000000000000000000000000000000000";
		final String actual = boardConverter.boardToBaseThreeStringRepresentation(board);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBoardToBaseThreeStringRepresentationXAtZero() {
		Board board = BoardFactory.createSixBySixBoardBlank();
		board = board.placePieceAt(0, Piece.X);
		final String expected = "00000000000000000000000000000000000" + Piece.X.toInt();
		final String actual = boardConverter.boardToBaseThreeStringRepresentation(board);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBoardToBaseThreeStringRepresentationXAtZeroAndOAtEnd() {
		Board board = BoardFactory.createSixBySixBoardBlank();
		board = board.placePieceAt(0, Piece.X);
		board = board.placePieceAt(35, Piece.O);
		final String expected = Piece.O.toInt() + "0000000000000000000000000000000000" + Piece.X.toInt();
		final String actual = boardConverter.boardToBaseThreeStringRepresentation(board);
		assertEquals(expected, actual);
	}
}
