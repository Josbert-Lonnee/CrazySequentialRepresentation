package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.*;

public abstract class AbstractRangeRepresentationsIterator<ARI extends AbstractRepresentationsIterator> extends AbstractRepresentationsIterator
{
	protected int fromDigit;
	protected int toDigit;
	
	ARI innerIterator = null;
	
	public AbstractRangeRepresentationsIterator(RepresentationsCache context, int fromDigit, int toDigit)
	{
		super(context);
		
		this.fromDigit = fromDigit;
		this.toDigit   = toDigit  ;
	}
	
	protected final boolean createAndSetInnerIterator()
	{
		this.innerIterator = createInnerIterator();
		return (this.innerIterator != null);
	}
	
	protected abstract ARI createInnerIterator();
	
	protected void internalReset()
	{
		this.innerIterator = null;
	}

	@Override
	public void outputProgressIndication(Output output)
	{
		if (this.innerIterator != null)
			this.innerIterator.outputProgressIndication(output);
	}
}
