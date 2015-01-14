package heuristic;

import gamevalue.GameValue;
import board.Piece;

public class DoNothingHeuristicCalculator implements HeuristicCalculator {

	@Override
	public GameValue getHeuristicValue(Piece currentPlayerPiece) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addPieceValue(Piece piece, int index) {
		// no op
	}

}
