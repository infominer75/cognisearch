package org.infominer.cognisearch.thesaurusreader.core;

import java.util.Set;
import java.util.SortedSet;

import java.util.Map;

import org.infominer.cognisearch.thesaurusreader.exceptions.ThesaurusOperationException;


/**
 * Finds words based on a particular relationship to the base word.
 * The most common relations between two words are 
 * 	<br>
 * 		<li>
 * 			<ul>Synonymy - words with the same meaning as a given word</ul>
 * 			<ul>Hypernymy - words that indicate the concept of a given word. for e.g. 'Vehicle' is a hypernym of 'Car' </ul>
 * 			<ul>Hyponymy - words that are specializations of a given concept for eg.g 'Car' is a hyponym of 'Vehicle'
 * 		</li> 
 *		<br>
 * For a more complete set of relations <a href = "http://wordnet.princeton.edu/wordnet/man/wngloss.7WN.html">
 * The Wordnet Glossary of Terms</a> would serve a good reference.
 * An important aspect to be considered when finding word relationships is the relationship distance. A relationship
 * distance serves as a good indicator of how closely related the two words are.<br>
 * For e.g. for the given keyword <b>Car</b> the term <b>Hatchback</b> is more related than the term <b>Ship</b>
 * 
 * The <b>RelationshipFinder</b> interface is implemented by the built-in finders for the synonym, hypernym and holonym 
 * relations. The built-in relationship finders use <b>Wordnet</b> thesaurus for finding relations.
 * 
 */
public interface RelationshipFinder 
{
	/**
	 * Gets a set of terms that satisfy a particular relation for a given key word
	 * @param partOfSpeech The part of speech.
	 * @param keyWord The term for which the related terms need to be found
	 * @return Set of terms satisfying the relation with the keyword
	 */
	Set<String> getRelatedTermsForKeyword(PartOfSpeech partOfSpeech, String keyWord) throws ThesaurusOperationException;
	
	/**
	 * Gets a collection of terms satisfying a particular relation for a given keyword and
	 * the corresponding distance in the relationship
	 * @param partOfSpeech The part of speech
	 * @param keyWord The term for which the related terms need to be found
	 * @return A map containing the related terms and their corresponding distance from the keyword
	 */
	TermRelationCollection getRelatedTermsWithDistance(PartOfSpeech partOfSpeech, String keyWord) throws ThesaurusOperationException;
	
	/**
	 * Get the relationship distance between two terms
	 * @param partOfSpeech The part of speech 
	 * @param keyWord The keyword which a participant in the relation
	 * @param relatedTerm The term related to the keyword
	 * @return The distance between the keyword and the related term
	 */
	Integer getDistanceForRelatedTerm(PartOfSpeech partOfSpeech, String keyWord, String relatedTerm) throws ThesaurusOperationException;
	
}
