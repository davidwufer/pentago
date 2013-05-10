package transposition;

import gamevalue.GameValue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import board.Board;
import board.BoardArray;

public class HashMapTranspositionTable extends AbstractTranspositionTable {
	
	Map<BoardArray, GameValue> hashMap;
	
	public HashMapTranspositionTable() {
		hashMap = new HashMap<BoardArray, GameValue>();
	}
	
	@Override
	public BoardDatabaseResult contains(Board board, boolean isSymmetriesEnabled) {
		BoardArray boardArray = BoardArray.cloneBoardArray(board.getBoardArray());
		if (hashMap.containsKey(boardArray)) {
			GameValue gameValueResult = hashMap.get(boardArray);
			return BoardDatabaseResult.getBoardDatabaseResult(gameValueResult);
		} else if (isSymmetriesEnabled) {
			return TranspositionSymmetryChecker.contains(board, this);
		} else {
			return BoardDatabaseResult.NO_RESULT;
		}
	}
	
	@Override
	public boolean add(Board board, GameValue gameValue) {
		if (gameValue.isTerminalValue()) {
			BoardArray boardArray = BoardArray.cloneBoardArray(board.getBoardArray());
			return hashMap.put(boardArray, gameValue) != null;
		} else {
			throw new IllegalArgumentException("Only terminal values should be added to a TT");
		}
	}

	@Override
	public long getNumberReplacedElements() {
		return 0;
	}

	@Override
	public long getSize() {
		return hashMap.size();
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
