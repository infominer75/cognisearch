package dto;

public class TermFrequencyDetail implements Comparable<TermFrequencyDetail>
{

	private final String termValue;
	
	private final Integer termFrequency;
	
	public TermFrequencyDetail(String termValue, Integer termFrequency)
	{
		this.termValue = termValue;
		this.termFrequency = termFrequency;
	}

	public String getTermValue() 
	{
		return termValue;
	}

	public Integer getTermFrequency() 
	{
		
		return termFrequency;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!( o instanceof TermFrequencyDetail))
		{
			return false;
		}
		
		if( o == this)
		{
			return true;
		}
		
		TermFrequencyDetail otherObject = (TermFrequencyDetail)o;
		
		boolean isValueEqual = this.termValue.equals(otherObject.termValue);
		
		return isValueEqual;
	}
	
	@Override
	public int hashCode()
	{
		int hashCode = this.termValue.hashCode();
		return hashCode;
		
	}
	
	@Override
	public String toString()
	{
		return this.termValue + ":" + Integer.toString(this.termFrequency);
	}

	@Override
	public int compareTo(TermFrequencyDetail o)
	{
		
		if(this.termFrequency < o.termFrequency)
			return -1;
		
		
		if(this.termFrequency > o.termFrequency)
			return 1;
		
		return 0;
	}
	
	
	
	
}
