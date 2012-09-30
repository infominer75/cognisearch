package org.infominer.cognisearch.search.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.infominer.cognisearch.indexanalyzer.TermSearcher;
import org.infominer.cognisearch.search.core.exceptions.SearchException;
import org.infominer.cognisearch.search.dto.SearchResultSet;

public class ExploratoryInformationSearchUTest 
{
	
	@Mock
	private TermSearcher termSearcherMock;
	
	@Mock
	private ScoreDoc scoreDocMock1;
	
	@Mock
	private ScoreDoc scoreDocMock2;
	
	private Set<ScoreDoc> scoreDocSetMock;
	
	private ExploratoryInformationSearch underTest;
	
	@Before
	public void setUp() throws Exception
	{

		MockitoAnnotations.initMocks(this);
		
		scoreDocSetMock = new HashSet<ScoreDoc>();
		scoreDocSetMock.add(scoreDocMock1);
		scoreDocSetMock.add(scoreDocMock2);
		
		when(termSearcherMock.search(any(Query.class), any(TopScoreDocCollector.class))).thenReturn(scoreDocSetMock);
		underTest = new ExploratoryInformationSearch(termSearcherMock);
	}
	
	@Test
	@Ignore
	
	public void shouldReturnSearchResultSet() throws Exception
	{
		SearchResultSet searchResultSet = underTest.search("test");
		assertTrue(searchResultSet.size() > 0);
	}
	

}
