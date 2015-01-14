/**
 * 
 */
package game;

import gamevalue.GameValue;
import junit.framework.TestCase;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorMetric;
import move.MoveGeneratorMetric.MoveGeneratorMetricInfo;
import movecomparator.HeuristicComparatorFactory;
import board.Board;
import board.BoardFactory;

/**
 * @author David Wu
 *
 */
public class MoveGeneratorMetricTest extends TestCase {
	
	private Board gameBoard;
	private MoveGenerator moveGenerator;
	private Game game;
		
	public void testGenerateMovesForEmptyFourByFourBoardWithSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoardBlank();
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Blank 4x4 board unfiltered children: 16*4*2", 128, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Total non-duplicate symmetry-included children", 16, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Total non-duplicate symmetry-excluded children", 3, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				assertNumWinsLossDraws(0, 0, 0, info);
			}
		};
		moveGenerator.generateMoves(game);
	}

	
	public void testGenerateMovesForFullFourByFourBoardWithImmediateTerminalAndNonImmediateTerminalAndNoSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoard("[xx   o   o      ]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Full 4x4 board unfiltered children", 89, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Total non-duplicate symmetry-included children", 56, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Total non-duplicate symmetry-excluded children", 56, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				assertNumWinsLossDraws(2, 0, 0, info);
			}
		};
		moveGenerator.generateMoves(game);
	}
	
	public void testGenerateMovesForFullFourByFourBoardWithImmediateTerminalAndNonImmediateTerminalAndSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoard("[xx  oo  oo  xx  ]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Full 4x4 board unfiltered children", 50, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Total non-duplicate symmetry-included children", 32, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Total non-duplicate symmetry-excluded children", 16, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				assertNumWinsLossDraws(1, 0, 12, info);
			}
		};
		moveGenerator.generateMoves(game);
	}
	
	public void testGenerateMovesForFullFourByFourBoardWithImmediateTerminalAndNonImmediateTerminalAndSymmetriesInLateGame() {
		gameBoard = BoardFactory.createFourByFourBoard("[ o   xoxxxoxooxo]");
		game = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Full 4x4 board unfiltered children", 11, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Total non-duplicate symmetry-included children", 10, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Total non-duplicate symmetry-excluded children", 10, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				assertNumWinsLossDraws(5, 2, 1, info);
			}
		};
		moveGenerator.generateMoves(game);
	}
	
	
	public void testGenerateMovesForEmptySixBySixBoardWithSymmetries() {
		gameBoard = BoardFactory.createSixBySixBoardBlank();
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Blank board should have 288 total (maybe duplicated) moves", 288, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Blank board should have 6 non-duplicate moves", 36, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Blank board should have 6 non-duplicate moves", 6, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
			}
		};
		
		moveGenerator.generateMoves(game);
	}
	
	public void testGenerateMovesForSixBySixBoardWithFilledDraw() {
		gameBoard = BoardFactory.createSixBySixBoard("[xxxxo ooooxxxxxxooooooxxxxxxooooooxx]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				printAllBoardsFromMoves(info);
				assertEquals("Unfiltered Children: ", 8, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Duplicates excluding symmetries removed: ", 6, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Duplicates including symmetries removed: ", 6, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				assertNumWinsLossDraws(0, 6, 0, info);
			}
		};
		moveGenerator.generateMoves(game);
	}
	
	public void testGenerateMovesForSixBySixBoardWithSymmetries() {
		gameBoard = BoardFactory.createSixBySixBoard("[xoo xxxox oo  xxxooxxx  oo xoxxx oox]");
		game = GameFactory.createGame(gameBoard);
		System.out.println(gameBoard.toPrettyString());
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Unfiltered Children: ", 64, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Duplicates excluding symmetries removed: ", 64, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Duplicates including symmetries removed: ", 16, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				printAllBoardsFromMoves(info);
				assertNumWinsLossDraws(3, 0, 0, info);
			}
		};
		moveGenerator.generateMoves(game);
	}
	
	public void testGenerateMovesForSixBySixMidGameBoardWithNoDuplicatesOrSymmetries() {
		gameBoard = BoardFactory.createSixBySixBoard("[o    x xxoo  xoox  ox    x  oo ox xx]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorMetric(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorMetricInfo info) {
				super.hookAfterStage1(info);
				printAllBoardsFromMoves(info);
				assertEquals("Unfiltered Children: ", 136, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorMetricInfo info) {
				super.hookAfterStage2(info);
				assertEquals("Duplicates excluding symmetries removed: ", 136, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorMetricInfo info) {
				super.hookAfterStage3(info);
				assertEquals("Duplicates including symmetries removed: ", 136, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorMetricInfo info) {
				super.hookAfterStage4(info);
				printAllBoardsFromMoves(info);
				assertNumWinsLossDraws(0, 0, 0, info);
			}
		};
		moveGenerator.generateMoves(game);
	}
	
	private void printAllBoardsFromMoves(MoveGeneratorMetricInfo info) {
		Move move;
		for (int index = 0; index < info.getNumChildren(); index++) {
			move = info.getChildren()[index];
			game.doMove(move);
//			System.out.println(game.getBoard().toString());
			if (move.getResultingGameValue().isWin()) {
				System.out.println(game.getBoard().toPrettyString());
			}
			//System.out.println(move.toString());
			game.undoMove(move);
		}
	}
	
	private void assertNumWinsLossDraws(int eWins, int eDraws, int eLosses, MoveGeneratorMetricInfo info) {
		int numWins = 0, numLosses = 0, numDraws = 0, numOther = 0;
		GameValue resultingGameValue;
		for (int index = 0; index < info.getNumChildren(); index++) {
			Move move = info.getChildren()[index];
			resultingGameValue = move.getResultingGameValue();
			if (resultingGameValue.isWin()) {
				numWins += 1;
			} else if (resultingGameValue.isLoss()) {
				numLosses += 1;
			} else if (resultingGameValue.isDraw()) {
				numDraws += 1;
			} else {
				numOther += 1;
			}
		}
		assertEquals("#Wins incorrect", eWins, numWins);
		assertEquals("#Draws incorrect", eDraws, numDraws);
		assertEquals("#Losses incorrect", eLosses, numLosses);
	}
}
