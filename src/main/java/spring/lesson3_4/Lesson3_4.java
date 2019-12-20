/**
 *
 * Инверсия управления.
 * В более-менее сложном Java-приложение рано или поздно появляются зависимости. Вот, например, ниже представлен класс
 * MusicPlayer у которого есть зависимость от класса ClassicalMusic. То есть класс MusicPlayer жестко заточен под класс
 * ClassicalMusic. Это "сильная" зависимость, по сути, наше плеер умеет проигрывать только классическую музыку. Избежать
 * этого можно с помощью интерфейсов или абстрактных классов. В этом случае у нас появляется так называемая "слабая
 * зависимость", когда мы можем сделать интерфейс Music и реализовать его, например, в классах, RockMusic и
 * ClassicalMusic, а в классе MusicPlayer тип зависимости определить как Music. Но и в том и в другом случае мы сами
 * вручную создаем объекты, тогда как Spring предлагает нам просто описать все объекты в конфигурационном файле и Spring
 * сам, согласно конфигурации, создаст нужные нам объекты(бины). Это достигается использованием принципа Инверсии
 * Управления (Inversion of Control). В этом случае зависимость можно внедрить как через конструктор, так и через
 * сеттер. То есть, в нашем случае, вместо того, чтобы создавать объект Music внутры класса MusicPlayer, мы ВНЕДРЯЕМ
 * через конструктор или сеттер уже созданный объект Music.
 * Что такое бины? Бин - это просто Java-объект. Когда объекты создаются с помощью Spring'а - они называются бинами.
 * Бины создаются из Java-классов.
 * Spring можно конфигурировать через:
 *      - XML-файла конфигурации (устаревшее)
 *      - Аннотации + XML (более современно)
 *      - Только аннотации (еще более современно)
 *
 * Внедрение зависимостей. Типичные шаги:
 *      - Создаем Java-класса (будущие бины)
 *      - Создаем и связываем бины с помощью Spring (XML-файл или Java-аннотации)
 *      - При использовании скофигурированных нами бинов, все они берутся из IoC-контейнера Spring
 * Как уже говорилось, внедрять зависимости можно через конструктор или сеттер, но можно и просто написать @Autowired
 * над зависимостью, что автоматизирует внедрение зависимости Spring'ом.
 *
 * */

package spring.lesson3_4;

public class Lesson3_4 {
}


class ClassicalMusic {}

class MusicPlayer {
    private ClassicalMusic classicalMusic;

    public void playMusic() {
        classicalMusic = new ClassicalMusic();
    }
}