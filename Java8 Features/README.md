Java 8+ Features

## 🕒 LocalDate / LocalTime / Instant API (java.time)

### 📜 What it is:

- Before Java 8, we used **Date** and **Calendar**. They were confusing, `mutable`, and error-prone.
- Java 8 introduced the `java.time` package for better date and time handling.

### 🔑 Key Classes and Their Uses:

- 📅 **LocalDate** – Only date (year, month, day). No time.
    - ➕ Add/Subtract days, months, years
    - 📆 Get day of week, month, year
    - 📋 Compare dates

- ⏰ **LocalTime** – Only time (hour, minute, second, nanosecond). No date.
    - ➕ Add/Subtract hours, minutes, seconds
    - ⏱️ Measure durations between times
    - 📋 Compare times

- 📆 **LocalDateTime** – Date + Time together.
    - ➕ Manipulate both date and time
    - 📌 Useful for scheduling events
    - 📋 Compare date-times

- 🕓 **Instant** – A timestamp, usually in UTC.
    - 🌍 Represent a point on the global timeline
    - ⏳ Measure elapsed time
    - 🔄 Convert to/from LocalDateTime in a specific timezone


### ✅ Why it’s useful:

- 🛡️ **Immutable → thread-safe**
- 👀 **Easy to read and use**
- 🔄 **Can manipulate dates easily** (add days, months, etc.)

---

### 🖋 Formatting LocalTime / LocalDate / LocalDateTime

To format or parse a `LocalTime` / `LocalDateTime` / `LocalDate`, you use **DateTimeFormatter**:

- 🔹 Convert a `LocalTime` / `LocalDate` / `LocalDateTime` to a **String**
- 🔹 Parse a **String** back into a `LocalTime` / `LocalDate` / `LocalDateTime`

**Options:**

- 🏷️ Predefined Constants
  ``` java
  
     DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
     String formattedTime = time.format(formatter);
     System.out.println(formattedTime); // 13:45:30

  ```
- ✏️ Custom Pattern
  ``` java
     DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("hh:mm a");
     String formattedTime = time.format(customFormatter);
     System.out.println(formattedTime); // 01:45 PM

  ```
- 📥 Parsing a String
  ``` java
    String str = "07:30:45";
    LocalTime parsedTime = LocalTime.parse(str, DateTimeFormatter.ISO_LOCAL_TIME);
    System.out.println(parsedTime); // 07:30:45

  ```


### 📌 Useful DateTimeFormatter Methods

| Method | Description |
|--------|-------------|
| 🛠️ `ofPattern(String pattern)` | Creates a formatter with a custom pattern, e.g., `"HH:mm:ss"` or `"yyyy-MM-dd"` |
| 🌐 `ISO_LOCAL_TIME` | Standard ISO format for `LocalTime`, e.g., `13:45:30` |
| 🌐 `ISO_LOCAL_DATE` | Standard ISO format for `LocalDate`, e.g., `2025-11-03` |
| 🌐 `ISO_LOCAL_DATE_TIME` | Standard ISO format for `LocalDateTime`, e.g., `2025-11-03T13:45:30` |
| 🖊️ `format(TemporalAccessor temporal)` | Formats a date/time object into a **String** |
| 🔄 `parse(CharSequence text)` | Parses a **String** into a date/time object |

```java
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeExample {
    public static void main(String[] args) {
        LocalTime time = LocalTime.of(21, 15, 30);

        // Formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = time.format(formatter);
        System.out.println("Formatted Time: " + formattedTime); // 09:15 PM

        // Parsing
        String strTime = "06:45 AM";
        LocalTime parsedTime = LocalTime.parse(strTime, formatter);
        System.out.println("Parsed Time: " + parsedTime); // 06:45
    }
}

```
### 📝 Summary:

- ⏱️ **LocalTime** is for **time only**
- 📅 **LocalDate** is for **date only**
- 📆 **LocalDateTime** combines **date and time**
- 🕓 **Instant** is for **timestamps (UTC)**
- 🖋️ Use **DateTimeFormatter** for formatting and parsing
- 🌍 Supports predefined **ISO formats** and **custom patterns**

---

# 📘 Interface in Java

An `interface` in Java is a `blueprint of a class`. It defines a `contract` or a `set of rules` that implementing classes must follow.

🕰️ Originally (before Java 8), interfaces could contain only abstract methods and public static final constants.  
🚀 From Java 8 onward, more types of methods were introduced.


## 🔒 Constants

Variables in interfaces are always:

- `public`
- `static`
- `final`

``` java

✔️ All of these are the same:
    public int x = 10;
    public final int y = 10;
    public static int z = 10;
    public static final int a = 10;
    final static int b = 10;
    static final int c = 10;
    public static final int d = 10;
    int e = 10;
    
All of the above become:
    public static final int <variableName> = <value>;


    interface Test {
      int x = 10;                 // becomes public static final int x = 10
      public int y = 20;          // becomes public static final int y = 20
      static int z = 30;          // becomes public static final int z = 30
      final int a = 40;           // becomes public static final int a = 40
      public static final int b = 50;
    }
    
```

### 🔹 Interface Variables (Constants) in Java

- 💠 `1. All interface variables are constants.`  
     - They are always `public static final` and cannot be changed.
- 💾 `2. They are stored in the interface’s memory` (as static) 
     - (Since they are `static`, they belong to the interface itself).
- ⚡ `3. They must be initialized when declared`  
     - Being `final`, they need a value at the moment of declaration.
- 🔗 `4. They are accessed using the interface name:`  
Example:
``` java
InterfaceName.VARIABLE_NAME;

System.oput.println(Test.x);
```


## 📦 What an Interface Can Contain (Java 8+)

### 1. ✨ Abstract Methods
- Declared `without a body`.

### 2. 🟩 Default Methods (Java 8+)
- Contain a method body.
- Allow adding new functionality without breaking existing implementations.

### 3. 🔹 Static Methods (Java 8+)
- Have a body.
- Belong `to the interface itself`, not the implementing classes.

### 4. 🔐 Private Methods (Java 9+)
- Used only inside the interface.
- Not visible to implementing classes.

```java

interface A {

    void m1();                    // abstract method

    default void m2() {           // default method
        System.out.println("default method");
    }

    static void m3() {            // static method
        System.out.println("static method");
    }

    private void m4() {           // private method (Java 9+)
        System.out.println("private method");
    }
}

```


## 🔁 Overriding Rules

### ✔️ Default methods
- `Can be overridden`

```java

class B implements A {
    @Override
    public void m2() {
        System.out.println("overridden default method");
    }
}

```

### ❌ Static methods

- ⚠️ `Cannot be overridden in implementing classes.`
- 💡 `If you declare a method with the same signature in the class, it is method hiding, not overriding.`
- 🏷️ `The hidden method belongs to the class, not the interface.`


```java

interface A {
  static void m3() {
    System.out.println("Interface static method");
  }
}

class B implements A {
  static void m3() {  // method hiding, NOT Overriding 
      
      // If you don’t use static in the subclass, then:
      // You are creating a `new instance` method that is unrelated to the static method in the interface.
    System.out.println("Class B static method");
  }
}

public class Test {
  public static void main(String[] args) {
    A.m3();  // calls interface static method
    B.m3();  // calls class static method
  }
}


```

### ❌ 🔐 Private methods

- ❌ `Cannot be overridden.`
- 
- 🚫 `Cannot be called by implementing classes.`
- 🔒 `Can only be accessed within the interface, not by subclasses or implementing classes.`
- ⚠️ `Even with Interface reference = new ImplementingClass(), private methods cannot be called.`
-
- 👁️ `They are only visible inside the interface itself.`
-
- 🔗 `They are mainly used to share code between default or static methods within the same interface.`
```java

interface A {
    private void m4() {              // private method
        System.out.println("Private method in interface");
    }

    default void m2() {
        System.out.println("Default method calling private method:");
        m4();                        // allowed inside interface
    }
}

class B implements A {
    void test() {
        // m4(); // ❌ Not allowed here! private method cannot be accessed
    }
}

public class Test {
    public static void main(String[] args) {
        A obj = new B();
        obj.m2();   // ✅ Works, because m2() is default
        // obj.m4(); // ❌ Not allowed, private method cannot be accessed
    }
}

```

## 📝 Interface Method Conflicts in Java

### ⚠️🧩 Conflict with Default Methods
- 🧩 When two interfaces have the same default method signature, the implementing class must override it.
> Rule: Conflict must be resolved manually by overriding.
- 🔄 Inside the overridden method, you can call a specific interface’s default method using:
 ``` java
  InterfaceName.super.methodName();
 ```
```java
  interface A {
  default void hello() {
    System.out.println("Hello from A");
  }
}

interface B {
  default void hello() {
    System.out.println("Hello from B");
  }
}

class C implements A, B {
  @Override
  public void hello() {
    A.super.hello();  // calling A's default
    B.super.hello();  // calling B's default
    System.out.println("Hello from C");
  }
}

public class Test {
  public static void main(String[] args) {
    C obj = new C();
    obj.hello();
  }
}

```

### ⚡🛑 Conflict with Static Methods
- 🛑 Static methods are not inherited, so they cannot truly conflict.
- ❌ If two interfaces have static methods with the same signature, you cannot override them.
- 🏷️ You can use method hiding in the class, but you must call the interface static method using the interface name:
> 📌 Rule: Always use InterfaceName.methodName() to call interface static methods.

```java
interface A {
    static void greet() { System.out.println("Greet from A"); }
}

interface B {
    static void greet() { System.out.println("Greet from B"); }
}

class C implements A, B {
    static void greet() { System.out.println("Greet from C"); } // hiding
}

public class Test {
    public static void main(String[] args) {
        A.greet(); // Greet from A
        B.greet(); // Greet from B
        C.greet(); // Greet from C
    }
}

```


### 🔒👁️‍🗨️ Private Methods in Multiple Interfaces
- 👁️‍🗨️ No conflict can occur because private methods are not visible to implementing classes.
- 🔐 Private methods can only be called inside the same interface (from default or static methods).
- 🚫 Even if multiple interfaces have private methods with the same name, the subclass never sees them → no ambiguity.


---

Modules (Java 9)

---
## 🗂️ Java Records (Java 14+)

- 📝 **Definition:** A `Record is a special kind of class` `designed to store immutable data`.  
- 💡 It is a compact way to create classes that are `mainly used to hold immutable data` — sometimes called `data carriers` or `value objects`.
- 🚀 Introduced in Java 14 (preview), finalized in Java 16.
- 🛠️ Reduces boilerplate code for data classes by automatically generating:
    - ⚡ **Fields** (Fields are final)
    - ⚡ **Constructor**
    - ⚡ **Getters**
    - ⚡ **`equals()`, `hashCode()`, `toString()`**

```java
public record Person(String name, int age) {}
```
- Java automatically generates:
  - 🔒 `private final String name;`
  - 🔒 `private final int age;`
  - 🏗️ `Constructor Person(String name, int age)`
  - 📝 `Getter methods`: `name()`, `age()`
  - ⚡ `equals(), hashCode(), toString()`

### 🔑 Key Features

- 🔒 **Immutable by default**  
  Fields are final → cannot be changed after object creation.
  ```java
   Person p = new Person("Alice", 25);
   // p.name = "Bob"; // ❌ Compilation error
  ```

- 📝 **Accessor methods**  
  Use `name()` and `age()` instead of `getName()/getAge()`.
  ``` java
    System.out.println(p.name()); // Alice
    System.out.println(p.age());  // 25
  ```

- 🚫 **No setters**  
  Records are immutable → no `setX()` methods.

- 🛠️ **Built-in methods**  
  - `equals()`, `hashCode()`, `toString()` auto-generated.
  - Makes comparing and printing objects very convenient.
  ``` java
   Person p1 = new Person("Alice", 25);
   Person p2 = new Person("Alice", 25);

   System.out.println(p1.equals(p2)); // true
   System.out.println(p1);            // Person[name=Alice, age=25]

  ```

### ✨ Customization
1. 🏗️ **Compact Constructor**

 - ```java
      public record Person(String name, int age) {
        public Person {
         if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        }
      }
     ```
 - `public Person { ... }` is called a compact constructor.
 - 🔹 It automatically receives all fields as parameters.

2. ➕ **Adding Methods**
 - ```java
    
    public record Person(String name, int age) {
      public String greet() {
        return "Hello, my name is " + name;
      }
    }

   ```
3. ✏️ **Overriding Methods**  
    Can override `toString()`, `hashCode()`, etc.

### ⚠️ Restrictions

- ❌ Cannot extend another class (implicitly final).  
  -  In Java, every record you declare is implicitly final.  
  - 💡 That means a record is essentially a final class, even if you don’t write the `final` keyword.
- ✅ Can implement interfaces.
- 🔒 Fields are always final → no mutable fields.
- 🚫 Cannot be subclassed.
- Record `cannot extend any other class` because it `implicitly` `extends java.lang.Record`, and `Java does not support multiple inheritance` of classes. 
- Record can `implement interfaces` because `Java supports` `one extends + multiple implements`
- 📌 A record is `implicitly final`, and since `final classes cannot be extended`, a `record cannot be a superclass`.


### 🏷️ When to Use

- 📦 Classes meant to store data only (DTOs, configuration, responses)
- 🔒 Data is immutable
- ✔️ Need automatic `equals/hashCode/toString`
- 🚫 No complex behavior or inheritance required

---
## 🧱 Sealed Classes (Java 15+)

- A sealed class 🧩 restricts which classes or interfaces can extend or implement it.  
- Helps in ⚙️ `controlling inheritance` and creating 🔒 predictable hierarchies.

- Useful for:

  - 🧩 Modeling finite sets of types. 
  - 🔍 Exhaustive `switch with pattern matching`. 
  - 🛡️ Secure and maintainable API design.
  - `Safer inheritance` 
  - Makes code more predictable and maintainable


### 🗝️ Keywords

| Keyword     | Purpose                                                                 |
|--------------|--------------------------------------------------------------------------|
| 🧭 `sealed`      | Declares a class as sealed; restricts which classes can extend it.         |
| 🪶 `permits`     | Lists allowed subclasses of the sealed class.                              |
| 🚪 `non-sealed`  | Subclass can be extended freely without restriction.                        |
| 🔒 `final`       | Subclass cannot be extended further.                                       |

```java

public sealed class Shape permits Circle, Rectangle, Square { }

public final class Circle extends Shape { }      // cannot extend further
public non-sealed class Rectangle extends Shape { } // can be extended further
public final class Square extends Shape { }      // cannot extend further

```

### 📜 Rules

- 📦 `Permitted subclasses` `must be` in the `same module/package`.
- 🏷️ All subclasses must declare one of:
    - 🔒 `final`
    - 🧭 `sealed`
    - 🚪 `non-sealed`
- ✅ Only classes listed in `permits` can extend the sealed class.
- 🚫 `non-sealed` subclass ends the sealing for that branch (can be extended freely).
- 🧱 Sealed interfaces also exist.

```java

public sealed interface Animal permits Dog, Cat { } //interface

public final class Dog implements Animal { }
public non-sealed class Cat implements Animal { }

```

```java

public sealed class Vehicle permits Car, Bike { }

public final class Car extends Vehicle { }      // no subclass allowed
public non-sealed class Bike extends Vehicle { } // can be subclassed
public class MountainBike extends Bike { }      // valid

```


### 🌟 Advantages

- 🎯 **Controlled inheritance** – restricts who can extend a class.
- 🧮 **Exhaustive pattern matching** – compiler knows all subclasses.
  ``` java
     // In newer Java versions (17+), when using switch with sealed classes:
  
     Shape shape = ...;

     switch (shape) {
      case Circle c -> System.out.println("Circle");
      case Triangle t -> System.out.println("Triangle");
     }
     // Here, the compiler knows all possible subclasses and can warn if a case is missing.
  ```
- 🛡️ **Safer APIs** – prevents misuse or accidental subclassing.
- 🧠 **Helps model finite sets of types clearly.**


---
## ⚙️ Pattern Matching and Switch Expressions (Java 17+)

Traditionally, Java had `instanceof` and `switch` statements that were:

- 📝 **Verbose**
- ⚠️ **Type-unsafe** (you had to cast manually)
- 🎯 **Limited** to `int`, `String`, or `enum` in switch

---

## 🚀 Modern Java (14+ → 17+)

Modern Java brings several improvements:

- ✅ **Pattern Matching for `instanceof`**
- 🔄 **Switch Expressions**
- 🧩 **Pattern Matching for `switch`** *(Java 17–21+)*

These features make Java more **concise**, **safer**, and **expressive**.

---

### 🧠 Pattern Matching (for `instanceof`)

- old way (before 14)
``` java
  if (obj instanceof String) {
    String s = (String) obj;   // manual casting 
    System.out.println(s.toLowerCase());
}

```
- New Way (Java 14+)
``` java

Object obj = "Hello";

if (obj instanceof String s) { // s is pattern variable
    System.out.println(s.length()); // valid here
} else {
    // s is not in scope here
}

```

- Java now allows you to declare a `pattern variable` directly inside `instanceof`.

### ✨ Highlights:

- The variable is **automatically casted** if the condition is true.
- ✅ **Safer and shorter** syntax.
- 🧩 The variable (`s`) exists **only inside the true branch**.
- 🚫 Avoids confusion and variable shadowing.

### 🧩 Pattern Matching + 🔒 Sealed Classes (Java 17+)

``` java
   double area(Shape shape) {
    if (shape instanceof Circle c) return Math.PI * c.radius * c.radius;
    else if (shape instanceof Rectangle r) return r.length * r.width;
    else if (shape instanceof Square s) return s.side * s.side;
    else throw new IllegalArgumentException("Unknown shape");
}

```

---

## 🎯 Switch Expressions (Java 14+)

Switch expressions allow `switch` to:

- 🔁 **Return a value**
- 🧮 Be used as an **expression**, not just a statement
- ➡️ Support **arrow (`->`) syntax**
- ✅ Have **exhaustive cases**

- Old way
``` java
  int day = 2;
String dayType;

switch (day) {
    case 1:
    case 7:
        dayType = "Weekend";
        break;
    default:
        dayType = "Weekday";
        break;
}
System.out.println(dayType);

```

- New Switch (Expression)

``` java
  int day = 2;
String dayType = switch (day) {
    case 1, 7 -> "Weekend";
    default -> "Weekday";
};
System.out.println(dayType);

```

### ✨ Features:

- `switch` **returns a value directly**.
- `->` **syntax** (no `break` needed).
- 🧹 **Compact and less error-prone.**
- 🧱 Use `yield` for **multi-line cases**.

---

## 🧩 Pattern Matching in `switch` (Java 17 Preview → Java 21 Stable)

- This extends **pattern matching** to `switch` itself!
- You can now **switch by type**, not just by value.

``` java

  static String getShapeInfo(Shape shape) {
    return switch (shape) {
        case Circle c -> "Circle with radius " + c.radius;
        case Rectangle r -> "Rectangle " + r.length + "x" + r.width;
        case Square s -> "Square with side " + s.side;
    };
}

```

### 🚀 Benefits:

- ❌ No need for long `instanceof` chains.
- 🧮 **Compiler checks for exhaustiveness** (all subclasses handled).
- 🔒 Works perfectly with **sealed hierarchies**.

---

