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
	
	private final SortedMap<Double, SortedSet<SearchResult>> searchResultSet;
	private final Comparator<SearchResult> comparator;
	
	public SearchResultSet()
	{
		this(new SearchRelevanceComparator());
	}
	
	public SearchResultSet(Comparator<SearchResult> comparator)
	{
		this.searchResultSet = new TreeMap<Double, SortedSet<SearchResult>>();
		this.comparator = comparator;
	}

	public void clear() 
	{
		searchResultSet.clear();
		
	}

	public boolean containsKey(Object arg0) 
	{
		return searchResultSet.containsKey(arg0);
	}

	public boolean containsValue(Object arg0) 
	{
		return searchResultSet.containsValue(arg0);
	}

	public SortedSet<SearchResult> get(Object arg0) 
	{
		
		return searchResultSet.get(arg0);
	}

	public boolean isEmpty() 
	{
		return searchResultSet.isEmpty();
	}

	public SortedSet<SearchResult> put(Double arg0, SortedSet<SearchResult> arg1) 
	{
		return searchResultSet.put(arg0, arg1);
	}

	public void putAll(Map<? extends Double, ? extends SortedSet<SearchResult>> m) 
	{
		searchResultSet.putAll(m);
		
	}

	public SortedSet<SearchResult> remove(Object arg0) 
	{
		return searchResultSet.remove(arg0);
	}

	public int size() 
	{
		return searchResultSet.size();
	}

	public Comparator<? super Double> comparator() 
	{
		return searchResultSet.comparator();
	}

	

	public Set<Entry<Double, SortedSet<SearchResult>>> entrySet() 
	{
		return searchResultSet.entrySet();
	}

	public Double firstKey() 
	{
		return searchResultSet.firstKey();
	}

	public SortedMap<Double, SortedSet<SearchResult>> headMap(Double arg0) 
	{
		return searchResultSet.headMap(arg0);
	}

	public Set<Double> keySet() 
	{
		return searchResultSet.keySet();
	}

	public Double lastKey() 
	{
		return searchResultSet.lastKey();
	}

	public SortedMap<Double, SortedSet<SearchResult>> subMap(Double arg0, Double arg1) 
	{
		return searchResultSet.subMap(arg0, arg1);
	}

	public SortedMap<Double, SortedSet<SearchResult>> tailMap(Double arg0) 
	{
		return searchResultSet.tailMap(arg0);
	}

	public Collection<SortedSet<SearchResult>> values() 
	{
		return searchResultSet.values();
	}
	
	
	public boolean addSearchResult(SearchResult searchResult)
	{
		boolean added = true;
		
		if(!searchResultSet.containsKey(searchResult.getRelevanceScore()))
		{
			SortedSet<SearchResult> searchResultSet = new TreeSet<SearchResult>(comparator);
			added = searchResultSet.add(searchResult);
		}
		else
		{
			added = searchResultSet.get(searchResult.getRelevanceScore()).add(searchResult);
		}
		
		return added;
	}
	
	
	public boolean removeSearchResult(SearchResult searchResult)
	{
		boolean removed = true;
		
		if(!searchResultSet.containsKey(searchResult.getRelevanceScore()))
		{
			removed = false;
			
		}
		else
		{
			removed = searchResultSet.get(searchResult.getRelevanceScore()).remove(searchResult);
		}
			
		return removed;
	}
	
}
