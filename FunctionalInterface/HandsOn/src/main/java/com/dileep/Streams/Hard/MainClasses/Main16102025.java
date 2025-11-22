package com.dileep.Streams.Hard.MainClasses;

import com.dileep.Streams.Hard.Model.Item;
import com.dileep.Streams.Hard.Model.Order;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main16102025 {
    public  static  void main(String[] args){

        List<String> logs = Arrays.asList(
                "ERROR: 500 - Internal Server Error",
                "INFO: User logged in",
                "DEBUG: 2023-10-15T12:45:30 - Session started",
                "ERROR: 404 - Not Found",
                "DEBUG: 2023-10-15T12:46:00 - Session updated"
        );

        Map<Integer, String> error = logs.stream()
                .filter(v -> v.trim().toLowerCase().startsWith("error"))
                .map(v ->
                        v.split(":")[1].trim().split("-")
                ).collect(Collectors.toMap(i -> Integer.parseInt(i[0].trim()), i -> i[1]));
        error.entrySet().stream()
                .forEach(entry->
                        System.out.println(entry.getKey()+" "+ entry.getValue()));


        /**
         * Task:
         *
         * Skip the header row
         * Convert the rest into a Map<String, Integer>
         *
         * Key = name
         * Value = score (as Integer)
         *
         * Constraints:
         *
         * Use Java Streams only
         * No loops, no external libraries
         * Assume data is always well-formed (3 elements per row)
         */
        List<List<String>> rows = Arrays.asList(
                Arrays.asList("id", "name", "score"),
                Arrays.asList("1", "Alice", "85"),
                Arrays.asList("2", "Bob", "92"),
                Arrays.asList("3", "Charlie", "78")
        );

        Map<String, Integer> collect = rows.stream()
                .skip(1)
                .map(list -> list.get(1) + ":" + list.get(2))
                .collect(Collectors.toMap(
                        i -> i.substring(0, i.indexOf(":")),
                        i -> Integer.parseInt(i.substring(i.indexOf(":") + 1).trim())
                ));

        collect.entrySet().stream()
                .forEach((entry)->System.out.println(entry.getKey()+" "+ entry.getValue()));

        // optimal solution
        Map<String, Integer> optimalSolution = rows.stream()
                .skip(1)
                .collect(Collectors.toMap(
                        row -> row.get(1),                     // name
                        row -> Integer.parseInt(row.get(2))    // score
                ));

        System.out.println(optimalSolution);

        /**
         *Use Java Streams only to create a new Map<String, Set<String>> where:
         *
         * Key = Employee name (just the last name)
         * Value = Set of departments they belong to
         */
        Map<String, List<String>> deptEmployees = Map.of(
                "HR", List.of("John Doe", "Alice Smith"),
                "Engineering", List.of("Bob Stone", "Charlie Day"),
                "Finance", List.of("Eva Green", "Frank Black", "Alice Smith")
        );

        Map<String, Set<String>> collect1 = deptEmployees.entrySet().stream()
                .flatMap(entry ->
                        entry.getValue().stream()
                                .map(name -> {
                                    String fullName = name.trim();
                                    return new AbstractMap.SimpleEntry(fullName, entry.getKey());
                                })
                )
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey().toString(), Collectors.mapping(entry -> entry.getValue().toString(), Collectors.toSet())
                ));
        System.out.println(collect1);


        /**
         * Task:
         *
         * Use Java Streams only to compute a Map<String, Double> where:
         *
         * Key = item name
         * Value = total revenue generated for that item across all orders
         * (revenue = quantity × pricePerUnit)
         */
        List<Order> orders = List.of(
                new Order("O1", "C1", List.of(
                        new Item("Apple", 10, 2.0),
                        new Item("Orange", 3, 1.5)
                ), LocalDate.of(2025, 10, 10)),

                new Order("O2", "C2", List.of(
                        new Item("Apple", 5, 2.0),
                        new Item("Banana", 7, 1.0)
                ), LocalDate.of(2025, 10, 11)),

                new Order("O3", "C3", List.of(
                        new Item("Orange", 2, 1.5),
                        new Item("Banana", 3, 1.0)
                ), LocalDate.of(2025, 10, 12))
        );


        Map<String, Double> collect2 = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(item -> item.getProductId() ,
                       Collectors.summingDouble(item-> item.getQuantity()* item.getPricePerUnit())
                ));
        System.out.println(collect2);


        /**
         * Using Java Streams only, build a nested map with the following structure:
         *
         * Map<String, Map<String, Integer>>
         *
         * where:
         *
         * The outer key is the userId
         * The inner key is the productId
         * The value is the total quantity purchased by that user for that product, only for transactions in the current year
         */
        List<Transaction> transactions = List.of(
                new Transaction("user1", "productA", 3, LocalDate.of(2025, 1, 10)),
                new Transaction("user1", "productB", 2, LocalDate.of(2025, 3, 5)),
                new Transaction("user2", "productA", 1, LocalDate.of(2025, 2, 20)),
                new Transaction("user2", "productB", 4, LocalDate.of(2024, 12, 25)),
                new Transaction("user1", "productA", 5, LocalDate.of(2025, 6, 30)),
                new Transaction("user3", "productC", 7, LocalDate.of(2025, 7, 15)),
                new Transaction("user3", "productA", 2, LocalDate.of(2023, 5, 10))
        );


        Map<String, Map<String, Integer>> collect3 = transactions.stream()
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getUserId(),
                        Collectors.groupingBy(v->v.getProductId(),
                                Collectors.filtering(t1 -> t1.getTransactionDate().getYear() == LocalDate.now().getYear(),
                                                Collectors.summingInt(i -> i.getQuantity()))
                        )
                ));
        System.out.println(collect3);

    }



    static class Transaction {
        String userId;
        String productId;
        int quantity;
        LocalDate transactionDate;

        public Transaction(String userId, String productId, int quantity, LocalDate transactionDate) {
            this.userId = userId;
            this.productId = productId;
            this.quantity = quantity;
            this.transactionDate = transactionDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public LocalDate getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "userId='" + userId + '\'' +
                    ", productId='" + productId + '\'' +
                    ", quantity=" + quantity +
                    ", transactionDate=" + transactionDate +
                    '}';
        }
    }
}
