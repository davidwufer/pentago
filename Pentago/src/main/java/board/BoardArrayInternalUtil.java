package board;

public class BoardArrayInternalUtil {
	private static byte MAXIMUM_BYTE = ~0;
	
	private static final BoardArrayInternalUtil util = new BoardArrayInternalUtil();
	
	private BoardArrayInternalUtil() {
	}
	
	public static BoardArrayInternalUtil getUtil() {
		return util;
	}
	
	/* A bit hacky, but this works because a 4x4 board does not use the last byte */
	public void markAsFourByFourBoard(byte[] boardArrayInternal) {
		boardArrayInternal[BoardArray.BOARD_ARRAY_SIZE - 1] = MAXIMUM_BYTE;
	}
	
	public boolean isFourByFourBoard(byte[] boardArrayInternal) {
		return boardArrayInternal[BoardArray.BOARD_ARRAY_SIZE - 1] == MAXIMUM_BYTE;
	}
	
//	public Board markAsFourByFourBoard(Board board) {
//		board.get
//		return new FourByFourBoard(boardArray, board.getPiecesOnBoard());
//	}
	
//	public static void copyBoardArray(byte[] src, byte[] dest) {
//		System.arraycopy(src, 0, dest, 0, BoardArray.BOARD_ARRAY_SIZE);
//	}
}
