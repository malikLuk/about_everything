package multithreading.bank;

public class TransferRunnable implements Runnable {

    Bank bank;
    int fromAccount;
    double maxAmount;
    int DELAY = 10;

    public TransferRunnable(Bank bank, int fromAccount, double maxAmount) {
        this.bank = bank;
        this.fromAccount = fromAccount;
        this.maxAmount = maxAmount;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int toAccount = (int) (bank.size() * Math.random());
                double amount = maxAmount * Math.random();
                bank.transfer(fromAccount, toAccount, amount);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {

        }
    }
}
