package solver;

import gamevalue.GameValue;

public class SearchStats {
	final long numExpandedNodes;
	final long numTerminalNodes;
	final long milliseconds;
	final GameValue gameValue;
	
	public SearchStats(GameValue gameValue, long numExpandedNodes, long numTerminalNodes, long milliseconds) {
		this.gameValue = gameValue;
		this.numExpandedNodes = numExpandedNodes;
		this.numTerminalNodes = numTerminalNodes;
		this.milliseconds = milliseconds;
	}
	
	@Override
	public String toString() {
		return "" + gameValue + '\t' + numExpandedNodes + '\t' + numTerminalNodes + '\t' + milliseconds + '\n';
	}

}