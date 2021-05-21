/**
 *
 * Партиционирование - это метод разделения больших (по кол-ву записей) таблиц на много маленьких. Создадим таблицу
 *  create table users (
 *      id             serial primary key,
 *      username       text not null unique,
 *      password       text,
 *      created_on     timestamptz not null,
 *      last_logged_on timestamptz not null
 *  );
 *
 * Добавим несколько строк и создадим индекс
 *
 * insert into users (username, password, created_on, last_logged_on)
 *     select
 *         random_string( (random() * 4 + 5)::int4),
 *         random_string( 20 ),
 *         now() - '2 years'::interval * random(),
 *         now() - '2 years'::interval * random()
 *     from
 *         generate_series(1, 10000);
 * create index newest_users on users (created_on);
 *
 * Итак, у нас появилась тестовая таблица с какими то рандомными данными. Теперь можно создавать партиции. Понятие партиция
 * тесно связано с понятием наследования, по сути - это оно и есть
 *  create table users_1 () inherits (users);
 *
 * Таким образом, у нас получилась новая таблица, у которой есть определенные интересные свойства%
 *      - она использует тот же сиквенс, что и основная таблица для своей колонки id
 *      - все столбцы имеют одинаковые определения, включая ограничения на null
 *      - нет ни первичного ключа, ни ограничений уникальности для имени пользователя, ни индекса для created_on
 * Попробуем по-другому
 *  drop table users_1;
 *  create table users_1 ( like users including all );
 *
 * Теперь у нас есть все индексы и ограничения, но мы потеряли информацию о наследовании. Но мы можем добавить ее позже с
 * помощью
 *  alter table users_1 inherit users;
 *
 * Мы могли бы сделать все в один шаг, но выскакивают всякие некрасивые уведомления. Так или иначе, у нас теперь есть две
 * таблицы - основная и первая партиция. Если мы произведем какое-либо действие - выборка/обновление/удаление - то обе таблицы
 * будут просканированы.
 *  explain analyze select * from users where id = 123; выдает такой лпан запроса
 *  Append  (cost=0.29..16.47 rows=2 width=66) (actual time=0.008..0.009 rows=1 loops=1)
 *    ->  Index Scan using users_pkey on users  (cost=0.29..8.30 rows=1 width=48) (actual time=0.008..0.008 rows=1 loops=1)
 *          Index Cond: (id = 123)
 *    ->  Index Scan using users_1_pkey on users_1  (cost=0.15..8.17 rows=1 width=84) (actual time=0.001..0.001 rows=0 loops=1)
 *          Index Cond: (id = 123)
 *  Planning time: 0.327 ms
 *  Execution time: 0.031 ms
 * (7 rows)
 *
 * Но если мы обратимся к партици напрямую - запрос будет выполнен только на ней
 *  explain analyze select * from users_1 where id = 123;
 *  Index Scan using users_1_pkey on users_1  (cost=0.15..8.17 rows=1 width=84) (actual time=0.002..0.002 rows=0 loops=1)
 *    Index Cond: (id = 123)
 *  Planning time: 0.162 ms
 *  Execution time: 0.022 ms
 * (4 rows)
 *
 * Если мы хотим обратиться только к таблице без ее партиций - то нужно использовать ключевое слово ONLY
 *  explain analyze select * from only users where id = 123;
 *  Index Scan using users_pkey on users  (cost=0.29..8.30 rows=1 width=48) (actual time=0.008..0.008 rows=1 loops=1)
 *    Index Cond: (id = 123)
 *  Planning time: 0.229 ms
 *  Execution time: 0.031 ms
 * (4 rows)
 *
 * Мы поговорили о выборке/обновлении/удалении, которые работают на всех партициях, но что насчет вставки? Вставка всегда
 * работает так, как будто использовано ключевое слово ONLY, поэтому, если мы хотим добавить строку в партицию users_1,
 * надо сделать так
 *  insert into users_1 ...
 *
 *  Выглядит не слишком хорошо, но есть способы это обойти
 *
 * */

package habr.postgres_partitioning;

public class Partitions {
}
