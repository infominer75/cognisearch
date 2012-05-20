package org.infominer.cognisearch.indexanalyzer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

/**
 * Describes the apache lucene index.
 * @author prahaladdeshpande
 *
 */
public class IndexDescriptor 
{
	public final int maxDocuments;
	
	private final IndexReader indexReader;
	
	public IndexDescriptor(String indexPath) throws CorruptIndexException, IOException
	{
		this(IndexReader.open(FSDirectory.open(new File(indexPath))));
		
	}

	IndexDescriptor(IndexReader indexReader) throws IOException 
	{
		this.indexReader = indexReader;
		
		this.maxDocuments = indexReader.maxDoc();
		indexReader.close();
	}

	public int getMaxDocuments() 
	{
		return this.maxDocuments;
	}
	

	
}
