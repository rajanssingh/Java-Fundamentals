package com.rajancodes.examples;

import com.rajancodes.beans.Car;
import com.rajancodes.beans.Person;
import com.rajancodes.beans.PersonDTO;
import com.rajancodes.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TransformationsMapAndReduce {

    @Test
    void yourFirstTransformationWithMap() throws IOException {
        // transforming one datatype to another
        List<Person> people = MockData.getPeople();
        List<PersonDTO> dtoList = people.stream().map(person -> {
            PersonDTO personDTO = new PersonDTO(person.getId(), person.getFirstName(), person.getAge());
            return personDTO;
        }).collect(Collectors.toList());
        dtoList.forEach(System.out::println);
        // cleaner way to do the above operation
        dtoList = people.stream().map(PersonDTO::map).collect(Collectors.toList());
    }

    @Test
    void mapToDoubleAndFindAverageCarPrice() throws IOException {
        List<Car> cars = MockData.getCars();
        Double avg = cars.stream().mapToDouble(Car::getPrice).average().orElse(0);
        System.out.println("Average Car Price "+avg);
    }

    @Test
    public void reduce() {
        int[] integers = {1, 2, 3, 4, 99, 100, 121, 1302, 199};
    }
}

