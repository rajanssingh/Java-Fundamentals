package com.rajancodes.streamsAPI.examples;

import com.rajancodes.streamsAPI.beans.Car;
import com.rajancodes.streamsAPI.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Filtering {

    @Test
    public void filter() throws Exception {
        List<Car> cars = MockData.getCars();
        cars.stream().filter(carPredicate).forEach(System.out::println);
    }

    final Predicate<Car> carPredicate = car -> car.getPrice() < 10000;

    @Test
    public void dropWhile() throws Exception {
        System.out.println("using filter");
        Stream.of(2, 4, 6, 8, 9, 10, 12).filter(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));
        System.out.println();
        System.out.println("using dropWhile");
    }

    @Test
    public void takeWhile() throws Exception {
        // using filter
        System.out.println("using filter");
        Stream.of(2, 4, 6, 8, 9, 10, 12).filter(n -> n % 2 == 0)
                .forEach(n -> System.out.print(n + " "));

        System.out.println();
        System.out.println("using take while");
    }

    final Predicate<Integer> numbersLessThan10 = n -> n < 10;
    @Test
    public void findFirst() throws Exception {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(Arrays.stream(numbers).boxed().filter(numbersLessThan10).findFirst().get());
    }

    @Test
    public void findAny() throws Exception {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10};
        System.out.println(Arrays.stream(numbers).filter(n -> n < 10).findAny().getAsInt());
    }

    @Test
    public void allMatch() throws Exception {
        int[] even = {2, 4, 6, 8, 10};
    }

    @Test
    public void anyMatch() throws Exception {
        int[] evenAndOneOdd = {2, 4, 6, 8, 10, 11};
    }

}



