package org.infominer.cognisearch.thesaurusreader.core;

import java.util.Comparator;

/**
 * Compares two {@link TermRelation} instances based on their relationship distance.
 * 
 *
 */
public class RelationDistanceComparator implements Comparator<TermRelation> 
{

	@Override
	public int compare(TermRelation arg0, TermRelation arg1) 
	{
		if(arg0.getRelationshipDistance() == arg1.getRelationshipDistance())
		{
			return  0;
		}
		
		return(arg0.getRelationshipDistance() > arg1.getRelationshipDistance()) ? 1 : -1;
	}

}
