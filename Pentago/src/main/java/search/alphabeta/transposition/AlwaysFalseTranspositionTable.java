package search.alphabeta.transposition;

import gamevalue.GameValue;

import java.io.File;

import board.Board;

public class AlwaysFalseTranspositionTable extends AbstractTranspositionTable {

	@Override
	public boolean add(Board board, GameValue gameValue) {
		return false;
	}

	@Override
	public BoardDatabaseResult contains(Board board, boolean isSymmetriesEnabled) {
		return BoardDatabaseResult.NO_RESULT;
	}

	@Override
	public long getNumberReplacedElements() {
		return 0;
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public boolean writeToFile(File file) {
		return false;
	}

	@Override
	public boolean readFromFile(File file) {
		return false;
	}
}
