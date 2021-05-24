/**
 *
 * Алгоритм работы мультиплеера, исходя из двух пердыдущих статей:
 *  - Сервер получает команды с клиентов и времена их отправления
 *  - Сервер обновляет состояние мира
 *  - Сервер с некоторой частотой отправляет свое состояние всем клиентам
 *  - Клиент отправляет команды и локально воспроизводит их результат
 *  - Клиент получает обновленные состояния мира и:
 *      - применяет состояние от сервера
 *      - заново применяет все свои команды, которые сервер не успел применить
 *      - интерполирует предыдущие состояния других игроков
 *  - С точки зрения игрока есть два серьезных последствия
 *      - игрок видит себя в настоящем
 *      - игрок видит других в прошлом
 * Обычно, это работает, но не в тех ситуациях, когда важна высокая пространственно-временная точность, например, когда мы хотим
 * поставить хэдшот.
 * Компенсация лага.
 * В архитектуре, которую мы построили, мы целимся в место, где голова врага была в прошлом, за 100 мс до момента нашего выстрела
 * (scheme_3_0.gif). Этому есть простое решение, которое применимо во многих случаях:
 *  - Когда мы стреляем, клиент отправляет событие выстрела, содержащее серверное время состояния, которое
 *      отображалось в момент выстрела. И точное направление оружия.
 *  - Ключевое! Так как сервер все равно рассылает свое состояние каждый кадр по клиентам, он может восстановить
 *      любой момент из прошлого. В том числе, он может восстановить мир в точности таким, какием его видел клиент
 *      в момент выстрела.
 *  - Таким образом сервер ОСТАВЛЯЕТ СТРЕЛКА в том положении, в котором он находится сейчас (в момент выстрела),
 *      а весь ОСТАЛЬНОЙ МИР МЕНЯЕТ на то состояние, которое клиент отображал в момент выстрела, то есть, ВОЗВРАЩАЕТ
 *      НАЗАД ВО ВРЕМЕНИ
 *  - Сервер обрабатывает выстрел и получает его результат
 *  - Сервер возвращает весь мир в текущее состояние времени
 *  - Сервер применяет результат выстрела и рассылает обновления по клиентам (schema_3_1.gif)
 * Минус в том, что застрелен может быть игрок, который в своем времени уже пробежал через опасную зону (schema_3_2.gif) и думал,
 * что уже в безопасности. Это компромисс, на который мы вынуждены пойти, так как стрелявший все таки застрелил его в прошлом. Было
 * бы гораздо хуже, если бы точный выстрел не сработал.
 *
 *
 * */

package habr.multiplayer;

public class Part3 {
}