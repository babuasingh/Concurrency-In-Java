package Lec_02_ThreadStates;

//
//class Waiter extends Thread{
//    private final Object object;
//    Waiter(Object object){
//        this.object=object;
//    }
//
//    @Override
//    public void run() {
//        synchronized (object) {
//            System.out.println("Waiter : I have taken the order , waiting for Chef to complete it");
//            try {
//                object.wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Waiter : The Order is ready .");
//        }
//    }
//
//}
//
//class Chef extends Thread{
//    private final Object object;
//
//    Chef(Object object){
//        this.object=object;
//    }
//
//    @Override
//    public void run() {
//        synchronized (object) {
//            try {
//                Thread.sleep(1000);
//                System.out.println("Chef : I am preparing the order");
//                Thread.sleep(2000);
//                System.out.println("Chef : The order is ready");
//                object.notify();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//}


public class WaitingState {
    public static void main(String[] args) {

        // Just remember : wait() and notify() should be called inside synchronized block only
        // Also , wait() releases the lock , notify() does not release the lock .
        // After notify() is called , the waiting thread does not resume immediately , it has to wait for the lock to be released
        // by the notifying thread first before it can resume execution .
        // wait() relaeses the lock immediately upon being called , but notify() only signals the waiting thread and continues to hold the lock until the synchronized block is exited .
        // Difference between wait and sleep :
        // 1. wait() is called on an object and releases the lock on that object
        // 2. sleep() is a static method of Thread class and does not release any locks held by the thread

        Object object=new Object();
//        Thread waiter = new Waiter(obj);
//        Thread chef=new Chef(obj);

        Thread waiter = new Thread(()->{
            synchronized (object) {
                System.out.println("Waiter : I have taken the order , waiting for Chef to complete it");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Waiter : The Order is ready .");
            }
        });

        Thread chef = new Thread(()->{
            synchronized (object) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Chef : I am preparing the order");
                    Thread.sleep(2000);
                    System.out.println("Chef : The order is ready");
                    object.notify();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        waiter.start();
        chef.start();
    }
}
