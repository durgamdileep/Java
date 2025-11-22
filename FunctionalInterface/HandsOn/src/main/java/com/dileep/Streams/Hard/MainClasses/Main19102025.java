package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Employee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main19102025 {
    public static  void main(String[] args){


        List<Employee> employees = List.of(
                new Employee(1, "Alice", "Engineering", 120000, 30),
                new Employee(2, "Bob", "HR", 130000, 35),
                new Employee(3, "Charlie", "Engineering", 125000, 32),
                new Employee(4, "David", "Engineering", 115000, 28),
                new Employee(5, "Eve", "Engineering", 120000, 40),
                new Employee(6, "Frank", "Engineering", 135000, 33)
        );


        /**
         * Group employees by department, and for each department, find the name of the youngest employee.

         * Return a Map<String, String> — where the key is department name, and the value is the name of the youngest employee in that department.
         */
        employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                        Collectors.minBy(Comparator.comparing(Employee::getAge)),
                                e->e.map(Employee::getName).orElse("No Employee Exist")
                        )))
                .forEach((k,v)->System.out.println(k+" : "+ v));


        /**
         * Find the average salary of the oldest employee in each department.

         * Return a Map<String, Double> where:

         * Key = department name
         * Value = average salary of oldest employees in that department (in case multiple employees share the same max age).
         */
        Map<String, Double> collect = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(
                                        e -> e.getAge(),
                                        () -> new TreeMap<Integer, List<Employee>>(),
                                        Collectors.toList()
                                ),
                                entry -> entry.lastEntry().getValue().stream()
                                        .collect(Collectors.averagingDouble(
                                                Employee::getSalary
                                        ))
                        )
                ));

        System.out.println(collect);

        /**
         * You have a List<Employee> and want to find the department(s) with the highest average salary among employees older than 30, and for each such department, list the names of those employees.

         * Write a single stream pipeline (or as concise as possible) to produce a Map<String, List<String>> mapping from department name → list of employee names, but only for the department(s) that have the highest average salary among employees aged > 30.
         */

        Map<String, List<String>> collect1 = employees.stream()
                .filter(emp -> emp.getAge() > 30)
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> Map.entry(
                                                list.stream().mapToDouble(Employee::getSalary).average().orElse(0.0),
                                                list.stream().map(Employee::getName).collect(Collectors.toList())
                                        )
                                )
                        ),
                        map -> {
                            double avgMaxSalary = map.values().stream()
                                    .mapToDouble(Map.Entry::getKey)
                                    .max()
                                    .orElse(0.0);
                            return map.entrySet().stream()
                                    .filter(entry -> entry.getValue().getKey() == avgMaxSalary)
                                    .collect(Collectors.toMap(
                                            entry-> entry.getKey(),
                                            entry -> entry.getValue().getValue()
                                    ));


                        }

                ));
        System.out.println(collect1);


    }
}
