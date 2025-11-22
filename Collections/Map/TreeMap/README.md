# 🌳 TreeMap Internal Working

TreeMap stores key-value pairs.

-TreeMap implements both `Map` and `SortedMap`, maintaining its keys in `sorted order` using their natural ordering or a specified `Comparator`.

- It sorts the data `based on keys` (ascending by default).
- It uses a `Red-Black Tree` (a type of `self-balancing binary search tree`).
- `Keys` must be comparable (either via `Comparable` or a custom `Comparator`).
- Nodes are placed `left` or `right` depending on `key comparison`.

---

## 🔴 Red-Black Tree Properties

- 🔴 Every node is either `red or black`.
- ⚫ The `root node` is always black.
- 🚫 `No two red nodes` can be together.
- ⚖️ `Every path` from `root node` to `leaf node` has `same number of black nodes`.

These rules help TreeMap stay balanced after insert/delete.

---
``` java
   TreeMap<String, Integer> tm = new TreeMap<>();
    tm.put("James", 100);
    tm.put("Larry", 50);
    tm.put("ted", 400);
    tm.put("peddi", 800);
    tm.put("brad", 71);
    tm.put("albert", 150);

```
## 🌱 Step-by-step Tree Building:

1. ➕ Insert **"James"**  
      - 🌳 Tree is empty → "James" becomes root node.

2. ➕ Insert **"Larry"**  
      - 🔍 Compare: "Larry" > "James" → go to right of James → insert there.

3. ➕ Insert **"ted"**  
      - 🔍 Compare: "ted" > "James" → go right
      - 🔍 Compare: "ted" > "Larry" → go right → insert.

4. ➕ Insert **"peddi"**  
      - 🔍 "peddi" > "James" → right
      - 🔍 "peddi" > "Larry" → right  
      - 🔍 "peddi" < "ted" → left → insert.

5. ➕ Insert **"brad"**  
      - 🔍 "brad" < "James" → left → insert.

6. ➕ Insert **"albert"**  
      - 🔍 "albert" < "James" → left 
      - 🔍 "albert" < "brad" → left → insert.

---

## ⚖️ Tree Balancing:

After each insertion, TreeMap (Red-Black Tree) checks and maintains balance using:

- 🔴 Node colors (red or black)
- 🔄 Rotations (left or right)
- 🎨 Color flips

You don’t see this, but it happens internally to keep search and insert operations fast.

---

## 🚫 Null keys and values

- ❌ **No null keys allowed**  
  TreeMap `does not allow` null as a key.  
  Because it uses `compareTo()` or `Comparator`, and comparing null `throws NullPointerException`.
  ```
    tm.put(null, 10); // ❌ Throws NullPointerException
  ```
- ✅ **But:**  
  `null values are allowed`.

---

## 🔑 Ordering is based on keys only

- 📌 Values are not considered in sorting.
- ⚖️ Even if values are the same, TreeMap sorts and stores by keys only.

---

## 🛠️ Custom sorting using Comparator

- 🔧 You can give your own Comparator for custom ordering:
- 🔽 Now the keys will be sorted in descending order.
```
  TreeMap<String, Integer> tm = new TreeMap<>(Comparator.reverseOrder());
```

---

## ⏳ Time complexity

| Operation           | Time     |
|---------------------|----------|
| ➕ put()             | O(log n)  |
| 🔍 get()             | O(log n)  |
| ❌ remove()          | O(log n)  |
| 🔄 iteration (in order) | O(n)      |

---

## ⚔️ TreeMap vs HashMap (internally):

| Feature             | TreeMap                      | HashMap                       |
|---------------------|------------------------------|-------------------------------|
| 📏 Ordering         | Sorted (by key)               | No order                      |
| 🏗️ Data structure   | Red-Black Tree                | Hash table + Linked list / Tree |
| ⚡ Performance      | O(log n)                     | O(1) average                  |
| 🚫 Null keys        | Not allowed                   | One null key allowed          |

---

## 📌 Final Notes:

- ❌ TreeMap `does not use hashing` (unlike HashMap).
- ⏱️ All operations like put(), get(), remove() work in O(log n) time because of the balanced tree.
- 🔑 Keys are always sorted.

