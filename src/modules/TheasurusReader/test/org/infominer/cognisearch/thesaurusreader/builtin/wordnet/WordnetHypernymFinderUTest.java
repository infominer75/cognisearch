package org.infominer.cognisearch.thesaurusreader.builtin.wordnet;

import java.util.Set;

import org.infominer.cognisearch.thesaurusreader.core.PartOfSpeech;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

public class WordnetHypernymFinderUTest extends WordnetThesaurusTest
{
	private WordnetHypernymFinder underTest;
	
	@Before
	public void setUp() throws Exception
	{
		super.setUpWordnetDictionary();
		underTest = super.wordnet.getHypernymFinder();
	}
	
	@Test
	public void shouldFetchHypernyms() throws Exception
	{
		Set<String> carHypernyms = underTest.getRelatedTermsForKeyword(PartOfSpeech.NOUN, "car");
		assertNotNull(carHypernyms);
		assertFalse(carHypernyms.contains("car"));
		assertTrue(carHypernyms.contains("automotive_vehicle"));
		
		
	}

}
