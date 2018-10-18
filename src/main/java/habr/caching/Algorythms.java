/**
 *
 *                                              Кэширование.
 * Коротко об основных алгоритмах кэширования(также называемых алгоритмами вытеснения).
 * Базовые:
 *  LRU - вытесняются данные, которые давно не используются.
 *  MRU - последний использованный вылетает из кэша.
 *  LFU - реже всего используемый вылетает из кэша.
 * Посложнее:
 *  SNLRU - сегментированный LRU. Заводим несколько "коробочек" с LRU. Сперва кладем в первую, при повторном запросе
 *      перекладываем во вторую, далее, в третью. Используется в недрах freebsd.
 *  MidPointLRU - сегментированный LRU, в котором всего две "коробочки".
 *
 * */

package habr.caching;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Algorythms {

    static CacheLoader<String, String> loader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            return key+="1488";
        }
    };

    static LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);

    public static void main(String[] args) {
        cache.getUnchecked("first");
        cache.getUnchecked("sec");
        cache.getUnchecked("dsdt");
        cache.getUnchecked("fsdst");
        cache.getUnchecked("fsds11t");
        System.out.println();
    }

}
