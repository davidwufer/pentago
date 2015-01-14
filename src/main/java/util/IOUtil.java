package util;

import gamevalue.GameValueFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import search.alphabeta.transposition.BoardDatabase;
import search.alphabeta.transposition.BoardDatabaseFactory;
import board.Board;
import board.BoardFactory;

public class IOUtil {

    private static final String ENDGAME_DB_DIR = "endgamedb/";

    private static String getCurrentDirectoryPath() {
        return new File(".").getAbsolutePath();
    }
    
    public static void main(String[] args) {
    	final int depth = 1;
        final EndGameDbIO endGameDbIO = IOUtil.getEndGameDbIO(depth);
        final File file = endGameDbIO.getNextEndGameDbFile();
        
        BoardDatabase db = BoardDatabaseFactory.createHashMapEndGameDatabaseBoard(depth);
        final Board board = BoardFactory.createSixBySixBoardRandom();
		db.add(board, GameValueFactory.getWin());
        db.writeToFile(file);
        
        BoardDatabase db2 = BoardDatabaseFactory.createHashMapEndGameDatabaseBoard(depth);
        db2.readFromFile(file);
        System.out.println(db2.contains(board, false).getResult());
    }
    
    public static EndGameDbIO getEndGameDbIO(int depth) {
    	return new EndGameDbIO(depth);
    }

    /**
     * The files will be stored with dates as titles
     */
    public static class EndGameDbIO {

        private final int depth;
        
        public EndGameDbIO(int depth) {
            this.depth = depth;
        }
        
        //define Format
        private final DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        private File getEndGameDbDir() {
            final File endGameDbDirFile = new File(getEndGameDbDirPath());
            // Make the directory if needed
            endGameDbDirFile.mkdir();
            return endGameDbDirFile;
        }
        
        private File getSpecificDepthDir() {
            final File endGameDbDir = getEndGameDbDir();
            final File specificDepthDir = new File(endGameDbDir, String.valueOf(depth));
            // Make the directory if needed
            specificDepthDir.mkdir();
            return specificDepthDir;
        }

        private String getEndGameDbDirPath() {
            return new File(getCurrentDirectoryPath(), ENDGAME_DB_DIR).getAbsolutePath();
        }

        public File getNextEndGameDbFile() {
            final Date now = new Date();
            final String newFileName = formatter.format(now);
            return new File(getSpecificDepthDir(), newFileName);
        }
        
        public File[] getEndGameDbFiles() {
            final File specificDepthDir = getSpecificDepthDir();
            final String[] filenames = specificDepthDir.list();
            
            final File[] files = new File[filenames.length];
            for (int index = 0; index < filenames.length; index++) {
                final String filename = filenames[index];
                files[index] = new File(specificDepthDir, filename);
            }
            
            return files;
        }
    }

}


