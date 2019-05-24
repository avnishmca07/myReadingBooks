package com.thread;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

	public static void main(String[] args)  {
		ExecutorService threadpool= Executors.newFixedThreadPool(10);
		List<Future<String>> futureList= new ArrayList<>();
		for(int i=0;i<2;i++) {
			Future<String> future=threadpool.submit(new MyTask1());
			futureList.add(future);
		}
		threadpool.shutdown();
		System.out.println(" "+futureList.size());
		for(Future<String> future : futureList) {
			System.out.println(future);
			try {
				future.get();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Animal a = new Dog2();
		Dog2 d = new Dog2();
		d.eat(); // ok
       
	}

}

class MyTask1 implements Callable{

	@Override
	public String call() throws Exception {
    String name= Thread.currentThread().getName();
    throw new RuntimeException("Hey You!");
	}
	
}

class Animal {
public void eat() throws Exception {
// throws an Exception
}
}
class Dog2 extends Animal {
	@Override
public void eat()  { /* no Exceptions */}
}
