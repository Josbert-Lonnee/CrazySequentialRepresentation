package com.josbertlonnee.crazy_sequential_representation;

import com.josbertlonnee.crazy_sequential_representation.representation.*;

public class RepresentationsFinder18_Subt_Div extends AbstractRepresentationsFinder
{
	public RepresentationsFinder18_Subt_Div(Output output)
	{
		super(output);
	}

	@Override
	protected void initializeConfiguration()
	{
		this.fromDigit =  1;
		this.toDigit   =  9;
		
		this.concatenation  = false;
		this.negativeOnNode = true;
		
		// UNARY operators:
		unaryClasses.add(DirectRepresentation   .class);
		//unaryClasses.add(NegateRepresentation   .class);
		unaryClasses.add(FactorialRepresentation.class);
		
		// BINARY operators:
		binaryClasses.add(AddRepresentation     .class);
		binaryClasses.add(SubtractRepresentation.class);
		binaryClasses.add(MultiplyRepresentation.class);
		binaryClasses.add(DivideRepresentation  .class);
		//binaryClasses.add(PowerRepresentation   .class);
		
		this.powerMaxBase  =  7;
		this.powerMaxPower =  7;
		this.factorialMax  =  8;
	}
}
