/**
 * 
 */
package org.infominer.cognisearch.thesaurusreader.core;

import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetHypernymFinder;
import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetHyponymFinder;
import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetSynonymFinder;

/**
 * An interface that serves to abstract external applications from the underlying thesaurus implementation.
 * 
 */
public interface Thesaurus
{

	/**
	 * Obtains the {@link WordnetSynonymFinder} for this thesaurus.
	 * @return The {@link WordnetSynonymFinder} instance for this thesaurus
	 */
	WordnetSynonymFinder getSynonymFinder();
	
	
	/**
	 * Obtains the {@link HyperymFinder} for this thesaurus.
	 * @return The {@link WordnetHypernymFinder} instance for this thesaurus
	 */
	WordnetHypernymFinder getHypernymFinder();
	
	
	/**
	 * Obtains the {@link WordnetHyponymFinder} for this thesaurus.
	 * @return The {@link WordnetHyponymFinder} instance for this thesaurus
	 */
	WordnetHyponymFinder getHyponymFinder();
	
	/**
	 * Close the current dictionary file handle and release resources
	 */
	void close();
	
}
