package movecomparator;

import move.Move;

public class AbstractHeuristicComparator implements HeuristicComparator {

	@Override
	public int compare(Move m1, Move m2) {
		return m1.getResultingGameValue().getValue() - m2.getResultingGameValue().getValue();
	}

}
