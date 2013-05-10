package board;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Test;


public class BoardArrayBuilderTest {

	@Test
	public void newBuilderWithArgumentShouldGrabTheCorrectBoardArray() {
		final byte[] internalBoardArray = new byte[] {'1', '2', '3', '4', '1', '2', '3', '4', '1'};
		final BoardArray boardArray = BoardArray.createNewBoardArray(internalBoardArray);
		
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(boardArray);
		assertTrue("The internal board array for the builder should be the same as the one for the board array that was passed in",
				Arrays.equals(internalBoardArray, builder.getInternalBoardArray()));
	}
	
	@Test
	public void getPieceAtReturnsBlankForNewBoardArray() {
		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY;
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(boardArray);
		
		assertEquals(Piece.BLANK, builder.getPieceAt(0));
	}
	
	@Test
	public void placePieceAt() {
		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY;
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(boardArray);
		
		final int index = 0;
		final Piece piece = Piece.X;
		builder.placePieceAt(index, piece);
		
		assertEquals(piece, builder.getPieceAt(index));
	}
	
	@Test
	public void swapPiecesSwapsThePieces() {
		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY;
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(boardArray);
		
		final int index1 = 0;
		final Piece piece1 = Piece.X;
		builder.placePieceAt(index1, piece1);
		
		final int index2 = 5;
		final Piece piece2 = Piece.O;
		builder.placePieceAt(index2, piece2);
		
		builder.swapPiecesAt(index1, index2);
		
		assertEquals(piece2, builder.getPieceAt(index1));
		assertEquals(piece1, builder.getPieceAt(index2));
	}
	
	@Test
	public void swapPiecesAtShouldShortCircuitIfPiecesAreTheSame() {
		final BoardArray boardArray = BoardArray.NEW_BOARD_ARRAY;
		final BoardArrayBuilder spyBuilder = spy(BoardArrayBuilder.newBuilder(boardArray));
		
		final int index1 = 0;
		final int index2 = 4;
		
		final Piece p1 = Piece.BLANK;
		final Piece p2 = Piece.BLANK;
		
		doReturn(p1).when(spyBuilder).getPieceAt(index1);
		doReturn(p2).when(spyBuilder).getPieceAt(index2);
		
		spyBuilder.swapPiecesAt(index1, index2);
		
		verify(spyBuilder, times(0)).placePieceAt(any(Integer.class), any(Piece.class));
	}
	
	@Test
	public void buildReturnsABoardArrayWithTheRightInternalBoardArray() {
		final byte[] internalBoardArray = new byte[] {'1', '2', '3', '4', '1', '2', '3', '4', '1'};
		final BoardArray boardArray = BoardArray.createNewBoardArray(internalBoardArray);
		
		final BoardArrayBuilder builder = BoardArrayBuilder.newBuilder(boardArray);
		
		final BoardArray createdBoardArray = builder.build();
		
		assertTrue(Arrays.equals(internalBoardArray, createdBoardArray.getInternalBoardArray()));
	}
}
