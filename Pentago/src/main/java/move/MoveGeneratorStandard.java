package move;

import game.Game;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import hash.BoardSet;
import hash.BoardSetFactory;

import java.util.Arrays;

import movecomparator.HeuristicComparator;
import board.Board;
import board.Piece;

/**
 * @author David Wu
 *
 */
public class MoveGeneratorStandard extends AbstractMoveGenerator {
	
	private Move[] uniqueMoves;
	private int numMoves;
	
	private Move[] unfilteredMoves;
	private int numUnfilteredMoves;
	
	private final HeuristicComparator heuristicComparator;
	
	public MoveGeneratorStandard(HeuristicComparator heuristicComparator) {
		this.heuristicComparator = heuristicComparator;
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
		int index, currSubBoard;
		GameValue currGameState; 
		final BoardSet boardSet;
		
		unfilteredMoves = new Move[game.getMaxNumChildren()];
		boardSet = BoardSetFactory.createBoardHashSetBasic();
		
		final Board gameBoard = game.getBoard();
	
		// Stage -1: Create a new array each time so this can be used with alpha beta
		createNewArrayForUniqueMoves(game.getMaxNumChildren());
		
		//Stage 0: Initialize the moves array so boards don't need to be recreated;
		
		//Stage 1: Generate all possible moves
	    //Put potential no rotation moves in the front to make the search easier
	    for (index = 0, numUnfilteredMoves = 0; index < gameBoard.getNumberOfSpotsOnBoard(); index++) {
	        for (currSubBoard = 0; currSubBoard <= 4; currSubBoard++) {
				/* OPTIMIZATION: the next conditional */
	        	/* OPTIMIZATION: if there is a win, stick it and return it! */
				if (currSubBoard == 0 && gameBoard.getPiecesOnBoard() < (gameBoard.getNInARow() * 2)) {
					continue;
				}

	            if (gameBoard.getPieceAt(index) == Piece.BLANK) {
	                if (currSubBoard == 0) {
						/* OPTIMIZATION: the next conditional */
						//if (piecesOnBoard >= (NINAROW * 2)) {
	                			//This ordering is weird so we don't have to create multiple objects
	                			unfilteredMoves[numUnfilteredMoves] = MoveFactory.createMove(index, 0, false, GameValueFactory.getUndetermined());
	                			
	                			currMove = unfilteredMoves[numUnfilteredMoves];
	                			
	                			final Game gameAfterDoMove = game.doMove(currMove);
	                			final Board boardAfterDoMove = gameAfterDoMove.getBoard();
	                			currGameState = gameAfterDoMove.getGameState().oppositeGameValue();
	                			//just in case we run into the same hashed value twice
	                			if (currGameState.isTerminalValue()) {
	                				boardSet.add(boardAfterDoMove);
	                			}
	                			
	                			unfilteredMoves[numUnfilteredMoves].setResultingGameValue(currGameState);
	                			
	                			if (currGameState.isTerminalValue()) {
	                				numUnfilteredMoves += 1;
	                				break;
	                			}
						//}
	                } else {
	                    unfilteredMoves[numUnfilteredMoves] = MoveFactory.createMove(index,
	                    													  currSubBoard, 
	                    													          true, 
	                    											GameValueFactory.getUndetermined());
	                    numUnfilteredMoves += 1;
						/* OPTIMIZATION: moveCpy instead of moveCreate */
	                    unfilteredMoves[numUnfilteredMoves] = MoveFactory.createMove(index,
	                    													  currSubBoard, 
	                    													         false, 
	                    													         GameValueFactory.getUndetermined());
	                    numUnfilteredMoves += 1;
	                }   
	            }
	        }
	    }
	    
	    hookAfterStage1(new MoveGeneratorStandardInfo(uniqueMoves, numMoves, unfilteredMoves, numUnfilteredMoves, null));
	
	    //Stage 2: Generate do each move, hash it, and if it is unique, add it to the list of legit children. Also calculates the gameValues for each move!!!
	    
	    //initializeMovesHash();
	    for (index = 0, numMoves = 0; index < numUnfilteredMoves; index++) { //index: unfilteredChildren[], counter: children[]
	    	currMove = unfilteredMoves[index];
	    	
	    	//We already know this move is a winning move, so add it and continue
	    	// OPTIMIZE: when we find a terminal move, we can directly place it into the uniqueMoves array without putting it in the unfilteredChildren first.
	    	//if (GameValue.isTerminalValue(currMove.getResultingGameValue())) {
	    	// I'm using getSubBoard == 0 to reduce the number of conditional checks. If a subBoard in here is == 0, then it must have a terminal value from step 1
	    	if (currMove.getSubBoard() == 0) {
    			uniqueMoves[numMoves] = MoveFactory.createMove(currMove.getIndex(),
															   currMove.getSubBoard(),
															   currMove.getIsClockwise(),
															   currMove.getResultingGameValue());
    			numMoves += 1;
	    	} else {
		    	final Game gameAfterDoMove = game.doMove(currMove);
		    	final Board boardAfterDoMove = gameAfterDoMove.getBoard();
		    	
		    	if (!boardSet.contains(boardAfterDoMove, true)) {
		    		GameValue currGameValue;
		    		
		    		//if in tt {
		    		//  currGameValue = TT value
		    		//} else {
		    		currGameValue = gameAfterDoMove.getGameState().oppositeGameValue();
		    		
	    			boardSet.add(boardAfterDoMove);
	    			uniqueMoves[numMoves] = MoveFactory.createMove(currMove.getIndex(),
	    														   currMove.getSubBoard(),
	    														   currMove.getIsClockwise(),
	    														   currGameValue);
	    			numMoves += 1;
		    	}
		    	
	    	}
	    }
	    hookAfterStage2(new MoveGeneratorStandardInfo(uniqueMoves, numMoves, unfilteredMoves, numUnfilteredMoves, boardSet));
	
	    //Stage 3: calculating heuristicValues is done in gameState
		hookAfterStage3(new MoveGeneratorStandardInfo(uniqueMoves, numMoves, unfilteredMoves, numUnfilteredMoves, boardSet));
	
		//Stage 4: sort by ascending or descending game state values to help in the pruning process.
	    Arrays.sort(uniqueMoves, 0, numMoves, heuristicComparator);
	    hookAfterStage4(new MoveGeneratorStandardInfo(uniqueMoves, numMoves, unfilteredMoves, numUnfilteredMoves, boardSet));
	    
	    return createResults();
	}
	
	public MoveGeneratorResults createResults() {
		return new MoveGeneratorResults(uniqueMoves, numMoves);
	}
	
	/* Override these in tests to find out more information about the stages 
	 * Be sure to call "super" in the subclasses and comment these out when running
	 * the final version. */
	public void hookAfterStage1(MoveGeneratorStandardInfo info) {
		System.out.println("hookAfterStage1:\n");
	}
	
	public void hookAfterStage2(MoveGeneratorStandardInfo info) {
		System.out.println("hookAfterStage2:\n");
	}
	
	public void hookAfterStage3(MoveGeneratorStandardInfo info) {
		System.out.println("hookAfterStage3:\n");
	}
	
	public void hookAfterStage4(MoveGeneratorStandardInfo info) {
		System.out.println("hookAfterStage4:\n");
	}
	
	/* Used to provide information for the hooks */
	public class MoveGeneratorStandardInfo {
		private final Move[] children;
		private final int numChildren;
		
		private final Move[] unfilteredChildren;
		private final int numUnfilteredChildren;
		
		private final BoardSet boardSet;
		
		public MoveGeneratorStandardInfo(Move[] children, 
										 int numChildren,
										 Move[] unfilteredChildren,
										 int numUnfilteredChildren,
										 BoardSet boardSet) {
			this.children = children;
			this.numChildren = numChildren;
			this.unfilteredChildren = unfilteredChildren;
			this.numUnfilteredChildren = numUnfilteredChildren;
			this.boardSet = boardSet;
		}
		
		public Move[] getChildren() {
			return children;
		}
		
		public int getNumChildren() {
			return numChildren;
		}
		
		public Move[] getUnfilteredChildren() {
			return unfilteredChildren;
		}
		
		public int getNumUnfilteredChildren() {
			return numUnfilteredChildren;
		}

		public BoardSet getBoardSet() {
			return boardSet;
		}
	}
}	 	
