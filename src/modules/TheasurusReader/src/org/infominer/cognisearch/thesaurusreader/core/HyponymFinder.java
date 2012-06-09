package org.infominer.cognisearch.thesaurusreader.core;

import java.util.List;

/**
 * 
 * Finds a hyponym for a given word within the thesaurus.
 * There may be individual, specific implementations of this interface for a thesaurus.
 */
public interface HyponymFinder 
{

	/**
	 * Fetches the hyponyms for a given word.
	 * @param word The word for which hypoyms need to be found
	 * @return A list of hyponyms for the word.
	 */
	List<String> getHyponyms(String word);
}
