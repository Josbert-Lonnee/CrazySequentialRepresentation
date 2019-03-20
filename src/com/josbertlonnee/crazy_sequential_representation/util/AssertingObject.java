package com.josbertlonnee.crazy_sequential_representation.util;

public abstract class AssertingObject
{
	protected final static void ASSERT(boolean assertion)
	{
		if (!assertion)
			throw new RuntimeException();
	}
}
