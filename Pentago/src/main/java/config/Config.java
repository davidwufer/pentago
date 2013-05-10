package config;

import java.io.File;

public class Config {
	public static int TTSIZE = 50000000;
//	public static int TTSIZE = 80000000;
	public static int TTSIZE_TEST = 1024;
	public static int ALPHABETA_PRINTOUT_INTERVAL = 100000;
	public static boolean RESUME = true;
	
	// A prime number roughly 3x 324
	public static int BOARD_HASH_SET_CUSTOM_SIZE = 983;
	
	public final static int WIN_VALUE  =  10000;
	public final static int LOSS_VALUE = -10000;
	public final static int DRAW_VALUE =      0;
	public final static int UNDETERMINED_VALUE = 1;
	public static final int MAX_CHILDREN = (36 * 4 * 2) + 36;
	
	private static final String ROOT_PATH = new File(new File(".").getAbsolutePath(), "pentago").getAbsolutePath();
	
	public static final String TT_PATH = new File(ROOT_PATH, "TT").getAbsolutePath();
	public static final String TT_PREVIOUS = new File(TT_PATH, "tt_prev.dat").getAbsolutePath();
	public static final String TT_CURRENT = new File(TT_PATH, "tt_current.dat").getAbsolutePath();
	public static final String TT_PENDING = new File(TT_PATH, "tt_pending.dat").getAbsolutePath();
	
	public static final String SEARCH_COMPARISON_PATH = new File(ROOT_PATH, "searchcomparison").getAbsolutePath();
	
	public static final String RESULTS = new File(ROOT_PATH, "results.txt").getAbsolutePath();
	
	public static final String ENDGAMEDB = "endgamedb";
	
	// 50 MB
	public static final int RUN_LIST_LENGTH_NODES_BEFORE_WRITING = 5825420;
}
