package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class FactorialRepresentation extends AbstractUnaryRepresentation
{
	public FactorialRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation innerRepresentation)
	{
		super(configuration, innerRepresentation);
	}

	@Override
	protected RationalNumber evaluateInternal(RationalNumber inner)
	{
		if (!inner.isNaturalNumber())
			return null;
		
		long n = inner.getNaturalNumber();
		if (n < 0 || n > this.configuration.getFactorialMax())
			return null;
		
		long result = 1;
		for(; n>0; --n)
			result *= n;
		
		return new RationalNumber(result);
	}

	@Override
	protected String getOperatorPostfixSymbol()
	{
		return "!";
	}
}
