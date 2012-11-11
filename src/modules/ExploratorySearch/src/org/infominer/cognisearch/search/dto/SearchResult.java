package org.infominer.cognisearch.search.dto;

/**
 * Encapsulates a search result
 * @author infominer
 *
 */

public class SearchResult 
{
	private final String fileName;
	
	private final Double relevanceScore;
	
	private final String[] matchedDocumentFragments;
	
	
	public SearchResult(String fileName, Double relevanceScore)
	{
		this(fileName, relevanceScore, new String[]{});
	}
	
	public SearchResult(String fileName, Double relevanceScore, String[] matchedDocumentFragments)
	{
		this.fileName = fileName;
		this.relevanceScore = relevanceScore;
		this.matchedDocumentFragments = matchedDocumentFragments;
	}

	public String getFileName() 
	{
		return fileName;
	}
	

	public Double getRelevanceScore() 
	{
		return relevanceScore;
	}

	public String[] getMatchedDocumentFragments()
	{
		return matchedDocumentFragments;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (relevanceScore == null) {
			if (other.relevanceScore != null)
				return false;
		} else if (!relevanceScore.equals(other.relevanceScore))
			return false;
		return true;
	}


	@Override
	public String toString() 
	{
		StringBuilder stringRepresentation = new StringBuilder();
		
		stringRepresentation.append(String.format("%s:%s", "File", fileName));
		stringRepresentation.append(System.getProperty("line.separator"));
		stringRepresentation.append(String.format("%s:%f", "Relevance ranking:", relevanceScore));
		stringRepresentation.append(System.getProperty("line.separator"));
		stringRepresentation.append(String.format("Matched text fragments:"));
		stringRepresentation.append(System.getProperty("line.separator"));
		stringRepresentation.append("\t\t\t\t");
		for(String fragment: matchedDocumentFragments)
		{
			stringRepresentation.append(fragment);
			stringRepresentation.append(System.getProperty("line.separator"));
			stringRepresentation.append("\t\t\t");
		}
		
		return stringRepresentation.toString();
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((relevanceScore == null) ? 0 : relevanceScore.hashCode());
		return result;
	}

}
