package org.infominer.cognisearch.thesaurusreader.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * A mapping between the {@link TermRelation} and the corresponding distance in the concept hierarchy.
 * Each entry in this collection is a pair of <term_distance> - <set of term relation>. Hence for e.g entry 1-<TermRelation1,TermRelation2, TermRelation3>
 * represents the collection of all terms that are at a distance of 1 in the concept hierarchy.<br/>
 * The elements of the set at each position can be ordered based on the comparator passed in during class instantiation. If no comparator is supplied then the 
 * {@link:TargetTermComparator} is used for purposes of comparison.
 */
public class TermRelationCollection implements Map<Integer, SortedSet<TermRelation>> 
{

	private final Map<Integer,SortedSet<TermRelation>> termRelationMap;
	
	private final Comparator<TermRelation> termRelationComparator;
	
	public TermRelationCollection()
	{
		this(new TargetTermComparator());
	}
	
	public TermRelationCollection(Comparator<TermRelation> termRelationComparator)
	{
		this(new TreeMap<Integer, SortedSet<TermRelation>>(), termRelationComparator);
		
	}
	
	TermRelationCollection(Map<Integer, SortedSet<TermRelation>> termRelationMap, Comparator<TermRelation> termRelationComparator)
	{
		this.termRelationMap = termRelationMap;
		this.termRelationComparator = termRelationComparator;
	}
	
	@Override
	public void clear() 
	{
		termRelationMap.clear();
		
	}

	@Override
	public boolean containsKey(Object arg0) 
	{
		
		if(!(arg0 instanceof Integer))
		{
			return false;
		}
		
		return termRelationMap.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) 
	{
		if(!(arg0 instanceof TermRelation))
		{
			return false;
		}
		
		return termRelationMap.containsValue(arg0);
	}
	

	@Override
	public Set<Entry<Integer, SortedSet<TermRelation>>> entrySet() 
	{
		return termRelationMap.entrySet();
	}

	@Override
	public SortedSet<TermRelation> get(Object arg0) 
	{
		return termRelationMap.get(arg0);
		
	}

	@Override
	public boolean isEmpty() 
	{
		return termRelationMap.isEmpty();
	}

	@Override
	public Set<Integer> keySet() 
	{
		return termRelationMap.keySet();
	}

	
	
	@Override
	public SortedSet<TermRelation> put(Integer key, SortedSet<TermRelation> value) 
	{
		return termRelationMap.put(key, value);
			
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends SortedSet<TermRelation>> m) 
	{
		termRelationMap.putAll(m);
		
	}

	

	@Override
	public SortedSet<TermRelation> remove(Object key) 
	{
		return termRelationMap.remove(key);
	}

	@Override
	public int size() 
	{
		return termRelationMap.size();
	}

	@Override
	public Collection<SortedSet<TermRelation>> values() 
	{
		return termRelationMap.values();
	}
	
	public void addTermRelation(TermRelation termRelation)
	{
		if(!termRelationMap.containsKey(termRelation.getRelationshipDistance()))
		{
			SortedSet<TermRelation> termRelations = new TreeSet<TermRelation>(termRelationComparator);
			
			termRelationMap.put(termRelation.getRelationshipDistance(), termRelations);
		}
		else
		{
			termRelationMap.get(termRelation.getRelationshipDistance()).add(termRelation);
		}
	}
	
	public void removeTermRelation(TermRelation termRelation)
	{
		termRelationMap.get(termRelation.getRelationshipDistance()).remove(termRelation);
	}

}
