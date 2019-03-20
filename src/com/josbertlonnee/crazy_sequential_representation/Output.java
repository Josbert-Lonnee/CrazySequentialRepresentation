package com.josbertlonnee.crazy_sequential_representation;

import java.io.PrintStream;

import com.josbertlonnee.crazy_sequential_representation.representation.AbstractRepresentation;

public class Output
{
	private PrintStream progress;
	private PrintStream data;

	private PrintStream out;
	
	public Output(PrintStream progress, PrintStream data)
	{
		this.progress = progress;
		this.data     = data    ;
		
		setOutputIsData(false);
	}
	
	public void setOutputIsData(boolean isData)
	{
		if (isData)
			this.out = this.data;
		else
			this.out = this.progress;
	}
	
	public Output append(char c)
	{
		this.out.print(c);
		return this;
	}
	
	public Output append(int i)
	{
		this.out.print(i);
		return this;
	}
	
	public Output append(long l)
	{
		this.out.print(l);
		return this;
	}
	
	public Output append(String s)
	{
		this.out.print(s);
		return this;
	}
	
	public Output appendAndNewline(String s)
	{
		this.out.println(s);
		return this;
	}
	
	public Output newline()
	{
		this.out.println();
		return this;
	}
	
	public Output appendNewSectionHeader(String title)
	{
		newline();
		for(int i=0; i<8 + title.length(); ++i)
			append('*');
		newline();
		append("*** ");
		append(title);
		appendAndNewline(" ***");
		return this;
	}
	
	public void outputResultFor(int number, AbstractRepresentation representation, String note)
	{
		append(Integer.toString(number));
		append(" = ");
		if (representation == null) {
			append("still not available");
		}
		else {
			AbstractRepresentation simplifiedRepresentation = representation.getSimplified();
			if (simplifiedRepresentation != null)
				representation = simplifiedRepresentation;
			
			append(representation.toString());
		}
		
		if (note != null) {
			append(" (");
			append(note);
			append(')');
		}
		
		newline();
	}
}
