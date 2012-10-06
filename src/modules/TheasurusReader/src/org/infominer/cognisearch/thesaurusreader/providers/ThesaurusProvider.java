package org.infominer.cognisearch.thesaurusreader.providers;

import java.util.Properties;

import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;

/**
 * 
 * An interface for providing the Thesaurus service.
 *
 */
public interface ThesaurusProvider 
{

	/**
	 * Initialize a new 
	 * @param initializationProperties The properties needed to initialize the Thesaurus
	 * @return The Thesaurus instance
	 * @throws ThesaurusInitializationException 
	 */
	Thesaurus newInstance(Properties initializationProperties) throws ThesaurusInitializationException;
}
