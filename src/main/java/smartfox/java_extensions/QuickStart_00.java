/**
 *
 * Смартфокс позволяет разработчикам расширять логику самого смартфокса с помощью Экстеншенов (sm_java_ext.png). В грубом приближении, Экстеншены
 * смартфокса для игры - это как PHP или JSP логика для вебсайтов. Экстеншены позволяют нашему коду запускаться на сервере, то есть, добавляют новую
 * логику в приложение.
 * В смартфоксе Экстеншены могут быть прикреплены к Зоне или Комнате, в зависимости от области видимости самого Экстеншена. У Комнатного Экстеншена
 * область видимости меньше, чем у Зонального. Пример, класс MyExtension. Каждый Экстеншен должен наследоваться от SFSExtension и реализовывать метод
 * init(), который вызывается при старте сервера. В нем можно инициализировать глобальные объекты или структуры данных, а также зарегистрировать
 * обработчики событий. Экстешен может также реализовывать метод destroy(), он может понадобится для закрытия соединения с дб, закрытия файлов или
 * для планировщика задач. Плюс ко всему, для обработки запросов серверные Экстеншены могут слушать некоторые серверные ивенты, такие как логин, логаут,
 * присоединение к Комнате и т д. Создать слушателя событий очень просто: просто создаем функцию и регистрируем ее как обработчика событий. Полный
 * список ивентов здесь http://docs2x.smartfoxserver.com/api-docs/javadoc/server/com/smartfoxserver/v2/core/SFSEventParam.html.
 * Чтобы задеплоить наш Экстеншен, мы должны скомпилить джарник из Экстеншена и положить его в {smartfox_home}/SFS2X/extensions/, потом идем в админку,
 * в Конфигуратор Зон (Zone Configurator) и кликаем по Zone Extension. Мы можем сконфигурировать конкретный Экстеншен для каждой конкретной Зоны.
 *
 * */

package smartfox.java_extensions;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class QuickStart_00 {
}

class MyExtension extends SFSExtension {

    @Override
    public void init() {
        trace("Hello, this is my first SFS2X Extension!");

        // Добавляем обработчик запроса
        addRequestHandler("sum", SumReqHandler.class);
        // Добавляем обработчик события
        addEventHandler(SFSEventType.USER_JOIN_ZONE, ZoneEventHandler.class);
    }

    @Override
    public void destroy() {
        // Всегда надо удостовериться, что super вызван первым
        super.destroy();
        trace("Destroy is called");
    }
}

class SumReqHandler extends BaseClientRequestHandler {

    @Override
    public void handleClientRequest(User sender, ISFSObject params) {
        // Получаем параметры клиента
        int n1 = params.getInt("n1");
        int n2 = params.getInt("n2");

        // Создаем объект для ответа
        ISFSObject resObj = new SFSObject();
        resObj.putInt("res", n1 + n2);

        // Отправляем ответ
        send("sum", resObj, sender);

    }
}

class ZoneEventHandler extends BaseServerEventHandler {

    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException {
        User user = (User) event.getParameter(SFSEventParam.USER);
        trace("Welcome new user: " + user.getName());
    }
}
