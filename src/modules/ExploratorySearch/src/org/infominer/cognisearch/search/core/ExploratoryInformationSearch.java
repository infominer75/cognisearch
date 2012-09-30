package org.infominer.cognisearch.search.core;

import java.io.IOException;
import java.util.Set;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
import org.infominer.cognisearch.indexanalyzer.TermSearcher;
import org.infominer.cognisearch.search.core.exceptions.SearchException;
import org.infominer.cognisearch.search.dto.SearchResult;
import org.infominer.cognisearch.search.dto.SearchResultSet;



public class ExploratoryInformationSearch implements InformationSearch 
{
	private final TermSearcher termSearcher;
	private static final String SEARCH_TERM_FIELD = "content";
	private static final String FILE_NAME_FIELD = "path";
	private static final String DATE_FIELD = "dateCreated";
	
	private final Integer DEFAULT_PAGE_SIZE = 10;
	
	public ExploratoryInformationSearch(String indexPath) throws IOException
	{
		this(new TermSearcher(indexPath));
	}
	
	ExploratoryInformationSearch(TermSearcher termSearcher)
	{
		this.termSearcher = termSearcher;
	}
	
	
	public SearchResultSet search(String keyWord) throws SearchException
	{
		SearchResultSet resultSet = null;
		try
		{
			resultSet = search(keyWord, DEFAULT_PAGE_SIZE);
		}
		catch(ParseException parseEx)
		{
			throw new SearchException(parseEx);
		}
		catch(IOException iex)
		{
			throw new SearchException(iex);
		}
		
		return resultSet;
		 
	}
	
	public SearchResultSet search(String keyWord,Integer hitsPerPage) throws ParseException, IOException 
	{
		Query query = buildQueryForTerm(keyWord);
		Set<ScoreDoc> documentSet = termSearcher.search(query, buildTopScoreDocCollector(hitsPerPage));

		SearchResultSet  searchResultSet = new SearchResultSet();
		
		IndexSearcher indexSearcher = termSearcher.getIndexSearcher();
		for(ScoreDoc scoreDoc : documentSet)
		{
			Document document = indexSearcher.doc(scoreDoc.doc);
			
			String fileName = document.get(FILE_NAME_FIELD);
			
			
			SearchResult searchResult = new SearchResult(fileName, (double) scoreDoc.score);
			
			searchResultSet.addSearchResult(searchResult);
		}
		
		return searchResultSet;
	}

	private Query buildQueryForTerm(String term) throws ParseException
	{
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, SEARCH_TERM_FIELD, new StandardAnalyzer(Version.LUCENE_35));
		return queryParser.parse(term);
		
	}
	
	private TopScoreDocCollector buildTopScoreDocCollector(Integer hitsPerPage)
	{
		return TopScoreDocCollector.create(hitsPerPage, true);
	}

}
