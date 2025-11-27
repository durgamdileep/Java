# 📚 Java Collections Overview

## 📋 1. List Implementations
Lists are ordered collections that allow duplicates.

### 🔹 ArrayList
- ⚡ Resizable array
- 🏎️ Fast random access (`get()`), slow insertion/deletion in middle
- 📝 Maintains insertion order
- 💻 Example use: `ArrayList<String> list = new ArrayList<>()`

### 🔹 LinkedList
- 🔗 Doubly-linked list
- 🏃 Fast insertion/deletion at any position, slower random access
- 🔄 Can also be used as Queue/Deque
- 💻 Example use: `LinkedList<Integer> list = new LinkedList<>()`

---

## 🛡️ 2. Set Implementations
Sets do not allow duplicates.

### 🔹 HashSet
- 🗃️ Uses a hash table
- ❌ No order guaranteed
- ⚡ Fast operations (O(1) average for `add`, `remove`, `contains`)

### 🔹 LinkedHashSet
- 🔄 Like HashSet but maintains insertion order

### 🔹 TreeSet
- 🌳 Implements `SortedSet`, elements sorted naturally or via comparator
- 🐢 Slower than HashSet (O(log n) operations)

---

## 🗺️ 3. Map Implementations
Maps store key-value pairs. Keys are unique.

### 🔹 HashMap
- 🗃️ Unordered map
- ⚡ Fast access (O(1) average)
- 🔑 Allows one null key

### 🔹 LinkedHashMap
- 🔄 HashMap + maintains insertion order
- 🛠️ Can also maintain access order (LRU cache)

### 🔹 TreeMap
- 🌳 Keys sorted naturally or by comparator
- 🐢 Slower (O(log n) for operations)

### 🔹 Hashtable (Legacy)
- 🔒 Thread-safe version of HashMap
- 🕰️ Synchronized; generally replaced by `ConcurrentHashMap`

---

## ⏳ 4. Queue Implementations
Queues are FIFO (first-in-first-out) structures, some allow priority ordering.

### 🔹 PriorityQueue
- 🏆 Elements ordered by natural ordering or comparator
- ❌ No null elements
- ⚠️ Not thread-safe

### 🔹 ArrayDeque
- 🔄 Double-ended queue (Deque)
- ↔️ Can add/remove from both ends
- 🏎️ Faster than LinkedList for queue operations

### 🔹 BlockingQueue / ConcurrentLinkedQueue
- 🧵 Thread-safe queues (used in concurrent programming)

---

## ↔️ 5. Deque (Double-ended Queue)
- 🔄 `Deque` interface implemented by `ArrayDeque` and `LinkedList`
- 🏗️ Can act as stack (`push()`, `pop()`) or queue (`add()`, `remove()`)

---

## 🕰️ 6. Other Legacy Collections
- 📦 **Vector:** Synchronized version of ArrayList. Rarely used now
- 📚 **Stack:** Extends Vector, used for LIFO. Replaced by Deque in modern code

---

## 🛠️ 7. Utility Classes
- 🧰 **Collections:** Contains static methods like `sort()`, `shuffle()`, `reverse()`
- 🗂️ **Arrays:** Static helper for array operations, like `asList()`

---

## 🧵 8. Concurrent Collections
- 🔒 **ConcurrentHashMap:** thread-safe alternative to HashMap
- 📝 **CopyOnWriteArrayList:** thread-safe version of ArrayList
- 🛡️ **CopyOnWriteArraySet:** thread-safe version of HashSet
- 🔄 **ConcurrentLinkedQueue:** thread-safe queue

---

## 🌟 9. Specialized Collections
- 🏷️ **EnumSet:** highly efficient Set implementation for enum types
- 🗝️ **EnumMap:** map with enum keys, very fast and memory-efficient

---

## 🧭 10. Navigable Collections
- 🧭 Extends `SortedSet` or `SortedMap` to provide extra navigation methods:
    - 🌳 **NavigableSet:** implemented by TreeSet
    - 🗺️ **NavigableMap:** implemented by TreeMap
- 🔎 Methods like `lower()`, `floor()`, `ceiling()`, `higher()`

---

## 📝 11. Other Notes
- 🔢 **Primitive wrappers:** collections work with objects, so `int → Integer`, `double → Double`
- 🔒 **Immutability:** `Collections.unmodifiableList()`, `Set.of()`, `List.of()` (Java 9+)
- 🔄 **Sorting & utilities:** `Collections.sort()`, `Collections.reverseOrder()`, `Collections.shuffle()`
