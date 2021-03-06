/**
 *
 *                                              https://habr.com/post/340146/
 * Пять шагов для понимания jwt.
 * JSON Web Token(JWT) - это JSON объект, который определен в открытом стандарте RDF 7519. Он считается одним из безопасных
 * способов передачи информации между двумя участниками. Для его создания нужно определить заголовок(header) с общей
 * информацией по токену, полезные данные(payload), такие как id пользователся, его роль и т.п. и подписи(signature).
 * Простыми словами, jwt - это лишь строка в формате header.payload.signature.
 * Допустим, мы хотим зарегаться на сайте. В нашем случае есть три участника - пользователь user, сервер приложения app
 * server и сервер аутентификации auth server. Сервер аутентификации будет обеспечивать пользователся токеном, с помощью
 * которого он позднее сможет взаимодействовать с приложением (рисунок jwt_1). То есть:
 * 1. Пользователь заходит на сервер аутентификации, проходит аутентификацию(не важно как, логин/пароль, ключи и т д).
 * 2. Сервер аутентификации создает jwt и отправляет его пользователю.
 * 3. Когда пользователь делает запрос к API приложения, он добавляет к нему ранее полученный jwt.
 * 4. При поступлении от пользователя запроса с jwt, приложение смотрит, является ли пользователь тем, за кого себя
 * выдает. В этой схеме сервер приложения сконфигурирован так, что может проверить, являетсяя ли входящий jwt именно тем,
 * что был создан сервером аутентификации.
 * Струтура JWT.
 * JWT состоит из трех частей: заголовок header, полезные данные payload, подпись signature.
 *
 * Шаг 1. Создаем header.
 * Заголовок jwt содержит информацию о том, как должна вычисляться jwt-подпись. Заголовок - это тоже JSON объект, который
 * выглядит так: header = { "alg": "HS256", "typ": "JWT"}. Поле alg определяет алгоритм хэширования. Он будет использоваться
 * при создании подписи HS256 не что иное, как HMAC-SHA256, для его вычисления нужен лишь один секретный ключ(в шаге 3).
 * Может использоваться и другой алгоритм RS256 - он является ассиметричным и создает два ключа, публичный и приватный.
 * Шаг 2. Создаем payload.
 * Полезные данные также называют jwt-claims(заявки). Например, в payload, может хранится информация о userId:
 * payload = { "userId": "b08f86af-35da-48f2-8fab-cef3904660bd" }. В payload можно положить несколько заявок, вот
 * некоторые из них:
 *  - iss(issuer) - определяет приложение, из которого отправляется токен.
 *  - sub(subject) - определяет тему токена.
 *  - exp(expiration time) - время жизни токена.
 * Эти поля не являются обязательными.
 * Шаг 3. Создаем signature.
 * Подпись вычисляется с использованием следующего псевдокода:
 *  const SECRET_KEY = "cAtwa1kkEy";
 *  const unsignedToken = base64urlEncode(header) + '.' + base64urlEncode(payload);
 *  const signature = HMAC-SHA256(unsignedToken, SECRET_KEY);
 * Алгоритм base64url кодирует заголовок и полезные данные, созданные на 1 и 2 шаге. Он соединяет закодированные строки
 * через точку. Затем полученная строка хэшируется алгоритмом, заданным в хэдере на основе нашего секретного ключа.
 * // header eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
 * // payloadey J1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ
 * // signature -xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM
 * Шаг 4. Объединим все вместе.
 * Теперь просто соединяем все через точку.
 * const token = encodeBase64Url(header) + '.' + encodeBase64Url(payload) + '.' + encodeBase64Url(signature)
 * Получится примерно вот это
 * eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJiMDhmODZhZi0zNWRhLTQ4ZjItOGZhYi1jZWYzOTA0NjYwYmQifQ.-xN_h82PHVTCMA9vdoHrcZxH-x5mb11y1537t3rGzcM
 * Шаг 5. Проверка JWT.
 * Сервер аутентификации и сервер приложения знают секретный ключ и алгоритм шифрования. Таким образом, при проверке
 * токена, сервер, может тоже выполнить тотже алгоритм, что и в шаге 3, и сравнить полученный результат с пришедшим.
 *
 * */

package habr.authentication;

public class JWT {
}
