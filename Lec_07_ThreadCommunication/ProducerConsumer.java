package Lec_07_ThreadCommunication;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public List<Double> list = new ArrayList<>(); // buffer
    public int maxbuffersize = 5;
    public volatile boolean running = true;


    public void produce() {
        while (running) {
            try {
                synchronized (list) {
                    while (list.size() == maxbuffersize && running) {
                        System.out.println(Thread.currentThread().getName() + " is waiting to produce.....");
                        list.wait();
                    }
                    if(!running) // make sures that it stops after seconds(mentioned in the main method)
                        break;
                    Double num = Math.random();
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
                        list.wait();
                    }
                    if(!running) // make sures that it stops after seconds(mentioned in the main method)
                            break;
                    Double val = list.remove(list.size() - 1);
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

    public static void main(String[] args) throws InterruptedException {

        ProducerConsumer pc = new ProducerConsumer();

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
