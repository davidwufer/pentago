package gamevalue;

import static config.Config.WIN_VALUE;

public class WinValue extends GameValue {
	// For Optimization (so objects don't have to be recreated)
	private static int hashCode = Integer.valueOf(WIN_VALUE).hashCode();
	
	protected WinValue(int gameValue) {
		super(gameValue);
	}
	
	@Override
	public boolean isTerminalValue() {
		return true;
	}
	
	@Override
	public boolean isWin() {
		return true;
	}
	
	@Override
	public boolean isWinOrLoss() {
		return true;
	}
	
	@Override
	public String toString() {
		return GameValueFactory.WIN_STRING;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
}
