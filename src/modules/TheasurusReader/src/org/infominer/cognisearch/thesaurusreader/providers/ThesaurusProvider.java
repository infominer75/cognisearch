package org.infominer.cognisearch.thesaurusreader.providers;

import org.infominer.cognisearch.thesaurusreader.core.Thesaurus;

/**
 * 
 * An interface for providing the Thesaurus service.
 *
 */
public interface ThesaurusProvider 
{

	Thesaurus newInstance();
}
