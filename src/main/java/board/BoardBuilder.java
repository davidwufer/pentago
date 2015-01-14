package board;

public class BoardBuilder {

	private int piecesOnBoard;
	private BoardArray boardArray;
	
	protected BoardBuilder() {}

	public Board build() {
		throw new UnsupportedOperationException("You must override this when you create a BoardBuilder");
	}
	
	public BoardBuilder setPiecesOnBoard(int piecesOnBoard) {
		this.piecesOnBoard = piecesOnBoard;
		return this;
	}

	public BoardBuilder setBoardArray(BoardArray boardArray) {
		this.boardArray = boardArray;
		return this;
	}
	
	public BoardArray getBoardArray() {
		return boardArray;
	}
	
	public int getPiecesOnBoard() {
		return piecesOnBoard;
	}
}
