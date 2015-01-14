package search.pns;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static search.pns.ProofNumberNode.UNDEFINED;
import game.Game;
import game.GameFactory;
import gamevalue.GameValueFactory;
import hash.BoardSet;
import hash.BoardSetFactory;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import move.MoveGenerator;
import move.MoveGeneratorFactory;
import movecomparator.HeuristicComparator;
import movecomparator.HeuristicComparatorFactory;
import search.pns.transposition.PnsHashTT;
import search.pns.transposition.PnsTT;
import search.pns.transposition.PnsTTResult;
import board.Board;
import board.BoardFactory;
import board.Piece;


public class ProofNumberSearchTest extends TestCase {
	
	/* Search-specific tests */
	public void testPNSOnFourByFourBlankBoard() {
		final Game game = GameFactory.createGame(BoardFactory.createFourByFourBoardBlank());
		
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
		final HeuristicComparator heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, boardSet);
		final PnsTT tt = new PnsHashTT();
		final ProofNumberSearch search = new ProofNumberSearch(game, moveGenerator, tt);
		
		assertEquals(GameValueFactory.getWin(), search.search());
		System.out.println(search.getNumExpandedNodes());
	}
	
	public void testPNSOnFourByFourBoardEndingInLossIsDraw() {
		final Game game = GameFactory.createGame(BoardFactory.createFourByFourBoard("[xx o            ]"));
		
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
		final HeuristicComparator heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, boardSet);
		final PnsTT tt = new PnsHashTT();
		final ProofNumberSearch search = new ProofNumberSearch(game, moveGenerator, tt);
		
		// Not really a draw, but it is not a win, so it's a draw
		assertEquals("Not a win should be a draw", GameValueFactory.getDraw(), search.search());
	}
	
	public void testPNSOnFourByFourBoardImmediateWin() {
		final Game game = GameFactory.createGame(BoardFactory.createFourByFourBoard("[xx      oo      ]"));
		
		final BoardSet boardSet = BoardSetFactory.createBoardHashSetBasic();
		final HeuristicComparator heuristicComparator = HeuristicComparatorFactory.WinDrawHeuristicLossComparator();
		final MoveGenerator moveGenerator = MoveGeneratorFactory.createMoveGeneratorOptimized(heuristicComparator, boardSet);
		final PnsTT tt = new PnsHashTT();
		final ProofNumberSearch search = new ProofNumberSearch(game, moveGenerator, tt);
		
		// Not really a draw, but it is not a win, so it's a draw
		assertEquals("Immediate Win should be a win", GameValueFactory.getWin(), search.search());
	}
	
	/* Testing more specific functions */
	/* This test is the epitome of nastiness */
	public void testDeltaMin() {
		// Set up arguments for search
		final Game game = mock(Game.class);
		final Board board = mock(Board.class);
		
		doReturn(board).when(game).getBoard();
		doReturn(Piece.X).when(board).currPlayerPiece();
		
		final MoveGenerator moveGenerator = mock(MoveGenerator.class);
		final PnsTT tt = spy(new PnsHashTT());
		final ProofNumberSearch search = spy(new ProofNumberSearch(game, moveGenerator, tt));
		
		// Set up arguments for deltaMin
		final ProofNumberNode parentNode = spy(new ProofNumberNode(game, UNDEFINED, UNDEFINED));
		doReturn(game).when(parentNode).getGame();

		final ProofNumberNode node1 = mock(ProofNumberNode.class);
		final ProofNumberNode node2 = mock(ProofNumberNode.class);
		
		final Game game1 = mock(Game.class);
		final Game game2 = mock(Game.class);
		
		final int delta1 = 10;
		final int delta2 = 2;
		
		final PnsTTResult result1 = mock(PnsTTResult.class);
		final PnsTTResult result2 = mock(PnsTTResult.class);
		
		final Board board1 = mock(Board.class);
		final Board board2 = mock(Board.class);
		
		doReturn(board1).when(game1).getBoard();
		doReturn(board2).when(game2).getBoard();
		
		doReturn(game1).when(node1).getGame();
		doReturn(game2).when(node2).getGame();
		
		doReturn(delta1).when(result1).getDelta(game1);
		doReturn(delta2).when(result2).getDelta(game2);
		
		doReturn(result1).when(tt).get(board1, true);
		doReturn(result2).when(tt).get(board2, true);
		
		final List<ProofNumberNode> childNodes = new ArrayList<ProofNumberNode>();
		childNodes.add(node1);
		childNodes.add(node2);
		
		assertEquals(delta2, search.deltaMin(parentNode, childNodes));
	}
	
	/* This test is also the epitome of nastiness */
	public void testPhiSum() {
		// Set up arguments for search
		final Game game = mock(Game.class);
		final Board board = mock(Board.class);
		
		doReturn(board).when(game).getBoard();
		doReturn(Piece.X).when(board).currPlayerPiece();
		
		final MoveGenerator moveGenerator = mock(MoveGenerator.class);
		final PnsTT tt = spy(new PnsHashTT());
		final ProofNumberSearch search = spy(new ProofNumberSearch(game, moveGenerator, tt));
		
		// Set up arguments for deltaMin
		final ProofNumberNode parentNode = spy(new ProofNumberNode(game, UNDEFINED, UNDEFINED));
		doReturn(game).when(parentNode).getGame();

		final ProofNumberNode node1 = mock(ProofNumberNode.class);
		final ProofNumberNode node2 = mock(ProofNumberNode.class);
		
		final Game game1 = mock(Game.class);
		final Game game2 = mock(Game.class);
		
		final int phi1 = 10;
		final int phi2 = 2;
		
		final PnsTTResult result1 = mock(PnsTTResult.class);
		final PnsTTResult result2 = mock(PnsTTResult.class);
		
		final Board board1 = mock(Board.class);
		final Board board2 = mock(Board.class);
		
		doReturn(board1).when(game1).getBoard();
		doReturn(board2).when(game2).getBoard();
		
		doReturn(phi1).when(result1).getPhi(game1);
		doReturn(phi2).when(result2).getPhi(game2);
		
		doReturn(game1).when(node1).getGame();
		doReturn(game2).when(node2).getGame();
		
		doReturn(result1).when(tt).get(board1, true);
		doReturn(result2).when(tt).get(board2, true);
		
		final List<ProofNumberNode> childNodes = new ArrayList<ProofNumberNode>();
		childNodes.add(node1);
		childNodes.add(node2);
		
		assertEquals(phi1 + phi2, search.phiSum(parentNode, childNodes));
	}
}

