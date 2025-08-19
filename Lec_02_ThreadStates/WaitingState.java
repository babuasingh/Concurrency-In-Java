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
