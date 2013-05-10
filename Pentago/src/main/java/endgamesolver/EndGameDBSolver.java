package endgamesolver;

import gamevalue.GameValue;

import java.io.File;

import util.IOUtil;
import board.Board;
import board.BoardFactory;
import board.Piece;

public class EndGameDBSolver {

	private static final int	DIM	= 6;
	private static final int	NUMBER_OF_SPOTS_ON_BOARD	= DIM * DIM;
	private static final int	N_IN_A_ROW	= DIM - 1;
	private static final long	ACTION_INTERVAL	= 1000000;
//	private static final int RUN_LIST_LENGTH_NODES_BEFORE_WRITING = 5825420;
	private static final int RUN_LIST_LENGTH_NODES_BEFORE_WRITING = 1000000;
//	private static final int RUN_LIST_LENGTH_NODES_BEFORE_WRITING = 2;
	
	private final GameStateMemoizer[] gameStateMemoizer;
	private final BestResultingMoveAnalyzer bestResultingMoveAnalyzer;
	private final RunLengthEncodingEndgameDatabase writeDatabase;
	
	private final int depthArgument;
	
	private File dbFile;

	private long positions = 0;
	
	public EndGameDBSolver(int depth, RunLengthEncodingEndgameDatabase readDatabase) {
		this.depthArgument = depth;
		this.bestResultingMoveAnalyzer = new BestResultingMoveAnalyzerStandard(readDatabase);
		this.writeDatabase = new RunLengthEncodingEndgameDatabase(0L);
		gameStateMemoizer = new GameStateMemoizer[NUMBER_OF_SPOTS_ON_BOARD];
		for (int index = 0; index < NUMBER_OF_SPOTS_ON_BOARD; index++) {
			gameStateMemoizer[index] = new GameStateMemoizer();
		}
	}

	void generatePositionsAtDepth(int depth) {
		final Board board = BoardFactory.createSixBySixBoardBlank();
	    int x, o, blank;
	    
	    x     = getNumberOfXOnBoard(depth);
	    o     = getNumberOfOOnBoard(depth);
	    blank = getNumberOfBlanksOnBoard(depth);

	    dbFile = IOUtil.getEndGameDbIO(depthArgument).getNextEndGameDbFile();
	    
	    System.out.println("Starting depth " + depth);
	    
	    generatePermutations(board.getNumberOfSpotsOnBoard() - 1, x, o, blank, board);
	    
//	    writeDatabase.writeToFile(dbFile);
//	    writeDatabase.writeToBinaryFile(dbFile);
	}

	private int getNumberOfBlanksOnBoard(int depth) {
		return NUMBER_OF_SPOTS_ON_BOARD - getNumberOfXOnBoard(depth) - getNumberOfOOnBoard(depth);
	}

	boolean couldBeTerminalPosition() {
		return depthArgument >= 9;
	}

	int getNumberOfXOnBoard(int depth) {
	    return (depth / 2) + (depth % 2);
	}

	int getNumberOfOOnBoard(int depth) {
	    return (depth / 2);
	}

	void generatePermutations(int boardIndex, int x, int o, int blank, Board board) {
		
		// we need the 'is terminal' check because terminals should not be included in the db
	    if (couldBeTerminalPosition() && isTerminalOrImpossibleGameState(board)) {
	        return;
	    }

	    if (x == 0 && o == 0 && blank == 0) {
//	    		System.out.println(BoardConverter.getBoardConverter().boardToLong(board));
//	    	final GameValue gameValue = getBestResultingGameValue(board);
	    	
	    	// write to file
	    	
//	    	writeDatabase.add(board, gameValue);

	    	
	    	positions += 1;
	    	if (positions % ACTION_INTERVAL == 0) {
	    		printNumberOfPositionsSearched();
	    	}
	    	
//	    	if (writeDatabase.getSize() == RUN_LIST_LENGTH_NODES_BEFORE_WRITING + 1) {
//	    		System.out.println("Saving endgame database");
//	    		writeToBinaryDatabaseAndClearAllButLastNode();
//	    	}

	    } else {
	    	// NOTE: THIS ORDER MUST BE MAINTAINED SINCE BLANK < X < O
	    	if (blank > 0) {
	    		generatePermutations(boardIndex - 1, x, o, blank - 1, board);
	    	}
	    	
	    	if (x > 0) {
//	    		generatePermutations(boardIndex - 1, x - 1, o, blank, board.placePieceAt(boardIndex, Piece.X).incrementPiecesOnBoard());
	    		generatePermutations(boardIndex - 1, x - 1, o, blank, placePieceAndIncrementPiecesOnBoard(board, boardIndex, Piece.X));
	    	}

	        if (o > 0) {
//	            generatePermutations(boardIndex - 1, x, o - 1, blank, board.placePieceAt(boardIndex, Piece.O).incrementPiecesOnBoard());
	            generatePermutations(boardIndex - 1, x, o - 1, blank, placePieceAndIncrementPiecesOnBoard(board, boardIndex, Piece.O));
	        }
	    }
	}
	
	private void writeToBinaryDatabaseAndClearAllButLastNode() {
		writeDatabase.writeToBinaryFileAllButLastNode(dbFile);
		writeDatabase.getEncoder().clearAllButLastNode();
		
		// TODO: fix this to have startindex, endingindex, data
//		final long previousLastIndex = writeDatabase.getLastIndex();
//		writeDatabase.resetAndInitializeStartingIndexTo(previousLastIndex + 1);
	}

	private Board placePieceAndIncrementPiecesOnBoard(Board board, int boardIndex, Piece piece) {
		return board.getBoardBuilder().
				setBoardArray(board.placePieceAt(boardIndex, piece).getBoardArray()).
				setPiecesOnBoard(board.getPiecesOnBoard() + 1).
				build();
	}

	public static void main(String[] args) {
	    final int depth;

	    if (args.length < 1) {
	        System.out.println("Usage: ./generate <depth>\n");
	        System.exit(1);
	    }

//	    depth = Integer.parseInt(args[0]);
	    depth = 35;

	    final RunLengthEncodingEndgameDatabase readDatabase = null;
	    final EndGameDBSolver boardGenerator = new EndGameDBSolver(depth, readDatabase);
	    boardGenerator.generatePositionsAtDepth(depth);
	    
	    //writeToFile();
	    boardGenerator.printNumberOfPositionsSearched();
	}

	private void printNumberOfPositionsSearched() {
	    System.out.println("Depth " + depthArgument);
	    System.out.println("Positions: " + positions);
	    System.out.println("EndGameDb Size: " + writeDatabase.getSize());
//	    System.out.println(endGameDb.toString());
	    System.out.println();
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

	public GameValue getBestResultingGameValue(Board board) {
		final GameValue gameValue =  bestResultingMoveAnalyzer.getBestResultingGameValue(board);
		
		if (!gameValue.isTerminalValue()) {
			throw new UnsupportedOperationException();
		}
		
		return gameValue;
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
	
}
