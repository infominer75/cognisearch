package org.infominer.cognisearch.thesaurusreader.providers.builtin;

import java.util.Properties;

import org.infominer.cognisearch.thesaurusreader.builtin.wordnet.WordnetInitializer;
import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.infominer.cognisearch.thesaurusreader.providers.ThesaurusProvider;

/**
 * 
 * Creates an instance of the  {@link:WordnetThesaurus}
 *
 */

public class WordnetThesaurusProvider implements ThesaurusProvider 
{
	
	private static final String WORDNET_DICTIONARY_PATH_PROPERTY = "DictionaryPath";
	
	public WordnetThesaurusProvider()
	{
		
	}

	@Override
	public Thesaurus newInstance(Properties initializationProperties) throws ThesaurusInitializationException 
	{
		if(!initializationProperties.containsKey(WORDNET_DICTIONARY_PATH_PROPERTY))
		{
			throw new IllegalArgumentException("Default Thesaurus initialization properties does not " +
					"contain entry for dictionary path. Add the  \"DictionaryPath\" property with the value pointing" +
					"to the correct dictionary path");
		}
		WordnetInitializer wordnetInitializer = new WordnetInitializer(initializationProperties);
		return wordnetInitializer.getWordnetDictionary();
	}

}
