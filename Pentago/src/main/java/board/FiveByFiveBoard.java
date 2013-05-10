package board;

import heuristic.FiveByFiveHeuristicCalculator;
import heuristic.HeuristicCalculator;

public class FiveByFiveBoard extends AbstractBoard {
	final static private String blankFiveByFiveBoard = "[                         ]";
	final static private String fiveByFiveBorder =    "  +---------------+\n";
	
	final static protected int WIDTH  = 5;
	final static protected int HEIGHT = 5;
	final static protected int N_IN_A_ROW = 4;
	final static public int NUMBER_OF_SPOTS_ON_BOARD = WIDTH * HEIGHT;
	
	protected FiveByFiveBoard () {
		super(blankFiveByFiveBoard);	
	}
	
	protected FiveByFiveBoard(String boardConfiguration) {
		super(boardConfiguration);	
	}
	
	protected FiveByFiveBoard(int piecesOnBoard, BoardArray boardArray) {
		super(boardArray, piecesOnBoard);
	}

	public FiveByFiveBoard(BoardArray boardArray, int piecesOnBoard) {
		super(boardArray, piecesOnBoard);
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public Board copy() {
		return new FiveByFiveBoard(boardArray, getPiecesOnBoard());
	}

	@Override
	public HeuristicCalculator getHeuristicCalculator() {
		return new FiveByFiveHeuristicCalculator();
	}
	
	@Override
	public int getDimension() {
		return WIDTH;
	}
	
	@Override
	public int getNInARow() {
		return N_IN_A_ROW;
	}
	
	@Override
	public int getNumberOfSpotsOnBoard() {
		return NUMBER_OF_SPOTS_ON_BOARD;
	}
	
	@Override
	public String toPrettyString() {
		StringBuilder builder = new StringBuilder();
		final int DIM = getDimension();
		final int NUMBER_OF_SPOTS_ON_BOARD = getNumberOfSpotsOnBoard();
	    final String border = getPrintBorder();
	    
	    for (int index = 0; index < NUMBER_OF_SPOTS_ON_BOARD; index++) {
	        if (index == 0 || index == NUMBER_OF_SPOTS_ON_BOARD/2) {
	        	builder.append(border);
	        }
	        if (index % DIM == 0) {
	        	builder.append(' ').append(DIM - (index / DIM));
	        }
	        if (index % (DIM / 2) == 0) {
//	        	builder.append('|');
	        }
	        
	        builder.append(' ').append(getPieceAt(index).toChar()).append(' ');

	        if ((index + 1) % DIM == 0) {
	        	builder.append('|').append('\n');
	        }
	    }

	    return builder.append(border).toString();
	}
}
