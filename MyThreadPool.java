package com.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
   BlockingQueue taskQueue;
   PoolWorker[] poolThreads;
   int nThreads;
   
   public MyThreadPool(int nThread) {
	   this.nThreads=nThread;
	   taskQueue= new LinkedBlockingQueue<>();
	   poolThreads = new PoolWorker[nThread];
	   
	   for(int i=0;i<nThread;i++) {
		   poolThreads[i]= new PoolWorker();
		   poolThreads[i].start();
	   }
   }
   
   public void execute(Runnable runnable) {
	   synchronized(taskQueue) {
		   taskQueue.add(runnable);
		   taskQueue.notifyAll();
	   }
	  
   }
   private class PoolWorker extends Thread{
		@Override
		public void run() {
			Runnable task;
			while(true) {
				  synchronized(taskQueue) {
				while(taskQueue.isEmpty()) {
					try {
						taskQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				task= (Runnable) taskQueue.poll();
				task.run();
			  }
			}
		}
	}
}


