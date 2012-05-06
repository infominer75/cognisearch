/**
 * 
 */
package indexers;

import java.io.FileNotFoundException;

import exceptions.IndexingException;

/**
 * Interface to be implemented by all types of content indexers.
 * Each content indexer will handle indexing a particular type of content e.g. text documents, images, movies etc.
 *
 */
public interface ContentIndexer 
{

	void indexContent() throws FileNotFoundException, IndexingException;
}
