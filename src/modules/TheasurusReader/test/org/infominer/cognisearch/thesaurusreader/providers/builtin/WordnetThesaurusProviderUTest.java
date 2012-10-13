package org.infominer.cognisearch.thesaurusreader.providers.builtin;

import java.util.Properties;

import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetThesaurus;
import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;


public class WordnetThesaurusProviderUTest 
{

	private WordnetThesaurusProvider underTest;
	
	@Before
	public void setUp() throws Exception
	{
		underTest = new WordnetThesaurusProvider();
		
		
	}
	
	@Test(expected=ThesaurusInitializationException.class)
	public void shouldThrowExceptionIfDictionaryPathPropertyAbsent() throws Exception
	{
		Properties properties = new Properties();
		properties.setProperty("testProperty", "testValue");
		underTest.newInstance(properties);
		
	}
	
	@Test
	public void shouldProvideWordnetThesaurus() throws Exception
	{
		Properties properties = new Properties();
		properties.setProperty("DictionaryPath", "/Users/prahaladdeshpande/java_progs/Java Libs/Wordnet-3.0");
		Thesaurus thesaurus = underTest.newInstance(properties);
		
		assertTrue(thesaurus instanceof WordnetThesaurus);
	}
	
}
