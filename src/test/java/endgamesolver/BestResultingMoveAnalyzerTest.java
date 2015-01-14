package endgamesolver;

import static org.junit.Assert.assertEquals;
import gamevalue.GameValue;

import org.junit.Test;

import board.Board;
import board.BoardFactory;

public class BestResultingMoveAnalyzerTest {

	@Test
	public void testCompareStandardToOptimized() {
		final int numCompares = 1000;
		final int depth = 35;
		
		final BestResultingMoveAnalyzer standard = new BestResultingMoveAnalyzerStandard(null);
		final BestResultingMoveAnalyzer optimized = new BestResultingMoveAnalyzerSubOptimal(null);
		
		for (int num = 0; num < numCompares; num++) {
			final Board board = BoardFactory.createSixBySixBoardAtRandomDepth(depth);
			
			final GameValue standardValue = standard.getBestResultingGameValue(board);
			final GameValue optimizedValue = optimized.getBestResultingGameValue(board);
			assertEquals(standardValue.getValue(), optimizedValue.getValue());
		}
	}
	
	@Test
	public void testBenchmark() {
		final int numCompares = 100000;
		final int depth = 35;
		
		final BestResultingMoveAnalyzer standard = new BestResultingMoveAnalyzerStandard(null);
		final BestResultingMoveAnalyzer optimized = new BestResultingMoveAnalyzerSubOptimal(null);
		
		for (int num = 0; num < numCompares; num++) {
			final Board board = BoardFactory.createSixBySixBoardAtRandomDepth(depth);
			
			final GameValue standardValue = standard.getBestResultingGameValue(board);
//			final GameValue optimizedValue = optimized.getBestResultingGameValue(board);
//			assertEquals(standardValue.getValue(), optimizedValue.getValue());
		}
	}
}
