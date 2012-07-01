package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.Set;

import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.infominer.cognisearch.thesaurusreader.core.RelationshipFinder;

import java.util.Map;

public class WordnetHypernymFinder implements RelationshipFinder 
{

	private final Dictionary wordnetDictionary;
	
	public WordnetHypernymFinder(Dictionary wordnetDictionary) 
	{
		this.wordnetDictionary = wordnetDictionary;
	}

	public Set<String> getRelatedTermsForKeyword(PartOfSpeech partOfSpeech,String keyWord) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Integer> getRelatedTermsWithDistance(PartOfSpeech partOfSpeech, String keyWord) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getDistanceForRelatedRelatedTerm(PartOfSpeech partOfSpeech,String keyWord, String relatedTerm) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
