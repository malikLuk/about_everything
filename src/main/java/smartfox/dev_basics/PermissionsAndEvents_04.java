/**
 *
 * Доп. настройки. Доступы и события. В смартфоксе у нас есть полный контроль над событиями, запускаемыми каждой Комнатой. Пример конфигурации
 * комнаты в админке (sm_room_cfg.png). По этому примеру видно, как можно настроить права и ивенты. Согласно примеру, игрок может менять имя и
 * пароль Комнаты, а вот изменение вместимости запрещено. Также там указаны запускаеме Комнатой ивенты. В Комнатах есть две категории настроек,
 * Ивенты и Права доступа. Ивенты указывают, о каких события Крмната будет уведомлять присоединившихся игроков:
 *  - USER_ENTER_EVENT (default true). Уведомление о присоединении. Отключение приведет у нарушение правильной работы личных и публичных
 *      сообщений. Но общение через список друзей будет работать.
 *  - USER_EXIT_EVENT (default true). Уведомление о выходе из Комнаты. Отключение приведет у нарушение правильной работы личных и публичных
 *      сообщений. Но общение через список друзей будет работать.
 *  - USER_COUNT_CHANGE_EVENT (default true). Уведомление количестве игроков и спектраторов в Комнате. Одно из самых ресурсоемких уведомлений
 *      при интенсивном траффике. Смартфокс предлагает некотоыре оптимизации.
 *          ** Приоритетность пакетов. Смартфокс позволяет указать приоритет для каждого пакета, позволяя отбрасывать такие низкоприоритетные
 *          уведомления как обновление количества игроков.
 *          ** Регулирование таких событий. Смартфокс может просто не сразу отправлять уведомления о количестве игроков, а через некоторый
 *          интервал, который надо указать в админке.
 *  - USER_VARIABLES_UPDATE_EVENT (default true). Уведомление об обновлении пользовательских переменных.
 * USER_ENTER_EVENT и USER_EXIT_EVENT всегда нужно включать и выключать в паре.
 * Права доступа:
 *  - ROOM_NAME_CHANGE. Право менять имя Комнаты. Только для создателя Комнаты.
 *  - PASSWORD_STATE_CHANGE. Право удалять/изменять пароль Комнаты. Только для создателя Комнаты.
 *  - PUBLIC_MESSAGES. Право отправлять публичные сообщения.
 *  - CAPACITY_CHANGE. Право менять максимальное число игроков в Комнате.
 *
 * */

package smartfox.dev_basics;

public class PermissionsAndEvents_04 {
}
