package heuristic;

import gamevalue.GameValue;
import board.Piece;

public interface HeuristicCalculator {

	public GameValue getHeuristicValue(Piece currentPlayerPiece);
	
	public void addPieceValue(Piece piece, int index);
}
