/**
 * 
 */
package org.infominer.cognisearch.thesaurusreader.core;

/**
 * An interface that serves to abstract external applications from the underlying thesaurus implementation.
 * 
 */
public interface Thesaurus
{

	/**
	 * Obtains the {@link SynonymFinder} for this thesaurus.
	 * @return The {@link SynonymFinder} instance for this thesaurus
	 */
	SynonymFinder getSynonymFinder();
	
	
	/**
	 * Obtains the {@link HyperymFinder} for this thesaurus.
	 * @return The {@link HypernymFinder} instance for this thesaurus
	 */
	HypernymFinder getHypernymFinder();
	
	
	/**
	 * Obtains the {@link HyponymFinder} for this thesaurus.
	 * @return The {@link HyponymFinder} instance for this thesaurus
	 */
	HyponymFinder getHyponymFinder();
	
}
