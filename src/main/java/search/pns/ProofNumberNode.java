package search.pns;

import board.Piece;
import game.Game;

public class ProofNumberNode {
	public static final int INFINITY = Integer.MAX_VALUE;
//	public static final int INFINITY = 5000;
	public static final int UNDEFINED = 1;
	
	public final Game game;
	public final Piece currPlayerPiece;
	public int proofNumber;
	public int disproofNumber;

	public ProofNumberNode(Game game, int proofNumber, int disproofNumber) {
		this.game = game;
		this.currPlayerPiece = game.getBoard().currPlayerPiece();
		this.proofNumber = proofNumber;
		this.disproofNumber = disproofNumber;
	}
	
	public ProofNumberNode(Game game) {
		this(game, UNDEFINED, UNDEFINED);
	}
	
	public static ProofNumberNode createDfPnRootNode(Game game) {
		return new ProofNumberNode(game, INFINITY, INFINITY);
	}
	
	public Game getGame() {
		return game;
	}
	
	public int getPhi() {
		return isOrNode() ? proofNumber : disproofNumber;
	}
	
	public int getDelta() {
		return isOrNode() ? disproofNumber : proofNumber;
	}
	
	public ProofNumberNode setPhi(int phi) {
		if (isOrNode()) {
			proofNumber = phi;
		} else {
			disproofNumber = phi;
		}
		return this;
	}
	
	public ProofNumberNode setDelta(int delta) {
		if (isOrNode()) {
			disproofNumber = delta;
		} else {
			proofNumber = delta;
		}
		return this;
	}
	
	public ProofNumberNode incrementPhi() {
		setPhi(getPhi() + 1);
		return this;
	}
	
	public ProofNumberNode incrementDelta() {
		setDelta(getDelta() + 1);
		return this;
	}
	
	public boolean isPhiInfinity() {
		return getPhi() == INFINITY;
	}
	
	public boolean isDeltaInfinity() {
		return getDelta() == INFINITY;
	}

	public boolean isOrNode() {
		return currPlayerPiece == Piece.X;
	}
	
	public boolean isAndNode() {
		return !isOrNode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("board: ").append(game.getBoard()).
								   append(", piece: ").append(currPlayerPiece). 
								   append(", pn: ").append(proofNumber).
								   append(", dn: ").append(disproofNumber).
								   toString();
	}

	public int getProofNumber() {
		return proofNumber;
	}

	public int getDisproofNumber() {
		return disproofNumber;
	}
	
	public ProofNumberNode setProofNumber(int pn) {
		this.proofNumber = pn;
		return this;
	}
	
	public ProofNumberNode setDisproofNumber(int dn) {
		this.disproofNumber = dn;
		return this;
	}
	
}