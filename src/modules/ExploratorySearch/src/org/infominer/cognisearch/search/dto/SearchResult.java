package org.infominer.cognisearch.search.dto;

/**
 * Encapsulates a search result
 * @author prahaladdeshpande
 *
 */

public class SearchResult implements Comparable<SearchResult>
{
	private String fileName;
	
	private int relevanceRank;

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	
	

	public int getRelevanceRank() 
	{
		return relevanceRank;
	}

	public void setRelevanceRank(int relevanceRank) 
	{
		this.relevanceRank = relevanceRank;
	}

	public int compareTo(SearchResult other) 
	{
		if(relevanceRank < other.relevanceRank)
		{
			return -1;
		}
		
		return (relevanceRank == other.relevanceRank) ? 0 : 1;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof SearchResult))
		{
			return false;
		}
		
		if(this == o)
		{
			return true;
		}
		
		SearchResult other = (SearchResult)o;
		
		return fileName.equals(other.fileName);
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = 0;
		
		for(int  i = 0; i < fileName.length(); i++)
		{
			hashCode =  hashCode ^ (fileName.charAt(i));
		}
		
		return hashCode;
	}

}
