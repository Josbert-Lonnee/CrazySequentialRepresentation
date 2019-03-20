package com.josbertlonnee.crazy_sequential_representation;

import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;

public class Representations
{
	public static void main(String[] args)
	{
		Output output = new Output(System.err, System.out);
		
		//AbstractRepresentationsFinder rf = new RepresentationsFinder18_Subt();
		//printSingleFinderResult(output, rf);
		
		//new Representations().testThreadsFull();
		
		compareSuccessors(output, new RepresentationsFinder18(output), new RepresentationsFinder18_Div(output), new RepresentationsFinder18_Subt(output), new RepresentationsFinder18_Subt_Div(output));
	}
	
	@SuppressWarnings("unused")
	private void testThreadsFull()
	{
		ThreadsList<EvaluateThread> l = new ThreadsList<>(4, EvaluateThread.class, this, Representations.class);
		l.startAll();
		l.joinAll();
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
				"".length();
			}
		}
	}

	@SuppressWarnings("unused")
	private static void printSingleFinderResult(Output output, AbstractRepresentationsFinder rf)
	{
		for(int i=0; i<RepresentationsConfiguration.MAX_RESULT; ++i)
			output.outputResultFor(i, rf.result[i], null);
		
		output.append("First 100 not-found results: ");
		int toDo = 100;
		for(int i=0; i<RepresentationsConfiguration.MAX_RESULT && toDo>0; ++i) {
			if (rf.result[i] == null) {
				output.append(";");
				output.append(i);
				--toDo;
			}
		}
		
		output.appendAndNewline(".");
	}
	
	@SuppressWarnings("unused")
	private static void compareSuccessors(Output output, AbstractRepresentationsFinder... rf)
	{
		output.appendNewSectionHeader("Results");
		output.setOutputIsData(true);
		
		int[] foundIn = new int[rf.length];
		int totalFound = 0;
		
		for(int i=0; i<RepresentationsConfiguration.MAX_RESULT; ++i) {
			int j=0;
			for(; j<rf.length; ++j) {
				AbstractRepresentation representation = rf[j].result[i];
				if (representation != null) {
					++foundIn[j];
					++totalFound;
					
					String note = null;
					if (j > 0)
						note = "Found with " + rf[j].getClass().getSimpleName();
					
					output.outputResultFor(i, representation, note);
					break;
				}
			}
			
			if (j >= rf.length)
				output.outputResultFor(i, null, null);
		}
		
		output.setOutputIsData(false);
		output.appendNewSectionHeader("Summary");
		
		int totalFoundIn = 0;
		for(int i=0; i<foundIn.length; ++i) {
			totalFoundIn += foundIn[i];
			output.appendAndNewline("Still missing " + (RepresentationsConfiguration.MAX_RESULT - totalFoundIn) + " (of " + RepresentationsConfiguration.MAX_RESULT + ") after " + rf[i].getClass().getSimpleName() + ".");
		}
		
		output.appendAndNewline("Found in total " + totalFound + " out of " + RepresentationsConfiguration.MAX_RESULT + ".");
	}
}
