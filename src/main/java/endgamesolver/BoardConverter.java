package endgamesolver;

import java.math.BigInteger;
import java.util.Arrays;

import board.Board;
import board.BoardArray;
import board.BoardFactory;
import board.Piece;

public class BoardConverter {
	
		private final static BoardConverter converter = new BoardConverter();
	
		private BoardConverter() {
			
		}
		
		public static BoardConverter getBoardConverter() {
			return converter;
		}
	
		protected Long boardToLong(Board board) {
			final String boardAsBaseThree = boardToBaseThreeStringRepresentation(board);
			final BigInteger bigInt = new BigInteger(boardAsBaseThree, 3);
			return bigInt.longValue();
		}
		
		protected String boardToBaseThreeStringRepresentation(Board board) {
			final byte[] boardArrayInternal = board.getBoardArray().getInternalBoardArray();
			
			final StringBuilder builder = new StringBuilder(board.getDimension());
			
//			for (int index =  board.getNumberOfSpotsOnBoard() - 1; index >= 0; index -= 1) {
//				final Piece piece = board.getPieceAt(index);
//				builder.append(piece.toInt());
//			}
			
			// we go from the back to the front since we are appending
			for (int index = boardArrayInternal.length - 1; index >= 0; index -= 1) {
				byte currentByte = boardArrayInternal[index];
				final Piece piece1 = Piece.getPieceFromVal((byte) (currentByte & 3));
				currentByte >>= 2;
				final Piece piece2 = Piece.getPieceFromVal((byte) (currentByte & 3));
				currentByte >>= 2;
				final Piece piece3 = Piece.getPieceFromVal((byte) (currentByte & 3));
				currentByte >>= 2;
				final Piece piece4 = Piece.getPieceFromVal((byte) (currentByte & 3));
				
				builder.append(piece4.toInt()).
						append(piece3.toInt()).
						append(piece2.toInt()).
						append(piece1.toInt());
			}
			
			return builder.toString();
		}
		
		protected Board longToBoard(Long longNum, boolean isSixBySixBoard) {
			final byte[] boardArrayInternal = new byte[BoardArray.BOARD_ARRAY_SIZE];
			Arrays.fill(boardArrayInternal, (byte) 0);

			final BoardArray boardArray = BoardArray.createNewBoardArray(boardArrayInternal);
			Board board;
			if (isSixBySixBoard) {
				board = BoardFactory.createSixBySixBoardWithPiecesAndBoardArray(0, boardArray);
			} else {
//				board = BoardFactory.createFourByFourBoardWithPiecesAndBoardArray(0, boardArray);
				throw new UnsupportedOperationException("This hasn't been implemented for 4x4 boards yet");
	 		}

			// Where the actual conversion takes place 
			final String longAsString = String.valueOf(longNum);
			final BigInteger bigInt = new BigInteger(new BigInteger(longAsString).toString(3));
			// Reverse the string so index 0 in the string corresponds to index 0 on the board
			final String baseThreeString = new StringBuilder(bigInt.toString()).reverse().toString();
			
			
			for (int index = 0; index < baseThreeString.length(); index += 1) {
				final int pieceAsInt = baseThreeString.charAt(index) - '0';
//				System.out.println(pieceAsInt);
				final Piece pieceToPlace = Piece.getPieceFromVal((byte) pieceAsInt);
				
				board = board.placePieceAt(index, pieceToPlace);
				
				if (pieceToPlace != Piece.BLANK) {
					board = board.incrementPiecesOnBoard();
				}
			}
			
			return board;
		}
	}