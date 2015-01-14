package search.pns.transposition;

import java.util.HashMap;
import java.util.Map;

import search.pns.ProofNumberNode;
import board.Board;
import board.BoardArray;

public class PnsHashTT extends AbstractPnsTT {
	final Map<BoardArray, PnsTTResult> tt;
	
	public PnsHashTT() {
		tt = new HashMap<BoardArray, PnsTTResult>();
	}
	
	@Override
	public PnsTTResult get(Board board, boolean includeSymmetries) {
		final BoardArray boardArray = board.getBoardArray();
		if (tt.containsKey(boardArray)) {
			return tt.get(boardArray);
		} else if (includeSymmetries) {
			return PnsSymmetryChecker.contains(board, this);
		} else {
			return PnsTTResult.createNoResult();
		}
	}
	
	@Override
	public void upsert(ProofNumberNode node) {
		final BoardArray boardArray = node.getGame().getBoard().getBoardArray();
		final int proofNumber = node.getProofNumber();
		final int disproofNumber = node.getDisproofNumber();
		
		final PnsTTResult result = new PnsTTResult(proofNumber, disproofNumber);
		tt.put(boardArray, result);
	}
	
	protected Map<BoardArray, PnsTTResult> getInternalHash() {
		return tt;
	}
	
	protected int size() {
		return tt.size();
	}
	
	private static class PnsSymmetryChecker {
		public static PnsTTResult contains(Board board, PnsTT tt) {
			PnsTTResult ttResult;
			
			ttResult = tt.get(board.flipOverX(), false);
			if (ttResult.isResultFound()) { return ttResult; }

			ttResult = tt.get(board.flipOverY(), false);
			if (ttResult.isResultFound()) { return ttResult; }
				
			board.flipOverBottomLeftToTopRightDiag();
			ttResult = tt.get(board.flipOverBottomLeftToTopRightDiag(), false);
			if (ttResult.isResultFound()) { return ttResult; }
			
			ttResult = tt.get(board.flipOverTopLeftToBottomRightDiag(), false);
			if (ttResult.isResultFound()) { return ttResult; }
			
			final Board boardRotatedOnce = board.rotateNinetyDegreesClockwise();
			ttResult = tt.get(boardRotatedOnce, false);
			if (ttResult.isResultFound()) { return ttResult; }
			
			final Board boardRotatedTwice = boardRotatedOnce.rotateNinetyDegreesClockwise();
			ttResult = tt.get(boardRotatedTwice, false);
			if (ttResult.isResultFound()) { return ttResult; }
			
			final Board boardRotatedThrice = boardRotatedTwice.rotateNinetyDegreesClockwise();
			ttResult = tt.get(boardRotatedThrice, false);
			if (ttResult.isResultFound()) { return ttResult; }
			
			return PnsTTResult.createNoResult();
		}
	}
}