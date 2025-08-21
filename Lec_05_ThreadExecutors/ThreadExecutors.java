package Lec_05_ThreadExecutors;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ThreadExecutors {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        • Separation of task submission from execution details

//        ExecutorService executor = Executors.newFixedThreadPool(3);
//        executor.submit(() -> {
//            System.out.println("Task executed by: " + Thread.currentThread().getName());
//        });

//        Built-in thread pooling and resource management 🏊‍♂️

//        ExecutorService pool = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 10; i++) {
//            pool.execute(() -> {
//                System.out.println("Running: " + Thread.currentThread().getName());
//            });
//        }


//        Task queuing, scheduling, and execution policies

//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.schedule(() -> {
//            System.out.println("Executed after 3 seconds!");
//        }, 3, TimeUnit.SECONDS);
//        scheduler.shutdown();



//        Monitoring and management facilities

//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//        executor.submit(()->{
//            try {
//                Thread.sleep(2000);
//            }catch (InterruptedException e){
//
//            }
//        });
//        System.out.println(executor.getActiveCount());
//        System.out.println(executor.getQueue().size());
//        executor.shutdown();

//        Core Executor Interfaces and Classes
//        Executor executor =command -> {
//                new Thread(command).start();
//        };
//
//        executor.execute(()->
//                System.out.println("In the method run by "+Thread.currentThread().getName())) ;


//        ExecutorService: Extends Executor with lifecycle management :

//        try {
//            ExecutorService executorService = Executors.newFixedThreadPool(2);
//            Future<String> future = executorService.submit(() -> "Hello ExecutorService");
//            System.out.println(future.get()); // Output: Hello ExecutorService
//            executorService.shutdown();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }


//        ScheduledExecutorService: Adds task scheduling capabilities

//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
//        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("Executing every 2 sec "),2,2,TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleWithFixedDelay(()-> System.out.println("Every 2 seconds"),0,2,TimeUnit.SECONDS);


//        ThreadPoolExecutor: Primary implementation of ExecutorService

//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
//                2,
//                4,
//                6,
//                TimeUnit.SECONDS,
//                new LinkedBlockingDeque<>()
//        );
//        poolExecutor.submit(()-> System.out.println("Running"));


//        Factory class for creating executor instances
//        Instead of writing:
//        new ThreadPoolExecutor(...);
//        we use
//        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
//        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);


        /*execute takes only Runnable task and dont return anything
        * whereas submit takes both Runnable and Callable task and returns a Future Object
        * */

//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<String> future = executor.submit(() -> "Hello from Callable");
//        System.out.println(future.get());
//        executor.shutdown();

//        ExecutorService executor = Executors.newFixedThreadPool(2); // takes the number of threads as arguments
//        // invokeAll Example
//        Collection<Callable<String>> allTasks = Arrays.asList(() -> "Task 1", () -> "Task 2");
//        try {
//            // Process results
//            List<Future<String>> results = executor.invokeAll(allTasks);
//            // Process timeout results
//            List<Future<String>> timeoutResults = executor.invokeAll(allTasks, 1, TimeUnit.SECONDS);
//
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }


//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Future<?> future = executorService.submit(()->{
//            throw new RuntimeException("Boom!");
//        });
//        try {
//            future.get(); // Will throw ExecutionException wrapping the original
//        } catch (ExecutionException e) {
//            System.out.println("Caught: " + e.getCause()); // prints: Boom!
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        executorService.shutdown();

    }

}
