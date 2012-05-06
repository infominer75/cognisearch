package indexers;

/**
 * 
 * Specifies the indexing mode for the Indexer.
 *
 */
import org.apache.lucene.index.IndexWriterConfig.OpenMode;

public enum IndexingMode 
{
	CREATE { OpenMode getLuceneIndexMode() {return OpenMode.CREATE; }},
	CREATE_OR_APPEND { OpenMode getLuceneIndexMode() { return OpenMode.CREATE_OR_APPEND;}},
	APPEND { OpenMode getLuceneIndexMode() { return OpenMode.APPEND;}};

	
	abstract OpenMode getLuceneIndexMode();
	
}
