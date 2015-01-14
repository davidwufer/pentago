package board;

import heuristic.HeuristicCalculator;
import heuristic.SixBySixBoardHeuristicCalculator;



public class SixBySixBoard extends AbstractBoard {

	final static private String blankSixBySixBoard   = "[                                    ]";
	final static private String sixBySixBorder   = "  +---------+---------+\n";
	
	final static protected int WIDTH  = 6;
	final static protected int HEIGHT = 6;
	final static protected int N_IN_A_ROW = 5;
	final static public int NUMBER_OF_SPOTS_ON_BOARD = WIDTH * HEIGHT;
	
	protected SixBySixBoard() {
		super(blankSixBySixBoard);	
	}
	
	protected SixBySixBoard(String boardConfiguration) {
		super(boardConfiguration);	
	}
	
	protected SixBySixBoard(int piecesOnBoard, BoardArray boardArray) {
		super(boardArray, piecesOnBoard);
	}

	public SixBySixBoard(BoardArray boardArray, int piecesOnBoard) {
		super(boardArray, piecesOnBoard);
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

	// TODO: Check to see when this is actually getting called. Am I creating too many objects?
	@Override
	public Board copy() {
		return new SixBySixBoard(getPiecesOnBoard(), getBoardArray());
	}
	
	@Deprecated
	public int calculateNumberOfPiecesOnBoard() {
		int piecesOnBoard = 0;
		for (int index = 0; index < NUMBER_OF_SPOTS_ON_BOARD; index++) {
			if (Piece.BLANK != getPieceAt(index)) {
				piecesOnBoard += 1;
			}
		}
		return piecesOnBoard;
	}
	
	@Override
	String getPrintBorder() {
		return sixBySixBorder;
	}
	
	@Override
	public BoardBuilder getBoardBuilder() {
		return new BoardBuilder() {
			@Override
			public Board build() {
				return new SixBySixBoard(getBoardArray(), getPiecesOnBoard());
			}
		};
	}

	@Override
	public HeuristicCalculator getHeuristicCalculator() {
		return new SixBySixBoardHeuristicCalculator();
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}
}
