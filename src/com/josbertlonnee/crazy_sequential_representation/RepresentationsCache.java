package com.josbertlonnee.crazy_sequential_representation;

import java.util.HashMap;

import com.josbertlonnee.crazy_sequential_representation.iterator.AbstractRepresentationsIterator;
import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

abstract class Representation3DMap extends RepresentationsConfiguration
{
	private HashMap<Integer, HashMap<Integer, ResultToRepresentationsMap>> map = new HashMap<Integer, HashMap<Integer, ResultToRepresentationsMap>>();
	
	public Representation3DMap(Output output)
	{
		super(output);
	}

	protected void setMap(int fromDigit, int toDigit, ResultToRepresentationsMap map)
	{
		HashMap<Integer, ResultToRepresentationsMap> map2 = this.map.get(fromDigit);
		if (map2 == null) {
			map2 = new HashMap<Integer, ResultToRepresentationsMap>();
			this.map.put(fromDigit, map2);
		}
		
		map2.put(toDigit, map);
	}
	
	public ResultToRepresentationsMap getCachedMap(int fromDigit, int toDigit)
	{
		HashMap<Integer, ResultToRepresentationsMap> map2 = this.map.get(fromDigit);
		if (map2 == null)
			return null;
		
		return map2.get(toDigit);
	}
	
	protected void clear()
	{
		map.clear();
	}
}

public abstract class RepresentationsCache extends Representation3DMap implements RepresentationReceiver
{
	private Stream<AbstractRepresentation> representationsStream;
	
	public RepresentationsCache(Output output)
	{
		super(output);
	}

	protected void fillCache(int maxLength)
	{
		output.appendAndNewline("Filling the representations cache up to length " + maxLength + "...");
		
		for(int length=2; length <= maxLength; ++length)
			fillForLength(length);
	}
	private void fillForLength(int length)
	{
		output.appendAndNewline("Filling for length " + length + "...");
		
		int fromDigit = this.fromDigit;
		
		for(;fromDigit + length <= this.toDigit; ++fromDigit)
			fillFor(fromDigit, fromDigit + length);
	}

	private void fillFor(int fromDigit, int toDigit)
	{
		this.representationsStream = new Stream<>(1000);
		
		ResultToRepresentationsMap map = new ResultToRepresentationsMap();
		EvaluateThread evaluateThread = new EvaluateThread(map);
		evaluateThread.start();
		
		AbstractRepresentationsIterator iterator = AbstractRepresentationsIterator.CreateCompleteIterator(this, fromDigit, toDigit);
		iterator.iterateForNaturalNumbers((representation) -> {
			this.representationsStream.write(representation);
			return true;
		});
		this.representationsStream.setEndOfInput();
		
		try {
			evaluateThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		setMap(fromDigit, toDigit, map);
		
		this.representationsStream = null;
	}
	
	@Override
	public boolean receiveRepresentation(AbstractRepresentation representation)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	class EvaluateThread extends Thread
	{
		private ResultToRepresentationsMap map;
		
		public EvaluateThread(ResultToRepresentationsMap map)
		{
			this.map = map;
		}
		
		@Override
		public void run()
		{
			for(;;) {
				AbstractRepresentation representation = RepresentationsCache.this.representationsStream.read();
				if (representation == null)
					return;
				
				RationalNumber result = representation.evaluate();
				if (result != null)
					this.map.put(result, representation);
			}
		}
	}
	
	protected void clearCache()
	{
		clear();
	}
}

