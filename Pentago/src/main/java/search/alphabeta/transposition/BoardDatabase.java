package search.alphabeta.transposition;

import gamevalue.GameValue;

import java.io.File;

import board.Board;

public interface BoardDatabase {
	/* Returns true if the board is in the TT */
	public BoardDatabaseResult contains(Board board, boolean useSymmetries);
	
	/* This will return true if an entry in the TT has been kicked out */
	public boolean add(Board board, GameValue gameValue);
	
	public boolean writeToFile(File file);
	
	public boolean readFromFile(File file);
	
	public long getNumberReplacedElements();
	
	public long getSize();
}



