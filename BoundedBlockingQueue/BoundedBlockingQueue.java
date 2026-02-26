package BoundedBlockingQueue;
import java.util.*;
import java.util.concurrent.*;


/*
Question : Why cant we use the wait/notify to implement the bounded blocking queue?
Answer : We can use wait/notify to implement the bounded blocking queue but it is not preferred .
The reason is that wait/notify can lead to issues like missed signals and spurious wakeups,
which can cause threads to wait indefinitely or wake up without the intended condition being met.
This can make the implementation more complex and error-prone.

Missed Signals :-> If a producer thread calls notify() before a consumer thread starts waiting, the consumer will miss the signal and wait indefinitely.
Imagine this scenario:
Step 1
Producer executes:
notify();
But at this moment:
No consumer has called wait() yet.
So nothing happens.
The notification disappears.
Step 2
Now consumer executes:
wait();
But the signal already happened.
So now:
Consumer goes to sleep.
Nobody will wake it up.
It waits forever.
💥 Deadlock.

That’s a missed signal.

Spurious Wakeups :-> Threads can wake up without being notified, which can lead to unexpected behavior if the condition is not re-checked after waking up.

On the other hand, using semaphores provides a more robust and straightforward way to manage the synchronization
 between producer and consumer threads, ensuring that the queue operates correctly without the risk of missed signals
 or spurious wakeups. Semaphores allow us to control access to the queue and manage the number of items in the queue more
 effectively, making it a better choice for implementing a bounded blocking queue.
 */

class BoundedBlockingQueue {

    Queue<Integer> queue;
    Semaphore add;
    Semaphore remove;

    BoundedBlockingQueue(int capacity) {
        queue = new LinkedList<>();
        add = new Semaphore(capacity);
        remove = new Semaphore(0);
    }

    void enque(int num)  {
        try {
            add.acquire();
            queue.add(num);
            System.out.println(Thread.currentThread().getName()+" added : "+num +" . Current size "+queue.size());
            remove.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    int deque() {
        try {
            remove.acquire();
            int val= queue.poll();
            System.out.println(Thread.currentThread().getName()+" removed : "+val+" . Current size "+queue.size());
            add.release();
            return val;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        BoundedBlockingQueue bbq = new BoundedBlockingQueue(2);

        Runnable producer = () -> {
            for (int i = 0; i < 5; i++) {
                bbq.enque(i);
                try {
                    Thread.sleep(100); // Simulate time taken to produce an item
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            for (int i = 0; i < 5; i++) {
                bbq.deque();
                try {
                    Thread.sleep(150); // Simulate time taken to consume an item
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread producerThread = new Thread(producer, "Producer");
        Thread consumerThread = new Thread(consumer, "Consumer");

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
