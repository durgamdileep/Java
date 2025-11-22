

## 📚 Jagged Array in Java

> A jagged array in Java is a 2D array where `each row can have a different number of columns`

```
int jagged[][]= new int[4][];
jagged[0]= new int[2];
jagged[1]= new int[3];
jagged[2]=new int[1];
jagged[3]=new int[4];
/*
        jagged = [
    [10, 20],            // Row 0 → 2 elements
    [30, 40, 50],        // Row 1 → 3 elements
    [60],                // Row 2 → 1 element
    [70, 80, 90, 100]    // Row 3 → 4 elements
]

 */
```

### 🛠️ Usage Scenarios

- 🧩 Representing non-uniform grids
- 📅 Storing data like days in months (e.g., 28, 30, 31)
- 🧮 Sparse matrices

---
## 🔤 For String class:

- 🔗 `==` compares references (memory locations)
- ✅ `equals()` compares content (because String overrides equals())

## 🧑‍🤝‍🧑 For custom classes (like Person):

### 🚫 Without overriding equals():

- 🔗 `==` compares references
- 🔗 `equals()` compares references (inherited from Object)

### ✔️ With overriding equals():

- 🔗 `==` still compares references
- ✅ `equals()` compares content (based on your override)

---

## 🔤 Strings

- 🛑 Immutable — once created, the content cannot be changed.
- 🔄 Any "modification" of a String actually creates a new String object.
- 🔒 Since the `content never changes`, `multiple threads can safely read the same String` instance `without any synchronization`.
- 🤝 You can share the same String object between threads without any problems.
- 🚫 `No need for synchronization or locks` when `reading or using Strings concurrently`.
``` java
  String s1 = new String("hello");
  String s2 = "hello";
```

## 🧰 StringBuffer

- 🔄 Mutable — content can be modified without creating a new object.
- 🛡️ Thread-safe — synchronized methods ensure `only one thread can modify it at a time`.
- ⚙️ Used when you need to `modify` strings in a `multi-threaded environment`.
``` java
   StringBuffer sb= new StringBuffer("hello");
```

## ⚡ StringBuilder

- 🔄 Mutable — content can be modified.
- 🚫 Not thread-safe — no synchronization, so faster in single-threaded scenarios.
- 🏆 Preferred choice when `modifying strings` in a `single-threaded context` due to better performance.
``` java
   StringBuilder sb = new StringBuilder("hello");
```
| Class          | Mutable?       | Thread-safe?    | Use case                          |
| -------------- | -------------- | --------------- | -------------------------------- |
| 🔤 String      | ❌ No          | N/A             | 📌 Constant strings, immutable data |
| 🧰 StringBuffer | ✅ Yes         | 🛡️ Yes          | ⚙️ Multithreaded string modification  |
| ⚡ StringBuilder | ✅ Yes         | ❌ No           | 🏃‍♂️ Single-threaded string modification |

---
## AutoBoxing and Auto UnBoxing

- 📦 **Boxing** = primitive ➝ wrapper
- 🔓 **Unboxing** = wrapper ➝ primitive

---


# 🧊 Wrapper Class in Java

In Java, wrapper classes are used to wrap (encapsulate) primitive data types into objects.

## 💡 Why Use Wrapper Classes?

- 🧱 **Object required** – Many Java libraries (like collections) only work with objects, not primitive types.
- 🛠️ **Utility methods** – Wrapper classes have built-in methods
     ```
        int a= Integer.parseInt("10"); 
     ```
- ⚫ **Nullability** – Wrapper classes can be null, but primitive types cannot.
- 🔄 **Autoboxing/Unboxing** – Java automatically converts between primitive and wrapper types.

## 🔁 Immutability

Yes, wrapper class objects are immutable, meaning once created, their value cannot be changed.
   ```
     Integer a=10;
     a=20;// this creates a new Integer object, doesn't modify the existing one 
   ```

---

## 📊 Feature Comparison

| 🧩 Feature            | 🔢 Primitive Types     | 📦 Wrapper Classes            |
|----------------------|------------------------|-------------------------------|
| 📐 Data type          | Basic (int, double)    | Object (Integer, etc.)        |
| 💾 Memory efficient   | Yes                    | No (more overhead)            |
| 🚫 Null values allowed| No                     | Yes                           |
| ♻️ Mutable            | Not exactly mutable, but value can be reassigned | Immutable (value can't be changed after creation) |
| 📚 Used in Collections| No                     | Yes                           |

---

## 📝 Final Notes:

- ✅ **Primitive**: Fast, memory-efficient, can't be used where objects are required.
- ⚠️ **Wrapper**: Slower, uses more memory, but necessary in many scenarios (like collections, generics, etc.).

