package com.josbertlonnee.crazy_sequential_representation;

import com.josbertlonnee.crazy_sequential_representation.util.AssertingObject;

public class Stream<T> extends AssertingObject
{
	private Object[] buffer;
	
	private int writeIndex = 0;
	private int readIndex = 0;
	
	private boolean endOfInput = false;
	
	public Stream(int maxBufferLength)
	{
		buffer = new Object[maxBufferLength];
	}
	
	public synchronized void write(T next)
	{
		ASSERT( !this.endOfInput );
		
		int nextWriteIndex;
		for(;;) {
			nextWriteIndex = getNextIndex(this.writeIndex);
			
			// Buffer full?
			if (nextWriteIndex == this.readIndex)
				waitInternal();
			else
				break;
		}
		
		buffer[this.writeIndex] = next;
		this.writeIndex = nextWriteIndex;
		notifyAll();
	}
	
	public synchronized void setEndOfInput()
	{
		ASSERT( !this.endOfInput );
		
		this.endOfInput = true;
		
		notifyAll();
	}
	
	public synchronized T read()
	{
		// Buffer empty? Or even end of input?
		while(this.readIndex == this.writeIndex) {
			if (this.endOfInput)
				return null;
			
			waitInternal();
		}
		
		@SuppressWarnings("unchecked")
		T next = (T)this.buffer[ this.readIndex ];
		this.readIndex = getNextIndex(this.readIndex);
		
		notifyAll();
		return next;
	}

	public int getBufferLength()
	{
		int bufferLength = this.writeIndex - this.readIndex;
		if (bufferLength < 0)
			bufferLength += buffer.length;
		
		return bufferLength;
	}
	
	private int getNextIndex(int index)
	{
		int nextIndex = index + 1;
		if (nextIndex >= this.buffer.length)
			return 0;
		
		return nextIndex;
	}
	
	private void waitInternal()
	{
		try {
			wait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString()
	{
		return "Stream[" + getBufferLength() + '/' + this.buffer.length + ']';
	}
}
