package multithreading.bank;

/**
 * Created by Lukmanov.MN on 19.09.2018.
 */
public class BankTest {

    static final int NACCOUNTS = 100;
    static final double INITIAL_BALANCE = 1000;

    public static void main(String[] args) {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);

        for (int i = 0; i < NACCOUNTS; i++) {
            TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }

}
