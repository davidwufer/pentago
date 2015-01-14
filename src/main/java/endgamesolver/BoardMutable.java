package endgamesolver;

import gamevalue.GameValue;
import heuristic.DoNothingHeuristicCalculator;
import heuristic.HeuristicCalculator;
import move.Move;
import board.Board;
import board.BoardArray;
import board.BoardBuilder;
import board.Piece;

/**
 * THIS IS NOT IMMUTABLE!
 * @author David Wu
 *
 */
public class BoardMutable implements Board {
	
	private final Piece[] boardInternal;
	private int piecesOnBoard;
	
	private final HeuristicCalculator heuristicCalculator;

	public BoardMutable() {
		this.boardInternal = new Piece[getNumberOfSpotsOnBoard()];
		piecesOnBoard = 0;
		this.heuristicCalculator = new DoNothingHeuristicCalculator();
	}
	
	@Override
	public int getDimension() {
		return 6;
	}

	@Override
	public int getNumberOfSpotsOnBoard() {
		return 36;
	}

	@Override
	public int getNInARow() {
		return 5;
	}

	@Override
	public Piece getPieceAt(int index) {
		return boardInternal[index];
	}

	@Override
	public Board placePieceAt(int index, Piece pieceToPlace) {
		boardInternal[index] = pieceToPlace;
		return this;
	}

	@Override
	public Board doMove(Move move) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board undoMove(Move move) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toPrettyString() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BoardArray getBoardArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Piece currPlayerPiece() {
		return piecesOnBoard % 2 == 0 ? Piece.X : Piece.O;
	}

	@Override
	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}

	@Override
	public Board incrementPiecesOnBoard() {
		piecesOnBoard++;
		return this;
	}

	@Override
	public Board decrementPiecesOnBoard() {
		piecesOnBoard--;
		return this;
	}

	@Override
	public GameValue getGameState() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board rotateSubBoard(int subBoard, boolean isClockWise) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board flipOverX() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board flipOverY() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board flipOverTopLeftToBottomRightDiag() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board flipOverBottomLeftToTopRightDiag() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board rotateNinetyDegreesClockwise() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Board copy() {
		throw new UnsupportedOperationException();
	}

	@Override
	public HeuristicCalculator getHeuristicCalculator() {
		return heuristicCalculator;
	}

	@Override
	public BoardBuilder getBoardBuilder() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public void invalidateBoard() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public boolean isValidBoard() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public Piece waitingPlayerPiece() {
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public Board getPiecesOnBoardAfterRecalculation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		return 6;
	}

}
