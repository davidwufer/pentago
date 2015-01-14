package search.alphabeta.transposition;

import gamevalue.GameValue;
import board.Board;

public abstract class AbstractTranspositionTable implements BoardDatabase {
	
	@Override
	public BoardDatabaseResult contains(Board board, boolean isSymmetriesEnabled) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean add(Board board, GameValue gameValue) {
		throw new UnsupportedOperationException();
	}
	
	public boolean writeToFile() {
		throw new UnsupportedOperationException(); 
	}
	
	public boolean readFromFile() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public long getNumberReplacedElements() {
		throw new UnsupportedOperationException();
	}
}
