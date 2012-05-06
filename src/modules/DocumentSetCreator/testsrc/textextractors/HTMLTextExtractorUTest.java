package textextractors;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;


public class HTMLTextExtractorUTest 
{

	private HTMLTextExtractor underTest;
	
	@Before
	public void setUp() throws Exception 
	{
		String htmlContent = "<html><head>Head content in test HTML</head><body>Body content in test HTML</body></html>";
		underTest= new HTMLTextExtractor(htmlContent);
	}

	@Test
	public void shouldConvertFromHtmlToPlainText()
	{
		String plainText = underTest.extractText();
		
		assertFalse(plainText.contains("<head>"));
	}
	
	@Test
	public void shouldConvertMalformedHTML()
	{
		String malformedHtmlContent = "<html><head>Some malformed content</head><body></html>";
		underTest = new HTMLTextExtractor(malformedHtmlContent);
		
		String malformedPlainText = underTest.extractText();
		
	}
}
