package com.josbertlonnee.crazy_sequential_representation;

import com.josbertlonnee.crazy_sequential_representation.util.AssertingObject;

public abstract class AbstractOutputUser extends AssertingObject
{
	protected Output output;
	
	protected AbstractOutputUser(Output output)
	{
		this.output = output;
	}
}
