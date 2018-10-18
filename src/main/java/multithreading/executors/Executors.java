/**
 *
 *                                                          Исполнители.
 * Создание нового потока - это дорогостоящая операция с точки зрения потребляемых ресурсов, поскольку она включает в
 * себя взаимодействие с ОС. Если в программе создается большое количество кратковременных потоков, то имеет смысле
 * использовать пул потоков. В пуле потоков содержится целый ряд простаивающих потокв, готовых к запуску. Так, исполняемый
 * поток типа Runnable размещается в пуле, и один из остальных потоков запускает его метод run(). Когда метод run()
 * завершается, его поток не уничтожается, но остается в пуле, готовый обслужить новый запрос.
 * Другая причина для использования пула потоков - необходимость ограничить количество параллельно выполняющихся потоков.
 * В состав класса Executors входит несколько статичных фабричных методов для построения пулов потоков. Их перечень:
 *      newCachedThreadPool() - новые потоки создаются по мере необходимости,
 *                              а проставивающие потоки сохраняются в течение
 *                              60 секунд
 *      newFixedThreadPool() - пул содержит фиксированное количество потоков.
 *      newSingleThreadExecutor() - пул с единственным потоком, выполняющим
 *                                  переданные ему задачи поочередно.
 *      newScheduledThreadPool() - пул с фиксированным количеством потоков,
 *                                 для запуска потоков по расписнаию. Заменяет
 *                                 java.util.Timer
 *      newSingleThreadScheduledExecutor() - пул с едиснвтенным потоком, для запуска
 *                                           по расписанию.
 * Пулы потоков.
 * Передать задачу типа Runnable и Callable объекту типа ExecutorService можно одним из следующих вариантов метода submit():
 *      Future<?> submit(Runnable task);
 *      Future<T> submit(Runnable task, T result);
 *      Future<T> submit(Callable<T> task);
 * Пул запускает переданную задачу при первом удобном случае. В результате вызова метода submit() возвращается объект
 * типа Future, который можно использовать для опроса состояния задачи. По завершении работы с пулом - нужно вызвать
 * метод shutdown(). Этот метод инициирует последовательность закрытия пула, после чего новые задачи не принимаются на
 * выполнение. По завершении всех задач потоки в пуле уничтожаются.
 *
 * */

package multithreading.executors;

import java.util.concurrent.ExecutorService;

public class Executors {

    ExecutorService service = java.util.concurrent.Executors.newFixedThreadPool(10);

}
