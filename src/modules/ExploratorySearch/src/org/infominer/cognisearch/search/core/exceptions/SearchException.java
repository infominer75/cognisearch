package org.infominer.cognisearch.search.core.exceptions;

public class SearchException extends Exception 
{
	
	public SearchException()
	{
		super();
	}

	public SearchException(String message, Throwable throwable) 
	{
		super(message, throwable);
		
	}

	public SearchException(String message) 
	{
		super(message);
		
	}

	public SearchException(Throwable throwable) 
	{
		super(throwable);
		
	}
	
	

}
