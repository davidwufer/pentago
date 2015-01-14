package heuristic;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import board.Piece;

public class FourByFourBoardHeuristicCalculator extends AbstractHeuristicCalculator {

	@Override
	public GameValue getHeuristicValue(Piece currrentPlayerPiece) {
		return GameValueFactory.getUndetermined();
	}

	@Override
	public void addPieceValue(Piece piece, int index) {
		// do nothing
	}

}
