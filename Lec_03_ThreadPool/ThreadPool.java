package Lec_03_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class ThreadPoolExample implements Runnable{
    private int id;
    ThreadPoolExample(int id){
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is executing task "+id);
        try {
            Thread.sleep(2000); //simulating the work this thread is doing
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+" has completed task "+id);
    }
}


public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        for(int i=1;i<=5;i++){
            executorService.submit(new ThreadPoolExample(i));
        }


        executorService.shutdown();


    }
}
