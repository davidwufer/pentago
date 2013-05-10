/**
 * 
 */
package game;

import junit.framework.TestCase;
import move.MoveGenerator;
import move.MoveGeneratorStandard;
import movecomparator.HeuristicComparatorFactory;
import board.Board;
import board.BoardFactory;

/**
 * @author David Wu
 *
 */
public class MoveGeneratorStandardTest extends TestCase {
	
	private Board gameBoard;
	private MoveGenerator moveGenerator;
	private Game game;
	
	@Override
	protected void setUp() {
	}
	
	public void testGenerateMovesForEmptyFourByFourBoardWithSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoardBlank();
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorStandard(HeuristicComparatorFactory.SubtractionComparator()) {
			@Override
			public void hookAfterStage1(MoveGeneratorStandardInfo info) {
				super.hookAfterStage1(info);
				assertEquals("Blank 4x4 board unfiltered children", 128, info.getNumUnfilteredChildren());
			}
			
			@Override
			public void hookAfterStage2(MoveGeneratorStandardInfo info) {
				super.hookAfterStage2(info);
				/*
				Move move;
				for (int currMoveIndex = 0; currMoveIndex < info.getNumChildren(); currMoveIndex++) {
					move = info.getChildren()[currMoveIndex];
					game.doMove(move);
					assertTrue(info.getBoardSet().contains(game.getBoard(), false));
					game.undoMove(move);
				}
				*/
//				AbstractBoardHashSet hashSet = (AbstractBoardHashSet) info.getBoardSet();
//				HashSet<?> set = hashSet.analyze();
				
				/*
				Board testBoard = BoardFactory.createFourByFourBoard("[x               ]");
				System.out.println(Arrays.hashCode(testBoard.getBoardInternal()));
				for (int index = 0; index < 9; index++) {
					System.out.printf("%3d ", testBoard.getBoardInternal()[index] - 0);
				}
				System.out.println();
				assertTrue(info.getBoardSet().contains(testBoard, false));
				*/
				
				assertEquals("BoardSet should have excluded the right number of boards", 3, info.getBoardSet().size());
				assertEquals("Blank 4x4 board non-duplicate children", 3, info.getNumChildren());
			}
			
			@Override
			public void hookAfterStage3(MoveGeneratorStandardInfo info) {
				super.hookAfterStage3(info);
			}
			
			@Override
			public void hookAfterStage4(MoveGeneratorStandardInfo info) {
				super.hookAfterStage4(info);
			}
		};
		
		moveGenerator.generateMoves(game);
	}
	
//	public void testGenerateMovesForEmptySixBySixBoardWithSymmetries() {
//		gameBoard = BoardFactory.createBlankSixBySixBoard();
//		game = GameFactory.createGame(gameBoard);
//		moveGenerator = new MoveGeneratorStandard(HeuristicComparatorFactory.SubtractionComparator()) {
//			@Override
//			public void hookAfterStage1(MoveGeneratorStandardInfo info) {
//				super.hookAfterStage1(info);
//				assertEquals("Blank board should have 288 total (maybe duplicated) moves", 288, info.getNumUnfilteredChildren());
//			}
//			
//			@Override
//			public void hookAfterStage2(MoveGeneratorStandardInfo info) {
//				super.hookAfterStage2(info);
//				for (Move move : info.getChildren()) {
//					game.doMove(move);
//					System.out.println(game.getBoard().toString());
//					game.undoMove(move);
//				}
//				assertEquals("Blank board should have 6 non-duplicate moves", 6, info.getNumChildren());
//			}
//			
//			@Override
//			public void hookAfterStage3(MoveGeneratorStandardInfo info) {
//				super.hookAfterStage3(info);
//			}
//			
//			@Override
//			public void hookAfterStage4(MoveGeneratorStandardInfo info) {
//				super.hookAfterStage4(info);
//			}
//		};
//		
//		MoveGeneratorResults moveGeneratorResults;
//		moveGeneratorResults = moveGenerator.generateMoves();
//	}
	
//	public void testGenerateMovesForFilledBoard() {
//		gameBoard = BoardFactory.createSixBySixBoard("xxxoooxxxoooxxxoooxxxoooxxxoooxxxooo");
//		
//		game.generateMoves();
//		fail();
//	}
}
