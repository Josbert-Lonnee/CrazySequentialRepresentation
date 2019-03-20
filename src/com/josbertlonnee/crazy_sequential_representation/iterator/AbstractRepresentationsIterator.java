package com.josbertlonnee.crazy_sequential_representation.iterator;

import com.josbertlonnee.crazy_sequential_representation.*;
import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;
import com.josbertlonnee.crazy_sequential_representation.util.*;

public abstract class AbstractRepresentationsIterator extends AssertingObject
{
	protected RepresentationsCache context;
	
	protected AbstractRepresentationsIterator(RepresentationsCache context)
	{
		this.context = context;
	}
	
	public abstract AbstractRepresentation createNextRepresentation();
	
	public void reset()
	{
		throw new RuntimeException("Not implemented.");
	}

	protected final AbstractRepresentationsIterator createCompleteIterator(int fromDigit, int toDigit)
	{
		return CreateCompleteIterator(this.context, fromDigit, toDigit);
	}
	
	public static AbstractRepresentationsIterator CreateCompleteIterator(RepresentationsCache context, int fromDigit, int toDigit)
	{
		if (context != null) {
			ResultToRepresentationsMap map = context.getCachedMap(fromDigit, toDigit);
			if (map != null)
				return new CacheRepresentationsIterator(context, map);
		}
		
		AbstractRepresentationsIterator innerIterator = new NodeRepresentationsIterator(context, fromDigit, toDigit);
		return new UnaryRepresentationsIterator(context, innerIterator);
	}

	public void outputProgressIndication(Output output)
	{
	}
	
	public final void iterateForNaturalNumbers(RepresentationReceiver receiver)
	{
		for(;;) {
			AbstractRepresentation representation = createNextRepresentation();
			if (representation == null)
				break;
			
			boolean cont = receiver.receiveRepresentation(representation);
			if (!cont)
				break;
		}
	}
}
