package search.alphabeta.transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import java.util.Arrays;

import board.Board;
import board.BoardArray;
import board.Piece;

/* We only need to store the first level depth because the criteria for booting is to check only the first level's depth */
public class TwoTierElement {
	public static final String INVALID = "[INVALID]";
	private static final byte INVALID_DEPTH = -1;
	private short firstLevelGameValueShort, secondLevelGameValueShort;
	
	// The first 9 bytes will be for the first board, and the second 9 bytes will be for the second board
	final private byte[] boardArrayInternalsHolder;
	
	private byte firstLevelDepth;
	
	public TwoTierElement() {
		boardArrayInternalsHolder = new byte[BoardArray.BOARD_ARRAY_SIZE * 2];
		initialize();
	}
	
	/** Returns a boolean if the first level has been moved to the second level
	 * @param board
	 * @return boolean
	 */
	// TODO: minimize the number of object creation!
	public boolean add(Board board, GameValue gameValue) {
		final byte[] boardArrayInternal = board.getBoardArray().getInternalBoardArray();
		final short gameValueShort = (short) gameValue.getValue();
		final byte boardDepth = (byte) board.getPiecesOnBoard();
		
		final byte[] firstLevelBoardArrayInternal = getFirstLevelBoardArrayInternal();
		
		if (isInvalidBoardArrayInternal(firstLevelBoardArrayInternal)) {
			setFirstLevelBoardArrayInternal(boardArrayInternal);
			firstLevelGameValueShort = gameValueShort;
			firstLevelDepth = boardDepth;
		} else if (firstLevelDepth < boardDepth) { // this condition determines the booting scheme
			setSecondLevelBoardArrayInternal(boardArrayInternal);
			secondLevelGameValueShort = gameValueShort;
		} else {
			setSecondLevelBoardArrayInternal(firstLevelBoardArrayInternal);
			setFirstLevelBoardArrayInternal(boardArrayInternal);
			
			secondLevelGameValueShort = firstLevelGameValueShort;
			firstLevelGameValueShort = gameValueShort;
			firstLevelDepth = boardDepth;
			
			return true;
		}
		return false;
	}
	
	public int getFirstLevelDepth() {
		return firstLevelDepth;
	}
	
	// Only used in test code
	public short getFirstLevelGameValueShort() {
		return firstLevelGameValueShort;
	}

	// Only used in test code
	public short getSecondLevelGameValueShort() {
		return secondLevelGameValueShort;
	}
	
	public byte[] getFirstLevelBoardArrayInternal() {
		return Arrays.copyOfRange(boardArrayInternalsHolder, 0, BoardArray.BOARD_ARRAY_SIZE);
	}

	public byte[] getSecondLevelBoardArrayInternal() {
		return Arrays.copyOfRange(boardArrayInternalsHolder, BoardArray.BOARD_ARRAY_SIZE, BoardArray.BOARD_ARRAY_SIZE * 2);
	}
	
	public void setFirstLevelBoardArrayInternal(byte[] boardArrayInternal) {
		System.arraycopy(boardArrayInternal, 0, boardArrayInternalsHolder, 0, BoardArray.BOARD_ARRAY_SIZE);
	}
	
	public void setSecondLevelBoardArrayInternal(byte[] boardArrayInternal) {
		System.arraycopy(boardArrayInternal, 0, boardArrayInternalsHolder, BoardArray.BOARD_ARRAY_SIZE, BoardArray.BOARD_ARRAY_SIZE);
	}
	
	//TODO: cleanup?
	public BoardDatabaseResult contains(Board board) {
		final byte[] firstLevelBoardArrayInternal = getFirstLevelBoardArrayInternal();
		
		if (isInvalidBoardArrayInternal(firstLevelBoardArrayInternal)) {
			return BoardDatabaseResult.NO_RESULT;
		}
		
		final int boardDepth = board.getPiecesOnBoard();		
		final byte[] boardArrayInternal = board.getBoardArray().getInternalBoardArray();
		
		if (firstLevelDepth == boardDepth && Arrays.equals(firstLevelBoardArrayInternal, boardArrayInternal)) {
			return BoardDatabaseResult.getBoardDatabaseResult(GameValueFactory.createTerminalGameValue(firstLevelGameValueShort));
		} else { 
			final byte[] secondLevelBoardArrayInternal = getSecondLevelBoardArrayInternal();
			if (!isInvalidBoardArrayInternal(secondLevelBoardArrayInternal)) {
				if (Arrays.equals(secondLevelBoardArrayInternal, boardArrayInternal)) {
					return BoardDatabaseResult.getBoardDatabaseResult(GameValueFactory.createTerminalGameValue(secondLevelGameValueShort));
				}
			}
		} 
		return BoardDatabaseResult.NO_RESULT;
	}
	
	private void invalidateBoardArrayInternal(byte[] boardArrayInternalsHolder) {
		boardArrayInternalsHolder[0] = boardArrayInternalsHolder[BoardArray.BOARD_ARRAY_SIZE] = Piece.valueOf(Piece.INVALID);
	}
	
	private boolean isInvalidBoardArrayInternal(byte[] boardArrayInternal) {
		return boardArrayInternal[0] == Piece.valueOf(Piece.INVALID);
	}

	private void initialize() {
		invalidateBoardArrayInternal(boardArrayInternalsHolder);
		firstLevelDepth = INVALID_DEPTH;
		firstLevelGameValueShort = (short) GameValueFactory.getUndetermined().getValue();
		secondLevelGameValueShort = (short) GameValueFactory.getUndetermined().getValue();
	}
	
//	private Board getCorrectStubBoardFromBoardArrayInternal(byte[] boardArrayInternal) {
//		final Board board;
//		if (BoardArrayInternalUtil.getUtil().isFourByFourBoard(boardArrayInternal)) {
//			board = BoardFactory.createFourByFourBoardBlank();
//		} else {
//			board = BoardFactory.createSixBySixBoardBlank();
//		}
//		
//		board.setBoardArrayInternalWithoutPieceNumberChange(boardArrayInternal);
//		return board;
//	}
//	
//	/* FirstLevelDepth, FirstLevelShort, SecondLevelShort, BoardArrayString */
//	@Override
//	public String toString() { 
//		final byte[] firstLevelBoardArrayInternal = getFirstLevelBoardArrayInternal();
//		final byte[] secondLevelBoardArrayInternal = getSecondLevelBoardArrayInternal();
//		
//		String firstLevelBoardString, secondLevelBoardString;
//		if (isInvalidBoardArrayInternal(firstLevelBoardArrayInternal)) {
//			firstLevelBoardString = secondLevelBoardString = TwoTierElement.INVALID;
//		} else {
//			final Board firstLevelStubBoard = getCorrectStubBoardFromBoardArrayInternal(firstLevelBoardArrayInternal);
//			firstLevelBoardString = firstLevelStubBoard.toString();
//			if (isInvalidBoardArrayInternal(secondLevelBoardArrayInternal)) {
//				secondLevelBoardString = TwoTierElement.INVALID;
//			} else {
//				final Board secondLevelStubBoard = getCorrectStubBoardFromBoardArrayInternal(secondLevelBoardArrayInternal);
//				secondLevelBoardString = secondLevelStubBoard.toString(); 
//			}
//		}
//		
//		final StringBuilder builder = new StringBuilder().
//										append("firstLevelDepth: ").
//										append(firstLevelDepth).
//										append(',').
//										append("firstLevelGameValueShort: ").
//										append(firstLevelGameValueShort).
//										append(',').
//										append("secondLevelGameValueShort: ").
//										append(secondLevelGameValueShort).
//										append(',').
//										append("firstLevelBoard: ").
//										append(firstLevelBoardString).
//										append(',').
//										append("secondLevelBoard: ").
//										append(secondLevelBoardString).
//										append('\n');
//		return builder.toString();
//	}
}
