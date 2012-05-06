/**
 * Copyright(C) 2012 The Info Miner.
 */
package textextractors;

import java.io.IOException;
import java.net.*;

import net.htmlparser.jericho.Source;


/**
 * Decorator class to convert  a specified remote and file based URLs content 
 * into plain text strings.
 * Relies on the underlying {@link:HTMLTextExtractor} to convert HTML to 
 * plain text.<br>
 * Source URLs must follow the convention "<http|<file>://<site_name> for e.g.
 * http://www.google.com or file:///Users/johnsmith/documents/doc.html.
 * In case the specified URL is not of the correct format; this class throws a 
 * {@link:MalformedURLException} that needs to be handled by the clients.
 * In case the specified URL is a file resource which does not exist on the disk,
 * then an {@link:IOException} is thrown which needs to be handled by the clients.
 * Instances of this class are thread safe.
 */
public class URLContentExtractor implements ContentExtractor 
{

	
	private final HTMLTextExtractor textExtractor;
	
	public URLContentExtractor(String sourceLocation) throws MalformedURLException, IOException
	{
		this(new HTMLTextExtractor(new Source(new URL(sourceLocation))));
	}
	
	URLContentExtractor(HTMLTextExtractor textExtractor)
	{
		this.textExtractor = textExtractor;
	}
	
	@Override
	public String extractText() 
	{
		return textExtractor.extractText();
		
	}

}
