/**
 *
 * Интерфейс Callable подобен интерфейсу Runnable, за тем исключением, что он возвращает значение. Callable относится к
 * параметризированному типу и имеет едиснтвенный метод call():
 *  public interface Callable<V> {
 *      V call() throws Exception;
 *  }
 * Параметр типа (это V) обозначает тип возвращаемого значения. То есть Callable<Integer> после вызова call() вернет
 * значение типа Integer. А сохранение результатов асинхронного вычисления обеспечивает интерфейс Future. В частности,
 * вычисление можно начать, предоставив кому-нибудь другому объект типа Future, а затем просто забыть о нем. Владелец
 * объекта типа Future может получить результат, когда он будет готов. У интерфейса Future есть некоторые важные
 * методы:
 *  V get() throws ... - при вызове этого метода блокировка устанавливается до тех пор, пока не завершится вычисление
 *  V get(long timeout, TimeUnit unit) throws ... - генерирует TimeoutException, если время ожидание истекло до завршения
 *      вычислений.
 *  void isDone() - false, если вычисления еще не закончены.
 * Класс-оболочка FutureTask служит удобным механизмом для превращения интерфейса Callable одновременно в интерфейсы
 * Future и Runnable, реализуя их оба. Пример:
 *  Callable<Integer> task = new FutureTask<Integer>(myComputation);
 *  Thread t = new Thread(task);
 *  t.start();
 *  Integer result = task.get();
 *  В классе MatchCounter описывается программа подсчитывает количество файлов, соответствующих критерию поиска.
 *
 * */

package multithreading.callable_and_future;

public class CallableAndFuture {
}
