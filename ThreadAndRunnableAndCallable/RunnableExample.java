package ThreadAndRunnableAndCallable;

//class ThreadAndRunnableAndCallable.MyRunnable implements Runnable {
//    @Override
//    public void run(){
//        for (int i=0;i<5;i++){
//            System.out.println(Thread.currentThread().threadId()+" is running iteration "+i);
//            try{
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                System.out.println(Thread.currentThread().threadId()+" interrupted "+e.getMessage());
//            }
//        }
//    }
//}

class MyRunnable {

    public void method(){
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


public class RunnableExample {
    public static void main(String[] args) {
        /* this works for the commented ThreadAndRunnableAndCallable.MyRunnable class
        ThreadAndRunnableAndCallable.MyRunnable obj=new ThreadAndRunnableAndCallable.MyRunnable();
        Thread t1=new Thread(obj);
        Thread t2=new Thread(obj);
        t1.start();
        t2.start();
         */


        Thread t1 =new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+ " is in the run method");
        },"thread1");

        t1.start();
    }
}
