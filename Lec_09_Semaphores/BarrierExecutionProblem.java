package Lec_09_Semaphores;

import java.util.concurrent.Semaphore;

public class BarrierExecutionProblem {
    int count=0;
    int noOfThreads;
    Semaphore mutex = new Semaphore(1);
    Semaphore barrier = new Semaphore(0);

    BarrierExecutionProblem(int num){
        this.noOfThreads = num;
    }

    void await() {
        try {
            mutex.acquire();
            System.out.println(Thread.currentThread().getName()+" is in await method");
            count++;
            System.out.println(Thread.currentThread().getName()+" is waiting at the barrier");
            if(count==noOfThreads){
                barrier.release(noOfThreads);
                System.out.println("All the threads have reached the barrier");
            }
            mutex.release();
            barrier.acquire();
            System.out.println(Thread.currentThread().getName()+" has continued after the barrier");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        BarrierExecutionProblem barrierExecutionProblem = new BarrierExecutionProblem(3);
        for(int i=0;i< barrierExecutionProblem.noOfThreads;i++){
            new Thread(()->barrierExecutionProblem.await() , "Thread-"+i).start();
        }

    }
}
