package org.infominer.cognisearch.thesaurusreader;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
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

	private static final Map<String, String> providers = 
		new ConcurrentHashMap<String, String>(); 
	
	
	private static final String DEFAULT_THESAURUS_PROVIDER_NAME = "Wordnet";
	private static final String DEFAULT_THESAURUS_PROVIDER_CLASS_NAME = "org.infominer.cognisearch.thesaurusreader.providers.builtin.WordnetThesaurusProvider";
	private static final Handler logHandler = new ConsoleHandler();
	private static final Logger logger = Logger.getLogger(ThesaurusManager.class.getName());
	
	//register the Wordnet Thesaurus by default.
	static
	{
		logger.addHandler(logHandler);
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
		logger.info("Registering default thesaurus provider Wordnet");
		registerThesaurusProvider(DEFAULT_THESAURUS_PROVIDER_NAME, DEFAULT_THESAURUS_PROVIDER_CLASS_NAME);
	}
	
	/**
	 * Registers a {@link:ThesaurusProvider} identified by name.
	 * @param providerName The name of the {@link: ThesaurusProvider} to register
	 * @param fullyQualifiedClassName The fully qualified class name to be load
	 */
	
	public static void registerThesaurusProvider(String providerName, String fullyQualifiedClassName) throws IllegalArgumentException
	{
		if(providerName == null || fullyQualifiedClassName == null)
		{
			throw new IllegalArgumentException("Error registring the Thesaurus Provider." +
					"Provider Name d Fully Qualified class names cannot be null");
		}
		if(providerName.length() == 0 || fullyQualifiedClassName.length() == 0)
		{
			throw new IllegalArgumentException("Error registring the Thesaurus Provider." +
					"Provider Name and Full Qualified Name cannot be empty");
		}
		
		providers.put(providerName, fullyQualifiedClassName);
		
	}
	
	/**
	 * De-registers an instance of the {@link:ThesaurusProvider}
	 * @param providerName The name of the {@link:ThesaurusProvider} to de-register.
	 * Will throw a {@link: java.lang.UnsupportedOperationException} in case the name 
	 * of the default provider is specified.
	 * @throws UnsupportedOperationException
	 */
	
	public static void deregisterThesaurusProvider(String providerName) 
							throws UnsupportedOperationException,IllegalArgumentException
	{
		if(providerName == null)
		{
			throw new IllegalArgumentException("Error de-registring the Thesaurus Provider." +
					"Provider Name cannot be null");
		}
		if(providerName.length() == 0)
		{
			throw new IllegalArgumentException("Error de-registring the Thesaurus Provider." +
					"Provider Name cannot be empty");
		}
		
		if(providerName.equals(DEFAULT_THESAURUS_PROVIDER_NAME))
		{
			throw new UnsupportedOperationException("De-registring the Default Wordnet Thesaurus Provider is not supported");
		}
		
		providers.remove(DEFAULT_THESAURUS_PROVIDER_NAME);
	}
	
	/**
	 * Returns an instance of the built-in Wordnet thesaurus
	 * @param initializationProperties The properties needed to initialize the Thesaurus
	 * @return An instance of the {@link: WordnetThesaurus} service.
	 * @throws ThesaurusInitializationException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 */
	public static Thesaurus newThesaurus(Properties initializationProperties) throws IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, ThesaurusInitializationException
	{
		return newThesaurus(DEFAULT_THESAURUS_PROVIDER_NAME, initializationProperties);
	}
	
	/**
	 * Returns an instance of the thesaurus service identified by name.
	 * The name specified must match the name of a registered thesaurus provider.
	 * In case there is no registered thesaurus provider with the specified name, then
	 * an {@link:java.lang.IllegalArgumentException} is thrown.
	 * 
	 * @param name The name of the {@link:Thesaurus} service to instantiate
	 * @param initializationProperties The properties needed to initialize the Thesaurus
	 * @return An instance of the specified Thesaurus.
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ThesaurusInitializationException 
	 */
	public static  Thesaurus newThesaurus(String name, Properties initializationProperties) 
				throws IllegalArgumentException, ClassNotFoundException, 
				InstantiationException, IllegalAccessException, ThesaurusInitializationException
	{
		
		if(name == null)
		{
			throw new IllegalArgumentException("The Thesaurus Provider name cannot be null");
		}
		
		if(name.length() == 0)
		{
			throw new IllegalArgumentException("The Thesaurus Provider name cannot be empty.");
		}
		
		if(!providers.containsKey(name))
		{
			throw new IllegalArgumentException("The specified Thesaurus Provider does not exist " +
					"and hence cannot be instantiated");
		}
		
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		String fullyQualifiedProviderClassName = providers.get(DEFAULT_THESAURUS_PROVIDER_NAME);
		logger.info("Initializing Thesaurus provider :" + fullyQualifiedProviderClassName);
		Class providerClass= classLoader.loadClass(fullyQualifiedProviderClassName);
		ThesaurusProvider provider = (ThesaurusProvider)(providerClass.newInstance());
		
		Thesaurus thesaurus = (Thesaurus)(provider.newInstance(initializationProperties));
		
		return thesaurus;
		
	}
	
	
	
	
}
