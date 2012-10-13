package org.infominer.cognisearch.indexanalyzer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.sun.source.tree.AssertTree;

public class TermSearcherUTest 
{

	@Mock
	private IndexSearcher indexSearcherMock;
	
	@Mock
	private TopScoreDocCollector topScoreDocCollectorMock;
	
	@Mock
	private TopDocs topDocsMock;
	
	@Mock
	private ScoreDoc scoreDocMock1;
	
	@Mock
	private ScoreDoc scoreDocMock2;
	
	/*@Mock
	private Document documentMock1;
	
	@Mock
	private Document documentMock2;*/
	
	@Mock
	private Query queryMock;
	
	
	private TermSearcher underTest;
	
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		
		scoreDocMock1.doc = 1;
		scoreDocMock2.doc = 2;
		
		
		ScoreDoc[] mockDocuments = new ScoreDoc[] { scoreDocMock1, scoreDocMock2};		
		topDocsMock.scoreDocs = mockDocuments;
		when(topScoreDocCollectorMock.topDocs()).thenReturn(topDocsMock);		
		
	
		
		underTest = new TermSearcher(indexSearcherMock);
	}
	
	@Test
	public void shouldSearchForTerms() throws Exception
	{
		
		Set<ScoreDoc> testDocumentSet = underTest.search(queryMock, topScoreDocCollectorMock);
		assertTrue(testDocumentSet.size() == 2);
		assertTrue(testDocumentSet.contains(scoreDocMock1));
		assertTrue(testDocumentSet.contains(scoreDocMock2));
		
	}
	
	@Test
	public void shouldFetchDocumentsForTerms() throws Exception
	{
		Document documentMock1 = new Document();
		Document documentMock2 = new Document();
		
		when(indexSearcherMock.doc(1)).thenReturn(documentMock1);
		when(indexSearcherMock.doc(2)).thenReturn(documentMock2);
		
		Set<Document> documentSet = underTest.getDocumentsForTerm(queryMock, topScoreDocCollectorMock);
		
		assertTrue(documentSet.size() > 0);
		
		assertTrue(documentSet.contains(documentMock1));
		assertTrue(documentSet.contains(documentMock2));
		
	}
	
}
