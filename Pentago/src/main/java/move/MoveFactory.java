package move;

import gamevalue.GameValue;

public class MoveFactory {
	public static Move createMove(int index, int subBoard, boolean isClockwise, GameValue resultingGameValue) {
		return new MoveImpl(index, subBoard, isClockwise, resultingGameValue);
	}
}

class MoveImpl implements Move {

	private final int index, subBoard;
	private GameValue resultingGameValue;
	private final boolean isClockwise;
	
	public MoveImpl(int index, int subBoard, boolean isClockwise, GameValue resultingGameValue) {
		this.index = index;
		this.subBoard = subBoard;
		this.resultingGameValue = resultingGameValue;
		this.isClockwise = isClockwise;
	}
	
	// As far as I know, this is never used!
	@Override
	public int compareTo(Move m) {
		return resultingGameValue.getValue() - m.getResultingGameValue().getValue();
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public boolean getIsClockwise() {
		return isClockwise;
	}

	@Override
	public GameValue getResultingGameValue() {
		return resultingGameValue;
	}

	@Override
	public int getSubBoard() {
		return subBoard;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("index: ").append(getIndex())
               .append(", subBoard: ").append(getSubBoard())
	           .append(", rotate: ");
		if (getIsClockwise()) {
			builder.append("clockwise");
		} else {
			builder.append("counter-clockwise");
		}
		builder.append(", resultingGameValue: ").append(getResultingGameValue());
		return builder.toString();
	}

	@Override
	public void setResultingGameValue(GameValue resultingGameValue) {
		this.resultingGameValue = resultingGameValue;
	}
	
}