package heuristic;

import static config.Config.DRAW_VALUE;
import static config.Config.LOSS_VALUE;
import static config.Config.WIN_VALUE;
import gamevalue.GameValue;
import junit.framework.TestCase;
import board.Piece;

public class SixBySixBoardHeuristicCalculatorTest extends TestCase {
	
	public void testPreventTerminalValueConflictWithDraw() {
		final SixBySixBoardHeuristicCalculator calc = new SixBySixBoardHeuristicCalculator();
		final int value = calc.preventTerminalValueConflict(DRAW_VALUE);
		assertNotSame(DRAW_VALUE, value);
	}
	
	public void testPreventTerminalValueConflictWithNonTerminal() {
		final SixBySixBoardHeuristicCalculator calc = new SixBySixBoardHeuristicCalculator();
		final int initialValue = 10;
		final int processedValue = calc.preventTerminalValueConflict(initialValue);
		assertEquals(initialValue, processedValue);
	}
	
	public void testPreventTerminalValueConflictWithWin() {
		final SixBySixBoardHeuristicCalculator calc = new SixBySixBoardHeuristicCalculator();
		final int initialValue = WIN_VALUE;
		final int processedValue = calc.preventTerminalValueConflict(initialValue);
		assertTrue(initialValue > processedValue);
	}
	
	public void testPreventTerminalValueConflictWithLoss() {
		final SixBySixBoardHeuristicCalculator calc = new SixBySixBoardHeuristicCalculator();
		final int initialValue = LOSS_VALUE;
		final int processedValue = calc.preventTerminalValueConflict(initialValue);
		assertTrue(initialValue < processedValue);
	}
	
	public void testGetHeuristicValueOppositeValues() {
		final SixBySixBoardHeuristicCalculator calc = new SixBySixBoardHeuristicCalculator();
		
		calc.addPieceValue(Piece.X, 0);
		
		final GameValue xHeuristicValue = calc.getHeuristicValue(Piece.X);
		final GameValue oHeuristicValue = calc.getHeuristicValue(Piece.O);
		assertTrue(xHeuristicValue.getValue() == -oHeuristicValue.getValue());
		
		
	}
	
//	public void testLinesAndLayersWithBlankBoard() {
//		final Board board = BoardFactory.createSixBySixBoardBlank();
//		assertEquals(0, calc.calculateLinesAndLayers(board));
//	}
//	
//	public void testLinesAndLayersWithXOnlyBoard() {
//		final Board board = BoardFactory.createSixBySixBoard("[                 x                  ]");
//		final int expected = -((SixBySixHeuristicCalculator.layersWeight * SixBySixHeuristicCalculator.layersValue[17]) + 
//					           (SixBySixHeuristicCalculator.linesWeight *  SixBySixHeuristicCalculator.linesValue[17]));
////		assertEquals(-2, expected);
//		assertEquals(expected, calc.calculateLinesAndLayers(board));
//	}
//	
//	public void testLinesAndLayersWithXStrongerBoard() {
//		final Board board = BoardFactory.createSixBySixBoard("[     x o      xx o     o            ]");
//		final int expected = (SixBySixHeuristicCalculator.layersWeight * (SixBySixHeuristicCalculator.layersValue[5]
//				                                                          - SixBySixHeuristicCalculator.layersValue[7]
//				                                                          + SixBySixHeuristicCalculator.layersValue[14]
//				                                                          + SixBySixHeuristicCalculator.layersValue[15]
//				                                                          - SixBySixHeuristicCalculator.layersValue[17]
//				                                                          - SixBySixHeuristicCalculator.layersValue[23])) +
//					         (SixBySixHeuristicCalculator.linesWeight * (SixBySixHeuristicCalculator.linesValue[5]
//			                                                             - SixBySixHeuristicCalculator.linesValue[7]
//   				                                                         + SixBySixHeuristicCalculator.linesValue[14]
//   				                                                         + SixBySixHeuristicCalculator.linesValue[15]
//   				                                                         - SixBySixHeuristicCalculator.linesValue[17]
//   				                                                         - SixBySixHeuristicCalculator.linesValue[23]));
////		assertEquals(5, expected);
//		assertEquals(expected, calc.calculateLinesAndLayers(board));
//	}
//	
//	public void testLinesAndLayersWithOStrongerBoard() {
//		final Board board = BoardFactory.createSixBySixBoard("[  oo  x                x o   x      ]");
//		final int expected = (SixBySixHeuristicCalculator.layersWeight * (- SixBySixHeuristicCalculator.layersValue[2]
//				                                                          - SixBySixHeuristicCalculator.layersValue[3]
//				                                                          + SixBySixHeuristicCalculator.layersValue[6]
//				                                                          + SixBySixHeuristicCalculator.layersValue[23]
//				                                                          - SixBySixHeuristicCalculator.layersValue[25]
//				                                                          + SixBySixHeuristicCalculator.layersValue[29])) +
//					         (SixBySixHeuristicCalculator.linesWeight *  (- SixBySixHeuristicCalculator.linesValue[2]
//			                                                              - SixBySixHeuristicCalculator.linesValue[3]
//   				                                                          + SixBySixHeuristicCalculator.linesValue[6]
//   				                                                          + SixBySixHeuristicCalculator.linesValue[23]
//   				                                                          - SixBySixHeuristicCalculator.linesValue[25]
//   				                                                          + SixBySixHeuristicCalculator.linesValue[29]));
////		assertEquals(-2, expected);
//		assertEquals(expected, calc.calculateLinesAndLayers(board));
//	}
//	
//	public void testLShapeAndThreeInARowPerSubBoardForBlankSubBoard() {
//		final Board board = BoardFactory.createSixBySixBoardBlank();
//		assertEquals(0, calc.calculateLShapeAndThreeInARowForASubBoard(board, calc.getCenterIndex(1)));
//	}
//	
//	public void testLShapeAndThreeInARowPerSubBoardForSubBoardWithThreeInARowForCurrentPlayer() {
//		final Board board = BoardFactory.createSixBySixBoard("[ xx      xx             ooo         ]");
//		final int expected = SixBySixHeuristicCalculator.threeInARowWeight * SixBySixHeuristicCalculator.threeInARowValue;
//		assertEquals(expected, calc.calculateLShapeAndThreeInARowForASubBoard(board, calc.getCenterIndex(3)));
//	}
//	
//	public void testLShapeAndThreeInARowPerSubBoardForSubBoardWithThreeInARowForOpponent() {
//		final Board board = BoardFactory.createSixBySixBoard("[x x      x              ooo         ]");
//		final int expected = -(SixBySixHeuristicCalculator.threeInARowWeight * SixBySixHeuristicCalculator.threeInARowValue);
//		assertEquals(expected, calc.calculateLShapeAndThreeInARowForASubBoard(board, calc.getCenterIndex(3)));
//	}
//	
//	// Also 4 L-Shapes
//	public void testLShapeAndThreeInARowPerSubBoardForSubBoardWithTwoThreeInARows() {
//		final Board board = BoardFactory.createSixBySixBoard("[xxx      x         o    ooo    o    ]");
//		final int expected = (2 * (SixBySixHeuristicCalculator.threeInARowWeight * SixBySixHeuristicCalculator.threeInARowValue)) +
//		                     (4 * SixBySixHeuristicCalculator.LShapeWeight * SixBySixHeuristicCalculator.LShapeValue);
//		assertEquals(expected, calc.calculateLShapeAndThreeInARowForASubBoard(board, calc.getCenterIndex(3)));
//	}
//	
//	public void testLShapeAndThreeInARowPerSubBoardForSubBoardWithNoLShapeOrThreeInARow() {
//		final Board board = BoardFactory.createSixBySixBoard("[x o   o x    x                      ]");
//		final int expected = 0;
//		assertEquals(expected, calc.calculateLShapeAndThreeInARowForASubBoard(board, calc.getCenterIndex(1)));
//	}	
//	
//	public void testLShapeAndThreeInARowWithBlankBoard() {
//		Board board = BoardFactory.createSixBySixBoardBlank();
//		assertEquals(0, calc.calculateLShapeAndThreeInARow(board));
//	}
//	
//	public void testLShapeAndThreeInARowWithZeroValueBoard() {
//		final Board board = BoardFactory.createSixBySixBoard("[x o   o x    x      o     x   x  o  ]");
//		final int expected = 0;
//		assertEquals(expected, calc.calculateLShapeAndThreeInARow(board));
//	}
//	
//	public void testLShapeAndThreeInARowWithComplicatedBoard() {
//		final Board board = BoardFactory.createSixBySixBoard("[xxx o     ooxxx             oo    o ]");
//		final int expected =   (2 * SixBySixHeuristicCalculator.threeInARowWeight * SixBySixHeuristicCalculator.threeInARowValue)
//		                     - (2 * SixBySixHeuristicCalculator.LShapeWeight * SixBySixHeuristicCalculator.LShapeValue);
//		assertEquals(expected, calc.calculateLShapeAndThreeInARow(board));
//	}
//	
//	public void testTotalHeuristic() {
//		final Board board = BoardFactory.createSixBySixBoard("[xxx o  xo ooxxx    o x o xx oo o  o ]");
//		final int linesAndLayers = (SixBySixHeuristicCalculator.layersWeight * (+  SixBySixHeuristicCalculator.layersValue[0]
//	  			                                                                +  SixBySixHeuristicCalculator.layersValue[1]
//				                                                                +  SixBySixHeuristicCalculator.layersValue[2]
//				                                                                -  SixBySixHeuristicCalculator.layersValue[4]
//				                                                                +  SixBySixHeuristicCalculator.layersValue[7]
//				                                                                -  SixBySixHeuristicCalculator.layersValue[8]
//		                                                                        - SixBySixHeuristicCalculator.layersValue[10]
//				                                                                - SixBySixHeuristicCalculator.layersValue[11]
//				                                                                + SixBySixHeuristicCalculator.layersValue[12]
//				                                                                + SixBySixHeuristicCalculator.layersValue[13]
//				                                                                + SixBySixHeuristicCalculator.layersValue[14]
//				                                                                - SixBySixHeuristicCalculator.layersValue[19]
//				                                                                + SixBySixHeuristicCalculator.layersValue[21]
//				                                                                - SixBySixHeuristicCalculator.layersValue[23]
//		                                                                        + SixBySixHeuristicCalculator.layersValue[25]
//				                                                                + SixBySixHeuristicCalculator.layersValue[26]
//				                                                                - SixBySixHeuristicCalculator.layersValue[28]
//				                                                                - SixBySixHeuristicCalculator.layersValue[29]
//				                                                                - SixBySixHeuristicCalculator.layersValue[31]
//				                                                                - SixBySixHeuristicCalculator.layersValue[34])) +
//                                   (SixBySixHeuristicCalculator.linesWeight *  (+  SixBySixHeuristicCalculator.linesValue[0]
//			  		                                                            +  SixBySixHeuristicCalculator.linesValue[1]
//					                                                            +  SixBySixHeuristicCalculator.linesValue[2]
//					                                                            -  SixBySixHeuristicCalculator.linesValue[4]
//					                                                            +  SixBySixHeuristicCalculator.linesValue[7]
//					                                                            -  SixBySixHeuristicCalculator.linesValue[8]
//			                                                                    - SixBySixHeuristicCalculator.linesValue[10]
//					                                                            - SixBySixHeuristicCalculator.linesValue[11]
//					                                                            + SixBySixHeuristicCalculator.linesValue[12]
//					                                                            + SixBySixHeuristicCalculator.linesValue[13]
//					                                                            + SixBySixHeuristicCalculator.linesValue[14]
//					                                                            - SixBySixHeuristicCalculator.linesValue[19]
//					                                                            + SixBySixHeuristicCalculator.linesValue[21]
//					                                                            - SixBySixHeuristicCalculator.linesValue[23]
//			                                                                    + SixBySixHeuristicCalculator.linesValue[25]
//					                                                            + SixBySixHeuristicCalculator.linesValue[26]
//					                                                            - SixBySixHeuristicCalculator.linesValue[28]
//					                                                            - SixBySixHeuristicCalculator.linesValue[29]
//					                                                            - SixBySixHeuristicCalculator.linesValue[31]
//					                                                            - SixBySixHeuristicCalculator.linesValue[34]));
////		assertEquals(5, linesAndLayers);
//		final int threeInARowAndLShape = (3 * SixBySixHeuristicCalculator.threeInARowWeight * SixBySixHeuristicCalculator.threeInARowValue)
//		           - (2 * SixBySixHeuristicCalculator.LShapeWeight * SixBySixHeuristicCalculator.LShapeValue);
////		assertEquals(5, threeInARowAndLShape);
//		assertEquals(GameValueFactory.createGameValue(linesAndLayers + threeInARowAndLShape), calc.getHeuristicValue(board));
//	}
}
