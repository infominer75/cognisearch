package org.infominer.cognisearch.search.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class SearchResultSet implements Set<SearchResult> 
{

	private TreeSet<SearchResult> searchResultSet;
	
	
	public SearchResultSet()
	{
		searchResultSet = new TreeSet<SearchResult>();
	}
	
	public boolean add(SearchResult e) 
	{
		return searchResultSet.add(e);
	}

	public boolean addAll(Collection<? extends SearchResult> c) 
	{
		return searchResultSet.addAll(c);
	}

	public void clear() 
	{
		searchResultSet.clear();
		
	}

	public boolean contains(Object o) 
	{
		if(!(o instanceof SearchResult))
		{
			return false;
		}
			
		return searchResultSet.contains((SearchResult)o);
	}

	public boolean containsAll(Collection<?> c) 
	{
		return searchResultSet.containsAll(c);
	}

	public boolean isEmpty() 
	{
		return searchResultSet.isEmpty();
	}

	public Iterator<SearchResult> iterator() 
	{
		
		return searchResultSet.iterator();
	}

	public boolean remove(Object o) 
	{
		if(!(o instanceof SearchResult))
		{
			return false;
		}
		
		return searchResultSet.remove((SearchResult)o);
	}

	public boolean removeAll(Collection<?> c) 
	{
		return searchResultSet.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) 
	{
		return searchResultSet.retainAll(c);
	}

	public int size() 
	{
		return searchResultSet.size();
	}

	public Object[] toArray() 
	{
		return searchResultSet.toArray();
	}

	public <T> T[] toArray(T[] a) 
	{
		return searchResultSet.toArray(a);
	}

	
	
}
