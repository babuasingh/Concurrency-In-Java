package BoundedBlockingQueue;
import java.util.*;
import java.util.concurrent.*;

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
