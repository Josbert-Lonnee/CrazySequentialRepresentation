package com.josbertlonnee.crazy_sequential_representation;

import java.lang.reflect.*;
import java.util.*;

import com.josbertlonnee.crazy_sequential_representation.representation.*;

public abstract class RepresentationsConfiguration extends AbstractOutputUser
{
	static final int MAX_RESULT = 111112;
	
	protected int fromDigit =  1;
	protected int toDigit   =  2;
	
	protected boolean concatenation  = false;
	protected boolean negativeOnNode = false;
	
	protected List<Class<? extends AbstractUnaryRepresentation >> unaryClasses  = new ArrayList<Class<? extends AbstractUnaryRepresentation >>(10);
	protected List<Class<? extends AbstractBinaryRepresentation>> binaryClasses = new ArrayList<Class<? extends AbstractBinaryRepresentation>>(10);
	
	protected int powerMaxBase  = 0;
	protected int powerMaxPower = 0;
	protected int factorialMax  = 0;
	
	public RepresentationsConfiguration(Output output)
	{
		super(output);
		
		initializeConfiguration();
	}
	
	protected abstract void initializeConfiguration();

	public boolean getConcatenation()
	{
		return this.concatenation;
	}
	
	public final boolean getNegativeOnNode()
	{
		return this.negativeOnNode;
	}
	
	public AbstractUnaryRepresentation createUnaryRepresentation(int operatorIndex, AbstractRepresentation inner)
	{
		if (operatorIndex >= unaryClasses.size())
			return null;
		
		try {
			Class<? extends AbstractUnaryRepresentation> binaryClass = unaryClasses.get(operatorIndex);
			Constructor<? extends AbstractUnaryRepresentation> constructor = binaryClass.getConstructor(RepresentationsConfiguration.class, AbstractRepresentation.class);
			return constructor.newInstance(this, inner);
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public AbstractBinaryRepresentation createBinaryRepresentation(int operatorIndex, AbstractRepresentation left, AbstractRepresentation right)
	{
		if (operatorIndex >= binaryClasses.size())
			return null;
		
		try {
			Class<? extends AbstractBinaryRepresentation> binaryClass = binaryClasses.get(operatorIndex);
			Constructor<? extends AbstractBinaryRepresentation> constructor = binaryClass.getConstructor(RepresentationsConfiguration.class, AbstractRepresentation.class, AbstractRepresentation.class);
			return constructor.newInstance(this, left, right);
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public final int getPowerMaxBase()
	{
		return this.powerMaxBase;
	}

	public final int getPowerMaxPower()
	{
		return this.powerMaxPower;
	}

	public final int getFactorialMax()
	{
		return this.factorialMax;
	}
}
