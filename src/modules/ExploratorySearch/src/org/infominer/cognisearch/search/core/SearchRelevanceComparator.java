package org.infominer.cognisearch.search.core;

import java.util.Comparator;

import org.infominer.cognisearch.search.dto.SearchResult;

public class SearchRelevanceComparator implements Comparator<SearchResult> 
{

	public int compare(SearchResult arg0, SearchResult arg1) 
	{
		if(arg0.getRelevanceScore() < arg1.getRelevanceScore())
		{
			return -1;
		}
		
		return (arg0.getRelevanceScore() == arg1.getRelevanceScore()) ? 0 : 1;
	}

}
