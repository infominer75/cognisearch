package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.Map;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;

import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.POSKey;

/**
 * A utility class that converts from the ThesurusReader PartOfSpeech to Wordnet POS
 * @author prahaladdeshpande
 *
 */
public final class WordnetPOSConverter 
{

	public static POS getWordnetPOSMapping(PartOfSpeech partOfSpeech)
	{
		
		switch(partOfSpeech)
		{
		case NOUN:
			return POS.NOUN;
			
		case ADJECTIVE:
			return POS.ADJECTIVE;
			
		case ADVERB:
			return POS.ADVERB;
			
		case VERB:
			return POS.VERB;
			
			default:
				throw new IllegalArgumentException("The part of speech is not supported by the Wordnet thesaurus.");
		}
		
	}
	
	
	public static PartOfSpeech getThesaurusReaderPOSMapping(POS wordnetPOS)
	{
		if(wordnetPOS.getKey().equalsIgnoreCase("NOUN_KEY"))
		{
			return PartOfSpeech.NOUN;
		}
		
		if(wordnetPOS.getKey().equalsIgnoreCase("VERB_KEY"))
		{
			return PartOfSpeech.VERB;
		}
		
		if(wordnetPOS.getKey().equalsIgnoreCase("ADJECTIVE_KEY"))
		{
			return PartOfSpeech.ADJECTIVE;
		}
		
		if(wordnetPOS.getKey().equalsIgnoreCase("ADVERB_KEY"))
		{
			return PartOfSpeech.ADVERB;
		}
		
		return PartOfSpeech.NOUN;
	}
	
	
}
