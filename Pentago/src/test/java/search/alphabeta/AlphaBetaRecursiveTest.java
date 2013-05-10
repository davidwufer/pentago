package search.alphabeta;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import game.Game;
import game.GameFactory;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import hash.BoardSetFactory;
import junit.framework.TestCase;
import move.Move;
import move.MoveFactory;
import move.MoveGenerator;
import move.MoveGeneratorFactory;
import move.MoveGeneratorResults;
import movecomparator.HeuristicComparator;
import movecomparator.HeuristicComparatorFactory;

import org.mockito.MockitoAnnotations;

import search.SearchFactory;
import search.alphabeta.AlphaBetaSearch;
import search.alphabeta.transposition.BoardDatabase;
import search.alphabeta.transposition.BoardDatabaseFactory;
import search.alphabeta.transposition.BoardDatabaseResult;
import board.Board;
import board.BoardFactory;
import config.Config;

public class AlphaBetaRecursiveTest extends TestCase {
	
	@Override
	public void setUp() {
		MockitoAnnotations.initMocks(AlphaBetaRecursiveTest.class);
	}
	
	public void testOneMoveOneDepthExample() {
		final Game game = mock(Game.class);
		final Board board = mock(Board.class);
		final BoardDatabase table = mock(BoardDatabase.class);
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(HeuristicComparatorFactory.WinDrawHeuristicLossComparator(), BoardSetFactory.createBoardHashSetBasic());
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, table);
		
		doReturn(BoardDatabaseResult.NO_RESULT).when(table).contains(any(Board.class), anyBoolean());
		doReturn(true).when(table).add(any(Board.class), any(GameValue.class));
		
		when(game.doMove(any(Move.class))).thenReturn(game);
		when(game.getBoard()).thenReturn(board);
		
		when(board.toPrettyString()).thenReturn("");
		
		final int numMoves = 1;
		final Move[] stubMoves = new Move[numMoves];
		stubMoves[0] = MoveFactory.createMove(0, 1, true, GameValueFactory.getWin());
		final MoveGeneratorResults results = new MoveGeneratorResults(stubMoves, numMoves);
		
		doReturn(results).when(game).generateMoves(moveGenerator);
		
		assertEquals(GameValueFactory.getWin(), minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()));
	}
	
	public void testTwoMovesOneDepthExample() {
		final Game game = mock(Game.class);
		final Board board = mock(Board.class);
		final BoardDatabase table = mock(BoardDatabase.class);
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(HeuristicComparatorFactory.WinDrawHeuristicLossComparator(), BoardSetFactory.createBoardHashSetBasic());
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, table);
		
		doReturn(BoardDatabaseResult.NO_RESULT).when(table).contains(any(Board.class), anyBoolean());
		doReturn(true).when(table).add(any(Board.class), any(GameValue.class));
		
		when(game.doMove(any(Move.class))).thenReturn(game);
		when(game.getBoard()).thenReturn(board);
		
		when(board.toPrettyString()).thenReturn("");
		
		final int numMoves = 2;
		final Move[] stubMoves = new Move[numMoves];
		stubMoves[0] = MoveFactory.createMove(0, 1, true, GameValueFactory.getLoss());
		stubMoves[1] = MoveFactory.createMove(0, 1, true, GameValueFactory.getDraw());
		final MoveGeneratorResults results = new MoveGeneratorResults(stubMoves, numMoves);
		
		doReturn(results).when(game).generateMoves(moveGenerator);
		
		assertEquals(GameValueFactory.getDraw(), minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()));
	}
	
	public void testLossesOneDepth() {
		final Game game = mock(Game.class);
		final Board board = mock(Board.class);
		final BoardDatabase table = mock(BoardDatabase.class);
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(HeuristicComparatorFactory.WinDrawHeuristicLossComparator(), BoardSetFactory.createBoardHashSetBasic());
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, table);
		
		doReturn(BoardDatabaseResult.NO_RESULT).when(table).contains(any(Board.class), anyBoolean());
		doReturn(true).when(table).add(any(Board.class), any(GameValue.class));
		
		when(game.doMove(any(Move.class))).thenReturn(game);
		when(game.getBoard()).thenReturn(board);
		
		when(board.toPrettyString()).thenReturn("");
		
		final int numMoves = 2;
		final Move[] stubMoves = new Move[numMoves];
		stubMoves[0] = MoveFactory.createMove(0, 1, true, GameValueFactory.getLoss());
		stubMoves[1] = MoveFactory.createMove(0, 1, true, GameValueFactory.getLoss());
		final MoveGeneratorResults results = new MoveGeneratorResults(stubMoves, numMoves);
		
		doReturn(results).when(game).generateMoves(moveGenerator);
		
		assertEquals(GameValueFactory.getLoss(), minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()));
	}
	
	public void testWinLossDrawOneDepth() {
		final Game game = mock(Game.class);
		final Board board = mock(Board.class);
		final BoardDatabase table = mock(BoardDatabase.class);
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(HeuristicComparatorFactory.WinDrawHeuristicLossComparator(), BoardSetFactory.createBoardHashSetBasic());
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, table);
		
		doReturn(BoardDatabaseResult.NO_RESULT).when(table).contains(any(Board.class), anyBoolean());
		doReturn(true).when(table).add(any(Board.class), any(GameValue.class));
		
		when(game.doMove(any(Move.class))).thenReturn(game);
		when(game.getBoard()).thenReturn(board);
		
		when(board.toPrettyString()).thenReturn("");
		
		final int numMoves = 3;
		final Move[] stubMoves = new Move[numMoves];
		stubMoves[0] = MoveFactory.createMove(0, 1, true, GameValueFactory.getLoss());
		stubMoves[1] = MoveFactory.createMove(0, 1, true, GameValueFactory.getDraw());
		stubMoves[2] = MoveFactory.createMove(0, 1, true, GameValueFactory.getWin());
		final MoveGeneratorResults results = new MoveGeneratorResults(stubMoves, numMoves);
		
		doReturn(results).when(game).generateMoves(moveGenerator);
		
		assertEquals(GameValueFactory.getWin(), minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()));
	}
	
	public void testDeepPruningFromReader() {
		//game#L, # is the level of the tree starting at 0, and L is the order of the nodes at depth # from left-to-right.
		final Game game1a, game2a, game2b, game3a, game3b, game3c, game3d, game4a, game4b, game4c, game4d, game4e, game4f, game4g, game4h,
		game5a, game5b, game5c, game5d, game5e, game5f, game5g, game5h, game5i, game5j, game5k, game5l, game5m, game5n, game5o, 
		game5p;
		
		final Board board = mock(Board.class);
		
		game1a = mock(Game.class);
		game2a = mock(Game.class);
		game2b = mock(Game.class);
		game3a = mock(Game.class);
		game3b = mock(Game.class);
		game3c = mock(Game.class);
		game3d = mock(Game.class);
		game4a = mock(Game.class);
		game4b = mock(Game.class);
		game4c = mock(Game.class);
		game4d = mock(Game.class);
		game4e = mock(Game.class);
		game4f = mock(Game.class);
		game4g = mock(Game.class);
		game4h = mock(Game.class);
		game5a = mock(Game.class);
		game5b = mock(Game.class);
		game5c = mock(Game.class);
		game5d = mock(Game.class);
		game5e = mock(Game.class);
		game5f = mock(Game.class);
		game5g = mock(Game.class);
		game5h = mock(Game.class);
		game5i = mock(Game.class);
		game5j = mock(Game.class);
		game5k = mock(Game.class);
		game5l = mock(Game.class);
		game5m = mock(Game.class);
		game5n = mock(Game.class);
		game5o = mock(Game.class);
		game5p = mock(Game.class);
		
		when(game1a.getBoard()).thenReturn(board);
		when(game2a.getBoard()).thenReturn(board);
		when(game2b.getBoard()).thenReturn(board);
		when(game3a.getBoard()).thenReturn(board);
		when(game3b.getBoard()).thenReturn(board);
		when(game3c.getBoard()).thenReturn(board);
		when(game3d.getBoard()).thenReturn(board);
		when(game4a.getBoard()).thenReturn(board);
		when(game4b.getBoard()).thenReturn(board);
		when(game4c.getBoard()).thenReturn(board);
		when(game4d.getBoard()).thenReturn(board);
		when(game4e.getBoard()).thenReturn(board);
		when(game4f.getBoard()).thenReturn(board);
		when(game4g.getBoard()).thenReturn(board);
		when(game4h.getBoard()).thenReturn(board);
		when(game5a.getBoard()).thenReturn(board);
		when(game5b.getBoard()).thenReturn(board);
		when(game5c.getBoard()).thenReturn(board);
		when(game5d.getBoard()).thenReturn(board);
		when(game5e.getBoard()).thenReturn(board);
		when(game5f.getBoard()).thenReturn(board);
		when(game5g.getBoard()).thenReturn(board);
		when(game5h.getBoard()).thenReturn(board);
		when(game5i.getBoard()).thenReturn(board);
		when(game5j.getBoard()).thenReturn(board);
		when(game5k.getBoard()).thenReturn(board);
		when(game5l.getBoard()).thenReturn(board);
		when(game5m.getBoard()).thenReturn(board);
		when(game5n.getBoard()).thenReturn(board);
		when(game5o.getBoard()).thenReturn(board);
		when(game5p.getBoard()).thenReturn(board);
		
		
		/* Parent -> Child */
		when(game1a.doMove(any(Move.class))).thenReturn(game2a, game2b);
		
		when(game2a.doMove(any(Move.class))).thenReturn(game3a, game3b);
		when(game2b.doMove(any(Move.class))).thenReturn(game3c, game3d);
		
		when(game3a.doMove(any(Move.class))).thenReturn(game4a, game4b);
		when(game3b.doMove(any(Move.class))).thenReturn(game4c, game4d);
		when(game3c.doMove(any(Move.class))).thenReturn(game4e, game4f);
		when(game3d.doMove(any(Move.class))).thenReturn(game4g, game4h);
		
		when(game4a.doMove(any(Move.class))).thenReturn(game5a, game5b);
		when(game4b.doMove(any(Move.class))).thenReturn(game5c, game5d);
		when(game4c.doMove(any(Move.class))).thenReturn(game5e, game5f);
		when(game4d.doMove(any(Move.class))).thenReturn(game5g, game5h);
		when(game4e.doMove(any(Move.class))).thenReturn(game5i, game5j);
		when(game4f.doMove(any(Move.class))).thenReturn(game5k, game5l);
		when(game4g.doMove(any(Move.class))).thenReturn(game5m, game5n);
		when(game4h.doMove(any(Move.class))).thenReturn(game5o, game5p);
		
		
		/* GameValues */
		GameValue ONE = mock(GameValue.class);
		GameValue TWO = mock(GameValue.class);
		GameValue THREE = mock(GameValue.class);
		GameValue FOUR = mock(GameValue.class);
		GameValue FIVE = mock(GameValue.class);
		GameValue SIX = mock(GameValue.class);
		GameValue SEVEN = mock(GameValue.class);
		GameValue EIGHT = mock(GameValue.class);
		GameValue NINE = mock(GameValue.class);
		GameValue TEN = mock(GameValue.class);
		GameValue ELEVEN = mock(GameValue.class);
		GameValue TWELVE = mock(GameValue.class);
		GameValue THIRTEEN = mock(GameValue.class);
		GameValue FOURTEEN = mock(GameValue.class);
		GameValue FIFTEEN = mock(GameValue.class);
		
		when(ONE.getValue()).thenReturn(1);
		when(TWO.getValue()).thenReturn(2);
		when(THREE.getValue()).thenReturn(3);
		when(FOUR.getValue()).thenReturn(4);
		when(FIVE.getValue()).thenReturn(5);
		when(SIX.getValue()).thenReturn(6);
		when(SEVEN.getValue()).thenReturn(7);
		when(EIGHT.getValue()).thenReturn(8);
		when(NINE.getValue()).thenReturn(9);
		when(TEN.getValue()).thenReturn(10);
		when(ELEVEN.getValue()).thenReturn(11);
		when(TWELVE.getValue()).thenReturn(12);
		when(THIRTEEN.getValue()).thenReturn(13);
		when(FOURTEEN.getValue()).thenReturn(14);
		when(FIFTEEN.getValue()).thenReturn(15);
		
		when(ONE.isTerminalValue()).thenReturn(true);
		when(TWO.isTerminalValue()).thenReturn(true);
		when(THREE.isTerminalValue()).thenReturn(true);
		when(FOUR.isTerminalValue()).thenReturn(true);
		when(FIVE.isTerminalValue()).thenReturn(true);
		when(SIX.isTerminalValue()).thenReturn(true);
		when(SEVEN.isTerminalValue()).thenReturn(true);
		when(EIGHT.isTerminalValue()).thenReturn(true);
		when(NINE.isTerminalValue()).thenReturn(true);
		when(TEN.isTerminalValue()).thenReturn(true);
		when(ELEVEN.isTerminalValue()).thenReturn(true);
		when(TWELVE.isTerminalValue()).thenReturn(true);
		when(THIRTEEN.isTerminalValue()).thenReturn(true);
		when(FOURTEEN.isTerminalValue()).thenReturn(true);
		when(FIFTEEN.isTerminalValue()).thenReturn(true);
		
		GameValue ONE_NEGATIVE = mock(GameValue.class);
		GameValue TWO_NEGATIVE = mock(GameValue.class);
		GameValue THREE_NEGATIVE = mock(GameValue.class);
		GameValue FOUR_NEGATIVE = mock(GameValue.class);
		GameValue FIVE_NEGATIVE = mock(GameValue.class);
		GameValue SIX_NEGATIVE = mock(GameValue.class);
		GameValue SEVEN_NEGATIVE = mock(GameValue.class);
		GameValue EIGHT_NEGATIVE = mock(GameValue.class);
		GameValue NINE_NEGATIVE = mock(GameValue.class);
		GameValue TEN_NEGATIVE = mock(GameValue.class);
		GameValue ELEVEN_NEGATIVE = mock(GameValue.class);
		GameValue TWELVE_NEGATIVE = mock(GameValue.class);
		GameValue THIRTEEN_NEGATIVE = mock(GameValue.class);
		GameValue FOURTEEN_NEGATIVE = mock(GameValue.class);
		GameValue FIFTEEN_NEGATIVE = mock(GameValue.class);
		
		when(ONE_NEGATIVE.getValue()).thenReturn(-1);
		when(TWO_NEGATIVE.getValue()).thenReturn(-2);
		when(THREE_NEGATIVE.getValue()).thenReturn(-3);
		when(FOUR_NEGATIVE.getValue()).thenReturn(-4);
		when(FIVE_NEGATIVE.getValue()).thenReturn(-5);
		when(SIX_NEGATIVE.getValue()).thenReturn(-6);
		when(SEVEN_NEGATIVE.getValue()).thenReturn(-7);
		when(EIGHT_NEGATIVE.getValue()).thenReturn(-8);
		when(NINE_NEGATIVE.getValue()).thenReturn(-9);
		when(TEN_NEGATIVE.getValue()).thenReturn(-10);
		when(ELEVEN_NEGATIVE.getValue()).thenReturn(-11);
		when(TWELVE_NEGATIVE.getValue()).thenReturn(-12);
		when(THIRTEEN_NEGATIVE.getValue()).thenReturn(-13);
		when(FOURTEEN_NEGATIVE.getValue()).thenReturn(-14);
		when(FIFTEEN_NEGATIVE.getValue()).thenReturn(-15);
		
		when(ONE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(TWO_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(THREE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FOUR_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FIVE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(SIX_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(SEVEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(EIGHT_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(NINE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(TEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(ELEVEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(TWELVE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(THIRTEEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FOURTEEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FIFTEEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		
		when((ONE).oppositeGameValue()).thenReturn(ONE_NEGATIVE);
		when((TWO).oppositeGameValue()).thenReturn(TWO_NEGATIVE);
		when((THREE).oppositeGameValue()).thenReturn(THREE_NEGATIVE);
		when((FOUR).oppositeGameValue()).thenReturn(FOUR_NEGATIVE);
		when((FIVE).oppositeGameValue()).thenReturn(FIVE_NEGATIVE);
		when((SIX).oppositeGameValue()).thenReturn(SIX_NEGATIVE);
		when((SEVEN).oppositeGameValue()).thenReturn(SEVEN_NEGATIVE);
		when((EIGHT).oppositeGameValue()).thenReturn(EIGHT_NEGATIVE);
		when((NINE).oppositeGameValue()).thenReturn(NINE_NEGATIVE);
		when((TEN).oppositeGameValue()).thenReturn(TEN_NEGATIVE);
		when((ELEVEN).oppositeGameValue()).thenReturn(ELEVEN_NEGATIVE);
		when((TWELVE).oppositeGameValue()).thenReturn(TWELVE_NEGATIVE);
		when((THIRTEEN).oppositeGameValue()).thenReturn(THIRTEEN_NEGATIVE);
		when((FOURTEEN).oppositeGameValue()).thenReturn(FOURTEEN_NEGATIVE);
		when((FIFTEEN).oppositeGameValue()).thenReturn(FIFTEEN_NEGATIVE);
		
		when(ONE_NEGATIVE.oppositeGameValue()).thenReturn(ONE);
		when(TWO_NEGATIVE.oppositeGameValue()).thenReturn(TWO);
		when(THREE_NEGATIVE.oppositeGameValue()).thenReturn(THREE);
		when(FOUR_NEGATIVE.oppositeGameValue()).thenReturn(FOUR);
		when(FIVE_NEGATIVE.oppositeGameValue()).thenReturn(FIVE);
		when(SIX_NEGATIVE.oppositeGameValue()).thenReturn(SIX);
		when(SEVEN_NEGATIVE.oppositeGameValue()).thenReturn(SEVEN);
		when(EIGHT_NEGATIVE.oppositeGameValue()).thenReturn(EIGHT);
		when(NINE_NEGATIVE.oppositeGameValue()).thenReturn(NINE);
		when(TEN_NEGATIVE.oppositeGameValue()).thenReturn(TEN);
		when(ELEVEN_NEGATIVE.oppositeGameValue()).thenReturn(ELEVEN);
		when(TWELVE_NEGATIVE.oppositeGameValue()).thenReturn(TWELVE);
		when(THIRTEEN_NEGATIVE.oppositeGameValue()).thenReturn(THIRTEEN);
		when(FOURTEEN_NEGATIVE.oppositeGameValue()).thenReturn(FOURTEEN);
		when(FIFTEEN_NEGATIVE.oppositeGameValue()).thenReturn(FIFTEEN);
		
		/* GameValues end */
		
		when(game1a.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game2a.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game2b.generateMoves(any(MoveGenerator.class))).thenReturn(											
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game3a.generateMoves(any(MoveGenerator.class))).thenReturn(											
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
						
		when(game3b.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game3c.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game3d.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
					
		when(game4a.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(FOUR_NEGATIVE),
													createStubMoveWithGameValue(FIVE_NEGATIVE)));
													
		when(game4b.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(THREE_NEGATIVE),
													createStubMoveWithGameValue(TWO_NEGATIVE)));
													
		when(game4c.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(SIX_NEGATIVE),
													createStubMoveWithGameValue(SEVEN_NEGATIVE)));
		
		when(game4d.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(EIGHT_NEGATIVE),
													createStubMoveWithGameValue(NINE_NEGATIVE)));
													
		when(game4e.generateMoves(any(MoveGenerator.class))).thenReturn(													
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(ONE_NEGATIVE),
													createStubMoveWithGameValue(TEN_NEGATIVE)));
														
		when(game4f.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(TWO_NEGATIVE),
													createStubMoveWithGameValue(ELEVEN_NEGATIVE)));
													
		when(game4g.generateMoves(any(MoveGenerator.class))).thenReturn(													
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(TWELVE_NEGATIVE),
													createStubMoveWithGameValue(THIRTEEN_NEGATIVE)));
																
		when(game4h.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(FOURTEEN_NEGATIVE),
													createStubMoveWithGameValue(FIFTEEN_NEGATIVE)));
		
		final BoardDatabase table = mock(BoardDatabase.class);
		final MoveGenerator moveGenerator = mock(MoveGenerator.class);
		
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game1a, moveGenerator, table);
		
		doReturn(BoardDatabaseResult.NO_RESULT).when(table).contains(any(Board.class), anyBoolean());
		doReturn(true).when(table).add(any(Board.class), any(GameValue.class));
		
		when(board.toPrettyString()).thenReturn("");
		
		assertEquals(4, minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()).getValue());
	}
	
	public void testNoPruningFromReader() {
		//game#L, # is the level of the tree starting at 0, and L is the order of the nodes at depth # from left-to-right.
		final Game game1a, game2a, game2b, game3a, game3b, game3c, game3d, game4a, game4b, game4c, game4d, game4e, game4f, game4g, game4h,
		game5a, game5b, game5c, game5d, game5e, game5f, game5g, game5h, game5i, game5j, game5k, game5l, game5m, game5n, game5o, 
		game5p;
		
		final Board board = mock(Board.class);
		
		game1a = mock(Game.class);
		game2a = mock(Game.class);
		game2b = mock(Game.class);
		game3a = mock(Game.class);
		game3b = mock(Game.class);
		game3c = mock(Game.class);
		game3d = mock(Game.class);
		game4a = mock(Game.class);
		game4b = mock(Game.class);
		game4c = mock(Game.class);
		game4d = mock(Game.class);
		game4e = mock(Game.class);
		game4f = mock(Game.class);
		game4g = mock(Game.class);
		game4h = mock(Game.class);
		game5a = mock(Game.class);
		game5b = mock(Game.class);
		game5c = mock(Game.class);
		game5d = mock(Game.class);
		game5e = mock(Game.class);
		game5f = mock(Game.class);
		game5g = mock(Game.class);
		game5h = mock(Game.class);
		game5i = mock(Game.class);
		game5j = mock(Game.class);
		game5k = mock(Game.class);
		game5l = mock(Game.class);
		game5m = mock(Game.class);
		game5n = mock(Game.class);
		game5o = mock(Game.class);
		game5p = mock(Game.class);
		
		when(game1a.getBoard()).thenReturn(board);
		when(game2a.getBoard()).thenReturn(board);
		when(game2b.getBoard()).thenReturn(board);
		when(game3a.getBoard()).thenReturn(board);
		when(game3b.getBoard()).thenReturn(board);
		when(game3c.getBoard()).thenReturn(board);
		when(game3d.getBoard()).thenReturn(board);
		when(game4a.getBoard()).thenReturn(board);
		when(game4b.getBoard()).thenReturn(board);
		when(game4c.getBoard()).thenReturn(board);
		when(game4d.getBoard()).thenReturn(board);
		when(game4e.getBoard()).thenReturn(board);
		when(game4f.getBoard()).thenReturn(board);
		when(game4g.getBoard()).thenReturn(board);
		when(game4h.getBoard()).thenReturn(board);
		when(game5a.getBoard()).thenReturn(board);
		when(game5b.getBoard()).thenReturn(board);
		when(game5c.getBoard()).thenReturn(board);
		when(game5d.getBoard()).thenReturn(board);
		when(game5e.getBoard()).thenReturn(board);
		when(game5f.getBoard()).thenReturn(board);
		when(game5g.getBoard()).thenReturn(board);
		when(game5h.getBoard()).thenReturn(board);
		when(game5i.getBoard()).thenReturn(board);
		when(game5j.getBoard()).thenReturn(board);
		when(game5k.getBoard()).thenReturn(board);
		when(game5l.getBoard()).thenReturn(board);
		when(game5m.getBoard()).thenReturn(board);
		when(game5n.getBoard()).thenReturn(board);
		when(game5o.getBoard()).thenReturn(board);
		when(game5p.getBoard()).thenReturn(board);
		
		/* Parent -> Child */
		when(game1a.doMove(any(Move.class))).thenReturn(game2a, game2b);
		
		when(game2a.doMove(any(Move.class))).thenReturn(game3a, game3b);
		when(game2b.doMove(any(Move.class))).thenReturn(game3c, game3d);
		
		when(game3a.doMove(any(Move.class))).thenReturn(game4a, game4b);
		when(game3b.doMove(any(Move.class))).thenReturn(game4c, game4d);
		when(game3c.doMove(any(Move.class))).thenReturn(game4e, game4f);
		when(game3d.doMove(any(Move.class))).thenReturn(game4g, game4h);
		
		when(game4a.doMove(any(Move.class))).thenReturn(game5a, game5b);
		when(game4b.doMove(any(Move.class))).thenReturn(game5c, game5d);
		when(game4c.doMove(any(Move.class))).thenReturn(game5e, game5f);
		when(game4d.doMove(any(Move.class))).thenReturn(game5g, game5h);
		when(game4e.doMove(any(Move.class))).thenReturn(game5i, game5j);
		when(game4f.doMove(any(Move.class))).thenReturn(game5k, game5l);
		when(game4g.doMove(any(Move.class))).thenReturn(game5m, game5n);
		when(game4h.doMove(any(Move.class))).thenReturn(game5o, game5p);
		
		/* GameValues */
		GameValue ONE = mock(GameValue.class);
		GameValue TWO = mock(GameValue.class);
		GameValue THREE = mock(GameValue.class);
		GameValue FOUR = mock(GameValue.class);
		GameValue FIVE = mock(GameValue.class);
		GameValue SIX = mock(GameValue.class);
		GameValue SEVEN = mock(GameValue.class);
		GameValue EIGHT = mock(GameValue.class);
		GameValue NINE = mock(GameValue.class);
		GameValue TEN = mock(GameValue.class);
		GameValue ELEVEN = mock(GameValue.class);
		GameValue TWELVE = mock(GameValue.class);
		GameValue THIRTEEN = mock(GameValue.class);
		GameValue FOURTEEN = mock(GameValue.class);
		GameValue FIFTEEN = mock(GameValue.class);
		
		when(ONE.getValue()).thenReturn(1);
		when(TWO.getValue()).thenReturn(2);
		when(THREE.getValue()).thenReturn(3);
		when(FOUR.getValue()).thenReturn(4);
		when(FIVE.getValue()).thenReturn(5);
		when(SIX.getValue()).thenReturn(6);
		when(SEVEN.getValue()).thenReturn(7);
		when(EIGHT.getValue()).thenReturn(8);
		when(NINE.getValue()).thenReturn(9);
		when(TEN.getValue()).thenReturn(10);
		when(ELEVEN.getValue()).thenReturn(11);
		when(TWELVE.getValue()).thenReturn(12);
		when(THIRTEEN.getValue()).thenReturn(13);
		when(FOURTEEN.getValue()).thenReturn(14);
		when(FIFTEEN.getValue()).thenReturn(15);
		
		when(ONE.isTerminalValue()).thenReturn(true);
		when(TWO.isTerminalValue()).thenReturn(true);
		when(THREE.isTerminalValue()).thenReturn(true);
		when(FOUR.isTerminalValue()).thenReturn(true);
		when(FIVE.isTerminalValue()).thenReturn(true);
		when(SIX.isTerminalValue()).thenReturn(true);
		when(SEVEN.isTerminalValue()).thenReturn(true);
		when(EIGHT.isTerminalValue()).thenReturn(true);
		when(NINE.isTerminalValue()).thenReturn(true);
		when(TEN.isTerminalValue()).thenReturn(true);
		when(ELEVEN.isTerminalValue()).thenReturn(true);
		when(TWELVE.isTerminalValue()).thenReturn(true);
		when(THIRTEEN.isTerminalValue()).thenReturn(true);
		when(FOURTEEN.isTerminalValue()).thenReturn(true);
		when(FIFTEEN.isTerminalValue()).thenReturn(true);
		
		GameValue ONE_NEGATIVE = mock(GameValue.class);
		GameValue TWO_NEGATIVE = mock(GameValue.class);
		GameValue THREE_NEGATIVE = mock(GameValue.class);
		GameValue FOUR_NEGATIVE = mock(GameValue.class);
		GameValue FIVE_NEGATIVE = mock(GameValue.class);
		GameValue SIX_NEGATIVE = mock(GameValue.class);
		GameValue SEVEN_NEGATIVE = mock(GameValue.class);
		GameValue EIGHT_NEGATIVE = mock(GameValue.class);
		GameValue NINE_NEGATIVE = mock(GameValue.class);
		GameValue TEN_NEGATIVE = mock(GameValue.class);
		GameValue ELEVEN_NEGATIVE = mock(GameValue.class);
		GameValue TWELVE_NEGATIVE = mock(GameValue.class);
		GameValue THIRTEEN_NEGATIVE = mock(GameValue.class);
		GameValue FOURTEEN_NEGATIVE = mock(GameValue.class);
		GameValue FIFTEEN_NEGATIVE = mock(GameValue.class);
		
		when(ONE_NEGATIVE.getValue()).thenReturn(-1);
		when(TWO_NEGATIVE.getValue()).thenReturn(-2);
		when(THREE_NEGATIVE.getValue()).thenReturn(-3);
		when(FOUR_NEGATIVE.getValue()).thenReturn(-4);
		when(FIVE_NEGATIVE.getValue()).thenReturn(-5);
		when(SIX_NEGATIVE.getValue()).thenReturn(-6);
		when(SEVEN_NEGATIVE.getValue()).thenReturn(-7);
		when(EIGHT_NEGATIVE.getValue()).thenReturn(-8);
		when(NINE_NEGATIVE.getValue()).thenReturn(-9);
		when(TEN_NEGATIVE.getValue()).thenReturn(-10);
		when(ELEVEN_NEGATIVE.getValue()).thenReturn(-11);
		when(TWELVE_NEGATIVE.getValue()).thenReturn(-12);
		when(THIRTEEN_NEGATIVE.getValue()).thenReturn(-13);
		when(FOURTEEN_NEGATIVE.getValue()).thenReturn(-14);
		when(FIFTEEN_NEGATIVE.getValue()).thenReturn(-15);
		
		when(ONE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(TWO_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(THREE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FOUR_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FIVE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(SIX_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(SEVEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(EIGHT_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(NINE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(TEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(ELEVEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(TWELVE_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(THIRTEEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FOURTEEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		when(FIFTEEN_NEGATIVE.isTerminalValue()).thenReturn(true);
		
		when((ONE).oppositeGameValue()).thenReturn(ONE_NEGATIVE);
		when((TWO).oppositeGameValue()).thenReturn(TWO_NEGATIVE);
		when((THREE).oppositeGameValue()).thenReturn(THREE_NEGATIVE);
		when((FOUR).oppositeGameValue()).thenReturn(FOUR_NEGATIVE);
		when((FIVE).oppositeGameValue()).thenReturn(FIVE_NEGATIVE);
		when((SIX).oppositeGameValue()).thenReturn(SIX_NEGATIVE);
		when((SEVEN).oppositeGameValue()).thenReturn(SEVEN_NEGATIVE);
		when((EIGHT).oppositeGameValue()).thenReturn(EIGHT_NEGATIVE);
		when((NINE).oppositeGameValue()).thenReturn(NINE_NEGATIVE);
		when((TEN).oppositeGameValue()).thenReturn(TEN_NEGATIVE);
		when((ELEVEN).oppositeGameValue()).thenReturn(ELEVEN_NEGATIVE);
		when((TWELVE).oppositeGameValue()).thenReturn(TWELVE_NEGATIVE);
		when((THIRTEEN).oppositeGameValue()).thenReturn(THIRTEEN_NEGATIVE);
		when((FOURTEEN).oppositeGameValue()).thenReturn(FOURTEEN_NEGATIVE);
		when((FIFTEEN).oppositeGameValue()).thenReturn(FIFTEEN_NEGATIVE);
		
		when(ONE_NEGATIVE.oppositeGameValue()).thenReturn(ONE);
		when(TWO_NEGATIVE.oppositeGameValue()).thenReturn(TWO);
		when(THREE_NEGATIVE.oppositeGameValue()).thenReturn(THREE);
		when(FOUR_NEGATIVE.oppositeGameValue()).thenReturn(FOUR);
		when(FIVE_NEGATIVE.oppositeGameValue()).thenReturn(FIVE);
		when(SIX_NEGATIVE.oppositeGameValue()).thenReturn(SIX);
		when(SEVEN_NEGATIVE.oppositeGameValue()).thenReturn(SEVEN);
		when(EIGHT_NEGATIVE.oppositeGameValue()).thenReturn(EIGHT);
		when(NINE_NEGATIVE.oppositeGameValue()).thenReturn(NINE);
		when(TEN_NEGATIVE.oppositeGameValue()).thenReturn(TEN);
		when(ELEVEN_NEGATIVE.oppositeGameValue()).thenReturn(ELEVEN);
		when(TWELVE_NEGATIVE.oppositeGameValue()).thenReturn(TWELVE);
		when(THIRTEEN_NEGATIVE.oppositeGameValue()).thenReturn(THIRTEEN);
		when(FOURTEEN_NEGATIVE.oppositeGameValue()).thenReturn(FOURTEEN);
		when(FIFTEEN_NEGATIVE.oppositeGameValue()).thenReturn(FIFTEEN);
		
		/* GameValues end */
		
		when(game1a.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game2a.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game2b.generateMoves(any(MoveGenerator.class))).thenReturn(											
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game3a.generateMoves(any(MoveGenerator.class))).thenReturn(											
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
						
		when(game3b.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game3c.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
		
		when(game3d.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(GameValueFactory.getUndetermined()),
													createStubMoveWithGameValue(GameValueFactory.getUndetermined())));
					
		when(game4a.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(FOURTEEN_NEGATIVE),
													createStubMoveWithGameValue(FIFTEEN_NEGATIVE)));
													
		when(game4b.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(TWELVE_NEGATIVE),
													createStubMoveWithGameValue(THIRTEEN_NEGATIVE)));
													
		when(game4c.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(TWO_NEGATIVE),
													createStubMoveWithGameValue(ELEVEN_NEGATIVE)));
		
		when(game4d.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(ONE_NEGATIVE),
													createStubMoveWithGameValue(TEN_NEGATIVE)));
													
		when(game4e.generateMoves(any(MoveGenerator.class))).thenReturn(													
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(EIGHT_NEGATIVE),
													createStubMoveWithGameValue(NINE_NEGATIVE)));
														
		when(game4f.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(SIX_NEGATIVE),
													createStubMoveWithGameValue(SEVEN_NEGATIVE)));
													
		when(game4g.generateMoves(any(MoveGenerator.class))).thenReturn(													
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(THREE_NEGATIVE),
													createStubMoveWithGameValue(TWO_NEGATIVE)));
																
		when(game4h.generateMoves(any(MoveGenerator.class))).thenReturn(
				createMoveGeneratorResultsFromMoves(createStubMoveWithGameValue(FOUR_NEGATIVE),
													createStubMoveWithGameValue(FIVE_NEGATIVE)));
		
		final BoardDatabase table = mock(BoardDatabase.class);
//		final Game game = mock(Game.class);
		final MoveGenerator moveGenerator = mock(MoveGenerator.class);
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game1a, moveGenerator, table);
		
		doReturn(BoardDatabaseResult.NO_RESULT).when(table).contains(any(Board.class), anyBoolean());
		doReturn(true).when(table).add(any(Board.class), any(GameValue.class));
		
		assertEquals(4, minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()).getValue());
	}
	
	private MoveGeneratorResults createMoveGeneratorResultsFromMoves(Move ... moves ) {
		return new MoveGeneratorResults(moves, moves.length);
	}
	
	private Move createStubMoveWithGameValue(GameValue resultingGameValue) {
		return MoveFactory.createMove(0, 0, true, resultingGameValue);
	}
	
	
	/* NON-MOCKED */
	
	public void testAlphaBetaOnFourByFourBlankBoard() {
		Game game = GameFactory.createGame(BoardFactory.createFourByFourBoardBlank());
//		TranspositionTable table = TranspositionTableFactory.createAlwaysFalseTT();
//		TranspositionTable table = TranspositionTableFactory.createHashMapTT();
//		final TranspositionTable table = TranspositionTableFactory.createTwoTierTT(Config.TTSIZE);
		final BoardDatabase table = BoardDatabaseFactory.createTwoTierTT(Config.TTSIZE_TEST);
		final HeuristicComparator heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, BoardSetFactory.createBoardHashSetBasic());
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, table);
		
		assertEquals(GameValueFactory.getWin(), minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()));
	}
	
	public void testAlphaBetaOnBoardLosingAfterTwoDepths() {
		final Game game = GameFactory.createGame(BoardFactory.createFourByFourBoard("[xx o            ]"));
		final BoardDatabase table = BoardDatabaseFactory.createAlwaysFalseTT();
//		TranspositionTable table = TranspositionTableFactory.createHashMapTT();
		final HeuristicComparator heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, BoardSetFactory.createBoardHashSetBasic());
		final AlphaBetaSearch minimax = SearchFactory.createAlphaBetaRecursive(game, moveGenerator, table);
		
		assertEquals(GameValueFactory.getLoss(), minimax.runSearch(GameValueFactory.getLoss(), GameValueFactory.getWin()));
	}
}
