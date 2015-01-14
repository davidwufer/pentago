package move;

/* TODO: maybe make this iterable? */
public class MoveGeneratorResults {
	final private Move[] generatedMoves;
	final private int numMoves;
	
	public MoveGeneratorResults(Move[] generatedMoves, int numMoves) {
		this.generatedMoves = generatedMoves;
		this.numMoves = numMoves;
	}

	public Move[] getGeneratedMoves() {
		return generatedMoves;
	}

	public int getNumMoves() {
		return numMoves;
	}

	public Move getMove(int childIndex) {
		return generatedMoves[childIndex];
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("# moves: ").append(numMoves).toString();
	}
}
