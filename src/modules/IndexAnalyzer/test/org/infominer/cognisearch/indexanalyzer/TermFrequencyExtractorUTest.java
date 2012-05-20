package org.infominer.cognisearch.indexanalyzer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermFreqVector;
import org.infominer.cognisearch.indexanalyzer.TermFrequencyExtractor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import org.infominer.cognisearch.indexanalyzer.dto.TermFrequencyDetail;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import static org.junit.Assert.*;

public class TermFrequencyExtractorUTest 
{

	private static final String FIELD_NAME = "content";
	
	
	
	@Mock
	private IndexReader indexReaderMock;
	
	@Mock
	private TermFreqVector termFreqVector1;
	
	@Mock
	private TermFreqVector termFreqVector2;
	
	private TermFrequencyExtractor underTest;
	
	private String[] FIRST_DOC_TERMS = new String[] {"Python", "Java", "C++", "Erlang"};
	
	private String[] SECOND_DOC_TERMS = new String[] {"Integer", "Long", "String", "Structure"};
	
	private int[] FIRST_DOC_TERM_FRQ = new int[] { 10, 20, 5, 3};
	
	private int[] SECOND_DOC_TERM_FRQ = new int[] { 3, 6, 9, 12};
	
	
	
	@Before
	public void setUp() throws IOException
	{
		MockitoAnnotations.initMocks(this);
		underTest = new TermFrequencyExtractor(indexReaderMock);
		
		when(indexReaderMock.maxDoc()).thenReturn(2);
		
		when(indexReaderMock.getTermFreqVector(0, FIELD_NAME)).thenReturn(termFreqVector1);
		when(indexReaderMock.getTermFreqVector(1, FIELD_NAME)).thenReturn(termFreqVector2);
		
		
		when(termFreqVector1.getTerms()).thenReturn(FIRST_DOC_TERMS);
		when(termFreqVector1.getTermFrequencies()).thenReturn(FIRST_DOC_TERM_FRQ);

		when(termFreqVector2.getTerms()).thenReturn(SECOND_DOC_TERMS);
		when(termFreqVector2.getTermFrequencies()).thenReturn(SECOND_DOC_TERM_FRQ);
		
		
	}
	
	@Test
	public void shouldFetchAllTermFrequencies() throws IOException
	{
		Set<TermFrequencyDetail> termDetails = underTest.getAllTermFrequencyDetails();
		assertTrue(termDetails.size() == 8);
	}
	
	@Test
	public void shouldNotAddDuplicateTerms() throws IOException
	{
		String[] mockTermsForSecondDocument = {"Python", "Long", "Integer", "Structure"};
		
		when(termFreqVector2.getTerms()).thenReturn(mockTermsForSecondDocument);
		Set<TermFrequencyDetail> termDetails = underTest.getAllTermFrequencyDetails();
		assertTrue(termDetails.size() == 7);
	}
	
	@Test
	public void shouldAddTermFrequencyIfDuplicateTerm() throws IOException
	{
		String[] mockTermsForSecondDocument = {"Python", "Long", "Integer", "Structure"};
		
		when(termFreqVector2.getTerms()).thenReturn(mockTermsForSecondDocument);
		when(termFreqVector2.getTermFrequencies()).thenReturn(new int[] {4,5,6,7});
		
		Set<TermFrequencyDetail> termDetails = underTest.getAllTermFrequencyDetails();
		
		TermFrequencyDetail pythonTermDetail = null;
		
		for(TermFrequencyDetail termDetail : termDetails)
		{
			if(termDetail.getTermValue().equalsIgnoreCase("Python"))
			{
				pythonTermDetail = termDetail;
				break;
			}
		}
		
		assertTrue(pythonTermDetail.getTermFrequency() == 14);
	}
	
	@Test
	public void shouldReturnWordWithHighestFrequencyInDocument() throws IOException
	{
		List<TermFrequencyDetail> frequentTerms = underTest.getDocumentTermWithHighestFrequency(1);
		assertTrue(frequentTerms.size() == 1);
		assertTrue(frequentTerms.get(0).getTermValue().equals("Structure"));
	}
	
	@Test
	public void shouldReturnAllTermsWithSameFrequencyInDocument() throws IOException
	{
		when(termFreqVector2.getTerms()).thenReturn(new String[] {"C++", "Python", "Java", "Scala"});
		when(termFreqVector2.getTermFrequencies()).thenReturn(new int[] {20, 20, 5, 10});
		
		List<TermFrequencyDetail> frequentTerms = underTest.getDocumentTermWithHighestFrequency(1);
		
		assertTrue(frequentTerms.size() == 2);
		assertTrue(frequentTerms.contains(new TermFrequencyDetail("C++", 20)));
		assertTrue(frequentTerms.contains(new TermFrequencyDetail("Python", 20)));
		
	}
	
 	@Test
	public void shouldReturnTopNTermFrequenciesInDocument() throws IOException
	{
 		
 		
 		when(termFreqVector2.getTerms()).thenReturn(new String[] {"C++", "Python", "Java", "Scala"});
		when(termFreqVector2.getTermFrequencies()).thenReturn(new int[] {20, 15, 5, 10});
		
		Map<Integer, List<TermFrequencyDetail>> topNTermsMap = underTest.getTopNTermsInDocument(1, 3);
 		
 		assertTrue(topNTermsMap.size() > 0);
 		assertTrue(topNTermsMap.containsKey(20));
 		assertTrue(topNTermsMap.containsKey(15));
 		assertTrue(topNTermsMap.containsKey(10));
 		assertFalse(topNTermsMap.containsKey(5));
	}
}
