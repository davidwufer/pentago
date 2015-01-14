package heuristic;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import junit.framework.TestCase;

import org.junit.Test;

import board.Board;
import board.BoardFactory;
import board.Piece;

public class FourByFourBoardHeuristicCalculatorTest extends TestCase {

	@Test
	public void testGetHeuristicValueReturnsUndeterminedForBlankBoard() {
		final Board board = BoardFactory.createFourByFourBoardBlank();
		final HeuristicCalculator heuristicCalculator = new FourByFourBoardHeuristicCalculator();
		
		assertEquals(GameValueFactory.getUndetermined(), heuristicCalculator.getHeuristicValue(board.currPlayerPiece()));
	}
	
	@Test
	public void testGetHeuristicValueWithX() {
		final HeuristicCalculator heuristicCalculator = new FourByFourBoardHeuristicCalculator();
		assertEquals(GameValueFactory.getUndetermined(), heuristicCalculator.getHeuristicValue(Piece.X));
	}
	
	@Test
	public void testGetHeuristicValueWithO() {
		final HeuristicCalculator heuristicCalculator = new FourByFourBoardHeuristicCalculator();
		assertEquals(GameValueFactory.getUndetermined(), heuristicCalculator.getHeuristicValue(Piece.O));
	}
	
	@Test
	public void testGetHeuristicValueReturnsUndeterminedForRandomBoard() {
		final Board board = BoardFactory.createFourByFourBoardRandom();
		final HeuristicCalculator heuristicCalculator = new FourByFourBoardHeuristicCalculator();
		
		assertEquals(GameValueFactory.getUndetermined(), heuristicCalculator.getHeuristicValue(board.currPlayerPiece()));
	}
	
	@Test
	public void testAddPieceValueDoesNotChangeHeuristicValue() {
		final HeuristicCalculator heuristicCalculator = new FourByFourBoardHeuristicCalculator();
		
		final Piece piece = Piece.X;
		final GameValue heuristicValue = heuristicCalculator.getHeuristicValue(piece);
		
		heuristicCalculator.addPieceValue(Piece.X, 0);
		
		assertEquals(heuristicValue, heuristicCalculator.getHeuristicValue(piece));
	}
}
