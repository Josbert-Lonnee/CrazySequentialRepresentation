package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class PowerRepresentation extends AbstractBinaryRepresentation
{
	public PowerRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		super(configuration, leftRepresentation, rightRepresentation);
	}
	
	@Override
	protected RationalNumber evaluateInternal(RationalNumber left, RationalNumber right)
	{
		if (!left.isNaturalNumber() || !right.isNaturalNumber())
			return null;

		long base  = left .getNaturalNumber();
		long power = right.getNaturalNumber();
		
		if (base == 0)
			return new RationalNumber(0);
		
		if (power < 0 || base > this.configuration.getPowerMaxBase() || power > this.configuration.getPowerMaxPower())
			return null;
		
		long result = 1;
		for(; power > 0; --power)
			result *= base;

		return new RationalNumber(result);
	}
	
	@Override
	protected String getOperatorSymbol()
	{
		return "^";
	}
}
