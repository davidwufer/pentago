package move;

import gamevalue.GameValue;


public interface Move extends Comparable<Move> {
	public int getIndex();
	public int getSubBoard();
	public boolean getIsClockwise();
	
	/**
	 * Returns the game value for the current board that would result
	 * if a move were chosen. For example, if this move results in a LOSS
	 * for the next player, the value returned by this will be a WIN since
	 * we are only looking at the current player
	 * @return
	 */
	public GameValue getResultingGameValue();
	
	@Override
	public String toString();
	
	@Override
	public int compareTo(Move m);
	
	public void setResultingGameValue(GameValue gameValue);
}
