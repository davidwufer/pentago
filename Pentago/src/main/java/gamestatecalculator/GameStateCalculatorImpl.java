package gamestatecalculator;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import heuristic.HeuristicCalculator;
import board.Board;
import board.Piece;

public class GameStateCalculatorImpl implements GameStateCalculator {
	
	
	/*TODO: Right now it displays the game state for the current player.
	 * Maybe I can change it so it's for the person who just played (to save recursive calls?)
	 */
	@Override
	public GameValue calculateGameState(Board board) {
		final GameStateMemoizer[] gameStateMemoizer = new GameStateMemoizer[board.getNumberOfSpotsOnBoard()];
		initializeGameStateMemoizer(board.getNumberOfSpotsOnBoard(), gameStateMemoizer);
		
		final int DIM = board.getDimension();
//		final int WIDTH = board.getWidth();
//		final int HEIGHT = board.getHeight();
		final int N_IN_A_ROW = board.getNInARow();
		final int NUMBER_OF_SPOTS_ON_BOARD = board.getNumberOfSpotsOnBoard();
	    int index, leftIndex, upIndex, upLeftIndex, upRightIndex;
	    GameValue gameValue;
	    Piece currPiece;
	    boolean isXWin, isOWin;
	    
		isXWin = isOWin = false;

	    final Piece currPlayer = board.currPlayerPiece();
	    final HeuristicCalculator heuristicCalculator = board.getHeuristicCalculator();

	    for (index = 0; index < board.getNumberOfSpotsOnBoard(); index++) {
	        currPiece = board.getPieceAt(index);
	        
	        heuristicCalculator.addPieceValue(currPiece, index);
 	        
	        gameStateMemoizer[index].left = gameStateMemoizer[index].up = gameStateMemoizer[index].upLeft = gameStateMemoizer[index].upRight = 0;
	        gameStateMemoizer[index].piece = currPiece;
	        
		    leftIndex = index - 1;
		    upIndex = index - DIM;
		    upLeftIndex = upIndex - 1;
		    upRightIndex = upIndex + 1;
		    
		    if (currPiece == Piece.BLANK || index == 0) {
		    	// gameStateMemoizer[index].setLeft(1).setUp(1).setUpLeft(1).setUpRight(1);
			    gameStateMemoizer[index].left = gameStateMemoizer[index].up = gameStateMemoizer[index].upLeft = gameStateMemoizer[index].upRight = 1;
		    } else if (index % DIM == 0) {
//		    	gameStateMemoizer[index].up = gameStateMemoizer[upIndex].getPiece() == currPiece ? gameStateMemoizer[upIndex].up + 1 : 1;
			    if (gameStateMemoizer[upIndex].piece == currPiece) {
				    gameStateMemoizer[index].up = gameStateMemoizer[upIndex].up + 1;
			    } else {
				    gameStateMemoizer[index].up = 1;
			    }
			    
			    if (gameStateMemoizer[upRightIndex].piece == currPiece) {
			        gameStateMemoizer[index].upRight = gameStateMemoizer[upRightIndex].upRight + 1;
			    } else {
			        gameStateMemoizer[index].upRight = 1;
			    }
			    
			    gameStateMemoizer[index].left = gameStateMemoizer[index].upLeft = 1;
		    } else if (index < DIM) {
			    if (gameStateMemoizer[leftIndex].piece == currPiece) {
				    gameStateMemoizer[index].left = gameStateMemoizer[leftIndex].left + 1;
			    } else {
				    gameStateMemoizer[index].left = 1;
			    }
			    gameStateMemoizer[index].upLeft = gameStateMemoizer[index].up = gameStateMemoizer[index].upRight = 1;
			} else if ((index + 1) % DIM == 0) {
			    if (gameStateMemoizer[leftIndex].piece == currPiece) {
				    gameStateMemoizer[index].left = gameStateMemoizer[leftIndex].left + 1;
			    } else {
				    gameStateMemoizer[index].left = 1;
			    }
				
			    if (gameStateMemoizer[upIndex].piece == currPiece) {
				    gameStateMemoizer[index].up = gameStateMemoizer[upIndex].up + 1;
			    } else {
				    gameStateMemoizer[index].up = 1;
			    }
				
			    if (gameStateMemoizer[upLeftIndex].piece == currPiece) {
				    gameStateMemoizer[index].upLeft = gameStateMemoizer[upLeftIndex].upLeft + 1;
			    } else {
				    gameStateMemoizer[index].upLeft = 1;
			    }
			
			    gameStateMemoizer[index].upRight = 1;
		    } else {
			    if (gameStateMemoizer[leftIndex].piece == currPiece) {
				    gameStateMemoizer[index].left = gameStateMemoizer[leftIndex].left + 1;
			    } else {
				    gameStateMemoizer[index].left = 1;
			    }
				
			    if (gameStateMemoizer[upIndex].piece == currPiece) {
				    gameStateMemoizer[index].up = gameStateMemoizer[upIndex].up + 1;
			    } else {
				    gameStateMemoizer[index].up = 1;
			    }
				
			    if (gameStateMemoizer[upLeftIndex].piece == currPiece) {
				    gameStateMemoizer[index].upLeft = gameStateMemoizer[upLeftIndex].upLeft + 1;
			    } else {
				    gameStateMemoizer[index].upLeft = 1;
			    }
			    
			    if (gameStateMemoizer[upRightIndex].piece == currPiece) {
			        gameStateMemoizer[index].upRight = gameStateMemoizer[upRightIndex].upRight + 1;
			    } else {
			        gameStateMemoizer[index].upRight = 1;
			    }
			    
		    }
			
		    if (Math.max(gameStateMemoizer[index].upLeft, Math.max(gameStateMemoizer[index].left, Math.max(gameStateMemoizer[index].up, gameStateMemoizer[index].upRight))) == N_IN_A_ROW) {
			    if (gameStateMemoizer[index].piece == Piece.X) {
				    isXWin = true;
			    } else {
				    isOWin = true;
			    }
		    }
	    }
	    if (isXWin == true && isOWin == true) {
		    gameValue = GameValueFactory.getDraw();
	    } else if (isXWin == true) {
	        gameValue = (currPlayer == Piece.X ? GameValueFactory.getWin() : GameValueFactory.getLoss());
	    } else if (isOWin == true) {
	        gameValue = (currPlayer == Piece.O ? GameValueFactory.getWin() : GameValueFactory.getLoss());
	    } else if (board.getPiecesOnBoard() == NUMBER_OF_SPOTS_ON_BOARD){
		    gameValue = GameValueFactory.getDraw();
	    } else {
	    	gameValue = heuristicCalculator.getHeuristicValue(currPlayer);
	    }
	    return gameValue;
	}
	
	/* TODO: OPTIMIZE: Could initialize this on the fly inside generateMoves() */
	private void initializeGameStateMemoizer(int numberOfSpotsOnBoard, GameStateMemoizer[] gameStateMemoizer) {
		for (int index = 0; index < numberOfSpotsOnBoard; index++) {
			gameStateMemoizer[index] = new GameStateMemoizer();
		}
	}
	
	private class GameStateMemoizer {
		private int up;
		private int	upLeft;
		private int	left;
		private int	upRight; 
		private Piece piece;
		
		public GameStateMemoizer() {
			up = upLeft = left = upRight = 0;
		}
	}
}
