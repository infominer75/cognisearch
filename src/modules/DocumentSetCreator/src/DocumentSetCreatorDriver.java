
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import textextractors.URLContentExtractor;

public class DocumentSetCreatorDriver 
{

	private static final String  FILE_NAME_SUFFIX = "_extracted.txt";
	/**
	 * @param args Specifies the properties file to be picked up for running the 
	 * DocumentSetCreator.
	 */
	public static void main(String[] args) 
	{
		if(args.length == 0)
		{
			System.out.println("Usage: DocumentSetCreator <path_to_DocumentSetCreator.properties>");
			System.exit(-1);
		}
		
		Properties properties = new Properties();
		List<String> contentURLList = new ArrayList<String>();
		String contentURLs = "";
		String outputDirectory = "";
		try
		{
			properties.load(new FileInputStream(args[0]));
			
			contentURLs = properties.getProperty("CONTENT_URLS");
			outputDirectory = properties.getProperty("OUTPUT_DIR");
			
			System.out.println("Content url:" + contentURLs);
			System.out.println("Output directory:" + outputDirectory);
			
			contentURLList = Arrays.asList(contentURLs.split(";"));
			
		}
		catch(FileNotFoundException fex)
		{
			System.out.println("Could not load properties file:" + args[0]);
		}
		catch(IOException iex)
		{
			System.out.println("Unhandled error while reading from properties file:" + args[0]);
		}
		
		//spool the content to a file.
		try
		{
			for(String contentURL : contentURLList)
			{ 
				Date currentSystemDate = new Date(System.currentTimeMillis());
				String fileName = outputDirectory + createFileNameFromURL(contentURL) + FILE_NAME_SUFFIX; 
				URLContentExtractor contentExtractor = new URLContentExtractor(contentURL);
				BufferedWriter contentWriter = new BufferedWriter(new FileWriter(new File(fileName))); 
				contentWriter.write(contentExtractor.extractText());
				
			}
		}
		catch(IOException iex)
		{
			System.out.println("Unhandled error while writing contents to the disk");
		}
		
		
	}
	
	public static String createFileNameFromURL(String contentURL)
	{
		//TODO: Add advanced file name creation from URL based on metadata and other attributes.
		//Currently we do a simple task of taking the last portion of the URL and using that as the
		//relative file name.
		
		int indexOfSlash = contentURL.lastIndexOf("/");
		return contentURL.substring(indexOfSlash+1);
	}

}
