/**
 *                                                  https://habr.com/post/216431/
 *                                                              Java 8.
 * Методы интерфейсов по умолчанию.
 * Java позволяет добавлять неабстрактные реализации методов в интерфейс, используя ключевое слово default. Эта фича
 * известна также, как методы расширения.
 *
 * interface Formula {
 *  double calculate(int a);
 *
 *  default double sqrt(int a) {
 *      return Math.sqrt(a);
 *  }
 * }
 * Классы, реализующие интерфейс Formula должны переопределить только абстрактный метод calculate(). default-метод sqrt()
 * будет доступен без переопределения.
 *
 * Лямбда-выражения.
 * Начнем с простого примера: сортировка массива строк в предыдущих версиях языка
 *
 * List<String> names = Arrays.asList("Malik", "Julia", "Tigra");
 * Collections.sort(names, new Comparator<String>() {
 *     @Override
 *     public inc compare(String a, String b) {
 *         return b.compareTo(a);
 *     }
 * });
 * То есть, использовали анонимный компаратор. Java 8 же предоставляет нам возможность обойтись без анонимных объектов
 * с помощью лямбда выражения
 * Collections.sort(names, (String a, String b) -> {
 *     return b.compareTo(a);
 * });
 * Можно сделать еще короче
 * Collections.sort(names, (a, b) -> b.compareTo(a));
 * Так как компилятору известны типы параметров - их можно опустить.
 *
 * Функциональные интерфейсы.
 * Лямбда-выражения соотносятся с системой типов Java следующим образом. Каждой лямбде соответствует функциональный
 * интерфейс. Функциональный интерфейс - это интерфейс, содержащий один и только один абстрактный метод. Используется
 * аннотация @FunctionalInterface. Каждое лямбда выражение будет сопоставлено объявленному методу.
 *
 * @FunctionalInterface
 * interface Converter<F, T> {
 *     T convert(F from);
 * }
 *
 * То есть лямбда-выражение просто переопределяет единственный метод функционального интерфейса.
 *
 * Converter<String, Integer> converter = (from) -> {
 *     return Integer.valueOf(from);
 * }
 * Integer converted = converter.convert("123");
 * System.out.println(converted);   // 123
 *
 * Ссылки на методы и конструкторы.
 * Предыдущий пример можно упростить, если использовать статические ссылки на методы:
 *
 * Converter<String, Integer> converter = Integer::valueOf;
 * Integer converted = converter.convert("123");
 * sout(converted); //123
 *
 * То есть Java 8 позволяет передавать ссылки на методы или конструкторы. Для этого используется ::. Этот пример
 * иллюстрирует ссылку на static метод valueOf, однако, мы также можем сослаться на экземплярный метод.
 *
 * class Something {
 *     String startsWith(String s) {
 *         return String.valueOf(s.charAt(0));
 *     }
 * }
 *
 * Something something = new Something();
 * Converter<String, String> converter = something::startsWith;
 * String converted = converter.convert("Java");
 * sout(converted); // J
 *
 * Можно также передавать ссылки на конструкторы. Например
 *
 * class Person {
 *     String fName;
 *     String lName;
 *
 *     Person() {}
 *
 *     Person(String, fn, String ln) {
 *         this.fName = fn;
 *         this.lName = ln;
 *     }
 * }
 *
 * Затем определим интерфейс фабрики:
 *
 * interface PresonFactory<P extends Person> {
 *     P create (String fName, String lName);
 * }
 *
 * Теперь вместо реализации интерфейса мы соединяем все вместе при помощи ссылки на конструктор:
 *
 * PersonFactory<Person> personFactory = Person::new;
 * Person person = personFactory.create("Malik", "Lukmanov");
 *
 * То есть мы переопределяем метод create() интерфейса фабрики конструктором класса Person. Компилятор автоматически выберет
 * подходящий конструктор, сигнатура которого совпадает сс сигнатурой PersonFactory.create()
 *
 * Видимость лямбд в основном такая же, как у анонимных объектов. Также, внутри лямбда-выражений запрещено обращаться к
 * методам по умолчанию(default).
 *
 * Встроенные функциональные интерфейсы. К ним относятся Comparator, Runnable и много других.
 *
 */


package habr;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Java8 {

    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return a*100;
            }
        };

        System.out.println(formula.calculate(10));
        System.out.println(formula.sqrt(16));

        Arrays.asList("Malik", "Julia", "Tigra").stream().forEach(i -> {
            System.out.println(i);
        });

        System.out.println("========================================================================================");

        List<String> names = Arrays.asList("Malik", "Julia", "Tigra");
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });
        names.stream().forEach(elem -> {
            System.out.println(elem);
        });

        System.out.println("========================================================================================");

        Converter<String, Integer> converter = from -> Integer.valueOf(from);
        System.out.println(converter.convert("11"));

        System.out.println("========================================================================================");

        Something something = new Something();
        Converter<String, String> conv = something::startsWith; // то есть для переопределения метода convert функционального
        String converted1 = conv.convert("Java");          // интерфейса Converter мы просто сослались на метод startsWith
        System.out.println(converted1);                         // объекта something. И теперь при вызове convert - происходит
                                                                // вызов метода startsWith

    }

}

interface Formula {
    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}

class Something {
    String startsWith(String s) {
        return String.valueOf(s.charAt(0));
    }
}