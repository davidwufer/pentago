package transposition;

import board.Board;

public class TranspositionSymmetryChecker {

	// This doesn't need to check the unmutated board
	public static BoardDatabaseResult contains(Board board, BoardDatabase ttable) {
		BoardDatabaseResult ttResult;
		
//		final Board newBoard = BoardFactory.createBoardClone(board);
//		final byte[] newBoardArrayInternal = newBoard.getBoardArray().getInternalBoardArray();
//		System.arraycopy(newBoardArrayInternal, 0, boardArrayInternalForResetting, 0, BoardArray.BOARD_ARRAY_SIZE);
			
		ttResult = ttable.contains(board.flipOverX(), false);
		if (ttResult.isResultFound()) { return ttResult; }

		ttResult = ttable.contains(board.flipOverY(), false);
		if (ttResult.isResultFound()) { return ttResult; }
			
		board.flipOverBottomLeftToTopRightDiag();
		ttResult = ttable.contains(board.flipOverBottomLeftToTopRightDiag(), false);
		if (ttResult.isResultFound()) { return ttResult; }
		
		ttResult = ttable.contains(board.flipOverTopLeftToBottomRightDiag(), false);
		if (ttResult.isResultFound()) { return ttResult; }
		
		final Board boardRotatedOnce = board.rotateNinetyDegreesClockwise();
		ttResult = ttable.contains(boardRotatedOnce, false);
		if (ttResult.isResultFound()) { return ttResult; }
		
		final Board boardRotatedTwice = boardRotatedOnce.rotateNinetyDegreesClockwise();
		ttResult = ttable.contains(boardRotatedTwice, false);
		if (ttResult.isResultFound()) { return ttResult; }
		
		final Board boardRotatedThrice = boardRotatedTwice.rotateNinetyDegreesClockwise();
		ttResult = ttable.contains(boardRotatedThrice, false);
		if (ttResult.isResultFound()) { return ttResult; }
		
		return BoardDatabaseResult.NO_RESULT;
	}
}