package org.infominer.cognisearch.thesaurusreader.exceptions;

public class ThesaurusOperationException extends Exception 
{

	public ThesaurusOperationException()
	{
		super();
	}
	
	public ThesaurusOperationException(String message)
	{
		super(message);
	}
	
	public ThesaurusOperationException(Throwable toThrow)
	{
		super(toThrow);
	}
	
	public ThesaurusOperationException(String message,Throwable toThrow)
	{
		super(message, toThrow);
	}
}
