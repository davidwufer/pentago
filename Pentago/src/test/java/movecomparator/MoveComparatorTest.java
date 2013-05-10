package movecomparator;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveFactory;

public class MoveComparatorTest extends TestCase {

	private final GameValue BETWEEN_WIN_AND_DRAW = GameValueFactory.createGameValue((GameValueFactory.getWin().getValue() + GameValueFactory.getDraw().getValue()) / 2);
	private final GameValue BETWEEN_DRAW_AND_LOSS = GameValueFactory.createGameValue((GameValueFactory.getDraw().getValue() + GameValueFactory.getLoss().getValue()) / 2);
	Move m1, m2;
	
	
	public void testWinVsWin() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		assertTrue("Win should be equal to a Win", m1.compareTo(m2) == 0);
	}
	
	public void testLossVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("Loss should be equal to a Loss", m1.compareTo(m2) == 0);
	}
	
	public void testDrawVsDraw() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		assertTrue("Draw should be equal to a Draw", m1.compareTo(m2) == 0);
	}
	
	public void testUndeterminedVsUndetermined() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getUndetermined());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getUndetermined());
		assertTrue("Undetermined should be equal to Undetermined", m1.compareTo(m2) == 0);
	}	
	
	public void testWinVsDraw() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		assertTrue("WIN > DRAW", m1.compareTo(m2) > 0);
	}
	
	public void testWinVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("WIN > LOSS", m1.compareTo(m2) > 0);
	}
	
	public void testWinVsUndetermined() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getUndetermined());
		assertTrue("WIN > UNDETERMINED", m1.compareTo(m2) > 0);
	}
	
	public void testUndeterminedVsDraw() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getUndetermined());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		assertTrue("UNDETERMINED > DRAW", m1.compareTo(m2) > 0);
	}
	
	public void testUndeterminedVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getUndetermined());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("UNDETERMINED > LOSS", m1.compareTo(m2) > 0);
	}
	
	public void testDrawVsLoss() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getDraw());
		m2 = MoveFactory.createMove(1, 1, true, GameValueFactory.getLoss());
		assertTrue("DRAW > LOSS", m1.compareTo(m2) > 0);
	}
	
	public void testWinVsPositiveHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_WIN_AND_DRAW);
		assertTrue("WIN > +HEURISTIC", m1.compareTo(m2) > 0);	
	}
	
	public void testWinVsNegativeHeuristic() {
		m1 = MoveFactory.createMove(1, 1, true, GameValueFactory.getWin());
		m2 = MoveFactory.createMove(1, 1, true, BETWEEN_DRAW_AND_LOSS);
		assertTrue("WIN > -HEURISTIC", m1.compareTo(m2) > 0);	
	}
	
	public void testPositiveHeuristicVsDraw() {
		
	}
	
	
}
