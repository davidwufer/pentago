package search.alphabeta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import search.alphabeta.AlphaBetaIterative.DepthData;
import search.alphabeta.AlphaBetaIterative.DepthData.DepthDataEntry;

public class DepthDataTest {
	
	@Test
	public void getGetAndSetEntry() {
		final DepthDataEntry depthDataEntry = mock(DepthDataEntry.class);
		
		final int numberOfSpotsOnBoard = 10;
		final DepthData depthData = new DepthData(numberOfSpotsOnBoard);
		
		final int testDepth = numberOfSpotsOnBoard / 2;
		assertFalse(depthData.getEntry(testDepth) == depthDataEntry);
		
		depthData.setEntry(depthDataEntry, testDepth);
		assertTrue(depthData.getEntry(testDepth) == depthDataEntry);
	}
	
	@Test
	public void sizeShouldBeEqualToNumberOfSpotsOnBoard() {
		final int numberOfSpotsOnBoard = 10;
		
		final DepthData depthData = new DepthData(numberOfSpotsOnBoard);
		assertEquals(numberOfSpotsOnBoard, depthData.size());
	}
}
