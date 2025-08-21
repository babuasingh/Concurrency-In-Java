package Lec_06_ThreadSynchronization;

class Counter{

    public int count=0;

    public synchronized void increment(){
        System.out.println(Thread.currentThread().getName()+" is currently running ");
        System.out.println(Thread.currentThread().getName()+" : The initial value of counter is : "+count);
        count++;
        System.out.println(Thread.currentThread().getName()+" updated the value of count to "+count);
    }

}

public class SynchronizationMethod {
    public static void main(String[] args) throws InterruptedException {

        Counter counter=new Counter();
        int n=5;
        Thread []threads = new Thread[n];
        for(int i=0;i<n;i++){
//            threads[i]=new Thread(()->counter.increment(),"Thread - "+i);
            threads[i]=new Thread(counter::increment , "Thread - "+i);
            threads[i].start();
//            threads[i].join();
        }

        for(int i=0;i<n;i++) {
            threads[i].join();
        }

        System.out.println("The final value of counter is "+counter.count);
    }
}
