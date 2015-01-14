package endgamesolver;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import move.Move;
import move.MoveFactory;
import board.Board;
import board.Piece;

public class BestResultingMoveAnalyzerStandard extends BestResultingMoveAnalyzer {
	
	private final static boolean[] BOOLEANS = {true, false};
	
	public BestResultingMoveAnalyzerStandard(RunLengthEncodingEndgameDatabase readDatabase) {
		super(readDatabase);
	}

	@Override
	public GameValue getBestResultingGameValue(Board board) {
		GameValue bestGameValue = GameValueFactory.getLoss();
		
		Move currMove;
		GameValue currGameState; 
		
	    for (int index = 0; index < board.getNumberOfSpotsOnBoard(); index++) {
	    	if (board.getPieceAt(index) != Piece.BLANK) { 
	    		continue; 
	    	}
	    	
	    	if (isImmediateTerminalPossible(board)) {
	    		currMove = MoveFactory.createMove(index, 0, false, GameValueFactory.getUndetermined());
	    		
				currGameState = getGameState(board.doMove(currMove));
	    		
    			if (currGameState.isWin()) {
    				return GameValueFactory.getWin();
    			}
	    	}
	    	
	    	// The rest of the moves
	    	for (int currSubBoard = 1; currSubBoard <= 4; currSubBoard++) {
	    		for (boolean isClockwise : BOOLEANS) {
	    			currMove = MoveFactory.createMove(index, currSubBoard, isClockwise, GameValueFactory.getUndetermined());
	    			final GameValue gameValue = getGameState(board.doMove(currMove));
	    			
	    			if (gameValue.isWin()) { 
	    				return gameValue;
	    			} else {
	    				bestGameValue = getBetterGameValue(bestGameValue, gameValue); 
	    			}
	    		}
	    	}
	    }
	    return bestGameValue;
	}
	
}
