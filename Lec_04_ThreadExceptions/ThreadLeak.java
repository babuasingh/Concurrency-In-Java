package Lec_04_ThreadExceptions;


class SafeLock{
    private final Object object=new Object();

    void waitForSignal(){
        synchronized (object){
            try{
                System.out.println(Thread.currentThread().getName()+" is waiting......");
                object.wait(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class ThreadLeak {
    public static void main(String[] args) {
//        SafeLock obj=new SafeLock();
//        new Thread(obj::waitForSignal," thread-1").start();

//        int val = Runtime.getRuntime().availableProcessors();
//        System.out.println(val);
    }
}
