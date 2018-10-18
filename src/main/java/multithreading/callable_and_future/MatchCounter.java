package multithreading.callable_and_future;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Подсчитывает файлы, содержащие заданное
 * ключевое слово в каталогах и подкаталогах
 */
public class MatchCounter implements Callable<Integer> {

    private File directory;
    private String keyword;
    private int count;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }

    @Override
    public Integer call() throws Exception {
        count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            /**
             * Стартуем потоки поиска с задачей
             * в директории и ее поддиректориях,
             * добавляя каждую новую задачу в список
             * Future results
             * */
            for (File file : files) {
                if (file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask<Integer> task = new FutureTask<Integer>(counter);
                    results.add(task);
                    Thread t = new Thread(task);
                    t.start();
                } else {
                    if (search(file)) {
                        count++;
                    }
                }

                /**
                 * А здесь, проходимся по всему нашему списку Future
                 * и забираем из каждого результат.
                 * */
                for (Future<Integer> result : results) {
                    try {
                        count += result.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean search(File file) {
        try (Scanner in = new Scanner(file)) {
            boolean found = false;
            while (!found && in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    found = true;
                }
            }
            return found;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

}
