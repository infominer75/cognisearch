package org.infominer.cognisearch.search.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * A base class for all Lucene Index related tests.
 * The set up method will leverage the capability of Lucene to create
 * a light weight in memory index directory using RAMDirectory
 * @author infominer
 *
 */

public class LuceneTest 
{
	
	protected RAMDirectory indexDirectory;
	protected IndexWriterConfig indexWriterConfig;
	protected IndexWriter indexWriter;
	
	
	
	private boolean isInitialized = false;
	
	protected void buildIndexDirectory() throws Exception
	{
		indexDirectory = new RAMDirectory();
		indexWriterConfig = new IndexWriterConfig(Version.LUCENE_35,
				new StandardAnalyzer(Version.LUCENE_35));
		indexWriter = new IndexWriter(indexDirectory, indexWriterConfig);
		
	}
	
	
	protected void writeToIndex(List<IndexEntryDescriptor> indexContents) throws Exception
	{
		for(IndexEntryDescriptor descriptor : indexContents)
		{
			
			Field filePathField = new Field("path", descriptor.getFilePath(), Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS);
			Field fileContentField = new Field("content", descriptor.getFileContent(), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
			
			Document document = new Document();
			
			document.add(filePathField);
			document.add(fileContentField);
			indexWriter.addDocument(document);
		}
		indexWriter.close();
	}
	
	protected IndexSearcher getIndexSearcher() throws Exception
	{
		IndexReader indexReader = IndexReader.open(indexDirectory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		
		return indexSearcher;
		
	}

}
