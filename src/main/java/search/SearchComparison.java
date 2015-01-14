package search;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import solver.AlphaBetaSolver;
import solver.DfPnSolver;
import solver.SearchStats;
import solver.Solver;
import board.Board;
import board.BoardFactory;
import config.Config;

/**
 * Class to compare different classes of search for Pentago
 * @author dwu
 *
 */
public class SearchComparison {
	
	private static final int ITERATIONS = 500000;
	private static final int DEPTH_UPPER = 35;
	private static final int DEPTH_LOWER = 20;

	public List<SearchStats> gatherAlphaBetaStats() {
		// from max depth to min depth, run alpha-beta search on 100,000 random, solveable boards
		return null;
	}
	
	public static void main(String[] args) throws IOException {
//		Board board = BoardFactory.createSixBySixBoard("[ooxxooxxoxox oxoxxox oxoxxoxooxoxoxo]");
//		Solver abSolver = new AlphaBetaSolver(board);
//		Solver pnSolver = new DfPnSolver(board);
//		
//		System.out.println(abSolver.solveAndGetStats());
//		System.out.println(pnSolver.solveAndGetStats());
//		
//		System.exit(1);
		
//		for (int depth = DEPTH_UPPER; depth >= DEPTH_LOWER; depth -= 1) {
//			BoardGenerator generator = new BoardGenerator(ITERATIONS, depth);
//			generator.generateAndWriteToFile();
//		}
		
		for (int depth = DEPTH_UPPER; depth >= DEPTH_LOWER; depth -= 1) {
			final SolverWriter reader = new SolverWriter(depth);
			
			reader.readFromFileAndSolveAtTheSameTime(false);
			reader.readFromFileAndSolveAtTheSameTime(true);
		}
	}
	
	private static class SolverWriter {
		private final int depth;
		
		public SolverWriter(int depth) throws IOException {
			this.depth = depth;
			
			readFromFile();
		}
		
		public Collection<Board> getBoards() throws IOException {
			return readFromFile();
		}
		
		// If alpha-beta is false, then we will assume it's df-pn.
		public void readFromFileAndSolveAtTheSameTime(boolean isAlphaBeta) throws IOException {
			final String fileName = depth + (isAlphaBeta ? "_ab.txt" : "_pn.txt");
			
			final File writeFile = new File(Config.SEARCH_COMPARISON_PATH, fileName);
			
			// Do nothing if it already exists
			if (writeFile.exists()) { return; }
			
			writeFile.createNewFile();
			final BufferedWriter writer = new BufferedWriter(new FileWriter(writeFile));
			
			try {
			
				int iteration = 0;
				final File readFile = new File(Config.SEARCH_COMPARISON_PATH, depth + ".dat");
				final BufferedReader reader = new BufferedReader(new FileReader(readFile));
				try {
					String boardString;
					while ((boardString = reader.readLine()) != null) {
						if (iteration % 1000 == 0) {
							writer.flush();
							System.out.print(isAlphaBeta ? "AB" : "PN");
							System.out.println(", Depth: " + depth + ", Iteration: " + iteration);
						}
						
						final Board board = BoardFactory.createSixBySixBoard(boardString);
						
						final Solver solver = isAlphaBeta ? new AlphaBetaSolver(board) : new DfPnSolver(board);
						
						final SearchStats stats = solver.solveAndGetStats();
						
						writer.write(stats.toString());
						
						iteration += 1;
					}
					
				} finally {
					reader.close();
				}
			} finally {
				writer.close();
			}
		}
		
		public Collection<Board> readFromFile() throws IOException {
			final Collection<Board> boards = new LinkedList<Board>();
			final File file = new File(Config.SEARCH_COMPARISON_PATH, depth + ".dat");
			file.createNewFile();
			final BufferedReader reader = new BufferedReader(new FileReader(file));
			try {
				String boardString;
				while ((boardString = reader.readLine()) != null) {
					final Board board = BoardFactory.createSixBySixBoard(boardString);
					boards.add(board);
				}
				
			} finally {
				reader.close();
			}
			return boards;
		}
	}
	
	private static class BoardGenerator {
		private final Set<Board> uniqueBoards;
		private final int iterations;
		private final int depth;
		
		public BoardGenerator(int iterations, int depth) {
			this.uniqueBoards = new HashSet<Board>();
			this.iterations = iterations;
			this.depth = depth;
		}
		
		public List<Board> getBoards() {
			while (uniqueBoards.size() != iterations) {
				if (uniqueBoards.size() % 100000 == 0) { 
					System.out.println(uniqueBoards.size());
				}
//			for (int currIteration = 0; currIteration < iterations; currIteration += 1) {
				Board board = BoardFactory.createSixBySixBoardAtRandomDepth(depth);
				if (!uniqueBoards.contains(board) && !board.getGameState().isTerminalValue()) {
					uniqueBoards.add(board);
				}
			}
			 
			return new ArrayList<Board>(uniqueBoards);
		}
		
		public void generateAndWriteToFile() throws IOException {
			final File file = new File(Config.SEARCH_COMPARISON_PATH, depth + ".dat");
			
			if (file.exists()) { 
				return;
			}
			
			file.createNewFile();
			final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			try {
				for (Board board : getBoards()) {
					writer.write(board.toString() + '\n');
				}
			} finally {
				writer.close();
			}
		}
	}
}
