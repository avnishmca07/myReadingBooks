package com.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecuterUncaughtExceptionHandler {
	public static void main(String[] args) {
	
		ExecutorService  threadpool = Executors.newFixedThreadPool(10, new MyThreadFactory(new MyExceptionhandler()));
		for(int i=0;i<10;i++)		
		threadpool.execute(new MyTask());
	}

}

class MyThreadFactory implements ThreadFactory{
	private static final ThreadFactory defaultFactory = Executors.defaultThreadFactory();
	private final Thread.UncaughtExceptionHandler handler;
	
	public MyThreadFactory(Thread.UncaughtExceptionHandler handler) {
		this.handler=handler;
	}
	

	@Override
	public Thread newThread(Runnable r) {
		
		Thread thread = defaultFactory.newThread(r);
		thread.setUncaughtExceptionHandler(handler);
		return thread;
	}
	
}
class MyExceptionhandler implements UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		 System.err.println("Uncaught exception is detected! " + t
	                + " st: " + Arrays.toString(t.getStackTrace()) +e.getMessage());
		
	}
	
}
class MyTask implements Runnable{

	@Override
	public void run() {
		System.out.println("My task is started running...");	
		 throw new ArithmeticException("Hey You!!!!");
	}
	
	
}