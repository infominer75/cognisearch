package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerUtils;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.data.list.PointerTargetNode;
import net.didion.jwnl.data.list.PointerTargetNodeList;
import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.infominer.cognisearch.thesaurusreader.core.RelationshipFinder;
import org.infominer.cognisearch.thesaurusreader.core.TermRelation;
import org.infominer.cognisearch.thesaurusreader.core.TermRelationCollection;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusOperationException;

import java.util.Map;

public class WordnetHypernymFinder implements RelationshipFinder 
{

	private final Dictionary wordnetDictionary;
	
	public WordnetHypernymFinder(Dictionary wordnetDictionary) 
	{
		this.wordnetDictionary = wordnetDictionary;
	}

	public Set<String> getRelatedTermsForKeyword(PartOfSpeech partOfSpeech,String keyWord) throws ThesaurusOperationException
	{
		SortedSet<String> hypernymTerms = new TreeSet<String>();
		TermRelationCollection relatedTerms = getRelatedTermsWithDistance(partOfSpeech, keyWord);
		
		for(SortedSet<TermRelation> relatedTermSet : relatedTerms.values())
		{
			for(TermRelation relatedTerm : relatedTermSet)
			{
				hypernymTerms.add(relatedTerm.getTargetTerm());
			}
		}
		
		return hypernymTerms;
		
	}

	public TermRelationCollection getRelatedTermsWithDistance(PartOfSpeech partOfSpeech, String keyWord) throws ThesaurusOperationException
	{
		TermRelationCollection relatedTerms = new TermRelationCollection();
		try
		{
			POS wordnetPOS = WordnetPOSConverter.getWordnetPOSMapping(partOfSpeech);
			IndexWord indexWord = wordnetDictionary.getIndexWord(wordnetPOS, keyWord);
			
			getHypernyms(indexWord, relatedTerms);
		}
		catch(JWNLException exception)
		{
			throw new ThesaurusOperationException(exception);
		}
		
		return relatedTerms;
	}

	public Integer getDistanceForRelatedTerm(PartOfSpeech partOfSpeech,String keyWord, String relatedTerm) throws ThesaurusOperationException
	{
		return 1;
	}
	
	private void getHypernyms(IndexWord indexWord, TermRelationCollection relatedTerms) throws JWNLException
	{
		Synset[] synSets = indexWord.getSenses();
		
		PointerUtils pointerUtils = PointerUtils.getInstance();
		for(Synset synSet : synSets)
		{
			PointerTargetNodeList hypernymNodeList = pointerUtils.getDirectHypernyms(synSet);
			
			Iterator iterator = hypernymNodeList.iterator();
			
			while(iterator.hasNext())
			{
				PointerTargetNode targetNode = (PointerTargetNode)(iterator.next());
				Synset hypernymSet = targetNode.getSynset();
				Word[] words = hypernymSet.getWords();
				
				for(int  i = 0; i < words.length; i++)
				{
					TermRelation termRelation = new TermRelation(indexWord.getLemma(), words[i].getLemma(), 1);
					relatedTerms.addTermRelation(termRelation);
				}
				
			}
		}
		
		
	}

}
