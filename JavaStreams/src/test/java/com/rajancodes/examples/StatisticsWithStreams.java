package com.rajancodes.examples;


import com.rajancodes.beans.Car;
import com.rajancodes.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class StatisticsWithStreams {

    @Test
    public void count() throws Exception {
        List<Car> cars = MockData.getCars();
        // count number of toyota
        long count = cars.stream().filter(car -> car.getMake().equalsIgnoreCase("Toyota")).count();
        System.out.println("Total toyota - "+ count);
    }

    @Test
    public void min() throws Exception {
        List<Car> cars = MockData.getCars();
        System.out.println(cars.stream().filter(car -> car.getColor().equalsIgnoreCase("yellow")).mapToDouble(Car::getPrice).min().getAsDouble());
    }

    @Test
    public void max() throws Exception {
        List<Car> cars = MockData.getCars();
        System.out.println(cars.stream().filter(car -> car.getColor().equalsIgnoreCase("yellow")).mapToDouble(Car::getPrice).max().getAsDouble());
    }


    @Test
    public void average() throws Exception {
        List<Car> cars = MockData.getCars();
        double avgPrice = cars.stream().mapToDouble(Car::getPrice).average().orElse(0);
        System.out.println(avgPrice);
    }

    @Test
    public void sum() throws Exception {
        List<Car> cars = MockData.getCars();
        double sum =cars.stream().mapToDouble(Car::getPrice).sum();
        System.out.println("Sum - "+sum);
        BigDecimal bigDecimalSum = BigDecimal.valueOf(sum);
        System.out.println("BigDecimal Sum -"+bigDecimalSum);
    }

    @Test
    public void statistics() throws Exception {
        List<Car> cars = MockData.getCars();
        DoubleSummaryStatistics doubleSummaryStatistics = cars.stream().mapToDouble(Car::getPrice).summaryStatistics();
        System.out.println(doubleSummaryStatistics);
    }

}