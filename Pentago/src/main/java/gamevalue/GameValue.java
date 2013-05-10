package gamevalue;

import static config.Config.LOSS_VALUE;
import static config.Config.WIN_VALUE;

public abstract class GameValue {
	
	protected int gameValue;

	/* When extending GameValue, be sure to override these if necessary */
	public boolean isTerminalValue() { return false; }
	
	public boolean isWin() { return false; }
	
	public boolean isLoss() { return false; }
	
	public boolean isDraw() { return false; }
	
	public boolean isWinOrLoss() { return false; }
	
	protected GameValue() { throw new UnsupportedOperationException(); } // So Eclipse doesn't complain
	
	protected GameValue(int gameValue) {
		this.gameValue = gameValue;
	}
	
	//This could be the biggest performance hit of switching from an int representation of GameValue to an object
	public GameValue oppositeGameValue() {
		if (isWin()) { 
			return GameValueFactory.getLoss();
		} else if (isLoss()) {
			return GameValueFactory.getWin();
		} else if (isDraw()) {
			return this;
		}
		return GameValueFactory.createGameValue(-gameValue);
	}
	
	public boolean isValidGameValue() {
		return (WIN_VALUE >= gameValue) && (gameValue >= LOSS_VALUE);
	}
	
	public int preCheckIsValidGameValue() {
		if (!isValidGameValue()) {
			throw new IllegalArgumentException("gameValue is not valid");
		}
		return gameValue;
	}
	
	public int getValue() {
		return gameValue;
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.gameValue);
	}
	
	public static GameValue max(GameValue g1, GameValue g2) {
		return (g1.getValue() >= g2.getValue()) ? g1 : g2;
	}
	
	@Override
	public boolean equals(Object other) {
		GameValue otherGameValue = (GameValue) other;
		return gameValue == otherGameValue.getValue();
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(gameValue).hashCode();
	}
}
