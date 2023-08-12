package com.rajancodes.streamsAPI.examples;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class JoiningStrings {

    @Test
    public void joiningStrings() throws Exception {
        List<String> names = List.of("anna", "john", "marcos", "helena", "yasmin");
        // "Anna, John, Marcos, Helena, Yasmin"
        String allName = "";
        for(String name:names){
            allName += name + ", ";
        }
        System.out.println(allName.substring(0,allName.length()-2));
    }

    @Test
    public void joiningStringsWithStream() throws Exception {
        List<String> names = List.of("anna", "john", "marcos", "helena", "yasmin");
        // "Anna, John, Marcos, Helena, Yasmin"
        System.out.println(names.stream().collect(Collectors.joining()));

        System.out.println(names.stream().collect(Collectors.joining(",")));
        System.out.println(names.stream().map(String::toUpperCase).collect(Collectors.joining("|")));

    }

}
