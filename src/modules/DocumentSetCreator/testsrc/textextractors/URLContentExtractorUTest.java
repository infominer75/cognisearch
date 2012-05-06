package textextractors;


import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.MalformedURLException;

public class URLContentExtractorUTest 
{

	@Mock
	private HTMLTextExtractor textExtractorMock;
	
	private URLContentExtractor underTest;
	
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		underTest = new URLContentExtractor(textExtractorMock);
	}
	
	@Test
	public void shouldExtractTextContent()
	{
		underTest.extractText();
		verify(textExtractorMock).extractText();
	}
	
	@Test(expected = MalformedURLException.class)
	public void shouldErrorWhenInvalidURLSpecified() throws MalformedURLException, IOException
	{
		String malformedHttpURL = "http//www.google.com";
		
		underTest = new URLContentExtractor(malformedHttpURL);
	}

}
