import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermFreqVector;

import com.sun.tools.corba.se.idl.InvalidArgument;

import java.util.Map;

import exceptions.IndexingException;

import indexanalyzers.TermFrequencyExtractor;
import indexers.FileContentIndexer;



public class DocumentIndexerDriver 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String contentRoot = args[0];
		String indexPath = args[1];
		
		Map<String,String> arguments = new HashMap<String,String>();
		
		try
		{
			parseCommandLineArguments(args, arguments);
		}
		catch(IllegalArgumentException iex)
		{
			
		}
		try
		{
			if(arguments.get("mode").equals("index"))
			{
				contentRoot = arguments.get("contentroot");
				indexPath = arguments.get("indexpath");
				FileContentIndexer indexer = new FileContentIndexer(contentRoot, indexPath);
				indexer.indexContent();
				indexer.commitIndex();
			}
			else
			{
				String indexDir = arguments.get("indexpath");
				TermFrequencyExtractor termFrequencyExtractor = new TermFrequencyExtractor(indexDir);
				List<TermFreqVector> termFrequencyVectors = termFrequencyExtractor.getAllTermFrequencyVectors();
				
				for(TermFreqVector termFreqVector : termFrequencyVectors)
				{
					System.out.println(termFreqVector);
				}
			}
		}
		catch(IOException iex)
		{
			System.out.println("IO Exception encountered");
		}
		catch(IndexingException inex)
		{
			System.out.println("IndexingException encountered");
			
		}
	}
	
	private static void parseCommandLineArguments(String[] args, Map<String,String> arguments) throws IllegalArgumentException
	{
		if(args.length == 1)
		{
			throw new IllegalArgumentException();
		}
		if(args[0].equals("-index"))
		{
			arguments.put("mode", "index");
			try
			{
				arguments.put("contentroot", args[1]);
				arguments.put("indexpath", args[2]);
			}
			catch(ArrayIndexOutOfBoundsException aex)
			{
				throw new IllegalArgumentException();
			}
		}
		else if(args[0].equals("-list"))
		{
			arguments.put("mode", "list");
			try
			{
				arguments.put("indexpath", args[1]);
			}
			catch(ArrayIndexOutOfBoundsException aex)
			{
				throw new IllegalArgumentException();
			}
		}
		else
		{
			throw new IllegalArgumentException();
			
			
		}
		
		
	}

}
