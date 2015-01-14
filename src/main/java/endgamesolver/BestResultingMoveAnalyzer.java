package endgamesolver;

import gamevalue.GameValue;
import board.Board;

public abstract class BestResultingMoveAnalyzer {
	
	private final RunLengthEncodingEndgameDatabase readDatabase;

	public BestResultingMoveAnalyzer(RunLengthEncodingEndgameDatabase readDatabase) {
		this.readDatabase = readDatabase;
	}
	
	public boolean isImmediateTerminalPossible(Board gameBoard) {
		return gameBoard.getPiecesOnBoard() >= (2 * gameBoard.getDimension() - 4);
	}
	
	public GameValue getGameState(Board board) {
		if (board.getPiecesOnBoard() == board.getNumberOfSpotsOnBoard()) {
			return board.getGameState().oppositeGameValue();
		} else {
			return readDatabase.contains(board, false).getResult().oppositeGameValue();
		}
	}
	
	public abstract GameValue getBestResultingGameValue(Board board);
	
	public GameValue getBetterGameValue(GameValue gv1, GameValue gv2) {
		return (gv1.getValue() > gv2.getValue()) ? gv1 : gv2;
	}
	
}