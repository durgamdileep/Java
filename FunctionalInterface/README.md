# ☕ Functional Interface in Java

A **functional interface** is an interface that has `only one abstract method`.  
It is used mainly with `lambda expressions` in Java.

You can mark it with `@FunctionalInterface`, but it's **optional**.

``` java
        @FunctionalInterface
        interface MyFunctionalInterface {
            void doSomething();
        }
```

## ✅ In a functional interface, we can add:

- 🧩 **Default methods**
- ⚙️ **Static methods**
- 🔐 **Private methods** (since Java 9)
- 🧱 **Methods from Object class** (like `toString()`, `equals()`, `hashCode()`)

These do **not** count as abstract methods, so they `do not violate` the rule of having `only one abstract method` in a functional interface.

``` java
@FunctionalInterface
interface MyFunctionalInterface {

    // ✅ Only one abstract method
    void performTask();

    // ✅ Default method
    default void showMessage() {
        log("Default method: showMessage()");
    }

    // ✅ Static method
    static void displayInfo() {
        System.out.println("Static method: displayInfo()");
    }

    // ✅ Private method (Since Java 9)
    private void log(String msg) {
        System.out.println("LOG: " + msg);
    }

    // ✅ Method from Object class (allowed)
    boolean equals(Object obj);
}

```


## 🚫 What happens if you use `@FunctionalInterface` and add two abstract methods?

If you mark an interface with `@FunctionalInterface` and try to add more than one abstract method, the **compiler will throw an error**.

This is because `@FunctionalInterface` tells the compiler:

> "This interface must have exactly one abstract method."

```java
@FunctionalInterface
interface MyInterface {
    void method1();   // ✅ First abstract method

    void method2();   // ❌ Second abstract method – causes compile-time error
}
// output:
// Unexpected @FunctionalInterface annotation
//MyInterface is not a functional interface

```


## 🧠 Important:

- ❗ **Only one abstract method is allowed.**
- 📌 Other method types are allowed and useful for adding extra functionality or utility.

---

# Functional Interface Implementation in Java

A functional interface in Java can be implemented in three ways:

- ⚙️ **Using a regular class**  
  You create a separate class that `implements` the interface and provides the method body.
  ``` java
     @FunctionalInterface
     interface Bird {
        void fly(String destination);
     }
     
    class Sparrow implements Bird {
        @Override
        public void fly(String destination) {
            System.out.println("Sparrow is flying to " + destination + " using implements keyword!");
        }

        public static void main(String[] args) {
            Bird bird = new Sparrow();
            bird.fly("forest");
          }
    }

  ```

- 🧩 **Using an anonymous class**  
  You create an object of the interface with an `inline (temporary) class` implementation.
  ``` java
  
  
    @FunctionalInterface
    interface Bird {
    void fly(String destination);
    }


    public class AnonymousBird {
        public static void main(String[] args) {
            Bird bird = new Bird() {
               @Override
               public void fly(String destination) {
                  System.out.println("Bird is flying to " + destination + " using anonymous class!");
               }
            };
        bird.fly("mountains");
        }
    }

  ```

- ⚡ **Using a lambda expression**  
  You use a short and clean syntax to implement the method directly, without a class.


## 🔥 Lambda Expression

🧑‍💻 **Lambda Expression** is a way to implement a functional interface `without writing a separate class` or `anonymous class`,  
using a short syntax like `() -> {}`.

``` java
    @FunctionalInterface
    interface Bird {
        void fly(String destination);
    }
    
    public class LambdaBird {
    public static void main(String[] args) {
        Bird bird = (destination) -> System.out.println("Bird is flying to " + destination + " using lambda expression!");
        bird.fly("lake");
      }
    }

```

---

# 📘 Functional Interfaces in Java

Java provides several built-in **functional interfaces** in the `java.util.function` package. Here are the most commonly used ones:

## 1. 🧾 Consumer<T>

- 👉 Takes `one input` of type `T`
- 🚫 Returns `nothing (void)`
- ⚙️ Used when you want to perform an operation on the input without returning anything

### ✅ Use Case:
📌 Use when you want to perform an action (like printing, updating, or storing) on a value but don't need to return a result

``` java
   @FunctionalInterface
    interface Consumer<T> {
        void accept(T t);
    }
```
---

## 2. 🎁 Supplier<T>

- ❌ `No input parameters`
- 🎯 Returns a value of type `T`
- 🛠️ Used to supply objects or values, often lazily (when needed)

### ✅ Use Case:
📌 Use when you want to generate or supply a value, e.g., generating random numbers, current timestamps, etc.

``` java
    @FunctionalInterface
    interface Supplier<T> {
        T get();
    }

```

---

## 3. 🔍 Predicate<T>

- 👉 Takes one input of type `T`
- ✅ Returns a `boolean result`
- 🧪 Used for conditional checks or filtering

### ✅ Use Case:
📌 Use when you want to test a condition, such as checking if a string is empty or a number is positive


``` java
    @FunctionalInterface
    interface Predicate<T> {
        boolean test(T t);
    }
```
---

## 4. 🔁 Function<T, R>

- 👉 Takes one input of type `T`
- 🎯 Returns a result of type `R`
- 🔄 Used for transformation or mapping

### ✅ Use Case:
📌 Use when you need to convert one type of object to another, like converting a string to its length or parsing a string to an integer

``` java
    @FunctionalInterface
    interface Function<T, R> {
        R apply(T t);
    }
```


---



## 🌟 Advantages of Functional Interfaces

Functional interfaces in Java bring several benefits that lead to cleaner, more maintainable, and more expressive code.

- ✂️ **Less Code**  
  You don’t need to write full classes — just use lambda expressions.

- 🧼 **Cleaner Code**  
  Code becomes shorter and easier to read.

- 🔁 **Reusability**  
  You can reuse logic by passing functions as parameters.

- 🌿 **Supports Functional Programming**  
  Helps you write code in a functional style (like in modern languages).

- 📊 **Used in Streams & Collections**  
  Makes working with Java Streams, filters, maps, etc. much easier.

- 🧩 **Custom Behavior**  
  You can pass logic like data, making your code more flexible.

---
