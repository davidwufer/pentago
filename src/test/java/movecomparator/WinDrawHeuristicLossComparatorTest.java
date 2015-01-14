package movecomparator;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveFactory;

public class WinDrawHeuristicLossComparatorTest extends TestCase {

	private final GameValue BETWEEN_WIN_AND_DRAW = GameValueFactory.createGameValue((GameValueFactory.getWin().getValue() + GameValueFactory.getDraw().getValue()) / 2);
	private final GameValue BETWEEN_DRAW_AND_LOSS = GameValueFactory.createGameValue((GameValueFactory.getDraw().getValue() + GameValueFactory.getLoss().getValue()) / 2);
	Move m1, m2;
	
	private HeuristicComparator comparator;
	
	@Override
	public void setUp() {
		comparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
	}
	
	
	public void testWinVsWin() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		assertTrue("Win should be equal to a Win", comparator.compare(m1, m2) == 0);
		assertTrue("Win should be equal to a Win", comparator.compare(m2, m1) == 0);
	}
	
	public void testLossVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("Loss should be equal to a Loss", comparator.compare(m1, m2) == 0);
		assertTrue("Loss should be equal to a Loss", comparator.compare(m2, m1) == 0);
	}
	
	public void testDrawVsDraw() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		assertTrue("Draw should be equal to a Draw", comparator.compare(m1, m2) == 0);
		assertTrue("Draw should be equal to a Draw", comparator.compare(m2, m1) == 0);
	}
	
	public void testWinVsDraw() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		assertTrue("WIN > DRAW", comparator.compare(m1, m2) > 0);
		assertTrue("DRAW < WIN", comparator.compare(m2, m1) < 0);
	}
	
	public void testWinVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("WIN > LOSS", comparator.compare(m1, m2) > 0);
		assertTrue("LOSS < WIN", comparator.compare(m2, m1) < 0);
	}
	
	public void testDrawVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("DRAW > LOSS", comparator.compare(m1, m2) > 0);
		assertTrue("LOSS < DRAW", comparator.compare(m2, m1) < 0);
	}
	
	public void testWinVsPositiveHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_WIN_AND_DRAW);
		assertTrue("WIN > +HEURISTIC", comparator.compare(m1, m2) > 0);
		assertTrue("+HEURISTIC < WIN", comparator.compare(m2, m1) < 0);
	}
	
	public void testWinVsNegativeHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		assertTrue("WIN > -HEURISTIC", comparator.compare(m1, m2) > 0);
		assertTrue("-HEURISTIC < WIN", comparator.compare(m2, m1) < 0);
	}
	
	public void testDrawVsPositiveHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_WIN_AND_DRAW);
		assertTrue("DRAW > -HEURISTIC", comparator.compare(m1, m2) > 0);
		assertTrue("-HEURISTIC < DRAW", comparator.compare(m2, m1) < 0);
	}
	
	public void testDrawVsNegativeHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		assertTrue("DRAW > -HEURISTIC", comparator.compare(m1, m2) > 0);
		assertTrue("-HEURISTIC < DRAW", comparator.compare(m2, m1) < 0);
	}
	
	public void testLossVsPositiveHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_WIN_AND_DRAW);
		assertTrue("LOSS < +HEURISTIC", comparator.compare(m1, m2) < 0);
		assertTrue("+HEURISTIC > LOSS", comparator.compare(m2, m1) > 0);
	}
	
	public void testLossVsNegativeHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		assertTrue("LOSS < -HEURISTIC", comparator.compare(m1, m2) < 0);
		assertTrue("-HEURISTIC > LOSS", comparator.compare(m2, m1) > 0);
	}
	
	public void testPositiveHeuristicVsNegativeHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, BETWEEN_WIN_AND_DRAW);
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		assertTrue("+HEURISTIC > -HEURISTIC", comparator.compare(m1, m2) > 0);
		assertTrue("-HEURISTIC < +HEURISTIC", comparator.compare(m2, m1) < 0);
	}
	
	public void testNegativeHeuristicVsPositiveHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_WIN_AND_DRAW);
		assertTrue("-HEURISTIC < +HEURISTIC", comparator.compare(m1, m2) < 0);
		assertTrue("+HEURISTIC > -HEURISTIC", comparator.compare(m2, m1) > 0);
	}
	
	public void testSameHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		assertTrue("HEURISTIC == HEURISTIC", comparator.compare(m1, m2) == 0);
		assertTrue("HEURISTIC == HEURISTIC", comparator.compare(m2, m1) == 0);
	}	
}
