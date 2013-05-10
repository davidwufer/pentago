package search.alphabeta;

import game.Game;
import gamevalue.GameValue;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorResults;
import search.alphabeta.AlphaBetaIterative.DepthData.DepthDataEntry;
import search.alphabeta.transposition.BoardDatabase;
import config.Config;

/* THIS CLASS HAS NOT BEEN FINISHED. DO NOT USE IT */
@Deprecated
public class AlphaBetaIterative extends AbstractMinimax {

	private final DepthData depthData;
	
	public AlphaBetaIterative(Game game, MoveGenerator moveGenerator, BoardDatabase boardDatabase) {
		super(game, moveGenerator, boardDatabase);
		depthData = new DepthData(game.getBoard().getNumberOfSpotsOnBoard());
	}

	@Override
	public GameValue runSearch(GameValue alpha, GameValue beta) {
		solvedValue = alphaBetaSearch(startingGame, alpha, beta);
		return solvedValue;
	}

	private GameValue alphaBetaSearch(Game startingGame, GameValue alpha, GameValue beta) {
		// Initialize for the beginning depths (You should change this implementation later on)
		final int initialDepth = 0;
		final MoveGeneratorResults moveResults = startingGame.generateMoves(moveGenerator);
		final DepthDataEntry initialDepthDataEntry = new DepthDataEntry(startingGame, alpha, beta, moveResults.getNumMoves(), 0, moveResults.getGeneratedMoves());
		
		depthData.setEntry(initialDepthDataEntry, initialDepth);
		
		int currDepth = initialDepth;
		
		while (initialDepthDataEntry.alphaLessThanBeta() && initialDepthDataEntry.stillHasMoves()) {
			hookSearch(alpha, beta, startingGame, numTerminalsReached);
			
			DepthDataEntry currLevelDepthDataEntry = depthData.getEntry(currDepth);
			
			while (currLevelDepthDataEntry.alphaLessThanBeta() && currLevelDepthDataEntry.stillHasMoves()) {
				
				final Move currMove = currLevelDepthDataEntry.getCurrentMove();
				final GameValue moveAlpha = currMove.getResultingGameValue();
				
				if (moveAlpha.isTerminalValue()) {
					currLevelDepthDataEntry = currLevelDepthDataEntry.getBuilder().
												setAlpha(GameValue.max(currLevelDepthDataEntry.alpha, moveAlpha)).
												incrementCurrentMoveIndex().
												build();
					depthData.setEntry(currLevelDepthDataEntry, currDepth);
					
				} else {
					final Game currentGame = currLevelDepthDataEntry.getGame();
					final Game doMoveGame = currentGame.doMove(currMove);
					
					final MoveGeneratorResults nextMoveGeneratorResults = doMoveGame.generateMoves(moveGenerator);
					
					final DepthDataEntry prevLevelDepthDataEntry = currLevelDepthDataEntry;
					
					
					final DepthDataEntry nextLevelDepthDataEntry = currLevelDepthDataEntry = depthData.getEntry(currDepth).getBuilder().
																	setAlpha(prevLevelDepthDataEntry.beta.oppositeGameValue()).
																	setBeta(prevLevelDepthDataEntry.alpha.oppositeGameValue()).
																	setCurrentMoveIndex(0).
																	setNumMoves(nextMoveGeneratorResults.getNumMoves()).
																	setGenerateMoves(nextMoveGeneratorResults.getGeneratedMoves()).
																	setGame(doMoveGame).
																	build();
					
					currDepth += 1;
					depthData.setEntry(nextLevelDepthDataEntry, currDepth);
				}
			}
			
			if (currDepth == 0) {
				break;
			}
			
			final DepthDataEntry returningLevelDepthDataEntry = currLevelDepthDataEntry;
			
			currDepth -= 1;
			currLevelDepthDataEntry = depthData.getEntry(currDepth).getBuilder().
										setAlpha(GameValue.max(currLevelDepthDataEntry.alpha, returningLevelDepthDataEntry.alpha.oppositeGameValue())).
										incrementCurrentMoveIndex().
										build();
			
			depthData.setEntry(currLevelDepthDataEntry, currDepth);
			
			table.add(startingGame.getBoard(), returningLevelDepthDataEntry.alpha.oppositeGameValue());
			
		}
		
		return initialDepthDataEntry.alpha;
	}

	@Override
	public void hookSearch(GameValue alpha, GameValue beta, Game game,
			long numTerminalsSearched) {
		if (numTerminalsSearched % Config.ALPHABETA_PRINTOUT_INTERVAL == 0 && numTerminalsSearched != 0) {
			System.out.print(game.getBoard().toPrettyString());
			System.out.println("Number of Terminals Searched: " + numTerminalsSearched);
			System.out.println();
		}
	}
	
	protected static class DepthData {
		
		private final DepthDataEntry[] depthDataEntries;
		
		public DepthData(int numberOfSpotsOnBoard) {
			depthDataEntries = new DepthDataEntry[numberOfSpotsOnBoard];
		}
		
		public DepthDataEntry getEntry(int depth) {
			return depthDataEntries[depth];
		}
		
		public DepthData setEntry(DepthDataEntry depthDataEntry, int depth) {
			depthDataEntries[depth] = depthDataEntry;
			return this;
		}

		protected static class DepthDataEntry {
			private final GameValue alpha;
			private final GameValue beta;
			private final int currentMoveIndex;
			
			private final Game game;
			private final int numMoves;
			private final Move[] generatedMoves;
			
			public DepthDataEntry(Game game, GameValue alpha, GameValue beta, int numMoves, int currMoveIndex, Move[] generatedMoves) {
				this.alpha = alpha;
				this.beta = beta;
				this.currentMoveIndex = currMoveIndex;
				
				this.game = game;
				this.numMoves = numMoves;
				this.generatedMoves = generatedMoves;
			}
			
			public GameValue getAlpha() {
				return alpha;
			}

			public GameValue getBeta() {
				return beta;
			}

			public int getCurrentMoveIndex() {
				return currentMoveIndex;
			}

			public Game getGame() {
				return game;
			}

			public int getNumMoves() {
				return numMoves;
			}

			public Move[] getGeneratedMoves() {
				return generatedMoves;
			}
			
			public boolean stillHasMoves() {
				return currentMoveIndex < numMoves;
			}
			
			public boolean alphaLessThanBeta() {
				return beta.getValue() > alpha.getValue(); 
			}
			
			public Move getCurrentMove() {
				return generatedMoves[currentMoveIndex];
			}
			
			public DepthDataEntryBuilder getBuilder() {
				return new DepthDataEntryBuilder(this);
			}
			
			public static class DepthDataEntryBuilder {
				private GameValue alpha;
				private GameValue beta;
				private int currentMoveIndex;
				
				private Game game;
				private int numMoves;
				private Move[] generatedMoves;
				
				public DepthDataEntryBuilder(DepthDataEntry depthDataEntry) {
					this.alpha = depthDataEntry.getAlpha();
					this.beta = depthDataEntry.getBeta();
					this.currentMoveIndex = depthDataEntry.getCurrentMoveIndex();
					this.game = depthDataEntry.getGame();
					this.numMoves = depthDataEntry.getNumMoves();
					this.generatedMoves = depthDataEntry.getGeneratedMoves();
				}
				
				public DepthDataEntryBuilder setAlpha(GameValue alpha) {
					this.alpha = alpha;
					return this;
				}
				
				public DepthDataEntryBuilder setBeta(GameValue beta) {
					this.beta = beta;
					return this;
				}
				
				public DepthDataEntryBuilder setCurrentMoveIndex(int newMoveIndex) {
					this.currentMoveIndex = newMoveIndex;
					return this;
				}
				
				public DepthDataEntryBuilder incrementCurrentMoveIndex() {
					this.currentMoveIndex += 1;
					return this;
				}
				
				public DepthDataEntryBuilder setGame(Game game) {
					this.game = game;
					return this;
				}
				
				public DepthDataEntryBuilder setNumMoves(int numMoves) {
					this.numMoves = numMoves;
					return this;
				}
				
				public DepthDataEntryBuilder setGenerateMoves(Move[] generatedMoves) {
					this.generatedMoves = generatedMoves;
					return this;
				}
				
				public DepthDataEntry build() {
					return new DepthDataEntry(game, alpha, beta, numMoves, currentMoveIndex, generatedMoves);
				}
			}
			
		}
		
		// Used for tests
		public Object size() {
			return depthDataEntries.length;
		}
	}
}