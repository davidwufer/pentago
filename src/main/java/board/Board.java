package board;

import gamevalue.GameValue;
import heuristic.HeuristicCalculator;
import move.Move;


// TODO: Add doc annotations for each method
// TODO: Consider making each board have a symmetry bucket (so it knows what symmetries it has)
// TODO: What if i stored symmetries IN the transposition table?

public interface Board {
	
	/** Returns the width/height of the board
	 * @return
	 */
	public int getDimension();
	

	/** Returns the width of the board
	 * @return
	 */
	public int getWidth();
	
	/** Returns the height of the board
	 * @return
	 */
	public int getHeight();
	
	
	/** Returns the total number of spots on the board
	 * @return
	 */
	public int getNumberOfSpotsOnBoard();
	
	
	/** Returns the number of pieces in a row needed to end the game
	 * @return
	 */
	public int getNInARow();
	
	/** Makes the board invalid for TT Storage //TODO: is this needed in Java?
	 * @return
	 */
	@Deprecated
	public void invalidateBoard();
	
	/** Returns true if the board is invalid
	 * @return
	 */
	@Deprecated
	public boolean isValidBoard();
	
	/** Returns the Piece at index
	 * @param index
	 * @return
	 */
	public Piece getPieceAt(int index);
	
	/** Sets the piece at index to pieceToPlace
	 * @param index
	 * @param pieceToPlace
	 * @return Board
	 */
	public Board placePieceAt(int index, Piece pieceToPlace);

	/**
	 * Does a move!
	 * @param move
	 * @return
	 */
	public Board doMove(Move move);
	
	/**
	 * Undoes a move!
	 * @param move
	 * @return
	 */
	public Board undoMove(Move move);
	
	/** Prints out a board in the nice chess format
	 * @return
	 */
	public String toPrettyString();
	
	/** Only used for testing
	 * @return
	 */
	public BoardArray getBoardArray();
	
	/** Returns the piece for the current player
	 * @return
	 */
	public Piece currPlayerPiece();
	
	/** Returns the piece for the player waiting for the current player
	 * @return
	 */
	@Deprecated
	public Piece waitingPlayerPiece();
	
	/** Returns the number of pieces on the board (or the depth of the search)
	 * @return
	 */
	public int getPiecesOnBoard();
	
	/** Returns the number of pieces on the board (or the depth of the search) after a recalculation.
	 * @return
	 */
	@Deprecated
	public Board getPiecesOnBoardAfterRecalculation();
	
	public Board incrementPiecesOnBoard();
	
	public Board decrementPiecesOnBoard();
	
	/**
	 * Returns the value of the board (WIN, LOSS, DRAW).
	 * If the game is not in a terminal state, this returns the heuristic value of the board.
	 * @return
	 */
	public GameValue getGameState();
	
	//TODO: pull this out into another class, maybe?
	/** Rotates the subBoard {0..4} 90 degrees clockwise or counter-clockwise
	 * @param subBoard
	 * @param isClockWise
	 */
	public Board rotateSubBoard(int subBoard, boolean isClockWise);
	
	public Board flipOverX();
	public Board flipOverY();
	public Board flipOverTopLeftToBottomRightDiag();
	public Board flipOverBottomLeftToTopRightDiag();
	public Board rotateNinetyDegreesClockwise();
	
	public Board copy();
	
	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();

	/**
	 * Returns the appropriate heuristic calculator for a 4x4 or 6x6 board
	 * @return
	 */
	public HeuristicCalculator getHeuristicCalculator();
	
	public BoardBuilder getBoardBuilder();

}
