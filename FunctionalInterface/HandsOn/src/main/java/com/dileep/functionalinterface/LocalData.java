package com.dileep.functionalinterface;

import java.util.ArrayList;
import java.util.List;

public class LocalData {
    static List<Person> personList = new ArrayList<>();

    static  {
        personList.add(new Person("Alice", 25, "New York"));
        personList.add(new Person("Bob", 30, "London"));
        personList.add(new Person("Charlie", 22, "San Francisco"));
        personList.add(new Person("Diana", 28, "Berlin"));
        personList.add(new Person("Ethan", 35, "Tokyo"));
        personList.add(new Person("Fiona", 27, "Paris"));
        personList.add(new Person("George", 40, "Toronto"));
        personList.add(new Person("Hannah", 24, "Madrid"));
        personList.add(new Person("Ivan", 32, "Dubai"));
        personList.add(new Person("Jasmine", 29, "Sydney"));
    }

    public static List<Person> getPersonList() {
        return personList;
    }
}
