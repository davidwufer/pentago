package gamevalue;
import static config.Config.DRAW_VALUE;

public class DrawValue extends GameValue {
	// For Optimization (so objects don't have to be recreated)
	private static int hashCode = Integer.valueOf(DRAW_VALUE).hashCode();
	
	protected DrawValue(int gameValue) {
		super(gameValue);
	}
	
	@Override
	public boolean isTerminalValue() {
		return true;
	}
	
	@Override
	public boolean isDraw() {
		return true;
	}
	
	@Override
	public String toString() {
		return GameValueFactory.DRAW_STRING;
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}
}
