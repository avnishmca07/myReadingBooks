package com.thread;

import java.util.HashMap;

public class MyReadReentrantLock {

	private  HashMap<Thread,Integer> readerThreads= new HashMap<>();
	private int writers=0;
	private int writersRequest=0;
	
	public synchronized void readLock() throws InterruptedException {
		Thread currentThread = Thread.currentThread();
		if(!canGetReadAccess(currentThread)) {
			wait();
		}
		readerThreads.put(currentThread, getReadAccessCount(currentThread)+1);
	}
	
	public synchronized void readUnLock() throws InterruptedException {
		Thread currentThread = Thread.currentThread();
		int accessCount= getReadAccessCount(currentThread);
		if(accessCount==1) { readerThreads.remove(currentThread);} else {
		readerThreads.put(currentThread, getReadAccessCount(currentThread)-1);}
		notifyAll();
	}
	
	private boolean canGetReadAccess(Thread callingThread) {
		if(writers>0) return false;
		if(writersRequest >0) return false;
		if(isReader(callingThread)) return true;
		return true;
	}
	
	private boolean isReader(Thread callingThread) {
		return readerThreads.get(callingThread) !=null;
	}
	
	
	 private int getReadAccessCount(Thread callingThread){
		    Integer accessCount = readerThreads.get(callingThread);
		    if(accessCount == null) return 0;
		    return accessCount.intValue();
		  }
}
