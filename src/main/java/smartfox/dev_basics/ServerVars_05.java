/**
 *
 * Серверные Переменные позволяют клиенту или серверу создавать настраиваемые значения, которые автоматически обновляться в пределах их
 * области видимости. СУществуют три типа серверных переменных с разной областью видимости:
 *  - Пользовательские Переменные. Представляют собой пользовательские данные, прикрепленные к конкретному игроку. Все обновления получают
 *      игроки в той же Комнате, к которой присоединился владелец. Другими словами, пользователь А может иметь доступ и получать обновления
 *      всех пременных пользователя Б, если они находятся в одной Комнате. Типичное использование: сохранение профиля игрока (очки, ранк,
 *      возраст, аватар и т д).
 *  - Переменные Комнаты. Представляют собой данные об объекте Комнаты. Область видимости - сама Комната, то есть все пользователи в данной
 *      Комнате могут получать информацию об обновлениях этих Переменных. Можно сделать их видимыми даже для другой Комнаты. Обычно в них
 *      хранится состояние игры, например, ид карты.
 *  - Переменные списка друзей. Представляют собой данные о игроках из списка друзей. Область видимости - сам список друзей. Применение -
 *      информация о статусе друзей из списка (онлайн, оффлайн, играет и т д).
 * Общая архитектура на sm_general_arch.png.
 * Пользовательские Переменные и Переменные списка друзей могут быть созданы ТОЛЬКО динамически. Переменные Комнаты же могут быть созданы во
 * время настройки самой Комнаты в админке.
 *
 * Примеры Пользовательских Переменных:
 *
 * private void setUserProfile(User user, int databaseId) {
 *     // public user var
 *     UserVariable avatarPic = new SFSUserVariable("pic", "GonzoTheGreat.jpg");
 *
 *     // private user var
 *     UserVariable dbId = new SFSUserVariable("dbId", databaseId);
 *     dbId.setHidden(true);
 *
 *     // Set variables via the server side API
 *     getApi().setUserVariables(user, Arrays.asList(avatarPic, dbId));
 * }
 *
 * Пример Переменных Комнаты. В отличие от ПК, созданных клиентом, серверные никогда не протухают, потому что их создатель - сам сервер, апи это
 * понимает, когда мы передаем null в качестве создателя:
 *
 * private void setupRoomVariables(Room room) {
 *     // Private Global Room variable, no one will be able to overwrite this value
 *     // The topic will be visible from outside the Room
 *     RoomVariable chatTopic = new SFSRoomVariable("topic", "Multiplayer Game Development");
 *     chatTopic.setPrivate(true);
 *     chatTopic.setGlobal(true);
 *
 *     // Secret variable, only accessible from server side
 *     RoomVariable isModerated = new SFSRoomVariable("isMod", true);
 *     isModerated.setHidden(true);
 *
 *     // Set variables via the server side API
 *     // Passing null as the User parameter sets the ownership of the variables to the Server itself
 *     getApi().setRoomVariables(null, room, Arrays.asList(chatTopic, isModerated));
 * }
 *
 *
 * */

package smartfox.dev_basics;

public class ServerVars_05 {
}
