package movecomparator;

import java.util.Comparator;

import move.Move;

public class WinHeuristicsDrawLossComparator extends AbstractHeuristicComparator {
	// TODO: improve the comparator so it's ordered by WINs, unknowns, DRAWs, and LOSSes.
	public class MoveComparator implements Comparator<Move>{
		@Override
		public int compare(Move m1, Move m2) {
			throw new RuntimeException("Not implemented");
		}
	}
}
