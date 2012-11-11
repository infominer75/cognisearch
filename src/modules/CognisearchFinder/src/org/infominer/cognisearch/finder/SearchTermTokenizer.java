package org.infominer.cognisearch.finder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Tokenizes the search terms entered as arguments.This class is currently
 * exposes very elementary functionality related to search term tokenization
 * 
 * @author infominer
 *
 */

public class SearchTermTokenizer 
{

	
	
	public SearchTermTokenizer()
	{
		
	}
	
	
	public Set<String> tokenize(String searchString)
	{
		//very elementary tokenization currently supported.
		//multiple search terms are separated by comma(,) character
		//and phrases are enclosed within double quote characters.
		//Words separated by a space character are considered as belonging to
		// a single phrase
		
		String[] searchTokens = searchString.split(",");
		Set<String> tokens = new HashSet<String>();
		
		for(String searchToken : searchTokens)
		{
			tokens.add(decorateToken(searchToken.trim()));
		}
		
		
		return tokens;
	}
	
	private boolean isPhrase(String tokenString)
	{
		if(tokenString.startsWith("\"") && tokenString.endsWith("\""))
		{
			return true;
		}
		
		String[] tokenParts = tokenString.split(" ");
		
		return (tokenParts.length > 1);
	}
	
	private String decorateToken(String token)
	{
		if(isPhrase(token) && token.startsWith("\"") && token.endsWith("\""))
		{
			return token;
		}
		
		if(isPhrase(token))
		{
			StringBuilder wrappedToken = new StringBuilder("\"");
			wrappedToken.append(token);
			wrappedToken.append("\"");
		
			return wrappedToken.toString();
		}
		
		return token;
	}
	
}
