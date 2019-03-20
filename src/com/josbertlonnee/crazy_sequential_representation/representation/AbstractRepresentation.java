package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.*;

public abstract class AbstractRepresentation extends AssertingObject
{
	protected RepresentationsConfiguration configuration;
	
	private RationalNumber evaluation;
	private boolean evaluated = false;
	
	public AbstractRepresentation(RepresentationsConfiguration configuration)
	{
		this.configuration = configuration;
	}

	public final RationalNumber evaluate()
	{
		if (!evaluated) {
			this.evaluation = evaluateInternal();
			this.evaluated = true;
		}
		
		if (this.evaluation != null)
			return this.evaluation.clone();
		
		return null;
	}
	
	protected abstract RationalNumber evaluateInternal();
	
	public final AbstractRepresentation getSimplified()
	{
		AbstractRepresentation previousSimplified = simplified();
		if (previousSimplified == null)
			return null;
		
		for(;;) {
			AbstractRepresentation simplified = previousSimplified.simplified();
			if (simplified == null)
				return previousSimplified;
			
			previousSimplified = simplified;
		}
	}
	
	protected abstract AbstractRepresentation simplified();
	
	AbstractRepresentation getSimplifiedNegative()
	{
		return null;
	}
	
	public final String toString()
	{
		StringBuilder sb = new StringBuilder(50);
		
		appendTo(sb, null, false);
		
		return sb.toString();
	}
	
	protected abstract void appendTo(StringBuilder sb, AbstractRepresentation parent, boolean isTheRight);
}
