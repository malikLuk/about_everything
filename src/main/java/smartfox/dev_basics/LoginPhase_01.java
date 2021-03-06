/**
 *
 * Фаза логина. После установления соединения и перед тем, как клиент начнет взаимодействовать с серверным апи и другими пользователями, необходимо
 * залогиниться в Зоне. Чтобы увидеть доступные Зоны и создать новые, можно использовать админку в разделе Zone Configurator (sm_zone_conf.png). Там
 * есть очень вжаная настройка Use custom login (по дефолту выключено), которая находится ниже Zone name. Эта настройка определяет, какой контроллер
 * получит и будет обрабатывать запрос логина.
 *  - customLogin == false. Обрабатывать логин-запрос будет Системный контроллер (System Controller). В этом случае любой пользователь будет принят,
 *      кроме тех, что уже залогинены в Зоне с таким же именем. Пароль не требуется. Если передать пустую строку в качестве имени, то сервер автоматом
 *      назначит что-то типа Guest#42.
 *  - customLogin == true. Логин-запрос будет обрабатывать Контроллер Расширений (Extension Controller), логикой, которую серверный разработчик прописал
 *      для авторизации и других операций.
 * Фаза логина всегда инициируется с клиента, через отправку LoginRequest'а серверу. Вот его параметры:
 *  - user name (op)
 *  - password (op)
 *  - Zone name (m) название Зоны. Она должна существовать.
 *  - extra parameters (op). Обычно используется для кастомного логина.
 * Со стороны клиент логин прост: нужно просто зарегистрировать объект класса SmartFox, чтобы получать события SFSEvent.LOGIN и SFSEvent.LOGIN_ERROR.
 * Во время фазы логина сервер выполняет ряд проверок, которые могут заблокировать процесс и вызвать событие SFSEvent.LOGIN_ERROR. Вот что может пойти
 * не так:
 *  - Missing Zone: запращиваемая Зона не существует
 *  - Zone full: запрашиваемая Зона переполнена игроками (настраивается в конфигураторе зон в админке)
 *  - Server full: сам смартфокс сервер переполнен
 *  - Duplicate names: дублирование имём игроков. Имена регистрозависимы. Malik и malik - разные имена, тут все будет ок
 *  - Bad Words in username: можно сконфигурировать мат-фильтр
 *  - Banned user name: пользователь забанен
 * Список Комнат. После логина, сервер добавляет пользователя в Зону. За кулисами смартфокс производит еще некоторые операции:
 *  - Автоподписка на дефолтную Группу Комнат. В конфигураторе Зон мы можем объявить набор публичных Групп Комнат. По умолчанию
 *      такая группа называется "default" и мы можем добавить еще, если надо. Группа Комнат - это просто строковый идшник. После
 *      логина клиент будет подписан на все группы, указанные в настройках Default Room Groups в Зоне.
 *  - Передача клиенту исходного (initial) списка Комнат. Это значит, что после логина клиент получает список Комнат, которые
 *      относятся к Группам Комнат по умолчанию, на которые он подписался в предыдущем шаге. Эта операция выполняется один раз,
 *      при логине.
 * На sm_zone_example.png показано, что Зона состоит из Групп Комнат, таких как Европа, Америка и Азия, каждая из которых соджержит
 * некоторое количество Комнат. Если мы предположим, что дефолтные Группы в Зоне - это "Европа, Америка" (напомню, Группа - это строковый
 * идентификатор, а не список строк), то клиентский список Комнат будет заполнен Комнатами из этих Групп.
 * Кастомный логин. Пишется с помощью Экстеншенов, который обрабатывает пользовательские креденшелы и проверяет их. Шаги:
 *  - Установка соединения с БД (https://smartfoxserver.com/blog/how-to-setup-a-connection-to-an-external-database/).
 *  - Подготовка простого Экстеншена, который будет выполнять валидацию юзернейма и пароля (
 *      http://docs2x.smartfoxserver.com/ExtensionsJava/overview и https://smartfoxserver.com/blog/how-to-create-an-extension-based-custom-login/)
 *  - Включение настройки Use custom login в конфигураторе.
 *
 * */

package smartfox.dev_basics;

public class LoginPhase_01 {
}
