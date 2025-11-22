package com.dileep.Streams.Hard.MainClasses;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main18102025 {

    public static  void main(String[] args){


        /**
         *  You are given a List<String> where each string represents a mathematical expression containing only positive integers, addition (+), and multiplication (*) operators (e.g., "2+3*4").

         * Your task is to evaluate each expression following standard operator precedence (* before +) and then:

         * Group the expressions by their resulting value.
         * From each group, select the lexicographically smallest expression.
         * Return a Map<Integer, String> where each key is the evaluated result and the value is the corresponding smallest expression.
         * You must solve this using Java Stream API only (no external libraries, no for-loops, no manual sorting, etc.).
         */
        List<String> expressions = List.of(
                "2+3*4",     // 14
                "3*4+2",     // 14
                "10+2*3",    // 16
                "1+1+1+1",   // 4
                "2*2*2",     // 8
                "4+4",       // 8
                "5+5+5",     // 15
                "7+7+1",     // 15
                "8+8",       // 16
                "20"         // 20
        );


        Map<Integer, String> aNull = expressions.stream()
                .collect(Collectors.groupingBy(exp -> evaluate(exp),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                exprs -> exprs.stream().min(Comparator.naturalOrder()).orElse("null")
                        )));
        System.out.println(aNull);


    }
    static int evaluate(String expr) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char lastOp = '+'; // Assume the first operator is '+'

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            }

            // If current char is an operator or end of expression
            if (!Character.isDigit(ch) || i == expr.length() - 1) {
                switch (lastOp) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num); // Integer division
                        break;
                }

                lastOp = ch;
                num = 0;
            }
        }

        // Sum the result from the stack
        int result = 0;
        for (int n : stack) {
            result += n;
        }

        return result;
    }

}
