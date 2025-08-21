package Lec_06_ThreadSynchronization;


class CounterSyncBlock {
    private int count = 0;
    // Explicit lock object for finer control.
    private final Object lock = new Object();

    public void increment() {
        // Non-critical part: runs without locking.
        System.out.println(Thread.currentThread().getName()+" Non-Synchronized part (pre-processing): ");
        // Critical section: only this part is synchronized.
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName()+" Synchronized Block - Start increment: ");
            count++;
            System.out.println( Thread.currentThread().getName()+" Synchronized Block - Counter value after increment: " + count);
            System.out.println(Thread.currentThread().getName()+" Synchronized Block - End increment: ");
        }
        // Non-critical part: runs after the synchronized block.
        System.out.println(Thread.currentThread().getName()+" Non-Synchronized part (post-processing): " + Thread.currentThread().getName());
    }

    public int getCount() {
        return count;
    }
}



public class SynchronizationBlock {
    public static void main(String[] args) {
        CounterSyncBlock counter = new CounterSyncBlock();
        int numberOfThreads = 5;
        Thread[] threads = new Thread[numberOfThreads];
        // Create and start threads that execute the increment method.
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    counter.increment();
                }
            }, "Thread-" + (i + 1));
            threads[i].start();
        }
        // Wait for all threads to finish.
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Display the final value of the counter.
        System.out.println("Final counter value: " + counter.getCount());
    }
}
