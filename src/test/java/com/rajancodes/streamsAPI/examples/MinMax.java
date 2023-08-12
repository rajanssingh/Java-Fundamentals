package com.rajancodes.streamsAPI.examples;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinMax {

    @Test
    public void min() {
        List<Integer> numbers = List.of(1, 2, 3, 100, 23, 93, 99);
        Integer minimum = numbers.stream().min((num1,num2) -> num1 > num2 ? 1 : -1)
                .get(); // as min operator returns an optional
        // can use Comparator.naturalOrder instead of defining custom comparator
        minimum = numbers.stream().min(Comparator.naturalOrder()).get();
        assertEquals(1,minimum);
    }

    @Test
    public void max() {
        List<Integer> numbers = List.of(1, 2, 3, 100, 23, 93, 99);
        Integer maximum = numbers.stream().max(Comparator.naturalOrder()).get();
        assertEquals(100,maximum);
    }
}
