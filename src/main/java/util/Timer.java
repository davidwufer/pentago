package util;

/**
 * @author dwu
 */
public class Timer {
	
	private final long seconds;
	private long startTime;
	private long endTime;
	
	public Timer(long seconds) {
		this.seconds = seconds;
	}
	
	public void start() {
		startTime = System.nanoTime();
		endTime = startTime + (seconds * 1000000000); 
	}
	
	public void check() {
		if (System.nanoTime() > endTime) {
			throw new TimeLimitExceededException();
		}
	}
	
}
