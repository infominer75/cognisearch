package org.infominer.cognisearch.finder;

import java.util.HashSet;
import java.util.Set;

import org.infominer.cognisearch.search.core.ExploratoryInformationSearch;
import org.infominer.cognisearch.search.core.exceptions.SearchException;
import org.infominer.cognisearch.utils.CognisearchProperties;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.InOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;

public class CognisearchFinderUTest 
{
	
	@Mock
	private CognisearchProperties cogniSearchPropertiesMock;
	
	@Mock
	private ExploratoryInformationSearch searchEngineMock;
	
	@Mock
	private SearchTermTokenizer searchTermTokenizerMock;
	
	
	private CognisearchFinder underTest;
	
	
	@Before
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		underTest = new CognisearchFinder(cogniSearchPropertiesMock, searchEngineMock, searchTermTokenizerMock);
	}
	
	@Test
	public void shouldInvokeSearchEngine() throws Exception
	{
		Set<String> queryTermsMock = new HashSet<String>();
		queryTermsMock.add("test");
		queryTermsMock.add("query");
		when(searchTermTokenizerMock.tokenize("test,query")).thenReturn(queryTermsMock);
		
		underTest.find("test,query");
		
		verify(searchEngineMock,times(2)).search(anyString());
	}
	
	@Test
	public void shouldTokenizeSearchTermsPriorToSearch() throws Exception
	{
		InOrder invocationOrder = inOrder(searchTermTokenizerMock, searchEngineMock);
		Set<String> queryTermsMock = new HashSet<String>();
		queryTermsMock.add("test");
		queryTermsMock.add("query");
		when(searchTermTokenizerMock.tokenize("test,query")).thenReturn(queryTermsMock);
		underTest.find("test,query");
		invocationOrder.verify(searchTermTokenizerMock).tokenize("test,query");
		invocationOrder.verify(searchEngineMock, times(2)).search(anyString());
	}
	

}
