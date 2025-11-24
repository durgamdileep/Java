I/O and NIO

File handling (java.io, java.nio)
Streams (byte vs character)
Buffer, Channel, Path, Files APIs
Serialization and Deserialization


# 📦 Serialization & 🔄 Deserialization

## 📌 What is Serialization?

Serialization is the process of converting a Java object into a **byte stream** so it can be:

- 📁 stored in a file  
- 🗄️ stored in a database  
- 🌐 transferred over a network  
- 🔁 sent between JVMs  
- ⚡ cached or logged  

🧩 Byte streams are universal and can be understood by any system.

---

### ❓ Why can’t we send a Java object directly?

Because a Java object exists only inside the JVM, and it contains:

- 🧱 object structure  
- 🏷️ metadata  
- 🧠 memory addresses  
- 🔗 references to other objects  
- ⚙️ JVM-specific internal representation  

- Other systems do **NOT** understand Java’s internal memory format.
- Even another Java program running on a different machine or different JVM cannot access your object directly.

---

## ✔ Main Reasons We Need Serialization

### 1. 💾 Objects exist only in RAM

RAM data is temporary.  
To store or send it, we must convert it into a stable format → **byte stream**.


### 2. 🖥️ Memory structure is JVM-specific

An object in memory looks like this (conceptually):
``` java
  [Header][Metadata][Field values][References]
```
- Another system (even another JVM) cannot read this layout.

### 3. 🌍 Different systems use different programming languages

Java object → not understood by:

- 🐍 Python  
- 🌐 JavaScript  
- 🧩 C++  
- 🗄️ Databases  
- 📁 File systems  
- 🌐 Network protocols  

But **bytes are universal** ⚡

### 4. 🌐 Network communication uses bytes

Network protocols (TCP/IP, HTTP, sockets) accept only:
``` java
0s and 1s (bytes)
```
- 🚫 Not Java objects.
- 🔄 Serialization converts: `Java object → byte stream → send over network`
- ↩️ Deserialization does the reverse: `byte stream → Java object`
  
### 5. 💻 Cross-platform compatibility

A serialized object (byte stream) can be understood by:

- 🌍 different machines  
- 🧩 different operating systems  
- 🏗️ different architectures  

Java objects cannot.

---

## 🔄 What is Deserialization?

- Deserialization is the process of converting a byte stream back into a `Java object`.
- It is the `reverse of serialization`.

We use deserialization when we want to:

- 📂 read back objects stored in a file  
- 🗃️ fetch objects from a database  
- 📡 receive objects sent over a network  
- 🔧 rebuild objects from cached or logged data  

---

### ❓ Why do we need Deserialization? / Why can’t we use the byte data directly?

- Because the byte stream is just raw `0s` and `1s`.
- Other systems (and even the JVM) cannot understand what the bytes represent unless they know:

   - 🏷️ which class the object belongs to  
   - 🧩 what each byte means (field mapping)
   - 📝 what data type each field is
   - 🏗️ how to reconstruct the object structure
   - ♻️ how to restore the internal state  

- Bytes alone do not carry semantic meaning.
- Deserialization gives those `bytes meaning` by `re-forming the original Java object`.

---

## ✔ Main Reasons We Need Deserialization

### 1. 📊 Byte streams contain data but not usable structure

A byte stream is like this:
``` java
 10101001 11001010 00011100 ...
```
- This is useless to the JVM unless it knows how to interpret it.

Deserialization tells the JVM:

- 🏗️ what class to create  
- 🧱 which values go into which fields  
- 🔄 how to rebuild the object  

### 2. 🕸️ Reconstructing complex object graphs

Java objects may contain:

- 🔗 references to other objects  
- 📦 nested objects  
- 📚 lists, maps, arrays  
- 🧬 inheritance structures  

Deserialization recreates the entire object graph exactly as it was.


### 3. 🌐 Cross-system communication

If a remote system sends you serialized bytes (over network), your system must convert those bytes back into Java objects to use the data.


### 4. 🗄️ Persistence

When reading from:

  - 📁 files
  - 🗃️ databases
  - 📡 remote services  

- We get only `bytes`.
- `Java cannot use raw bytes directly`.
- `Deserialization` converts them back to objects.
  
---

# 🔑 What is `transient`?

- `transient` is a keyword in Java used to mark a variable that should `NOT be serialized`.
- When an object is serialized:
  - 🔹 normal variables (only instance variables) → saved into the byte stream
  - 🔸 transient variables → skipped / ignored  

- During deserialization, transient variables get `default values`.

---

## ❓ Why do we need transient?

We use `transient` to protect or ignore certain values during serialization.

### 📌 Reasons:

### 1. 🔐 Sensitive or confidential data

Example:

- 🔑 passwords  
- 🔢 PINs  
- 💳 bank details  

You don't want these written to files or sent over a network.

### 2. 🚫 Irrelevant data (not needed to store)

- Some fields may change or can be recalculated, so no need to save them.


### 3. 🖥️ Fields related to system resources

- Example:
  - 📁 file handles
  - 🗄️ database connections
  - 🌐 network sockets  
- These cannot be meaningfully serialized.

### 4. 🧮 Derived or computed fields

- If a variable is computed from other fields, you don't need to serialize it.

---

## 🎯 How `transient` Works in Serialization/Deserialization

### During Serialization:
- 🛑 transient variable is ignored  
- 📭 it does **NOT** go into the output byte stream  

### During Deserialization:
- ⚙️ JVM creates the object  
- 🔄 assigns **default value** to transient variable  

---

## 📝 Important Points to Remember

- 🧩 transient works only on **instance variables**

- It does not apply to:
  - 🚫 methods
  - 🚫 classes
  - 🚫 local variables
    
- ⚠️ Static variables are already **not serialized**, so marking **static + transient** together is unnecessary.

--- 


# 📝 Externalization

- ⚡ Externalization is an `advanced form of serialization`.  
- 🛠️ It allows a class to control exactly how its objects are `saved and restored`.  
- 🔄 Instead of `Java automatically saving all non-transient fields` (like in serialization),  
- ✍️ With externalization, you `manually decide what to write and read`.

---

## 🔑 Key Points

- 📦 **Requires `Externalizable` interface**  
  - Unlike `Serializable`, which is a marker interface, `Externalizable` has **two methods you must implement**.
  ``` java
    void writeExternal(ObjectOutput out) throws IOException
    void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
  ``` 
  - 🧩 These methods let you control how the object is saved and loaded.

---

### ✍️ You must implement the methods

- 💾 `writeExternal()` → decides `what data to write` to the stream  
- 📤 `readExternal()` → decides `how to read the data back`  

---

### 🚫 Transient is irrelevant

- Because you control exactly what gets written, you choose **which fields to save**.

---

### 🏗️ Default constructor is mandatory

- Externalization requires a `public no-argument constructor` to recreate the object during deserialization.

---

## 📄 Summary

- ✍️ **Externalization = manual serialization**  
- 🧩 **Implements `Externalizable`**  
- 💾 **Must implement `writeExternal` and `readExternal`**  
- 🎛️ **Gives full control over what data to save/load**  
- 🏗️ **Needs public no-arg constructor**

---
```java
import java.io.*;

// Employee class implements Externalizable
class Employee implements Externalizable {
    int id;
    String name;
    double salary;

    // Must have public no-arg constructor
    public Employee() {
        // Required for Externalizable
    }

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Decide what to save
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(name);
        // We choose NOT to save salary
    }

    // Decide how to read
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        name = (String) in.readObject();
        salary = 0.0; // default value, not serialized
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
    }
}

public class ExternalizationDemo {
    public static void main(String[] args) {
        Employee emp = new Employee(101, "Alice", 75000.0);

        // File to store object
        String filename = "employee.dat";

        // Serialization (write object)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(emp);
            System.out.println("Employee object serialized: " + emp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialization (read object)
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Employee emp2 = (Employee) ois.readObject();
            System.out.println("Employee object deserialized: " + emp2);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// Output:
// Employee object serialized: Employee [id=101, name=Alice, salary=75000.0]
// Employee object deserialized: Employee [id=101, name=Alice, salary=0.0]

```

---

## ⚖️ Differences Between Serialization and Externalization

| Feature            | Serialization             | Externalization                          |
|-------------------|---------------------------|-----------------------------------------|
| 🧩 Interface       | `Serializable`           | `Externalizable`                         |
| ⚡ Control         | Automatic                | Full manual control                      |
| 💾 Fields saved    | All non-transient        | Only fields you choose                   |
| 🏗️ Constructor     | No-arg optional          | Must have public no-arg constructor     |
| 🚀 Performance     | Slightly slower          | Faster, smaller output                   |

---
## 🏗️ Why a default no-arg constructor is needed in Externalization

When you implement `Externalizable`, Java does **NOT** automatically create an object using its normal constructors.

Instead, during deserialization:

- ⚙️ JVM creates the object first using the **public no-arg constructor**  
- ✍️ Then it calls your `readExternal()` method to populate the fields

---

### 🔹 Step-by-Step Flow

1. JVM sees: `Employee emp = (Employee) ois.readObject();`  
2. JVM needs to create an **empty object** of class `Employee`  
3. JVM calls the **public no-arg constructor** (mandatory!)  
4. JVM calls `readExternal(ObjectInput in)` → you read the fields from the stream  

---

### ⚠️ Without a public no-arg constructor:

- JVM cannot instantiate the object  
- Deserialization will **throw an exception**
- `java.io.InvalidClassException: Employee; no valid constructor`

---

## ⚖️ Key Difference vs Serializable

| Feature                | Serializable                                    | Externalizable                               |
|------------------------|-------------------------------------------------|---------------------------------------------|
| 🏗️ Constructor needed  | Not required                                    | Must have public no-arg constructor        |
| ⚙️ Object creation      | JVM creates object using `Unsafe.allocateInstance()` | JVM creates object using public no-arg constructor |

