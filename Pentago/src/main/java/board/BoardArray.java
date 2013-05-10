package board;

import java.util.Arrays;

public class BoardArray {
	public final static int BOARD_ARRAY_SIZE = 9;
	protected static final BoardArray NEW_BOARD_ARRAY = new BoardArray(new byte[] {'0', '0', '0', '0', '0', '0', '0', '0', '0'}, false);

	private final byte[] internalBoardArray;
	
	public static BoardArray cloneBoardArray(BoardArray boardArray)  {
		return new BoardArray(boardArray.getInternalBoardArray(), false);
	}
	
	public static BoardArray createNewBoardArray() {
		return NEW_BOARD_ARRAY;
	}
	
	public static BoardArray createNewBoardArray(byte[] internalBoardArray) {
		return new BoardArray(internalBoardArray, true);
	}
	
	// System.arraycopy() is much faster than array.clone()
	protected static byte[] createInternalBoardArrayCopy(byte[] internalBoardArray) {
		final byte[] internalBoardArrayClone = new byte[BOARD_ARRAY_SIZE];
		System.arraycopy(internalBoardArray, 0, internalBoardArrayClone, 0, BOARD_ARRAY_SIZE);
		return internalBoardArrayClone;
	}
	
	protected BoardArray(byte[] newInternalBoard, boolean cloneInternalBoardArray) {
		internalBoardArray = cloneInternalBoardArray ? createInternalBoardArrayCopy(newInternalBoard) : newInternalBoard;
	}
	
	@Override
	public boolean equals(Object other) {
		final BoardArray comparingBoardArray = (BoardArray) other;
		return Arrays.equals(internalBoardArray, comparingBoardArray.internalBoardArray);
	}
	
	// We want to make sure the internalBoardArray is immutable, so we make a copy and return it
	public byte[] getInternalBoardArray() {
		return createInternalBoardArrayCopy(internalBoardArray);
	}
	
	/**
	 * Returns the piece at index. DUPLICATE METHOD IN BoardArrayBuilder. Change both if you change one.
	 * @param index
	 * @return
	 */
	public Piece getPieceAt(int index) {
	    byte currByte;
	    currByte = internalBoardArray[(index / 4)];
	    currByte >>= (2 * (index % 4));
	    currByte &= 3;
	    return Piece.getPieceFromVal(currByte);
	}
	
	/**
	 * Swaps pieces at the two indexes
	 * @param index1
	 * @param index2
	 * @return
	 */
	protected BoardArray swapPiecesAt(int index1, int index2) {
		return BoardArrayBuilder.newBuilder(this).swapPiecesAt(index1, index2).build();
	}
	
	// OPTIMIZE: This is a lot of object-creation overhead. 
	// TODO: Subclass so you have 6x6 BoardArrays and 4x4 BoardArrays
	public BoardArray flipOverX(int dim, int numberOfSpotsOnBoard) {
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(this);
	    for (int topRow = 0, bottomRow = (numberOfSpotsOnBoard - dim) ; (topRow * dim) < (bottomRow * dim) ; topRow += dim, bottomRow -= dim) {
	        for (int currCol = 0; currCol < dim; currCol++) {  
	        	builder.swapPiecesAt(topRow + currCol, bottomRow + currCol);
	        }
	    }
	    return builder.build();
	}
	
	public BoardArray flipOverY(int boardDimension, int numberOfSpotsOnBoard) {
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(this);
	    for (int leftCol = 0, rightCol = (boardDimension - 1) ; leftCol < rightCol ; leftCol++, rightCol--) {
	        for (int currRow = 0; currRow < numberOfSpotsOnBoard; currRow += boardDimension) {         
	        	builder.swapPiecesAt(currRow + leftCol, currRow + rightCol);
	        }
	    } 
	    return builder.build();
	}
	
	public BoardArray flipOverTopLeftToBottomRightDiag(int boardDimension, int numberOfSpotsOnBoard) {
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(this);
		for (int diag = 0; diag < numberOfSpotsOnBoard; diag += (boardDimension + 1)) {
			for (int horizontal = diag + 1, vertical = diag + boardDimension; vertical < numberOfSpotsOnBoard; horizontal += 1, vertical += boardDimension) {
				builder.swapPiecesAt(vertical, horizontal);
			}
		}
		return builder.build();
	}
	
	/**
	 * Loops from the bottom left diagonal to the top right diagonal and swaps the pieces
	 * @param boardDimension
	 * @param numberOfSpotsOnBoard
	 * @return
	 */
	public BoardArray flipOverBottomLeftToTopRightDiag(int boardDimension, int numberOfSpotsOnBoard) {
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(this);
		// diag > boardDimension instead of diag > 0 since we don't need to do anything for the top-right diagonal
		for (int diag = boardDimension * (boardDimension - 1);  diag > boardDimension; diag = diag - boardDimension + 1) {
			for (int horizontal = diag + 1, vertical = diag - boardDimension; vertical >= 0; horizontal += 1, vertical -= boardDimension) {
				builder.swapPiecesAt(vertical, horizontal);
			}
		}
		return builder.build();
	}
	
	/**
	 * Given a boardDimension, rotates the boardArray 90 degrees clockwise
	 * @param boardDimension
	 * @return
	 */
	public BoardArray rotateNinetyDegreesClockwise(int boardDimension) {
		return rotateSubSectionNinetyDegreesClockwise(boardDimension, boardDimension, 0, 0);
	}
	
	/**
	 * @param subBoard
	 * @param isClockWise
	 * @return
	 */
	public BoardArray rotateSubBoard(int boardDimension, int subBoard, boolean isClockWise) {
		if (subBoard == 0) {
			return this;
		}
		
		final int boardSubSectionWidth = boardDimension / 2;
		int colShift, rowShift;
		switch (subBoard) {
			case 1: colShift = rowShift = 0; break;
			case 2: colShift = boardSubSectionWidth; rowShift = 0; break;
			case 3: colShift = 0; rowShift = boardSubSectionWidth; break;
			case 4: colShift = rowShift = boardSubSectionWidth; break;
			default: throw new IllegalArgumentException("subBoard must be [0, 4]");
		}
		
		if (isClockWise) {
			return rotateSubSectionNinetyDegreesClockwise(boardDimension, boardSubSectionWidth, colShift, rowShift);
		} else {
			return rotateSubSectionNinetyDegreesCounterClockwise(boardDimension, boardSubSectionWidth, colShift, rowShift);
		}
		
	}
	
	/**
	 * Rotates an n x n subsection of a DIM * DIM board counter-clockwise
	 * Formula: new[i, j] = old[j, n-i-1] for n x n matrix
	 * SEE COMMENTS FOR rotateSubSectionNinetyDegreesClockwise since this class is similar
	 * @param boardDimension - should be DIM
	 * @param boardSubSectionWidth - the n in 'n x n' for the sub-section of the board you want to rotate
	 * @param colShift - how many columns over you want to start the rotation. [0, DIM)
	 * @param rowShift - how many rows down you want to start the rotation. [0, DIM)
	 * @return
	 */
	private BoardArray rotateSubSectionNinetyDegreesCounterClockwise(int boardDimension, int boardSubSectionWidth, int colOffset, int rowOffset) {
		final int offsetAddition = (boardDimension * rowOffset) + colOffset;
		
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(this);
		for (int row = 0; row < boardSubSectionWidth; row += 1) {
			for (int col = 0; col < boardSubSectionWidth; col += 1) {
				final int newIndex = (row * boardDimension) + col;
				final int oldIndex = (boardDimension * col) + boardSubSectionWidth - row - 1;
				
				
				final int newIndexWithOffset = newIndex + offsetAddition;
 				final int oldIndexWithOffset = oldIndex + offsetAddition;
 				
				final Piece piece = getPieceAt(oldIndexWithOffset);
				builder.placePieceAt(newIndexWithOffset, piece);
			}
		}
		return builder.build();
	}
	
	/**
	 * Rotates an n x n subsection of a DIM * DIM board clockwise
	 * Formula: new[i, j] = old[n-j-1, i] for n x n matrix
	 * The formula itself looks kind of tricky, but first we calculate the indexes with a relative offset of zero. After
	 * the indexes are calculated, we apply the offsetAddition part. We have to convert from cartesian (row, col) coordinates
	 * to 1d coordinates using (boardDimension * row) + col, so the actual formula used is a 1-d projection of the above version.
	 * @param boardDimension - should be DIM
	 * @param boardSubSectionWidth - the n in 'n x n' for the sub-section of the board you want to rotate
	 * @param colOffset - how many columns over you want to start the rotation. [0, DIM)
	 * @param rowOffset - how many rows down you want to start the rotation. [0, DIM)
	 * @return
	 */
	private BoardArray rotateSubSectionNinetyDegreesClockwise(int boardDimension, int boardSubSectionWidth, int colOffset, int rowOffset) {
		final int offsetAddition = (boardDimension * rowOffset) + colOffset;
		
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(this);
		for (int row = 0; row < boardSubSectionWidth; row += 1) {
			for (int col = 0; col < boardSubSectionWidth; col += 1) {
 				final int newIndex = boardDimension * (boardSubSectionWidth - col - 1) + row;
 				final int oldIndex = (row * boardDimension) + col;
 				
				final int newIndexWithOffset = newIndex + offsetAddition;
 				final int oldIndexWithOffset = oldIndex + offsetAddition;
 				
				final Piece piece = getPieceAt(newIndexWithOffset);
				builder.placePieceAt(oldIndexWithOffset, piece);
			}
		}
		return builder.build();
	}
	
	/**
	 * Places a piece at an index
	 * @param index
	 * @param pieceToPlace
	 * @return
	 */
	public BoardArray placePieceAt(int index, Piece pieceToPlace) {
		return BoardArrayBuilder.newBuilder(this).placePieceAt(index, pieceToPlace).build();
	}
	
	/* I'm implementing this to ensure that the value will stay consistent
	 * across runs. This is useful for serialization
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	//TODO: Make sure this is actually legit. Copying & Pasting code is prone to errors
	//TODO: Does this actually provide a good distribution of values for the hash code??
	@Override
	public int hashCode() {
//		return Arrays.hashCode(boardArray);
		int hash = 5381;
		for (int i = 0; i < 9; i++) {
			//sdbm
			hash = internalBoardArray[i] + (hash << 6) + (hash << 16) - hash;
			//djb2
//		    hash = ((hash << 5) + hash) + internalBoardArray[i];
//			hash = hash * 33 ^ internalBoardArray[i];
		}
		return hash;
	}
	
	
	// TODO: If you really want to use this, create an INVALID_BOARD_ARRAY singleton
	@Deprecated
	public void invalidateBoard() {
		setZeroIndex(Piece.valueOf(Piece.INVALID));
	}
	
	// TODO: rename these variable names because they are misleading
	public BoardArray setInternalBoardArray(byte[] newInternalBoardArray) {
		return new BoardArray(newInternalBoardArray, true);
	}

	@Deprecated
	byte getZeroIndex() {
		return internalBoardArray[0];
	}

	@Deprecated
	// TODO: If this is really used, we need to return an invalid board or something. This object needs to be IMMUTABLE
	void setZeroIndex(byte b) {
		internalBoardArray[0] = b;
	}
	
	protected int getDimension() {
		throw new UnsupportedOperationException("You must override this method");
	}
	
	protected int getNumberOfSpotsOnBoard() {
		throw new UnsupportedOperationException("You must override this method");
	}
}