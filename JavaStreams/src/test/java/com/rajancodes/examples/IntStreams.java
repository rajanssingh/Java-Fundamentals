package com.rajancodes.examples;


import com.rajancodes.beans.Person;
import com.rajancodes.mockdata.MockData;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IntStreams {

    @Test
    public void range() throws Exception {
    }

    // Loop through people using IntStream
    @Test
    public void rangeIteratingLists() throws Exception {
        List<Person> people = MockData.getPeople();
    }

    @Test
    public void intStreamIterate()  {
    }
}
