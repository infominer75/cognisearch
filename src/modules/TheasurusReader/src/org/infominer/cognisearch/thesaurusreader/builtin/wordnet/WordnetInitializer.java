/**
 * Initializes the Wordnet thesaurus.
 */
package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.infominer.cognisearch.thesaurusreader.init.ThesaurusInitializer;


/**
 * An implementation of the {@link ThesaurusInitializer} for the Wordnet Thesaurus.
 *
 */
public class WordnetInitializer implements ThesaurusInitializer 
{

	
	private static final String WORDNET_DICT_LOCATION = "DictionaryPath";
	private static final String WORDNET_DICT_LOCATION_UNSPECIFIED = "Wordnet dictionary path is not specified.";
	private static final String WORDNET_DICT_INVALID_LOCATION = "Invalid Wordnet dictionary path specifed.";
	
	private static final String USER_HOME_DIR_ENV_PROPERTY = "user.home";
	private static final String OS_PATH_SEPARATOR = "file.separator";
	private static final String WORDNET_PROP_FILENAME = "wordnet.file.properties.xml";
	private static final String PROCESSED_WORDNET_PROP_FILENAME_FORMAT = "%s%s%s";

	
	private final WordnetPropertyFileParser propertyFileParser;
	
	private  static Dictionary wordnet;
	
	
	public WordnetInitializer(Properties thesaurusProperties) throws ThesaurusInitializationException
	{
		
		if(!thesaurusProperties.containsKey(WORDNET_DICT_LOCATION))
		{
			throw new ThesaurusInitializationException(WORDNET_DICT_LOCATION_UNSPECIFIED);
		
		}

		String wordnetDictionaryLocation = thesaurusProperties.getProperty(WORDNET_DICT_LOCATION);
		
		try
		{
			String propertyFileURI = "file_properties.xml";
			this.propertyFileParser = new WordnetPropertyFileParser(this.getClass().getResourceAsStream(propertyFileURI));
			
		}
		catch(Exception ex)
		{
			throw new ThesaurusInitializationException(ex);
		}


	}
	
	public WordnetInitializer( WordnetPropertyFileParser propertyFileParser) throws ThesaurusInitializationException 
	{
		
			this.propertyFileParser = propertyFileParser;	
		
	}
	
	public void initializeThesaurus() throws  ThesaurusInitializationException 
	{
		propertyFileParser.setWordnetDictionaryPath(WORDNET_DICT_LOCATION);
		propertyFileParser.save();	
		
		//initialize the wordnet dictionary based on the configuration file
		String modifiedPropertiesFilePath = String.format(PROCESSED_WORDNET_PROP_FILENAME_FORMAT, System.getProperty(USER_HOME_DIR_ENV_PROPERTY),
				System.getProperty(OS_PATH_SEPARATOR), WORDNET_PROP_FILENAME);
		
		try
		{
			FileInputStream dictionaryFileStream = new FileInputStream(modifiedPropertiesFilePath);
			JWNL.initialize(dictionaryFileStream);
			
			if(wordnet == null)
			{
				wordnet = Dictionary.getInstance();
			}
		}
		catch(IOException iex)
		{
			throw new ThesaurusInitializationException(iex);
		} 
		catch (JWNLException jex) 
		{
			// TODO Auto-generated catch block
			throw new ThesaurusInitializationException(jex);
		}
		
	}
	
	public WordnetThesaurus getWordnetDictionary()
	{
		return new WordnetThesaurus(wordnet, 
				new WordnetSynonymFinder(wordnet), 
				new WordnetHypernymFinder(wordnet), 
				new WordnetHyponymFinder(wordnet));
	}
	
}
