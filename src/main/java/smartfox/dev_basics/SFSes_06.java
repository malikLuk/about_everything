/**
 *
 * В смартфоксе есть 2 фундаментальных класса, SFSObject и SFSArray, которые являются основой при работе и пересылке данных между клиентом и
 * сервером. Эти классы являются общими для всех апи на всех языках. SFSObject и SFSArray представляют собой платформонезависимые, выскоуровневые
 * абстракции данных, пересылаемых между клиентом и сервером. SFSObject похож на ассоциативный массив (мапу), SFSArray - на список. Могут быть
 * вложенными. Рассмотрим пример, где нам нужно отправить данные о боевой машине м мультиплеерной игре:
 *  ISFSObject sfso = new SFSObject();
 *  sfso.putByte("id", 10);
 *  sfso.putShort("health", 5000);
 *  sfso.putIntArray("pos", Arrays.asList(120,150));
 *  sfso.putUtfString("name", "Hurricane");
 * Обратим внимание, что при использовании числовых типов, мы пользуемся только тем, что надо (шорт, байт), а не всегда интом. Только для массива
 * координат мы использовали инты, но это заивисит от размеров игрового мира. Поддерживаемые типы данных на sm_data_types.png
 * Рассмотрим более полный пример, клиенту надо передать данные серверу:
 *  public function sendSomeData():void {
 *     var sfso:SFSObject = new SFSObject();
 *     sfso.putByte("id", 10);
 *     sfso.putShort("health", 5000);
 *     sfso.putIntArray("pos", [120,150]);
 *     sfso.putUtfString("name", "Hurricane");
 *
 *     // Send request to Zone level extension on server side
 *     sfs.send( new ExtensionRequest("data", sfso) );
 *  }
 *
 *  Серверный Эктеншен (Java) принимает эти данные, зная параметры SFSObject'а:
 *  public class DataRequestHandler extends BaseClientRequestHandler {
 *      @Override
 *      public void handleClientRequest(User sender, ISFSObject params) {
 *          // Get the client parameters
 *          byte id = params.getByte("id");
 *          short health = params.getShort("health");
 *          Collection<Integer> pos = params.getIntArray("pos");
 *          String name = params.getUtfString("name");
 *
 *          // Do something cool with the data...
 *      }
 *  }
 *
 * Оба эти класса имеют методы для вывода своего содержимого в иерархичном или шестнадцатиричном формате, это методы getDump() и getHexDump().
 * Особо следует остановиться на ByteArray - его надо использовать для передачи небольших файлов, изображений, медиа, зашифрованных данных и т
 * д. При передаче больших блоков данных рекомендуется предварительное сжатие.
 * Лучшие практики. SFSObject и SFSArray не потокобезопасны. В 90% случаев эти объекты используются для передачи данных и усправления локальными
 * переменными и многопоточность особо не нужна. Если для представления модели какой-то игровой сущности мы будем использовать несколько кастомных
 * классов, то лучше добавить в них методы toSFSObject и newFromSFSObject для преобразования их в SFSObject и обратно. По крайней мере, делать это
 * для тех классов, объекты которых будут передоваться по сети. Вот пример ниже:
 *
 *  public class CombatQuad {
 *      private int unitID;
 *      private int posx;
 *      private int posy;
 *      private int energyLevel;
 *      private int bulletCount;
 *
 *      public CombatQuad(int unitID) {
 *          this.unitID = unitID;
 *          this.energyLevel = 100;
 *          this.bulletCount = 20;
 *      }
 *
 *      //... More getters and setters...
 *
 *      public ISFSObject toSFSObject() {
 *          ISFSObject sfso = new SFSObject();
 *
 *          sfso.putByte("id", unitID);
 *          sfso.putShort("px", posx);
 *          sfso.putShort("py", posy);
 *          sfso.putByte("el", energyLevel);
 *          sfso.putShort("bc", bulletCount);
 *
 *          return sfso;
 *      }
 *  }
 *
 * Отправить эти данные можно так:
 *
 *  public void sendMapUpdate(CombatQuad quad, OtherObject other, User recipient) {
 *      ISFSObject responseObj = new SFSObject();
 *      responseObj.putSFSObject("quad", quad.toSFSObject());
 *      responseObj.putSFSObject("other", other.toSFSObject());
 *
 *      send("quadUpdate", responseObj, recipient);
 *  }
 *
 * На клиенте же можно использовать newFromSFSObject и "распкаовать" SFSObject в CombatQuad. Важно, UTF-8 не поддерживается для ключей SFSObject,
 * только ASCII. Также рекомендуется делать ключи короткими.
 *
 * */

package smartfox.dev_basics;

public class SFSes_06 {
}
