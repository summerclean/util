package work.sihai.common.utils;

import java.util.Map;
import java.util.TreeMap;

public class TMap {

    public static void main(String[] args) {
        TreeMap<Integer,String> a = new TreeMap<>();
        a.put(10,"1");
        a.put(20,"2");
        a.put(30,"3");

        Map.Entry<Integer, String> entry = a.higherEntry(11);
        System.out.println(entry.getValue());

    }

}
