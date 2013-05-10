package board;

import gamestatecalculator.GameStateCalculator;
import gamestatecalculator.GameStateCalculatorImpl;
import gamevalue.GameValue;
import move.Move;

public abstract class AbstractBoard implements Board {
	/* TODO: Convert to shorts? store this information somewhere else? probably the latter (to store only 1 static one per class) */
	protected final BoardArray boardArray;
	protected final int piecesOnBoard;
	private GameValue gameState;
	
	//TODO: Make this a builder if worried about thread-safety
	public AbstractBoard(String initialBoardConfig) {
		this.boardArray = getBoardArrayFromBoardConfig(initialBoardConfig);
		this.piecesOnBoard = getPiecesFromBoardConfig(initialBoardConfig);
	}

	protected AbstractBoard(BoardArray boardArray, int piecesOnBoard) {
		this.boardArray = BoardArray.cloneBoardArray(boardArray);
		this.piecesOnBoard = piecesOnBoard;
	}
	
	@Override
	public Piece currPlayerPiece() {
		final int piecesOnBoard = getPiecesOnBoard();
		return (piecesOnBoard % 2 == 0) ? Piece.X : Piece.O; 
	}
	
	@Override
	public boolean equals(Object obj) {
		final Board board = (Board) obj;
		return getBoardArray().equals(board.getBoardArray());
	}
	
	@Override
	public Board flipOverX() {
		final int dim = getDimension();
		final int numberOfSpotsOnBoard = getNumberOfSpotsOnBoard();
		return getBoardBuilder().
				setBoardArray(boardArray.flipOverX(dim, numberOfSpotsOnBoard)).
				setPiecesOnBoard(piecesOnBoard).
				build();
	}
	
	@Override
	public Board flipOverY() {
		final int dim = getDimension();
		final int numberOfSpotsOnBoard = getNumberOfSpotsOnBoard();
		return getBoardBuilder().
				setBoardArray(boardArray.flipOverY(dim, numberOfSpotsOnBoard)).
				setPiecesOnBoard(piecesOnBoard).
				build();
	}
	
	@Override
	public Board rotateNinetyDegreesClockwise() {
		return getBoardBuilder().
				setBoardArray(boardArray.rotateNinetyDegreesClockwise(getDimension())).
				setPiecesOnBoard(piecesOnBoard).
				build();
	}
	
	@Override
	public Board flipOverTopLeftToBottomRightDiag() {
		return getBoardBuilder().
				setBoardArray(boardArray.flipOverTopLeftToBottomRightDiag(getDimension(), getNumberOfSpotsOnBoard())).
				setPiecesOnBoard(piecesOnBoard).
				build();
	}
	
	@Override
	public Board flipOverBottomLeftToTopRightDiag() {
		return getBoardBuilder().
				setBoardArray(boardArray.flipOverBottomLeftToTopRightDiag(getDimension(), getNumberOfSpotsOnBoard())).
				setPiecesOnBoard(piecesOnBoard).
				build();
	}
	
	@Override
	public Board rotateSubBoard(int subBoard, boolean isClockWise) {
		return getBoardBuilder().
				setBoardArray(boardArray.rotateSubBoard(getDimension(), subBoard, isClockWise)).
				setPiecesOnBoard(piecesOnBoard).
				build();
	}
	
	@Override
	public BoardArray getBoardArray() {
		return boardArray;
	}
	
	@Override
	public int getDimension() {
		throw new UnsupportedOperationException("This needs to be overriden.");
	}
	
	@Override
	public int getNInARow() {
		throw new UnsupportedOperationException("This needs to be overriden.");
	}
	
	@Override
	public int getNumberOfSpotsOnBoard() {
		throw new UnsupportedOperationException("This needs to be overriden.");
	}
	
	@Override
	public Piece getPieceAt(int index) {
		return boardArray.getPieceAt(index);
	}
	
	@Override
	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}
	
	@Override
	@Deprecated
	public Board getPiecesOnBoardAfterRecalculation() {
		int newPiecesOnBoard = 0;
		for (int index = 0; index < getNumberOfSpotsOnBoard(); index++) {
			if (Piece.BLANK != getPieceAt(index)) {
				newPiecesOnBoard += 1;
			}
		}
		return getBoardBuilder().setBoardArray(boardArray).setPiecesOnBoard(newPiecesOnBoard).build();
	}

	@Override
	public int hashCode() {
		return getBoardArray().hashCode();
	}
	
	@Override
	public Board incrementPiecesOnBoard() {
		return setPiecesOnBoard(piecesOnBoard + 1);
	}

	@Override
	public Board decrementPiecesOnBoard() {
		return setPiecesOnBoard(piecesOnBoard - 1);
	}
	
	@Override
	public BoardBuilder getBoardBuilder() {
		throw new UnsupportedOperationException("You must override this method.");
	}
	
	@Override
	// TODO: OPTIMIZE THIS to use a builder or something!
	public Board doMove(Move move) {
		final Piece pieceToPlace = currPlayerPiece();
		
		if (pieceToPlace == Piece.BLANK) {
			throw new IllegalArgumentException("A Blank piece cannot be used in doMove()");
		}
		
		return
		placePieceAt(move.getIndex(), pieceToPlace).
		rotateSubBoard(move.getSubBoard(), move.getIsClockwise()).
		incrementPiecesOnBoard();
	}
	
	@Override
	public Board undoMove(Move move) {
		return  decrementPiecesOnBoard().
				rotateSubBoard(move.getSubBoard(), !move.getIsClockwise()).
				placePieceAt(move.getIndex(), Piece.BLANK);
	}
	
	
	@Override
	@Deprecated
	public void invalidateBoard() {
		boardArray.invalidateBoard();
	}

	@Override
	@Deprecated
	public boolean isValidBoard() {
		return boardArray.getZeroIndex() != Piece.valueOf(Piece.INVALID);
	}
	
	/* Returns true if a piece was replaced and false otherwise */
	//TODO: what if the piece replaced and piece to place are both NOT blank?
	// This will NOT increment the number of pieces on the board
	@Override
	public Board placePieceAt(int index, Piece pieceToPlace) {
		final BoardArray newBoardArray = boardArray.placePieceAt(index, pieceToPlace);
		return getBoardBuilder().setBoardArray(newBoardArray).setPiecesOnBoard(piecesOnBoard).build();
	}
	
	protected BoardArrayBuilder getBoardArrayBuilder() {
		throw new UnsupportedOperationException("Subclasses need to override this method");
	}
	
	/* Could maybe be optimized as well for the boardArray version */
	public void printBoard() {
		System.out.println(this.toString());
	}
	
	public void printPrettyBoard() {
		System.out.println(this.toPrettyString());
	}
	
//	@Deprecated
//	public void setBoardArrayInternalWithoutPieceNumberChange(byte[] boardArrayInternal) {
//		boardArray.setInternalBoardArray(boardArrayInternal);
//	}
	
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
	        	builder.append('|');
	        }
	        
	        builder.append(' ').append(getPieceAt(index).toChar()).append(' ');

	        if ((index + 1) % DIM == 0) {
	        	builder.append('|').append('\n');
	        }
	    }

	    return builder.append(border).toString();
	}
	
	@Override 
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		Piece currPiece;
		builder.append('[');
		for(int index = 0; index < getNumberOfSpotsOnBoard(); index++) {
			currPiece = getPieceAt(index);
			builder.append(currPiece.toChar());
		}
		builder.append(']');
		return builder.toString();
	}
	
	@Override
	public Piece waitingPlayerPiece() {
		final int piecesOnBoard = getPiecesOnBoard();
		return (piecesOnBoard % 2 == 1) ? Piece.X : Piece.O; 
	}
	
	private int getPiecesFromBoardConfig(String boardConfiguration) {
		final int boardLength = boardConfiguration.length();
		int piecesOnBoard = 0;
		for (int strIndex = 1, boardIndex = 0; strIndex < boardLength - 1; strIndex++, boardIndex++) {
			final Piece pieceToPlace = Piece.fromChar(boardConfiguration.charAt(strIndex));
			placePieceAt(boardIndex, pieceToPlace);
			if (pieceToPlace != Piece.BLANK) {
				piecesOnBoard += 1;
			}
		}
		return piecesOnBoard;
	}

	private BoardArray getBoardArrayFromBoardConfig(String boardConfiguration) {
		final int boardLength = boardConfiguration.length();
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(BoardArray.NEW_BOARD_ARRAY);
		for (int strIndex = 1, boardIndex = 0; strIndex < boardLength - 1; strIndex++, boardIndex++) {
			final Piece pieceToPlace = Piece.fromChar(boardConfiguration.charAt(strIndex));
			builder.placePieceAt(boardIndex, pieceToPlace);
		}
		return builder.build();
	}
	
	private Board setPiecesOnBoard(int newPiecesOnBoard) {
		return getBoardBuilder().setBoardArray(boardArray).setPiecesOnBoard(newPiecesOnBoard).build();
	}
	
	String getPrintBorder() {
		throw new UnsupportedOperationException("You must override this");
	}
	
	@Override
	public GameValue getGameState() {
		if (gameState == null) {
			final GameStateCalculator calculator = new GameStateCalculatorImpl();
			gameState = calculator.calculateGameState(this);
		}
		return gameState;
	}
}
