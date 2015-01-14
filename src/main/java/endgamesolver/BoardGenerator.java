package endgamesolver;

import hash.BoardHashSetWithRemoval;
import hash.BoardSet;
import board.Board;
import board.BoardFactory;
import board.Piece;

public class BoardGenerator {

	private static final int	DIM	= 6;
	private static final int	NUMBER_OF_SPOTS_ON_BOARD	= DIM * DIM;
	private static final int	N_IN_A_ROW	= DIM - 1;
	private static final long	ACTION_INTERVAL	= 100000;
	
//	private final int boards_index = 0; 
//	private final int part = 0;
	private final int depth_argument;

	private long positions = 0;
	private long duplicates = 0;
	
	private long hashSizeIfBoardsWereNotRemoved = 0;
	
	private final GameStateMemoizer[] gameStateMemoizer;
	
	private final BoardSet boardHashSet;
	
	public BoardGenerator(int depth) {
		this.depth_argument = depth;
		gameStateMemoizer = new GameStateMemoizer[NUMBER_OF_SPOTS_ON_BOARD];
		for (int index = 0; index < NUMBER_OF_SPOTS_ON_BOARD; index++) {
			gameStateMemoizer[index] = new GameStateMemoizer();
		}
//		boardHashSet = BoardSetFactory.createBoardHashSetBasic();
		boardHashSet = new BoardHashSetWithRemoval();
	}

	void generatePositionsAtDepth(int depth) {
		final Board board = BoardFactory.createSixBySixBoardBlank();
	    int x, o, blank;
	    
	    x     = getNumberOfXOnBoard(depth);
	    o     = getNumberOfOOnBoard(depth);
	    blank = getNumberOfBlanksOnBoard(depth);

	    generatePermutations(board.getNumberOfSpotsOnBoard() - 1, x, o, blank, board);
	}

	private int getNumberOfBlanksOnBoard(int depth) {
		return NUMBER_OF_SPOTS_ON_BOARD - getNumberOfXOnBoard(depth) - getNumberOfOOnBoard(depth);
	}

	boolean couldBeTerminalPosition() {
		return depth_argument >= 9;
	}

	int getNumberOfXOnBoard(int depth) {
	    return (depth / 2) + (depth % 2);
	}

	int getNumberOfOOnBoard(int depth) {
	    return (depth / 2);
	}

	void generatePermutations(int board_index, int x, int o, int blank, Board board) {
		
	    if (couldBeTerminalPosition() && isTerminalOrImpossibleGameState(board)) {
	        return;
	    }

	    if (x == 0 && o == 0 && blank == 0) {
	    	
	    	System.out.println(BoardConverter.getBoardConverter().boardToLong(board));
	    	
			if (boardHashSet.contains(board, true)) {
				duplicates += 1;
				return;
			} else {
				boardHashSet.add(board);
				hashSizeIfBoardsWereNotRemoved += 1;
			}

	        positions += 1;
	        if (positions % ACTION_INTERVAL == 0) {
	            printNumberOfPositionsSearched();
	        }

	    } else {
	    	if (blank > 0) {
//	        	We don't need to put down a piece since it's already all blank
	    		generatePermutations(board_index - 1, x, o, blank - 1, board);
	    	}
	    	
	    	if (x > 0) {
	    		generatePermutations(board_index - 1, x - 1, o, blank, board.placePieceAt(board_index, Piece.X));
	    	}

	        if (o > 0) {
	            generatePermutations(board_index - 1, x, o - 1, blank, board.placePieceAt(board_index, Piece.O));
	        }
	    }
	}

	public static void main(String[] args) {
	    final int depth;

	    if (args.length < 1) {
	        System.out.println("Usage: ./generate <depth>\n");
	        System.exit(1);
	    }

	    depth = Integer.parseInt(args[0]);

	    final BoardGenerator boardGenerator = new BoardGenerator(depth);
	    boardGenerator.generatePositionsAtDepth(depth);
	    
	    //writeToFile();
	    boardGenerator.printNumberOfPositionsSearched();
	}

	private void printNumberOfPositionsSearched() {
		System.out.println("Hash Size: " + boardHashSet.size());
		System.out.println("Hash Size if Boards were not removed: " + hashSizeIfBoardsWereNotRemoved);
		System.out.println("Amount removed from hash: " + (hashSizeIfBoardsWereNotRemoved - boardHashSet.size()));
	    System.out.println("Positions: " + positions + ", Duplicates: " + duplicates + ", Total: " + (positions + duplicates));
	    System.out.println();
	}

	boolean isTerminalOrImpossibleGameState(Board board) {
	    int index, leftIndex, upIndex, upLeftIndex, upRightIndex;
	    
	    for (index = 0; index < board.getNumberOfSpotsOnBoard(); index++) {
	    	final Piece currPiece = board.getPieceAt(index);
	    	
	        gameStateMemoizer[index].left = gameStateMemoizer[index].up = gameStateMemoizer[index].upLeft = gameStateMemoizer[index].upRight = 0;
	        gameStateMemoizer[index].piece = currPiece;
	        
		    leftIndex = index - 1;
		    upIndex = index - DIM;
		    upLeftIndex = upIndex - 1;
		    upRightIndex = upIndex + 1;
		    
		    if (currPiece == Piece.BLANK || index == 0) {
			    gameStateMemoizer[index].left = gameStateMemoizer[index].up = gameStateMemoizer[index].upLeft = gameStateMemoizer[index].upRight = 1;
		    } else if (index % DIM == 0) {
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
			    return true;
		    }
	    }
	    return false;
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
