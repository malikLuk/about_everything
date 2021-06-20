/**
 * Копнем поглубже.
 * Обзор деплоя. Экстеншены деплоятся в .jar файлы в папки с соответствующим именем в директории extensions (sm_ext_folder.png). Нужно отметить
 * некоторые вещи:
 *  - мы можем деплоить несколько джарников в одной папке Экстеншена, например зависимости или другие библиотеки. Они все вместе будут загружены
 *      одним класс лоадером.
 *  - существует специальная директория __lib__ (тоже видно на sm_ext_folder.png), куда мы также можем поместить зависимости. Таким образом мы
 *      можем решить, какие зависимости будут расшарены между разными Экстеншенами, а какие будут локальными для каждого конкретного.
 * Оба подхода могут быть полезны в зависимости от того, что нам нужно:
 *  - либы, задеплоеные в папке конкретного Экстеншена будут загружены класс лоадером Экстеншена. Это значит, что мы можем менять эти конкретные
 *      либы и никак не заденем другие Экстеншены.
 *  - либы из общей директории __lib__ загружаются через другой класс лоадер. Соответственно, если мы что-то с ними сделаем, это затронет все
 *      Экстеншены, которые их юзают. Зато мы сэкономим ресурсы сервера.
 * Экстеншены автоматически загружают .properties файл. По дефолту смартфокс пытается найти файл с именем config.properties, но мы можем дать и
 * другое имя. Разные имена могут быть полезны, когда мы подключаем один Экстеншен к разным Зонам и для каждой требуются свои пропертис. На
 * sm_ext_config.png видна конфигурация Экстеншена в админке, где:
 *  - Name ссылается на имя папки Экстеншена
 *  - Type тип Экстеншена (у нас Java)
 *  - Main class - класс, унаследованный от ISFSExtension и реализующий метод init()
 *  - Use naming convention - не настройка Экстеншенов, просто включает/выключает фильтр, отображающий только те классы, имя которых
 *      заканчивается на Extension
 *  - Properties file - опциональный, указывает на имя конфигурационного файла. Если не указано, то это config.properties
 *  - Reload mode показывает, будут ли мониториться изменения Экстеншенов и будет ли автоматическая перезагрузка обновленных Экстеншенов.
 *      Бывает AUTO, MANUAL и NONE.
 * Смартфокс позволяет производить хот-редеплой. Когда он включен, сервер сам перезагружает измененные Экстеншены. Как настроить логи Экстеншенов
 * смотреть тут https://smartfoxserver.com/blog/custom-logging-for-sfs2x-extensions/.
 * Анатомия Экстеншена. На sm_ext_anatomy.png показано, как Экстеншен общается с сервером и внешним миром. Есть три способа взаимодействия:
 *  - Подключенные клиенты: отвечая на запросы или отправляя уведомления
 *  - Серверные ивенты: через реакции на определенные события, генерируемые сервером
 *  - Совместимость Экстеншенов: отправляя или получая сообщения от других Экстеншенов в системе
 * Подключенные клиенты. Выглядит это примерно так. Клиент:
 *
 *  SFSObject params = new SFSObject();
 *  params.putInt("number", 100);
 *  params.putBool("bool", true);
 *  params.putUtfString("str", "Hello");
 *
 *  // test - это "эндпоинт", params - параметры и всегда SFSObject
 *  smartfox.send(new ExtensionRequest("test", params));
 *
 * Со стороны сервера в Экстеншене должен быть обработчик на "эндпоинте" test:
 *
 *  @Override
 *  public void init() {
 *      addRequestHandler("test", TestHandler.class);
 *  }
 *
 * Нужно обратить внимание на то, что каждый обработчик представлен отдельным классом, так делать и рекомендуется. Но есть альтернативный способ,
 * передать объект IClientRequestHandler, но тогда мы сами будем управлять жизненным циклом обработчиков. Вот пример:
 *
 *  public class MyReqHandler extends BaseClientRequestHandler {
 *      @Override
 *      public void handleClientRequest(User sender, ISFSObject params) {
 *          int number = params.getInt("number");
 *          boolean bool = params.getBool("bool");
 *          string str = params.getUtfString("str);
 *
 *          // ... more logic here ...
 *      }
 *  }
 *
 * Серверные ивенты. Они могут быть полезны для того, чтобы реагировать на некоторые изменения состояния смартфокса, такие как отключение игроков,
 * создание Комнат и т д. Экстеншен может подписаться на несколько ивентов через addEventHandler:
 *
 *  public class MyExtension extends SFSExtension {
 *      @Override
 *      public void init() {
 *          addEventHandler(SFSEventType.USER_JOIN_ZONE, ZoneEventHandler.class);
 *      }
 *  }
 *  
 *  public class ZoneEventHandler extends BaseServerEventHandler {
 *      @Override
 *      public void handleServerEvent(ISFSEvent event) throws SFSException {
 *          User user = (User) event.getParameter(SFSEventParam.USER);
 *          trace("Welcome new user: " + user.getName());
 *      }
 *  }
 *
 * Каждый ивент отправляет отправляет Экстеншену разные параметры через объект ISFSEvent event. Документация здесь
 * http://docs2x.smartfoxserver.com/api-docs/javadoc/server/com/smartfoxserver/v2/core/SFSEventType.html
 * Совместимость Экстеншенов. Экстеншены могут общаться друг с другом через реализацию метода handleInternalMessage из родительского
 * класса. Вот он
 * Object handleInternalMessage(String cmdName, Object params)
 * Очень гибкий метод, так как мы можем передать любое имя команды и в ответ получить все что угодно. А вот пример реализации на уровне Зоны:
 *  @Override
 *  public Object handleInternalMessage(String cmdName, Object params) {
 *      if (cmdName.equals("test")) {
 *          Vec3D vec = (Vec3D) params;
 *
 *          // Do something with parameters
 *          return new Vec3D(x, y, z);
 *      }
 *  }
 * Вот как Комнатный Экстеншен может вызвать команду "test" Зонового Экстеншена:
 *  MyZoneExtension zoneExt = (MyZoneExtension) getParentZone().getExtension();
 *  Vec3D vec = zoneExt.handleInternalMessage("test", someVec3D);
 * Метод destroy() вызывается сервером каждый раз, когда Экстеншен выключается. Это происходит каждый раз, когда редеплоится Экстеншен уровня Зоны, или
 * когда удаляется Комната и Экстеншены, к которым она относится должны быть выключены. Вообще, метод destroy() необязательно реализовывать, все итак
 * должно пройти нормально, за некоторыми исключительными случяаями. Вот они: работа с файлами, базами данных или другими ресурсами со сложным жизненным
 * циклом - в этих случаях нужно явно указывать, как именно их разрушать. И самое главное, всегда в первую очередь в переопределенном методе destroy()
 * нужно вызывать super.destroy().
 * Серверное апи - http://docs2x.smartfoxserver.com/api-docs/javadoc/server/.
 * Смартфокс поддерживает несколько полезных аннотаций, указывающих на то, как именно классы-обработчики должны быть инстанциированы. По дефолту, когда
 * мы создаем класс-обработчик запросов и ивентов, его новый объект создается на каждый вызов. Но можно это изменить:
 *  - @Instantiation(NEW_INSTANCE) создает новый инстанс на каждый вызов
 *  - @Instantiation(SINGLE_INSTANCE) использует один инстанс на все вызовы
 * Экстеншен апи также поддерживает аннотацию @MultiHandler, которая добавляется к определению класса-обработчика. Она говорит о том, что класс будет
 * обрабатывать все запросы, начинающиеся с конкретного префикса. Подробнее здесь в секции Multi handlers and the request dot-syntax
 * (http://docs2x.smartfoxserver.com/ExtensionsJava/overview)
 * Фильтры Экстеншенов. Работают как фильтры сервлетов и служат для того же: выполняются по цепочке и могут использоваться для регистрации, фильтрации
 * и прочим видам обработки, перед тем, как попадут непосредственно в Экстеншен. Они не мешают коду в Экстеншене, их порядок исполнения может быть
 * изменен, они даже могут остановить поток исполнения, если необходимо. Пример - бан фильтр, где перед тем, как передать запрос обработчику, фильтр
 * проверяет имя игрока по черному списку. Простой пример:
 *
 *  public class CustomFilter extends SFSExtensionFilter {
 *     @Override
 *     public void init(SFSExtension ext) {
 *         super.init(ext);
 *         trace("Filter inited!");
 *     }
 *
 *     @Override
 *     public void destroy() {
 *         trace("Filter destroyed!");
 *     }
 *
 *      @Override
 *      public FilterAction handleClientRequest(String cmd, User sender, ISFSObject params) {
 *          // Если что-то пойдет не так, мы можем остановить выпонение здесь
 *          if (cmd.equals("BadRequest"))
 *              return FilterAction.HALT;
 *          else
 *              return FilterAction.CONTINUE;
 *      }
 *
 *      @Override
 *      public FilterAction handleServerEvent(ISFSEvent event) {
 *          return FilterAction.CONTINUE;
 *      }
 *  }
 * Добавляем фильтр к Экстеншену, можно динамически или статически:
 *  @Override
 *  public void init() {
 *
 *     // This is your Extension main class init()
 *
 *     // Add filters
 *      addFilter("customLoginFilter",new CustomLoginFilter());
 *      addFilter("pubMessageFilter",new PubMessageFilter());
 *      addFilter("privMessageFilter",new PrivMessageFilter());
 *  }
 * Когда Экстеншену придет новый запрос или ивент, он сначал пойдет по цепочке фильтров, которые мы добавили, в том порядке, в каком мы их
 * добавили: customLoginFilter -> pubMessageFilter -> privMessageFilter
 *
 * */

package smartfox.java_extensions;

public class JavaExtInDepth_01 {
}
