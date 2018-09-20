/**
 *
 * ALL_SOURCE - это таблица, которая описывает доступные объекты БД для текущего пользователя
 *
 * Synonym(синоним) - это альтернативное имя для таких объектов как таблицы, представления, последовательности, процедуры
 * и др. Как правило, используется для предоставления доступа к объекту из другой схемы.
 * Синтаксис:
 *      create [or replace] [public] synonym [schema.]synonym_name for [schema.]object_name [@dblink]
 * Например:
 *      create public synonym suppliers for app.suppliers
 *      Здесь создается публичный синоним с именем suppliers для таблицы suppliers из схемы app.
 *
 *
 * */


package oracle;

public class Tips {
}
