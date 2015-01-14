package move;

import game.Game;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import hash.BoardSet;

import java.util.Arrays;

import movecomparator.HeuristicComparator;
import board.Board;
import board.Piece;

public class MoveGeneratorOptimized extends AbstractMoveGenerator {

		private Move[] uniqueMoves;
		private int numMoves;
		
//		private final Game game;
//		private final Board gameBoard;
		
		private final HeuristicComparator heuristicComparator;
		private final boolean isWinImmediatelyReturned;
		private final boolean useSymmetries;
		
		private final BoardSet boardSet;
		
		public MoveGeneratorOptimized(HeuristicComparator heuristicComparator, BoardSet boardSet) {
			this(heuristicComparator, boardSet, true, true);
		}

		public MoveGeneratorOptimized(HeuristicComparator heuristicComparator, BoardSet boardSet, boolean isWinImmediatelyReturned, boolean useSymmetries) {
//			this.game = game;
			this.heuristicComparator = heuristicComparator;
			this.isWinImmediatelyReturned = isWinImmediatelyReturned;
			this.useSymmetries = useSymmetries;
			this.boardSet = boardSet;
//		    gameBoard = game.getBoard();
		}
		
		public void createNewArrayForUniqueMoves(int maxNumChildren) {
			uniqueMoves = new Move[maxNumChildren];	
		}
		
		/* All the steps of generateMoves is located in the same method so we can
		 * immediately return upon seeing a winning move. This makes it easier for 
		 * optimizations.
		 * (non-Javadoc)
		 * @see move.AbstractMoveGenerator#generateMoves()
		 */
		@Override
		public MoveGeneratorResults generateMoves(Game game) {
			Move currMove;
			GameValue currGameState; 
			boardSet.reset();
			
			final Board originalBoard = game.getBoard();
		
			//Stage -1: Create a new array each time so this can be used with alpha beta
			//TODO: I can probably just have a bunch of arrays at different levels so I don't need to recreate them
			createNewArrayForUniqueMoves(game.getMaxNumChildren());
			
			//Stage 0: Initialize the moves array so boards don't need to be recreated;
			numMoves = 0;
		    for (int index = 0; index < originalBoard.getNumberOfSpotsOnBoard(); index++) {
		    	if (originalBoard.getPieceAt(index) != Piece.BLANK) { 
		    		continue; 
		    	}
		    	
		    	if (isImmediateTerminalPossible(originalBoard)) {
		    		currMove = MoveFactory.createMove(index, 0, false, GameValueFactory.getUndetermined());
		    		
		    		final Game gameAfterDoMove = game.doMove(currMove);
		    		final Board boardAfterDoMove = gameAfterDoMove.getBoard();
		    		currGameState = gameAfterDoMove.getGameState().oppositeGameValue();
		    		// TODO: OPTIMIZE: could move the following into the other functions to save computation time, but is it worth it?
		    		currMove.setResultingGameValue(currGameState);
	    			if (currGameState.isWin()) {
	    				if (isWinImmediatelyReturned) {
		    				uniqueMoves[0] = currMove;
		    				numMoves = 1; // isn't necessarily needed, but it's here for clarity
		    				return createResults();
	    				} else if (!boardSet.contains(boardAfterDoMove, useSymmetries)) { 
	    					boardSet.add(boardAfterDoMove);
			    			uniqueMoves[numMoves] = currMove;
			    			numMoves += 1;
	    				}
	    				continue; //Keep this outside of the loop because we want to skip over the rest
	    				
	    			//A Draw could happen if the game is FULLY filled, so we have the following exception
	    			// TODO: Remove the exception below?
	    			} else if (currGameState.isLoss()) {
		    			throw new RuntimeException("This should never get hit");
	    			}
		    	}
		    	
		    	// The rest of the moves
		    	for (int currSubBoard = 1; currSubBoard <= 4; currSubBoard++) {
		    		currMove = MoveFactory.createMove(index, currSubBoard, false, GameValueFactory.getUndetermined());
		    		processMoveWithRotate(game, currMove, boardSet);
		    		if (isImmediateWin() && isWinImmediatelyReturned) { return createResults(); }
		    		
		    		currMove = MoveFactory.createMove(index, currSubBoard, true, GameValueFactory.getUndetermined());
		    		processMoveWithRotate(game, currMove, boardSet);
		    		if (isImmediateWin() && isWinImmediatelyReturned) { return createResults(); }
		    	}
		    }
		
			//Sort the results
		    Arrays.sort(uniqueMoves, 0, numMoves, heuristicComparator);
		    
		    return createResults();
		}

		private boolean isImmediateWin() {
			final Move firstMove = uniqueMoves[0];
			return firstMove != null && firstMove.getResultingGameValue() == GameValueFactory.getWin();
		}

		private void processMoveWithRotate(Game game, Move currMove, final BoardSet boardSet) {
			final Game gameAfterDoMove = game.doMove(currMove);
			final Board boardAfterDoMove = gameAfterDoMove.getBoard();
			
			final GameValue currGameState = gameAfterDoMove.getGameState().oppositeGameValue();
			currMove.setResultingGameValue(currGameState);
			
			if (currGameState.isWin() && isWinImmediatelyReturned) {
				uniqueMoves[0] = currMove;
				numMoves = 1;
			} else if (!boardSet.contains(boardAfterDoMove, useSymmetries)) {
				boardSet.add(boardAfterDoMove);
				uniqueMoves[numMoves] = currMove;
				numMoves += 1;
			}
			
//			game.undoMove(currMove);
//			game.undoMoveLatest();
		}
		
		/* Funky calculation, but it works out
		 * if Dimension is 6, we want at least 8 pieces
		 * if Dimension is 4, we want at least 4 pieces
		 */
		public boolean isImmediateTerminalPossible(Board gameBoard) {
			return gameBoard.getPiecesOnBoard() >= (2 * gameBoard.getDimension() - 4);
		}

		public MoveGeneratorResults createResults() {
			return new MoveGeneratorResults(uniqueMoves, numMoves);
		}

}
