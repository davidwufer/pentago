package gamestatecalculator;

import gamevalue.GameValue;
import board.Board;

public interface GameStateCalculator {
	public GameValue calculateGameState(Board board);
}
