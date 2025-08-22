package Lec_07_ThreadCommunication;

public class WaitNotifyExample {

    private final Object object = new Object();
    private volatile boolean condition = false;

    void doWait() {
        synchronized (object) {
            while (!condition) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting.............");
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + " has resumed action ");
        }
    }

    void doNotify() {
        synchronized (object) {
            condition = true;
            System.out.println(Thread.currentThread().getName() + " called notify .");
            object.notify();  // Wakes up one waiting thread (if any)
        }
    }

    void doNotifyAll() {
        synchronized (object) {
            condition = true;
            System.out.println(Thread.currentThread().getName() + " called notifyAll .");
            object.notifyAll();  // Wakes up one waiting thread (if any)
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // NotifyAll Example
        System.out.println("Illustrating NotifyAll Example ");
        WaitNotifyExample waitNotifyExample = new WaitNotifyExample();
        Thread r1 = new Thread(() -> {
            waitNotifyExample.doWait();
        }, "reader1");

        Thread r2 = new Thread(() -> {
            waitNotifyExample.doWait();
        }, "reader2");

        Thread r3 = new Thread(() -> {
            waitNotifyExample.doWait();
        }, "reader3");

        r1.start();
        r2.start();
        r3.start();

        Thread.sleep(2000);
        Thread w1 = new Thread(waitNotifyExample::doNotifyAll, "writer");
        w1.start();

        r1.join();
        r2.join();
        w1.join();

        Thread.sleep(3000);

        System.out.println("Illustrating Notify Example ");

        WaitNotifyExample waitNotifyExample2 = new WaitNotifyExample();
        Thread rd1 = new Thread(waitNotifyExample2::doWait, "reader 1");
        Thread rd2 = new Thread(waitNotifyExample2::doWait, "reader 2");
        Thread rd3 = new Thread(waitNotifyExample2::doWait, "reader 3");

        rd1.start();
        rd2.start();
        rd3.start();

        Thread.sleep(3000);

        Thread wr1 = new Thread(waitNotifyExample2::doNotify, "writer 1");
        wr1.start();

        Thread.sleep(3000);
        System.out.println("some threads are still running .. we need to call notifyAll to stop them all");

        Thread wr2 = new Thread(waitNotifyExample2::doNotifyAll, "writer 2");
        wr2.start();

        rd1.join();rd2.join();rd3.join();wr1.join();wr2.join();
        Thread.sleep(2000);
        System.out.println("Main Thread execution also completed");
    }
}

// Mercedesbenz@2002
