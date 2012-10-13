package org.infominer.cognisearch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader 
{

	private final InputStream propertiesFile;
	
	public PropertiesReader(String filePath) throws FileNotFoundException
	{
		this(new FileInputStream(new File(filePath)));
	}
	
	PropertiesReader(InputStream propertiesFile)
	{
		this.propertiesFile = propertiesFile;
	}
	
	public Properties load() throws IOException
	{
		Properties properties = new Properties();
		properties.load(propertiesFile);
		
		return properties;
	}
}
