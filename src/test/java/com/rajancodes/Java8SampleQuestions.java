package com.rajancodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Java8SampleQuestions {

    /**
     * Separate odd and even numbers in a list of integers.
     * <p>
     * Given a list of integers, write a Java 8 program to separate
     * the odd and even numbers into two separate lists.
     *
     * Usages  -
     * 1. Collectors.partitioningBy(Predicate)
     * 2. Collectors.collectingAndThen(
     */
    @Test
    @DisplayName("Separate odd and even numbers into Map")
    void _01_separateOddEven() {
        List<Integer> nums = Arrays.asList(1, 4, 5, 6, 7, 3, 3, 8, 0);

        Map<Boolean, List<Integer>> oddEvenMap =
                nums.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0));

        oddEvenMap.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " --> " + entry.getValue());
        });

        System.out.println("-------------------------------------------------");

        /**
         * Follow up , split and sort and unique
         */
        nums.stream().distinct().sorted().collect(Collectors.partitioningBy(n -> n % 2 == 0, Collectors.toList()))
                .entrySet()
                .forEach(entry ->
                        System.out.println(entry.getKey() + " --> " + entry.getValue()));

        System.out.println("-------------------------------------------------");
        /**
         * Separate into List
         */
        nums.stream().collect(Collectors.collectingAndThen(Collectors.partitioningBy(n -> n % 2 == 0), Map::values))
                .forEach(list -> System.out.println(list));

    }

    /**
     * Remove duplicate elements from a list using Java 8 streams
     *
     * Write a Java 8 program to remove duplicate elements from a list
     * using the stream API and lambda expressions.
     *
     * Usages - distinct()
     */
    @Test
    @DisplayName("Remove duplicates from a list")
    void _02_removeDuplicatesFromList(){
        List<Integer> nums = Arrays.asList(1,4,5,6,3,2,2,4,4);

        List<Integer> uniqueNums = nums.stream().distinct().collect(Collectors.toList());
        uniqueNums.forEach(i -> System.out.print(i + ", "));
        System.out.println("---------------------------");

        nums.stream().collect(Collectors.toSet()).forEach(i -> System.out.print(i + ", "));
        System.out.println("---------------------------");

        // Removes the all the duplicate entries from the list
        nums.stream().filter(number -> nums.indexOf(number) == nums.lastIndexOf(number))
                .collect(Collectors.toList())
                .forEach(i -> System.out.print(i + ", "));
        System.out.println("---------------------------");
    }

    /**
     * Find the frequency of each character in a string using Java 8 streams
     *
     * Write a Java 8 program to find the frequency of each character in
     * a given string using the stream API and collectors.
     *
     * Usages -
     * 1. peek - Stream<T> peek(Consumer<? super T> action);
     * 2. Collectors.joining(delimiter)
     * 3. Function.identity()
     * 4. Collectors.counting()
     * 5. Collectors.groupingBy(classifier, downstream)
     */
    @Test
    @DisplayName("Character frequency")
    void _03_characterFrequency(){
        String word = "Hello World";

        String wordWithoutSpace = Arrays.stream(word.split(" ")).peek(System.out::println).collect(Collectors.joining(""));
        System.out.println(wordWithoutSpace);

        Arrays.stream(wordWithoutSpace.split("")).collect(groupingBy(Function.identity(), counting()))
                .entrySet().forEach(e -> System.out.println(e.getKey() + " --> " + e.getValue()));

    }

    /**
     * Find the frequency of each element in an array or a list
     *
     * Write a Java 8 program to find the frequency of
     * each element in an array or a list using streams and collectors.
     */
    @Test
    @DisplayName("Word frequency")
    void _04_wordFrequency(){
        List<String> names = Arrays.asList("rohit", "urmila", "rohit", "urmila", "ram", "sham", "sita", "gita");
        Map<String, Long> frequencyWords = names.stream()
                .collect(groupingBy(Function.identity(), counting()));
        System.out.println(frequencyWords);
        System.out.println("-------------------------------");

        Map<String, Integer> frequencyWordsUsingSummingInt = names.stream().parallel()
                .collect(groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
        System.out.println(frequencyWordsUsingSummingInt);
        System.out.println("--------------------------------");

        Map<String, Integer> frequencyWordsUsingMap = names.stream()
                .collect(Collectors.toMap(Function.identity(), w->1, Integer::sum));
        System.out.println(frequencyWordsUsingMap);
        System.out.println("---------------------------------");
    }

    /**
     * Sort a given list of decimals in reverse order
     *
     * Write a Java 8 program to sort a given list of decimal numbers in reverse order.
     */
    @Test
    @DisplayName("Sort in reverse order")
    void _05_reverseOrderSort(){
        // List.of() returns unmodifiable list, thus cannot call sort in that
        List<Integer> list = new ArrayList<>(List.of(1,2,5,4,3,6,7,7));
//        list.sort(Comparator.reverseOrder());

        list.stream().sorted((x,y) -> Integer.compare(y, x)).collect(Collectors.toList()).forEach(System.out::print);
        System.out.println("-----------------------");

        list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).forEach(System.out::print);
        System.out.println("------------------------");
    }

    /**
     * Join a list of strings with '[' as prefix, ']' as suffix, and ',' as delimiter
     *
     * Given a list of strings, write a Java 8 program to join the strings
     * with '[' as a prefix, ']' as a suffix, and ',' as a delimiter.
     */

    @Test
    @DisplayName("Join list of strings with prefix, postfix and delimiter")
    void _06_joinListOfStrings(){
        List<String> languageList = List.of("java", "c++", "c", "C sharp", "python", "kotlin", "scala");
        String joinedString = languageList.stream().collect(Collectors.joining(", ", "[", "]"));
        System.out.println(joinedString);
    }

    /**
     * Find the maximum and minimum of a list of integers
     * Given a list of integers, write a Java 8 program to find the maximum and minimum numbers in the list.
     */
    @Test
    @DisplayName("Min and max from list")
    void _07_minMaxFromList(){
        List<Integer> list = List.of(1,5,3,2,65,72,0);
        Integer max = list.stream().max(Integer::compareTo).orElse(Integer.MAX_VALUE);
        Integer min = list.stream().min(Integer::compareTo).orElse(Integer.MIN_VALUE);
        System.out.println("Min and Max - " + min + ", " + max);

        IntSummaryStatistics summaryStatistics = list.stream().collect(summarizingInt(Integer::intValue));
        System.out.println("min - " + summaryStatistics.getMin());
        System.out.println("max - " + summaryStatistics.getMax());
        System.out.println("count - " + summaryStatistics.getCount());
        System.out.println("average - " + summaryStatistics.getAverage());
    }

    /**
     * Merge two unsorted arrays into a single sorted array using Java 8 streams
     * Write a Java 8 program to merge two unsorted arrays into a single-sorted array using the stream API.
     */
    @Test
    @DisplayName("Merge unsorted array into sorted array")
    void _08_mergeUnsortedArrayIntoSorted(){
        int [] array1 = {12, 32, 2, 4, 777, 5, 32, 890, 422, 44, 99, 43};
        int [] array2 = {4, 3, 2, 5, 6, 78, 98, 53, 90};

        int [] sortedArray = IntStream.concat(Arrays.stream(array1), Arrays.stream(array2)).sorted().toArray();
        System.out.println(Arrays.toString(sortedArray));

        int[] sortedArrayWithoutDuplicates = IntStream.concat(Arrays.stream(array1), Arrays.stream(array2))
                .distinct().sorted().toArray();
        System.out.println(Arrays.toString(sortedArrayWithoutDuplicates));
    }





    /**
     * Find most frequent item and frequencies of items in a generic collection.
     * @param items
     * @return
     * @param <T>
     */
    private <T> T findMostFrequentItem(Collection<T> items){
        return items.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private <T> Map<T, Long> findItemFrequencies(Collection<T> items){
        return items.stream()
                .filter(Objects::nonNull)
                .collect(groupingBy(Function.identity(), counting()));
    }

}
