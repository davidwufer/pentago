package endgamesolver;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

// TODO: what if the number of files exceeds MAX_INT?
public class EndGameDBCache {
	
	
	private class Lru {
		
		private final LruInternal<File, RunLengthEncoder> lru;
		
		public Lru(int maxEntries) {
			this.lru = new LruInternal<File, RunLengthEncoder>(maxEntries);
		}
		
		private class LruInternal<K, V> extends LinkedHashMap<K, V> {
			private final int maxEntries;
			
			public LruInternal(final int maxEntries) {
				super(maxEntries + 1, 1.0f, true);
				this.maxEntries = maxEntries;
			}
			
		    @Override
		    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
		        return super.size() > maxEntries;
		    }
		}
	}
	
	private class FileInfoArray {
		
		private final FileInfoArrayEntry[] entries;
		
		public FileInfoArray(int arraySize) {
			this.entries = new FileInfoArrayEntry[arraySize];
		}
		
		
		private class FileInfoArrayEntry {
			final long startIndex;
			final long endIndex;
			final File file;
			
			public FileInfoArrayEntry(long startIndex, long endIndex, File file) {
				this.startIndex = startIndex;
				this.endIndex = endIndex;
				this.file = file;
			}
			
			public long getStartIndex() {
				return startIndex;
			}
			
			public long getEndIndex() {
				return endIndex;
			}
			
			public File getFile() {
				return file;
			}
		}
	}
}
