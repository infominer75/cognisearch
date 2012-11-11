package org.infominer.cognisearch.finder;

import java.io.IOException;

import org.infominer.cognisearch.search.core.exceptions.SearchException;
import org.infominer.cognisearch.search.dto.SearchResultSet;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.infominer.cognisearch.search.dto.SearchResult;


public class CognisearchSearchEngine 
{

	private static CognisearchFinder FINDER;
	
	public static void main(String[] args) throws IOException, SearchException
	{
		
		System.out.println("Cognisearch Search Engine(v 0.1 - alpha)");
		
		
		
		if(args.length == 0)
		{
			StringBuilder usageString = new StringBuilder("Usage:");
			usageString.append("CognisearchFinder.jar <search terms>");
			usageString.append(System.getProperty("line.separator"));
			usageString.append("<search_terms>:= keyword1,keyword2,...keywordn");
			usageString.append(System.getProperty("line.separator"));
			usageString.append("Enclose keyword within the \" character if the keyword is a phrase.");
			usageString.append("Keywords separated by the space character are considered " +
					"to belong to a single phrase");
			System.out.println(usageString.toString());
			return;
		}
		FINDER = new CognisearchFinder();
		Map<String, SearchResultSet> results =  FINDER.find(args[0]);
		System.out.println("=================================================");
		System.out.println("Searching for term " + args[0]);
		System.out.println("=================================================");
		for(String key : results.keySet())
		{
			StringBuilder resultsDisplayBuilder = new StringBuilder();
			resultsDisplayBuilder.append(key);
			resultsDisplayBuilder.append(System.getProperty("line.separator"));
			SearchResultSet resultSet = results.get(key);
			for(SortedSet<SearchResult> resultCollection : resultSet.values())
			{
				resultsDisplayBuilder.append("\t\t");
				
				for(SearchResult result : resultCollection)
				{
					resultsDisplayBuilder.append("\t\t");
					resultsDisplayBuilder.append(result.getFileName());
					resultsDisplayBuilder.append(System.getProperty("line.separator"));
					resultsDisplayBuilder.append(formatTextFragmentsDisplay(result.getMatchedDocumentFragments()));
					resultsDisplayBuilder.append(System.getProperty("line.separator"));
				}
				resultsDisplayBuilder.append(System.getProperty("line.separator"));
			}
			resultsDisplayBuilder.append(System.getProperty("line.separator"));
			
			System.out.println(resultsDisplayBuilder.toString());
		}
		
		
		
	}
	
	private static String formatTextFragmentsDisplay(String[] textFragments)
	{
		StringBuilder textFragmentDisplayStringBuilder = new StringBuilder();
		for(String textFragment : textFragments)
		{
			textFragmentDisplayStringBuilder.append("\t\t\t--");
			textFragmentDisplayStringBuilder.append(textFragment);
			textFragmentDisplayStringBuilder.append(System.getProperty("line.separator"));
			
		}
		
		return textFragmentDisplayStringBuilder.toString();
	}
	
}
