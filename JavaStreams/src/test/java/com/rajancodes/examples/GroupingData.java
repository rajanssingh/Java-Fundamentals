package com.rajancodes.examples;


import com.google.gson.internal.bind.util.ISO8601Utils;
import com.rajancodes.beans.Car;
import com.rajancodes.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GroupingData {

    @Test
    public void simpleGrouping() throws Exception {
        List<Car> carList = MockData.getCars();
        Map<String,List<Car>> groupedData = carList.stream().collect(Collectors.groupingBy(Car::getMake));
        groupedData.forEach((make,cars) -> {
            System.out.println(make);
            cars.forEach(System.out::println);
        });
    }

    @Test
    public void groupingAndCounting() throws Exception {
        List<String> names = List.of(
                "John",
                "John",
                "Mariam",
                "Alex",
                "Mohammado",
                "Mohammado",
                "Vincent",
                "Alex",
                "Alex"
        );
        names.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((name,count) -> {
                    System.out.println(name + " > " + count);
                });
    }

}