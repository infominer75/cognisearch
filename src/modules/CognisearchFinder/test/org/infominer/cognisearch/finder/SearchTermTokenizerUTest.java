package org.infominer.cognisearch.finder;

import java.util.HashSet;
import java.util.Set;

import org.infominer.cognisearch.finder.SearchTermTokenizer;
import org.junit.Assert;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse; 

public class SearchTermTokenizerUTest 
{
	
	private SearchTermTokenizer underTest;
	
	
	@Before
	public void setUp()
	{
		underTest = new SearchTermTokenizer();
	}
	
	@Test
	public void shouldTokenizeSearchString()
	{
		Set<String> searchTokens = underTest.tokenize("hello,world,test,tokenization");
		
		assertTrue(searchTokens.size() == 4);
		
		
	}
	
	@Test
	public void shouldTokenizeSearchTermsInQuotesAsPhraseToken()
	{
		Set<String> searchTokens = underTest.tokenize("hello,world,\"phrase token\"");
		assertTrue(searchTokens.size() ==3);
		assertTrue(searchTokens.contains("\"phrase token\""));
		
	}
	
	@Test
	public void shouldTokenizeSearchTermsWithSpaceAsPhraseTokens()
	{
		Set<String> searchTokens = underTest.tokenize("hello,world,phrase token");
		assertTrue(searchTokens.size() ==3);
		assertTrue(searchTokens.contains("\"phrase token\""));
	}
	
	

}
