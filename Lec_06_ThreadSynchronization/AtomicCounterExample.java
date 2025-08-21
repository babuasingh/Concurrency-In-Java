package Lec_06_ThreadSynchronization;


import java.util.concurrent.atomic.AtomicInteger;

//AtomicInteger is lock-free but still thread-safe.

class AtomicCounter{
    AtomicInteger count = new AtomicInteger(0);

    public void increment(){
        int newvalue = count.incrementAndGet();
        System.out.println(Thread.currentThread().getName()+" has incremented value to "+newvalue);
    }

    public int getCount(){
        return count.get();
    }
}

public class AtomicCounterExample {
    public static void main(String[] args) throws InterruptedException {

        AtomicCounter atomicCounter=new AtomicCounter();

        int noOfThreads = 10;
        Thread [] threads=new Thread[noOfThreads];
        int operationsPerThread=10;

        for(int i=0;i<noOfThreads;i++){
            threads[i]=new Thread(()->{
                for(int j=0;j<operationsPerThread;j++) {
                    atomicCounter.increment();
                }
            });

            threads[i].start();
        }


        for(Thread t:threads)
            t.join();

        System.out.println("Final Value of counter "+atomicCounter.getCount());

    }
}
