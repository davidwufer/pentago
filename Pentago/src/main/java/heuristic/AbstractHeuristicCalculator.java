package heuristic;

import static config.Config.DRAW_VALUE;
import static config.Config.LOSS_VALUE;
import static config.Config.WIN_VALUE;

public abstract class AbstractHeuristicCalculator implements HeuristicCalculator {
	
	protected final static int NON_DRAW_CONFLICT_VALUE = DRAW_VALUE - 1;
	protected final static int NON_WIN_CONFLICT_VALUE  = WIN_VALUE - 1;
	protected final static int NON_LOSS_CONFLICT_VALUE = LOSS_VALUE + 1;
	
	protected final static int NUM_SUB_BOARDS = 4;
	protected final static int[] subBoardToCenterIndex = {7, 10, 25, 28};
	
}