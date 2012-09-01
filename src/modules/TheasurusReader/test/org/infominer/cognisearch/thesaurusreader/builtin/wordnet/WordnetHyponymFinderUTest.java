package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusInitializationException;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;


public class WordnetHyponymFinderUTest extends WordnetThesaurusTest
{
	private WordnetHyponymFinder underTest;
	
	
	@Before
	public void setUp() throws Exception
	{
		super.setUpWordnetDictionary();
		underTest = super.wordnet.getHyponymFinder();
	}
	
	@Test
	public void shouldFetchHyponyms() throws Exception
	{
		Set<String> carHyponyms = underTest.getRelatedTermsForKeyword(PartOfSpeech.NOUN, "car");
		assertNotNull(carHyponyms);
		assertFalse(carHyponyms.contains("car"));
		assertTrue(carHyponyms.contains("taxi"));
	}

}
