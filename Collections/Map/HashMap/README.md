# 📘 HashMap in Java - Internal Working

- `HashMap` is a class that implements the `Map` interface. 
- 🔗 **Stores data as key-value pairs.**

## 🧱 Structure

- Uses an array of **buckets** to store data.
- Each bucket contains a **LinkedList** (or **TreeNode** since Java 8).
- 🧮 **Default size of the array (number of buckets):** 16.
- ⚖️ **Load factor:** 0.75 (**treeify-threshold** to resize the map).

## 🗃️ Use Cases of HashMap

- ⚡ **Caching**
- 🔢 **Indexing**
- 🔍 **Lookup Tables**
- 📊 **Counting Frequency**
- 🧩 **Grouping Data**
- 🗺️ **Implementing Maps/Dictionaries**
- 🚫 **Removing Duplicates (using keys)**
- ⏳ **LRU Cache (with LinkedHashMap)**
- 🧠 **Memoization**
- 🔑 **Fast Search by Key**


## 🔐 Hashing

Hashing is the process of converting an object (key) into an integer value using its `hashCode()`.

- 🎯 **Purpose:** Faster lookup and indexing. 
- 📌 **Formula to calculate bucket index:**
- ### index = hashCode(key) & (n - 1)


> where `n` is the number of buckets (length of the array).

## 📝 `put()` Method – Internal Working

**Step-by-step:**

1. 🧮 Calculate `hashCode` of the key.
2. 📍 Use the formula to calculate **index (bucket)**.
3. 🔍 Check if the bucket is empty:

    - ✅ If empty → create new node and insert.
    - ❌ If not empty (**collision**):
        - Traverse the list:
            - If key exists (using `.equals()`), update value.
            - If not found, go to the end of the list and append a new node.
        - 🌳 If number of nodes in bucket > 8 and array size ≥ 64 → convert to **TreeNode** (Java 8+).


### 📦 Example:

```
Map<String,Employee> hm= new HashMap<>();
hm.put("virat", new Employee("Virat", 36));
hm.put("ABD", new Employee("ABD", 35));
hm.put("uv", new Employee("uv", 38));
hm.put("sachin", new Employee("sachin", 45));
```
- If:
  - `virat` → index 2
  - `ABD` → index 7
  - `uv` → index 4
  - `sachin` → index 2 → ❗ `collision with "virat"`

### 📌 Structure at index 2 becomes:
   ```
     [Node1] key: virat → next: [Node2] key: sachin → next: null 
   ```

## 💥 Collision Handling in Hashing

### 🔁 Hash Collision
 - When two keys map to the same bucket index.
 - A collision in HashMap occurs when `two different keys map` to the same bucket index, `not necessarily the same hash code`.
#### 🛠️ Handled using:
   - 🔍 `equals()` check:
      - ✅ If equal → update existing value.
   - 🔗 LinkedList traversal:
      - 🔁 If not equal → traverse till end → insert new node

## 🔍 get() Method – Internal Working

- 🧮 Calculate `hashCode` of the key.
- 📍 Compute index using the same formula.
- 🔎 Traverse the bucket (linked list or tree):
    - Compare keys using `.equals()`.
    - If `match found` → `return value`.
    - If `no match found` in that bucket → `return null`.


## ⏱️ Time Complexity

| 🛠️ Operation | ⚡ Best / Average Case | 🚨 Worst Case (all keys collide)     |
|--------------|------------------------|--------------------------------------|
| 📝 `put()`   | ⚡ O(1)                | 🐢 O(n) (if LinkedList)              |
| 🔍 `get()`   | ⚡ O(1)                | 🐢 O(n) (if LinkedList)              |

---

# 📌 HashMap Optimization & Time Complexity

## ⏱️ 1. Time Complexity of HashMap Operations

| 🛠️ Operation | ⚖️ Average Case | 😨 Worst Case (LinkedList) | 🌳 Worst Case (Balanced Tree) |
|--------------|----------------|-----------------------------|-------------------------------|
| `put()`      | ⚡ O(1)         | 🐢 O(n)                      | 🌲 O(log n)                   |
| `get()`      | ⚡ O(1)         | 🐢 O(n)                      | 🌲 O(log n)                   |

✅ In average cases, both `put()` and `get()` are O(1).

⚠️ Worst-case with long linked list bucket → O(n).

✅ Optimization reduces worst case to O(log n).

---

## 🌲 2. Optimization Using Balanced Tree (Treeify)

- 📌 Java 8 introduced treeification to reduce worst-case time from **O(n)** to **O(log n)**. 
- 🌳 When the `linked list size` in a `single bucket` `exceeds 8 nodes` (**constant TREEIFY_THRESHOLD = 8** AND **MIN_TREEIFY_CAPACITY>=64**) that `bucket is converted` into a `balanced red-black tree`. 
- 📍 This conversion applies `only` to that **particular bucket** — not to the entire HashMap. 
- 🪣 `Other buckets` with `fewer nodes remain` as linked lists.

---

# 🌳 HashMap Treeify & Resize Rules

## 🔄 1. Resize Condition:

HashMap resizes (`doubles the number of buckets`) when:  
📊 **Total entries in the entire map** > capacity × load factor

⚙️ **Default:**
- Capacity = number of buckets (e.g., 16)
- Load factor = 0.75
- Threshold = 16 × 0.75 = 12

🚨 Resize triggered **only by total entries count**, regardless of distribution in buckets.

for example

HashMap size is 16 
- 🪣 Bucket 3 → 9 nodes 
- 🪣 Bucket 5 → 1 nodes 
- 🪣 Bucket 8 → 1 node 

Total entries = 9+1+1 =11

resize formula : 11> (16*0.75) -> 11 > 12 then it will not resize bucket 3 will still be  linkedlist

---

## 🌲 2. Treeify Condition:

A `single bucket’s` `linked list` converts to `balanced tree` when:

- 🔢 Number of nodes in that bucket > 8
- 📦 AND capacity (number of buckets) ≥ 64

⚠️ If capacity(no.of buckets) < 64, `no treeify` happens even if a bucket has >8 nodes.

---


# 🧪 Edge Case Examples

| Cases | 🔍 Scenario                                      | 📦 Capacity (buckets) | 🧮 Total entries | 🔢 Max nodes in any bucket (Longest Bucket) | ⚙️ Action |
|-------|--------------------------------------------------|-----------------------|------------------|---------------------------------------------|-----------|
| 1.| 13 entries spread evenly, no bucket > 8 nodes    | 16 | 13 | 1                                           | 🔄 Resize (entries > threshold) |
| 2.| 9 nodes in one bucket, capacity = 16             | 16 | 11 | 9                                           | ❌ No resize (entries < threshold), no treeify (capacity < 64) |
| 3.| 9 nodes in one bucket, capacity = 64             | 64 | 70 | 9                                           | 🌳 Treeify (bucket size > 8 & capacity ≥ 64) |
| 4.| 70 entries total, capacity = 64, bucket size < 8 | 64 | 70 | 5                                           | 🔄 Resize if entries > threshold; ❌ No treeify (bucket < 8) |
| 5.| 10 nodes in one bucket, capacity = 128           | 128 | 130 | 10                                          | 🌲 Treeify bucket (capacity ≥ 64 & bucket > 8) |



---

## 🤔 Why does HashMap resize instead of treeify (when bucket has > 8 nodes)?

Because:

- ⚡ Resizing is `cheaper` and `faster` than treeifying.

🧠 Think of it like this:

If one bucket is getting too crowded (say, 9+ nodes), HashMap tries to fix the problem by resizing first — that means:

- 📦 It creates more buckets (doubles them)
- 🔄 Then spreads out all the entries again 
- All existing entries are `rehashed`:
  - It `recalculates the index` for `each key` using the `new bucket size`. 
  - Then it `redistributes all entries` into the `new buckets`. 

📉 Often, after resizing, that crowded bucket becomes less crowded
```
Before Resize (16 buckets):
Bucket 2 → [C → D → E → F → G]

After Resize (32 buckets):
Bucket 2 → [C]
Bucket 10 → [D]
Bucket 18 → [E]
Bucket 26 → [F]
Bucket 30 → [G]

```

## 🛑 Why avoid treeify early?

- 🪵 Tree structures are heavier and use more memory
- 🐢 They are slower to create than just adding more buckets

So Java says:
> 🧑‍💻 “Let me try resizing first. If that doesn't fix the crowding, and I'm big enough (≥64 buckets), then I’ll treeify.”


