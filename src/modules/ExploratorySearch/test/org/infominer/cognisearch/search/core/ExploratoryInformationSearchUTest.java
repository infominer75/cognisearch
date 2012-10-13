package org.infominer.cognisearch.search.core;

import indexanalyzers.IndexDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;


import org.infominer.cognisearch.indexanalyzer.TermSearcher;
import org.infominer.cognisearch.search.dto.SearchResult;
import org.infominer.cognisearch.search.dto.SearchResultSet;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;



public class ExploratoryInformationSearchUTest extends LuceneTest
{
	private ExploratoryInformationSearch underTest;
	
	@Before
	public void setUp() throws Exception
	{
		super.buildIndexDirectory();
		
		Properties thesaurusProperties = new Properties();
		
		thesaurusProperties.setProperty("DictionaryPath", "/Users/prahaladdeshpande/java_progs/Java Libs/Wordnet-3.0");
		
		indexTestData();
		
		TermSearcher termSearcher = new TermSearcher(super.getIndexSearcher());
		underTest = new ExploratoryInformationSearch(termSearcher, thesaurusProperties);
	}
	
	@Test
	public void shouldTriggerSearch() throws Exception
	{
		SearchResultSet searchResultSet = underTest.search("car");
		assertFalse(searchResultSet.size() == 0);
		boolean relevantDocumentsListed = false;
		Set<String> fileNamesInResultSet = new HashSet<String>();
		
		for(SortedSet<SearchResult> resultSet : searchResultSet.values())
		{
			assertTrue(resultSet.size() == 1);
			for(SearchResult searchResult : resultSet)
			{
				fileNamesInResultSet.add(searchResult.getFileName());
			}
		}
		
		assertTrue(fileNamesInResultSet.contains("/home/test1.txt"));
		assertTrue(fileNamesInResultSet.contains("/home/automobile_desc.txt"));
		
		
	}
	
	private void indexTestData() throws Exception
	{
		List<IndexEntryDescriptor> indexContents = new ArrayList<IndexEntryDescriptor>();
		
		IndexEntryDescriptor descriptor1 = new IndexEntryDescriptor("/home/test1.txt","car drives through the country lane");
		IndexEntryDescriptor descriptor2 = new IndexEntryDescriptor("/home/automobile_desc.txt", "the traveller will sleep in the automotive_vehicle");
		IndexEntryDescriptor descriptor3 = new IndexEntryDescriptor("/home/sleep_esc.txt", "A state of rest or slumber");

		indexContents.add(descriptor1);
		indexContents.add(descriptor2);
		indexContents.add(descriptor3);
		super.writeToIndex(indexContents);
	}

}
