package org.infominer.cognisearch.thesaurusreader.exceptions;

public class ThesaurusInitializationException extends Exception 
{
	
	public ThesaurusInitializationException()
	{
		super();
	}
	
	public ThesaurusInitializationException(String message)
	{
		super(message);
	}
	
	public ThesaurusInitializationException(Throwable toThrow)
	{
		super(toThrow);
	}
	
	public ThesaurusInitializationException(String message,Throwable toThrow)
	{
		super(message, toThrow);
	}

}
