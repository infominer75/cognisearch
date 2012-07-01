package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.HashSet;
import java.util.Set;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.infominer.cognisearch.thesaurusreader.core.RelationshipFinder;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusOperationException;

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
		Set<String> synonymSet = new HashSet<String>();
		try
		{
			IndexWord searchTerm = wordnetDictionary.getIndexWord(WordnetPOSConverter.getWordnetPOSMapping(partOfSpeech), keyWord);
			
			Synset[] relatedSenses = searchTerm.getSenses();
			
			for(Synset synSet : relatedSenses)
			{
				Word[] words = synSet.getWords();
				for(Word word :  words)
				{
					synonymSet.add(word.getLemma());
				}
			}
		}
		catch(JWNLException wordnetException)
		{
			throw new ThesaurusOperationException(wordnetException);
		}
		
		return synonymSet;
	}

	public Map<String, Integer> getRelatedTermsWithDistance(PartOfSpeech partOfSpeech, String keyWord) throws ThesaurusOperationException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getDistanceForRelatedRelatedTerm(PartOfSpeech partOfSpeech, String keyWord,String relatedTerm) throws ThesaurusOperationException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
