package board;

public class BoardArrayBuilder {

	private final byte[] internalBoardArray;
	
	private BoardArrayBuilder(byte[] internalBoardArray) {
		this.internalBoardArray = internalBoardArray;
	}
	
//	public static BoardArrayBuilder newBuilder() {
//		final byte[] internalBoardArray = new byte[BoardArray.BOARD_ARRAY_SIZE];
//		return new BoardArrayBuilder(internalBoardArray);
//	}
	
	public static BoardArrayBuilder newBuilder(BoardArray boardArray) {
		return new BoardArrayBuilder(boardArray.getInternalBoardArray());
	}
	
	// TODO: This does an extra arraycopy
	public BoardArray build() {
		return new BoardArray(internalBoardArray, false);
	}
	
	public BoardArrayBuilder swapPiecesAt(int index1, int index2) {
		final Piece index1Piece = getPieceAt(index1);
		final Piece index2Piece = getPieceAt(index2);
		if (index1Piece != index2Piece) {
			return placePieceAt(index1, index2Piece).placePieceAt(index2, index1Piece);
		} else {
			return this;
		}
	}
	
	public BoardArrayBuilder placePieceAt(int index, Piece pieceToPlace) {
//		final Piece pieceToReplace = getPieceAt(index);
//		if (!pieceToReplace.equals(pieceToPlace)) {
    	final int boardIndex = index / 4;
    	final int shiftAmount = 2 * (index % 4);
    	internalBoardArray[boardIndex] &= ~(3 << shiftAmount);
    	if (pieceToPlace != Piece.BLANK) {
       		internalBoardArray[boardIndex] ^= (Piece.valueOf(pieceToPlace) << shiftAmount);
   		}
//		}
		return this;
	}
	
	/**
	 * DUPLICATE METHOD IN BoardArray. Change both if you change one.
	 * @param index
	 * @return
	 */
	protected Piece getPieceAt(int index) {
	    byte currByte;
	    currByte = internalBoardArray[(index / 4)];
	    currByte >>= (2 * (index % 4));
	    currByte &= 3;
	    return Piece.getPieceFromVal(currByte);
	}
	
	protected byte[] getInternalBoardArray() {
		return internalBoardArray;
	}
}
