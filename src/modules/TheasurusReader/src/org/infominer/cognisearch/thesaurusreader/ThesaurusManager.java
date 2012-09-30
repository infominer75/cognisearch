package org.infominer.cognisearch.thesaurusreader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;
import org.infominer.cognisearch.thesaurusreader.providers.ThesaurusProvider;


/**
 * 
 * Provides facilities to register and de-register Thesaurii implementations.
 * Also exposes APIs that will instantiate a particular  Thesaurii implementation for clients
 * based on the name specified by the client.
 * By default the ThesaurusManager will instantiate a {@link:WordnetThesaurus} service.
 * 
 */
public class ThesaurusManager 
{

	private static final Map<String, ThesaurusProvider> providers = 
		new ConcurrentHashMap<String, ThesaurusProvider>(); 
	
	
	private static final String DEFAULT_THESAURUS_NAME = "Wordnet";
	
	//register the Wordnet Thesaurus by default.
	{
		registerThesaurusProvider();
	}
	
	
	
	private ThesaurusManager()
	{
		
	}
	
	/**
	 * Registers the default {@link:ThesaurusProvider}. The default thesaurus provider
	 * provides for an instance of  {@link:WordnetThesaurus} service
	 */
	public static void registerThesaurusProvider()
	{
		registerThesaurusProvider(DEFAULT_THESAURUS_NAME);
	}
	
	/**
	 * Registers a {@link:ThesaurusProvider} identified by name.
	 * @param providerName The name of the {@link: ThesaurusProvider} to register
	 */
	
	public static void registerThesaurusProvider(String providerName)
	{
		
	}
	
	/**
	 * De-registers an instance of the {@link:ThesaurusProvider}
	 * @param providerName The name of the {@link:ThesaurusProvider} to de-register.
	 * Will throw a {@link: java.lang.UnsupportedOperationException} in case the name 
	 * of the default provider is specified.
	 * @throws UnsupportedOperationException
	 */
	
	public static void deregisterThesaurusProvider(String providerName) throws UnsupportedOperationException
	{
		
	}
	
	/**
	 * Returns an instance of the built-in Wordnet thesaurus
	 * @return An instance of the {@link: WordnetThesaurus} service.
	 */
	public static Thesaurus newThesaurus()
	{
		return null;
	}
	
	/**
	 * Returns an instance of the thesaurus service identified by name.
	 * The name specified must match the name of a registered thesaurus provider.
	 * In case there is no registered thesaurus provider with the specified name, then
	 * an {@link:java.lang.IllegalArgumentException} is thrown.
	 * 
	 * @param name The name of the {@link:Thesaurus} service to instantiate
	 * @return An instance of the specified Thesaurus.
	 * @throws IllegalArgumentException
	 */
	public static  Thesaurus newThesaurus(String name) throws IllegalArgumentException
	{
		return null;
	}
	
}
