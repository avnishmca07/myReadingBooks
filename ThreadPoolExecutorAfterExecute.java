package com.thread;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorAfterExecute {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService threadPool = new MyThreadpoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
		
		threadPool.execute(new MyTask());
		threadPool.shutdown();
	}

}

class MyThreadpoolExecutor extends ThreadPoolExecutor{

	public MyThreadpoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void afterExecute(Runnable r, Throwable t) {
		 super.afterExecute(r, t);
		 if(t==null && r instanceof Future<?>) {
			 try {
				 Object result= ((Future<?>)r).get();
			 }catch(ExecutionException|InterruptedException e) {
				 
				 Thread.currentThread().interrupt();
			 }
		 }
		 if(t!=null) {
			 System.err.println("Uncaught exception is detected! " + t
	                    + " st: " + Arrays.toString(t.getStackTrace()));
			 execute(r);
		 }
	}
	
}
