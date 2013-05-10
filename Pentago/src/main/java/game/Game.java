package game;
import gamevalue.GameValue;
import move.Move;
import move.MoveGenerator;
import move.MoveGeneratorResults;
import board.Board;

public interface Game {
	public Board getBoard();
	
	public Game doMove(Move m);
	
	public Game undoMove(Move m);
	
	public MoveGeneratorResults generateMoves(MoveGenerator moveGenerator);
	
	public GameValue getGameState();
	
	public int getMaxNumChildren();
	
	public boolean isSymmetriesOn();
	public boolean setSymmetriesOn();
	public boolean setSymmetriesOff();
}
