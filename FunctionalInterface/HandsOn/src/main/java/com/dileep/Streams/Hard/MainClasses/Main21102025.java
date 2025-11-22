package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Employee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main21102025 {
    public static  void main(String[] args){


        /**
         * Using Java 8 Streams, write logic to:

         * Group employees by department
         * For each department, group by age bracket (e.g., "20-29", "30-39", "40-49", etc.)
         * For each age bracket in a department, find the employee with the highest salary
         */
        List<Employee> employees = Arrays.asList(
                new Employee(1,"Alice", "Engineering", 90000, 28),
                new Employee(2,"Bob", "Engineering", 85000, 32),
                new Employee(3,"Charlie", "Engineering", 120000, 42),
                new Employee(4,"David", "HR", 70000, 25),
                new Employee(5,"Eve", "HR", 95000, 35),
                new Employee(6,"Frank", "HR", 80000, 45),
                new Employee(7,"Grace", "Sales", 75000, 31),
                new Employee(8,"Heidi", "Sales", 88000, 29),
                new Employee(9,"Ivan", "Sales", 97000, 39),
                new Employee(10,"Judy", "Sales", 91000, 41)
        );

        Map<String, Map<String, Optional<Employee>>> collect = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.groupingBy(emp -> {
                            int lowerBound = (emp.getAge() / 10) * 10;
                            int upperBound = lowerBound + 9;
                            return lowerBound + " - " + upperBound;
                        }, Collectors.maxBy(Comparator.comparing(Employee::getSalary)))
                ));


        /**
         * You are given a list of strings representing log entries in the format:
         * "<timestamp>-<userId>-<activity>",
         * e.g., "2025-10-20T10:15:30-user123-LOGIN".

         * Write a Java 8 stream pipeline that:

         * Groups the log entries by userId.
         * For each user, sorts their activities by timestamp.
         * Then collects the result into a Map<String, List<String>> where key = userId and value = list of activities sorted by timestamp (excluding the timestamp itself).
         */

        List<String> logs = Arrays.asList(
                "2025-10-20T10:15:30-user123-LOGIN",
                "2025-10-20T10:17:00-user123-VIEW",
                "2025-10-20T10:16:00-user456-LOGIN",
                "2025-10-20T10:18:00-user123-LOGOUT",
                "2025-10-20T10:17:30-user456-VIEW"
        );


        Map<String, List<String>> collect1 = logs.stream()
                .collect(Collectors.groupingBy(
                        log -> {
                            String temp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                            String demo = log.substring(0, temp.length() + 1);
                            String required = log.substring(demo.length());
                            return Arrays.stream(required.split("-")).findFirst().orElse("");
                        },
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                l -> l.stream()
                                        .sorted(Comparator.naturalOrder())
                                        .map(value -> {
                                            String temp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                                            String demo = value.substring(0, temp.length() + 1);
                                            String required = value.substring(demo.length());
                                            return Arrays.stream(required.split("-", 2)).skip(1).findFirst().orElse("");
                                        })
                                        .collect(Collectors.toList())
                        )
                ));
        System.out.println(collect1);

        // optimal solution
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Map<String, List<String>> result = logs.stream()
                .map(log -> {
                    String[] firstSplit = log.split("(?<=\\d{2}:\\d{2}:\\d{2})-", 2);
                    String timestamp = firstSplit[0];
                    String[] secondSplit = firstSplit[1].split("-", 2);
                    String userId = secondSplit[0];
                    String activity = secondSplit[1];
                    return new AbstractMap.SimpleEntry<>(userId, new AbstractMap.SimpleEntry<>(LocalDateTime.parse(timestamp, formatter), activity));
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.collectingAndThen(
                                Collectors.mapping(Map.Entry::getValue, Collectors.toList()),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(Map.Entry::getKey)) // sort by timestamp
                                        .map(Map.Entry::getValue) // get activity
                                        .collect(Collectors.toList())
                        )
                ));

        System.out.println(result);



    }
}
