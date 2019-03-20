package com.josbertlonnee.crazy_sequential_representation;

import com.josbertlonnee.crazy_sequential_representation.iterator.AbstractRepresentationsIterator;
import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;
import com.josbertlonnee.crazy_sequential_representation.util.RationalNumber;

public abstract class AbstractRepresentationsFinder extends RepresentationsCache
{
	private static final int REPORT_PERIOD_SECONDS = 5;
	private static final int EVALUATE_THREADS = 2;
	
	private Stream<AbstractRepresentation> representationsStream = new Stream<>(5000);
	
	AbstractRepresentation[] result = new AbstractRepresentation[AbstractRepresentationsFinder.MAX_RESULT];
	long[] numberOfResults = new long[AbstractRepresentationsFinder.MAX_RESULT];
	
	private int totalResultsFound = 0;
	private int consecutiveResultsFound = 0;
	
	private long lastReportedMilis = 0;

	protected AbstractRepresentationsFinder(Output output)
	{
		super(output);
		
		output.appendNewSectionHeader("Running " + getClass().getSimpleName());
		
		fillCache(Math.min(7, this.toDigit - this.fromDigit - 1));
		
		ThreadsList<EvaluateThread> evaluateThreads = new ThreadsList<EvaluateThread>(EVALUATE_THREADS, EvaluateThread.class, this, AbstractRepresentationsFinder.class);
		evaluateThreads.startAll();
		
		AbstractRepresentationsIterator iterator = AbstractRepresentationsIterator.CreateCompleteIterator(this, this.fromDigit, this.toDigit);
		iterator.iterateForNaturalNumbers((representation) -> {
			this.representationsStream.write(representation);
			return true;
		});
		
		this.representationsStream.setEndOfInput();
		
		evaluateThreads.joinAll();
		
		reportResultsProgress(output);
		output.newline();
		
		clearCache();
		System.gc();
	}
	
	private void reportResultsProgress(Output output)
	{
		output.append("Found ");
		output.append(this.totalResultsFound);
		output.append(" unique results. Consecutive results till ");
		output.append(this.consecutiveResultsFound);
		output.append(".");
	}
	
	class EvaluateThread extends Thread
	{
		public EvaluateThread()
		{
		}
		
		@Override
		public void run()
		{
			for(;;) {
				AbstractRepresentation representation = AbstractRepresentationsFinder.this.representationsStream.read();
				if (representation == null)
					return;
				
				RationalNumber rationalNumber = representation.evaluate();
				if (rationalNumber == null)
					continue;
				
				if (!rationalNumber.isNaturalNumber())
					continue;
				
				long number = rationalNumber.getNaturalNumber();
				if (number < 0 || number >= MAX_RESULT)
					continue;
				
				if (!AbstractRepresentationsFinder.this.foundResult(number, representation))
					return;
			}
		}
	}
	
	private synchronized boolean foundResult(long number, AbstractRepresentation representation)
	{
		++this.numberOfResults[(int)number];
		if (this.result[(int)number] != null)
			return true; // Continue
		
		this.result[(int)number] = representation;
		++this.totalResultsFound;
		
		if (number == consecutiveResultsFound) {
			while(++consecutiveResultsFound < MAX_RESULT && this.result[consecutiveResultsFound] != null)
				;
			
			if (consecutiveResultsFound >= MAX_RESULT)
				return false; // Done!
		}
		
		// Time to report?
		if (System.currentTimeMillis() - this.lastReportedMilis >= REPORT_PERIOD_SECONDS * 1000) {
			reportResultsProgress(output);
			
			// TODO:
			// output.append(" Progress indication ");
			// iterator.outputProgressIndication(output);
			
			output.append(" Buffer length: ");
			output.append(this.representationsStream.getBufferLength());
			
			output.appendAndNewline("...");
			
			this.lastReportedMilis = System.currentTimeMillis();
		}
		
		return true; // Continue
	}
}
