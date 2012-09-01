package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerTarget;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.infominer.cognisearch.thesaurusreader.core.RelationDistanceComparator;
import org.infominer.cognisearch.thesaurusreader.core.RelationshipFinder;
import org.infominer.cognisearch.thesaurusreader.core.TermRelation;
import org.infominer.cognisearch.thesaurusreader.core.TermRelationCollection;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusOperationException;
import org.mockito.cglib.transform.impl.AddStaticInitTransformer;

import java.util.Map;

public class WordnetSynonymFinder implements RelationshipFinder 
{
	private final Dictionary wordnetDictionary;
	
	
	
	public WordnetSynonymFinder(Dictionary wordnetDictionary)
	{
		this.wordnetDictionary = wordnetDictionary;
		
	}
	
	
	
	public Set<String> getRelatedTermsForKeyword(PartOfSpeech partOfSpeech, String keyWord) throws ThesaurusOperationException
	{
		Set<String> synonymSet = new TreeSet<String>();
		TermRelationCollection relatedTerms = getRelatedTermsWithDistance(partOfSpeech, keyWord);
			
		for(SortedSet<TermRelation> relatedTermsSet : relatedTerms.values())
		{
			for(TermRelation termRelation : relatedTermsSet)
			{
				synonymSet.add(termRelation.getTargetTerm());
			}
		}
		
		
		return synonymSet;
	}

	public TermRelationCollection getRelatedTermsWithDistance(PartOfSpeech partOfSpeech, String keyWord) throws ThesaurusOperationException
	{
		TermRelationCollection relatedTerms = new TermRelationCollection();
		try
		{
			POS wordnetPOS = WordnetPOSConverter.getWordnetPOSMapping(partOfSpeech);
			IndexWord indexWord = wordnetDictionary.getIndexWord(wordnetPOS, keyWord);
			if( wordnetPOS == POS.ADJECTIVE)
			{
				getSynonymsForAdjective(indexWord, relatedTerms);
			}
			else
			{
				getSynonymsForNonAdjective(indexWord, relatedTerms);
			}
		}
		catch(JWNLException exception)
		{
			throw new ThesaurusOperationException(exception);
		}
		return relatedTerms;
	}

	public Integer getDistanceForRelatedTerm(PartOfSpeech partOfSpeech, String keyWord,String relatedTerm) throws ThesaurusOperationException
	{
		return 0;
		
		
	}
	
	//get the related terms for an adjective. 
	private void getSynonymsForAdjective(IndexWord indexWord,TermRelationCollection relatedTerms) throws JWNLException
	{
		
		Synset[]  relatedSynsets = indexWord.getSenses();
		
		for(Synset relatedSynset : relatedSynsets)
		{
			addTermsInSynset(indexWord, relatedSynset, relatedTerms,0);
			PointerTargetNodeList synonymNodes = PointerUtils.getInstance().getSynonyms(relatedSynset);
			
			Iterator nodeIterator = synonymNodes.iterator();
			
			while(nodeIterator.hasNext())
			{
				Synset nodeSynset = ((PointerTargetNode) nodeIterator.next()).getSynset();
				Word[] words = nodeSynset.getWords();
				
				for(Word word : words)
				{
					TermRelation termRelation = new TermRelation(indexWord.getLemma(), word.getLemma(), 0);
					relatedTerms.addTermRelation(termRelation);
				}
			}
		}
		
	}
	
	//get all related terms for a non-adjective
	private void getSynonymsForNonAdjective(IndexWord indexWord, TermRelationCollection relatedTerms) throws JWNLException
	{
		Synset[] relatedSynsets = indexWord.getSenses();
		
		for(Synset relatedSynset : relatedSynsets)
		{
			addTermsInSynset(indexWord, relatedSynset, relatedTerms, 0);
			PointerTargetNodeList directHypernyms = PointerUtils.getInstance().getDirectHypernyms(relatedSynset);
			
			Iterator hypernymIterator = directHypernyms.iterator();
			
			while(hypernymIterator.hasNext())
			{
				Synset hypernymSynset = ((PointerTargetNode)(hypernymIterator.next())).getSynset();
				addTermsInSynset(indexWord, hypernymSynset, relatedTerms, 1);
			}
		}
		
	}
	
	private void addTermsInSynset(IndexWord indexWord, Synset  synSet, TermRelationCollection relatedTerms, int depth)
	{
		Word[] words = synSet.getWords();
		
		for(Word word : words)
		{
			TermRelation termRelation = new TermRelation(indexWord.getLemma(), word.getLemma(), depth);
			
			relatedTerms.addTermRelation(termRelation);
		}
	}
	
	

}
