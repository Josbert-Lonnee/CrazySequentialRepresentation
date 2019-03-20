package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class NegateRepresentation extends AbstractUnaryRepresentation
{
	public NegateRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation innerRepresentation)
	{
		super(configuration, innerRepresentation);
	}

	@Override
	protected RationalNumber evaluateInternal(RationalNumber inner)
	{
		return inner.negate();
	}

	@Override
	protected String getOperatorPrefixSymbol()
	{
		return "-";
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule -( A ) => -( A ) :
		return new DirectRepresentation(this.configuration, this.innerRepresentation);
	}
}
