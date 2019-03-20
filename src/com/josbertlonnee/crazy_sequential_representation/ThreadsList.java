package com.josbertlonnee.crazy_sequential_representation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ThreadsList<T extends Thread>
{
	private Thread[] threads = null;

	public ThreadsList(int length, Class<T> threadClass, Object outer, Class<?> outerClass)
	{
		threads = new Thread[length];
		
		for(int i=0; i<this.threads.length; ++i)
			this.threads[i] = createNewThread(threadClass, outer, outerClass);
	}
	
	private Thread createNewThread(Class<T> threadClass, Object outer, Class<?> outerClass)
	{
		try {
			Constructor<T> constructor = threadClass.getConstructor(outerClass);
			return constructor.newInstance(outer);
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public void startAll()
	{
		for(int i=0; i<this.threads.length; ++i)
			this.threads[i].start();
	}

	public void joinAll()
	{
		for(int i=0; i<this.threads.length; ++i) {
			try {
				this.threads[i].join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
