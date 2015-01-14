package search.pns;

import static search.pns.ProofNumberNode.INFINITY;
import static search.pns.ProofNumberNode.UNDEFINED;

import java.util.ArrayList;
import java.util.List;

import board.Board;

import game.Game;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import move.MoveGenerator;
import move.MoveGeneratorResults;
import search.pns.transposition.PnsTT;
import search.pns.transposition.PnsTTResult;

/**
 * @author dwu
 */
public class ProofNumberSearch {
	
	private final ProofNumberNode rootNode;
	private final MoveGenerator moveGenerator;
	private final PnsTT tt;
	private long numExpandedNodes;
	private long numTerminalsReached;
	
	public ProofNumberSearch(Game game, MoveGenerator moveGenerator, PnsTT tt) {
		this.rootNode = new ProofNumberNode(game, INFINITY, INFINITY);
		this.moveGenerator = moveGenerator;
		this.tt = tt;
		this.numExpandedNodes = 0L;
		this.numTerminalsReached = 0L;
	}
	
	public GameValue search() {
		mid(rootNode);
		if (rootNode.isDeltaInfinity()) {
			return GameValueFactory.getWin();
		} else {
			return GameValueFactory.getDraw();
		}
	}

	// Iterative Deepening at each node
	private void mid(ProofNumberNode node) {
		final Game game = node.getGame();
		final Board board = game.getBoard();
		final PnsTTResult result = tt.get(board, true);
		final int phi = result.getPhi(game);
		final int delta = result.getDelta(game);
		
//		System.out.println(node);
		
		
		if (node.getPhi() <= phi || node.getDelta() <= delta) {
			// Exceed thresholds
			node.setPhi(phi).setDelta(delta);
			return;
		}
		
		// Terminal node
		final GameValue gameState = game.getGameState();
		if (gameState.isTerminalValue()) {
			
			if ((node.isOrNode() && gameState.isWin()) || (node.isAndNode() && gameState.isLoss())) {
				node.setProofNumber(0).setDisproofNumber(INFINITY);
			} else {
				node.setProofNumber(INFINITY).setDisproofNumber(0);
			}
			
			
			
			// If there is a draw, we ALWAYS set the proof number value to 0
//			if (gameState.isDraw()) {
//				node.setProofNumber(0).setDisproofNumber(INFINITY);
//			} else if (gameState.isWin()) {
//				node.setPhi(0).setDelta(INFINITY);
//			} else {
//				node.setPhi(INFINITY).setDelta(0);
//			}
			tt.upsert(node);
			numTerminalsReached += 1;
			return;
		}
		
		// Expand Node
		final List<ProofNumberNode> childNodes = getChildNodes(node);
		numExpandedNodes += 1;
		
		// Store larger proof and disproof numbers to detect repetitions
		tt.upsert(node);
		
		// Iterative deepening
		final SelectChildArgument selectChildArgument = new SelectChildArgument();
		while (node.getPhi() > deltaMin(node, childNodes) && node.getDelta() > phiSum(node, childNodes)) {
			final ProofNumberNode childNode = selectChild(node, childNodes, selectChildArgument);
			
			// Update thresholds
			childNode.setPhi(
					overflowProtectedDifference(
							overflowProtectedSum(node.getDelta(), childNode.getPhi()), 
							phiSum(node, childNodes)));
			childNode.setDelta(Math.min(node.getPhi(), overflowProtectedSum(selectChildArgument.getDelta2(), 1)));
			
			mid(childNode);
		}
		
		// Store search results
		node.setPhi(deltaMin(node, childNodes)).setDelta(phiSum(node, childNodes));
		tt.upsert(node);
	}
	
	public long getNumExpandedNodes() {
		return numExpandedNodes;
	}
	
	public long getNumTerminalsReached() {
		return numTerminalsReached;
	}
	
	// Select the most promising child
	private ProofNumberNode selectChild(ProofNumberNode node, List<ProofNumberNode> childNodes, SelectChildArgument selectChildArgument) {
		ProofNumberNode bestNode = null;
		selectChildArgument.setDelta(INFINITY).setPhi(INFINITY);

		for (ProofNumberNode childNode : childNodes) {
			final Game childGame = childNode.getGame();
			final Board childBoard = childGame.getBoard();
			final PnsTTResult result = tt.get(childBoard, true);
			final int childPhi = result.getPhi(childGame);
			final int childDelta = result.getDelta(childGame);
			
			// Store the smallest and second-smallest delta in selectChildArgument.delta and selectChildArgument.delta2
			if (childDelta < selectChildArgument.getDelta()) {
				bestNode = childNode;
				selectChildArgument.setDelta2(childNode.getDelta());
				selectChildArgument.setPhi(childPhi);
				selectChildArgument.setDelta(childDelta);
			} else if (childDelta < selectChildArgument.getDelta2()) {
				selectChildArgument.setDelta2(childDelta);
			}
			
			if (childPhi == INFINITY) {
				return bestNode;
			}
		}
		return bestNode;
	}

	// Compute the smallest delta of node's children
	protected int deltaMin(ProofNumberNode parentNode, List<ProofNumberNode> childNodes) {
		int min = INFINITY;
		
		for (ProofNumberNode childNode : childNodes) {
			final Game childGame = childNode.getGame();
			final PnsTTResult result = tt.get(childGame.getBoard(), true);
			final int delta = result.getDelta(childGame);
			
			min = Math.min(min, delta);
		}
		
		return min;
	}

	// Compute sum of phi of node's children
	protected int phiSum(ProofNumberNode parentNode, List<ProofNumberNode> childNodes) {
		int sum = 0;
		
		for (ProofNumberNode childNode : childNodes) {
			final Game childGame = childNode.getGame();
			final PnsTTResult result = tt.get(childGame.getBoard(), true);
			final int phi = result.getPhi(childGame);
			
			sum = overflowProtectedSum(sum, phi);
		}
		
		return sum;
	}
	
	/**
	 * NO NEGATIVE NUMBER PARAMETERS
	 * @param a
	 * @param b
	 */
	private int overflowProtectedSum(int a, int b) {
		if (a < 0 || b < 0) {
			throw new IllegalArgumentException("negative parameters not accepted");
		}
		
		final int sum = a + b;
		return (sum < a || sum < b) ? INFINITY : sum;
	}
	
	// TODO: NOT SURE ABOUT THIS FUNCTION
	private int overflowProtectedDifference(int a, int b) {
		if (a == INFINITY && b == INFINITY) {
			throw new IllegalArgumentException("both of the arguments are infinity");
		}
		
		if (a == INFINITY) {
			return INFINITY;
		} else if (b == INFINITY) {
			return 0;
		} else {
			return a - b;
		}
	}
	
	protected List<ProofNumberNode> getChildNodes(ProofNumberNode node) {
		final List<ProofNumberNode> childNodes = new ArrayList<ProofNumberNode>();
		
		final Game game = node.getGame();
		final MoveGeneratorResults children = game.generateMoves(moveGenerator);
		for (int childIndex = 0; childIndex < children.getNumMoves(); childIndex++) {
			final Game childGame = game.doMove(children.getMove(childIndex));
			
			final ProofNumberNode childNode = new ProofNumberNode(childGame, UNDEFINED,	UNDEFINED);
			childNodes.add(childNode);
		}
		
		return childNodes;
	}
	
//	@Deprecated
//	PnsTTResult getGameValueOrLookupInTT(ProofNumberNode node) {
//		final Game game = node.getGame();
//		final GameValue gameState = game.getGameState();
//		if (gameState.isTerminalValue()) {
//			if (gameState.isWin() && node.isOrNode()) {
//				return new PnsTTResult(0, INFINITY);
//			} else {
//				return new PnsTTResult(INFINITY, 0);
//			}
//		} else {
//			return tt.get(game); 
//		}
//	}
	
	/**
	 * Data structure aggregating information for selectChild()
	 * @author David
	 *
	 */
	public class SelectChildArgument {
		private int phi;
		private int delta;
		private int delta2;
		
		public SelectChildArgument setPhi(int phi) {
			this.phi = phi;
			return this;
		}
		
		public SelectChildArgument setDelta(int delta) {
			this.delta = delta;
			return this;
		}
		
		public SelectChildArgument setDelta2(int delta2) {
			this.delta2 = delta2;
			return this;
		}
		
		public int getPhi() {
			return phi;
		}
		
		public int getDelta() {
			return delta;
		}
		
		public int getDelta2() {
			return delta2;
		}
	}
}
