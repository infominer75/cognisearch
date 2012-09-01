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


public class WordnetSynonymFinderUTest extends WordnetThesaurusTest
{
	
	
	private WordnetSynonymFinder underTest;
	
	
	@Before
	public void setUp() throws Exception
	{
		
		setUpWordnetDictionary();
		underTest = super.wordnet.getSynonymFinder();
	}

	@Test
	public void shouldFetchSynonymsForKeyword() throws Exception
	{
	
		Set<String> synonymWordset = underTest.getRelatedTermsForKeyword(PartOfSpeech.NOUN, "sleep");
		assertNotNull(synonymWordset);
		assertTrue(synonymWordset.contains("sleep"));
		assertTrue(synonymWordset.contains("slumber"));
	}
	
}
