package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.io.File;

import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetPropertyFileParser;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;
import static org.junit.Assert.assertTrue;


public class WordnetPropertyFileParserUTest 
{

	
	private WordnetPropertyFileParser underTest;
	private static final String USER_HOME_DIR_ENV_PROPERTY = "user.home";
	private static final String OS_PATH_SEPARATOR = "file.separator";
	private static final String WORDNET_PROP_FILENAME = "wordnet.file.properties.xml";
	private static final String PROCESSED_WORDNET_PROP_FILENAME_FORMAT = "%s%s%s";
	
	

	@Before
	public void setUp() throws Exception
	{
		String propertyFileURI = "file_properties.xml";
		underTest = new WordnetPropertyFileParser(this.getClass().getResourceAsStream(propertyFileURI));
	}
	
	@Test
	public void shouldSetWordnetDictPath() throws Exception
	{
		underTest.setWordnetDictionaryPath("/home/dummy");
	}
	
	@Test
	public void shouldSaveTransformedDocument() throws Exception
	{
		underTest.save();
		
		String modifiedPropertyFileName = String.format(PROCESSED_WORDNET_PROP_FILENAME_FORMAT, System.getProperty(USER_HOME_DIR_ENV_PROPERTY),
				System.getProperty(OS_PATH_SEPARATOR), System.getProperty(WORDNET_PROP_FILENAME));
		
		File file = new File(modifiedPropertyFileName);
		assertTrue(file.exists());
	}
	
}
