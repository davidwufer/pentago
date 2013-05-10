package board;


/* Currently not used. Might be used in the future for concurrency? */
public class BoardFactory {
	
	private BoardFactory() {}
	
	public static Board createSixBySixBoardBlank() {
		return new SixBySixBoard();
	}
	
	public static Board createSixBySixBoard(String boardConfiguration) {
		return new SixBySixBoard(boardConfiguration);
	}
	
	public static Board createSixBySixBoardWithPiecesAndBoardArray(int piecesOnBoard, BoardArray boardArray) {
		return new SixBySixBoard(piecesOnBoard, boardArray);
	}
	
	public static Board createFourByFourBoardBlank() {
		return new FourByFourBoard();
	}
	
	public static Board createFourByFourBoardWithPiecesAndBoardArray(int piecesOnBoard, BoardArray boardArray) {
		return new FourByFourBoard(boardArray, piecesOnBoard);
	}
	
	public static Board createFourByFourBoard(String boardConfiguration) {
		return new FourByFourBoard(boardConfiguration);
	}
	
	public static Board createSixBySixBoardRandom() {
		String randomBoardString = BoardGenerator.createRandomBoardStringAtRandomDepth(SixBySixBoard.NUMBER_OF_SPOTS_ON_BOARD);
		return createSixBySixBoard(randomBoardString);
	}
	
	public static Board createSixBySixBoardAtRandomDepth(int depth) {
		String randomBoardString = BoardGenerator.createRandomBoardStringAtDepth(depth, SixBySixBoard.NUMBER_OF_SPOTS_ON_BOARD);
		return createSixBySixBoard(randomBoardString);
	}
	
	public static Board createFourByFourBoardRandom() {
		String randomBoardString = BoardGenerator.createRandomBoardStringAtRandomDepth(FourByFourBoard.NUMBER_OF_SPOTS_ON_BOARD);
		return createFourByFourBoard(randomBoardString);
	}
	
	public static Board createFourByFourBoardAtRandomDepth(int depth) {
		String randomBoardString = BoardGenerator.createRandomBoardStringAtDepth(depth, FourByFourBoard.NUMBER_OF_SPOTS_ON_BOARD);
		return createFourByFourBoard(randomBoardString);
	}
	
	public static Board createBoardClone(Board board) {
		return board.copy();
	}
	
	public static Board createBoardFromConfiguration(String configuration) {
		final int fourByFourLength = 18;
		final Board board = (configuration.length() == fourByFourLength) ? BoardFactory.createFourByFourBoard(configuration) : BoardFactory.createSixBySixBoard(configuration);
		board.getPiecesOnBoardAfterRecalculation();
		return board;
	}
}

class BoardGenerator {
	public static String createRandomBoardStringAtRandomDepth(int totalSpotsOnBoard) {
		final int randomDepth = getRandomNumberInRange(0, totalSpotsOnBoard);
		return createRandomBoardStringAtDepth(randomDepth, totalSpotsOnBoard);
	}
	
	public static String createRandomBoardStringAtDepth(int numPiecesToPlace, int totalSpotsOnBoard) {
		// Gracefully avoid an error
		if (numPiecesToPlace > totalSpotsOnBoard) {
			numPiecesToPlace = totalSpotsOnBoard;
		}
		
		StringBuilder builder = new StringBuilder();
		char charToAppend;
		for (int depthCounter = 0; depthCounter < totalSpotsOnBoard; depthCounter++) {
			if (depthCounter < numPiecesToPlace) {
				charToAppend = (depthCounter % 2 == 0) ? 'x' : 'o';
			} else {
				charToAppend = ' ';
			}
			builder.append(charToAppend);
		}
		
		for (int index = 0; index < totalSpotsOnBoard; index++) {
			int randomIndex = getRandomNumberInRange(index, totalSpotsOnBoard);
			char currPiece = builder.charAt(index);
			char randomPiece = builder.charAt(randomIndex);
			builder.setCharAt(randomIndex, currPiece);
			builder.setCharAt(index, randomPiece);
		}
		
		return builder.insert(0, '[').append(']').toString();
	}

	// max is NOT inclusive
	private static int getRandomNumberInRange(int min, int max) {
		return min + (int)(Math.random() * (max - min));
	}
}