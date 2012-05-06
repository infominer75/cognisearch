/**
 * Copyright(C) 2012 The Info Miner.
 */
package textextractors;
import java.util.Iterator;
import java.util.logging.Logger;

import net.htmlparser.jericho.HTMLElements;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTagType;
import net.htmlparser.jericho.TextExtractor;

/**
 * Extracts plain text from an HTML document stream
 *
 */
public class HTMLTextExtractor implements ContentExtractor
{

	private final Source htmlSource;
	
	private static final Logger logger = Logger.getLogger(HTMLTextExtractor.class.getName());
	
	public HTMLTextExtractor(Source contentSource)
	{
		this.htmlSource = contentSource;
	}
	
	//for test
	HTMLTextExtractor(String htmlContent)
	{
		this.htmlSource =  new Source(htmlContent);
	}

	public String extractText() 
	{
		
		String plainText = htmlSource.getTextExtractor().setIncludeAttributes(true).toString();
		
		return plainText;
	}

}
