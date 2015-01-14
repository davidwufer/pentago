package solver;

import game.Game;
import game.GameFactory;
import gamevalue.GameValue;
import hash.BoardSet;
import hash.BoardSetFactory;
import move.MoveGenerator;
import move.MoveGeneratorFactory;
import movecomparator.HeuristicComparator;
import movecomparator.HeuristicComparatorFactory;
import search.pns.ProofNumberSearch;
import search.pns.transposition.PnsHashTT;
import search.pns.transposition.PnsTT;
import board.Board;

public class DfPnSolver extends Solver {

	private final ProofNumberSearch search; 

	public DfPnSolver(Board board) {
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
		final HeuristicComparator heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, boardSet);
		final PnsTT tt = new PnsHashTT();
		final Game game = GameFactory.createGame(board);
		
		this.search = new ProofNumberSearch(game, moveGenerator, tt);
	}
	
	@Override
	public GameValue solve() {
		return search.search();
	}

	@Override
	public long getNumExpandedNodes() {
		return search.getNumExpandedNodes();
	}

	@Override
	public long getNumTerminalsReached() {
		return search.getNumTerminalsReached();
	}

}
