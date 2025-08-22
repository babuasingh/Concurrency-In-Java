package Lec_08_ReentrantLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockExample {

    private int counter = 0;

    public ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + " acquired the lock ");
        counter++;
        System.out.println(Thread.currentThread().getName() + " hsd increased the value of counter to : " + counter);
        System.out.println(Thread.currentThread().getName() + " has released the lock ");
        lock.unlock();
    }

    public int getCounter(){
        return counter;
    }

    public static void main(String[] args) {
        ReentrantLockExample obj = new ReentrantLockExample();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for(int i=0;i<5;i++){
            executorService.submit(()-> obj.increment());
        }

        executorService.shutdown();

        try {
            // Wait for all tasks to finish; if not completed within 5 seconds, then exit.
            if (executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Final counter value: " + obj.getCounter());
            } else {
                System.out.println("Timeout: Not all tasks finished.");
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for tasks to finish.");
            Thread.currentThread().interrupt();
        }
    }
}
