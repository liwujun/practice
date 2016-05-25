package commontest;

import org.junit.Test;

import java.util.*;

/**
 * Created by liwujun
 * on 2016/2/23 at 13:40
 */
public class UnitT {
    @Test
    public void reference() {
        List<Map> list = new ArrayList<Map>();
        for (int i = 0; i < 10; i++) {
            Random random = new Random(10000);
            Map<String, Object> value_store = new HashMap<String, Object>();
            value_store.put(random.nextInt() + "", "省份" + random.nextInt());
            list.add(value_store);
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                Map map1 = list.get(i);
                Map map2 = list.get(j);
                int key1,key2;
                Iterator iterator1=map1.entrySet().iterator();
                Map.Entry entry= (Map.Entry) iterator1.next();
//                key1=entry.getKey();

            }
        }
    }
}
