package search.pns.transposition;

import search.pns.ProofNumberNode;
import board.Board;

public interface PnsTT {

	public void upsert(ProofNumberNode node);
	
	/**
	 * We use a game because we'll need to generate moves to remove children that are in the TT (for some implementations)
	 * If no result is found, return PnsTTResult.getNoResult()
	 */
	public PnsTTResult get(Board board, boolean includeSymmetries);
}
