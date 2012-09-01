package org.infominer.cognisearch.thesaurusreader.core;



/**
 * Encapsulate a relation between two terms.Also holds a measure of the relationship distance.
 * The distance of a relationship is a measure of how closely the two terms are related. Greater the distance, the more dissimilar the terms
 * This object is immutable and cannot be modified once created
 */
public class TermRelation
{

	private final String sourceTerm;
	private final String targetTerm;
	private final Integer relationshipDistance;
	
	public TermRelation(String sourceTerm, String targetTerm, Integer relationshipDistance) 
	{
		this.sourceTerm = sourceTerm;
		this.targetTerm = targetTerm;
		this.relationshipDistance = relationshipDistance;
	}

	public String getSourceTerm() 
	{
		return sourceTerm;
	}

	public String getTargetTerm() 
	{
		return targetTerm;
	}

	public Integer getRelationshipDistance() 
	{
		return relationshipDistance;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((relationshipDistance == null) ? 0 : relationshipDistance
						.hashCode());
		result = prime * result
				+ ((sourceTerm == null) ? 0 : sourceTerm.hashCode());
		result = prime * result
				+ ((targetTerm == null) ? 0 : targetTerm.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermRelation other = (TermRelation) obj;
		if (relationshipDistance == null) 
		{
			if (other.relationshipDistance != null)
				return false;
		} 
		else if (!relationshipDistance.equals(other.relationshipDistance))
			return false;
		if (sourceTerm == null) 
		{
			if (other.sourceTerm != null)
				return false;
		} 
		else if (!sourceTerm.equals(other.sourceTerm))
			return false;
		if (targetTerm == null) 
		{
			if (other.targetTerm != null)
				return false;
		}
		else if (!targetTerm.equals(other.targetTerm))
			return false;
		return true;
	}
	
	
}
