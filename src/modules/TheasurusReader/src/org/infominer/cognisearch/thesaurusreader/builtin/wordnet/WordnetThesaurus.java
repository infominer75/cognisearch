/**
 * 
 */
package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;


/**
 * @author prahaladdeshpande
 *
 */
public class WordnetThesaurus implements Thesaurus 
{

	private final Dictionary wordnetThesaurus;
	
	private final WordnetSynonymFinder wordnetSynonymFinder;
	
	private final WordnetHypernymFinder wordnetHypernymFinder;
	
	private final WordnetHyponymFinder wordnetHyponymFinder;
	
	
	
	public WordnetThesaurus(Dictionary wordnetThesaurus,
			WordnetSynonymFinder wordnetSynonymFinder,
			WordnetHypernymFinder wordnetHypernymFinder,
			WordnetHyponymFinder wordnetHyponymFinder) 
	{
		
		this.wordnetThesaurus = wordnetThesaurus;
		this.wordnetSynonymFinder = wordnetSynonymFinder;
		this.wordnetHypernymFinder = wordnetHypernymFinder;
		this.wordnetHyponymFinder = wordnetHyponymFinder;
	}

	public WordnetSynonymFinder getSynonymFinder() 
	{
		return wordnetSynonymFinder;
	}

	
	public WordnetHypernymFinder getHypernymFinder() 
	{
		return wordnetHypernymFinder;
	}

	
	public WordnetHyponymFinder getHyponymFinder() 
	{
		return wordnetHyponymFinder;
	}

}
