package search.pns.transposition;

import game.Game;
import board.Piece;


public class PnsTTResult {
	
	public int proofNumber;
	public int disproofNumber;
	
	public PnsTTResult(int proofNumber, int disproofNumber) {
		this.proofNumber = proofNumber;
		this.disproofNumber = disproofNumber;
	}
	
	/**
	 * Returns a new PnsTTResult since the values inside might change
	 * TODO: This is worth unit testing!
	 * @return
	 */
	public static PnsTTResult createNoResult() {
		return new PnsTTResult(1, 1);
	}
	
	public int getPhi(Game game) {
		return isMaxNode(game) ? proofNumber : disproofNumber;
	}
	
	public int getDelta(Game game) {
		return isMaxNode(game) ? disproofNumber : proofNumber;
	}
	
	public boolean isResultFound() {
		return proofNumber != 1 || disproofNumber != 1;
	}
	
	public PnsTTResult setPhi(Game game, int phi) {
		if (isMaxNode(game)) {
			proofNumber = phi;
		} else {
			disproofNumber = phi;
		}
		return this;
	}
	
	public PnsTTResult setDelta(Game game, int delta) {
		if (isMaxNode(game)) {
			disproofNumber = delta;
		} else {
			proofNumber = delta;
		}
		return this;
	}
	
	private boolean isMaxNode(Game game) {
		final Piece piece = game.getBoard().currPlayerPiece();
		return piece == Piece.X;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + disproofNumber;
		result = prime * result + proofNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PnsTTResult other = (PnsTTResult) obj;
		if (disproofNumber != other.disproofNumber)
			return false;
		if (proofNumber != other.proofNumber)
			return false;
		return true;
	}
	
	
}