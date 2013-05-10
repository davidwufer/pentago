package movecomparator;

public class HeuristicComparatorFactory {
	
	public static HeuristicComparator WinDrawHeuristicLossComparator() {
		return new WinDrawHeuristicsLossComparator();
	}
	
	public static HeuristicComparator SubtractionComparator() {
		return new SubtractionComparator();
	}
	
	public static HeuristicComparator WinHeuristicsDrawLossComparator() {
		return new WinHeuristicsDrawLossComparator();
	}
}
