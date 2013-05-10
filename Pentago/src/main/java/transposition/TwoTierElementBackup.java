package transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import board.Board;
import board.BoardArray;
import board.Piece;

/* We only need to store the first level depth because the criteria for booting is to check only the first level's depth */
public class TwoTierElementBackup {
	private static final byte INVALID_DEPTH = -1;
	private byte[] firstLevelBoardArrayInternal;
	private byte[] secondLevelBoardArrayInternal;
	private short firstLevelGameValueShort, secondLevelGameValueShort;
	
	private byte firstLevelDepth;
	
	public TwoTierElementBackup() {
		firstLevelBoardArrayInternal  = new byte[BoardArray.BOARD_ARRAY_SIZE];
		secondLevelBoardArrayInternal = new byte[BoardArray.BOARD_ARRAY_SIZE];
		initialize();
	}
	
	/** Returns a boolean if the first level has been moved to the second level
	 * @param board
	 * @return boolean
	 */
	// TODO: minimize the number of object creation!
	public boolean add(Board board, GameValue gameValue) {
		final BoardArray boardArray = BoardArray.cloneBoardArray(board.getBoardArray());
		
		final byte[] boardArrayInternal = boardArray.getInternalBoardArray();
		final short gameValueShort = (short) gameValue.getValue();
		final byte boardDepth = (byte) board.getPiecesOnBoard();
		
		if (isInvalidBoardArrayInternal(firstLevelBoardArrayInternal)) {
			System.arraycopy(boardArrayInternal, 0, firstLevelBoardArrayInternal, 0, BoardArray.BOARD_ARRAY_SIZE);
			firstLevelGameValueShort = gameValueShort;
			firstLevelDepth = boardDepth;
		} else if (firstLevelDepth < boardDepth) { // this condition determines the booting scheme
			System.arraycopy(boardArrayInternal, 0, secondLevelBoardArrayInternal, 0, BoardArray.BOARD_ARRAY_SIZE);
			secondLevelGameValueShort = gameValueShort;
		} else {
			final byte[] tempBoardPointer = secondLevelBoardArrayInternal;
			
			secondLevelBoardArrayInternal = firstLevelBoardArrayInternal;
			secondLevelGameValueShort = firstLevelGameValueShort;
			firstLevelBoardArrayInternal = tempBoardPointer;
			System.arraycopy(boardArrayInternal, 0, firstLevelBoardArrayInternal, 0, BoardArray.BOARD_ARRAY_SIZE);
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
	
	// Only used in test code
	public byte[] getFirstLevelBoardArray() {
		return firstLevelBoardArrayInternal;
	}

	// Only used in test code
	public byte[] getSecondLevelBoardArray() {
		return secondLevelBoardArrayInternal;
	}
	
	//TODO: cleanup?
	public BoardDatabaseResult contains(Board board) {
		if (firstLevelBoardArrayInternal == null) {
			return BoardDatabaseResult.NO_RESULT;
		}
		final int boardDepth = board.getPiecesOnBoard();		
		final BoardArray boardArray = BoardArray.cloneBoardArray(board.getBoardArray());
		final BoardArray firstLevelBoardArray = BoardArray.createNewBoardArray(firstLevelBoardArrayInternal);
		
		if (firstLevelDepth == boardDepth && firstLevelBoardArray.equals(boardArray)) {
			return BoardDatabaseResult.getBoardDatabaseResult(GameValueFactory.createTerminalGameValue(firstLevelGameValueShort));
		} else { 
			if (!isInvalidBoardArrayInternal(secondLevelBoardArrayInternal)) {
				final BoardArray secondLevelBoardArray = BoardArray.createNewBoardArray(secondLevelBoardArrayInternal);
				if (secondLevelBoardArray.equals(boardArray)) {
					return BoardDatabaseResult.getBoardDatabaseResult(GameValueFactory.createTerminalGameValue(secondLevelGameValueShort));
				}
			}
		}
		return BoardDatabaseResult.NO_RESULT;
	}
	
	private void invalidateBoardArrayInternal(byte[] boardArrayInternal) {
		boardArrayInternal[0] = Piece.valueOf(Piece.INVALID);
	}
	
	private boolean isInvalidBoardArrayInternal(byte[] boardArrayInternal) {
		return boardArrayInternal[0] == Piece.valueOf(Piece.INVALID);
	}

	public void initialize() {
		invalidateBoardArrayInternal(firstLevelBoardArrayInternal);
		invalidateBoardArrayInternal(secondLevelBoardArrayInternal);
		firstLevelDepth = INVALID_DEPTH;
		firstLevelGameValueShort = (short) GameValueFactory.getUndetermined().getValue();
		secondLevelGameValueShort = (short) GameValueFactory.getUndetermined().getValue();
	}
}
