package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.*;
import com.josbertlonnee.crazy_sequential_representation.representation.*;

public class SplitRepresentationsIterator extends AbstractRangeRepresentationsIterator<BinaryRepresentationsIterator>
{
	private int split = 1;
	
	public SplitRepresentationsIterator(RepresentationsCache context, int fromDigit, int toDigit)
	{
		super(context, fromDigit, toDigit);
		
		ASSERT( createAndSetInnerIterator() );
	}

	@Override
	public AbstractRepresentation createNextRepresentation()
	{
		while(this.innerIterator != null) {
			AbstractRepresentation representation = this.innerIterator.createNextRepresentation();
		
			if (representation != null)
				return representation;
			
			++split;
			
			createAndSetInnerIterator();
		}
		
		return null;
	}
	
	@Override
	protected BinaryRepresentationsIterator createInnerIterator()
	{
		if (this.fromDigit + this.split >= this.toDigit)
			return null;
		
		AbstractRepresentationsIterator leftIterator  = createCompleteIterator(this.fromDigit, this.fromDigit + this.split);
		AbstractRepresentationsIterator rightIterator = createCompleteIterator(this.fromDigit + this.split  , this.toDigit);
		
		return new BinaryRepresentationsIterator(this.context, leftIterator, rightIterator);
	}
	
	@Override
	public void reset()
	{
		this.split = 0;
		
		super.internalReset();
	}
	
	@Override
	public void outputProgressIndication(Output output)
	{
		output.append(this.split);
		
		if (Math.abs(this.fromDigit + this.split + 1 - this.toDigit) < this.split)
			this.innerIterator.leftIterator.outputProgressIndication(output);
		else
			this.innerIterator.rightIterator.outputProgressIndication(output);
	}
}
