package move;

import game.Game;

/**
 * 
 * @author David Wu
 *
 */
public interface MoveGenerator {
	public MoveGeneratorResults generateMoves(Game game);
}
