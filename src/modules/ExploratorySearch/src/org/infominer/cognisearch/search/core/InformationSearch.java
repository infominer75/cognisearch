package org.infominer.cognisearch.search.core;

import org.infominer.cognisearch.search.dto.SearchResult;
import org.infominer.cognisearch.search.dto.SearchResultSet;


/**
 * The base interface for a search. All search strategies derive from this interface to provide use-case
 * specific search methodologies.
 * @author prahaladdeshpande
 *
 */
public interface InformationSearch 
{

	
	/**
	 * Retrieves search result sets for the provided keyword.
	 * @param keyWord The keyword or phrase to be searched for
	 * @return {@link SearchResultSet} object that contains all the results for this search.
	 */
	SearchResultSet search(String keyWord);
}
