package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class DivideRepresentation extends AbstractBinaryRepresentation
{
	public DivideRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		super(configuration, leftRepresentation, rightRepresentation);
	}
	
	@Override
	protected RationalNumber evaluateInternal(RationalNumber left, RationalNumber right)
	{
		if (right.isZero())
			return null;
		
		return left.multiply(right.reciprocal());
	}
	
	@Override
	protected String getOperatorSymbol()
	{
		return "/";
	}
	
	@Override
	protected AbstractRepresentation simplifiedBinary()
	{
		// Rule -A / -B => A / B :
		AbstractRepresentation simplifiedLeftNegative  = this.leftRepresentation .getSimplifiedNegative();
		AbstractRepresentation simplifiedRightNegative = this.rightRepresentation.getSimplifiedNegative();
		if (simplifiedLeftNegative != null && simplifiedRightNegative != null)
			return new DivideRepresentation(this.configuration, simplifiedLeftNegative, simplifiedRightNegative);
		
		return super.simplifiedBinary();
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule -A / B => -( A / B ) :
		AbstractRepresentation simplifiedLeftNegative = this.leftRepresentation.getSimplifiedNegative();
		if (simplifiedLeftNegative != null)
			return new DivideRepresentation(this.configuration, simplifiedLeftNegative, this.rightRepresentation);
		
		// Rule A / -B => -( A / B ) :
		AbstractRepresentation simplifiedRightNegative = this.rightRepresentation.getSimplifiedNegative();
		if (simplifiedRightNegative != null)
			return new DivideRepresentation(this.configuration, this.leftRepresentation, simplifiedRightNegative);
		
		return super.getSimplifiedNegative();
	}
	
	@Override
	protected boolean canOmitParentheses(AbstractRepresentation parent, boolean isTheRight)
	{
		// "a*(b/c)" => "a*b/c"
		if (parent instanceof MultiplyRepresentation && isTheRight)
			return true;
		
		return super.canOmitParentheses(parent, isTheRight);
	}
}
