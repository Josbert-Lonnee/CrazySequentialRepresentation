package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsCache;
import com.josbertlonnee.crazy_sequential_representation.representation.*;

public class BinaryRepresentationsIterator extends AbstractOperatorRepresentationsIterator
{
	AbstractRepresentationsIterator leftIterator;
	AbstractRepresentationsIterator rightIterator;
	
	private AbstractRepresentation leftRepresentation  = null;
	private AbstractRepresentation rightRepresentation = null;
	
	public BinaryRepresentationsIterator(RepresentationsCache context, AbstractRepresentationsIterator leftIterator, AbstractRepresentationsIterator rightIterator)
	{
		super(context);
		
		this.leftIterator  = leftIterator;
		this.rightIterator = rightIterator;
		
		this.leftRepresentation  = this.leftIterator .createNextRepresentation(); 
		this.rightRepresentation = this.rightIterator.createNextRepresentation();
		
		ASSERT( this.leftRepresentation  != null );
		ASSERT( this.rightRepresentation != null );
	}

	@Override
	protected AbstractRepresentation createNextOperatorRepresentation(int operatorIndex)
	{
		return this.context.createBinaryRepresentation(operatorIndex, this.leftRepresentation, this.rightRepresentation);
	}

	@Override
	protected boolean createNextPartRepresentation()
	{
		this.rightRepresentation = this.rightIterator.createNextRepresentation();
		if (this.rightRepresentation != null)
			return true;
		
		this.leftRepresentation = this.leftIterator.createNextRepresentation();
		if (this.leftRepresentation == null)
			return false;
		
		this.rightIterator.reset();
		this.rightRepresentation = this.rightIterator.createNextRepresentation();
		ASSERT( this.rightRepresentation != null );
		return true;
	}
}
