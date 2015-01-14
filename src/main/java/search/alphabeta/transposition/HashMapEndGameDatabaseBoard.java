package search.alphabeta.transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import board.Board;
import board.BoardFactory;

public class HashMapEndGameDatabaseBoard implements BoardDatabase {
	
	final Map<Board, GameValue> map;
	final int depth;
	
	public HashMapEndGameDatabaseBoard(int depth) {
		map = new HashMap<Board, GameValue>();
		this.depth = depth;
	}
	
	@Override
	public BoardDatabaseResult contains(Board board, boolean useSymmetries) {
//		final BoardArray boardArray = board.getBoardArray();
		if (map.containsKey(board)) {
			final GameValue value = map.get(board);
			return BoardDatabaseResult.getBoardDatabaseResult(value);
		}
		
		return BoardDatabaseResult.NO_RESULT;
	}

	@Override
	public boolean add(Board board, GameValue gameValue) {
		map.put(board, gameValue);
		return false;
	}

	/**
	 * writes to endgamedb/<depth>/
	 */
	@Override
	public boolean writeToFile(File file) {
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for (Board board : map.keySet()) {
				final GameValue gameValue = map.get(board);
				writer.write(board.toString());
				writer.newLine();
				writer.write(gameValue.toString());
				writer.newLine();
			}
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Warning: This clears the map!
	 */
	@Override
	public boolean readFromFile(File file) {
		map.clear();
		
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String boardAsString = null;  
			while ((boardAsString = reader.readLine()) != null) {  
				final Board board = BoardFactory.createSixBySixBoard(boardAsString);
				
				final String gameValueAsString = reader.readLine();
				final GameValue gameValue = GameValueFactory.createTerminalGameValue(gameValueAsString);
				
				map.put(board, gameValue);
			} 
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public long getNumberReplacedElements() {
		return 0;
	}

	@Override
	public long getSize() {
		return map.size();
	}
}
