package hash;

import java.util.Arrays;

import board.Board;
import board.BoardArray;
import board.Piece;


/** This class holds the internalBoardArray and not the BoardArray itself
 * @author David Wu
 *
 */

// THIS IS BUGGY
@Deprecated
public class BoardHashSetSpaceOptimized implements BoardSet {

	public static BoardHashSetSpaceOptimized createBoardHashSetCustom(int maxSize) {
		return new BoardHashSetSpaceOptimized(maxSize);
	}
	private int currentSize;
	private final byte[][] hashArray;
	
	private final int maxSize;
	
	private BoardHashSetSpaceOptimized(int maxSize) {
		this.hashArray = new byte[maxSize][BoardArray.BOARD_ARRAY_SIZE];
		this.maxSize = maxSize;
		reset();
	}

	// For optimization purposes, I'm not going to check if the added board is already in the set
	@Override
	public void add(Board board) {
		final byte[] internalBoardArray = board.getBoardArray().getInternalBoardArray();
		final int boardHashIndex = calculateHashIndex(board);
		
		for (int indexOffset = 0; indexOffset < maxSize; indexOffset++) {
			final int currentHashIndex = (indexOffset + boardHashIndex) % maxSize;
			final byte[] currentInternalBoardArray = hashArray[currentHashIndex];
			
			if (isInvalidBoardArrayInternal(currentInternalBoardArray)) {
				System.arraycopy(internalBoardArray, 0, currentInternalBoardArray, 0, BoardArray.BOARD_ARRAY_SIZE);
				currentSize += 1;
				return;
			}
		}
		
		throw new OutOfHashSpaceException();
	}
	
	public int calculateHashIndex(Board board) {
		final int hashCode = getBoardHashCode(board);
		return (hashCode % maxSize + maxSize) % maxSize;
	}

	@Override
	public boolean contains(Board board, boolean isSymmetriesEnabled) {
		return containsHelper(board) || (isSymmetriesEnabled && SymmetryChecker.contains(board, this));
	}
	
	/* Created for mocking */
	public int getBoardHashCode(Board board) {
		return board.hashCode();
	}

	public int getCurrentHashArraySize() {
		return currentSize;
	}

	public byte[][] getHashArray() {
		return hashArray;
	}

	public int getMaxHashArraySize() {
		return maxSize;
	}
	
	@Override
	public void reset() {
		this.currentSize = 0;
		invalidateAllEntries();
	}
	
	@Override
	public int size() {
		return this.currentSize;
	}

	private boolean containsHelper(Board board) {
		final byte[] internalBoardArray = board.getBoardArray().getInternalBoardArray();
		final int boardHashIndex = calculateHashIndex(board);
		
		for (int indexOffset = 0; indexOffset < maxSize; indexOffset++) {
			final int currentHashIndex = (indexOffset + boardHashIndex) % maxSize;
			final byte[] currentInternalBoardArray = hashArray[currentHashIndex];
			
			if (isInvalidBoardArrayInternal(currentInternalBoardArray)) {
				return false;
			} else if (Arrays.equals(currentInternalBoardArray, internalBoardArray)) {
				return true;
			}
		}
		
		return false;
	}

	private void invalidateAllEntries() {
		for (int index = 0; index < maxSize; index++) {
			final byte[] newBoardArray = new byte[BoardArray.BOARD_ARRAY_SIZE];
			invalidateBoardArrayInternal(newBoardArray);
			hashArray[index] = newBoardArray;
		}
	}

	private void invalidateBoardArrayInternal(byte[] boardArrayInternalsHolder) {
		boardArrayInternalsHolder[0] = Piece.valueOf(Piece.INVALID);
	}
	
	private boolean isInvalidBoardArrayInternal(byte[] boardArrayInternal) {
		return boardArrayInternal[0] == Piece.valueOf(Piece.INVALID);
	}
}
