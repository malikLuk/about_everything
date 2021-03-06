/**
 *
 *                                                  Блокирующие очереди.
 * Многие затруднения, связанные с потоками, можно изящно и безопасно сформулировать, применив одну или больше очередей.
 * В частности, поставляющий поток вводит элементы в очередь, а потребляющие потоки извлекают их. Таким образом, очередь
 * позволяет безопасно передавать данные из одного потока в другой. Обратимся снова к примеру банка. Вместо того, чтобы
 * обращаться к объекту банка напрямую, потоки, выполняющие денежные переводы, вводят объекты команд на денежные переводы
 * в очередь. А другой поток удаляет эти объекты из очереди и сам выполняет денежные переводы. И только этот поток имеет
 * доступ к содержимому объекта банка. В итоге не требуется никакой синхронизации, так как разработчики таких очередей
 * уже позаботились об этом.
 * Блокирующая очередь вынуждает поток блокироваться при попытке ввести элемент в переполненную очередь или при попытке
 * удалить элемент из пустой очереди. Блокирующие очереди - удобный инструмент для координации работы множества потоков.
 * Такой подход автоматически уравновешивает нагрузку. Если первая группа потоков работает медленнее, чем вторая, то вторая
 * блокируется, ожидая результатов. И наоборот.
 * Методы блокирующих очередей разделяют на три категории, в зависимости от выполняемых действий, когда очередь заполнена
 * или пуста. Для применения блокирующей очереди в качестве инструментального средства управления потоками, понадобятся
 * методы put() и take(). Методы add(), remove(), element() генерируют исключение при попытке ввести элемент в заполненную
 * очередь или получить элемент из головы пустой очереди. Методы offer(), poll() и peek() возвращают признак сбоя вместо
 * исключения. Примечание: методы poll() и peek() возвращают значения null для обозначения неудачного исхода, поэтому
 * недопустимо вводить в очереди null'ы. Метода put() блокирует, когда очередь заполнена, а метод take(), когда очередь
 * пуста. В java.util.concurrent.* представлено несколько вариантов блокирующих очередей. По умолчанию, у очереди типа
 * LinkedBlockingQueue отсуствует верхняя граница емкости, но может быть указана при необходимости. LinkedBlockingDeque -
 * это вариант блокирующей двусторонней очереди. А блокирующая очередь типа ArrayBlockingQueue коструируется с заданной
 * емкостью и признаком равноправия блокировки в качестве необязательного параметра. Если этот параметр указан, то
 * предпочтение отдается элементам, дольше всего находящимся в ожидании. От этого, как правило, страдает производительность.
 * PriorityBlockingQueue - это блокирующая очередь с приоритетами, а не просто действующая по принципу "первый пришел -
 * первым обслужен". Элементы удаляются из такой очереди по приоритетам. Эта очередь имеет неограниченную емкость, но при
 * попытке извлечь элемент из пустой очереди происходит блокирование.
 * В программе ниже демонстрируется применение блокирующей очереди для управления многими потоками. Эта программа осуществляет
 * поиск среди всех файлов в каталоге и его подкаталогах, выводя строки кода, содержащие заданное ключевое слово. В
 * поставляющем потоке перечисляются все файлы во всех подкаталогах, а затем они размещаются в блокирующей очереди. Эта
 * операция выполняется быстро, и поэтому очередь быстро заполняется всеми файлами из ФС, если не установлена верхняя
 * граница ее емкости. Кроме того, запускается огромное количество поисковых потоков, в каждом из которых файл извлекается
 * из очереди, открывается, а затем, из него выводятся все строки, содержащие ключевое слово, после чего из очереди
 * извлекается следующий файл.
 * Для того, чтобы остановить программу ничего особенного не требуется, просто применин особый прием: из перечисляющего
 * потока в очередь вводится фиктивный объект, чтобы уведомить о завершении потока (это похоже на муляж чемодана с надписью
 * "последний чемодан" на ленте выдачи багажа). Когда поисковой поток получает такой объект, он возвращает его обратно и
 * завершается.
 * Следует отметить, что в данном примере программы не требуется никакой явной синхронизации, так как в качестве
 * синхронизирующего механизма используется сама структура очереди. Реализовано это дело в BlockingQueueTest.
 *
 * ArrayBlockingQueue(int capacity)
 * ArrayBlockingQueue(int capacity, boolean fair)
 * Конструируют блокирующую очередь заданной емкости с установленным правилом равноправия блокировки. Очередь основана
 * на массиве.
 *
 * LinkedBlockingQueue()
 * LinkedBlockingDeque()
 * Конструируют неограниченную блокирующую одностороннюю или двустороннюю очередь. Очередь основана на связном списке.
 *
 * LinkedBlockingQueue(int capacity)
 * LinkedBlockingDeque(int capacity)
 * Тоже самое, только задана емкость (максимальное число элементов).
 *
 * DelayQueue()
 * Коструирует неограниченную блокирующую очередь элементов типа Delayed. Из очереди могут быть удалены только элементы,
 * время задержки которых истекло.
 *
 * PriorityBlockingQueue()
 * PriorityBlockingQueue(int initialCapacity)
 * PriorityBlockingQueue(int initialCapacity, Comparator<? super E> comparator)
 * Конструирует очередь с приоритетами, основанную на Куче(почитать, сука), где
 *          initialCapacity - исходная емкость, по умолчанию 11
 *          comparator - компаратор для сравнения элементов и назначения им "приоритетов"
 *
 * */

package multithreading.concurrent_collections.blockingqueue;

public class BlockingQueue {
}
