package hash;

import java.util.HashSet;
import java.util.Set;

import board.Board;

public class SymmetryChecker {

	// TODO: ADD TEST CASES FOR THIS. WOW HOW DID I NOT BEFORE?
	public static boolean contains(final Board board, final BoardSet boardSet) {
		
		if (boardSet.contains(board.flipOverX(), false)) return true;

		if (boardSet.contains(board.flipOverY(), false)) return true;
			
		if (boardSet.contains(board.flipOverBottomLeftToTopRightDiag(), false)) return true;
		
		if (boardSet.contains(board.flipOverTopLeftToBottomRightDiag(), false)) return true;
		
		final Board boardRotatedOnce = board.rotateNinetyDegreesClockwise();
		if (boardSet.contains(boardRotatedOnce, false)) return true;
		
		final Board boardRotatedTwice = boardRotatedOnce.rotateNinetyDegreesClockwise();
		if (boardSet.contains(boardRotatedTwice, false)) return true;
		
		final Board boardRotatedThrice = boardRotatedTwice.rotateNinetyDegreesClockwise();
		if (boardSet.contains(boardRotatedThrice, false)) return true;
		
		return false;
	}
	
	public static int getNumberOfUniqueSymmetricBoardsIncludingItself(final Board board) {
		int symmetries = 0;
		final Set<Board> boardSet = new HashSet<Board>();
		
		boardSet.add(board);
		symmetries += 1;
		
		final Board boardFlippedOverX = board.flipOverX();
		if (!boardSet.contains(boardFlippedOverX)) {
			boardSet.add(boardFlippedOverX);
			symmetries += 1;
		}

		final Board boardFlippedOverY = board.flipOverY();
		if (!boardSet.contains(boardFlippedOverY)) {
			boardSet.add(boardFlippedOverY);
			symmetries += 1;
		}
			
		final Board boardFlippedOverBottomLeftToTopRightDiag = board.flipOverBottomLeftToTopRightDiag();
		if (!boardSet.contains(boardFlippedOverBottomLeftToTopRightDiag)) {
			boardSet.add(boardFlippedOverBottomLeftToTopRightDiag);
			symmetries += 1;
		}
		
		final Board boardFlippedOverTopLeftToBottomRightDiag = board.flipOverTopLeftToBottomRightDiag();
		if (!boardSet.contains(boardFlippedOverTopLeftToBottomRightDiag)) {
			boardSet.add(boardFlippedOverTopLeftToBottomRightDiag);
			symmetries += 1;
		}
		
		final Board boardRotatedOnce = board.rotateNinetyDegreesClockwise();
		if (!boardSet.contains(boardRotatedOnce)) {
			boardSet.add(boardRotatedOnce);
			symmetries += 1;
		}
		
		final Board boardRotatedTwice = boardRotatedOnce.rotateNinetyDegreesClockwise();
		if (!boardSet.contains(boardRotatedTwice)) {
			boardSet.add(boardRotatedTwice);
			symmetries += 1;
		}
		
		final Board boardRotatedThrice = boardRotatedTwice.rotateNinetyDegreesClockwise();
		if (!boardSet.contains(boardRotatedThrice)) {
			boardSet.add(boardRotatedThrice);
			symmetries += 1;
		}
		
		return symmetries;
	}
}
