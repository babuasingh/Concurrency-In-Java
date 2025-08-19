package Lec_01_ThreadAndRunnableAndCallable;

class MyWait{

    synchronized void waitMethod(){
        System.out.println(Thread.currentThread().getName() +" is going into wait state and has released the lock");
        try {
            wait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() +" has come out of the wait state and acquired the lock again");
    }

    synchronized void notifyMethod() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName()+" is notifying the waiting thread ");
        notify();
    }

}

public class WaitExample {
    public static void main(String[] args) {

        MyWait obj=new MyWait();
        Thread t1=new Thread(()->obj.waitMethod(),"Thread-1");

        Thread t2=new Thread(()-> {
            try {
                obj.notifyMethod();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread-2");

        t1.start();
        t2.start();

    }
}
