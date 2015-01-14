package endgamesolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import org.junit.Test;

import search.alphabeta.transposition.BoardDatabaseResult;

public class RunLengthEncoderTest {

//	private static final GameValue WIN  = GameValueFactory.getWin();
//	private static final GameValue LOSS = GameValueFactory.getLoss();
//	private static final GameValue DRAW = GameValueFactory.getDraw();
//	
//	@Test
//	public void testSinglesShouldBeEncodedWithoutANumber() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(0, WIN);
//        
//        assertEquals("W", encoder.toString());
//	}
//	
//	@Test
//	public void testRunLengthEncodingInsertConsecutiveWins() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(1, WIN);
//        encoder.insert(2, WIN);
//        
//        assertEquals("3W", encoder.toString());
//	}
//	
//	@Test
//	public void testRunLengthEncodingInsertConsecutiveWinsNotInFirstSpot() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(1, LOSS);
//        encoder.insert(2, WIN); 
//        encoder.insert(3, WIN);
//        
//        assertEquals("2L2W", encoder.toString());
//	}
//	
//	@Test
//	public void testRunLengthEncodingInsertConsecutiveLosses() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(1, LOSS);
//        encoder.insert(2, LOSS);
//        
//        assertEquals("3L", encoder.toString());
//	}
//	
//	@Test
//	public void testRunLengthEncodingInsertConsecutiveDraws() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(1, DRAW);
//        encoder.insert(2, DRAW);
//        
//        assertEquals("3D", encoder.toString());
//	}
//	
//	@Test
//	public void testRunLengthEncodingWithLongEntry() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(1,  DRAW);
//        encoder.insert(2,  DRAW);
//        encoder.insert(4,  WIN);
//        encoder.insert(10, LOSS);
//        encoder.insert(12, DRAW);
//        encoder.insert(13, WIN);
//        encoder.insert(15, LOSS);
//        
//        assertEquals("3D2W6L2DW2L", encoder.toString());
//	}
//	
//	@Test
//	public void testRunLengthEncodingWithAnniversary() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(2010, WIN);
//        encoder.insert(2015, LOSS);
//        encoder.insert(2044, DRAW);
//        
//        assertEquals("2011W5L29D", encoder.toString());
//	}
//	
//	@Test
//	public void testSequentialEntriesThrowException() {
//        final RunLengthEncoder encoder = new RunLengthEncoder();
//        encoder.insert(1, WIN);
//        
//        try {
//        	encoder.insert(1, WIN);
//        	fail();
//        } catch (IllegalArgumentException e) {
//        	
//        }
//	}
//	
//	@Test
//	public void testInitializeWithSingles() {
//		final String initializationString = "WDW2L";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		assertEquals(initializationString, encoder.toString());
//	}
//	
//	@Test
//	public void testInitializeWithOneRun() {
//		final String initializationString = "2L";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		assertEquals(initializationString, encoder.toString());
//	}
//	
//	@Test
//	public void testInitializeWithSeveral() {
//		final String initializationString = "6WL2D2L";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		assertEquals(initializationString, encoder.toString());
//	}
//	
//	@Test
//	public void testInitializeWithOneElement() {
//		final String initializationString = "W";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		assertEquals(initializationString, encoder.toString());
//	}
//	
//	@Test
//	public void testInitializeWithMoreThanOneDigit() {
//		final String initializationString = "2011W5L29D";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		assertEquals(initializationString, encoder.toString());
//	}
//	
//	
//	/* contains */
//	@Test
//	public void testSingleElementContains() {
//		final String initializationString = "W";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		final BoardDatabaseResult result = encoder.contains(0, false);
//		
//		assertEquals(WIN, result.getResult());
//	}
//	
//	@Test
//	public void testSingleElementContainsWithError() {
//		final String initializationString = "W";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		try {
//			encoder.contains(1, false);
//			fail();
//		} catch (IndexOutOfBoundsException e) {
//			// Success!
//		}
//	}
//	
//	@Test
//	public void testMultiSingularElements() {
//		final String initializationString = "WLD";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		
//		final BoardDatabaseResult winResult = encoder.contains(0, false);
//		assertEquals(WIN, winResult.getResult());
//		
//		final BoardDatabaseResult lossResult = encoder.contains(1, false);
//		assertEquals(LOSS, lossResult.getResult());
//		
//		final BoardDatabaseResult drawResult = encoder.contains(2, false);
//		assertEquals(DRAW, drawResult.getResult());
//	}
//
//	@Test
//	public void testContainsCuspDigits() {
//		final String initializationString = "200W100L";
//		
//		final RunLengthEncoder encoder = new RunLengthEncoder(initializationString);
//		
//		final BoardDatabaseResult winResult1 = encoder.contains(0, false);
//		assertEquals(WIN, winResult1.getResult());
//		
//		final BoardDatabaseResult winResult2 = encoder.contains(199, false);
//		assertEquals(WIN, winResult2.getResult());
//		
//		final BoardDatabaseResult lossResult1 = encoder.contains(200, false);
//		assertEquals(LOSS, lossResult1.getResult());
//		
//		final BoardDatabaseResult lossResult2 = encoder.contains(299, false);
//		assertEquals(LOSS, lossResult2.getResult());
//		
//		try {
//			encoder.contains(300, false);
//			fail();
//		} catch (IndexOutOfBoundsException e) {
//			// Sucess!
//		}
//	}
	
}
