package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class SubtractRepresentation extends AbstractBinaryRepresentation
{
	public SubtractRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		super(configuration, leftRepresentation, rightRepresentation);
	}
	
	@Override
	protected RationalNumber evaluateInternal(RationalNumber left, RationalNumber right)
	{
		return left.add(right.negate());
	}
	
	@Override
	protected String getOperatorSymbol()
	{
		return "-";
	}
	
	@Override
	protected AbstractRepresentation simplifiedBinary()
	{
		// Rule A - -B => A + B :
		AbstractRepresentation simplifiedNegativeRight = this.rightRepresentation.getSimplifiedNegative();
		if (simplifiedNegativeRight != null)
			return new AddRepresentation(this.configuration, this.leftRepresentation, simplifiedNegativeRight);
		
		return super.simplifiedBinary();
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule -A - B => -( A + B ) :
		AbstractRepresentation simplifiedNegativeLeft = this.leftRepresentation.getSimplifiedNegative();
		if (simplifiedNegativeLeft != null)
			return new AddRepresentation(this.configuration, simplifiedNegativeLeft, this.rightRepresentation);
		
		return super.getSimplifiedNegative();
	}
	
	@Override
	protected boolean canOmitParentheses(AbstractRepresentation parent, boolean isTheRight)
	{
		// "(a-b)+c" => "a-b+c"
		if (parent instanceof AddRepresentation && !isTheRight)
			return true;
		
		// "(a-b)-c" => "a-b-c"
		if ((parent instanceof SubtractRepresentation) && !isTheRight)
			return true;
		
		return super.canOmitParentheses(parent, isTheRight);
	}
}
