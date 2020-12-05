package org.xine;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.junit.Test;

public class EqualsHashCde {

    @Test
    public void testHash() {
        final Person v = new Person(1L, "vidigal");
        final Person j = new Person(1L, "joao");

        final Map<Person, Object> map = new Hashtable<>();
        map.put(v, v);
        map.put(j, j);

        System.out.println(map.keySet().size());

        // .map(e -> e.getKey())
        map.forEach((key, value) -> System.out.println(key + " <-> " + value));
    }

    public void hastTestWithNumbersAndString() {
        final Map<Integer, String> map = new HashMap<>();
        map.put(1, "AAA");
        map.put(1, "BBB");

        map.forEach((key, value) -> System.out.println(key + "- " + value));
    }

}
