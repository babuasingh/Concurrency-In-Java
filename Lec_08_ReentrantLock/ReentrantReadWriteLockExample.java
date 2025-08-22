package Lec_08_ReentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {

    public ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    public int counter=0;

    private void simulateWork() {
        long sum = 0;
        for (int i = 0; i < 500000; i++) {
            sum += i;
        }
        // (The computed sum is discarded; its purpose is solely to consume CPU time.)
    }


    public void readValue(){
        try {
            rwlock.readLock().lock();
            System.out.println(Thread.currentThread().getName()+" is reading counter : "+counter);
            simulateWork();
            System.out.println(Thread.currentThread().getName()+" has finished reading.");
        }finally {
            System.out.println(Thread.currentThread().getName()+" has released the lock. ");
            rwlock.readLock().unlock();
        }
    }

    public void writeValue(int newvalue){
        try {
            rwlock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+" is doing some work " );
            simulateWork();
            counter= newvalue;
            System.out.println(Thread.currentThread().getName()+" has finished the work");
        }finally {
            System.out.println(Thread.currentThread().getName()+" has released the lock. ");
            rwlock.writeLock().unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantReadWriteLockExample lockExample = new ReentrantReadWriteLockExample();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(lockExample::readValue);
        executorService.submit(lockExample::readValue);
        executorService.submit(()-> lockExample.writeValue(100));
        executorService.submit(lockExample::readValue);
        executorService.submit(lockExample::readValue);
        executorService.submit(()-> lockExample.writeValue(120));
        executorService.submit(lockExample::readValue);
        executorService.submit(lockExample::readValue);

        executorService.shutdown();
    }
}
