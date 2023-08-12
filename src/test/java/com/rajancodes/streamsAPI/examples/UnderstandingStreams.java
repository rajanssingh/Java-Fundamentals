package com.rajancodes.streamsAPI.examples;

import com.rajancodes.streamsAPI.beans.Person;
import com.rajancodes.streamsAPI.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnderstandingStreams {

    @Test
    void collect() throws IOException {
        List<String> emails = MockData.getPeople()
                .stream()
                .map(Person::getEmail)
                .collect(
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll
                );
        /*MockData.getPeople().stream().map(Person::getEmail).collect(
                () -> new ArrayList<String>(),
                (list,element) -> list.add(element),
                (list1,list2) -> list1.addAll(list2)
        );*/
        emails.forEach(System.out::println);
    }

    @Test
    public void intermediateAndTerminalOperations_LazyInitialization() throws Exception {
        System.out.println(
                MockData.getCars()
                        .stream()
                        .filter(car -> {
                            System.out.println("filter car " + car);
                            return car.getPrice() < 10000;
                        })
                        .map(car -> {
                            System.out.println("mapping car " + car);
                            return car.getPrice();
                        })
                        .map(price -> {
                            System.out.println("mapping price " + price);
                            return price + (price * .14);
                        })
                        .collect(Collectors.toList())
        );
    }
}
