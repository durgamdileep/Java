package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Employee;
import com.dileep.Streams.Hard.Model.Project;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main26102025 {

    public static void main(String[] args) {

        List<Employee1> employees = Arrays.asList(
                new Employee1("Alice",   "HR",        "New York",   70000),
                new Employee1("Bob",     "Finance",   "New York",   90000),
                new Employee1("Charlie", "Finance",   "New York",   120000),
                new Employee1("David",   "IT",        "Chicago",    110000),
                new Employee1("Eve",     "IT",        "Chicago",    95000),
                new Employee1("Frank",   "HR",        "Chicago",    88000),
                new Employee1("Grace",   "Finance",   "Boston",     99000),
                new Employee1("Hank",    "Finance",   "Boston",     105000),
                new Employee1("Ivy",     "HR",        "Boston",     97000)
        );


        /**
         * Using Java Streams only (no loops), write logic to:

         * Group employees by city,
         * Within each city, group employees by department,
         * For each department, find the employee with the highest salary,
         * Return a Map<String, Map<String, Employee>>
         * → where city → department → top-paid employee.
         */
        Map<String, Map<String, Employee1>> collect = employees.stream()
                .collect(
                        Collectors.groupingBy(
                        emp -> emp.getCity(),
                        Collectors.groupingBy(
                                employee -> employee.getDepartment(),
                                Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparing(e -> e.getSalary())),
                                        em -> em.map(e -> e).orElse(null)
                                )

                        )
                ));
        System.out.println(collect);


        /**
         * Using Java Streams only, write logic to produce the following result:
         * Map<Boolean, Double> result
         * where:
         *
         * Key true → the average salary of employees earning above 100,000 across all projects.
         * Key false → the average salary of employees earning 100,000 or below.

         */

        List<Project> projects = Arrays.asList(
                new Project("Apollo", Arrays.asList(
                        new Employee(1, "Alice",   "HR",       70000, 29),
                        new Employee(2, "Bob",     "Finance",  90000, 35),
                        new Employee(3, "Charlie", "Finance", 120000, 41)
                )),
                new Project("Zeus", Arrays.asList(
                        new Employee(4, "David", "IT", 110000, 38),
                        new Employee(5, "Eve",   "IT",  95000, 32)
                )),
                new Project("Hermes", Arrays.asList(
                        new Employee(6, "Hank", "Finance", 105000, 36),
                        new Employee(7, "Ivy",  "HR",       97000, 28)
                ))
        );

        Map<Boolean, Double> collect1 = projects.stream()
                .flatMap(project -> project.getTeam().stream())
                .collect(Collectors.partitioningBy(
                        emp -> emp.getSalary() > 100000,
                        Collectors.averagingDouble(e -> e.getSalary())
                ));
        System.out.println(collect1);


    }
    static class Employee1 {
        String name;
        String department;
        String city;
        int salary;

        public Employee1(String name, String department, String city, int salary) {
            this.name = name;
            this.department = department;
            this.city = city;
            this.salary = salary;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public String getCity() { return city; }
        public int getSalary() { return salary; }

        @Override
        public String toString() {
            return name + " (" + department + ", " + city + ", $" + salary + ")";
        }
    }

}
