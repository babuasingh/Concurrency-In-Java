package Lec_03_ThreadPool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class ThreadPoolAndThreadStatesExample implements Runnable{
    private int id;
    ThreadPoolAndThreadStatesExample(int id){
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" is starting task : "+id);
        try {
            Thread.sleep(2000); //simulating the work this thread is doing . Going from Running to Timed_waiting state
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+" is waiting on task : "+id);
                this.wait(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+" has completed task : "+id);
    }
}


class ThreadPoolExample2 implements Runnable {
    private int id;
    private final Object lock;  // shared lock

    ThreadPoolExample2(int id, Object lock) {
        this.id = id;
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) { // all tasks must compete for this lock
            System.out.println(Thread.currentThread().getName() + " is executing task " + id);
            try {
                Thread.sleep(2000); // simulating work
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " has completed task " + id);
        }
    }
}

public class ThreadPoolAndThreadStates {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        System.out.println("Thread Pool Created 🏊‍♂️");


        // each thread is having different resouce to handle , so there is no synchrinization
        System.out.println("Each thread is working on a different resouce , no synchronization");

        for(int i=1;i<=5;i++){
            executorService.submit(new ThreadPoolAndThreadStatesExample(i));
        }

        // Now we have a shared resouce so each thread is sharing lock on same object and we see here synchronization

//        Thread.sleep(12000);
//        System.out.println("Now Threads share the same resouce ,execution one thread at a time");
//        Object object=new Object();
//        for(int i=1;i<=5;i++){
//            executorService.submit(new ThreadPoolExample2(i,object));
//        }



        executorService.shutdown();
        System.out.println(" Thread pool shutdown initiated ");
        try {
            // Wait for all threads to terminate
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                System.out.println("Forcing Shutdown! 🚧");
                // If shutdownNow is called, threads currently in RUNNING state will be interrupted.
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println("All Threads Terminated ✅");
    }
}
