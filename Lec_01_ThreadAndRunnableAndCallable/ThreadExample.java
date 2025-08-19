package Lec_01_ThreadAndRunnableAndCallable;


class MyThread extends Thread {

    @Override
    public void run(){
        for (int i=0;i<5;i++){
            System.out.println(Thread.currentThread().threadId()+" is running iteration "+i);
            try{
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().threadId()+" interrupted "+e.getMessage());
            }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        Thread one=new MyThread();
        Thread two=new MyThread();
        one.start();
        two.start();
    }
}
