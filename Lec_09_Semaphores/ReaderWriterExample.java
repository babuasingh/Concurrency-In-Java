package Lec_09_Semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ReaderWriterExample {

    int totalReaders;
    int count = 0;
    public Semaphore mutex; // used for mutual exclusion of writers and it has nothing to do with readers
    public Semaphore writer;

    ReaderWriterExample(int n) {
        totalReaders = n;
        mutex = new Semaphore(1);
        writer = new Semaphore(1);
    }

    void readerLock() {
        try {
            mutex.acquire();
            count++;
            if (count == 1) {
                writer.acquire(); // first thread locks the writer
            }
            mutex.release();
            System.out.println(Thread.currentThread().getName() + " is reading..............");
            Thread.sleep(2000); // reading time

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void readUnlock() {

        try {
            mutex.acquire();
            System.out.println(Thread.currentThread().getName() + " is leaving............");
            count--;
            if (count == 0) {
                writer.release();
            }
            mutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void writerLock() {
        try {
            writer.acquire();
            System.out.println(Thread.currentThread().getName() + " is writing..................");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void writerUnLock() {
        System.out.println(Thread.currentThread().getName() + " has completed writing..................");
        writer.release();
    }

    public static void main(String[] args) {
        int nR = 3;
        int nW = 2;
        ReaderWriterExample readerWriterExample = new ReaderWriterExample(nR);
        for (int i = 0; i < nR; i++) {
            Thread readers = new Thread(() -> {
                while (true) {
                    readerWriterExample.readerLock();
                    readerWriterExample.readUnlock();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "Reader-" + i);
            readers.start();
        }

        for (int i = 0; i < nW; i++) {
            Thread writers = new Thread(() -> {
                while (true) {
                    readerWriterExample.writerLock();
                    readerWriterExample.writerUnLock();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "Writer-" + i);
            writers.start();
        }

    }
}
