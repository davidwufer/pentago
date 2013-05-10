package move;

import hash.BoardSet;
import movecomparator.HeuristicComparator;

public class MoveGeneratorFactory {
	public static MoveGenerator createMoveGeneratorOptimized(HeuristicComparator heuristicComparator, BoardSet boardSet) {
		return new MoveGeneratorOptimized(heuristicComparator, boardSet);
	}
	
	public static MoveGenerator createMoveGeneratorMetric(HeuristicComparator heuristicComparator) {
		return new MoveGeneratorMetric(heuristicComparator);
	}
	
	public static MoveGenerator createMoveGeneratorStandard(HeuristicComparator heuristicComparator) {
		return new MoveGeneratorStandard(heuristicComparator);
	}
}
