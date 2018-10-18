package multithreading.executors;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Lukmanov.MN on 10.10.2018.
 * Тоже считаем входения ключевого слова
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base dir: ");
        String directory = in.nextLine();
        System.out.println("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();

//        ExecutorService pool = java.util.concurrent.Executors.newCachedThreadPool();
        ExecutorService pool = java.util.concurrent.Executors.newFixedThreadPool(50);

        MatchCounter counter = new MatchCounter(new File(directory), keyword, pool);
        Future<Integer> result = pool.submit(counter);

        try {
            System.out.println(result.get() + " matching files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        int largestPoolSize = ((ThreadPoolExecutor) pool).getLargestPoolSize();
        System.out.println("largest pool size is " + largestPoolSize);
    }

}
