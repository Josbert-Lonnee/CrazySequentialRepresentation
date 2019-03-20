package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsCache;
import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;

public abstract class AbstractOperatorRepresentationsIterator extends AbstractRepresentationsIterator
{
	private int operatorIndex = 0;

	protected AbstractOperatorRepresentationsIterator(RepresentationsCache context)
	{
		super(context);
	}
	
	@Override
	public final AbstractRepresentation createNextRepresentation()
	{
		for(;;) {
			AbstractRepresentation representation = createNextOperatorRepresentation(this.operatorIndex);
			if (representation != null) {
				++this.operatorIndex;
				return representation;
			}
			
			if (!createNextPartRepresentation())
				return null;
			
			this.operatorIndex = 0;
		}
	}
	
	protected abstract AbstractRepresentation createNextOperatorRepresentation(int operatorIndex);
	protected abstract  boolean createNextPartRepresentation();
	
	protected void internalReset()
	{
		this.operatorIndex = 0;
	}
}
