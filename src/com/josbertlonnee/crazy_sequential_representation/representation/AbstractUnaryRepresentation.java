package com.josbertlonnee.crazy_sequential_representation.representation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsConfiguration;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public abstract class AbstractUnaryRepresentation extends AbstractRepresentation
{
	AbstractRepresentation innerRepresentation;

	public AbstractUnaryRepresentation(RepresentationsConfiguration configuration, AbstractRepresentation innerRepresentation)
	{
		super(configuration);
		
		this.innerRepresentation = innerRepresentation;
	}

	@Override
	protected RationalNumber evaluateInternal()
	{
		RationalNumber inner = this.innerRepresentation.evaluate();
		if (inner == null)
			return null;
		
		return evaluateInternal(inner);
	}
	
	protected abstract RationalNumber evaluateInternal(RationalNumber inner);
	
	protected String getOperatorPrefixSymbol()
	{
		return "";
	}
	
	protected String getOperatorPostfixSymbol()
	{
		return "";
	}
	
	@Override
	protected final AbstractRepresentation simplified()
	{
		AbstractRepresentation simplifiedInner = this.innerRepresentation.getSimplified();
		if (simplifiedInner != null)
			return createSameClassInstance(simplifiedInner);
		
		return simplifiedUnary();
	}

	private AbstractUnaryRepresentation createSameClassInstance(AbstractRepresentation innerRepresentation)
	{
		try {
			Constructor<? extends AbstractUnaryRepresentation> constructor = this.getClass().getConstructor(RepresentationsConfiguration.class, AbstractRepresentation.class);
			return constructor.newInstance(this.configuration, innerRepresentation);
		}
		catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected AbstractRepresentation simplifiedUnary()
	{
		return null;
	}
	
	@Override
	protected void appendTo(StringBuilder sb, AbstractRepresentation parent, boolean isTheRight)
	{
		boolean canOmitParentheses = (parent == null) || canOmitParentheses(parent, isTheRight);
		
		if (!canOmitParentheses)
			sb.append('(');
		
		sb.append(getOperatorPrefixSymbol());
		
		this.innerRepresentation.appendTo(sb, this, false);
		
		sb.append(getOperatorPostfixSymbol());
		
		if (!canOmitParentheses)
			sb.append(')');
	}
	
	protected boolean canOmitParentheses(AbstractRepresentation parent, boolean isTheRight)
	{
		if ((parent instanceof PowerRepresentation) && !isTheRight)
			return false;
		
		return true;
	}
}
