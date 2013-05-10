package hash;

import board.Board;

public interface BoardSet {
	boolean contains(Board board, boolean checkSymmetries);
	public void add(Board board);
	public int size();
	public void reset();
}
