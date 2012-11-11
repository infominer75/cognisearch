package indexers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import exceptions.IndexingException;

import utils.FileSystemWalker;

/**
 * 
 * 
 * Implements a text content indexer.This class is inherently thread safe.
 */

public class FileContentIndexer implements ContentIndexer 
{

	
	private final Integer indexDocumentThreshold;
	private final IndexingMode indexMode;
	private final List<Document> documentsToIndex;
	private final IndexWriter indexWriter;
	
	private final FileSystemWalker fileSystemWalker;
	
	private static final int INDEX_DOCUMENT_THRESHOLD_DEFAULT = 10;
	private static final int MAX_DEPTH_DEFAULT = 1;
	private static final IndexingMode INDEXING_MODE_DEFAULT = IndexingMode.CREATE_OR_APPEND;
	
	private static final Analyzer DEFAULT_ANALYZER;
	private static final IndexWriterConfig DEFAULT_INDEX_WRITER_CONFIG;
	
	static
	{
		DEFAULT_ANALYZER = new StandardAnalyzer(Version.LUCENE_35);
		DEFAULT_INDEX_WRITER_CONFIG = new IndexWriterConfig(Version.LUCENE_35,DEFAULT_ANALYZER);
		DEFAULT_INDEX_WRITER_CONFIG.setMaxBufferedDocs(INDEX_DOCUMENT_THRESHOLD_DEFAULT);
	}
	
	public FileContentIndexer(String contentRoot, String indexPath) throws IOException
	{
		this(contentRoot, indexPath, INDEX_DOCUMENT_THRESHOLD_DEFAULT, MAX_DEPTH_DEFAULT);
	}
	
	public FileContentIndexer(String contentRoot,
			String indexPath, Integer indexDocumentThreshold, Integer maxDepthToIndex) throws IOException
	{
		this(new FileSystemWalker(contentRoot,maxDepthToIndex),
				INDEX_DOCUMENT_THRESHOLD_DEFAULT, INDEXING_MODE_DEFAULT, indexPath);
	}
	
	public FileContentIndexer(FileSystemWalker fileSystemWalker, 
			Integer indexDocumentThreshold, IndexingMode indexMode,String indexPath) throws IOException
	{
		this(fileSystemWalker, indexDocumentThreshold,indexMode,new ArrayList<Document>(),
				new IndexWriter(FSDirectory.open(new File(indexPath)),DEFAULT_INDEX_WRITER_CONFIG));
	}
	
	FileContentIndexer(FileSystemWalker fileSystemWalker, Integer indexDocumentThreshold, IndexingMode indexMode,
			List<Document> documentsToIndex, 
			IndexWriter indexWriter) throws IOException
	{
		
		this.indexDocumentThreshold = indexDocumentThreshold;
		this.indexMode = indexMode;
		this.documentsToIndex = documentsToIndex;
		this.fileSystemWalker = fileSystemWalker;
		this.indexWriter = indexWriter;
	}
	
	@Override
	public void indexContent() throws IndexingException 
	{
		Iterator<File> fileIterator = fileSystemWalker.walk();
		
		
		
		while(fileIterator.hasNext())
		{
			Document document = new Document();
		
			File currentFile = fileIterator.next();
			indexFileAttribute(document, currentFile);
			indexFileContent(document, currentFile);
			writeIndex(document, currentFile);
			
		}
		
	}

	public void commitIndex() throws IOException
	{
		indexWriter.close();
	}
	private void indexFileAttribute(Document document, File currentFile)
	{
		
		//we make file name field as a searchable field. The contents of this field are not tokenized.
		//term frequency related information is also not stored in the field.
		Field fileNameField = new Field("path", currentFile.getPath(),Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		fileNameField.setIndexOptions(IndexOptions.DOCS_ONLY);
		document.add(fileNameField);
		
		NumericField createdDateField = new NumericField("dateCreated");
		createdDateField.setLongValue(currentFile.lastModified());
		
		document.add(createdDateField);
	
	}
	
	private void indexFileContent(Document document, File currentFile) throws IndexingException
	{
		try
		{
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(currentFile), "UTF-8");
		
			BufferedReader fileReader = new BufferedReader(inputReader);
			
			String line ="";
			StringBuilder fileContent = new StringBuilder(line);
			
			while((line = fileReader.readLine()) != null)
			{
				fileContent.append(line);
			}
		
			fileReader.close();
			//document.add(new Field("content", fileReader,Field.TermVector.YES));
			document.add(new Field("content",fileContent.toString(), Field.Store.YES,Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));
		}
		catch(FileNotFoundException fex)
		{
			throw new IndexingException(fex.getMessage());
		}
		catch(UnsupportedEncodingException uex)
		{
			throw new IndexingException(uex.getMessage());
		} 
		catch (IOException iex) 
		{
			// TODO Auto-generated catch block
			throw new IndexingException(iex.getMessage());
		}
	}
	
	private void writeIndex(Document document, File currentFile) throws IndexingException
	{
		try
		{
			if(this.indexWriter.getConfig().getOpenMode() == IndexingMode.CREATE_OR_APPEND.getLuceneIndexMode())
			{
				this.indexWriter.addDocument(document);
			}
			else
			{
				Term filePathTerm = new Term("path",currentFile.getPath());
				this.indexWriter.updateDocument(filePathTerm, document);
			}
		}
		catch(IOException iex)
		{
			throw new IndexingException(iex.getMessage());
		}
	}
}
