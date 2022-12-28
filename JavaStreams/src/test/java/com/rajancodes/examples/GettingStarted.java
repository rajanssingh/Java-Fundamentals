package com.rajancodes.examples;

import com.rajancodes.beans.Person;
import com.rajancodes.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GettingStarted {

    @Test
    public void imperativeApproach() throws IOException {
        // 1. Find people aged less or equal 18
        // 2. Then change implementation to find first 10 people
        List<Person> people = MockData.getPeople();
        List<Person> youngPeople = new ArrayList<>();
        final int limit = 10;
        int counter = 0;
        for(Person person: people){
            if(person.getAge() <= 18){
                youngPeople.add(person);
                counter++;
                if(counter == limit){
                    break;
                }
            }
        }
        for(Person person : youngPeople){
            System.out.println(person);
        }
    }

    @Test
    public void declarativeApproachUsingStreams() throws Exception {
        List<Person> people = MockData.getPeople();
        people.stream()
                .filter(person -> person.getAge() <= 18)
                .limit(10)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
