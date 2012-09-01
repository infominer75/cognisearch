package org.infominer.cognisearch.thesaurusreader.core;

import java.util.Comparator;

/**
 * A comparator that implements comparison based on the target related term.
 * <br/>
 * Collections using this comparator will have their contents in a sequence consistent with the <i>natural ordering</i> of the related target terms 
 * @author infominer
 *
 */
public class TargetTermComparator implements Comparator<TermRelation> 
{

	@Override
	public int compare(TermRelation arg0, TermRelation arg1) 
	{
		if(arg0.equals(arg1))
		{
			return 0;
		}
		
		return arg0.getTargetTerm().compareTo(arg1.getTargetTerm());
	
	}

	
}
