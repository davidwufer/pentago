package board;

import junit.framework.TestCase;

public class FiveByFiveBoardTest extends TestCase {
	
	Board board;
	
	/**
	 * Board:
	 * x o x o x
	 * - - - - -
	 * o x o x o
	 * - - - - -
	 * x o x o x
	 */
	@Override
	public void setUp() {
		board = BoardFactory.createFiveByFiveBoard("[xoxox     oxoxo     xoxox]");
	}
	
	
	public void testRotateSubboard1() {
		final Board expectedBoard = BoardFactory.createFiveByFiveBoard("[o xoxx o  o xxo     xoxox]");
		assertEquals(expectedBoard, board.rotateSubBoard(1, true));
	}
	
	public void testRotateSubboard2() {
		final Board expectedBoard = BoardFactory.createFiveByFiveBoard("[xoo x  x ooxo x     xoxox]");
		assertEquals(expectedBoard, board.rotateSubBoard(2, true));
	}

	public void testRotateSubboard3() {
		final Board expectedBoard = BoardFactory.createFiveByFiveBoard("[xoxox     x oxoo x  x oox]");
		assertEquals(expectedBoard, board.rotateSubBoard(3, true));
	}

	public void testRotateSubboard4() {
		final Board expectedBoard = BoardFactory.createFiveByFiveBoard("[xoxox     oxo x  x oxoo x]");
		assertEquals(expectedBoard, board.rotateSubBoard(4, false));
	}
	
	public void testRotate90DegreesClockwise() {
		final Board rotatedBoard = BoardFactory.createFiveByFiveBoard("[x o xo x ox o xo x ox o x]");
		assertEquals(rotatedBoard, board.rotateNinetyDegreesClockwise());
	}
}
