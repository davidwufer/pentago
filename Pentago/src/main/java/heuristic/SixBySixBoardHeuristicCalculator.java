package heuristic;

import static config.Config.DRAW_VALUE;
import static config.Config.LOSS_VALUE;
import static config.Config.WIN_VALUE;
import gamevalue.GameValue;
import gamevalue.GameValueFactory;
import board.Piece;

// TODO: This code is all horribly bunched up from when I first wrote it in C. Separate all the heuristics
public class SixBySixBoardHeuristicCalculator extends AbstractHeuristicCalculator {

	//TODO: we can keep track of the last move and only re-calculate the heuristic value for the corresponding subboard
	
	/* The number of lines going through each spot 
	   Added weights for center values */
	final public static int linesValue[] =  { 3, 3, 2, 2, 3, 3,
			                            	  3, 5, 3, 3, 5, 3,
				                              2, 3, 4, 4, 3, 2,
				                              2, 3, 4, 4, 3, 2,
				                              3, 5, 3, 3, 5, 3,
				                              3, 3, 2, 2, 3, 3 };
	                            
	/* Provides higher point values for spots closer to the center 
	   because these spaces have more room for growth */                             
	final public static int layersValue[] =    { 0, 0, 0, 0, 0, 0,
					                             0, 1, 1, 1, 1, 0,
					                             0, 1, 2, 2, 1, 0,
					                             0, 1, 2, 2, 1, 0,
					                             0, 1, 1, 1, 1, 0,
					                             0, 0, 0, 0, 0, 0 };
	
	final public static int linesWeight = 10;
	final public static int layersWeight = 12;
	
	int XPieceValue;
	int OPieceValue;

	public SixBySixBoardHeuristicCalculator() {
		XPieceValue = OPieceValue = 0;
	}
	
//	final public static int LShapeValue = 20;
//	final public static int LShapeWeight = 1;
//	
//	final public static int threeInARowValue = 15;
//	final public static int threeInARowWeight = 1;	
//	
//	public GameValue getHeuristicValue(Board board) {
//		int heuristicValueSoFar = 0;
//		heuristicValueSoFar += calculateLinesAndLayers(board);
//		heuristicValueSoFar += calculateLShapeAndThreeInARow(board);
//		heuristicValueSoFar = preventTerminalValueConflict(heuristicValueSoFar);
//		return GameValueFactory.createGameValue(heuristicValueSoFar);
//	}
	
	/**
	 * Returns a heuristic value of 1 to prevent conflicts with DRAW, WIN_VALUE - 1 instead of WIN_VALUE, and LOSS_VALUE + 1 instead of LOSS_VALUE
	 * @param heuristicValueSoFar
	 * @return
	 */
	protected int preventTerminalValueConflict(int heuristicValueSoFar) {
		if (heuristicValueSoFar == DRAW_VALUE) { 
			return NON_DRAW_CONFLICT_VALUE;
		} else if (heuristicValueSoFar == WIN_VALUE) {
			return NON_WIN_CONFLICT_VALUE;
		} else if (heuristicValueSoFar == LOSS_VALUE) {
			return NON_LOSS_CONFLICT_VALUE;
		} else {
			return heuristicValueSoFar;
		}
	}
	
//	protected int calculateLinesAndLayers(Board board) {
//		Piece currPlayer = board.currPlayerPiece();
//		Piece currPiece;
//		int linesValueSoFar  = 0;
//		int layersValueSoFar = 0;
//		for (int index = 0; index < board.getNumberOfSpotsOnBoard(); index++) {
//			currPiece = board.getPieceAt(index);
//			if (Piece.BLANK == currPiece) { continue; }
//			
//			if (currPlayer == currPiece) {
//				linesValueSoFar  +=  SixBySixHeuristicCalculator.linesValue[index]; 
//				layersValueSoFar += SixBySixHeuristicCalculator.layersValue[index];
//			} else {
//				linesValueSoFar  -=  SixBySixHeuristicCalculator.linesValue[index]; 
//				layersValueSoFar -= SixBySixHeuristicCalculator.layersValue[index];
//			}
//		}
//		return (linesWeight * linesValueSoFar) + (layersWeight * layersValueSoFar);
//	}
//	
//	protected int calculateLShapeAndThreeInARow(Board board) {
//		int heuristicValueSoFar = 0;
//		int subBoardCenterIndex;
//		for (int subBoard = 1; subBoard <= NUM_SUB_BOARDS; subBoard++) {
//			subBoardCenterIndex = getCenterIndex(subBoard);
//			heuristicValueSoFar += calculateLShapeAndThreeInARowForASubBoard(board, subBoardCenterIndex);
//		}
//		return heuristicValueSoFar;
//	}
//	
//	protected int getCenterIndex(int subBoard) {
//		return subBoardToCenterIndex[subBoard - 1];
//	}
//
//	protected int calculateLShapeAndThreeInARowForASubBoard(Board board, int subBoardCenterIndex) {
//		int LHeuristicSoFar, threeInARowHeuristicSoFar;
//	    final Piece indexPiece, leftPiece, topPiece, rightPiece, bottomPiece,
//	                topLeftPiece, topRightPiece, bottomRightPiece, bottomLeftPiece;
//	    LHeuristicSoFar = threeInARowHeuristicSoFar = 0;
//	    final Piece currPlayer = board.currPlayerPiece();
//	    final int DIM = board.getDimension();
//	    
//	    indexPiece  =   board.getPieceAt(subBoardCenterIndex);
//	    leftPiece   =   board.getPieceAt(subBoardCenterIndex - 1);
//	    rightPiece  =   board.getPieceAt(subBoardCenterIndex + 1);
//	    topPiece    =   board.getPieceAt(subBoardCenterIndex - DIM);
//	    bottomPiece =   board.getPieceAt(subBoardCenterIndex + DIM);
//
//	    topLeftPiece     = board.getPieceAt(subBoardCenterIndex - DIM - 1);
//	    topRightPiece    = board.getPieceAt(subBoardCenterIndex - DIM + 1);
//	    bottomRightPiece = board.getPieceAt(subBoardCenterIndex + DIM + 1);
//	    bottomLeftPiece  = board.getPieceAt(subBoardCenterIndex + DIM - 1);
//
//	    //L-Shape
//	    if (indexPiece != Piece.BLANK) {
//		    if (indexPiece == leftPiece) {
//		        if (indexPiece == topPiece) {
//		            LHeuristicSoFar += LShapeValue;
//		        }
//		        if (indexPiece == bottomPiece) {
//		            LHeuristicSoFar += LShapeValue;
//		        }
//		    }
//		    
//		    if (indexPiece == rightPiece) {
//		        if (indexPiece == bottomPiece) {
//		            LHeuristicSoFar += LShapeValue;
//		        }
//		        if (indexPiece == topPiece) {
//		            LHeuristicSoFar += LShapeValue;
//		        }
//		    }
//		    
//		    if (indexPiece != currPlayer) {
//		        LHeuristicSoFar = -LHeuristicSoFar;
//		    }
//	    }
//	    
//	    //Three-In-A-Row
//	    //need BLANK checks
//	    if (indexPiece != Piece.BLANK) {
//		    if (topPiece == indexPiece && indexPiece == bottomPiece) {
//		        if (indexPiece == currPlayer) {
//		            threeInARowHeuristicSoFar += threeInARowValue;
//		        } else {
//		            threeInARowHeuristicSoFar -= threeInARowValue;
//		        }
//		    }
//	    
//		    if (leftPiece == indexPiece && indexPiece == rightPiece) {
//		        if (indexPiece == currPlayer) {
//		            threeInARowHeuristicSoFar += threeInARowValue;
//		        } else {
//		            threeInARowHeuristicSoFar -= threeInARowValue;
//		        }
//		    }
//	    }
//	    
//	    if (topLeftPiece != Piece.BLANK) {
//	        if (topLeftPiece == leftPiece && leftPiece == bottomLeftPiece) {
//	            if (leftPiece == currPlayer) {
//	                threeInARowHeuristicSoFar += threeInARowValue;
//	            } else {
//	                threeInARowHeuristicSoFar -= threeInARowValue;
//	            }
//	        }
//	        if (topLeftPiece == topPiece && topPiece == topRightPiece) {
//	            if (topPiece == currPlayer) {
//	                threeInARowHeuristicSoFar += threeInARowValue;
//	            } else {
//	                threeInARowHeuristicSoFar -= threeInARowValue;
//	            }
//	        }
//	    }
//
//	    if (bottomRightPiece != Piece.BLANK) {
//	        if (bottomLeftPiece == bottomPiece && bottomPiece == bottomRightPiece) {
//	            if (bottomPiece == currPlayer) {
//	                threeInARowHeuristicSoFar += threeInARowValue;
//	            } else {
//	                threeInARowHeuristicSoFar -= threeInARowValue;
//	            }
//	        }
//	        
//	        if (topRightPiece == rightPiece && rightPiece == bottomRightPiece) {
//	            if (rightPiece == currPlayer) {
//	                threeInARowHeuristicSoFar += threeInARowValue;
//	            } else {
//	                threeInARowHeuristicSoFar -= threeInARowValue;
//	            }
//	        }
//	    }
//	    return LShapeWeight * LHeuristicSoFar + threeInARowWeight * threeInARowHeuristicSoFar;
//	}

	@Override
	public GameValue getHeuristicValue(Piece currentPlayerPiece) {
		final int difference = XPieceValue - OPieceValue;
		
		if (difference == 0) { return GameValueFactory.createGameValue(NON_DRAW_CONFLICT_VALUE); }
		
		return GameValueFactory.createGameValue(currentPlayerPiece == Piece.X ? difference : -difference);
	}

	@Override
	public void addPieceValue(Piece piece, int index) {
		final int value = (linesWeight * linesValue[index]) + (layersWeight * layersValue[index]);
		if (piece == Piece.X) {
			XPieceValue += value;
		} else {
			OPieceValue += value;
		}
	}
}
