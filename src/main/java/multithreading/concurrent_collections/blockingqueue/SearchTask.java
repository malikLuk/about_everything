package multithreading.concurrent_collections.blockingqueue;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * Осуществляет поиск заданного ключевого слова в файлах
 * */
public class SearchTask implements Runnable {

    private BlockingQueue<File> queue;
    private String keyword;

    public SearchTask(BlockingQueue<File> queue, String keyword) {
        this.queue = queue;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        try {
            boolean done = false;
            while (!done) {
                File file = queue.take();
                if (file == FileEnumerationTask.DUMMY) {
                    queue.put(file);
                    done = true;
                } else {
                    search(file);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Осуществляет поиск заданного слова
     * и выводит в консоль адрес файла
     * */
    public void search(File file) throws IOException {
        try (Scanner in = new Scanner(file)) {
            int lineNumber = 0;
            while (in.hasNextLine()) {
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword)) {
                    System.out.println(file.getPath() + " : " + lineNumber + " : " + line + " : " + keyword);
                }
            }
        }
    }

}
