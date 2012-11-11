package org.infominer.cognisearch.finder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.infominer.cognisearch.search.core.ExploratoryInformationSearch;
import org.infominer.cognisearch.search.core.exceptions.SearchException;
import org.infominer.cognisearch.search.dto.SearchResultSet;
import org.infominer.cognisearch.utils.CognisearchProperties;

import org.infominer.cognisearch.utils.PropertiesReader;


public class CognisearchFinder 
{

	private final CognisearchProperties cogniSearchProperties;
	
	private static final String COGNISEARCH_INDEX_PROPERTIES_FILENAME = "cognisearch.index.properties";
	private static final String COGNISEARCH_THESAURUS_PROPERTIES_FILENAME = "cognisearch.thesaurus.properties";
	
	private static final String COGNISEARCH_INDEX_PATH_PROPERTY_NAME = "cognisearch.index.path";
	
	
	private final ExploratoryInformationSearch searchEngine;
	private final SearchTermTokenizer searchTermTokenizer;
	
	
	public CognisearchFinder() throws IOException
	{
		
		cogniSearchProperties = new CognisearchProperties();
		initializeProperties();
		
		Properties indexProperties = cogniSearchProperties.get(COGNISEARCH_INDEX_PROPERTIES_FILENAME);
		Properties thesaurusProperties = cogniSearchProperties.get(COGNISEARCH_THESAURUS_PROPERTIES_FILENAME);
		
		searchEngine = new ExploratoryInformationSearch(indexProperties.getProperty(COGNISEARCH_INDEX_PATH_PROPERTY_NAME), thesaurusProperties);
		this.searchTermTokenizer = new SearchTermTokenizer();
		
	}
	
	public CognisearchFinder(CognisearchProperties cogniSearchProperties, ExploratoryInformationSearch searchEngine, 
			SearchTermTokenizer tokenizer)
	{
		this.cogniSearchProperties = cogniSearchProperties;
		this.searchEngine = searchEngine;
		this.searchTermTokenizer = tokenizer;
		
	}
	

	public Map<String, SearchResultSet> find(String query) throws SearchException
	{
		Map<String,SearchResultSet> searchResultCollection = new HashMap<String, SearchResultSet>();
		
		Set<String> searchTerms = searchTermTokenizer.tokenize(query);
		
		for(String searchTerm : searchTerms)
		{
			SearchResultSet searchResultSet = searchEngine.search(searchTerm);
			searchResultCollection.put(searchTerm, searchResultSet);
		}
		
		return searchResultCollection;
		
	}
	
	
	private void initializeProperties() throws IOException
	{
		/**
		 * read the following property files.
		 * cognisearch.index.properties - Stores properties corresponding to the lucene index
		 * cognisearch.thesaurus.properties - Stores properties corresponding to Thesaurus.
		 */
		PropertiesReader propertiesReader = new PropertiesReader(COGNISEARCH_INDEX_PROPERTIES_FILENAME);
		Properties indexProperties = propertiesReader.load();
		cogniSearchProperties.put(COGNISEARCH_INDEX_PROPERTIES_FILENAME, indexProperties);
		
		propertiesReader = new PropertiesReader(COGNISEARCH_THESAURUS_PROPERTIES_FILENAME);
		Properties thesaurusProperties = propertiesReader.load();
		cogniSearchProperties.put(COGNISEARCH_THESAURUS_PROPERTIES_FILENAME, thesaurusProperties);
		
		
	}

}
