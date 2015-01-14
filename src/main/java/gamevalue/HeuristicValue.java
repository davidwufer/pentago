package gamevalue;

public class HeuristicValue extends GameValue {
	
	protected HeuristicValue(int gameValue) {
		super(gameValue);
	}
	
	@Override
	public boolean isTerminalValue() {
		return false;
	}
	
	@Override
	public boolean isWin() {
		return false;
	}
	
	@Override
	public boolean isLoss() {
		return false;
	}
	
	@Override
	public boolean isWinOrLoss() {
		return false;
	}
}
