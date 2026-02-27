package ConcurrencyQuestions;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

// Question Link: https://leetcode.com/problems/fizz-buzz-multithreaded/description/

class FizzBuzz {
    private int n;
    Semaphore Nsem = new Semaphore(1);
    Semaphore FBsem = new Semaphore(0);
    Semaphore Bsem = new Semaphore(0);
    Semaphore Fsem = new Semaphore(0);
    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
   public void fizz(Runnable printFizz) throws InterruptedException {
    for (int i = 3; i <= n; i += 3) {
        if(i%5==0)
            continue;
            Fsem.acquire();
            printFizz.run();
            Nsem.release();
    }
}

public void buzz(Runnable printBuzz) throws InterruptedException {
    for (int i = 5; i <= n; i += 5) {
        if(i%3==0)
            continue;
            Bsem.acquire();
            printBuzz.run();
            Nsem.release();
    }
}

public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
    for (int i = 15; i <= n; i += 15) {
        FBsem.acquire();
        printFizzBuzz.run();
        Nsem.release();
    }
}


    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {

        for(int i=1;i<=n;i++){
            Nsem.acquire();
            if(i%3==0 && i%5==0){
               FBsem.release();
            }else if(i%3==0 && i%5!=0){
                Fsem.release();
            }else if(i%3!=0 && i%5==0){
               Bsem.release();
            }else{
                printNumber.accept(i);
                Nsem.release();
            }
        }

    }
}