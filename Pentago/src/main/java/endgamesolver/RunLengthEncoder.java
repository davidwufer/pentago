package endgamesolver;

import gamevalue.GameValue;
import gamevalue.GameValueFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import search.alphabeta.transposition.BoardDatabaseResult;
import util.IOUtil;

public class RunLengthEncoder {
    
    private final List<RunLengthEncoderNode> runLengthList;
    private long firstIndex;
    
    private RunLengthEncoderNode previousNode;
    
    public static void main(String[] args) throws IOException {
    	final RunLengthEncoder encoder = new RunLengthEncoder(0);
    	encoder.insert(1L, GameValueFactory.getWin());
    	encoder.insert(2L, GameValueFactory.getLoss());
    	
    	final File file = IOUtil.getEndGameDbIO(1).getNextEndGameDbFile();
    	final DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file, true));
    	
    	encoder.writeToOutputStream(outputStream, 2);
    	
    	encoder.reset(0);
    	
    	final DataInputStream inputStream = new DataInputStream(new FileInputStream(file));
    	encoder.readFromOutputStream(inputStream);
    }
    
    public RunLengthEncoder(long firstIndex) {
        this.runLengthList = new ArrayList<RunLengthEncoderNode>();
        this.firstIndex = firstIndex;
    }
    
    public RunLengthEncoder(int initialSize, long firstIndex) {
    	this.runLengthList = new ArrayList<RunLengthEncoderNode>(initialSize);
    	this.firstIndex = firstIndex;
    }
    
    public RunLengthEncoder(String initializationString, long firstIndex) {
    	this.runLengthList = new ArrayList<RunLengthEncoderNode>();
    	this.firstIndex = firstIndex;
    	initialize(initializationString);
    }
    
    public void reset(long firstIndex) {
    	runLengthList.clear();
    	this.firstIndex = firstIndex;
    	previousNode = null;
    }
    
    public long getFirstIndex() {
    	return firstIndex;
    }
    
    public long getLastIndex() {
    	final int size = runLengthList.size();
    	
    	if (size == 0) {
    		return getFirstIndex();
    	}
    	
    	final RunLengthEncoderNode lastNode = runLengthList.get(size - 1);
		return lastNode.getIndex();
    }
    
    public void initialize(String initializationString) {
    	runLengthList.clear();
    	
    	int runLengthListIndex = 0;
    	long numberCount = 0;
    	for (int index = 0; index < initializationString.length(); index++) {
    		final char currentChar = initializationString.charAt(index);
    		if (Character.isDigit(currentChar)) {
    			numberCount = (numberCount * 10) + (currentChar - '0');
    		} else {
    			final GameValue gameValue = getGameValue(currentChar);
    			
    			if (index - 1 < 0 || !Character.isDigit(initializationString.charAt(index - 1))) {
    				insert(runLengthListIndex, gameValue);
    				runLengthListIndex += 1;
    			} else {
    				insert(runLengthListIndex + numberCount - 1, gameValue);
	    			runLengthListIndex += numberCount;
	    			numberCount = 0;
    			}
    		}
    	}
	}
    
    private GameValue getGameValue(char c) {
    	switch (c) {
	    	case 'W' : return GameValueFactory.getWin();
	    	case 'L' : return GameValueFactory.getLoss();
	    	case 'D' : return GameValueFactory.getDraw();
	    	default : throw new IllegalArgumentException(c + " is not a valid argument!");
    	}
    }

	public BoardDatabaseResult contains(long index, boolean useSymmetries) {
    	if (useSymmetries) {
    		throw new UnsupportedOperationException("symmetries for the end game database is not supported!");
    	}
    	
    	GameValue gameValue = null;
    	for (int runLengthListIndex = 0; runLengthListIndex < runLengthList.size(); runLengthListIndex++) {
    		RunLengthEncoderNode node = runLengthList.get(runLengthListIndex);
    		if (node.getIndex() >= index) {
    			gameValue = node.getGameValue();
    			break;
    		}
    	}
    	
    	if (gameValue == null) {
    		throw new IndexOutOfBoundsException("index: " + index + " could not be found! It must be out of bounds");
    	} else {
    		return BoardDatabaseResult.getBoardDatabaseResult(gameValue);
    	}
    }
    
    /**
     * Index will be the board converted to a base-3 long
     * @param index
     * @param gameValue
     * @return
     */
    public RunLengthEncoderNode insert(long index, GameValue gameValue) {
        if (previousNode != null && previousNode.getGameValue() == gameValue) {
        	
        	if (previousNode.getIndex() >= index) {
        		throw new IllegalArgumentException("index: " + index + " came after previousIndex: " + previousNode.getIndex());
        	}
        	
            previousNode.setIndex(index);
            return previousNode;
        } else {
            final RunLengthEncoderNode node = new RunLengthEncoderNode(index, gameValue);
            runLengthList.add(node);
            previousNode = node;
            return node;
        }
    }
    
    public List<RunLengthEncoderNode> getRunLengthList() {
        return runLengthList;
    }
    
    @Override
    public String toString() {
        return toString(runLengthList.size());
    }
    
    public String toString(int numberOfNodes) {
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < numberOfNodes; index++) {
        	final long runLength;
        	final GameValue gameValue;
        	
        	// For the very first node, there is no previous node to compare it to
        	if (index == 0) {
        		final RunLengthEncoderNode node = runLengthList.get(0);
        		runLength = node.getIndex() + 1 - firstIndex;
        		gameValue = node.getGameValue();
        	} else {
        		final RunLengthEncoderNode firstNode = runLengthList.get(index - 1);
        		final RunLengthEncoderNode secondNode = runLengthList.get(index);
        		runLength = secondNode.getIndex() - firstNode.getIndex();
        		gameValue = secondNode.getGameValue();
        	}
        	
        	// We want 'W' instead of '1W'
        	if (runLength > 1) {
        		builder.append(runLength);
        	}
        	builder.append(gameValue.toString().charAt(0));
        }
        return builder.toString();
    }
    
    public void clearAllButLastNode() {
    	final int lastNodeIndex = runLengthList.size() - 1;
		final RunLengthEncoderNode lastNode = runLengthList.get(lastNodeIndex); 
		
		final long lastNodeEndIndex = lastNode.getIndex();
		
		runLengthList.clear();
		runLengthList.add(lastNode);
		
		firstIndex = lastNodeEndIndex + 1;
		previousNode = null;
    }
    
    public static class RunLengthEncoderNode {
        private long index;
        private final GameValue gameValue;
        
        public RunLengthEncoderNode(long index, GameValue gameValue) {
            this.index = index;
            this.gameValue = gameValue;
        }
        
        public long getIndex() {
            return index;
        }
        
        public GameValue getGameValue() {
            return gameValue;
        }
        
        public void setIndex(long index) {
            this.index = index;
        }
        
        @Override
        public String toString() {
            return String.valueOf(index) + String.valueOf(gameValue).charAt(0);
        }
    }

    /* The number of nodes! */
	public int getSize() {
		return runLengthList.size();
	}

	public void writeToOutputStream(DataOutputStream outputStream, int numberOfNodes) throws IOException {
		for (int index = 0; index < numberOfNodes; index++) {
			final RunLengthEncoderNode node = runLengthList.get(index);
			outputStream.writeLong(node.getIndex());
			outputStream.writeInt(node.getGameValue().getValue());
		}
	}
	
	public void readFromOutputStream(DataInputStream inputStream) throws IOException {
		runLengthList.clear();
		firstIndex = 0;
		previousNode = null;
		try {
			while (true) {
				final long index = inputStream.readLong();
				final GameValue gameValue = GameValueFactory.createTerminalGameValue(inputStream.readInt());
				final RunLengthEncoderNode node = new RunLengthEncoderNode(index, gameValue);
				runLengthList.add(node);
			}
		} catch (EOFException ex) {
			// Yay normal
		}
	}
}

