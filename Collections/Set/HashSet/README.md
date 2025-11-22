# 📚 HashSet in Java

🌟 **What is a HashSet?**

- A HashSet is a collection that stores `only unique values`.  
- It `does not maintain order` (`no insertion order`).  
- It is `backed by a HashMap` (used internally to store the data).

---

✅ **Why use HashSet?**

Because:
- 🛑 It automatically avoids duplicates.
- ⚡ It's fast for add, remove, and contains operations (almost constant time on average).

---

🔍 **How is HashSet backed by HashMap?**

Internally, when you add a value to a HashSet, it is stored as a key in a HashMap.  
The value in the map is a dummy object (`PRESENT`), just to satisfy the Map's key-value structure.


Under the hood, when you create a `HashSet`, it internally creates a `HashMap`.

`Set<K> st= new HashSet<>();`

### 🔧 Internals :

```java
public class HashSet<E> implements Set<E> {
    
    private transient HashMap<E, Object> map;

    // Dummy value to associate with each key in the map
    private static final Object PRESENT = new Object();

    public HashSet() {
        map = new HashMap<>();
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }
}
```

📌 **Explanation:**

- 🔑 `Each element added to the HashSet` becomes a `key in the HashMap`.
- 🎯 The value for every key is a shared dummy object: `PRESENT`.
- 🧩 This `satisfies` the `key-value structure` of the HashMap, `even though` `HashSet only exposes a set-like API`.


---

## ❓ How does HashSet maintain uniqueness?

When we add a duplicate value into a HashSet, under the hood:

- 🔍 Inside the `add()` method, the set checks whether the key is already present in the internal `HashMap`.
- ➕ If the key is **not present**, `map.put(key, PRESENT)` returns `null`, and the key-value pair is added to the `HashMap`.
- ✅ In this case, `add()` compares the return value to `null` and returns `true`, indicating the element was successfully added.


- 🔁 If the key **is already present**, `map.put(key, PRESENT)` returns the previous value (`PRESENT`) and does not add a new entry.
- ❌ Then, `add()` compares the return value with `null` and returns `false`, indicating the value was a duplicate and not added.

🛡️ This is how `HashSet` maintains uniqueness.


---

## 🗑️ How does HashSet handle removal?

When we call the `remove()` method on a `HashSet`, under the hood:

- 🧩 Inside the `remove()` method, the set calls `remove(key)` on the internal `HashMap`.
- 🔎 The `HashMap` checks if the key (element) is present.
- 🗑️ If the key is **present**, `map.remove(key)` removes the key-value pair from the map and returns the dummy value (`PRESENT`).
- ✅ In this case, `remove()` compares the returned value with `PRESENT` and returns `true`, indicating the element was successfully removed.


- 🚫 If the key is **not present**, `map.remove(key)` returns `null`.
- ❌ Then, `remove()` compares the returned value with `PRESENT` and returns `false`, indicating the element was not found and nothing was removed.

---
## ⚠️ Notes:

- 📥 If you need `insertion order`, use `LinkedHashSet.`
- 📊 For `sorted order`, use `TreeSet`.

---

## 🧠 Null Handling in Set Implementations

- 🟢 **HashSet**        -> allows one null element ✅
- 🟢 **LinkedHashSet**  -> allows one null element ✅
- 🔴 **TreeSet**        -> does NOT allow null element ❌  

