package ConcurrencyQuestions;

import java.util.concurrent.Semaphore;

/*
The problem is that there are 5 philosophers sitting around a circular table.
Each philosopher has a plate of spaghetti in front of them and a fork on either side.
To eat, a philosopher needs to use both the left and right forks.
However, each fork can only be used by one philosopher at a time.
The challenge is to design a protocol that allows the philosophers to eat without causing a deadlock
(where all philosophers are waiting for each other) or starvation (where one or more philosophers never get to eat).
So there are 5 philosophers and 5 forks. Each philosopher needs to acquire the left and right fork to eat.
 */

class DiningPhilosophers {
    Semaphore philosophers;
    Semaphore[] forks;

    public DiningPhilosophers() {
        /*
To prevent deadlock, we can use a semaphore to limit the number of philosophers that can attempt to eat at the same time.
If we allow all 5 philosophers to try to eat at the same time, they could all pick up their left fork and then wait indefinitely
for the right fork, resulting in a deadlock. By using a semaphore with a count of 4, we ensure that at most 4 philosophers can attempt
to eat at the same time, which guarantees that at least one philosopher will be able to eat and release their forks,
allowing the others to proceed without getting stuck in a deadlock.
         */
        philosophers = new Semaphore(4);
        forks = new Semaphore[5];
        for (int i = 0; i < 5; i++)
            forks[i] = new Semaphore(1);
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher, Runnable pickLeftFork, Runnable pickRightFork, Runnable eat, Runnable putLeftFork, Runnable putRightFork) throws InterruptedException {

        philosophers.acquire();

        forks[philosopher].acquire();
        forks[(philosopher + 1) % 5].acquire();

        pickLeftFork.run();
        pickRightFork.run();

        eat.run();

        putRightFork.run();
        forks[(philosopher + 1) % 5].release();

        putLeftFork.run();
        forks[philosopher].release();

        philosophers.release();


    }
}
