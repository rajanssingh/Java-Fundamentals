package com.rajancodes.Collections;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Puzzles shown in <a href="https://www.youtube.com/watch?v=i6xD6-KPQ-g&t=633s">...</a>
 * Seminar by Maurice Naftalin && Dmitry Vyazelenko
 */
public class CollectionPuzzlesTest {

    @Test
    void arraysAsList(){
        /**
         * public static <T> List<T> asList(T... a) {
         *         return new ArrayList<>(a);
         *     }
         */
        String[] stringArray = {"one", "two", "three"};
        var stringList = Arrays.asList(stringArray);

        System.out.println(stringList); // o/p -  [one, two, three]

        // Primitive array , thus the entire array is treated as an object
        int[] intArray = {1, 2, 3};
        var intList1 = Arrays.asList(intArray);
        var intList2 = List.of(intArray);

        System.out.println(intList1); // o/p - [[I@45afc369] -- entire intArray is boxed into an object, so intList is a list of arrays
        System.out.println(intList1.get(0).length); // op - 3
        System.out.println(intList2); // o/p - [[I@45afc369] - both lists just offer a view over the array
        System.out.println(intList2.get(0).length); // o/p - 3

        /**
         *     boolean contains(Object o);
         *     boolean containsAll(Collection<?> c);
         *
         */

        System.out.print(stringList.contains("one") + ","); // --true
        System.out.print(intList1.contains(1)); // -- false
        System.out.print(intList2.contains(1)); // -- false
    }

    @Test
    void subList(){
        var ints = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        var subList = ints.subList(0,0);
        // subList is a view on ints, does not create any new object
        System.out.print(subList + " ");

        /**
         *     boolean addAll(Collection<? extends E> c);
         */
        subList.addAll(List.of(10, 11, 12));
        System.out.print(ints);

        // o/p - [] [10, 11, 12, 1, 2, 3, 4, 5]
    }

    @Test
    void unModifiableCollections(){
        String[] strings = {"a", "b", "c", null};
        System.out.println(strings.length);

        var strings1 = Stream.of(strings).toList();
        System.out.println(strings1.size());

        var strings2 = Arrays.asList(strings);
        System.out.println(strings2.size());

//        var strings3 = List.of(strings); // throws exception
//        System.out.println(strings3.size());
        /**
         *  Unmodifiable collections (Introduced in Java 9) do not allow null values
         */

//        List<String> stringList = Arrays.asList(strings);
//        stringList.removeIf(Objects::isNull); //  throws exception UnsupportedOperation exception
//        System.out.println(stringList);
        // asList returns fixed sized list, can only change the contents of the array
    }

    @Test
    void hashMap(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, null);
        // Only check contains key or not
        System.out.println(map.getOrDefault(1, "One")); // o/p - null

        // treats null value as (key,value) not present
        map.putIfAbsent(1, "One");
        System.out.println(map.get(1)); // o/p - One
    }

    @Test
    void hashMap1(){
        var numbers = List.of(-1, 0, 1);
        Map<Integer, List<Integer>> map = new HashMap<>();

//        /**
//         * will throw NullPointerException -  putIfAbsent returns the old value which will be null, we cannot add to null
//         */
//        numbers.forEach(
//                number -> map.putIfAbsent(number, new ArrayList<>()).add(number)
//        );
//        System.out.println(map.get(0));

        // computeIfAbsent returns the new value
        // ArrayList::new will throw error - java.lang.IllegalArgumentException: Illegal Capacity: -1
        // computeIfAbsent requires a mappingFunction , when you choose method reference, we cannot choose which overload it will have, so it will choose the one which fits the context
        // which in case below will is --  number -> map.computeIfAbsent(number, initialCapacity -> new ArrayList<Integer>(initialCapacity)).add(number)

//        numbers.forEach(
//                number -> map.computeIfAbsent(number, ArrayList::new).add(number)
//        );
//        System.out.println(map.get(0));

    }

    @Test
    void hashSet(){
        Set<String> hashSet = new HashSet<>(List.of("a", "b", "c"));
        Set<String> treeSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        treeSet.addAll(List.of("A", "B", "C"));

        System.out.print(treeSet.equals(hashSet) + " ");
        System.out.print(hashSet.equals(treeSet));
        // o/p - true false
    }

    @Test
    void identityHashMap(){
        var map = new IdentityHashMap<Integer, String>();
        map.put(1, "one");
        map.put(10, "ten");
        map.put(100, "hundred");
        map.put(1000, "thousand");
        map.put(1, "one");
        map.put(10, "ten again");
        map.put(100, "hundred again");
        map.put(1000, "thousand again");
        System.out.println(map.size());

        map.entrySet().forEach(System.out::println);

        // o/p - 5
        // -128 to 127 values are cached in java
        // Introduced as boxing of primitive int to Integer is expensive operation
        // There is no ways to get value of 1000 from key, as we cannot get the same object reference for 1000
    }

    @Test
    void linkedHashMap(){
        // LinkedHashMap -- order is decided on the insertion order
        Map<String, Integer> map = new LinkedHashMap<>(3);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("b", 4);
        System.out.println(map.get("a"));
        System.out.println(map);
//        1
//        {a=1, b=4, c=3}
    }

    @Test
    void unModifiableCollectionsEquality(){
        var ints = List.of(1, 2, 3);
        var a = Arrays.asList(1, 2, 3);
        var c = Collections.unmodifiableCollection(ints);
        var l = Collections.unmodifiableList(ints);

        System.out.print(a.equals(c));
        System.out.print(a.equals(l));
        System.out.print(c.equals(l));

        // o/p - false true false
        // List can only be equal to a list
    }
}
