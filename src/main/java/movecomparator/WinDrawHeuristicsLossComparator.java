package movecomparator;

import static config.Config.WIN_VALUE;
import gamevalue.GameValue;
import move.Move;
public class WinDrawHeuristicsLossComparator extends AbstractHeuristicComparator {
	
	/*   W D H L
	 * W = > > >
	 * D   = > >
	 * H     c >
	 * L       =
	 */
	
	/* This is a bit of a hack...
	 * A heuristic value must take on a value in (LOSS_VALUE, WIN_VALUE) not including DRAW_VALUE.
	 * That means, if we increase the value of a DRAW_VALUE to a WIN_VALUE and a WIN_VALUE to WIN_VALUE + 1,
	 * all DRAW_VALUEs will be prioritized before heuristic values. This prevents needing to do a bunch of if/else checks
	 * because we simplify things by using a subtraction operation.
	 */
	@Override
	public int compare(Move move1, Move move2) {			
		final GameValue gameValue1 = move1.getResultingGameValue();
		final GameValue gameValue2 = move2.getResultingGameValue();
		
		int value1;
		int value2;
		
		if (gameValue1.isWin()) {
			value1 = WIN_VALUE + 1;
		} else if (gameValue1.isDraw()) {
			value1 = WIN_VALUE;
		} else {
			value1 = gameValue1.getValue();
		}
		
		if (gameValue2.isWin()) {
			value2 = WIN_VALUE + 1;
		} else if (gameValue2.isDraw()) {
			value2 = WIN_VALUE;
		} else {
			value2 = gameValue2.getValue();
		}

		return value1 - value2;
	}
}
