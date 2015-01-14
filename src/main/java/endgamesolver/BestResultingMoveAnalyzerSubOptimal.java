package endgamesolver;

import game.Game;
import game.GameFactory;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import java.util.ArrayList;
import java.util.List;

import move.Move;
import move.MoveFactory;
import board.Board;
import board.Piece;

@Deprecated
public class BestResultingMoveAnalyzerSubOptimal extends BestResultingMoveAnalyzer {

	private final static boolean[] BOOLEANS = {true, false};
	
	public BestResultingMoveAnalyzerSubOptimal(RunLengthEncodingEndgameDatabase readDatabase) {
		super(readDatabase);
	}

	@Override
	public GameValue getBestResultingGameValue(Board board) {
		final Game game = GameFactory.createGame(board);
		GameValue bestGameValue = GameValueFactory.getLoss();
		
		Move currMove;
		GameValue currGameState; 
		
	    for (int index = 0; index < board.getNumberOfSpotsOnBoard(); index++) {
	    	
	    	if (board.getPieceAt(index) != Piece.BLANK) { 
	    		continue; 
	    	}
	    	
	    	if (isImmediateTerminalPossible(board)) {
	    		currMove = MoveFactory.createMove(index, 0, false, GameValueFactory.getUndetermined());
	    		
	    		final Board boardAfterDoMove = board.doMove(currMove);
	    		currGameState = getGameState(boardAfterDoMove);
	    		
    			if (currGameState.isWin()) {
    				return GameValueFactory.getWin();
    			}
	    	}
	    }
	    	
//    	for (Board boardWithRotatedSubBoard : generateAllRotations(board)) {
    		
		for (int currSubBoard = 1; currSubBoard <= 4; currSubBoard++) {
			for (boolean bool : BOOLEANS) {
				
				 final Board boardWithRotatedSubBoard = board.rotateSubBoard(currSubBoard, bool);
				 
				 currGameState = boardWithRotatedSubBoard.getGameState();
				 if (currGameState.isWin()) {
					 return GameValueFactory.getWin();
				 } else if (currGameState.isDraw()) {
					 bestGameValue = GameValueFactory.getDraw();
					 continue;
				 }
				 
				 for (int boardWithRotatedSubBoardIndex = 0; boardWithRotatedSubBoardIndex < boardWithRotatedSubBoard.getNumberOfSpotsOnBoard(); boardWithRotatedSubBoardIndex++) {
					 
					 if (boardWithRotatedSubBoard.getPieceAt(boardWithRotatedSubBoardIndex) != Piece.BLANK) { 
						 continue; 
					 }
					 
					 currMove = MoveFactory.createMove(boardWithRotatedSubBoardIndex, 0, false, GameValueFactory.getUndetermined());
					 final Board boardWithRotatedSubBoardAfterDoMove = boardWithRotatedSubBoard.doMove(currMove);
					 final GameValue gameValueForRotatedSubBoard = getGameState(boardWithRotatedSubBoardAfterDoMove);
					 
					 if (gameValueForRotatedSubBoard.isWin()) {
						 return GameValueFactory.getWin();
					 } else {
						 bestGameValue = getBetterGameValue(bestGameValue, gameValueForRotatedSubBoard);
					 }
				 }
			}
		}
    		
//	    }
	
	    return bestGameValue;
	}

	private List<Board> generateAllRotations(Board board) {
//		final Set<Board> boardSet = new HashSet<Board>();
		final int maxBoards = 8;
		final List<Board> boardList = new ArrayList<Board>(maxBoards);
		
		for (int currSubBoard = 1; currSubBoard <= 4; currSubBoard++) {
			boardList.add(board.rotateSubBoard(currSubBoard, true));
			boardList.add(board.rotateSubBoard(currSubBoard, false));
		}
		
		return boardList;
	}

}
