package com.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ConcurrentHashMap;

public class MConcurrentHM {
	static volatile  String s;
	public static void main(String[] args) {
		ConcurrentHashMap chm= new ConcurrentHashMap();
		for(int i=0;i<=10;i++) {
			s =	"av "+i;
			chm.put(i,s );
			}
		Writer w= new Writer(chm,s);
		Reader r= new Reader(chm);
		Thread t1= new Thread(w);
		Thread t2= new Thread(r);
		t1.start();
		t2.start();
		t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName()+" Thread 1---> "+e.getMessage());
				
			}
			
		});
		t2.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName()+" Thread 2 ---> "+e.getMessage());
				
			}
			
		});
 }
}

class Writer implements Runnable{
   ConcurrentHashMap chm;
   static volatile  String s;
   public Writer(ConcurrentHashMap chm,String s) {
	   this.chm=chm;
	   this.s=s;
   }

   @Override
    public void run() {
		// TODO Auto-generated method stub
        
	   try {
			for(int i=0;i<=10;i++) {
			Thread.sleep(100);
			 s = "avav "+2*i;
				chm.put(i,s );
				if(i==9) {
					throw new RuntimeException("hey you!");
				}
			}
			
		}catch(InterruptedException e) {
			System.out.println(e);
		}
	   
	}
   
}

class Reader implements Runnable{
	   ConcurrentHashMap chm;
	   public Reader(ConcurrentHashMap chm) {
		   this.chm=chm;
	   }

	   @Override
	    public void run() {
			// TODO Auto-generated method stub
		   try {
			for(int i=0;i<=10;i++) {
				Thread.sleep(1000);
				System.out.println(chm.get(i)); 
			}
		   }catch(InterruptedException e) {
				System.out.println(e);
			}
		}
	   
	}