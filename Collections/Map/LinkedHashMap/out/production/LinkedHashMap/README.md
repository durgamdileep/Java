# 📘 LinkedHashMap in Java

`LinkedHashMap` is a subclass of `HashMap` that maintains a `doubly linked list` running through all of its entries. This allows it to preserve:

- 🔹 **Insertion order** (by default)
- 🔄 **Access order** (if configured)

---

## 🧠 Key Concepts

### ✅ Maintains Order
- **Insertion Order** (default): Entries are returned in the order they were added.
- **Access Order** (optional): Entries are reordered on access, moving the most recently accessed to the end.

### ⚙️ Structure
- Inherits from `HashMap`
- Maintains:
    - Array of buckets (for hashing and collisions)
    - Doubly linked list connecting all entries

### 🧱 Internal Node Structure:
```
before ← hash ← key ← value → after
```

## 🧬 Inherited from HashMap

LinkedHashMap inherits all features of HashMap, including:

- 🪣 Buckets (array of nodes)
- 🔗 Collision handling with chaining (linked list in each bucket)
- ⚖️ Resizing logic (based on load factor)
- 🌳 Treeification (converts bucket list to tree when collisions exceed threshold - from Java 8)


### 🔗 Doubly Linked List (from LinkedHashMap)

What makes `LinkedHashMap` special is that it keeps all entries connected in a **separate doubly linked list**.

This list is used to:

- 🔗 **Remember the order** (insertion or access).
- ↔️ **Connect entries across all buckets**, in the order you added or accessed them.


### 🧩 Concept Comparison

| 🔍 Concept                     | 🧱 What it is          | 🎯 Used for                          |
|-------------------------------|------------------------|--------------------------------------|
| 🪣 Bucket linked list          | Singly linked list     | Handling hash collisions             |
| 🔗 Doubly linked list (global) | Doubly linked list     | Maintaining insertion/access order   |


---

## 🛠️ Constructors

| 🏷️ Constructor                                            | 📝 Description                                                   |
|----------------------------------------------------------|-----------------------------------------------------------------|
| `LinkedHashMap()`                                        | ⚙️ Default capacity 16, load factor 0.75, maintains insertion order |
| `LinkedHashMap(int capacity)`                            | 🎛️ Custom capacity, load factor 0.75, insertion order           |
| `LinkedHashMap(int capacity, float loadFactor, boolean accessOrder)` | 🛠️ Fully customizable. If accessOrder is true, maintains access order (LRU-like) |

---

## 🔁 Access Order vs Insertion Order

### ➕ Insertion Order (default):

- 📥 Elements are iterated in the order they were inserted.
- 🔒 Accessing elements (`get()`) does **not** change the order.

### 🔄 Access Order (`accessOrder = true`):

- 🔁 Every call to `get()` or `put()` `moves the entry` to the `end`.
- 🔄 `Iteration order` reflects `least-recently` to `most-recently accessed`.
- ⚙️ This makes `LinkedHashMap` suitable for LRU cache implementations.

---

## 🕒 What is LRU?

**LRU** means **Least Recently Used** — it’s `a way to decide which items to remove` when a cache or storage is full.

- ➕ When you add new items but there’s no more space, you remove the item that was **used least recently** (the one `you haven't accessed for the longest time`).

---

## 🔍 Simple Example:

Imagine you have a cache that can hold **3 items**.

You add items like this:

| Step | 🛠️ Operation         | 📦 Cache Content (oldest → newest) |
|-------|---------------------|-----------------------------------|
| 1     | Add A               | A                                 |
| 2     | Add B               | A, B                              |
| 3     | Add C               | A, B, C                          |
| 4     | Access A (`get A`)   | B, C, A                          |
| 5     | Add D (cache full)   | C, A, D                          |


### 📝 Explanation:

- After step 3, cache is full with A, B, C.
- Step 4: Accessing `A` moves it to the **most recently used** position.
- Step 5: Adding `D` means we remove the **least recently used** item — which is `B` (because we used `C` and `A` more recently).

---

## 💡 Key point:

- LRU always removes the item you haven’t used for the longest time.
- `LinkedHashMap` with **access order** can be used to implement LRU easily.



---

## 🔧 Structural Modifications

| 🛠️ Modification                | ➕ Insertion Order | 🔄 Access Order                         |
|-------------------------------|-------------------|---------------------------------------|
| `put()` new key               | ✅ Yes            | ✅ Yes                                |
| `remove()` key               | ✅ Yes            | ✅ Yes                                |
| `get()` key                  | ❌ No             | ✅ Yes (affects iteration order)      |
| `put()` existing key (change value) | ❌ No             | ❌ No                                 |

---

## 🧾 What is `removeEldestEntry()`?

`removeEldestEntry()` is a **protected method** in `LinkedHashMap` that lets you `automatically`:

- 🗑️ **Remove the oldest entry** from the map
- ➕ **Trigger when a new entry is added**
- 📏 Typically used when the map reaches a **certain size limit**

This makes it useful for building things like an **LRU cache**, where the `least recently used items` should be `discarded automatically`.

## 🧷 By Default

``` java
 protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return false;
}
```

This means: 

- ❌ **Do NOT remove the oldest entry**.
- 📈 So normally, `LinkedHashMap` will `keep growing` as `you add more entries`.

## 🛠️ When You Customize It

- ✍️ You can override this method in a subclass to make it return **true** based on your rule.
``` java

 LinkedHashMap<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true) {
    private final static int MAX_ENTRIES=5;
    
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > MAX_ENTRIES; // Keep only the last 5 entries
    }
};

```

This Means: 
- 🔢 Keeps only the `latest n entries`.
- 🗑️ Automatically removes the **oldest** (least recently used if access order is true) when:
  - A new entry is added, and
  - The size is greater than **n**.

---

## 🎯 Use Case

- 🗂️ **LRU Cache** or limiting memory usage.

**Summary:**

- 🔄 To get the most accessed value, use `accessOrder = true`.
- 🗑️ To remove the oldest, override `removeEldestEntry()`.
