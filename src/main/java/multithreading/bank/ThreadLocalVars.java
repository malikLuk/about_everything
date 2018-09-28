/**
 *
 *                                                   Локальные переменные потоков.
 * В предыдущих разделах обсуждались риски совместного использования переменных, разделяемых между потоками. Иногда можно
 * избежать такого разделения общих ресурсов, предоставляя каждому потоку свой экземпляр с помощью вспомогательного класса
 * ThreadLocal. Например, класс SimpleDateFormat не является потокобезопасным. Допустим, имеется следующая статическая
 * переменная:
 *  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 * И есть операция, наподобие такой:
 *  String dateStamp = dateFormat.format(new Date());
 * Если в двух потоках выполняется такая операция, то ее результат может превратиться в мусор, поскольку внутренние
 * структуры данных класса SimpleDateFormat, используемые в переменной dateFormat могут быть повреждены в параллельно
 * выполняющемся потоке. Выход - посторить отдельный экземпляр на каждый поток:
 *  public static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
 *      protected SimpleDateFormat initialValue() {
 *          return new SimpleDateFormat("yyyy-MM-dd");
 *      }
 *  }
 * Для доступа к конкретному средству форматирования делается следующий вызов:
 *  String dateStamp = dateFormat.get().format(new Date());
 * При первом вызове метода get() вызовется также метод initValue(). А по его завершении - метод get() возвращает экземпляр,
 * принадлежащий текущему потоку. Аналогичные трудности вызывает генерирование случайных чисел в нескольких потоках. Для
 * этой цели служит java.util.Random, который хоть и является потокобезопасным, но и он может оказаться недостаточно
 * эффективным, если нескольким потокам приходится ожидать доступа к единственному разделяемому между ними генератору
 * случайных чисел. Здесь можно использовать ThreadLocal, как в примере выше, а можно использовать специальный класс
 * ThreadLocalRandom из Java 7. Достаточно вызвать ThreadLocalRandom.current(), и в результате возвратиться экземпляр
 * Random, однозначный для текущего потока.
 *  int random = ThreadLocalRandom.current().nextInt(upperBound);
 *
 * */

package multithreading.bank;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalVars {

    Random random = ThreadLocalRandom.current();

}
