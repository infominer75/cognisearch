package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.Properties;

import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetInitializer;
import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetPropertyFileParser;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;



public class WordnetInitializerUTest 
{
	
	@Mock
	private WordnetPropertyFileParser propertyFileParserMock;
	
	private WordnetInitializer underTest;
	
	@Before
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		underTest =  new WordnetInitializer(propertyFileParserMock);
	}
	
	@Test
	public void shouldSetWordnetInitializationParameters() throws Exception
	{
		Document mockDocument = mock(Document.class);
		when(propertyFileParserMock.getConfigDocument()).thenReturn(mockDocument);
		underTest.initializeThesaurus();
		verify(propertyFileParserMock).setWordnetDictionaryPath(anyString());
		verify(propertyFileParserMock).save();
	}

	@Test(expected=ThesaurusInitializationException.class)
	public void shouldThrowExceptionWhenInvalidPropertiesSpecified() throws Exception
	{
		Properties properties = new Properties();
		properties.put("testprop", "testarg1");
		underTest = new WordnetInitializer(properties);
	}
	
}
