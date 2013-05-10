package solver;

import game.Game;
import game.GameFactory;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import hash.BoardSet;
import hash.BoardSetFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import move.MoveGenerator;
import move.MoveGeneratorFactory;
import movecomparator.HeuristicComparator;
import movecomparator.HeuristicComparatorFactory;
import search.SearchFactory;
import search.alphabeta.AlphaBetaSearch;
import search.alphabeta.transposition.BoardDatabase;
import search.alphabeta.transposition.BoardDatabaseFactory;
import board.Board;
import board.BoardFactory;
import config.Config;

public class AlphaBetaSolver extends Solver {

	public final Board board;
	public final Game game;
	public final BoardDatabase boardDatabase;
	public final HeuristicComparator heuristicComparator;
	public final MoveGenerator moveGenerator;
	public final AlphaBetaSearch minimax;
	public final BoardSet boardSet;
	
	public AlphaBetaSolver(Board board) {
//		this.board = BoardFactory.createSixBySixBoardBlank();
		this.board = board;
		this.game = GameFactory.createGame(board);
		
		// TODO: This could be 2-tier or single
		this.boardDatabase = BoardDatabaseFactory.createHashMapTT();
//		this.transpositionTable = TranspositionTableFactory.createTwoTierTT(Config.TTSIZE);
		this.heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		
//		this.boardSet = BoardSetFactory.createBoardHashSetBasic(Config.MAX_CHILDREN);
//		this.boardSet = BoardSetFactory.createBoardHashSetSpaceOptimized();
		this.boardSet = BoardSetFactory.createBoardHashSetBasic();
		
		this.moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, boardSet);
		
//		this.minimax = MinimaxFactory.createAlphaBetaIterative(game, moveGenerator, transpositionTable);
		this.minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, boardDatabase);
	}
	
	public void runSolver() {
//		Logger.clearLog();
//		transpositionTable.readFromFile();
//		final Calendar calendar = new GregorianCalendar();
//		calendar.get(Calendar.)
		
//		if (Config.RESUME) {
//			System.out.println("Reading TT from file...");
//			transpositionTable.readFromFile();
//			System.out.println("Done reading TT");
//		}
		
//		final StringBuilder results = new StringBuilder();
		
//		System.out.println("Running the search...");
//		System.out.println("TTSize: " + boardDatabase.getSize());
//		final DateTime startTime = new DateTime();
		final GameValue solvedValue = minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin());
//		final DateTime finishedTime = new DateTime();
//		results.append("Total Number of Terminals: " + minimax.getNumTerminalsReached() + '\n');
//		results.append("Total Number of Expanded Positions: " + minimax.getNumExpandedNodes() + '\n');
//		results.append("Final Value: " + solvedValue + '\n');
//		
//		final Days differenceInDays = Days.daysBetween(startTime, finishedTime);
//		
//		results.append("Days to completion: " + differenceInDays.getDays() + '\n');
//		System.out.println(results.toString());
		
//		writeResultsToFile(results.toString());
	}
	
	private void writeResultsToFile(String string) {
		try {
			final File file = new File(Config.RESULTS);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(string);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 3) {
			final int userSpecifiedTTSize = Integer.parseInt(args[0]);
			final int userSpecifiedPrintoutInterval = Integer.parseInt(args[1]);
			final boolean userSpecifiedResume = Boolean.parseBoolean(args[2]);
			Config.TTSIZE = userSpecifiedTTSize;
			Config.ALPHABETA_PRINTOUT_INTERVAL = userSpecifiedPrintoutInterval;
			Config.RESUME = userSpecifiedResume;
		}
		AlphaBetaSolver solver = new AlphaBetaSolver(BoardFactory.createFourByFourBoardBlank());
		solver.runSolver();
	}

	@Override
	public GameValue solve() {
		return minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin());
	}

	@Override
	public long getNumExpandedNodes() {
		return minimax.getNumExpandedNodes();
	}

	@Override
	public long getNumTerminalsReached() {
		return minimax.getNumTerminalsReached();
	}
}
