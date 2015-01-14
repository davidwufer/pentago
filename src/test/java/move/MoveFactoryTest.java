package move;

import gamevalue.GameValueFactory;
import junit.framework.TestCase;

public class MoveFactoryTest extends TestCase {
	
	public void testCreatingMove() {
		MoveFactory.createMove(0, 0, true, GameValueFactory.getDraw());
	}
	
	public void testVariablesInitializedCorrectly() {
		Move m = MoveFactory.createMove(5, 2, true, GameValueFactory.getDraw());
		assertEquals("Index should be initialized correctly",     5, m.getIndex());
		assertEquals("Subboard should be initialized correctly",  2, m.getSubBoard());
		assertTrue("isClockwise should be initialized correctly", m.getIsClockwise());
		assertEquals("Resulting Game Value should be initialized correctly", GameValueFactory.getDraw(), m.getResultingGameValue());
	}
	
	public void testToString() {
		Move m1 = MoveFactory.createMove(20, 4, false, GameValueFactory.getDraw());
		assertEquals("Move to String should be correct",
				"index: 20, subBoard: 4, rotate: counter-clockwise, resultingGameValue: DRAW",
				m1.toString());
		
		
		Move m2 = MoveFactory.createMove(10, 0, true, GameValueFactory.createGameValue(10));
		assertEquals("Move to String should be correct",
				"index: 10, subBoard: 0, rotate: clockwise, resultingGameValue: 10",
				m2.toString());
	}
	
	public void testCompareTo() {
		Move m1 = MoveFactory.createMove(5, 4, true, GameValueFactory.getWin()); 
		Move m2 = MoveFactory.createMove(5, 4, true, GameValueFactory.getDraw());
		Move m3 = MoveFactory.createMove(5, 4, true, GameValueFactory.getLoss());
		Move m4 = MoveFactory.createMove(5, 4, true, 
				GameValueFactory.createGameValue((GameValueFactory.getWin().getValue() + GameValueFactory.getDraw().getValue()) / 2));
		assertTrue("WIN == WIN",  m1.compareTo(m1) == 0);
		assertTrue("WIN > DRAW",  m1.compareTo(m2) > 0);
		assertTrue("DRAW > LOSS", m2.compareTo(m3) > 0);
		assertTrue("WIN > " + m4.getResultingGameValue(), m1.compareTo(m4) > 0);
		assertTrue(m4.getResultingGameValue() + " > DRAW", m4.compareTo(m2) > 0);
	}
}
