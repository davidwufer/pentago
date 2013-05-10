package search;

import game.Game;
import move.MoveGenerator;
import search.alphabeta.AlphaBetaIterative;
import search.alphabeta.AlphaBetaRecursive;
import search.alphabeta.AlphaBetaSearch;
import search.alphabeta.transposition.BoardDatabase;

public class SearchFactory {
	public static AlphaBetaSearch createAlphaBetaRecursive(Game game, MoveGenerator moveGenerator, BoardDatabase boardDatabase) {
		return new AlphaBetaRecursive(game, moveGenerator, boardDatabase);
	}

	public static AlphaBetaSearch createAlphaBetaIterative(Game game, MoveGenerator moveGenerator, BoardDatabase boardDatabase) {
		return new AlphaBetaIterative(game, moveGenerator, boardDatabase);
	}
}
