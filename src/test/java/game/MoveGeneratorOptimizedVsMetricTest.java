package game;

import gamevalue.GameValue;
import hash.BoardSet;
import hash.BoardSetFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorMetric;
import move.MoveGeneratorOptimized;
import move.MoveGeneratorResults;
import movecomparator.HeuristicComparatorFactory;
import board.Board;
import board.BoardFactory;

public class MoveGeneratorOptimizedVsMetricTest extends TestCase {
	private Board gameBoard;
	private MoveGenerator metricMoveGenerator, optimizedMoveGenerator;
	private Game game;
	
	public void testEmptyFourByFourBoardWithSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoardBlank();
		game = GameFactory.createGame(gameBoard);
		
		final MoveGeneratorResults metricResults;
		metricMoveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator());
		metricResults = metricMoveGenerator.generateMoves(game);
		
		final MoveGeneratorResults optimizedResults;
		optimizedMoveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		optimizedResults = optimizedMoveGenerator.generateMoves(game);
		
		compareMoveResults(game, metricResults, optimizedResults);
	}

	// Not Deterministic!
	public void testABunchOfBoardsAgainstEachOther() {
		int NUM_RUNS = 500;
//		int NUM_RUNS = 5;
		while (NUM_RUNS >= 0) {
			//gameBoard = BoardFactory.createSixBySixBoardRandom();
			//gameBoard = BoardFactory.createSixBySixBoard("[     o      x xxxo  oo              ]");
			//gameBoard = BoardFactory.createSixBySixBoard("[      xxxx        o oo o            ]");
			gameBoard = BoardFactory.createFourByFourBoardRandom();
//			gameBoard = BoardFactory.createFourByFourBoard("[xx   o   o      ]");
			game = GameFactory.createGame(gameBoard);
//			System.out.println(gameBoard.toPrettyString());
			
			GameValue currGameState = game.getGameState();
			if (currGameState.isTerminalValue()) {
				continue;
			} 
			
			if (NUM_RUNS % 1000 == 0) {
				System.out.println("Runs left: " + NUM_RUNS);
			}
			
			
			final MoveGeneratorResults metricResults;
			metricMoveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator());
			metricResults = metricMoveGenerator.generateMoves(game);
			
			final MoveGeneratorResults optimizedResults;
			optimizedMoveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
			optimizedResults = optimizedMoveGenerator.generateMoves(game);
			
			compareMoveResults(game, metricResults, optimizedResults);
			
			NUM_RUNS -= 1;
		}
	}
	
	private void compareMoveResults(Game game, MoveGeneratorResults metricResults, MoveGeneratorResults optimizedResults) {
		MoveGeneratorResults largerResultSet, smallerResultSet;
		boolean isResultSetSame = true;
		
		if (metricResults.getNumMoves() != optimizedResults.getNumMoves()) {
			System.out.println("WARNING: Number of moves is different: " + metricResults.getNumMoves() + " vs " + optimizedResults.getNumMoves());
		}
		
		if (metricResults.getNumMoves() > optimizedResults.getNumMoves()) {
			largerResultSet = metricResults;
			smallerResultSet = optimizedResults;
		} else {
			largerResultSet = optimizedResults;
			smallerResultSet = metricResults;
		}
		
		//Add all moves of the smaller set into a hash
		BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
		for (int smallerIndex = 0; smallerIndex < smallerResultSet.getNumMoves(); smallerIndex++) {
			Move currMove = smallerResultSet.getGeneratedMoves()[smallerIndex];
			game.doMove(currMove);
			if (!boardSet.contains(game.getBoard(), true)) {
				boardSet.add(game.getBoard());
			}
			game.undoMove(currMove);
		}
		
		
		for (int largerIndex = 0; largerIndex < largerResultSet.getNumMoves(); largerIndex++) {
			Move currMove = largerResultSet.getGeneratedMoves()[largerIndex];
			game.doMove(currMove);
			if (!boardSet.contains(game.getBoard(), true)) {
				System.out.println(currMove.toString());
				isResultSetSame = false;
			}
			game.undoMove(currMove);
		}
		
		if (!isResultSetSame) {
			StringBuilder builder = new StringBuilder();
			builder.append("Result set is not the same for: ").append(game.getBoard().toString()).append('\n');
			builder.append("# metricResults: ").append(metricResults.getNumMoves()).append('\n');
			builder.append("# optimizedResults: ").append(optimizedResults.getNumMoves()).append('\n');
			fail(builder.toString());
		}
	}
}
