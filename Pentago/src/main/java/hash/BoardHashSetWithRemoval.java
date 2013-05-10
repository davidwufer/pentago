package hash;

import java.util.HashMap;
import java.util.Map;

import board.Board;
import board.BoardArray;

/**
 * This is to be used only by BoardGenerator.java
 * @author David Wu
 *
 */
public class BoardHashSetWithRemoval implements BoardSet {

	private final Map<BoardArray, BoardArrayWithCounter> boardMap;
	
	public BoardHashSetWithRemoval() {
		this(new HashMap<BoardArray, BoardArrayWithCounter>());
	}
	
	BoardHashSetWithRemoval(Map<BoardArray, BoardArrayWithCounter> boardMap) {
		this.boardMap = boardMap;
	}
	
	@Override
	public boolean contains(Board board, boolean isSymmetriesEnabled) {
		final BoardArray boardArray = board.getBoardArray();
		
		if (boardMap.containsKey(boardArray)) {
			final BoardArrayWithCounter boardArrayWithCounter = boardMap.get(boardArray);
			boardArrayWithCounter.incrementHitsSoFar();
			
			if (boardArrayWithCounter.maxHitsReached()) {
				boardMap.remove(boardArray);
			}
			
			return true;
		}
		
		return (isSymmetriesEnabled && SymmetryChecker.contains(board, this));
	}
	
	@Override
	public void add(Board board) {
		final BoardArray boardArray = board.getBoardArray();
		final int numSymmetries = SymmetryChecker.getNumberOfUniqueSymmetricBoardsIncludingItself(board);
		
		final BoardArrayWithCounter boardArrayWithCounter = new BoardArrayWithCounter(numSymmetries);
		
		boardMap.put(boardArray, boardArrayWithCounter);
	}
	
	@Override
	public int size() {
		return boardMap.size();
	}

	@Override
	public void reset() {
		boardMap.clear();
	}
	
	protected static class BoardArrayWithCounter {
		private final int hitsBeforeRemoval;
		private int hitsSoFar;
		
		public BoardArrayWithCounter(int hitsBeforeRemoval) {
			this.hitsBeforeRemoval = hitsBeforeRemoval;
			hitsSoFar = 1;
		}
		
		public boolean maxHitsReached() {
			return hitsSoFar == hitsBeforeRemoval;
		}
		
		public void incrementHitsSoFar() {
			hitsSoFar += 1;
		}
	}
}
