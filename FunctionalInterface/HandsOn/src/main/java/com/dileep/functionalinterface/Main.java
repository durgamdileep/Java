package com.dileep.functionalinterface;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class Main {
    public  static  void main(String[] args){

        /**
         *  Supplier Functional Interface
         */
        Supplier<Person> personSupplier=()->{
            return new Person("Virat",34,"Banglore");
        };
        // System.out.println(personSupplier.get());

        Person p= personSupplier.get();

        /**
         * Consumer functional Interface
         */
        Consumer<Person> personConsumer=(person)->{
            System.out.println(person);
        };
         personConsumer.accept(p);


        /**
         *  predicate Functional Interface
         */
        Predicate<Person> personPredicate=(person)->person.getAge() >= 30;;

        System.out.println("Check Whether the person age is greater than or equal 30: \n"+ personPredicate.test(p));


        /**
         *  Functional Interface
         */

        Function<Person,String> personStringFunction=(person)->{
            return  person.getName();
        };
        System.out.println(" Person Name: "+ personStringFunction.apply(p));


        CompletableFuture<Void> p1= CompletableFuture.supplyAsync(()->{
            return  p;
        }).thenApply(person -> {
            return  person.getAge();
        }).thenAccept(age->{
            System.out.println(age);
        });





        List<List<String>> javaInfo = Arrays.asList(
                Arrays.asList("java", "is", "object-oriented"),
                Arrays.asList("i", "love", "java"),
                Arrays.asList("java", "runs", "on", "JVM"),
                Arrays.asList("spring", "is", "a", "java", "framework"),
                Arrays.asList("hibernate", "is", "used", "for", "ORM"),
                Arrays.asList("java", "supports", "multithreading"),
                Arrays.asList("java", "has", "strong", "memory", "management"),
                Arrays.asList("maven", "is", "a", "build", "tool", "for", "java"),
                Arrays.asList("javafx", "is", "used", "for", "gui"),
                Arrays.asList("servlets", "are", "used", "in", "web", "development")
        );

        String answer="";
        Stream<String> news=javaInfo.stream()
                .map(innerList ->{
                  String a="";
                  for(String e: innerList)
                        a+= e+" ";
                  return a;
                });


        System.out.println(news.collect(Collectors.joining()));
      //System.out.println(r.toList().toString());
    }
}
