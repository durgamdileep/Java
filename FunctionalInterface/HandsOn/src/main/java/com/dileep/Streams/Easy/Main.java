package com.dileep.Streams.Easy;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public  static  void main(String[] args){

        /**
         * write logic to count the frequency of each unique string in the list and
         *  collect the result in a Map<String, Long>.
         */
        List<String> items = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        Map<String, Long> collect = items.stream()
                .collect(Collectors.groupingBy((item -> item), Collectors.counting()));
        System.out.println("count the frequency of each unique string in the list: ");
        collect.forEach((item,i)-> System.out.println( item+ ": "+ i));

        System.out.println();


        /**
         * Partition the numbers into even and odd numbers
         *
         * Store the result in a Map<Boolean, List<Integer>>,
         * where:
         * true → even numbers
         * false → odd numbers
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<Boolean, List<Integer>> partition = numbers.stream()
                .collect(Collectors.partitioningBy(number -> number % 2 == 0, Collectors.toList()));
        System.out.println("Partition the numbers into even and odd numbers: ");
        System.out.println( partition.toString()+"\n");


        /**
         * write logic to:
         *
         * Find the longest word in the list , if we have same length of word then the result should be in A-Z ordering
         * Return it as an Optional<String>
         */
        List<String> words = Arrays.asList("stream", "java", "lambda", "function","viratabd", "code", "program");

        Optional<String> max1 = words.stream()
                .max(Comparator.comparingInt(i-> i.length()));
        System.out.println("Find the longest word in the list :");
        System.out.println(max1+"\n");


        /**
         * write logic to:
         *
         * Group the names by their first character (case-insensitive)
         * Collect the result into a Map<Character, List<String>>
          */
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Amanda", "Brian");
        Map<Character, List<String>> firstCharacterGroupBy = names.stream()
                .map(name -> name.trim().toLowerCase())
                .collect(Collectors.groupingBy(character -> character.charAt(0)));
        System.out.println("Group the names by their first character (case-insensitive): ");
        System.out.println(firstCharacterGroupBy.toString());

        /**
         * write logic to:
         *
         * Filter only the numbers that are greater than 5 and divisible by 3
         * Then, double those numbers
         * Collect the result into a List<Integer>
         */
        List<Integer> numbersList = Arrays.asList(2, 3, 5, 7, 10, 15, 18, 21);

        List<Integer> numberGreaterThan5AndDivisibleBy3 = numbersList.stream()
                .filter(number -> number > 5 && number % 3 == 0)
                .map(number -> number * 2)
                .collect(Collectors.toList());
        System.out.println("numbers that are greater than 5 and divisible by 3: ");
        System.out.println(numberGreaterThan5AndDivisibleBy3.toString()+"\n");


        /**
         * write logic to:
         *
         * Split each sentence into words (by spaces)
         * Flatten the result into a single list of words
         * Convert all words to lowercase
         * Remove duplicates
         * Collect the final result into a List<String>
         */
        List<String> sentences = Arrays.asList(
                "Java is powerful",
                "Streams are useful",
                "Functional programming is elegant"
        );

        List<String> stringList = sentences.stream()
                .map(sentence -> Arrays.asList(sentence.split(" ")))
                .flatMap(wordse -> wordse.stream().map(word -> word.trim().toLowerCase()))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(" Collect the final result into a List<String> :");
        System.out.println(stringList+" \n");


        /**
         * write logic to:
         *
         * Convert all valid numeric strings to integers
         * Ignore any string that can't be parsed into an integer (i.e. skip "not_a_number")
         * Compute the sum of all valid integers
         */
        List<String> numberlist = Arrays.asList("10","10a","20", "30", "40", "50", "not_a_number9", "60");

        Integer i = numberlist.stream()
                .map(number -> number.trim().toLowerCase())
                .filter(stringNumber -> stringNumber.matches("\\d+"))
                .map(stringNumber -> Integer.parseInt(stringNumber))
                .reduce((number1, number2) -> number1 + number2)
                .orElse(-1);
        System.out.println("Compute the sum of all valid integers: ");
        System.out.println(i+"\n");


        /**
         * write logic to:
         *
         * Group employees by their age
         * For each age group, find the employee with the highest salary
         * Collect the result into a Map<Integer, Optional<Employee>>
         */
        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 30, 70000),
                new Employee("Bob", 24, 48000),
                new Employee("Charlie", 28, 52000),
                new Employee("David", 30, 80000),
                new Employee("Eva", 35, 90000)
        );

        Map<Integer, Optional<Employee>> collect1 = employees.stream()
                .collect(Collectors.groupingBy(employee -> employee.getAge(),
                        Collectors.maxBy(Comparator.comparingDouble(employee -> employee.salary))));

        System.out.println(" Group employees by their age\n" +
                " For each age group, find the employee with the highest salary");
        collect1.forEach((age,employee)->
                System.out.println(age+" : "+ employee.orElse(new Employee()))
        );

        System.out.println();




    }
}
