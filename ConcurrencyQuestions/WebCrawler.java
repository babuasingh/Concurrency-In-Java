package ConcurrencyQuestions;
//todo : implement a web crawler using multi threading and synchronization concepts

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

interface HtmlParser {
    List<String> getUrls(String url);
}


// solution 1 : using phaser
public class WebCrawler {

    public String hostname;
    public HtmlParser parser;
    ExecutorService executorService;
    public Map<String, Boolean> map = new ConcurrentHashMap<>();
    Phaser phaser;

    public List<String> crawl(String startUrl, HtmlParser parser) {
        hostname = startUrl.split("/")[2];
        this.parser = parser;
        phaser = new Phaser(1);
        executorService = Executors.newFixedThreadPool(5);
        map.put(startUrl, true);
        phaser.register();
        executorService.submit(new Task(startUrl));

        phaser.arriveAndAwaitAdvance();

        executorService.shutdown();
        return new ArrayList<>(map.keySet());
    }

    class Task implements Runnable {

        String url;

        public Task(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                for (String S : parser.getUrls(url)) {
                    if (S.split("/")[2].equalsIgnoreCase(hostname) && map.putIfAbsent(S, true) == null) {
                        phaser.register();
                        executorService.submit(new Task(S));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                phaser.arriveAndDeregister();
            }
        }
    }
}



// solution 2 : using AtomicInteger
//public class WebCrawler {
//
//    public String hostname;
//    public HtmlParser parser;
//    ExecutorService executorService;
//    public Map<String, Boolean> map = new ConcurrentHashMap<>();
//    AtomicInteger totalUrls = new AtomicInteger(1);
//
//    public List<String> crawl(String startUrl, HtmlParser parser) {
//        hostname = startUrl.split("/")[2];
//        this.parser = parser;
//        executorService = Executors.newFixedThreadPool(5);
//        map.put(startUrl, true);
//        executorService.submit(new Task(startUrl));
//
//        while (totalUrls.get()>0){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        executorService.shutdown();
//        return new ArrayList<>(map.keySet());
//    }
//
//    class Task implements Runnable {
//
//        String url;
//
//        public Task(String url) {
//            this.url = url;
//        }
//
//        @Override
//        public void run() {
//            try {
//                for (String S : parser.getUrls(url)) {
//                    if (S.split("/")[2].equalsIgnoreCase(hostname) && map.putIfAbsent(S, true) == null) {
//                        totalUrls.addAndGet(1);
//                        executorService.submit(new Task(S));
//                    }
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } finally {
//                totalUrls.addAndGet(-1);
//            }
//        }
//    }
//}
