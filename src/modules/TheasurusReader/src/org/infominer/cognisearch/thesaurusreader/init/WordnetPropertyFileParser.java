package org.infominer.cognisearch.thesaurusreader.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class WordnetPropertyFileParser
{
	
	private Document configDocument;
	private final InputStream fileStream;
	
	private static final String USER_HOME_DIR_ENV_PROPERTY = "user.home";
	private static final String OS_PATH_SEPARATOR = "file.separator";
	private static final String WORDNET_PROP_FILENAME = "wordnet.file.properties.xml";
	private static final String PROCESSED_WORDNET_PROP_FILENAME_FORMAT = "%s%s%s";
	
	public WordnetPropertyFileParser(String filePath) throws ParserConfigurationException, SAXException, IOException 
	{
		this(new FileInputStream(new File(filePath)));
		
	}
	
	WordnetPropertyFileParser(InputStream fileStream) throws ParserConfigurationException, SAXException, IOException
	{
		this.fileStream = fileStream;
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.configDocument = documentBuilder.parse(fileStream);
	}
	
	
	public void setWordnetDictionaryPath(String path) throws ThesaurusInitializationException
	{
		try
		{
			XPath xPath = XPathFactory.newInstance().newXPath();
			XPathExpression xPathExpression =  xPath.compile("//param[@name='dictionary_path']");
			
			Object result = xPathExpression.evaluate(getConfigDocument(), XPathConstants.NODESET);
			
			NodeList nodes = (NodeList)(result);
			
			for(int  i= 0; nodes !=null && i < nodes.getLength(); i++)
			{
				NamedNodeMap attributes = nodes.item(i).getAttributes();
				
				Node attribute = attributes.getNamedItem("name");
				if( attribute == null)
				{
					throw new ThesaurusInitializationException("Cannot find parameter 'name' in property file.");
				}
				attribute.setNodeValue(path);
			}
			
				
		}
		catch(XPathExpressionException ex)
		{
			throw new ThesaurusInitializationException(ex);
		}
		
		
	}
	
	public void save() throws ThesaurusInitializationException
	{
		
		try
		{
			String modifiedPropertiesFilePath = String.format(PROCESSED_WORDNET_PROP_FILENAME_FORMAT, System.getProperty(USER_HOME_DIR_ENV_PROPERTY),
					System.getProperty(OS_PATH_SEPARATOR), System.getProperty(WORDNET_PROP_FILENAME));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(modifiedPropertiesFilePath));
			Source input  = new DOMSource(getConfigDocument());
			transformer.transform(input, output);
		}
		catch(TransformerException tex)
		{
			throw new ThesaurusInitializationException(tex);
		}
		catch(TransformerFactoryConfigurationError err)
		{
			throw new ThesaurusInitializationException(err);
		}
	}
	
	//for testing purposes.Note however that this is NOT a good design practice. 
	//Certainly this class will need re-factoring. However I am going ahead for now with the current approach for two reasons
	//1. I need to get something working
	//2. I have satisfied the required testability criteria for this class.
	Document getConfigDocument()
	{
		return this.configDocument;
	}
}
