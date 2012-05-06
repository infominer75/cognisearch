package exceptions;

public class IndexingException extends Exception 
{
	
	public IndexingException()
	{
		this("Error while indexing");
	}
	
	public IndexingException(String message)
	{
		super(message);
	}
	
	
}
