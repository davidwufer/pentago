package endgamesolver;

import endgamesolver.RunLengthEncoder.RunLengthEncoderNode;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import search.alphabeta.transposition.BoardDatabase;
import search.alphabeta.transposition.BoardDatabaseResult;
import util.IOUtil;
import board.Board;
import board.BoardFactory;

public class RunLengthEncodingEndgameDatabase implements BoardDatabase {

	private final RunLengthEncoder encoder;
	private final BoardConverter converter;
	
	public static void main(String[] args) {
		RunLengthEncodingEndgameDatabase database = new RunLengthEncodingEndgameDatabase(0L);
		File file = IOUtil.getEndGameDbIO(1).getNextEndGameDbFile();
		
		Board board = BoardFactory.createSixBySixBoardRandom();
		Board board2 = BoardFactory.createSixBySixBoardBlank();
		database.add(board2, GameValueFactory.getWin());
		database.add(board, GameValueFactory.getLoss());
		
		database.writeToFile(file);
		
		
		RunLengthEncodingEndgameDatabase newDatabase = new RunLengthEncodingEndgameDatabase(0L);
		newDatabase.readFromFile(file);
		
		System.out.println(newDatabase.toString());
	}
	
	public RunLengthEncodingEndgameDatabase(long startIndex) {
		this(new RunLengthEncoder(startIndex), BoardConverter.getBoardConverter());
	}
	
	protected RunLengthEncodingEndgameDatabase(RunLengthEncoder encoder, BoardConverter converter) {
		this.encoder = encoder;
		this.converter = converter;
	}
	
	@Override
	public boolean writeToFile(File file) {
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			writer.write(encoder.toString());
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean writeToBinaryFile(File file) {
		return writeToBinaryFileUpTo(file, encoder.getSize());
	}
	
	private boolean writeToBinaryFileUpTo(File file, int size) {
		try {
			final DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file, true));
			
			encoder.writeToOutputStream(outputStream, size);
			
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean writeToBinaryFileAllButLastNode(File file) {
		return writeToBinaryFileUpTo(file, encoder.getSize() - 1);
	}
	
	public boolean writeToFileAllButLastNode(File file) {
		try {
			final BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			final int numberOfNodesToWrite = encoder.getSize() - 1;
			writer.write(encoder.toString(numberOfNodesToWrite));
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean readFromFile(File file) {
		try {
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			final String initializationString = reader.readLine();
			encoder.initialize(initializationString);
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
		return encoder.getSize();
	}

	/* This ignores symmetries for now */
	@Override
	public BoardDatabaseResult contains(Board board, boolean useSymmetries) {
		final long boardAsLong = converter.boardToLong(board);
		return encoder.contains(boardAsLong, useSymmetries);
	}

	@Override
	public boolean add(Board board, GameValue gameValue) {
		final long index = converter.boardToLong(board);
		final RunLengthEncoderNode node = encoder.insert(index, gameValue);
		return node != null;
	}
	
	@Override
	public String toString() {
		return encoder.toString();
	}
	
	public long getFirstIndex() {
		return encoder.getFirstIndex();
	}
	
	public long getLastIndex() {
		return encoder.getLastIndex();
	}
	public void resetAndInitializeStartingIndexTo(long newStartingIndex) {
		encoder.reset(newStartingIndex);
	}

	public RunLengthEncoder getEncoder() {
		return encoder;
	}
}
