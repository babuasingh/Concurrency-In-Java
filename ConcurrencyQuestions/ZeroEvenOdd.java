package ConcurrencyQuestions;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

//QuestionLink : https://leetcode.com/problems/print-zero-even-odd/description/

class ZeroEvenOdd {
    private int n;
    Semaphore Z;
    Semaphore E;
    Semaphore O;
    int count;
    public ZeroEvenOdd(int n) {
        this.n = n;
        count=1;
        Z=new Semaphore(1);
        O=new Semaphore(0);
        E=new Semaphore(0);
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for(int i=0;i<n;i++) {
            Z.acquire();
            printNumber.accept(0);
            if(count%2==1)
                O.release();
            else E.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for(int i=2;i<=n;i+=2) {
            E.acquire();
            printNumber.accept(count);
            count++;
            Z.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i+=2) {
            O.acquire();
            printNumber.accept(count);
            count++;
            Z.release();
        }
    }
}