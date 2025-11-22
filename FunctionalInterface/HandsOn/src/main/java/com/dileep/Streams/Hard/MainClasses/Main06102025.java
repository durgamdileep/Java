package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Employee;
import com.dileep.Streams.Hard.Model.Person;
import com.dileep.Streams.Hard.Model.Transaction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main06102025 {

    public static  String indent(int level){
        return " ".repeat(level);
    }
    public static  void main(String[] args){


        /**
         * write logic:
         *
         * to find the second highest salary employee in each department.
         * If a department has less than two employees, skip that department.
         */
        List<Employee> employees = Arrays.asList(
                new Employee(1, "Alice", "IT", 90000, 28),
                new Employee(2, "Bob", "IT", 95000, 32),
                new Employee(3, "Charlie", "IT", 87000, 26),
                new Employee(4, "David", "HR", 60000, 30),
                new Employee(5, "Eva", "HR", 75000, 29),
                new Employee(6, "Frank", "HR", 65000, 35),
                new Employee(7, "Grace", "Finance", 80000, 31),
                new Employee(8, "Hank", "Finance", 88000, 34),
                new Employee(9, "Ivy", "Finance", 77000, 27),
                new Employee(10, "Jack", "Sales", 70000, 25)
        );

        Map<String, Employee> result = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.mapping(employee -> employee, Collectors.toList()),
                                emp -> emp.size()>=2? emp.stream()
                                        .sorted(Comparator.comparing(Employee::getSalary).reversed())
                                        .skip(1)
                                        .findFirst()
                                        .orElse(null):  null
                        )
                )).entrySet().stream()
                .filter(entry-> entry.getValue()!=null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // other approach
        // or clean way

        Map<String, Employee> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() >= 2)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e1 -> e1.getValue().stream()
                                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                                .skip(1)
                                .findFirst()
                                .orElse(null)
                ));

        System.out.println("find the second highest salary employee in each department.\n" +
                "If a department has less than two employees, skip that department.");
        result.forEach((department, employee) -> {
            System.out.println(department + " : {");
            System.out.println(indent(2) + "Employee : {");
            System.out.println(indent(4) + "ID : " + employee.getId());
            System.out.println(indent(4) + "Name : " + employee.getName());
            System.out.println(indent(4) + "Salary : " + employee.getSalary());
            System.out.println(indent(4) + "Age : " + employee.getAge());
            System.out.println(indent(2) + "}");
            System.out.println("}");
            System.out.println();
        });


        /**
         * use Java Streams to
         * find the top 3 most frequent words across all sentences, ignoring case and punctuation.
         */
        List<String> sentences = List.of(
                "Java streams are powerful and useful.",
                "Streams allow functional-style operations on collections.",
                "Use streams to filter, map, and reduce data efficiently.",
                "Functional programming with streams leads to clean and concise code."
        );

        Map<String, Long> topWords = sentences.stream()
                .map(sentence -> sentence.replaceAll("[^a-zA-Z ]", "").toLowerCase())
                .flatMap(sentence -> Stream.of(sentence.split("\\s+")))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue,Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1,e2)-> e1,
                        LinkedHashMap::new // used linked hash map for maintaining the insertion order
                ));
        System.out.println("find the top 3 most frequent words across all sentences, ignoring case and punctuation.");
        topWords.forEach((word, frequency) -> {
            System.out.println(word + " : {");
            System.out.println(indent(1) + "Frequency : " + frequency);
            System.out.println("}");
            System.out.println();
        });


        /**
         * Given a Map<String, List<Integer>> where the key is a student name and the value is a list of their test scores,
         * write a Java Stream operation to return a Map<String, Double> with each student's name and
         * their average score, but only include students whose average score is above 75.
         */
        Map<String, List<Integer>> studentScores = Map.of(
                "Alice", List.of(80, 90, 85),
                "Bob", List.of(70, 65, 60),
                "Charlie", List.of(95, 88, 92),
                "Diana", List.of(76, 74, 78),
                "Ethan", List.of(100, 98, 97),
                "Fiona", List.of(50, 60, 55)
        );
        Map<String, Double> collect1 = studentScores.entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey(),
                        entry.getValue().stream()
                                .mapToInt(s -> s)
                                .average()
                                .orElse(0.0)
                )).filter(entry-> entry.getValue()>75)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



        System.out.println(collect1);


        /**
         * write a Java Stream operation to:
         *
         * Group the persons by age group (e.g., 20-29, 30-39, 40-49, etc.),
         * For each age group, calculate the total salary paid,
         * Return a Map<String, Double> where the key is the age group as a string (e.g., "20-29") and the value is the total salary of that group
         */
        

        List<Person> people = List.of(
                new Person("Alice", 25, 5000),
                new Person("Bob", 35, 7000),
                new Person("Charlie", 45, 8000),
                new Person("Diana", 28, 5200),
                new Person("Ethan", 33, 7500),
                new Person("Fiona", 41, 8200)
        );
        Map<String, Double> peopleInaRangeOfAge = people.stream()
                .collect(Collectors.groupingBy(person -> {
                    int lowerBound = (person.getAge() / 10) * 10;
                    int upperBound = lowerBound + 9;
                    return lowerBound + "-" + upperBound;
                }, Collectors.summingDouble(Person::getSalary)));
        System.out.println(peopleInaRangeOfAge);


        /**
         * Write a stream operation that returns a map of transaction types to their maximum successful transaction amount.
         */
        List<Transaction> transactions = List.of(
                new Transaction("T1", "U1", "DEPOSIT", 1000.0, "SUCCESS"),
                new Transaction("T2", "U2", "WITHDRAWAL", 500.0, "FAILED"),
                new Transaction("T3", "U3", "DEPOSIT", 1500.0, "SUCCESS"),
                new Transaction("T4", "U1", "WITHDRAWAL", 700.0, "SUCCESS"),
                new Transaction("T5", "U4", "DEPOSIT", 2000.0, "FAILED"),
                new Transaction("T6", "U2", "WITHDRAWAL", 1200.0, "SUCCESS"),
                new Transaction("T7", "U5", "TRANSFER", 300.0, "SUCCESS"),
                new Transaction("T8", "U3", "TRANSFER", 500.0, "FAILED")
        );

        Map<String, Optional<Double>> success = transactions.stream()
                .filter(transaction -> transaction.getStatus().trim().equalsIgnoreCase("success"))
                .collect(Collectors.groupingBy(Transaction::getType,
                        Collectors.mapping(
                                Transaction::getAmount,
                                Collectors.maxBy(Comparator.comparing(amount -> amount,Comparator.reverseOrder()))
                        )
                ));

        System.out.println(success);
    }
}
