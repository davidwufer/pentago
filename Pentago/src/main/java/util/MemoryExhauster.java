package util;

import gamevalue.GameValueFactory;
import search.alphabeta.transposition.TwoTierTranspositionTable;
import board.BoardFactory;

public class MemoryExhauster {

//	public static void main(String[] args) {
//		System.out.println("Total Memory: "+Runtime.getRuntime().totalMemory());    
//	    System.out.println("Free Memory: "+Runtime.getRuntime().freeMemory());
//	    
//	    System.out.println("Free Memory: "+Runtime.getRuntime().freeMemory());
//	}
	
	public static int getMaxTableSize() {
		try {
		    // create lots of objects here and stash them somewhere
			final int size = 100000;
		    TwoTierTranspositionTable tt = TwoTierTranspositionTable.createTwoTierTranspositionTable(size);
		    for (int loopTimes = 0; loopTimes < size * 2; loopTimes++) {	    	
		    	tt.add(BoardFactory.createSixBySixBoardRandom(), GameValueFactory.getWin());
		    }
		} catch (OutOfMemoryError E) {
		    // release some (all) of the above objects
			System.out.println("We've escaped");
		}
		return 0;
	}
}
