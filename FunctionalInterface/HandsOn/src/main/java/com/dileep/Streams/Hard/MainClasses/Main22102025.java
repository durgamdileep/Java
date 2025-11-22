package com.dileep.Streams.Hard.MainClasses;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main22102025 {
    public static void main(String[] args){

        /**
         * You are given a List<String> containing words.
         * Write a Java 8 Stream-based solution to group the words into anagrams.
         * The result should be a Map<String, List<String>> where the key is the sorted characters of the word, and the value is the list of words that are anagrams of each other.
         */


        List<String> words = Arrays.asList(
                "listen", "silent", "enlist",
                "google", "gooegl",
                "rat", "tar", "art"
        );
        Map<String, List<String>> collect = words.stream()
                .collect(Collectors.groupingBy(word -> {
                            return word.chars()
                                    .sorted()
                                    .mapToObj(c -> String.valueOf((char) c))
                                    .collect(Collectors.joining());
                        }
                ));
        System.out.println(collect);

    }
}
