package game;
import gamevalue.GameValue;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorResults;
import board.Board;
import board.Piece;

public class GameFactory {
	public static Game createGame(Board gameBoard) {
		return new PentagoGame(gameBoard);
	}
}

class PentagoGame implements Game {
	private final Board gameBoard;
	private boolean useSymmetries; // ON by default in the constructor
	
	public PentagoGame(Board gameBoard) {
		this.gameBoard = gameBoard;
		setSymmetriesOn();
	}
	
	@Override
	public Board getBoard() {
		return gameBoard;
	}
	
	@Override
	public Game doMove(Move move) {
		return new PentagoGame(gameBoard.doMove(move));
	}
	
	@Override
	public Game undoMove(Move move) {
		final Board boardAfterDecrementPieces = 
				gameBoard.decrementPiecesOnBoard().
				rotateSubBoard(move.getSubBoard(), !move.getIsClockwise()).
				placePieceAt(move.getIndex(), Piece.BLANK);
		final Game gameAfterDecrementPieces = new PentagoGame(boardAfterDecrementPieces);
		return gameAfterDecrementPieces;
	}
	
	@Override
	public GameValue getGameState() {
		return gameBoard.getGameState();
	}

	@Override
	public MoveGeneratorResults generateMoves(MoveGenerator moveGenerator) {
		return moveGenerator.generateMoves(this);
	}

	@Override
	//TODO: COULD be optimized so the +1 for no rotation is added only after piecesOnBoard >= 2 * N_IN_A_ROW??
	public int getMaxNumChildren() {
		 // 4 * 2 for subBoards, + 1 for no rotation case
		final int numSubBoards = (4 * 2) + 1;
	    final int numOpenSpots = gameBoard.getNumberOfSpotsOnBoard() - gameBoard.getPiecesOnBoard();
		return numOpenSpots * numSubBoards;
	}

	@Override
	public boolean isSymmetriesOn() {
		return this.useSymmetries;
	}

	@Override
	public boolean setSymmetriesOff() {
		return (this.useSymmetries = false);
	}

	@Override
	public boolean setSymmetriesOn() {
		return (this.useSymmetries = true);
	}
	
	@Override
	public String toString() {
		return gameBoard.toPrettyString();
	}
}
