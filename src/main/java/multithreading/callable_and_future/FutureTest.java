package multithreading.callable_and_future;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Lukmanov.MN on 01.10.2018.
 */
public class FutureTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base dir: ");
        String directory = in.nextLine();
        System.out.println("Enter keyword (e.g. volatile): ");
        String keyword = in.nextLine();

        /**
         * MatchCounter реализует Callable - это задача.
         * */
        MatchCounter counter = new MatchCounter(new File(directory), keyword);
        FutureTask<Integer> task = new FutureTask<Integer>(counter);
        Thread t = new Thread(task);
        t.start();
        try {
            /**
             * Здесь идет блокировка, при вызове task.get()
             * то есть ждем, пока task не выполнится
             * */
            System.out.println(task.get() + " matching files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
