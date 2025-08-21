package Lec_06_ThreadSynchronization;

class SharedResource {
    volatile boolean flag = false;

    void writer() {
        System.out.println("Writer sets flag = true");
        flag = true; // Thread A sets it
    }

    void reader() {
        System.out.println("Reader waiting...");
        while (!flag) {
            // may never exit due to caching!
        }
        System.out.println("Reader detected flag as true!");
    }

    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();

        Thread reader = new Thread(resource::reader);
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
            resource.writer();
        });

        reader.start();
        writer.start();

        reader.join();
        writer.join();
    }
}
