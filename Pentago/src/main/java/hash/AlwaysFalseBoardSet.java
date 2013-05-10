package hash;

import board.Board;

public class AlwaysFalseBoardSet implements BoardSet {

	@Override
	public void add(Board board) {
		// do nothing
	}

	@Override
	public boolean contains(Board board, boolean isSymmetriesEnabled) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public void reset() {}
}
