package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.RepresentationsCache;
import com.josbertlonnee.crazy_sequential_representation.representation.*;

public class NodeRepresentationsIterator extends AbstractRangeRepresentationsIterator<SplitRepresentationsIterator>
{
	private boolean numberCreated = false;
	private boolean negativeNumberCreated = false;
	
	public NodeRepresentationsIterator(RepresentationsCache context, int fromDigit, int toDigit)
	{
		super(context, fromDigit, toDigit);
	}

	@Override
	public AbstractRepresentation createNextRepresentation()
	{
		if (this.context.getConcatenation() || Math.abs(this.fromDigit - this.toDigit) <= 1) {
			if (!this.numberCreated) {
				this.numberCreated = true;
				
				return new NumberRepresentation(this.context, this.fromDigit, this.toDigit, /*negative=*/false);
			}
			
			if (this.context.getNegativeOnNode() && !this.negativeNumberCreated) {
				this.negativeNumberCreated = true;
				
				return new NumberRepresentation(this.context, this.fromDigit, this.toDigit, /*negative=*/true);
			}
		}
		
		if (this.innerIterator == null) {
			// Nothing to split?
			if (Math.abs(this.fromDigit - this.toDigit) < 2)
				return null; // Ending the recursion of iterator creation.
			
			ASSERT( createAndSetInnerIterator() );
		}
		
		return this.innerIterator.createNextRepresentation();
	}

	@Override
	protected SplitRepresentationsIterator createInnerIterator()
	{
		return new SplitRepresentationsIterator(this.context, this.fromDigit, this.toDigit);
	}
	
	@Override
	public void reset()
	{
		this.numberCreated = false;
		
		super.internalReset();
	}
}
