package search.pns.transposition;

import junit.framework.TestCase;

public class PnsTTResultTest extends TestCase {

	public void testResultFound() {
		PnsTTResult result = new PnsTTResult(5, 5);
		assertTrue(result.isResultFound());
	}
	
	public void testResultNotFound() {
		PnsTTResult result = new PnsTTResult(1, 1);
		assertFalse(result.isResultFound());
	}
	
	public void testNoResultValues() {
		PnsTTResult result = PnsTTResult.createNoResult();
		assertFalse(result.isResultFound());
	}
}
