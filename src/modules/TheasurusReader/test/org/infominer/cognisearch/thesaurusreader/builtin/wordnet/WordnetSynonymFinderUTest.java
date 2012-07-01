package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.Set;

import net.didion.jwnl.dictionary.Dictionary;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;


public class WordnetSynonymFinderUTest 
{
	
	@Mock
	private Dictionary wordnetMock;
	
	private WordnetSynonymFinder underTest;
	
	
	@Before
	public void setUp()
	{
		
		MockitoAnnotations.initMocks(this);
		underTest = new WordnetSynonymFinder(wordnetMock);
	}

	@Test
	public void shouldFetchSynonymsForKeyword() throws Exception
	{
	
		Set<String> synoymWordset = underTest.getRelatedTermsForKeyword(PartOfSpeech.NOUN, "sleep");
		assertNotNull(synoymWordset);
		
	}
	
}
