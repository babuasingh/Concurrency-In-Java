package Lec_09_Semaphores;


import java.util.concurrent.Semaphore;

class BinarySemaphore {
    public final Semaphore semaphore = new Semaphore(1);

    public void section(String threadName){
        try {
            System.out.println(threadName + " is attempting to acquire the lock.");
            semaphore.acquire(); // Acquire the semaphore
            System.out.println(threadName + " acquired the lock.");
            Thread.sleep(1000); // Simulate work in the critical section
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(threadName + " released the lock.");
            semaphore.release(); // Release the semaphore

        }
    }
}

class CountingSemaphore {
    public final Semaphore semaphore = new Semaphore(3);

    public void section(String threadName){

        try {
            System.out.println(threadName + " is attempting to acquire the lock.");
            semaphore.acquire(); // Acquire the semaphore
            System.out.println(threadName + " acquired the lock.");
            Thread.sleep(1000); // Simulate work in the critical section
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(threadName + " released the lock.");
            semaphore.release(); // Release the semaphore

        }
    }
}


public class SemaphoreExample {
    public static void main(String[] args) throws InterruptedException {
//        BinarySemaphore binarySemaphore = new BinarySemaphore();
//
//        Thread first = new Thread(()->binarySemaphore.section("first"));
//        Thread second = new Thread(() -> binarySemaphore.section("second"));
//
//        first.start();
//        second.start();
//
//        Thread.sleep(3000);

        CountingSemaphore countingSemaphore=new CountingSemaphore();
        for(int i=0;i<5;i++){
            final int num=i;
            Thread t = new Thread(()->countingSemaphore.section("Thread-"+num));
            t.start();
        }


    }
}
