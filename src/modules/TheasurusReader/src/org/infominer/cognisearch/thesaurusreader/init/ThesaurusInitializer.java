package org.infominer.cognisearch.thesaurusreader.init;

import java.util.Properties;

import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;

/**
 * Interface to be implemented by all thesaurus initializers.
 * Since there can be different types of thesaurii, there can be specific initializers per thesaurus to initialize the properties of the thesaurus.
 * This interface does not make an assumption about the manner in which thesaurus initialization properties are passed to the initializer implementations.
 * The underlying implementations are free to choose the mechanism for passing the initialization properties e.g. custom config files, property sets etc.
 * such as the locale and the dictionary data sets.
 * @author prahaladdeshpande
 *
 */
public interface ThesaurusInitializer 
{

	/**
	 * Initialize the thesaurus
	 * @throws ThesaurusInitializationException
	 */
	void initializeThesaurus() throws ThesaurusInitializationException;
}
