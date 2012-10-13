package org.infominer.cognisearch.search.dto;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import org.infominer.cognisearch.search.core.SearchRelevanceComparator;

public class SearchResultSet implements SortedMap<Double, SortedSet<SearchResult>> 
{
	
	private final SortedMap<Double, SortedSet<SearchResult>> searchResults;
	private final Comparator<SearchResult> comparator;
	
	public SearchResultSet()
	{
		this(new SearchRelevanceComparator());
	}
	
	public SearchResultSet(Comparator<SearchResult> comparator)
	{
		this.searchResults = new TreeMap<Double, SortedSet<SearchResult>>();
		this.comparator = comparator;
	}

	public void clear() 
	{
		searchResults.clear();
		
	}

	public boolean containsKey(Object arg0) 
	{
		return searchResults.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) 
	{
		return searchResults.containsValue(arg0);
	}

	public SortedSet<SearchResult> get(Object arg0) 
	{
		
		return searchResults.get(arg0);
	}

	public boolean isEmpty() 
	{
		return searchResults.isEmpty();
	}

	public SortedSet<SearchResult> put(Double arg0, SortedSet<SearchResult> arg1) 
	{
		return searchResults.put(arg0, arg1);
	}

	public void putAll(Map<? extends Double, ? extends SortedSet<SearchResult>> m) 
	{
		searchResults.putAll(m);
		
	}

	public SortedSet<SearchResult> remove(Object arg0) 
	{
		return searchResults.remove(arg0);
	}

	public int size() 
	{
		return searchResults.size();
	}

	public Comparator<? super Double> comparator() 
	{
		return searchResults.comparator();
	}

	

	public Set<Entry<Double, SortedSet<SearchResult>>> entrySet() 
	{
		return searchResults.entrySet();
	}

	public Double firstKey() 
	{
		return searchResults.firstKey();
	}

	public SortedMap<Double, SortedSet<SearchResult>> headMap(Double arg0) 
	{
		return searchResults.headMap(arg0);
	}

	public Set<Double> keySet() 
	{
		return searchResults.keySet();
	}

	public Double lastKey() 
	{
		return searchResults.lastKey();
	}

	public SortedMap<Double, SortedSet<SearchResult>> subMap(Double arg0, Double arg1) 
	{
		return searchResults.subMap(arg0, arg1);
	}

	public SortedMap<Double, SortedSet<SearchResult>> tailMap(Double arg0) 
	{
		return searchResults.tailMap(arg0);
	}

	public Collection<SortedSet<SearchResult>> values() 
	{
		return searchResults.values();
	}
	
	
	public boolean addSearchResult(SearchResult searchResult)
	{
		boolean added = true;
		
		if(!searchResults.containsKey(searchResult.getRelevanceScore()))
		{
			SortedSet<SearchResult> searchResultSet = new TreeSet<SearchResult>(comparator);
			added = searchResultSet.add(searchResult);
			searchResults.put(searchResult.getRelevanceScore(), searchResultSet);
		}
		else
		{
			added = searchResults.get(searchResult.getRelevanceScore()).add(searchResult);
		}
		
		return added;
	}
	
	
	public boolean removeSearchResult(SearchResult searchResult)
	{
		boolean removed = true;
		
		if(!searchResults.containsKey(searchResult.getRelevanceScore()))
		{
			removed = false;
			
		}
		else
		{
			removed = searchResults.get(searchResult.getRelevanceScore()).remove(searchResult);
		}
			
		return removed;
	}
	
}
