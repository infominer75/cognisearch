package org.infominer.cognisearch.thesaurusreader.core;

import java.util.List;

/**
 * 
 * Finds a synonym for a word in the thesaurus. 
 * There may be individual, specific implementations of this interface for a particular type of thesaurus.
 */

public interface SynonymFinder 
{

	List<String> getSynonyms(String word);
}
