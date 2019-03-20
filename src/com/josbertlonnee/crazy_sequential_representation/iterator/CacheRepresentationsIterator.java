package com.josbertlonnee.crazy_sequential_representation.iterator;

import java.util.*;
import java.util.Map.Entry;

import com.josbertlonnee.crazy_sequential_representation.*;
import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class CacheRepresentationsIterator extends AbstractRepresentationsIterator
{
	private ResultToRepresentationsMap map;
	private Iterator<Entry<RationalNumber, AbstractRepresentation>> internalIterator;
	
	public CacheRepresentationsIterator(RepresentationsCache context, ResultToRepresentationsMap map)
	{
		super(context);
		
		this.map = map;
		
		reset();
	}

	@Override
	public AbstractRepresentation createNextRepresentation()
	{
		if (this.internalIterator.hasNext()) {
			Entry<RationalNumber, AbstractRepresentation> entry = this.internalIterator.next();
			// TODO: Maybe use entry.getKey()?
			return entry.getValue();
		}
		
		return null;
	}

	@Override
	public void reset()
	{
		this.internalIterator = this.map.entrySet().iterator();
	}
}
