package search.alphabeta.transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import util.ConsoleOutput;
import board.Board;
import board.BoardFactory;
import config.Config;

public class TwoTierTranspositionTable extends AbstractTranspositionTable {

//	private static Class<? extends Board> boardType;
	private static TwoTierElement[] tableContents;
	private static TwoTierTranspositionTable table;
	private static int twoTierTranspositionTableSize;
//	private static int numReplacedElements;
	
	private TwoTierTranspositionTable() { }
	
	public static TwoTierTranspositionTable createTwoTierTranspositionTable(int size) {
		System.out.println("Creating TT...");
		if (table != null) {
			ConsoleOutput.printWarning("Table has already been made once. Unless you are running tests, you shouldn't be making this again");
		}
		table = new TwoTierTranspositionTable();
		twoTierTranspositionTableSize = size;
		tableContents = new TwoTierElement[size];
		for (int index = 0; index < size; index++) {
			tableContents[index] = new TwoTierElement();
			
		}
		System.out.println("Done creating TT");
		return table;
	}
	
	@Override
	public boolean add(Board board, GameValue gameValue) {
		final int index = getHashIndex(board);
		return tableContents[index].add(board, gameValue);
	}
	
	public TwoTierElement[] getTableContents() {
		return tableContents;
	}

	// I don't know why, but this returned a negative value before
	public int getHashIndex(Board board) {
		final int TTSIZE = Math.abs(twoTierTranspositionTableSize);
		return (board.hashCode() % TTSIZE + TTSIZE) % TTSIZE;
//		return Math.abs(board.hashCode()) % twoTierTranspositionTableSize;
	}
	
	@Override
	public BoardDatabaseResult contains(Board board, boolean isSymmetriesEnabled) {
		BoardDatabaseResult result;

		final int index = getHashIndex(board);
		result = tableContents[index].contains(board);
		
		if (isSymmetriesEnabled && !result.isResultFound()) {
			result = TranspositionSymmetryChecker.contains(board, this);
		}
		return result;
	}
	
	@Override
	public boolean writeToFile(File file) {
		return TwoTierTranspositionTableIO.writeTT();
	}
	
	@Override
	public boolean readFromFile(File file) {
		return TwoTierTranspositionTableIO.readTT(this);
	}
	
	protected static class TwoTierTranspositionTableIO {
		protected static File createIfDoesNotExist(String path, boolean isDirectory) {
			final File file = new File(path);
			if (!file.exists()) {
				try {
					if (isDirectory) {
						file.mkdir();
					} else {
						file.createNewFile();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			return file;
		}
		
		public static boolean readTT(BoardDatabase ttable) {
			final File currentData = new File(Config.TT_CURRENT);
			
			try {
				final BufferedReader reader = new BufferedReader(new FileReader(currentData));
				final int storedTTSize = Integer.parseInt(reader.readLine().trim());
				
				Config.TTSIZE = storedTTSize;
				
				// Take each element and hash it to a new location
				for (int index = 0; index < storedTTSize; index++) {
					configure(ttable, reader.readLine());
				}
				
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		public static void configure(BoardDatabase ttable, String configuration) {
			final String[] data = configuration.split("\\s*,\\s*");
//			final int firstLevelDepth = Byte.valueOf(data[0]);
			final short firstLevelGameValueShort = Short.valueOf(data[1]);
			final short secondLevelGameValueShort = Short.valueOf(data[2]);
			
			final String firstLevelBoardString = data[3].trim();
			final String secondLevelBoardString = data[4].trim();
			
			if (!firstLevelBoardString.equals(TwoTierElement.INVALID)) {
				final Board firstLevelBoard = BoardFactory.createBoardFromConfiguration(firstLevelBoardString);
//				firstLevelBoard.setPiecesOnBoard(firstLevelDepth);
				ttable.add(firstLevelBoard, GameValueFactory.createTerminalGameValue(firstLevelGameValueShort));
				if (!secondLevelBoardString.equals(TwoTierElement.INVALID)) {
					final Board secondLevelBoard = BoardFactory.createBoardFromConfiguration(secondLevelBoardString);
					ttable.add(secondLevelBoard, GameValueFactory.createTerminalGameValue(secondLevelGameValueShort));
				}
			}
		}

		public static boolean writeTT() {
			final File directory = createIfDoesNotExist(Config.TT_PATH, true);
			final File previousData = createIfDoesNotExist(Config.TT_PREVIOUS, false);
			final File currentData = createIfDoesNotExist(Config.TT_CURRENT, false);
			final File pendingData = createIfDoesNotExist(Config.TT_PENDING, false);
			
			if (directory == null || previousData == null || pendingData == null || currentData == null) {
				return false;
			}
			
			try {
				final BufferedWriter writer = new BufferedWriter(new FileWriter(pendingData));
				TwoTierElement twoTierElement;
				writer.write(String.valueOf(twoTierTranspositionTableSize) + '\n');
				for (int index = 0; index < twoTierTranspositionTableSize; index++) {
					twoTierElement = tableContents[index];
					writer.write(twoTierElement.toString());
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			final boolean previousDelete = previousData.delete();
			final boolean currentDataRename = currentData.renameTo(new File(Config.TT_PREVIOUS));
			final boolean pendingDataRename = pendingData.renameTo(new File(Config.TT_CURRENT));
			
			return previousDelete && currentDataRename && pendingDataRename;
		}
	}

	@Override
	public long getSize() {
		return twoTierTranspositionTableSize;
	}
}
