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
public class MoveGeneratorMetric extends AbstractMoveGenerator {
	
	private Move[] uniqueMovesSoFar;
	private int numUniqueMovesSoFar;
	
	private Move[] tempMoves;
	private int numTempMoves;
	
	BoardSet boardSet;
	
	private final HeuristicComparator heuristicComparator;
	
	public MoveGeneratorMetric(HeuristicComparator heuristicComparator) {
		this.heuristicComparator = heuristicComparator;
	}
	
	public void createNewArrayForUniqueMoves(int maxNumChildren) {
		uniqueMovesSoFar = new Move[maxNumChildren];	
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
		
		final Board gameBoard = game.getBoard();
		
		// Stage -1: Create a new array each time so this can be used with alpha beta
		createNewArrayForUniqueMoves(game.getMaxNumChildren());
		tempMoves = new Move[game.getMaxNumChildren()];
		
		boardSet = BoardSetFactory.createBoardHashSetBasic();
	
		//Stage 0: Make sure this isn't already a terminal state
		currGameState = game.getGameState();
		if (currGameState.isTerminalValue()) {
			throw new RuntimeException("Board is already in a terminal state: " + gameBoard.toString());
		}
		
		//Stage 1: Generate all possible moves
	    for (index = 0, numTempMoves = 0; index < gameBoard.getNumberOfSpotsOnBoard(); index++) {
	    	if (gameBoard.getPieceAt(index) != Piece.BLANK) { continue; }
	    	
	        for (currSubBoard = 0; currSubBoard <= 4; currSubBoard++) {
                if (currSubBoard == 0) {
        			//This ordering is weird so we don't have to create multiple objects
        			tempMoves[numTempMoves] = MoveFactory.createMove(index, 0, false, GameValueFactory.getUndetermined());
        			
        			currMove = tempMoves[numTempMoves];
        			
        			final Game gameAfterDoMove = game.doMove(currMove);
        			currGameState = gameAfterDoMove.getGameState().oppositeGameValue();
        			//just in case we run into the same hashed value twice. This can only happen for a WIN
//        			if (GameValue.isTerminalValue(currGameState)) {
//        				boardSet.add(gameBoard);
//        			}
//        			game.undoMove(currMove);
        			
        			tempMoves[numTempMoves].setResultingGameValue(currGameState);
        			
        			if (currGameState.isWin()) {
        				numTempMoves += 1;
        				break;
        			}
                } else {
                	tempMoves[numTempMoves] = MoveFactory.createMove(index,
            													  currSubBoard, 
            													          true, 
            											GameValueFactory.getUndetermined());
                	numTempMoves += 1;
					/* OPTIMIZATION: moveCpy instead of moveCreate */
                	tempMoves[numTempMoves] = MoveFactory.createMove(index,
            													  currSubBoard, 
            													         false, 
            											GameValueFactory.getUndetermined());
                    numTempMoves += 1;
                }   
            }
	    }
	    saveMoveFiltering(game.getMaxNumChildren());
	    hookAfterStage1(new MoveGeneratorMetricInfo(uniqueMovesSoFar, numUniqueMovesSoFar, boardSet));
	
	    //Stage 2: Remove duplicate moves w/o symmetries
	    removeDuplicates(game, false);
	    saveMoveFiltering(game.getMaxNumChildren());
	    hookAfterStage2(new MoveGeneratorMetricInfo(uniqueMovesSoFar, numUniqueMovesSoFar, boardSet));
	
	    //Stage 3: Remove symmetries
	    boardSet = BoardSetFactory.createBoardHashSetBasic(); // clear out the old entries
	    removeDuplicates(game, true);
	    saveMoveFiltering(game.getMaxNumChildren());
	    hookAfterStage3(new MoveGeneratorMetricInfo(uniqueMovesSoFar, numUniqueMovesSoFar, boardSet));
	    
		//Stage 4: sort by ascending or descending game state values to help in the pruning process.
	    Arrays.sort(uniqueMovesSoFar, 0, numUniqueMovesSoFar, heuristicComparator);
	    hookAfterStage4(new MoveGeneratorMetricInfo(uniqueMovesSoFar, numUniqueMovesSoFar, boardSet));
	    
	    return createResults();
	}
	
	public void removeDuplicates(Game game, boolean useSymmetries) {
		Move currMove;
		int index;
	    for (index = 0, numTempMoves = 0; index < numUniqueMovesSoFar; index++) {
	    	currMove = uniqueMovesSoFar[index];
	    	
	    	final Game gameAfterDoMove = game.doMove(currMove);
	    	final Board board = gameAfterDoMove.getBoard();
	    	if (currMove.getSubBoard() == 0) {
	    		if (!boardSet.contains(board, useSymmetries)) {
	    			boardSet.add(board);
	    			tempMoves[numTempMoves] = MoveFactory.createMove(currMove.getIndex(),
															         currMove.getSubBoard(),
															         currMove.getIsClockwise(),
															         currMove.getResultingGameValue());
	    			numTempMoves += 1;
	    		}
	    	} else {
		    	if (!boardSet.contains(board, useSymmetries)) {
		    		GameValue currGameValue;
		    		
		    		//if in tt {
		    		//  currGameValue = TT value
		    		//} else {
		    		currGameValue = gameAfterDoMove.getGameState().oppositeGameValue();
		    		
	    			boardSet.add(board);
	    			tempMoves[numTempMoves] = MoveFactory.createMove(currMove.getIndex(),
    														         currMove.getSubBoard(),
    														         currMove.getIsClockwise(),
    														         currGameValue);
	    			numTempMoves += 1;
		    	}
	    	}
//	    	game.undoMove(currMove);
	    }
	}
	
	public void saveMoveFiltering(int maxNumChildren) {
		uniqueMovesSoFar = tempMoves;
		numUniqueMovesSoFar = numTempMoves;
		tempMoves = new Move[maxNumChildren];
		numTempMoves = 0;
	}
	
	public MoveGeneratorResults createResults() {
		return new MoveGeneratorResults(uniqueMovesSoFar, numUniqueMovesSoFar);
	}
	
	/* Override these in tests to find out more information about the stages 
	 * Be sure to call "super" in the subclasses and comment these out when running
	 * the final version. */
	public void hookAfterStage1(MoveGeneratorMetricInfo info) {
//		System.out.println("hookAfterStage1:\n");
	}
	
	public void hookAfterStage2(MoveGeneratorMetricInfo info) {
//		System.out.println("hookAfterStage2:\n");
	}
	
	public void hookAfterStage3(MoveGeneratorMetricInfo info) {
//		System.out.println("hookAfterStage3:\n");
	}
	
	public void hookAfterStage4(MoveGeneratorMetricInfo info) {
//		System.out.println("hookAfterStage4:\n");
	}
	
	/* Used to provide information for the hooks */
	public class MoveGeneratorMetricInfo {
		private final Move[] children;
		private final int numChildren;
		
		private final BoardSet boardSet;
		
		public MoveGeneratorMetricInfo(Move[] children, int numChildren, BoardSet boardSet) {
			this.children = children;
			this.numChildren = numChildren;
			this.boardSet = boardSet;
		}
		
		public Move[] getChildren() {
			return children;
		}
		
		public int getNumChildren() {
			return numChildren;
		}
		
		public BoardSet getBoardSet() {
			return boardSet;
		}
	}
}	 	
