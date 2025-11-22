package com.dileep.Streams.Hard.MainClasses;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main08102025 {
    public  static  void main(String[] args){


        /**
         * Given a List<String> of file paths (e.g., /user/docs/file.txt),
         * write a Java 8 Stream-based solution to
         * find the deepest common directory among all the paths.
         */

        List<String> paths = Arrays.asList(
                "/user/docs/reports/2021/summary.docx",
                "/user/docs/reports/2021/financials.xlsx",
                "/user/docs/reports/2021/january/budget.pdf",
                "/user/docs/reports/2020/overview.txt",
                "/user/docs/presentations/2021/q1.pptx",
                "/user/images/profile.jpg"
        );

        List<String> collect = paths.stream()
                .flatMap(path -> Arrays.stream(path.trim().split("/")))
                .collect(Collectors.groupingBy(String::trim, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(entry-> entry.getKey().trim()).collect(Collectors.toList());


        System.out.println(collect);



    }
}
