package gamevalue;

import static config.Config.LOSS_VALUE;

public class LossValue extends GameValue {
	// For Optimization (so objects don't have to be recreated)
	private static int hashCode = Integer.valueOf(LOSS_VALUE).hashCode();
	
	protected LossValue(int gameValue) {
		super(gameValue);
	}
	
	@Override
	public boolean isTerminalValue() {
		return true;
	}
	
	@Override
	public boolean isLoss() {
		return true;
	}
	
	@Override
	public boolean isWinOrLoss() {
		return true;
	}
	
	@Override
	public String toString() {
		return GameValueFactory.LOSS_STRING;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
}
