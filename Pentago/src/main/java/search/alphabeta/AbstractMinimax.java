package search.alphabeta;

import game.Game;
import gamevalue.GameValue;
import move.MoveGenerator;
import search.alphabeta.transposition.BoardDatabase;

public abstract class AbstractMinimax implements AlphaBetaSearch {
	protected Game startingGame;
	protected final BoardDatabase table;
	protected final MoveGenerator moveGenerator;
	protected long numTerminalsReached;
	protected long numExpandedNodes;
	protected GameValue solvedValue;
	
	public AbstractMinimax(Game startingGame, MoveGenerator moveGenerator, BoardDatabase table) {
		this.startingGame = startingGame;
		this.table = table;
		this.moveGenerator = moveGenerator;
		numTerminalsReached = 0L;
		numExpandedNodes = 0L;
	}
	
	@Override
	public long getNumTerminalsReached() {
		return numTerminalsReached;
	}
	
	@Override
	public long getNumExpandedNodes() {
		return numExpandedNodes;
	}
	
	public void hookSearch(GameValue alpha, GameValue beta, Game game,
			long numTerminalsReached) {
		//override this
	}
}
