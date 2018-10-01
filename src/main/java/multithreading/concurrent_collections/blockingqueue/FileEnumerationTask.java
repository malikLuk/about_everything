package multithreading.concurrent_collections.blockingqueue;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Перечвисляет все файлы в каталоги и его подкаталогах
 * */
public class FileEnumerationTask implements Runnable {

    /**
     * ДУММИ - это и есть тот индикатор, по которому мы смотрим, пора ли заканчивать выполнение
     * как только поток, который осуществляет поиск в файлах, наткнется на ДУММИ - он прекратит
     * свое выполнение
     * */
    public static File DUMMY = new File("");
    private BlockingQueue<File> queue;
    private File startingDirectory;

    public FileEnumerationTask(BlockingQueue<File> queue, File startingDirectory) {
        this.queue = queue;
        this.startingDirectory = startingDirectory;
    }

    @Override
    public void run() {
        try {
            enumerate(startingDirectory);
            queue.put(DUMMY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Рекурсивно перечисляет все файлы в
     * данном каталоге и его подкаталогах
     * */
    public void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                enumerate(file);
            } else {
                queue.put(file);
            }
        }
    }

}
