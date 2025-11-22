package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Employee;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class Main03112025 {
    public static void main(String[] args) {

        /**
         * Given a List<String> representing sentences,
         * write a single stream pipeline to find the top 3 most frequent words across all sentences (case-insensitive),
         * and return them as a List<String> sorted by frequency descending.
         */
        List<String> sentences = Arrays.asList(
                "Java streams are powerful",
                "Streams in Java 8 are fun",
                "Java 8 introduced streams",
                "Learning Java streams is beneficial",
                "Functional programming with Java streams"
        );

        Map<String, Long> collect = sentences.stream()
                .map(word-> word.trim().toLowerCase())
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.groupingBy(word -> word,
                        Collectors.counting()
                ));
        List<String> collect1 = collect.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getValue(), Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
        System.out.println(collect1);


        /**
         * Given a List<Employee> where Employee has fields: String name, int age, double salary,
         * write a single stream pipeline to create a Map<Integer, String> where:
         *
         * Key = employee age
         * Value = a comma-separated string of employee names with that age, sorted alphabetically
         * For example, if two employees are 30 years old, their names in the map should appear as "Alice,Bob" (alphabetically).
         */

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "HR", 5000, 30),
                new Employee(2, "Bob", "IT", 4000, 25),
                new Employee(3, "Charlie", "Finance", 5500, 30),
                new Employee(4, "David", "IT", 4500, 25),
                new Employee(5, "Eve", "HR", 6000, 35),
                new Employee(6, "Frank", "Finance", 5200, 30)
        );


        Map<Integer, String> collect2 = employees.stream()
                .collect(Collectors.groupingBy(
                        employee -> employee.getAge(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                emp -> emp.stream()
                                        .sorted(Comparator.comparing(e -> e.getName()))
                                        .map(e -> e.getName())
                                        .collect(Collectors.joining(","))
                        )
                ));
        System.out.println(collect2);


        /**
         * Given a List<Employee> (with your class: id, name, department, salary, age),
         * write a single stream pipeline to create a Map<String, Double> where:

         * Key = department name
         * Value = average salary of the top 2 highest-paid employees in that department

         * Notes:

         * If a department has fewer than 2 employees, take the average of whatever employees exist.
         * The result should be a map of department → average salary of top 2 earners.
         */
        Map<String, Double> collect3 = employees.stream()
                .collect(Collectors.groupingBy(
                        emp -> emp.getDepartment(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.size() > 2 ?
                                        list.stream()
                                                .sorted(Comparator.comparing(emp -> emp.getSalary(), Comparator.reverseOrder()))
                                                .limit(2)
                                                .mapToDouble(e -> e.getSalary())
                                                .average()
                                                .orElse(0.0)
                                        :
                                        list.stream()
                                                .mapToDouble(e -> e.getSalary())
                                                .average()
                                                .orElse(0.0)
                        )
                ));

        System.out.println(collect3);


        /**
         * Given a List<Employee> (with your class: id, name, department, salary, age),
         * write a single stream pipeline to find the name of the employee with the second lowest salary in each department.

         * Return a Map<String, String> where:

         * Key = department name
         * Value = employee name with the second lowest salary

         * If a department has fewer than 2 employees, skip it in the result.
         */

        Map<String, Employee> collect4 = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.size() > 2 ?
                                        list.stream()
                                                .sorted(Comparator.comparingDouble(Employee::getSalary))
                                                .skip(1)
                                                .findFirst().orElse(null)
                                        :
                                        null
                        )
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(collect4);


        /**
         * Given a List<Employee> (with your class: id, name, department, salary, age),
         * write a single stream pipeline to produce a Map<String, Optional<Employee>> where:

         * Key = department
         * Value = employee with the highest salary under 40 years old in that department

         * Departments with no employees under 40 should be excluded from the map
         */

        List<Employee> employees1 = Arrays.asList(
                new Employee(1, "Alice", "HR", 5000, 30),
                new Employee(2, "Bob", "IT", 4000, 25),
                new Employee(3, "Charlie", "Finance", 5500, 45),
                new Employee(4, "David", "IT", 4500, 35),
                new Employee(5, "Eve", "HR", 6000, 38),
                new Employee(6, "Frank", "Finance", 5200, 28),
                new Employee(7, "Grace", "IT", 4700, 42)
        );
        Map<String, Optional<Employee>> collect5 = employees1.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .filter(employee -> employee.getAge() < 40)
                                        .sorted(Comparator.comparing(emp -> emp.getSalary(), Comparator.reverseOrder()))
                                        .findFirst()

                        )
                ));
        System.out.println(collect5);

    }
}
