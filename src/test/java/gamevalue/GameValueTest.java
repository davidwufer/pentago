package gamevalue;

import static config.Config.DRAW_VALUE;
import static config.Config.LOSS_VALUE;
import static config.Config.UNDETERMINED_VALUE;
import static config.Config.WIN_VALUE;
import junit.framework.TestCase;

public class GameValueTest extends TestCase {

	public void testZeroSumGame() {
		assertEquals("WIN and LOSS should have opposite signs", GameValueFactory.getWin(), GameValueFactory.getLoss().oppositeGameValue());
		assertTrue("WIN > DRAW", GameValueFactory.getWin().getValue() > GameValueFactory.getDraw().getValue());
		assertTrue("DRAW > LOSS", GameValueFactory.getDraw().getValue() > GameValueFactory.getLoss().getValue());
		
		assertTrue("UNDETERMINED != WIN", GameValueFactory.getUndetermined() != GameValueFactory.getWin());
		assertTrue("UNDETERMINED != DRAW", GameValueFactory.getUndetermined() != GameValueFactory.getDraw());
		assertTrue("UNDETERMINED != LOSS", GameValueFactory.getUndetermined() != GameValueFactory.getLoss());
	}
	
	public void testDrawValue() {
		assertEquals("DRAW should equal 0 (zero)", 0, GameValueFactory.getDraw().getValue());
		assertEquals("DRAW should equal the average between a WIN and LOSS", GameValueFactory.getDraw(), 
				GameValueFactory.createGameValue((GameValueFactory.getWin().getValue() + GameValueFactory.getLoss().getValue()) / 2));
	}
	
	public void testIsTerminalValue() {
		assertTrue("WIN should be a terminal value",   GameValueFactory.getWin().isTerminalValue());
		assertTrue("DRAW should be a terminal value", GameValueFactory.getDraw().isTerminalValue());
		assertTrue("LOSS should be a terminal value", GameValueFactory.getLoss().isTerminalValue());
		
		assertFalse("UNDETERMINED should not be a terminal value", GameValueFactory.getUndetermined().isTerminalValue());
	}
	
	public void testIsValidGameValue() {
		assertTrue("WIN should be a valid game value", GameValueFactory.getWin().isValidGameValue());
		assertTrue("DRAW should be a valid game value", GameValueFactory.getDraw().isValidGameValue());
		assertTrue("LOSS should be a valid game value", GameValueFactory.getLoss().isValidGameValue());
		assertTrue("UNDETERMINED should be a valid game value", GameValueFactory.getUndetermined().isValidGameValue());
		
		assertTrue((GameValueFactory.getWin().getValue() - 1) + " should be a valid game value", 
				GameValueFactory.createGameValue(GameValueFactory.getWin().getValue() - 1).isValidGameValue());
		assertFalse((GameValueFactory.getWin().getValue() + 1) + " should be an invalid game value", 
				GameValueFactory.createGameValue(GameValueFactory.getWin().getValue() + 1).isValidGameValue());
		
		assertTrue((GameValueFactory.getLoss().getValue() + 1) + " should be a valid game value", 
				GameValueFactory.createGameValue(GameValueFactory.getLoss().getValue() + 1).isValidGameValue());
		assertFalse((GameValueFactory.getLoss().getValue() - 1) + " should be an invalid game value", 
				GameValueFactory.createGameValue(GameValueFactory.getLoss().getValue() - 1).isValidGameValue());
	}
	
	public void testWin() {
		GameValue gameValue = GameValueFactory.getWin();
		assertTrue(gameValue.isWin());
		assertTrue(gameValue.isWinOrLoss());
		assertFalse(gameValue.isLoss());
		assertFalse(gameValue.isDraw());
		assertTrue(gameValue.isTerminalValue());
		assertEquals(WIN_VALUE, gameValue.getValue());
		assertEquals(GameValueFactory.getLoss(), gameValue.oppositeGameValue());
		assertEquals(GameValueFactory.getWin().hashCode(), gameValue.hashCode());
		assertEquals(GameValueFactory.getWin(), gameValue);
	}
	
	public void testLoss() {
		GameValue gameValue = GameValueFactory.getLoss();
		assertFalse(gameValue.isWin());
		assertTrue(gameValue.isWinOrLoss());
		assertTrue(gameValue.isLoss());
		assertFalse(gameValue.isDraw());
		assertTrue(gameValue.isTerminalValue());
		assertEquals(LOSS_VALUE, gameValue.getValue());
		assertEquals(GameValueFactory.getWin(), gameValue.oppositeGameValue());
		assertEquals(GameValueFactory.getLoss().hashCode(), gameValue.hashCode());
		assertEquals(GameValueFactory.getLoss(), gameValue);
	}
	
	public void testDraw() {
		GameValue gameValue = GameValueFactory.getDraw();
		assertFalse(gameValue.isWin());
		assertFalse(gameValue.isWinOrLoss());
		assertFalse(gameValue.isLoss());
		assertTrue(gameValue.isDraw());
		assertTrue(gameValue.isTerminalValue());
		assertEquals(DRAW_VALUE, gameValue.getValue());
		assertEquals(GameValueFactory.getDraw(), gameValue.oppositeGameValue());
		assertEquals(GameValueFactory.getDraw().hashCode(), gameValue.hashCode());
		assertEquals(GameValueFactory.getDraw(), gameValue);
	}
	
	public void testUndetermined() {
		GameValue gameValue = GameValueFactory.getUndetermined();
		assertFalse(gameValue.isWin());
		assertFalse(gameValue.isWinOrLoss());
		assertFalse(gameValue.isLoss());
		assertFalse(gameValue.isDraw());
		assertFalse(gameValue.isTerminalValue());
		assertEquals(UNDETERMINED_VALUE, gameValue.getValue());
		assertEquals(GameValueFactory.getUndetermined().hashCode(), gameValue.hashCode());
		assertEquals(GameValueFactory.getUndetermined(), gameValue);
	}
}
