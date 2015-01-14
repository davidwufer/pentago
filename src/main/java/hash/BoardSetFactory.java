package hash;

import config.Config;


public class BoardSetFactory {
	
	public static BoardSet createBoardHashSetBasic() {
		return new BoardHashSetBasic();
	}
	
	public static BoardSet createBoardHashSetBasic(int initialSize) {
		return new BoardHashSetBasic(initialSize);
	}
	
	public static BoardSet createAlwaysFalseBoardSet() {
		return new AlwaysFalseBoardSet();
	}

	/**
	 * You will probably want to use this. NEVERMIND IT IS BUGGY
	 * @param maxSize
	 * @return
	 */
	@Deprecated
	public static BoardHashSetSpaceOptimized createBoardHashSetSpaceOptimized(int maxSize) {
		return BoardHashSetSpaceOptimized.createBoardHashSetCustom(maxSize);
	}
	
	@Deprecated
	public static BoardHashSetSpaceOptimized createBoardHashSetSpaceOptimized() {
		return BoardHashSetSpaceOptimized.createBoardHashSetCustom(Config.MAX_CHILDREN);
	}
}



