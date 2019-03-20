package com.josbertlonnee.crazy_sequential_representation.util;

public class RationalNumber extends AssertingObject implements Cloneable
{
	private long numerator;
	private long denominator = 1;
	
	public RationalNumber(long number)
	{
		this.numerator = number;
	}
	
	public RationalNumber(long numerator, long denominator)
	{
		this.numerator = numerator;
		this.denominator = denominator;
		
		normalize();
	}

	private RationalNumber normalize()
	{
		if (this.denominator < 0) {
			this.numerator   = -this.numerator  ;
			this.denominator = -this.denominator;
		}
		
		long gcd = FindGCD(this.numerator, this.denominator);
		if (gcd > 1) {
			this.numerator   /= gcd;
			this.denominator /= gcd;
		}
		
		return this;
	}
	
	private static long FindGCD(long number1, long number2)
	{
		// Base case:
		if (number2 == 0)
			return number1;
		
		return FindGCD(number2, number1 % number2);
	}

	public RationalNumber add(RationalNumber other)
	{
		this.numerator = (this.numerator * other.denominator) + (other.numerator * this.denominator);
		this.denominator *= other.denominator;
		
		return normalize();
	}

	public RationalNumber negate()
	{
		this.numerator = -this.numerator;
		
		return normalize();
	}

	public RationalNumber multiply(RationalNumber other)
	{
		this.numerator   *= other.numerator  ;
		this.denominator *= other.denominator;
		
		return normalize();
	}

	public RationalNumber reciprocal()
	{
		// Swap this.numerator and this.denominator; 
		long l = this.numerator;
		this.numerator = this.denominator;
		this.denominator = l;
		
		return normalize();
	}

	public boolean isZero()
	{
		return (this.numerator == 0);
	}

	public boolean isNaturalNumber()
	{
		return (this.denominator == 1);
	}

	public long getNaturalNumber()
	{
		ASSERT( isNaturalNumber() );
		
		return this.numerator;
	}
	
	@Override
	public int hashCode()
	{
		return (int)((2011 * this.denominator) + this.numerator);
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof RationalNumber))
			return false;
		
		RationalNumber other = (RationalNumber)object;
		
		return ((this.numerator==other.numerator) && (this.denominator==other.denominator));
	}
	
	@Override
	public RationalNumber clone()
	{
		return new RationalNumber(this.numerator, this.denominator);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.numerator);
		if (!isNaturalNumber()) {
			sb.append('/');
			sb.append(this.denominator);
		}
		return sb.toString();
	}
}
