package multithreading;

/**
 * Created by Lukmanov.MN on 10.12.2019.
 */
public class TestIncrement {

    volatile Integer count = 0;

    public static void main(String[] args) throws InterruptedException {
        TestIncrement ti = new TestIncrement();
        new Thread(() -> {
            try {
                ti.incCount(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                ti.incCount(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            System.out.println(ti.count);
            Thread.sleep(1000);
        }
    }

    synchronized void incCount(int add) throws InterruptedException {
        Thread.sleep(5000);
        count += add;
        Thread.sleep(5000);
    }

}
