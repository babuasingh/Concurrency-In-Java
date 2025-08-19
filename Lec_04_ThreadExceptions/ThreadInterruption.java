package Lec_04_ThreadExceptions;

public class ThreadInterruption {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            try {
                while (!Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName()+" checking for updates");
                        Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        thread.start();

        Thread.sleep(6000);
        thread.interrupt();
    }

}
