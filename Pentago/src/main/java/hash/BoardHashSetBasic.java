package hash;

import java.util.HashSet;
import java.util.Set;

import board.Board;
import board.BoardArray;

public class BoardHashSetBasic extends AbstractBoardHashSet {
	private final Set<BoardArray> boardSet;
//	private final SymmetryChecker	symmetryChecker;
	
	//TODO: OPTIMIZE: put an initial load for the hash set
	public BoardHashSetBasic() {
		this(new HashSet<BoardArray>());
	}
	
	public BoardHashSetBasic(int initialSize) {
		this(new HashSet<BoardArray>(initialSize));
	}
	
	BoardHashSetBasic(Set<BoardArray> boardSet) {
		this.boardSet = boardSet;
	}
	
	@Override
	public boolean contains(Board board, boolean isSymmetriesEnabled) {
		final BoardArray boardArray = board.getBoardArray();
		if (boardSet.contains(boardArray)) {
			return true;
		}
		
		return (isSymmetriesEnabled && SymmetryChecker.contains(board, this));
	}
	
	@Override
	public void add(Board board) {
		if (!boardSet.add(board.getBoardArray())) {
			throw new OutOfHashSpaceException();
		}
	}
	
	@Override
	public int size() {
		return boardSet.size();
	}

	@Override
	public void reset() {
		boardSet.clear();
	}
}