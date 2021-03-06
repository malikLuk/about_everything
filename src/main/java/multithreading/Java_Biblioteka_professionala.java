/**
 *
 *                                              Многопоточность. Глава 14.
 * Как известно, запустить новый потомк можно создав Runnable, переопределив в нем метод run() и передать это дело в
 * new Thread(), вызвав затем, у него start(). НИ В КОЕМ СЛУЧАЕ НЕЛЬЗЯ стартовать новый поток прямо методом run(), так
 * как задача, вызванная таким образом, запустится в том же потоке. Только start() создает новый поток исполнения. Кроме
 * того, метод start() немедленно возвразщает управление, а созданный им поток выполняется параллельно.
 * Особенности прерывания потока:
 *    void interrupt() посылает потоку запрос на прерывание. Признак состояния прерывания потока, то есть, то, что вернет
 *      isInterrupted() устанавливается true.
 *    static boolean interrupted() проверяет, был ли прерван текущий поток. При вызове этого метода, состояние прерывания
 *      потока очищается - то есть.
 *    boolean isInterrupted() - проверяет, был ли прерван поток. Не изменяет состояния прерывания потока.
 * Состояния потоков - новый, исполняемый, блокированный, ожидающий, временно ожидающий, завершенный. Узнать состояние
 * можно, вызвав getState().
 * В Java каждый поток имеет свой приоритет. По умолчанию поток наследует приоритет того потока, который его создал.
 * Назначить приоритет - setPriority(). Но тут все зависит от платформы. В Windows - 7 уровней приортетов, а в Linux -
 * JVM все приоритеты игнорирует, то есть, все они имеют одинаковый приоритет.
 * Кстати, если ввести javap -c -v Classname то мы увидим байт-код класса, и поймем, что там операция инкремента - это
 * три операции.
 * Блокировки.
 * В JDK 1.5 появился класс ReentrantLock. В общем случае, работа с ним выглядит так:
 *      myLock.lock
 *      try {
 *          //code
 *      } finally {
 *          myLock.unlock();
 *      }
 * такая конструкция гарантирует, что только один поток в единицу времени сможет войти в критический раздел кода. Как
 * только один поток заблокирует объект блокировки (myLock.lock()), никакой другой поток не сможет обойти вызов метода
 * lock(). myLock.unlock() непременно(!!!) должен быть в finally. Блокировка называется Реентрабельной, если поток
 * может повторно захватить блокировку, которой уже владеет. Обычно требуется защищать блокировкой строки кода, обновляющие
 * или проверяющие разделяемый объект. Напрмер, запись нового значение в массив интов и подсчет суммы всех интов в массиве.
 * У ReentrantLock есть два контруктора:
 *  ReentrantLock() - создает простой ReentrantLock
 *  ReentrantLock(boolean fair) - создает ReentrantLock с правилом равноправия. Если true - то блокировка будет отдавать
 *      предпочтение потоку, ожидающему дольше всех. Обычно, не применяется, так как может сказаться на производительности.
 * Объекты условий.
 * Нередко бывает так, что поток входит в критический раздел кода только для того, чтобы обнаружить, что он не может
 * продолжить свое выполнение до тех пор, пока не будет соблюдено определенное условие. Для таких случаев у нас предусмотрен
 * Объект Условия, которые также называются Условными Переменными.
 * Страница 777 - переписать весь код для банка и Condition
 *
 * */

package multithreading;

public class Java_Biblioteka_professionala {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(0);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        });
        t.setPriority(5);
        t.start();
    }

}
