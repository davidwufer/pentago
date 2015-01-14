package search.alphabeta.transposition;




public class BoardDatabaseFactory {
	
	public static BoardDatabase createHashMapTT() {
		return new HashMapTranspositionTable();
	}
	
	public static BoardDatabase createAlwaysFalseTT() {
		return new AlwaysFalseTranspositionTable();
	}
	
	public static BoardDatabase createTwoTierTT(final int size) {
		return TwoTierTranspositionTable.createTwoTierTranspositionTable(size);
	}
	
	public static BoardDatabase createHashMapEndGameDatabaseBoard(final int depth) {
		return new HashMapEndGameDatabaseBoard(depth);
	}
}
