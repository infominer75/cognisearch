package org.infominer.cognisearch.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * 
 * Implements a sectioned collection of the search engine properties.
 * This class wraps the original java.util.Properties object to provide
 * a "named collection" semantics.
 * <br/>
 * <p>Each entry within this collection is a key value pair where the key represents the
 * section name and the value is the {@link:java.util.Properties} object associated with this 
 * section.</p>
 * <p>Separating section values provides the advantage of categorizing properties based on usage.
 * </p>
 *  
 */
public class CognisearchProperties implements Map<String, Properties> 
{

	private final Map<String, Properties> cognisearchProperties;
	
	public CognisearchProperties() 
	{
		cognisearchProperties = new HashMap<String, Properties>();
	}
	
	public void clear() 
	{
		cognisearchProperties.clear();
		
	}

	public boolean containsKey(Object sectionName) 
	{
		return cognisearchProperties.containsKey(sectionName);
		
	}

	public boolean containsValue(Object arg0) 
	{
		return cognisearchProperties.containsValue(arg0);
	}

	public Set<java.util.Map.Entry<String, Properties>> entrySet() 
	{
		return cognisearchProperties.entrySet();
	}

	public Properties get(Object arg0) 
	{
		return cognisearchProperties.get(arg0);
	}

	public boolean isEmpty() 
	{
		return cognisearchProperties.isEmpty();
	}

	public Set<String> keySet() 
	{
		return cognisearchProperties.keySet();
	}

	public Properties put(String arg0, Properties arg1) 
	{
		return cognisearchProperties.put(arg0, arg1);
	}

	public void putAll(Map<? extends String, ? extends Properties> arg0) 
	{
		cognisearchProperties.putAll(arg0);
		
	}

	public Properties remove(Object arg0) 
	{
		return cognisearchProperties.remove(arg0);
	}

	public int size() 
	{
		return cognisearchProperties.size();
	}

	public Collection<Properties> values() 
	{
		return cognisearchProperties.values();
	}
	
	

}
