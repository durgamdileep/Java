package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class Main17102025 {
    public  static  void main(String[] args){


        /**
         * Task:
         * Using Java Stream API only (no loops), perform the following:
         *
         * Extract only the digits from each string.
         * Convert the extracted digits into an integer (e.g., "abc123" → 123, "gh1i2j3" → 123).
         * Discard any string that has no digits at all (e.g., "XYZ").
         * Return a List<Integer> containing the extracted integers, sorted in descending order.
         */
        List<String> input = Arrays.asList("abc123", "456def", "789", "gh1i2j3", "XYZ", "1a2b3c");

        input.stream()
                .map(string-> string.replaceAll("[a-zA-Z]","").trim())
                .filter(s-> !s.isBlank())
                .map(Integer::parseInt)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);


        /**
         * Task:
         * Using Java Stream API only, perform the following:
         *
         * From all the sentences, extract all words (words are separated by spaces).
         * Remove any word that has less than 5 characters.
         * Convert the remaining words to uppercase.
         * Remove any duplicate words (case-insensitive — meaning "Java" and "java" should be considered the same).
         * Sort the result in alphabetical order.
         * Return the final List<String> of processed words.
         */
        List<String> sentences = Arrays.asList(
                "Java is awesome",
                "Streams are powerful",
                "Functional style coding",
                "Parallel processing is cool"
        );

        sentences.stream()
                .flatMap(strings-> Arrays.stream(strings.split(" ")))
                .map(String::trim)
                .filter(word -> word.length() >= 5)
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .forEach(System.out::println);


        /**
         * Task:
         * Using Java Stream API only:
         *
         * Group the employees by department.
         * For each department, find the highest-paid employee.
         * Return a Map<String, Employee> where the key is the department name, and the value is the employee with the highest salary in that department.
         */
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "HR", 5000, 30),
                new Employee(2, "Bob", "Engineering", 7000, 28),
                new Employee(3, "Charlie", "HR", 6000, 35),
                new Employee(4, "David", "Engineering", 8000, 40),
                new Employee(5, "Eve", "Sales", 4500, 29),
                new Employee(6, "Frank", "Sales", 7000, 33),
                new Employee(7, "Grace", "Engineering", 7000, 27),
                new Employee(8, "Helen", "HR", 5800, 42),
                new Employee(9, "Ivan", "Sales", 7000, 45)
        );

        Map<String, Employee> collect = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary)),
                                emp->emp.orElseGet(()-> null))
                ));

        /**
         * Using Java Stream API only, perform the following:

         * Group employees by department.
         * Then, within each department, group by age group:
         * "Young" → age < 30
         * "Mid" → 30 ≤ age < 40
         * "Senior" → age ≥ 40

         * So it's a Map<String, Map<String, List<Employee>>>
         * Where outer map key = department, inner map key = age group.

         * Return the resulting nested map structure.
         */

        Map<String, Map<String, List<Employee>>> collect1 = employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.getDepartment(),
                        Collectors.groupingBy(emp -> {
                            String group = "";
                            if (emp.getAge() < 30)
                                group = "Young";
                            else if (30 <= emp.getAge() && emp.getAge() < 40)
                                group = "Mid";
                            else group = "Senior";
                            return group;
                        }, Collectors.toList())
                ));

        /**
         * Task:

         * Group employees by department
         * For each department, calculate summary statistics for salaries:

         * Total salary
         * Average salary
         * Minimum salary
         * Maximum salary
         * Count of employees

         * Return a Map<String, DoubleSummaryStatistics>
         * Where:
         * Key = Department name
         * Value = DoubleSummaryStatistics for salaries of that department
         */

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summarizingDouble(Employee::getSalary)
                ))
                .forEach((k,v)->System.out.println(k+" = "+ v));

    }
}
