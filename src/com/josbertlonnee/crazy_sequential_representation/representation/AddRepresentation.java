package com.josbertlonnee.crazy_sequential_representation.representation;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public class AddRepresentation extends AbstractBinaryRepresentation
{
	public AddRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		super(configuration, leftRepresentation, rightRepresentation);
	}
	
	@Override
	protected RationalNumber evaluateInternal(RationalNumber left, RationalNumber right)
	{
		return left.add(right);
	}
	
	@Override
	protected String getOperatorSymbol()
	{
		return "+";
	}
	
	@Override
	protected AbstractRepresentation simplifiedBinary()
	{
		// Rule A + -B => A - B :
		AbstractRepresentation simplifiedNegativeRight = this.rightRepresentation.getSimplifiedNegative();
		if (simplifiedNegativeRight != null)
			return new SubtractRepresentation(this.configuration, this.leftRepresentation, simplifiedNegativeRight);
		
		return super.simplifiedBinary();
	}
	
	@Override
	AbstractRepresentation getSimplifiedNegative()
	{
		// Rule -A + B => -( A - B ) :
		AbstractRepresentation simplifiedNegativeLeft = this.leftRepresentation.getSimplifiedNegative();
		if (simplifiedNegativeLeft != null)
			return new SubtractRepresentation(this.configuration, simplifiedNegativeLeft, this.rightRepresentation);
		
		return super.getSimplifiedNegative();
	}
	
	@Override
	protected boolean canOmitParentheses(AbstractRepresentation parent, boolean isTheRight)
	{
		// "(a+b)+c" OR "a+(b+c)" => "a+b+c"
		if (parent instanceof AddRepresentation)
			return true;
		
		// "(a+b)-c" => "a+b-c"
		if ((parent instanceof SubtractRepresentation) && !isTheRight)
			return true;
		
		return super.canOmitParentheses(parent, isTheRight);
	}
}
