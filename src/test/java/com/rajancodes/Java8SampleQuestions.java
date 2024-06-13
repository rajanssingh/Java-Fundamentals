package com.rajancodes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
     * Get the three maximum and three minimum numbers from a given list of integers
     *
     * Write a Java 8 program to get the three maximum and three minimum numbers from a given list of integers.
     */

    @Test
    @DisplayName("3 min and max")
    void _09_min3max3(){
        List<Integer> randomNumbers = List.of(12, 32, 2, 4, 777, 5, 32, 890, 422, 44, 99, 43);

        List<Integer> min3 = randomNumbers.stream().sorted().limit(3).collect(toList());
        List<Integer> max3 = randomNumbers.stream().sorted(Comparator.reverseOrder()).limit(3).collect(toList());

        System.out.println(min3);
        System.out.println(max3);
    }

    @Test
    @DisplayName(" Two String is anagram or not")
    void _10_isAnagram(){
        String str1 = "Rajan";
        String str2 = "aajrn";

        String join1 = Arrays.stream(str1.split("")).map(String::toLowerCase).sorted().collect(Collectors.joining());
        String join2 = Arrays.stream(str2.split("")).map(String::toLowerCase).sorted().collect(joining());

        System.out.println(join2);
        System.out.println(join1);

        assertTrue(join2.equalsIgnoreCase(join1));
    }

    /**
     * Find the sum of all digits of a number in Java 8
     *
     * Write a Java 8 program to find the sum of all digits of a given number.
     *
     */
    @Test
    @DisplayName("Sum of all digits in a number")
    void _11_sumOfDigits(){
        int num = 152;
        Integer sumOfDigits = Arrays.stream(String.valueOf(num).split("")).map(str -> Integer.valueOf(str)).reduce(0, Integer::sum);
        System.out.println(sumOfDigits);
    }

    /**
     * Find the second-largest number in an integer array
     *
     * Write a Java 8 program to find the second-largest number in an integer array.
     */
    @Test
    @DisplayName("Second largest number from a list")
    void _12_secondLargestNumber(){
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer secondLarge = nums.stream()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(Integer.MAX_VALUE);

        System.out.println(secondLarge);
    }


    /**
     * Sort a list of strings according to the increasing order of their length
     *
     * Write a Java 8 program to sort a given list of strings according to the increasing order of their length.
     */
    @Test
    @DisplayName("Sort list of string by descending order of their lengths")
    void _13_sortByLengthOfList(){
        List<String> list = List.of("Guts","Casca","Berserk","kentaro");
        List<String> sortedList = list.stream().sorted(Comparator.comparing(String::length, Comparator.reverseOrder())).collect(toList());
        System.out.println(sortedList);
    }

    @Test
    @DisplayName("Sum and avg of all elements in an int array")
    void _14_sumAndAverage(){
        int [] nums = {1,4,5,3,6};
        IntSummaryStatistics summaryStatistics = Arrays.stream(nums).summaryStatistics();
        System.out.println(summaryStatistics.getAverage());
        System.out.println(summaryStatistics.getSum());

        List<Integer> numsList = List.of(1,3,5,3,5,6);
        IntSummaryStatistics collect = numsList.stream().collect(summarizingInt(Integer::intValue));
        System.out.println(collect.getSum());
        System.out.println(collect.getAverage());
    }

    /**
     * Find the common elements between two arrays
     *
     * Write a Java 8 program to find the common elements between two arrays using streams.
     */
    @Test
    @DisplayName("Common elements between two array")
    void _15_commonElements(){
        List<Integer> oneToTen = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> twoToTen = List.of(2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Common elements
        System.out.println(oneToTen.stream().filter(twoToTen::contains).map(String::valueOf).collect(Collectors.joining(",", "[", "]")));

        // Uncommon elements
        oneToTen.stream().filter(Predicate.not(twoToTen::contains)).collect(toList()).forEach(System.out::println);
    }

    /**
     * Reverse each word of a string using Java 8 streams
     *
     * Write a Java 8 program to reverse each word of a given string using the stream API and lambda expressions
     */
    @Test
    @DisplayName("Reverse each word in a string")
    void _16_reverseEachWord(){
        String str = "Hello my name is Rajan";

        String reversedStr = Arrays.stream(str.split(" ")).map(s -> new StringBuffer(s).reverse())
                .collect(Collectors.joining(" "));
        System.out.println(reversedStr);
    }

    @Test
    @DisplayName("Sum of first n natural numbers")
    void _17_sumOfFirstNNaturalNumbers(){
        int n = 10;
        System.out.println(IntStream.rangeClosed(1, n).sum());
    }

    @Test
    @DisplayName("Reverse an integer array")
    void _18_reverseIntArray(){
        int [] numberArray =new int[]{1,2,3,4,5,6,7,8,9,10};

        List<Integer> arrayList = Arrays.stream(numberArray).boxed().collect(toList());
        Collections.reverse(arrayList);
        System.out.println(Arrays.toString(arrayList.toArray()));
    }

    /**
     * Find the most repeated element in an array
     *
     * Write a Java 8 program to find the most repeated element in an array.
     *
     * Usages - Collectors.collectingAndThen(Collector<T,A,R> downstream,
     *                                                                 Function<R,RR> finisher)
     *
     */
    @Test
    @DisplayName("Most repeated element in array")
    void _19_mostRepeatedElement(){
        int[] elements = new int[]{2, 3, 1, 4, 4, 1, 4, 4, 4, 4, 4, 333, 3, 333, 2, 2, 2, 5, 222};

        Map.Entry<Integer, Long> integerLongEntry = Arrays.stream(elements).boxed()
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream().peek(System.out::println)
                .sorted((x, y) -> Math.toIntExact(y.getValue() - x.getValue())).findFirst().orElse(null);
        System.out.println(integerLongEntry.getKey());

        // Another way

        Function<Map<Integer,Long>, Integer> maxValuesKeyFunction = integerLongMap ->
                integerLongMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(Integer.MAX_VALUE);

/*        Integer maxDuplicateKey = Arrays.stream(elements)
                .boxed()
                .collect(collectingAndThen(groupingBy(Function.identity(), counting()),
                        integerLongMap ->
                        integerLongMap.entrySet().stream().max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey).orElse(Integer.MAX_VALUE)
                        ));
        */
        Integer maxDuplicateKey = Arrays.stream(elements)
                        .boxed().collect(collectingAndThen(groupingBy(Function.identity(), counting()), maxValuesKeyFunction));

        System.out.println(maxDuplicateKey);
    }

    @Test
    @DisplayName("Check if string is palindrome")
    void _20_isStringPalindrome(){
        String str = "RajaR";
        // using char array
        char [] charArray = str.toCharArray();
        int n = charArray.length;
        boolean isPalindrome = true;
        for(int i=0,j=n-1;i<=j;i++,j--){
            if(charArray[i] != charArray[j]){
                isPalindrome = false;
                break;
            }
        }
        System.out.println(isPalindrome);

        // Using streams
        str.replaceAll("\\s+", "").toLowerCase();
        System.out.println(IntStream.range(0, n/2).noneMatch(i -> str.charAt(i) != str.charAt(n - i - 1)));
    }


    /**
     * Find strings in a list that start with a number
     *
     * Given a list of strings, write a Java 8 program to find the strings that start with a number.
     */

    @Test
    void _21_stringsStartsWithNumber(){
        String [] words= new String[]{"rohit","foo","nemo","target1","12Target","2robot"};
        Arrays.stream(words).filter(w -> Character.isDigit(w.charAt(0))).collect(toList()).forEach(System.out::println);
    }

    /**
     * Extract duplicate elements from an array
     *
     * Write a Java 8 program to extract duplicate elements from an array.
     */
    @Test
    void _22_extractDuplicateElements(){
        List<Integer> duplicateElements = List.of(1, 2,2,2,3, 3, 4, 5,1,1,56, 7, 8, 9, 10);
        duplicateElements.stream().filter(i -> duplicateElements.indexOf(i) != duplicateElements.lastIndexOf(i))
                .distinct().sorted(Comparator.reverseOrder()).collect(toList()).forEach(System.out::println);
    }

    /**
     * Print duplicate characters in a string
     * Write a Java 8 program to print the duplicate characters in a string.
     */
    @Test
    void _23_duplicateCharactersInString(){
        String str = "0rajan0n";
        Arrays.stream(str.split("")).filter(c -> str.indexOf(c) != str.lastIndexOf(c)).distinct().sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }

    /**
     * Find the first repeated character in a string
     * Write a Java 8 program to find the first repeated character in a string.
     */
    @Test
    void _24_firstRepeatedCharacter(){
        String str = "rajAn0";
        System.out.println(Arrays.stream(str.split("")).filter(c -> str.lastIndexOf(c) != str.indexOf(c)).findFirst().orElse("NA"));
    }

    /**
     * Find the first non-repeated character in a string
     *
     * Write a Java 8 program to find the first non-repeated character in a string.
     */
    @Test
    void _25_firstNonRepeatingCharacter(){
        String str = "rajan";
        System.out.println(Arrays.stream(str.split("")).filter(c -> str.indexOf(c) == str.lastIndexOf(c)).findFirst().orElse("NA"));
    }

    /**
     * Generate the Fibonacci series
     *
     * Write a Java 8 program to generate the Fibonacci series.
     */
    @Test
    void _26_generateFibonacciSeries(){
        // Way 1
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1], t[0]+t[1]}).limit(10).map(t -> t[0]).forEach(System.out::println);

        // Way2
        List<Integer> integerList = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).limit(10)
                .flatMap(arr -> Arrays.stream(arr).boxed()).collect(toList());
        Arrays.toString(integerList.toArray());
    }

    /**
     * Print the first 10 odd numbers
     *
     * Write a Java 8 program to print the first 10 odd numbers.
     */
    @Test
    void _27_firstTenOddNumbers(){
        IntStream.iterate(1, i->i+2).limit(10).forEach(System.out::println);
    }

    /**
     * Get the last element of an array
     *
     * Write a Java 8 program using streams to get the last element of an array.
     *
     * Usgaes -     OptionalInt reduce(IntBinaryOperator op);
     */
    @Test
    void _28_lastElementInTheArrayUsingStreams(){
        int[] intArray = {0,1,2,3,4,5};
        System.out.println(Arrays.stream(intArray).boxed()
                .reduce((first,second) -> second).orElse(-1));
    }

    /**
     * Calculate the age of a person in years
     *
     * Write a Java 8 program to calculate the age of a person in years given their birthday.
     */
    @Test
    void _29_calculatePersonAgeInYear(){
        LocalDate birthDate = LocalDate.of(1999, 4, 11);
        LocalDate currentDate = LocalDate.now();
        Period timePeriod = Period.between(birthDate, currentDate);
        System.out.println(String.format("Age = %d years %d months %d days", timePeriod.getYears(), timePeriod.getMonths(), timePeriod.getDays()));
    }

    /**
     * Given list of numbers , find sum of squares of all nums
     */
    @Test
    void _30_sumSquare(){
        List<Integer> nums = Arrays.asList(1,2,3,4,5);

        System.out.println(nums.stream().map(n -> n*n).reduce(0, Integer::sum));
    }

    /**
     * List of string --> create a map where key -> string length and value is list of string of that length
     */
    @Test
    void _31_stringLengthMap(){
        List<String> words = Arrays.asList("apple", "orange", "banana", "kiwi", "pear", "pear", "1234");
        words.stream().collect(Collectors.groupingBy(String::length, Collectors.toSet())).entrySet().forEach(e -> System.out.println(e.getKey() + "--> " + e.getValue()));
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
