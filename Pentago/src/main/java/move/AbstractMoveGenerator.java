package move;

import game.Game;

public abstract class AbstractMoveGenerator implements MoveGenerator {
	@Override
	public abstract MoveGeneratorResults generateMoves(Game game);
}
