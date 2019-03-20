package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class DirectRepresentation extends AbstractUnaryRepresentation
{
	public DirectRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation innerRepresentation)
	{
		super(configuration, innerRepresentation);
	}

	@Override
	protected RationalNumber evaluateInternal(RationalNumber inner)
	{
		return inner;
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule A => -( -A ) :
		AbstractRepresentation simplifiedPositiveInner = this.innerRepresentation.getSimplifiedNegative();
		if (simplifiedPositiveInner != null)
			return new DirectRepresentation(this.configuration, simplifiedPositiveInner);
		
		return super.getSimplifiedNegative();
	}
	
	@Override
	protected void appendTo(StringBuilder sb, AbstractRepresentation parent, boolean isTheRight)
	{
		// Skipping the this-instance:
		this.innerRepresentation.appendTo(sb, parent, isTheRight); // Nasty exception! Not this, but parent passed.
	}
}
