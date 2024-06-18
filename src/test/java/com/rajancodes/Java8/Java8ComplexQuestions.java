package com.rajancodes.Java8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Java8ComplexQuestions {
    /**
     * 1. Multi level grouping
     * Given list of transactions, group them first by year, then by month.
     * Transactions have date in yyyy-mm-dd format and an amount
     */
    @Test
    void _01_multi_level_grouping() {
        record Transaction(String date, double amount) {
        }

        List<Transaction> transactions = Arrays.asList(
                new Transaction("2022-01-05", 200.0),
                new Transaction("2022-01-15", 300.0),
                new Transaction("2022-02-10", 150.0),
                new Transaction("2023-01-20", 400.0),
                new Transaction("2023-02-25", 250.0)
        );

        Map<Integer, Map<Integer, List<Transaction>>> map =
                transactions.stream()
                        .collect(Collectors.groupingBy(t -> Integer.parseInt(t.date.substring(0, 4)),
                                Collectors.groupingBy(t -> Integer.parseInt(t.date.substring(5, 7)))));

        printConsumer.accept(map);
    }

    /**
     * 2. Sliding window average
     * Given a list of integers, compute the average of every contiguous sublist of size 'k' using streams. Return list of averages.
     */
    @Test
    void _02_slidingWindowAverages(){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int k = 3;

        List<Double> collect = IntStream.range(0, numbers.size() - k + 1)
                .mapToObj(i -> numbers.subList(i, i + k))
                .map(subList -> subList.stream().mapToInt(Integer::intValue).average().orElse(0.0))
                .collect(Collectors.toList());
        printConsumer.accept(collect);

    }

    private Consumer printConsumer = (t) -> {
        if(t instanceof Map<?,?>){
            ((Map<?, ?>) t).entrySet().forEach(c -> System.out.println(c));
        }
        else if (t instanceof List<?>) {
            ((Collection<?>) t).forEach(c -> System.out.println(c));
        } else {
            System.out.println(t);
        }
    };
}
