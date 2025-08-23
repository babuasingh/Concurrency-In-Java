package Lec_10_RaceCondition;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

class RaceCondition{
    public int counter=0;

    public void increment(){
        counter=counter+1;
    }
}

class RaceConditionSolution1{
    public int counter=0;

    public synchronized void increment(){
        counter=counter+1;
    }
}

class RaceConditionSolution2{
  public AtomicInteger counter = new AtomicInteger();

    public synchronized void increment(){
        counter.incrementAndGet();
    }
}


public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
        RaceCondition obj=new RaceCondition();
        RaceConditionSolution1 obj1=new RaceConditionSolution1();
        RaceConditionSolution2 obj2=new RaceConditionSolution2();

        Thread t1=new Thread(()->{
            for(int i=0;i<10000;i++){
                obj2.increment();
            }
        });

        Thread t2=new Thread(()->{
            for(int i=0;i<10000;i++){
                obj2.increment();
            }
        });

        t1.start();t2.start();

        t1.join();t2.join();

        System.out.println("Final value of counter : "+obj2.counter);
    }
}
