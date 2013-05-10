package search.pns.transposition;

import game.Game;
import game.GameFactory;
import junit.framework.TestCase;

import org.junit.Test;

import search.pns.ProofNumberNode;
import board.Board;
import board.BoardFactory;

public class PnsHashTTTest extends TestCase {
	
	@Test
	public void testAddAndGet() {
		final PnsHashTT hash = new PnsHashTT();
		
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final Game game = GameFactory.createGame(board);
		
		final int proofNumber = 1;
		final int disproofNumber = 2;
		final ProofNumberNode node = new ProofNumberNode(game, proofNumber, disproofNumber);
		
		assertEquals(hash.get(board, true), PnsTTResult.createNoResult());
		
		hash.upsert(node);
		
		final PnsTTResult result = hash.get(board, true);
		assertEquals(result.proofNumber, proofNumber);
		assertEquals(result.disproofNumber, disproofNumber);
	}
	
	@Test
	public void testUpsertUpdatesEntry() {
		final PnsHashTT hash = new PnsHashTT();
		
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final Game game = GameFactory.createGame(board);
		
		final int proofNumber = 1;
		final int disproofNumber = 2;
		final ProofNumberNode node = new ProofNumberNode(game, proofNumber, disproofNumber);
		
		hash.upsert(node);
		
		final int beforeUpsertSize = hash.size();
		
		final int proofNumber2 = 2;
		final int disproofNumber2 = 3;
		final ProofNumberNode node2 = new ProofNumberNode(game, proofNumber2, disproofNumber2);
		
		hash.upsert(node2);
		
		assertEquals(beforeUpsertSize, hash.size());
		final PnsTTResult result = hash.get(board, true);
		
		assertEquals(proofNumber2, result.getPhi(game));
		assertEquals(disproofNumber2, result.getDelta(game));
	}
	
	@Test
	public void testGetWithSymmetry() {
		Board board = BoardFactory.createSixBySixBoard("[xooxoxxooxoxxooxoxxooxoxxooxoxxooxox]");
		Board rotatedBoard = board.rotateNinetyDegreesClockwise();
		
		Game game = GameFactory.createGame(board);
		Game rotatedGame = GameFactory.createGame(rotatedBoard);
		
		final int phi = 100;
		final int delta = 200;
		ProofNumberNode node = new ProofNumberNode(game).setPhi(phi).setDelta(delta);
		
		PnsTT tt = new PnsHashTT();
		tt.upsert(node);
		
		PnsTTResult pnsTTResult = tt.get(rotatedBoard, true);
		
		assertEquals(phi, pnsTTResult.getPhi(rotatedGame));
		assertEquals(delta, pnsTTResult.getDelta(rotatedGame));
	}
	
	@Test
	public void testGetWithoutSymmetry() {
		Board board = BoardFactory.createSixBySixBoard("[xooxoxxooxoxxooxoxxooxoxxooxoxxooxox]");
		Board rotatedBoard = board.rotateNinetyDegreesClockwise();
		
		Game game = GameFactory.createGame(board);
		Game rotatedGame = GameFactory.createGame(rotatedBoard);
		
		final int phi = 100;
		final int delta = 200;
		ProofNumberNode node = new ProofNumberNode(game).setPhi(phi).setDelta(delta);
		
		PnsTT tt = new PnsHashTT();
		tt.upsert(node);
		
		PnsTTResult pnsTTResult = tt.get(rotatedBoard, false);
		
		assertEquals(1, pnsTTResult.getPhi(rotatedGame));
		assertEquals(1, pnsTTResult.getDelta(rotatedGame));
	}
}
