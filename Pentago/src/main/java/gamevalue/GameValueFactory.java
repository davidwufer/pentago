package gamevalue;

import static config.Config.DRAW_VALUE;
import static config.Config.LOSS_VALUE;
import static config.Config.UNDETERMINED_VALUE;
import static config.Config.WIN_VALUE;
import util.ConsoleOutput;

public class GameValueFactory {
	protected static final String WIN_STRING = "WIN";
	protected static final String LOSS_STRING = "LOSS";
	protected static final String DRAW_STRING = "DRAW";
//	protected static final String UNDETERMINED_STRING = "UNDETERMINED";
	
	protected static final GameValue WIN = new WinValue(WIN_VALUE);
	protected static final GameValue LOSS = new LossValue(LOSS_VALUE);
	protected static final GameValue DRAW = new DrawValue(DRAW_VALUE);
	protected static final GameValue UNDETERMINED = new HeuristicValue(UNDETERMINED_VALUE);
	
	public static GameValue createGameValue(int gameValue) {
		if (gameValue == WIN_VALUE) {
			ConsoleOutput.printWarning("WIN should not be created using createGameValue. Try createWin() instead");
			return WIN;
		} else if (gameValue == LOSS_VALUE) {
			ConsoleOutput.printWarning("LOSS should not be created using createGameValue. Try createLoss() instead");
			return LOSS;
		} else if (gameValue == DRAW_VALUE) {
			ConsoleOutput.printWarning("DRAW should not be created using createGameValue. Try createDraw() instead");
			return DRAW;
		} else if (gameValue == UNDETERMINED_VALUE) {
//			 ConsoleOutput.printWarning("UNDETERMINED should not be created using createGameValue. Try createUndetermined() instead");
			return UNDETERMINED;
		} else {
			return new HeuristicValue(gameValue);
		}
	}
	
	public static GameValue createTerminalGameValue(String strGameValue) {
		if (WIN_STRING.equals(strGameValue)) {
			return WIN;
		} else if (LOSS_STRING.equals(strGameValue)) {
			return LOSS;
		} else if (DRAW_STRING.equals(strGameValue)) {
			return DRAW;
		} else {
			throw new IllegalArgumentException(strGameValue + " is not an accepted terminal value");
		}
	}
	
	public static GameValue createTerminalGameValue(int gameValue) {
		if (gameValue == WIN_VALUE) {
			return WIN;
		} else if (gameValue == LOSS_VALUE) {
			return LOSS;
		} else if (gameValue == DRAW_VALUE) {
			return DRAW;
		} else {
			throw new IllegalArgumentException(gameValue + " is not a terminal value");
		}
	}
	
	public static GameValue getWin() {
		return WIN;
	}
	
	public static GameValue getLoss() {
		return LOSS;
	}
	
	public static GameValue getDraw() {
		return DRAW;
	}
	
	// This should only be used for 4x4 boards, as there will always be a heuristic value for a 6x6
	public static GameValue getUndetermined() {
		return UNDETERMINED;
	}
}
