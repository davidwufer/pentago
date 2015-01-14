package transposition;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;

public class BoardDatabaseResult {
	protected static final GameValue NO_RESULT_VALUE = null;
	private final GameValue resultingGameValue;
	
	public static final BoardDatabaseResult NO_RESULT = new BoardDatabaseResult(NO_RESULT_VALUE);
	public static final BoardDatabaseResult WIN_RESULT = new BoardDatabaseResult(GameValueFactory.getWin());
	public static final BoardDatabaseResult LOSS_RESULT = new BoardDatabaseResult(GameValueFactory.getLoss());
	public static final BoardDatabaseResult DRAW_RESULT = new BoardDatabaseResult(GameValueFactory.getDraw());
	// TODO: since a TranspositionTableResult can only have terminal values, let's make singletons
	
	private BoardDatabaseResult(GameValue resultingGameValue) {
		this.resultingGameValue = resultingGameValue;
	}
	
	public static BoardDatabaseResult getBoardDatabaseResult(GameValue resultingGameValue) {
		if (resultingGameValue.isWin()) {
			return WIN_RESULT;
		} else if (resultingGameValue.isLoss()) {
			return LOSS_RESULT;
		} else if (resultingGameValue.isDraw()) {
			return DRAW_RESULT;
		} else {
			throw new IllegalArgumentException("createTTResult must be passed a terminal value");
		}
	}
	
	public boolean isResultFound() {
		return resultingGameValue != NO_RESULT_VALUE;
	}
	
	public GameValue getResult() {
		return resultingGameValue;
	}
}
