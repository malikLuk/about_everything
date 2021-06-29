/**
 *
 * Чтобы реализовать кастомный логин - надо выполнить два действия. Первое - сконфигурировать Зону, а именно включить в ней настройку
 * "use custom login" и рестартовать сервер. Второе - создать новый Экстеншен, наследующий SFSExtension, где init() метод будет выглядеть
 * примерно так:
 * @Override
 * public void init() {
 *    trace("My CustomLogin extension starts!");
 *
 *    // Register for login event
 *    addEventHandler(SFSEventType.USER_LOGIN, LoginEventHandler.class);
 * }
 *
 * А LoginEventHandler вот так:
 * public class LoginEventHandler extends BaseServerEventHandler {
 *    @Override
 *    public void handleServerEvent(ISFSEvent event) throws SFSException {
 *       String name = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
 *
 *       if (name.equals("Gonzo") || name.equals("Kermit")) {
 *
 *         // Create the error code to send to the client
 *         SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
 *         errData.addParameter(name);
 *
 *         // Fire a Login exception
 *         throw new SFSLoginException("Gonzo and Kermit are not allowed in this Zone!", errData);
 *       }
 *    }
 * }
 *
 * Мы видим, что если залогиниться пытается чел, который забанен, то его не пускает и выбрасывается SFSLoginException. В Экстеншене
 * кастомного лоигна можно контролировать такие вещи как переполнение Зоны, авторизацию пользователей с уже существующими именами,
 * забаненных пользователей, фильтр матерных ников и т д.
 * Для входа с паролем у смартфокса тоже есть инструментарий. В целях безопасности, пароль никогда не передается в чистом виде. Чтобы
 * сравнить переданный пароль с тем, что хранится в БД надо вызвать метод:
 *  getApi().checkSecurePassword(session, clearPass, encryptedPass);
 * Для клиента нет никакой разницы между стандартным и кастомным логином.
 * Существуют случаи, когда нам надо сменить имя, которое передал юзер с клиента, на какое-нибудь другое, например, извлеченное из БД.
 * Такое нужно, когда пользователь залогинился с помощью адреса электронной почты, но в БД у нас есть его никнейм, который нам надо
 * подставить. И нам как-то надо вернуть на клиент именно то имя, которое лежит в БД. Вот как это можно сделать:
 *
 * public class LoginEventHandler extends BaseServerEventHandler {
 *    @Override
 *    public void handleServerEvent(ISFSEvent event) throws SFSException {
 *       String name = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
 *       ISFSObject outData = (ISFSObject) event.getParameter(SFSEventParam.LOGIN_OUT_DATA);
 *
 *       // ...
 *       // your login logic goes here
 *       // ...
 *
 *       // Provide a new name for the user:
 *       String newName = "User-" + name;
 *       outData.putUtfString(SFSConstants.NEW_LOGIN_NAME, newName);
 *    }
 * }
 *
 * То есть, при обработке ивента USER_LOGIN мы просто подсовываем клиенту ответ, в котором лежит настоящий никнейм игрока.
 * Смартфокс позволяет также управлять правами доступа юзера. Можно это делать через сессию
 *
 *  session.setProperty("$permission", DefaultPermissionProfile.MODERATOR);
 *
 * Более подробно здесь http://docs2x.smartfoxserver.com/AdvancedTopics/privilege-manager
 *
 * */

package smartfox.tips;

public class CustomLogin {
}
