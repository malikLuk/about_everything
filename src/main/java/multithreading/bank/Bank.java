/**
 *
 *                                                      Имитация банка.
 * Код имитирует банк. Определен класс Bank с методом transfer(). Этот метод перемещает некоторую сумму денег с одного счета
 * на другой. Во время выполнения неизвестно, какая именно сумма находится на конкретном банковском счете в произвольный
 * момент времени. В то же время известно, что общая сумма денег по всем счетам должна оставаться неизменной, так как
 * деньги переводятся с одного счета на другой только внутри банка. В конце каждой транзакции метод transfer() вычисляет
 * итоговую сумму на счетах и выводит ее. При такой реализации, общая сумма будет уменьшаться или увеличиваться. Почему
 * так происходит. Допустим, два потока пытаются одновременно выполнить операцию
 *  accounts[to] += amount;
 * Дело в том, что такие операции не являются атомарными. Приведенная выше операция может быть выполнена поэтапно следующим
 * образом.
 * 1. Загрузить значение из элемента массива accounts[to] в регистр
 * 2. Добавить к нему значение amount.
 * 3. Переместить результат обратно в accounts[to].
 * Таким образом если два потока начали одновременно работать с одним и тем же элементом, но какой-то из них по некоторым
 * причинам затормозил, то опаздывающий поток никак не узнает об изменениях, внесенных первым потоком и будет оперировать
 * устаревшим значением и в итоге уничтожит изменения, внесенные другим потоком. Суть ошибки в том, что выполнение метода
 * transfer() может быть прервано на полпути к завершению. Если удасться гарантировать нормальное завершение этого метода
 * до того, как его поток утратит управление, то все будет ок. Для этого в Java имеются два механизма для защиты блока
 * кода от параллельного доступа. Первый - ключевое слово synchronized, а второй - в Java 5 появился класс ReentrantLock.
 * Слово synchronized автоматичеки обеспечивает блокировку, как и связанное с ней "условие" (об этом позже), которое удобно
 * указывать в большинстве случаев, когда требуется явная блокировка. ReentrantLock слегка описан в классе
 * Java_Biblioteka_professionala. Ниже рядом с //modify описаны изменения банка с помощью ReentrantLock. В новом классе
 * Bank описано Условие(Condition).
 *
 * */

package multithreading.bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientCondition;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
            bankLock = new ReentrantLock();
            sufficientCondition = bankLock.newCondition();
        }
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            while (accounts[from] < amount) {
                sufficientCondition.await();
                System.out.println(Thread.currentThread());
            }
            accounts[from] -= amount;
            System.out.printf (" %10.2f from %d to $d", amount, from, to);
            accounts[to] += amount;
            System.out.printf (" Total Balance: %10.2f%n", getTotalBalance ());
            sufficientCondition.signalAll();
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0;
            for (double d : accounts) {
                sum += d;
            }
            return sum;
        } finally {
            bankLock.unlock();
        }
    }

    public int size() {
        return accounts.length;
    }
}

/**
 * Старая версия
 * public class Bank {

    private Lock bankLock = new ReentrantLock(); //modify

    final double[] accounts;

    public Bank(int n, double initialBalance) {
        this.accounts = new double[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
    }

    public void transfer(int from, int to, double amount) {
        bankLock.lock();    //modify
        try {               //modify
            System.out.println(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            double tb = getTotalBalance();
            if (tb != 100000) {
                System.out.printf(" Total Balance: %10.2f%n", tb);
            }
        } finally {             //modify
            bankLock.unlock();  //modify
        }
    }

    private double getTotalBalance() {
        double sum = 0;
        for (double d : accounts) {
            sum += d;
        }
        return sum;
    }

    public int size() {
        return accounts.length;
    }

}*/
