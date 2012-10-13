package org.infominer.cognisearch.indexanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import org.apache.lucene.document.Document;

/**
 * Searches terms within a Lucene index using the specified IndexSearcher.Instances of this class are thread-safe.
 * @author infominer
 *
 */

public class TermSearcher 
{
	private final IndexSearcher indexSearcher;
	
	
	public TermSearcher(String indexPath) throws CorruptIndexException, IOException
	{
		this(new IndexSearcher(IndexReader.open(FSDirectory.open(new File(indexPath)))));
	}
	
	public TermSearcher(IndexSearcher indexSearcher)
	{
		this.indexSearcher = indexSearcher;
		
	}
	
	/**
	 * Returns the Lucene ScoreDoc instances for a given term query.
	 * @param termQuery The query for the term.
	 * @param documentCollector An instance of a Collector object that will contain the results of the query
	 * @return A {@link: Set} of ScoreDoc instances that will satisfy the term query.
	 * @throws IOException
	 */
	public Set<ScoreDoc> search(Query termQuery, TopScoreDocCollector documentCollector) throws IOException
	{
		
		
		Set<ScoreDoc> documentSet =  new HashSet<ScoreDoc>();
		
		indexSearcher.search(termQuery, documentCollector);
		
		ScoreDoc[] documents =  documentCollector.topDocs().scoreDocs;
		
		for(ScoreDoc document : documents)
		{
			documentSet.add(document);
		}
		
		return documentSet;
	}
	
	/**
	 * Returns the Lucene documents instances for a given term query.
	 * @param termQuery The query for the term
	 * @param documentCollector An instance of the Collector object that will contain the results of the query
	 * @return A {@link:Set} of Document instances that will satisfy the term query
	 * @throws IOException
	 */
	public Set<Document> getDocumentsForTerm(Query termQuery, TopScoreDocCollector documentCollector) throws IOException
	{
		Set<ScoreDoc> scoredDocumentSet = search(termQuery, documentCollector);
		
		Set<Document> documents = new HashSet<Document>();
		
		for(ScoreDoc scoreDoc : scoredDocumentSet)
		{
			 Document document = indexSearcher.doc(scoreDoc.doc);
			 documents.add(document);
		}
		
		
		return documents;
	}
	
	/**
	 * Returns the IndexSearcher instance that is being used by this instance of TermSearcher.
	 * @return
	 */
	public IndexSearcher getIndexSearcher()
	{
		return this.indexSearcher;
	}

}
