# 🧠 Java Memory Areas

Java has these main memory areas (within the JVM runtime data areas):

- 🧱 **Stack Area (Java Stack)**
- 🗃️ **Heap Area**
- 🏗️ **Method Area** (Part of Metaspace since Java 8)

---

## 🧱 Stack Area (Java Stack Memory)

Stack Area is part of the JVM Runtime Memory.

Used for method execution, storing:

- 🧮 Local variables
- 🔢 Primitive values
- 🧾 Object references (not objects themselves)
- 🧭 Return addresses
- 🧰 Method call information (stack frames)
- each method has its own stack frame 
- Not directly garbage collected, cleared automatically when method returns

### 🔄 Key Characteristics:

#### 🔹 Thread-specific:

- Each thread has its own stack.
- JVM creates one stack per thread.

#### 🔹 Follows LIFO (Last-In-First-Out):

- Method calls are pushed onto the stack.
- When a method completes, its stack frame is popped off.

#### 🔹 Memory allocation is done per method call, including:

- Parameters
- Local variables
- References to heap objects


### 🚨 Exceptions:

#### ❌ StackOverflowError:

- `Thrown` when the `stack exceeds its limit` (e.g., due to infinite recursion).
- Stack size is limited and can be configured via JVM options (`-Xss`)

### 🤝 Multithreading and Stack:

- `Each thread` operates independently with `its own stack memory`.
- `One thread` `cannot` access another thread’s stack.

### 🔗 Relation to Heap Memory:

- Stack only `holds references` to objects, but actual objects live in the heap.

**Example:**  
```
  String name= new String("John"); 
```
 `name` (reference) → stored in **stack**  
 `"John"` (object) → stored in **heap**

## 🗂️ Stack Frame Contents (Per Method Call):

Each stack frame contains:

- 🧮 Method’s local variables
- 🧠 Operand stack (for intermediate calculations)
- 📘 Reference to constant pool
- 🧭 Return address (for method return)

## ⚙️ Configuration:

JVM option to set stack size:
```
   -Xss512k
   -Xss1m 
```

## 🚨 When Does StackOverflowError Occur?

### 1️⃣ Infinite or Deep Recursion

- Each recursive call pushes a new stack frame onto the stack.
- Since the recursion never ends, the stack keeps growing until the JVM can't allocate more stack memory, causing a **StackOverflowError**.
``` java

   public class Test {
        public static void recursiveCall() {
            recursiveCall(); // calls itself infinitely
        }
    
        public static void main(String[] args) {
            recursiveCall();
        }
   }
   
```
### 2️⃣ Very Deep Method Call Chain

- Even if recursion stops eventually, if there are too many nested method calls, it can still overflow the stack.
- Each call consumes stack space.
- At some large depth, the stack size limit is reached.
``` java
   public class Test {
        public static void call(int i) {
            if (i == 0) return;
            call(i - 1);
        }
    
        public static void main(String[] args) {
            call(1_000_000); // too deep
        }
    }
```

### 3️⃣ Mutual Recursion (Indirect Infinite Recursion)

- Two or more methods call each other indirectly, forming an infinite loop.
``` java
  public class Test {
        public static void methodA() {
            methodB();
        }
    
        public static void methodB() {
            methodA();
        }
    
        public static void main(String[] args) {
            methodA(); // triggers endless back-and-forth
        }
  }

```

### 4️⃣ Small Stack Size Configuration

- Stack size can  defined as -Xss
- If the JVM is started with a very small stack size (e.g., using -Xss), even a modest recursion depth might cause the error.

```
   java -Xss256k Test
   // -Xss tells stack size
   //  K tells KB
```
- This reduces the stack size per thread, so less recursion is needed to overflow it.

---


# 💡 Java Reference Types (GC-Relevant)

Java provides different types of references to objects, which determine how the Garbage Collector (GC) treats those objects. These are defined in the **java.lang.ref** package.

---

## 1️⃣ Strong Reference (default)

🔍 **What it is:**  
The usual way of referring to objects in Java.

🧹 **GC behavior:**  
- The `object is not collected/removed  by GC` `as long as a strong reference exists`.

🧰 **Usage:**  
- Most commonly used in applications.

```
  MyClass obj = new MyClass(); // strong reference
```

---

## 2️⃣ Weak Reference

🔍 **What it is:**  
- A reference that `does not prevent GC` from collecting the object.

🧹 **GC behavior:**  
- The object is eligible for GC even if a WeakReference still refers to it.
- The object can be collected anytime if there are no strong references.

🧰 **Use Case:**

- 🧩 Caches (like `WeakHashMap`)
- 🧠 Memory-sensitive structures

``` 
 WeakReference<MyClass> weakRef = new WeakReference<>(new MyClass()); 
```

---

## 3️⃣ Soft Reference

🔍 **What it is:**  
- A reference that `tries to keep the object alive` `as long as there’s enough memory`.
- A reference that keeps the object alive until memory is low.

🧹 **GC behavior:**

- `GC will only remove the object` `if memory is low.`
- Less aggressive than weak references.

🧰 **Use Case:**

- 🖼️ Caching large objects (e.g., image caches)
- 🚫 Avoiding `OutOfMemoryError` while keeping objects available if possible

```
  SoftReference<MyClass> softRef = new SoftReference<>(new MyClass());
```

---

## 4️⃣ Phantom Reference (Often missed)

🔍 **What it is:**  
- `A way to know` `when an object is about to be collected`.
- `GC only clears heap memory`. If the `object holds other resources` (files,Database connections, sockets, off-heap memory), `you must clean them manually`.
- A reference that doesn't provide access to the object; 
- used to track when an object is really about to be collected.

🧹 **GC behavior:**

- The object is eligible for GC, and only appears in the `ReferenceQueue` after finalization.
- Cannot use `get()` method to retrieve the object—it always returns `null`.

🧰 **Use Case:**

- 🧼 Cleanup operations after an object is finalized
- 🧵 Direct memory management (e.g., cleaning up native resources)

```
 PhantomReference<MyClass> phantomRef = new PhantomReference<>(new MyClass(), referenceQueue);
```
## 📥 ReferenceQueue 

- A queue that `holds references whose objects have been collected`. It helps `you know when GC removed an object`.
- ReferenceQueue  Holds only the reference (not the object)

``` java
    import java.lang.ref.*;

class FileResource {
    private String fileName;

    FileResource(String fileName) {
        this.fileName = fileName;
        System.out.println(fileName + " opened.");
    }

    void close() {
        System.out.println(fileName + " closed.");
    }
}

// Helper class to hold cleanup action
class FileResourceReference extends WeakReference<FileResource> {
    private Runnable cleanupAction;

    FileResourceReference(FileResource referent, ReferenceQueue<FileResource> queue) {
        super(referent, queue);
        // Store cleanup logic
        this.cleanupAction = referent::close;
    }

    void cleanup() {
        cleanupAction.run();
    }
}

public class ReferenceQueueExample {
    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue<FileResource> queue = new ReferenceQueue<>();

        FileResource file = new FileResource("data.txt");
        FileResourceReference fileRef = new FileResourceReference(file, queue);

        // Remove the strong reference
        file = null;

        // Suggest GC
        System.gc();
        Thread.sleep(100); // wait for GC

        // Check ReferenceQueue
        FileResourceReference collectedRef = (FileResourceReference) queue.poll();
        if (collectedRef != null) {
            collectedRef.cleanup(); // actually calls file.close()
            System.out.println("FileResource cleaned up via ReferenceQueue!");
        }
    }
}

```
``` java
    [Your Object]
      ↓
    [WeakReference<MyObject>] ← attached to → [ReferenceQueue]
      ↓
    GC clears object → puts WeakReference into ReferenceQueue
      ↓
   You poll or check ReferenceQueue
      ↓
   You take action (e.g., remove from cache, free resources)
```
- Used to get notified when weak, soft, or phantom references are enqueued after the object becomes unreachable.
- Helps perform post-GC cleanup.
- Can be used to `release external resources` (like file handles, sockets, or native memory) without using finalize().
- It's `used to know` `which reference object` (like WeakReference, SoftReference, or PhantomReference) refers to an object that `has been removed by GC`.

---


# 🧊 2. Heap Area (Java Heap Memory)

Heap is a region of memory in the JVM where all Java objects are stored.

This includes:

- 📦 Object data (fields, arrays, etc.)
- 🧮 Instance variables 
- 🏷️ final instance variables
-  Wrapper objects (like Integer, Double, etc.) are objects

## 🔁 Key Characteristics:

| 🧩 Feature               | 📋 Description                                                                 |
|-------------------------|--------------------------------------------------------------------------------|
| 🔗 Shared Memory         | Shared across all threads in the JVM                                          |
| 🧍 Object Storage        | Stores all class instances and arrays                                         |
| 🧾 Reference Location    | References to these objects are stored in the stack                          |
| 🧹 Garbage Collected     | Yes, managed automatically by the Garbage Collector (GC)                     |
| 🚫 Exception on Overflow | Throws `OutOfMemoryError` if heap is full and GC can’t reclaim space         |
| ⚙️ Configurable Size     | You can set min and max heap size using JVM flags (`-Xms`, `-Xmx`)            |


## ⚠️ Common Causes of Heap Memory Errors (Leading to OutOfMemoryError)


### 🕳️ Memory Leaks

- Objects are unintentionally kept referenced (e.g., static collections, caches, listeners).
- GC can’t reclaim these objects because they are still reachable.
- Over time, memory usage grows until heap is full.


### 🏭 Creating Too Many Objects Too Quickly

- Rapid object creation without releasing references.
- The heap fills faster than GC can clear unused objects.

### ⏳ Holding References Longer Than Needed

- For example, large data structures or caches that grow indefinitely.
- Objects stay in memory even though no longer needed.

### ⚙️ Improper JVM Heap Size Configuration

- Heap size (`-Xmx`) set too low for application needs.
- Small heap space can cause frequent OutOfMemoryError even if your app is not leaking memory.

### 📦 Large Objects or Large Number of Large Objects

- Creating very large arrays or objects (e.g., big images, files in memory).
- These consume a large chunk of heap quickly.

### 🧩 Classloader Leaks (especially in app servers)

- Classes or ClassLoader instances retained unnecessarily (common in redeployment scenarios).
- Causes permgen/metaspace or heap to fill up.

### ⏳ Long-Lived Objects Filling Old Generation

- Objects that survive many GC cycles move to Old Gen.
- If Old Gen fills, GC can’t free space efficiently, leading to heap exhaustion.

---

## 📦 Heap Memory Structure (Generational GC Model):

Java divides the heap into generations to optimize GC:

### 1️⃣ Young Generation

- 🆕 Stores newly created objects
- 🔁 Further divided into:
    - 🌱 **Eden Space:** All new objects are first allocated here.
    - ♻️ **Survivor Spaces:** `S0` and `S1` 
    - S0 -> Survivor 0
- 🧹 Uses **Minor GC** for frequent, fast collection.
- ⏩ Objects that survive multiple GC cycles move to the **Old Generation**.

### 🔄 Minor GC Flow Example 


#### 🚀 Step-by-Step Flow:

1. 🆕 **5 new objects** → go into **Eden**.

2. 🧹 **GC runs (Minor GC)**.

3. ❌ Suppose **3 are unreachable** → **collected** using **Mark-and-Sweep**.

4. ✅ **2 live objects** → moved to **Survivor Space S0** (age = 1).

5. 🆕 **now 3 more objects created** → again go to **Eden**.

6. 🔁 **Next Minor GC runs**:

    - ✅ **Eden + S0** are checked.
    - ❌ **Unreachable objects** → collected.
    - ✅ **Live objects** → moved to **Survivor Space S1**, **age increased**.

### 🔁 This cycle repeats...

📈 Once the **age of an object reaches a threshold** (e.g., **15**), it's **promoted** to the **Old Generation**.

---

### 📌 Important Note:

- Only **one of S0/S1** is used **at a time**.
- The **other is always empty**, acting as a swap space during Minor GC cycles.


### 🧹 Mark-and-Sweep Algorithm

- **Mark Phase**: Traverse and **mark all reachable** objects.
- **Sweep Phase**: **Remove unmarked** (unreachable) objects and **reclaim memory**.

Used during both **Minor GC** and **Major GC** depending on the JVM GC strategy.



---

### 2️⃣ Old Generation (Tenured Generation)

- 🧓 Stores long-lived or aged objects
- 🧹 Cleaned less frequently by **Major GC** (Full GC)

---

### 3️⃣ Metaspace / PermGen *(not part of the heap in Java 8+)*

- 🧠 Stores class metadata and static variables (see Method Area notes)

---

## 🧹 Garbage Collection (GC) Behavior in Heap:

- GC automatically reclaims memory from unreferenced objects.

🧠 **GC Algorithms:**

- ✔️ Mark-and-Sweep
- 🔄 Generational GC (default)
- ⚡ G1 GC, ZGC, Shenandoah GC *(newer, low-latency collectors)*

---

## ⚙️ Heap Size Configuration (JVM Flags):

| 🛠️ Option | 📝 Description                                     |
|-----------|----------------------------------------------------|
| `-Xms`    | Set initial heap size (allocated at JVM startup)   |
| `-Xmx`    | Set maximum heap size (maximum it can stretch to.) |
| `-Xmn`    | Set Young Generation size                          |

```
  java -Xms256m -Xmx1024m MyApp
```
---

## 📌 Other Important Notes:

- 🔒 **Thread-safe:** Objects in the heap are accessible by multiple threads, so you must manage synchronization if needed.

- 🕳️ **Heap memory leak:** Happens when objects are still referenced but no longer needed—GC won’t collect them.

- ❗ **Finalization:** Objects may have a `finalize()` method called before being GC’d, but it’s discouraged and obsolete in modern Java.
    - finalize() is a `special method` `called before an object is removed` by garbage collection. 
    - It’s like a last cleanup step. 
    - But you should avoid using it because it’s unreliable and inefficient.
    - The `object has a final opportunity to perform cleanup` `before it is permanently destroyed by GC`. 
    - Specifically, this is useful for things that aren’t automatically cleaned up by Java’s memory management:
       - Files 
       - Database connections 
       - Network sockets 
       - Native memory (allocated via JNI)
    - In other words, finalize() is for non-memory resources, because Java’s GC only handles memory — it doesn’t know about these external resources.
  
  - ### ❓ Why is `finalize()` discouraged now? / ⚠️ Why finalize() Is Not Guaranteed to Be Reliable
    Even though `finalize()` is called before GC removes an object, it has some serious limitations:
     - ⏳ **Timing is unpredictable:** You don’t know exactly when the GC will run, so `finalize()` might be delayed. 
     - 🚫 **No guarantee of execution:** If the program exits before GC runs, `finalize()` may never be called. 
     - 🐢 **Performance overhead:** Objects with `finalize()` take longer to collect because they first go into a “finalization queue.” 
     - ☠️ **Dangerous to resurrect objects:** You could accidentally make an object reachable again inside `finalize()`, causing memory leaks.
    Because of these problems, modern Java recommends using **try-with-resources** or **cleaner API** or explicit cleanup methods instead of relying on `finalize()`. In fact, `finalize()` is **deprecated since Java 9**.

  #### ⚠️ Example:
     You leave a file open, expecting `finalize()` to close it. But GC doesn’t run soon, so the file remains open longer than you want, causing resource leaks.


## ✅ What Should You Do Instead?

Use explicit resource management, like:

- 🔒 **try-with-resources** for closing files, streams, etc.
- ✋ Manual `close()` calls.
- 🧹 Using Java’s **Cleaner** or **AutoCloseable** interface for controlled cleanup.

### 1. Try-with-resources (Recommended) ✅

- 📝 try-with-resources automatically calls the `close()` method at the end of the block. 
- 📌 Any class that implements `AutoCloseable` can be used in a try-with-resources statement. 
- ⏱️ It guarantees deterministic cleanup of resources.  
- Automatically closes resources that implement `AutoCloseable` (like files, streams, database connections).
- ⚡ Guarantees cleanup immediately after use, not waiting for GC.

```java
    class MyResource implements AutoCloseable {

    public void doWork() {
        System.out.println("Working...");
    }

    @Override
    public void close() {
        System.out.println("Cleaning up resource...");
    }
}

public class Main {
    public static void main(String[] args) {
        try (MyResource resource = new MyResource()) {
            resource.doWork();
        } // automatically calls resource.close()
    }
}

```

**Pros:** ✅ Deterministic, simple, no memory leaks.

### 2. Explicit Cleanup Methods 🔧

- 🛠️ Provide a method like `close()` or `dispose()` to manually release resources.

``` java
 MyResource resource = new MyResource();
  try {
    resource.doWork();
  } finally {
    resource.close(); // Explicit cleanup
 }

```

✅ **Pros:** Clear when cleanup happens; avoids finalization delays.

## 📝 `java.lang.ref.Cleaner`

### 1. What is Cleaner? 🤔

- A modern replacement for `finalize()`
- Helps automatically clean up resources when an object is no longer needed
- Safer, faster, and predictable

### 2. How it works ⚙️

1. Create a `Cleaner` object.
2. Register the object with the `Cleaner` and provide a cleanup task (a `Runnable`).
3. When the object becomes unreachable, `Cleaner` runs the task in the background.

**Key Points:**

- ⚡ Cleaner runs automatically when object is unreachable
- 🔒 Safer than `finalize()`
- 🚫 Avoids resurrection issues and memory leaks
- ⏱️ Runs in the background, does not block main program

### When to Use 📝

- Replacing `finalize()`
- Cleaning up files, sockets, or other system resources
- Use with objects that need automatic but safe cleanup


### Pros ✅

- 🔒 Safer than `finalize()` – avoids unpredictable behavior and object resurrection problems
- ⚡ Automatic cleanup – runs tasks automatically when objects become unreachable
- ⏱️ Predictable – runs in the background, not dependent on the program exit
- 🚀 Performance-friendly – less overhead than `finalize()`; objects don’t need special GC treatment
- 🛠️ Flexible – can clean up any kind of resource: files, sockets, memory, etc.
- 🌟 Modern and supported – introduced in Java 9, actively recommended over `finalize()`

```java
import java.lang.ref.Cleaner;

class Resource {
    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    Resource() {
        cleanable = cleaner.register(this, () -> {
            System.out.println("Cleaning up resources...");
        });
    }
}

public class Main {
    public static void main(String[] args) {
        Resource r = new Resource();
        r = null; // Object is unreachable // cleanup runs automatically when r is unreachable
        System.gc(); // Suggest GC to run
    }
}

```


---


# 🧠 Method Area / Metaspace 

- The Method Area is part of the JVM memory where `class-level information` is stored. 
- In Java 8 and later, the Method Area is implemented as **Metaspace**. 
- Before Java 8, this was called **PermGen (Permanent Generation)**.


## 📦 What is Stored in the Method Area / Metaspace?

| 🗂️ Type of Data           | 📋 Description                                                        |
|---------------------------|------------------------------------------------------------------------|
| 🧾 Class Metadata          | Information about loaded classes: name, superclass, interfaces, modifiers |
| 🧮 Static Variables        | `static` fields of classes (shared across all instances)              |
| 🧷 Runtime Constant Pool   | Compile-time constants (e.g., `final` variables, literals)             |
| 🧪 Method Information      | Method names, return types, parameter types                            |
| 🧱 Method Bytecode         | JVM instructions to be executed                                        |
| 🧾 Field Information       | Names, types, and modifiers of fields                                  |
| 🧬 Type Information        | Data required for type checking and linking                            |

---

## 🧵 Metaspace vs PermGen

| 🔍 Feature              | 🗃️ PermGen (Java ≤7)  (Permanent Generation) | 🌌 Metaspace (Java 8+)                   |
|------------------------|-----------------------------------------------|------------------------------------------|
| 📍 Location             | Inside Java heap                              | Outside Java heap (native memory)        |
| 📏 Max Size             | Fixed by default, can be set                  | Grows automatically (up to system memory)|
| 🔧 Tuning Option        | `-XX:MaxPermSize`                             | `-XX:MaxMetaspaceSize`                   |
| 💥 OOM Type             | `OutOfMemoryError: PermGen`                   | `OutOfMemoryError: Metaspace`            |

---

## 🚨 When `OutOfMemoryError: Metaspace` Occurs

Happens when:

- 📈 Too many classes are loaded dynamically (e.g., in web applications)
- 🔁 Classloaders are not garbage collected (common during redeployment in app servers)
- 🚫 Metaspace hits its maximum size (`-XX:MaxMetaspaceSize`)

---

## ⚙️ JVM Tuning Options for Metaspace (Java 8+)

| 🛠️ Option                           | 📋 Description                                       |
|-------------------------------------|------------------------------------------------------|
| `-XX:MetaspaceSize`                 | Initial size of Metaspace                           |
| `-XX:MaxMetaspaceSize`              | Maximum allowed size of Metaspace                   |
| `-XX:+UseCompressedClassPointers`   | Reduces memory used for class pointers              |


> 📌 **Note:**  
> If you don’t set `MaxMetaspaceSize`, it grows until system memory is exhausted.

## 🔁 String Pool Location 

### 🕰️ Before Java 7:
- String pool was part of the **PermGen**.
- Because PermGen is present inside Heap are

### 🆕 Java 7 onwards:
- String pool moved to the **heap**, improving memory usage and flexibility.

---

JVM internals details  
Class Unloading?  
ClassLoader Relation?  
Tuning Flags? in three areas Metaspace(Method area), stack area, heap area


Object Lifecycle and Allocation → under Heap Deep Dive
Minor GC vs Major (Full) GC → under Heap Deep Dive
GC Phases: Mark, Sweep, Compact, Copy → under Heap Deep Dive
GC Roots and Reachability Analysis → under Heap Deep Dive
TLABs (Thread-Local Allocation Buffers) → under Heap Deep Dive
Escape Analysis & Stack Allocation Optimization → under Heap Deep Dive


Class Loading & ClassLoader Memory Management → under Method Area / Metaspace


Code Cache / JIT Compilation Area → under JVM Tuning & Tools
Native / Off-Heap Memory → under JVM Tuning & Tools
Memory Monitoring & Analysis Tools (VisualVM, JConsole, MAT, JMC) → under JVM Tuning & Tools