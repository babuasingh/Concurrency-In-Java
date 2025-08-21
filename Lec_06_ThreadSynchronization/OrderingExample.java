package Lec_06_ThreadSynchronization;

class OrderingExample {
    private int data;
    private volatile boolean ready = false;

    public void writer() {
        data = 42;         // Step 1
        ready = true;      // Step 2
        System.out.println("Writer finished: data = 42, ready = true");
    }

    public void reader() {
        while (!ready) {
            // wait until writer sets ready
        }
        System.out.println("Reader sees data = " + data);
    }

    public static void main(String[] args) throws InterruptedException {
        OrderingExample ex = new OrderingExample();

        Thread reader = new Thread(ex::reader);
        Thread writer = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            ex.writer();
        });

        reader.start();
        writer.start();

        reader.join();
        writer.join();
    }
}
