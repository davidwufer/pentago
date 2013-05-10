package search.pns;

import static search.pns.ProofNumberNode.UNDEFINED;
import game.Game;
import game.GameFactory;
import junit.framework.TestCase;

import org.junit.Test;

import search.pns.ProofNumberNode;



import board.Board;
import board.BoardFactory;

/**
 * Test casea for ProofNumberNode.java
 * @author David
 *
 */
public class ProofNumberNodeTest extends TestCase {

	@Test
	public void testIsOrNodeTrue() {
		final ProofNumberNode node = createOrNode(UNDEFINED, UNDEFINED);
		
		assertTrue("The empty board should always be an OR node", node.isOrNode()); 
	}
	
	@Test
	public void testIsOrNodeFalse() {
		final ProofNumberNode node = createAndNode(UNDEFINED, UNDEFINED);
		
		assertFalse("A board with an odd number of pieces should not be an OR node (but an AND node", node.isOrNode()); 
	}
	
	@Test
	public void testGetPhiOrNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createOrNode(proofNumber, disproofNumber);
		
		assertEquals(proofNumber, node.getPhi());
	}
	
	@Test
	public void testGetPhiAndNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createAndNode(proofNumber, disproofNumber);
		
		assertEquals(disproofNumber, node.getPhi());
	}
	
	@Test
	public void testGetDeltaOrNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createOrNode(proofNumber, disproofNumber);
		
		assertEquals(disproofNumber, node.getDelta());
	}
	
	@Test
	public void testGetDeltaAndNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createAndNode(proofNumber, disproofNumber);
		
		assertEquals(proofNumber, node.getDelta());
	}
	
	@Test
	public void testSetPhiOrNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createOrNode(proofNumber, disproofNumber);
		
		final int newPhi = 100;
		node.setPhi(newPhi);
		
		assertEquals(newPhi, node.getPhi());
	}
	
	@Test
	public void testSetPhiAndNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createAndNode(proofNumber, disproofNumber);
		
		final int newPhi = 100;
		node.setPhi(newPhi);
		
		assertEquals(newPhi, node.getPhi());
	}
	
	@Test
	public void testSetDeltaOrNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createOrNode(proofNumber, disproofNumber);
		
		final int newDelta = 100;
		node.setDelta(newDelta);
		
		assertEquals(newDelta, node.getDelta());
	}
	
	@Test
	public void testSetDeltaAndNode() {
		final int proofNumber = 10;
		final int disproofNumber = 0;
		final ProofNumberNode node = createAndNode(proofNumber, disproofNumber);
		
		final int newDelta = 100;
		node.setDelta(newDelta);
		
		assertEquals(newDelta, node.getDelta());
	}
	
	private ProofNumberNode createOrNode(int proofNumber, int disproofNumber) {
		final Board board = BoardFactory.createSixBySixBoardBlank();
		final Game game = GameFactory.createGame(board);
		final ProofNumberNode node = new ProofNumberNode(game, proofNumber, disproofNumber);
		return node;
	}
	
	private ProofNumberNode createAndNode(int proofNumber, int disproofNumber) {
		final Board board = BoardFactory.createSixBySixBoard("[      x                             ]");
		final Game game = GameFactory.createGame(board);
		final ProofNumberNode node = new ProofNumberNode(game, proofNumber, disproofNumber);
		return node;
	}
}
