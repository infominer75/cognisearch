package org.infominer.cognisearch.thesaurusreader.core;

import java.util.List;

/**
 * 
 * Finds a hypernym for a specific word within the thesaurus.
 * There may be individual, specific implementations of this interface for a particular type of thesaurus.
 */
public interface HypernymFinder 
{

	List<String> getHypernyms(String word);
}
