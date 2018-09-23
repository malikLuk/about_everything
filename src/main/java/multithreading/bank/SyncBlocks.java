/**
 *
 *                                              Синхронизированные блоки.
 * Помимо синхронизированных методов сущетсвуют и синхронизированные блоки. Когда поток входит в блок, как в приведенной
 * ниже форме, он захватывает блокировку объекта obj.
 * synchronized(obj) {
 *     // some code
 * }
 * Иногда встречаются и такие конструкции
 * public class Bank {
 *     private double[] accounts;
 *     private Object lock = new Object();
 *     ...
 *     public void transfer(arguments) {
 *         synchronized(lock) {
 *             accounts[from] -= amount;
 *             accounts[to] += amount;
 *         }
 *     }
 * }
 * Здесь объект lock типа Object() создается для использования его встроенной блокировки, и, грубо говоря, имитирует
 * функционал ReentrantLock. Это называется Клиентской блокировкой - прием крайне ненадежный и не рекомендуется к использованию.
 *
 *
 * */

package multithreading.bank;

public class SyncBlocks {
}
