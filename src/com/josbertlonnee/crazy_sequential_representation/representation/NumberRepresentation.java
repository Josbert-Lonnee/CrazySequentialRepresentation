package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class NumberRepresentation extends AbstractRepresentation
{
	long number = 0;
	
	public NumberRepresentation(RepresentationsConfiguration configuration, long number)
	{
		super(configuration);
		
		this.number = number;
	}

	public NumberRepresentation(RepresentationsConfiguration configuration, long fromDigit, long toDigit, boolean negative)
	{
		super(configuration);
		
		while(fromDigit != toDigit) {
			this.number *= 10;
			this.number += fromDigit;
			fromDigit += ((fromDigit < toDigit) ? 1 : -1);
		}
		
		if (negative)
			this.number = -this.number;
	}

	@Override
	protected RationalNumber evaluateInternal()
	{
		return new RationalNumber(this.number);
	}

	@Override
	protected final AbstractRepresentation simplified()
	{
		return null;
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule -A => -( A ) :
		if (this.number < 0)
			return new NumberRepresentation(this.configuration, -this.number);
		
		return super.getSimplifiedNegative();
	}
	
	@Override
	protected void appendTo(StringBuilder sb, AbstractRepresentation parent, boolean isTheRight)
	{
		if (this.number < 0 && (parent instanceof PowerRepresentation) && !isTheRight) {
			sb.append( '(' );
			sb.append( Long.toString(this.number) );
			sb.append( ')' );
		}
		else {
			sb.append( Long.toString(this.number) );
		}
	}
}
