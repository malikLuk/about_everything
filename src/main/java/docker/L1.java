/**
 *
 * Технологии контейнеризации помогают сделать приложение более безопасным, облегчают их развертывание и улучшают
 * возможности по их масштабированию. Docker - это платформа, которая предназначена для разработки, развертывания и
 * запуска приложений в контейнерах.
 * Виртуальные контейнеры можно сркавнить с обычными пластиковыми контейнерами. Как и обычный пластиковый контейнер,
 * контейнер докер обладает следующими характеристиками:
 *      - В нем можно что-то хранить. Нечто может находиться либо в контейнере, либо за его пределами.
 *      - Его можно переносить. Контейнер Docker можно использовать на локальном компьютере, на компьютере коллеги,
 *          на сервере поставщика облачных услуг (типа AWS). Это роднит контейнеры Docker с обычными контейнерами,
 *          в которых, например, можно перевозить вещи при переезде в новый дом.
 *      - В контейнер удобно что-то класть и удобно что-то из него вынимать. У обычного контейнера есть крышка, которую
 *          надо снять, чтобы положить туда вещи или наоборот, что-либо из него достать. У контейнеров Docker есть
 *          нечто подобное, представляющее их интерфейс, то есть - механизны, позволяющие им взаимодействовать с внешним
 *          миром. Например, у контейнера есть порты, которые можно открывать для того, чтобы к приложению, работающему
 *          в контейнере, можно было бы обращаться из браузера. Работать с контейнером можно и средствами командной
 *          строки.
 * Благодаря использованию Docker можно на одном и том же компьютере одновременно запускать множество контейнеров. И,
 * как и любые другие программы, контейнеры Docker можно запускать, останавливать, удалять. Можно исследовать их
 * содержимое и создавать их.
 * Предшественниками контейнеров Docker были виртуальные машины. Виртуальная машина, как и контейнер, изолирует от
 * внешней среды приложение и его зависимости. Однако, контейнеры Docker обладают преимуществами перед виртуальными
 * машинами. Они потребляют меньше ресурсов, их легко переносить, они быстрее запускаются и приходят в работоспособное
 * состояние.
 * Образ Docker-контейнера можно сравнить с чертежами, с формочками для печенья. Образы - это неизменные шаблоны,
 * которые используются для создания одинаковых контейнеров. В образе Docker-контейнера содержится образ базовой ОС, код
 * приложения, библиотеки и прочие зависимости. Все это скомпановано в виде единой сущности, на основе которой можно
 * создать контейнер.
 * Файл dockerfile содержит набор инструкций, следуюя которым Docker будет соибрать образ контейнера. Этот файл содержит
 * описание базового образа. Среди популярных официальных образов можно отметить python, ubuntu, alpine. В образ
 * контейнера, поверх базового образа, можно добавлять дополнительные слои. Делается это в соответствии с инструкциями
 * из dockerfile.
 * Для того, чтобы запустить контейнер, нам нужен, во-первых, образ контейнера, во-вторых - среда, в которой установлен
 * Docker, способная понять команду docker run image_name. Эта команда создает контейнер из образа и запускает его.
 *
 * */

package docker;

public class L1 {
}
