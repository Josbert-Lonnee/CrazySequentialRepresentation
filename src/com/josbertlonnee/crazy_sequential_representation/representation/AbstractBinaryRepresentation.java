package com.josbertlonnee.crazy_sequential_representation.representation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public abstract class AbstractBinaryRepresentation extends AbstractRepresentation
{
	protected AbstractRepresentation leftRepresentation;
	protected AbstractRepresentation rightRepresentation;

	public AbstractBinaryRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		super(configuration);
		
		this.leftRepresentation  = leftRepresentation ;
		this.rightRepresentation = rightRepresentation;
	}

	@Override
	protected RationalNumber evaluateInternal()
	{
		RationalNumber left = this.leftRepresentation.evaluate();
		if (left == null)
			return null;
		
		RationalNumber right = this.rightRepresentation.evaluate();
		if (right == null)
			return null;
		
		return evaluateInternal(left, right);
	}
	
	protected abstract RationalNumber evaluateInternal(RationalNumber left, RationalNumber right);
	
	protected abstract String getOperatorSymbol();
	
	@Override
	protected final AbstractRepresentation simplified()
	{
		AbstractRepresentation simplifiedLeft = this.leftRepresentation.getSimplified();
		if (simplifiedLeft != null)
			return createSameClassInstance(simplifiedLeft, this.rightRepresentation);
		
		AbstractRepresentation simplifiedRight = this.rightRepresentation.getSimplified();
		if (simplifiedRight != null)
			return createSameClassInstance(this.leftRepresentation, simplifiedRight);
		
		return simplifiedBinary();
	}

	private AbstractBinaryRepresentation createSameClassInstance(AbstractRepresentation leftRepresentation, AbstractRepresentation rightRepresentation)
	{
		try {
			Constructor<? extends AbstractBinaryRepresentation> constructor = this.getClass().getConstructor(RepresentationsConfiguration.class, AbstractRepresentation.class, AbstractRepresentation.class);
			return constructor.newInstance(this.configuration, leftRepresentation, rightRepresentation);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected AbstractRepresentation simplifiedBinary()
	{
		return null;
	}
	
	@Override
	protected void appendTo(StringBuilder sb, AbstractRepresentation parent, boolean isTheRight)
	{
		boolean canOmitParentheses = (parent == null) || canOmitParentheses(parent, isTheRight);
		
		if (!canOmitParentheses)
			sb.append('(');
		
		this.leftRepresentation.appendTo(sb, this, false);
		
		sb.append(getOperatorSymbol());
		
		this.rightRepresentation.appendTo(sb, this, true);
		
		if (!canOmitParentheses)
			sb.append(')');
	}
	
	protected boolean canOmitParentheses(AbstractRepresentation parent, boolean isTheRight)
	{
		return false;
	}
}
