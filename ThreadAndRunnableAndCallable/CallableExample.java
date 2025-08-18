package ThreadAndRunnableAndCallable;

import java.util.concurrent.*;

class MyCallable implements Callable<String> {

    private String name;

    MyCallable(String name){
        this.name=name;
    }

    @Override
    public String call() throws Exception {

        StringBuilder st=new StringBuilder();
        for(int i=0;i<5;i++){
            st.append(" Callable ").append(name).append(" is running ").append(i);
            Thread.sleep(1000);
        }
        return st.toString();
    }
}


public class CallableExample{
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // Create Callable instances
        Callable<String> callable1 = new MyCallable("Task 1");
        Callable<String> callable2 = new MyCallable("Task 2");

        try {
            // Submit Callable tasks to the executor and get Future objects
            Future<String> future1 = executor.submit(callable1);
            Future<String> future2 = executor.submit(callable2);

            // Get results from Future objects
            System.out.println("Result from first task:");
            System.out.println(future1.get()); // Blocks until the task completes

            System.out.println("Result from second task:");
            System.out.println(future2.get()); // Blocks until the task completes

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Task execution interrupted: " + e.getMessage());
        } finally {
            // Shutdown the executor
            executor.shutdown();
        }
    }
}
