package search.alphabeta;

import game.Game;
import gamevalue.GameValue;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorResults;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import search.alphabeta.transposition.BoardDatabase;
import search.alphabeta.transposition.BoardDatabaseResult;
import board.Board;
import config.Config;

/**
 * @author dwu
 */
public class AlphaBetaRecursive extends AbstractMinimax {

	DateTime previousCheckDate = new DateTime();

	DepthTimer depthTimer;
	
	public AlphaBetaRecursive(Game startingGame, MoveGenerator moveGenerator, BoardDatabase table) {
		super(startingGame, moveGenerator, table);
	}

	@Override
	public GameValue runSearch(GameValue alpha, GameValue beta) {
		numTerminalsReached = 0;
		numExpandedNodes = 0;
		
		depthTimer = new DepthTimer(startingGame);
		
		solvedValue = alphaBetaSearch(startingGame, alpha, beta);
		return solvedValue;
	}

	private GameValue alphaBetaSearch(final Game game, GameValue alpha, GameValue beta) {
		GameValue currGameValue;
		final MoveGeneratorResults results = game.generateMoves(moveGenerator);
		
		numExpandedNodes += 1;
//		hookSearch(alpha, beta, game, numTerminalsReached);
		
		for (int currMoveIndex = 0; currMoveIndex < results.getNumMoves(); currMoveIndex += 1) {
			final Move currMove = results.getGeneratedMoves()[currMoveIndex];

			if (currMove.getResultingGameValue().isTerminalValue()) {
				numTerminalsReached += 1;
				currGameValue = currMove.getResultingGameValue();
			} else {
				final Game gameAfterDoMove = game.doMove(currMove);
				final Board boardAfterDoMove = gameAfterDoMove.getBoard();
				
				final BoardDatabaseResult ttResult = table.contains(boardAfterDoMove, true);
				if (ttResult.isResultFound()) {
					currGameValue = ttResult.getResult();
				} else {
					currGameValue = alphaBetaSearch(gameAfterDoMove, 
													beta.oppositeGameValue(), 
													alpha.oppositeGameValue())
									.oppositeGameValue();
					table.add(boardAfterDoMove, currGameValue);
				}
			}
			alpha = GameValue.max(alpha, currGameValue);
			
			if (beta.getValue() <= alpha.getValue()) {
				break;
			}
		}
		
//		depthTimer.updateDepth(game.getBoard().getPiecesOnBoard());
		
		return alpha;
	}

	@Override
	public void hookSearch(GameValue alpha, GameValue beta, Game game,
			long numTerminalsSearched) {
		if (numTerminalsSearched % Config.ALPHABETA_PRINTOUT_INTERVAL == 0 && numTerminalsSearched != 0) {
			System.out.print(game.getBoard().toPrettyString());
			System.out.println("Number of Terminals Searched: " + numTerminalsSearched);
			depthTimer.printDepthTimes();
			System.out.println();
			
			final DateTime newCheckDate = new DateTime();
			if (previousCheckDate.dayOfYear().get() != newCheckDate.dayOfYear().get()) {
				System.out.println("Writing TT...");
				table.writeToFile(null);
				System.out.println("Done writing TT...");
			}
			previousCheckDate = newCheckDate;
		}
	}
	
	private class DepthTimer {
		private final DateTime startTime;
		private final DateTime[] depthTimes;
		
		public DepthTimer(Game game) {
			startTime = new DateTime();
			depthTimes = new DateTime[game.getBoard().getNumberOfSpotsOnBoard() + 1];
		}
		
		public boolean updateDepth(int depth) {
			if (depthTimes[depth] == null) {
				depthTimes[depth] = new DateTime();
				return true;
			}
			return false;
		}
		
		public void printDepthTimes() {
			DateTime depthTime;
			for (int depth = depthTimes.length - 1; depth >= 0; depth -= 1) {
				if ((depthTime = depthTimes[depth]) == null) continue;
				
				final Hours hours = Hours.hoursBetween(startTime, depthTime);
				final float days = hours.getHours() / 24;
				
				System.out.println("Depth " + depth + ": " + days);
			}
		}
	}
}