package indexanalyzers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import dto.TermFrequencyDetail;

public class TermFrequencyExtractor 
{
	private final IndexReader indexReader;
	private final Map<String,Integer> termFrequencyMap;
	
	public TermFrequencyExtractor(String indexPath) throws IOException
	{
		Directory directory = FSDirectory.open(new File(indexPath));
		this.indexReader = IndexReader.open(directory);
		termFrequencyMap = new HashMap<String, Integer>();
	}
	
	//for test
	TermFrequencyExtractor(IndexReader indexReader)
	{
		this.indexReader = indexReader;
		termFrequencyMap = new HashMap<String, Integer>();
	}
	
	public TermDocs getTermFrequencyForTerm(String searchTerm) throws IOException
	{
		return indexReader.termDocs(new Term("content", searchTerm));
	}
	
	public TermDocs getAllTermFrequencies() throws IOException
	{
		return indexReader.termDocs();
	}
	
	public List<TermFreqVector> getAllTermFrequencyVectors() throws IOException
	{
		List<TermFreqVector> termFrequencyVectors = new ArrayList<TermFreqVector>();
		
		for(int  i = 0; i < indexReader.maxDoc(); i++)
		{
			termFrequencyVectors.add(indexReader.getTermFreqVector(i, "content"));
		}
		
		return termFrequencyVectors;
	}

	/**
	 * Obtains term details for all terms present within all the documents in the index
	 * @return List of {@link TermFrequencyDetail} objects
	 */
	public Set<TermFrequencyDetail> getAllTermFrequencyDetails() throws IOException
	{
		
		Set<TermFrequencyDetail> termDetails = new HashSet<TermFrequencyDetail>();
		
		
		for(int  i = 0; i < indexReader.maxDoc(); i++)
		{
			TermFreqVector termFrequencyVector = indexReader.getTermFreqVector(i, "content");
			
			String[] termsInDocument = termFrequencyVector.getTerms();
			int[] termFrequenciesInDocument = termFrequencyVector.getTermFrequencies();
			
			for(String term : termsInDocument)
			{
				int termIndex = termFrequencyVector.indexOf(term);
				int termFrequency = termFrequenciesInDocument[termIndex];
			
				if(termFrequencyMap.containsKey(term))
				{
					int newTermFrequency = termFrequencyMap.get(term) + termFrequency;
					termFrequencyMap.put(term, newTermFrequency);
				}
				else
				{
					termFrequencyMap.put(term,termFrequency);
				}
			}
			
			
		}
		
		for(Map.Entry<String, Integer> entry : termFrequencyMap.entrySet())
		{
			termDetails.add(new TermFrequencyDetail(entry.getKey(), entry.getValue()));
		}
		
		return termDetails;
	}
	
	/**
	 * Returns the term frequency detail for the term with the highest frequency within a given document
	 * @param docNo
	 * @return a list of  {@link TermFrequencyDetail} objects containing information about terms with highest frequencies.
	 * @throws IOException
	 */
	public List<TermFrequencyDetail> getDocumentTermWithHighestFrequency(int docNo) throws IOException
	{
		
		SortedMap<Integer, List<TermFrequencyDetail>> frequencyTermMapping = getFrequencyTermMapping(docNo);
		
		Integer highestFrequency = frequencyTermMapping.firstKey();
		return frequencyTermMapping.get(highestFrequency);
	}
	
	/**
	 * Returns the top N frequent terms within a particular document
	 * @param docNo The document number in the Lucene Index for which to obtain the terms
	 * @param n The number of top frequencies to get
	 * @return A frequency-term mapping with the frequency as the key and a list of terms of that frequency as values.
	 * @throws IOException
	 */
	
	public Map<Integer, List<TermFrequencyDetail>> getTopNTermsInDocument(int docNo, int n) throws IOException
	{
		
		SortedMap<Integer, List<TermFrequencyDetail>> frequencyTermMap = getFrequencyTermMapping(docNo);
		SortedMap<Integer,List<TermFrequencyDetail>> topNTermFrequencies = 
				new TreeMap<Integer, List<TermFrequencyDetail>>(Collections.reverseOrder());
		
		Set<Map.Entry<Integer, List<TermFrequencyDetail>>> reverseSortedTermFrequency = frequencyTermMap.entrySet();
		
		
		Iterator<Map.Entry<Integer,List<TermFrequencyDetail>>> setIterator = reverseSortedTermFrequency.iterator();
		
		for(int counter = 1; counter <= n; counter++)
		{
			Map.Entry<Integer, List<TermFrequencyDetail>> entry = setIterator.next();
			topNTermFrequencies.put(entry.getKey(), entry.getValue());
		}
		
		
		return topNTermFrequencies;
	}
	

	/**
	 * @param docNo
	 * @return
	 * @throws IOException
	 */
	private SortedMap<Integer, List<TermFrequencyDetail>> getFrequencyTermMapping(int docNo) throws IOException 
	{
		TermFreqVector termFrequencyVector = indexReader.getTermFreqVector(docNo, "content");
		
		SortedMap<Integer, List<TermFrequencyDetail>> frequencyTermMapping = 
				new TreeMap<Integer, List<TermFrequencyDetail>>(Collections.reverseOrder());
		
		String[] termsInDocument = termFrequencyVector.getTerms();
		int[]  termFrequencies = termFrequencyVector.getTermFrequencies();
		
		
		for(int i = 0; i < termsInDocument.length; i++)
		{
			TermFrequencyDetail termFrequencyDetail = new TermFrequencyDetail(termsInDocument[i], termFrequencies[i]);
			if(frequencyTermMapping.containsKey(termFrequencies[i]))
			{
				
				frequencyTermMapping.get(termFrequencies[i]).add(termFrequencyDetail);
			}
			else
			{
				List<TermFrequencyDetail> termFrequencyDetails = new ArrayList<TermFrequencyDetail>();
				termFrequencyDetails.add(termFrequencyDetail);
				frequencyTermMapping.put(termFrequencies[i], termFrequencyDetails);
			}
			
		}
		return frequencyTermMapping;
	}
	
	
	/**
	 * Closes the associated index reader so that
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		indexReader.close();
	}
}
