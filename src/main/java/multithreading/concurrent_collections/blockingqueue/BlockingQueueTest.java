package multithreading.concurrent_collections.blockingqueue;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base dir: ");
        String directory = in.nextLine();
        System.out.println("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();

        final int FILE_QUEUE_SIZE = 10;
        final int SEARCH_THREADS = 100;

        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

//        FileEnumerationTask enumerator = new FileEnumerationTask(queue, new File(directory));
        new Thread(new FileEnumerationTask(queue, new File(directory))).start();
        for (int i = 0; i < SEARCH_THREADS; i++) {
            new Thread(new SearchTask(queue, keyword)).start();
        }
    }

}
