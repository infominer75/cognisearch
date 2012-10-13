package org.infominer.cognisearch.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PropertiesReaderUTest 
{
	
	private PropertiesReader underTest;
	
	@Before
	public void setUp() throws Exception
	{
		underTest = new PropertiesReader(storeProperties());
	}
	
	@Test
	public void shouldLoadPropertiesFromInputStream() throws Exception
	{
		Properties properties = underTest.load();
		assertTrue(properties.size() == 2);
		assertTrue(properties.containsKey("SampleProperty1"));
		assertTrue(properties.containsKey("SampleProperty2"));
	}

	private InputStream storeProperties() throws Exception
	{
		Properties properties = new Properties();
		properties.put("SampleProperty1", "Sample Value 1");
		properties.put("SampleProperty2", "Sample Value 2");
		
		PipedInputStream pin = new PipedInputStream();
		PipedOutputStream pout = new PipedOutputStream(pin);
		properties.store(pout, "");
		pout.flush();
		pout.close();
		return pin;
	}
}
