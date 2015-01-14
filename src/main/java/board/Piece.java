package board;

public enum Piece {
	  BLANK ((byte) 0),
    	  X ((byte) 1),
	      O ((byte) 2),
	INVALID ((byte) 3);
	
	private final byte val;
	private static Piece[] lookupTable;
	
	static {
		final Piece[] values = Piece.values();
		lookupTable = new Piece[values.length];
		for (Piece p : values) {
			lookupTable[p.val] = p;
		}
	}
	
	public char toChar() {
		switch (this) {
			case BLANK: return ' ';
			case     X: return 'x';
			case     O: return 'o';
			default: throw new RuntimeException("Character has no printout: " + this);
		}
	}
	
	public static Piece fromChar(char c) {
		switch (c) {
			case ' ': return BLANK;
			case 'x': return     X;
			case 'o': return     O;
			default : throw new RuntimeException("Bad character passed into fromChar: " + c);
		}
	}
	
	private Piece(byte val) {
		this.val = val;
	}
	
	public static byte valueOf(Piece p) {
		return p.val;
	}
	
	public int toInt() {
		return this.val;
	}
	
	public static Piece getPieceFromVal(byte val) {
		return lookupTable[val];
	}
}
