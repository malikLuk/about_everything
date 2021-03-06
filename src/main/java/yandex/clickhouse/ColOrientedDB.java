/**
 *
 *                                          Колоночные СУБД.
 * Популярные в наше время СУБД - Oracle, SQL Server, Postgre SQL etc. базируются на архитектуре 1970-х годов. Главная
 * задача баз данных тогда заключалась в том, чтобы поддержать начавшийся в 1960-х годах массовый переход от бумажного
 * учета хозяйственной деятельности к компьютерному. Такие требования обусловили архитектурные особенности реляционных
 * СУБД, оставшиеся с 1970-х годов практически неизменными: построчное хранение данных, индексирование записей и
 * журналирование операций.
 * Под построчным хранением данных обычно понимается физическое хранение всей строки таблицы в виде одной записи, в
 * которой поля идут последовательно одно за другим, а за последним полем записи в обзем случае идет первое поле
 * следующей записи. Приблизительно это выглядит так:
 *      [A1, B1, C1], [A2, B2, C2], ...[An, Bn, Cn]
 * где A, B и C - это поля(столбцы) а 1,2...n - номер записи(строки). Такое хранение чрезвычайно удобно для частых операций
 * добавления новых строк в БД, храняющуюся, как правило, на жестком диске - ведь, в таком случае, новая запись может быть
 * добавлена целиком всего за один проход головки накопителя. Существенные ограничения по скорости, накладываемые НМЖД
 * (несъемный магнитный жесткий диск), вызвали также необходимость введения специальных индексов, которые позволяли бы
 * отыскивать нужную запись на диске за минимальное количество проходов головки ЖД. Обычно формируется несколько индексов,
 * в зависимости от того, по каким полям требуется делать поиск, что увеличивает объем БД на диске иногда в несколько раз.
 * Для отказоустойчивости, традиционных СУБД автоматически дублируют операции в логах, что приводит к еще большему месту,
 * занимаемому на дисках. В итоге, например, БД Oracle занимает на диске в пять раз больше места, чем объем полезных данных
 * в ней.
 * Однако, как позже выяснилось, характер нагрузки при накоплении данных, и характер нагрузки при анализе накопленных
 * данных радикально отличаются. Если транзакционным приложениям свойственны очень частые мелкие операции добавления или
 * изменения одной или нескольких записей(insert/update), то в случае аналитических систем картина прямо противоположная -
 * наибольшая нагрузка создается сравнительно редкими, но тяжелыми выборками(select) сотен тысяч и миллионов записей, часто
 * с группировками и расчетом итоговых значений(так называемых агрегатов). Количество операций записи при этом невысоко,
 * нередко менее 1% от общего числа. Причем запись часто идет крупными блоками(bulk load). Отметим, что у аналитических
 * выборок есть одна важная особенность - как правило, они содержат всего несколько полей, а не всю строку. Однако, что
 * произойдет, если выбрать, например, только 3 поля из таблицы, в которой их всего 50? В силу построчного хранения
 * данных в традиционных СУБД(необходимого для частых операций добавления новых записей) будут прочитаны абсолютно все
 * строки целиком со всеми полями. Это значит, что не важно, нужны ли нам только 3 поля или 50, с диска, в любом случае,
 * они будут прочитаны целиком и полностью, пропущены через контроллер дискового ввода-вывода и переданы процессору,
 * который уже отберет только необходимые для запроса. К сожалению, каналы дискового ввода-вывода обычно являются основным
 * ограничителем производительности аналитических систем. Как результат, эффективность традиционной СУБД при выполнении
 * данного запроса может снизиться в 10-15 раз из-за неминуемого чтения лишних ланных.
 * Решить эту проблему призваны колоночные СУБД. Основная идея колоночных СУБД - это хранение данных не по строкам, как
 * это делают традиционные СУБД, а по колонкам. Это означает, что с точки зрения SQL-клиента данные предствалены, как
 * обычно, в виде таблиц, но физически эти таблицы являются совокупностью столбцов, а не строк, как это сделано в
 * традиционных СУБД типа Oracle. Каждая колонка(столбец) по сути предстваляет собой таблицу из одного поля. При этом,
 * физически на диске значения одного поля(столбца) хранятся последовательно друг за другом, примерно так:
 *      [A1, A2, ...An], [B1, B2, ...Bn], [C1, C2, ...Cn] и так далее
 * Такая организация данных приводит к тому, что при выполнении select, в котором фигурируют только 3 поля из 50 полей
 * таблицы, с диска физически будут прочитаны только 3 колонки. Это означает, что нагрузка на канал ввода-вывода будет
 * приблизительно в 50/3=17 раз меньше, что при выполнении того же запроса в традиционной СУБД. Кроме того, при колоночном
 * хранении данных появляется возможность сильно сжимать данные, так как в одной колонке таблицы, как правило, однотипные
 * данные, чего не скажешь о строке.
 * В итоге - колоночные СУБД призваны решить проблему неэффективной работы традиционных СУБД в аналитических системах
 * и системах с подавляыющим большинством операций типа "чтение". Они позволяют на более дешевом и маломощном
 * оборудовании получить прирост скорости выполнения запросов в 5, 10 и много более раз, при этом, благодаря компрессии,
 * данные будут занимать на диске в 5-10 раз меньше места, чем в случае с традиционными СУБД.
 * У колоночных СУБД есть и недостатки - они медленно работают на запись, не подходят для тразакционных систем.
 *
 * */


package yandex.clickhouse;

public class ColOrientedDB {
}
