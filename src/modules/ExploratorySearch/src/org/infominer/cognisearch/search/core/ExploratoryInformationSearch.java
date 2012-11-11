package org.infominer.cognisearch.search.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.util.Version;
import org.infominer.cognisearch.indexanalyzer.TermSearcher;
import org.infominer.cognisearch.search.core.exceptions.SearchException;
import org.infominer.cognisearch.search.dto.SearchResult;
import org.infominer.cognisearch.search.dto.SearchResultSet;
import org.infominer.cognisearch.thesaurusreader.ThesaurusManager;
import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusOperationException;
import org.infominer.cognisearch.thesaurusreader.providers.ThesaurusProvider;




public class ExploratoryInformationSearch implements InformationSearch 
{
	private final TermSearcher termSearcher;
	private final Properties thesaurusProperties;
	
	private static final String SEARCH_TERM_FIELD = "content";
	private static final String FILE_NAME_FIELD = "path";
	private static final String DATE_FIELD = "dateCreated";
	
	private final Integer DEFAULT_PAGE_SIZE = 10;
	
	public ExploratoryInformationSearch(String indexPath,Properties thesaurusProperties) throws IOException
	{
		this(new TermSearcher(indexPath), thesaurusProperties);
	}
	
	ExploratoryInformationSearch(TermSearcher termSearcher, Properties thesaurusProperties)
	{
		this.termSearcher = termSearcher;
		this.thesaurusProperties = thesaurusProperties;
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
		catch(ThesaurusOperationException tex)
		{
			throw new SearchException(tex);
		}
		catch(IllegalArgumentException iex)
		{
			throw new SearchException(iex);
		}
		catch(ClassNotFoundException cex)
		{
			throw new SearchException(cex);
		}
		catch(InstantiationException instEx)
		{
			throw new SearchException(instEx);
		}
		catch(IllegalAccessException illegalEx)
		{
			throw new SearchException(illegalEx);
		}
		catch(ThesaurusInitializationException initEx)
		{
			throw new SearchException(initEx);
		}
		catch(InvalidTokenOffsetsException invalidTokenOffsetsEx)
		{
			throw new SearchException(invalidTokenOffsetsEx);
		}
		
		return resultSet;
		 
	}
	
	public SearchResultSet search(String keyWord,Integer hitsPerPage) throws ParseException, IOException, ThesaurusOperationException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, ThesaurusInitializationException, InvalidTokenOffsetsException 
	{
		Query query = buildQueryForTerm(keyWord);
		Set<ScoreDoc> documentSet = termSearcher.search(query, buildTopScoreDocCollector(hitsPerPage));

		SearchResultSet  searchResultSet = new SearchResultSet();
		
		IndexSearcher indexSearcher = termSearcher.getIndexSearcher();
		for(ScoreDoc scoreDoc : documentSet)
		{
			Document document = indexSearcher.doc(scoreDoc.doc);
			
			String fileName = document.get(FILE_NAME_FIELD);
			
			String[] matchedTextFragments = getMatchedTextFragmentsForQuery(query, document, SEARCH_TERM_FIELD, new StandardAnalyzer(Version.LUCENE_35));
			
			SearchResult searchResult = new SearchResult(fileName, (double) scoreDoc.score, matchedTextFragments);
			
			searchResultSet.addSearchResult(searchResult);
		}
		
		return searchResultSet;
	}

	private Query buildQueryForTerm(String term) throws ParseException, ThesaurusOperationException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, ThesaurusInitializationException
	{
		
		BooleanQuery parentSearchQuery = new BooleanQuery();
		
		QueryParser queryParser = new QueryParser(Version.LUCENE_35, SEARCH_TERM_FIELD, new StandardAnalyzer(Version.LUCENE_35));
		
		parentSearchQuery.add(queryParser.parse(term),Occur.SHOULD);
		
		Set<String> relatedTerms = getRelatedTerms(term);
		
		for(String relatedTerm : relatedTerms)
		{
			QueryParser qp = new QueryParser(Version.LUCENE_35, SEARCH_TERM_FIELD, new StandardAnalyzer(Version.LUCENE_35));
			Query childQuery = qp.parse(relatedTerm);
			parentSearchQuery.add(childQuery, Occur.SHOULD);
		}
		return parentSearchQuery;
	}
	
	private TopScoreDocCollector buildTopScoreDocCollector(Integer hitsPerPage)
	{
		return TopScoreDocCollector.create(hitsPerPage, true);
	}
	
	private Set<String> getRelatedTerms(String term) throws ThesaurusOperationException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, ThesaurusInitializationException
	{
		Set<String> relatedTerms = new HashSet<String>();
		Thesaurus thesaurus = ThesaurusManager.newThesaurus(thesaurusProperties);
		
		relatedTerms.addAll(thesaurus.getSynonymFinder().getRelatedTermsForKeyword(PartOfSpeech.NOUN, term));
		relatedTerms.addAll(thesaurus.getHypernymFinder().getRelatedTermsForKeyword(PartOfSpeech.NOUN, term));
		relatedTerms.addAll(thesaurus.getHyponymFinder().getRelatedTermsForKeyword(PartOfSpeech.NOUN, term));
		
		return relatedTerms;
		
		
	}
	
	private String[] getMatchedTextFragmentsForQuery(Query query, Document matchedDocument, 
			String fieldName, Analyzer analyzer) throws IOException, InvalidTokenOffsetsException
	{
		String fileContent = matchedDocument.get(fieldName);
		TokenStream stream = TokenSources.getTokenStream(fieldName, fileContent, analyzer);
		QueryScorer scorer = new QueryScorer(query, fieldName);
		
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
		Highlighter highlighter = new Highlighter(scorer);
		highlighter.setTextFragmenter(fragmenter);
		highlighter.setMaxDocCharsToAnalyze(Integer.MAX_VALUE);
		
		String[] matchedFragments= highlighter.getBestFragments(stream, fileContent, 5);
		
		return matchedFragments;
	}

}
