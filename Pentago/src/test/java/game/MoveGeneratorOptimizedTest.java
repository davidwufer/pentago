/**
 * 
 */
package game;

import gamevalue.GameValue;
import hash.BoardSetFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorOptimized;
import move.MoveGeneratorResults;
import movecomparator.HeuristicComparatorFactory;
import board.Board;
import board.BoardFactory;

/**
 * @author David Wu
 *
 */
public class MoveGeneratorOptimizedTest extends TestCase {
	
	private Board gameBoard;
	private MoveGenerator moveGenerator;
	private Game game;
	private MoveGeneratorResults results;
	
	@Override
	protected void setUp() {
	}
	
	public void testGenerateMovesForEmptyFourByFourBoardWithSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoardBlank();
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 3, results.getNumMoves());
		assertNumWinsLossDraws(results, 0, 0, 0);
	}

	public void testGenerateMovesForFullFourByFourBoardWithImmediateTerminalAndNonImmediateTerminalAndNoSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoard("[xx   o   o      ]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 56, results.getNumMoves());
		assertNumWinsLossDraws(results, 2, 0, 0);
	}
	
	public void testGenerateMovesForFullFourByFourBoardWithImmediateTerminalAndNonImmediateTerminalAndSymmetries() {
		gameBoard = BoardFactory.createFourByFourBoard("[xx  oo  oo  xx  ]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		printAllBoardsFromMoves(results);
		assertEquals("Number of moves", 16, results.getNumMoves());
		assertNumWinsLossDraws(results, 1, 0, 12);
	}
	
	public void testGenerateMovesForFullFourByFourBoardWithImmediateTerminalAndNonImmediateTerminalAndSymmetriesInLateGame() {
		gameBoard = BoardFactory.createFourByFourBoard("[ o   xoxxxoxooxo]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 10, results.getNumMoves());
		assertNumWinsLossDraws(results, 5, 2, 1);
	}
	
	public void testGenerateMovesForEmptySixBySixBoardWithSymmetries() {
		gameBoard = BoardFactory.createSixBySixBoardBlank();
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 6, results.getNumMoves());
		assertNumWinsLossDraws(results, 0, 0, 0);
	}
	
	public void testGenerateMovesForSixBySixBoardWithFilledDraw() {
		gameBoard = BoardFactory.createSixBySixBoard("[xxxxo ooooxxxxxxooooooxxxxxxooooooxx]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 6, results.getNumMoves());
		assertNumWinsLossDraws(results, 0, 6, 0);
	}
	
	public void testGenerateMovesForSixBySixBoardWithSymmetries() {
		gameBoard = BoardFactory.createSixBySixBoard("[xoo xxxox oo  xxxooxxx  oo xoxxx oox]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 16, results.getNumMoves());
		assertNumWinsLossDraws(results, 3, 0, 0);
	}
	
	public void testGenerateMovesForSixBySixMidGameBoardWithNoDuplicatesOrSymmetries() {
		gameBoard = BoardFactory.createSixBySixBoard("[o    x xxoo  xoox  ox    x  oo ox xx]");
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 136, results.getNumMoves());
		assertNumWinsLossDraws(results, 0, 0, 0);
	}
	
	public void testImmediateTerminalWinImmediatelyReturns() {
		gameBoard = BoardFactory.createSixBySixBoard("[xxxx  oooo                          ]");
		System.out.println(gameBoard.toPrettyString());
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), true, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 1, results.getNumMoves());
		assertNumWinsLossDraws(results, 1, 0, 0);		
	}
	
	public void testNonImmediateTerminalWinImmediatelyReturns() {
		gameBoard = BoardFactory.createSixBySixBoard("[xxx      x     x  oooo              ]");
		System.out.println(gameBoard.toPrettyString());
		game = GameFactory.createGame(gameBoard);
		moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), true, true);
		results = moveGenerator.generateMoves(game);
		assertEquals("Number of moves", 1, results.getNumMoves());
		assertNumWinsLossDraws(results, 1, 0, 0);		
	}
	
	
	private void assertNumWinsLossDraws(MoveGeneratorResults results, int eWins, int eDraws, int eLosses) {
		int numChildren = results.getNumMoves();
		Move[] moves = results.getGeneratedMoves();
		int numWins = 0, numLosses = 0, numDraws = 0, numOther = 0;
		GameValue resultingGameValue;
		for (int index = 0; index < numChildren; index++) {
			Move move = moves[index];
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
	
	private void printAllBoardsFromMoves(MoveGeneratorResults results) {
		Move move;
		for (int index = 0; index < results.getNumMoves(); index++) {
			move = results.getGeneratedMoves()[index];
			game.doMove(move);
//			System.out.println(game.getBoard().toString());
			System.out.println(game.getBoard().toPrettyString());
			//System.out.println(move.toString());
			game.undoMove(move);
		}
	}
	
	public void testImmediateTerminalPossibleForSixBySixBoard() {
		final Board sixBySixBoardBlank = BoardFactory.createSixBySixBoardBlank();
		final Board sixBySixBoardFilled = BoardFactory.createSixBySixBoard("[xxxoooxxxoooxxxoooxxxoooxxxoooxxxooo]");
		final Board sixBySixBoardBorderAbove = BoardFactory.createSixBySixBoard("[ xxoooxxo                           ]");
		final Board sixBySixBoardBorderUnder = BoardFactory.createSixBySixBoard("[  xoooxxo                           ]");
		
		final Board unusedBoard = BoardFactory.createSixBySixBoardBlank();
		Game game = GameFactory.createGame(unusedBoard);
		MoveGeneratorOptimized moveGenerator = new MoveGeneratorOptimized(HeuristicComparatorFactory.SubtractionComparator(), BoardSetFactory.createBoardHashSetBasic(), false, true);
		
		assertFalse(moveGenerator.isImmediateTerminalPossible(sixBySixBoardBlank));
		assertFalse(moveGenerator.isImmediateTerminalPossible(sixBySixBoardBorderUnder));
		assertTrue(moveGenerator.isImmediateTerminalPossible(sixBySixBoardBorderAbove));
		assertTrue(moveGenerator.isImmediateTerminalPossible(sixBySixBoardFilled));
	}
}
