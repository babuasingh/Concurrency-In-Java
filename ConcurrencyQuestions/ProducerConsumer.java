package ConcurrencyQuestions;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/*
    Approach 1 : Threads -> Using wait and notify(1 P and 1 C)
    Approach 2 : BlockingQueue -> Java inbuilt Queue for handling concurrency
    Approach 3 : Executors -> when multiple P and C
 */

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {

//        PCApproach1 obj1 = new PCApproach1();
//        obj1.solve();
//
//        PCApproach2 obj2 = new PCApproach2();
//        obj2.solve();
//
//        ProducerConsumerByMe obj = new ProducerConsumerByMe();
//        obj.solve();
    }
}


class PCApproach1 {

    Queue<Integer> queue = new LinkedList<>();
    int maxSize = 5;

    public void solve() {
        Thread producer = new Thread(()->{
            for(int i=1;i<=10;i++) {
                synchronized (queue) {
                    while(queue.size()== maxSize) {
                        System.out.println("Producer is waiting as queue is full");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    int val =(int) (Math.random() * 100);
                    queue.add(val);
                    System.out.println("Produced Value : "+val);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    queue.notifyAll();
                }
            }
        },"producer");

        Thread consumer = new Thread(()->{
            for(int i=1;i<=10;i++) {
                synchronized (queue) {
                    while(queue.size()== 0) {
                        System.out.println("Consumer is waiting to consume");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    int val = queue.poll();
                    System.out.println("Consumed : "+val);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    queue.notifyAll();
                }
            }
        },"consumer");

        producer.start();
        consumer.start();


        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Completed");
    }
}

class PCApproach2 {
    public void solve() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        Thread producer = new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    queue.put(i); // This will block if buffer is full
                    System.out.println(System.nanoTime() + " Produced " + i);
                    Thread.sleep((int)(Math.random()*1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread consumer = new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    Integer val = queue.take(); // This will block if buffer is empty
                    System.out.println(System.nanoTime() + " Consumed " + val);
                    Thread.sleep((int)(Math.random()*1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        producer.start();
        consumer.start();
    }
}

class ProducerConsumerByMe {
    public Queue<Double> list = new LinkedList<>(); // buffer
    public int maxbuffersize = 5;
    public volatile boolean running = true;


    public void produce() {
        while (running) {
            try {
                synchronized (list) {
                    while (list.size() == maxbuffersize && running) {
                        System.out.println(Thread.currentThread().getName() + " is waiting to produce.....");
                        list.notifyAll(); //Signal any waiting consumer before waiting
                        list.wait();
                    }
                    if(!running) // make sures that it stops after seconds(mentioned in the main method)
                        break;
                    Double num = Math.random()*100;
                    list.add(num);
                    System.out.println(Thread.currentThread().getName() + " has added a task : " + num + " |  current Jobs : " + list.size());
                    list.notifyAll();
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void consume() {
        while (running) {
            try {
                synchronized (list) {
                    while (list.size() == 0 && running)  {
                        System.out.println(Thread.currentThread().getName() + " is waitinggggggg to consume......");
                        list.notify(); //Signal any waiting producer before waiting
                        list.wait();
                    }
                    if(!running) // make sures that it stops after seconds(mentioned in the main method)
                        break;
                    Double val = list.poll();
                    System.out.println(Thread.currentThread().getName() + " is consuming the value " + val + " |  current Jobs : " + list.size());
                    list.notifyAll();
                }
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        running = false;
        synchronized (list) {
            list.notifyAll();
        }
    }

    public void solve() throws InterruptedException {

        ProducerConsumerByMe pc = new ProducerConsumerByMe();

        int noOfConsumers = 3;

        Thread[] consumer = new Thread[noOfConsumers];

        for (int i = 0; i < noOfConsumers; i++) {
            consumer[i] = new Thread(pc::consume, "consumer-" + (i + 1));

            consumer[i].start();
        }


        Thread producer = new Thread(pc::produce, "producer");
        producer.start();


        Thread.sleep(7000);
        System.out.println("Stopping threads now");
        pc.stop();



        try {
            for (Thread t : consumer)
                t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        try {
            producer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("All threads shutdown");

    }
}