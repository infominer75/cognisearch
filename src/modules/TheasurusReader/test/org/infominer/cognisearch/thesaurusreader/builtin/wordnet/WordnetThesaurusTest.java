package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;

import net.didion.jwnl.dictionary.Dictionary;

/**
 * A base class for all wordnet theasurus tests.
 * This class will initialize the wordnet thesaurus in reality and allow tests for validating synonym finding etc
 * to be authored
 * NOTE: At  a later stage consider moving this test to a functional test because that is what it exactly is!!
 */
public class WordnetThesaurusTest
{
	
	protected WordnetThesaurus wordnet;
	
	private static final String PROPERTIES_FILE_LOCATION = "bin/config/ThesaurusReader.properties";
	
	protected void setUpWordnetDictionary() throws FileNotFoundException, IOException, ThesaurusInitializationException
	{
		
		FileInputStream propertiesFile = new FileInputStream(new File(PROPERTIES_FILE_LOCATION));
		Properties properties = new Properties();
		properties.load(propertiesFile);
		
		WordnetInitializer wordnetInitializer = new WordnetInitializer(properties);
		
		wordnetInitializer.initializeThesaurus();
		
		wordnet = wordnetInitializer.getWordnetDictionary();
	}

}
