package solver;

import gamevalue.GameValue;

public abstract class Solver {
	public abstract GameValue solve();
	
	public abstract long getNumExpandedNodes();
	public abstract long getNumTerminalsReached();
	
	public SearchStats solveAndGetStats() {
		final long startTime = System.nanoTime();
		GameValue gameValue = solve();
		final long endTime = System.nanoTime();
		final long milliseconds = (endTime - startTime) / 1000000;
		
		return new SearchStats(gameValue, getNumExpandedNodes(), getNumTerminalsReached(), milliseconds);
	}
}
