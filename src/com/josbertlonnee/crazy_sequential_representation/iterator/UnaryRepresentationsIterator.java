package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.*;
import com.josbertlonnee.crazy_sequential_representation.representation.*;

public class UnaryRepresentationsIterator extends AbstractOperatorRepresentationsIterator
{
	AbstractRepresentationsIterator innerIterator;
	
	private AbstractRepresentation innerRepresentation = null;
	
	public UnaryRepresentationsIterator(RepresentationsCache context, AbstractRepresentationsIterator innerIterator)
	{
		super(context);
		
		this.innerIterator = innerIterator;
		
		this.innerRepresentation = this.innerIterator.createNextRepresentation();
		ASSERT( this.innerRepresentation != null );
	}

	@Override
	protected AbstractRepresentation createNextOperatorRepresentation(int operatorIndex)
	{
		return this.context.createUnaryRepresentation(operatorIndex, this.innerRepresentation);
	}

	@Override
	protected boolean createNextPartRepresentation()
	{
		this.innerRepresentation = this.innerIterator.createNextRepresentation();
		return (this.innerRepresentation != null);
	}
	
	@Override
	public void reset()
	{
		this.innerIterator.reset();
		
		this.innerRepresentation = this.innerIterator.createNextRepresentation();
		ASSERT( this.innerRepresentation != null );
		
		super.internalReset();
	}

	@Override
	public void outputProgressIndication(Output output)
	{
		this.innerIterator.outputProgressIndication(output);
	}
}
