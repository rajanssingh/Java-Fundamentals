package com.rajancodes.examples;


import com.rajancodes.beans.Person;
import com.rajancodes.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

public class IntStreams {

    @Test
    public void range() throws Exception {
        for(int i=0;i<=10;i++){
            System.out.println(i);
        }
        IntStream.range(0,10).forEach(System.out::println);
        IntStream.rangeClosed(0,10).forEach(System.out::println);
    }

    // Loop through people using IntStream
    @Test
    public void rangeIteratingLists() throws Exception {
        List<Person> people = MockData.getPeople();
        IntStream.range(0, people.size())
                .forEach(index -> {
                    Person person = people.get(index);
                    System.out.println(person);
                });
    }

    @Test
    public void intStreamIterate()  {
        IntStream.iterate(1,operand -> operand+1)
                .filter(number -> (number&1) ==0)
                .limit(20)
                .forEach(System.out::println);
    }
}
