package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class MultiplyRepresentation extends AbstractBinaryRepresentation
{
	public MultiplyRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		super(configuration, leftRepresentation, rightRepresentation);
	}
	
	@Override
	protected RationalNumber evaluateInternal(RationalNumber left, RationalNumber right)
	{
		return left.multiply(right);
	}
	
	@Override
	protected String getOperatorSymbol()
	{
		return "*";
	}
	
	@Override
	protected AbstractRepresentation simplifiedBinary()
	{
		// Rule -A * -B => A * B :
		AbstractRepresentation simplifiedLeftNegative  = this.leftRepresentation .getSimplifiedNegative();
		AbstractRepresentation simplifiedRightNegative = this.rightRepresentation.getSimplifiedNegative();
		if (simplifiedLeftNegative != null && simplifiedRightNegative != null)
			return new MultiplyRepresentation(this.configuration, simplifiedLeftNegative, simplifiedRightNegative);
		
		return super.simplifiedBinary();
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule -A * B => -( A * B ) :
		AbstractRepresentation simplifiedLeftNegative = this.leftRepresentation.getSimplifiedNegative();
		if (simplifiedLeftNegative != null)
			return new MultiplyRepresentation(this.configuration, simplifiedLeftNegative, this.rightRepresentation);
		
		// Rule A * -B => -( A * B ) :
		AbstractRepresentation simplifiedRightNegative = this.rightRepresentation.getSimplifiedNegative();
		if (simplifiedRightNegative != null)
			return new MultiplyRepresentation(this.configuration, this.leftRepresentation, simplifiedRightNegative);
		
		return super.getSimplifiedNegative();
	}
	
	@Override
	protected boolean canOmitParentheses(AbstractRepresentation parent, boolean isTheRight)
	{
		// "(a*b)*c" OR "a*(b*c)" => "a*b*c"
		if (parent instanceof MultiplyRepresentation)
			return true;
		
		// "(a*b)/c" => "a*b/c"
		if (parent instanceof DivideRepresentation && !isTheRight)
			return true;
		
		return super.canOmitParentheses(parent, isTheRight);
	}
}
