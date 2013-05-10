package search.alphabeta;

import gamevalue.GameValue;

public interface AlphaBetaSearch {
	public GameValue runSearch(GameValue alpha, GameValue beta);
	
	public long getNumTerminalsReached();
	public long getNumExpandedNodes();
}
