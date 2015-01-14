package movecomparator;

import java.util.Comparator;

import move.Move;

// This exists so I can use different types of comparators to see which one is the best
public interface HeuristicComparator extends Comparator<Move>{
	@Override
	public int compare(Move m1, Move m2);
}
