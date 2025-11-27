# 📘 ArrayList in Java - Internal Working

- ArrayList is a `class` that implements the `List` interface. 
- 🔗 Stores data as a `resizable array`. 
- ✅ `Allows dynamic resizing`, unlike standard arrays. 
- ⚡ Maintains `insertion order`. 
- ❌ Does not allow primitive types directly (use wrappers like Integer, Double).

## 🧱 Structure

- Internally uses an array (`Object[] elementData`) to store elements. 
- 🧮 Default initial capacity: `10` (if default constructor is used). 
- ⚖️ Resizable: grows when size exceeds capacity (doubles the array + copies elements). (old capacity+ (old capacity >>1))

``` java
  ArrayList<Integer> list = new ArrayList<>();
// elementData[] = new Object[10] initially

```

## 📝 add() Method – Internal Working

Step-by-step for `add(E e)` at the end:

- 🧮 Check if current size < capacity:
  - ✅ If yes → insert element at `elementData[size]`. 
  - ❌ If no → resize array. 
- 🔄 Increment size by 1.

### Resize Logic (`ensureCapacityInternal()`):

- New capacity = old capacity × 1.5 (or `oldCapacity + (oldCapacity >> 1)` in Java 8+)
- Create a new array of `newCapacity`
- Copy existing elements to the new array (`System.arraycopy`)
- Assign `elementData = new array`

``` java
Before adding:
elementData.length = 10
size = 10

Add 11th element → resize:
newCapacity = 10 + (10 >> 1) = 15
elementData = new Object[15] // copy old elements

```

## 📝 get() Method – Internal Working

Direct array access:
``` java
return (E) elementData[index];
```
- ⚡ Constant time O(1) because arrays support direct indexing.

## 📝 add(index, element) – Inserting in Middle

- 🔎 Validate index (`0 <= index <= size`)
- 🔄 Shift all elements from `index` to `size-1` one position right. 
- 🧮 Insert element at `elementData[index]`. 
- Increment size. 
- ⚠️ Resize if needed.

### Time Complexity:

- Best case (add at end) → O(1)
- Worst case (add at beginning) → O(n) because of shifting

## 📝 remove(index) – Internal Working

- 🔎 Validate index. 
- 🔄 Shift all elements from `index+1` to `size-1` one position left. 
- Set `elementData[size-1] = null` (help GC). 
- Decrement size.

### Time Complexity:

- Best case (remove last element) → O(1)
- Worst case (remove first element) → O(n)

## ⏱️ Time Complexity

| 🛠️ Operation       | ⚡ Best / Average Case | 🐢 Worst Case      |
|-------------------|----------------------|------------------|
| get(index)        | ⚡ O(1)              | O(1)             |
| add(element)      | ⚡ O(1) amortized    | O(n) (resize)    |
| add(index, el)    | ⚡ O(1)              | O(n) (shift)     |
| remove(index)     | ⚡ O(1)              | O(n) (shift)     |
| contains(el)      | ⚡ O(n)              | O(n)             |
| indexOf(el)       | ⚡ O(n)              | O(n)             |

 > ⚡ Amortized O(1) for add at end: because resize happens occasionally, not every add.

## 🔄 Resizing Rules

- ✅ Default initial capacity: 10
- ⚠️ Resize occurs only when `size == capacity`
- 🧮 New capacity = `oldCapacity + (oldCapacity >> 1)` → roughly 1.5× old capacity 
- 🔄 Old elements are copied to new array using `System.arraycopy`

## 🧪 Edge Case Examples

| Cases                        | Scenario                  | Capacity      | Size        | Action                      |
|-------------------------------|--------------------------|---------------|------------|----------------------------|
| 1                             | Add 5 elements to new ArrayList | 10            | 5          | No resize                   |
| 2                             | Add 12 elements           | 10 → 15       | 12         | Resize triggered at 11th element |
| 3                             | Remove element at index 0 | 15            | 11 → 10    | Shift elements left         |
| 4                             | Add element at index 5    | 15            | 10 → 11    | Shift elements from 5 to 10 to right |

---

## 🔄 Resizing Problem

Every time the array fills up, resizing is expensive because it has to:

- ⚡ Create a bigger array
- 📋 `Copy all elements` from the `old array` to the `new array`

Adding many items one by one can trigger multiple resizes and slow things down.

### ⚡ `ensureCapacity(int minCapacity)`

This method tells the ArrayList:

> “Hey, make sure your internal array can hold at least `minCapacity` items now.”

- 🔧 `Avoids multiple resizes if you know you'll add a lot of elements`

``` java
ArrayList<Integer> list = new ArrayList<>();

// Suppose we know we'll add 1000 elements
list.ensureCapacity(1000);

// Now we add 1000 elements without resizing multiple times
for (int i = 0; i < 1000; i++) {
    list.add(i);
}

```

### 🗑️ `trimToSize()`

This method shrinks the internal array to match the actual number of elements in the list.

- 💾 `After trimming, no extra memory is wasted`

`Problem:` If your ArrayList has extra unused space, it’s wasting memory.  
``` java
ArrayList<Integer> list = new ArrayList<>(1000); // big array allocated
list.add(1);
list.add(2);

```
- here Internal array can hold 1000 elements, but only 2 are used → most memory is wasted.

``` java
ArrayList<Integer> list = new ArrayList<>(1000);
list.add(1);
list.add(2);

System.out.println(list.size()); // 2 elements
// Internal array size is 1000 (capacity)

// Now trim
list.trimToSize();
// Internal array size becomes 2 (same as list size)

```
✅ Use it when:
- ✔️ `You’ve finished adding elements`
- ✔️ `You won’t add more elements`
- 💡 `You want to save memory`

---

## 🚨 Fail-Fast in Java Collections

Some Java collections like ArrayList are `Fail-Fast`.

`Fail-Fast` means: if you modify a collection while iterating over it (using an `iterator` or `enhanced for loop`), it throws an exception immediately.

### 🛡️ Behavior

- ⚠️ `Java throws a ConcurrentModificationException instead of letting you silently mess up the list`
- 🛑 `Prevents inconsistent results`

### 🕵️ How ArrayList Detects It

- 🔢 `ArrayList has a hidden counter called modCount`
- ➕ `Each time you add or remove elements, modCount increases`
- 👀 `The iterator remembers modCount when it was created`
- ❌ `While iterating, if it notices modCount has changed → exception is thrown`

``` java
ArrayList<Integer> list = new ArrayList<>();
list.add(1);
list.add(2);

Iterator<Integer> it = list.iterator();
list.add(3); // modifies list, increments modCount

it.next(); 
// Iterator sees modCount changed → throws ConcurrentModificationException

```

### 📝 Summary

- 🛡️ `Fail-Fast: Stops dangerous concurrent modifications`
- 🔢 `modCount: Internal counter used to detect structural changes`

---

## 🔁 Iterator / ListIterator

- 🔄 `Iterators are fail-fast: detect concurrent modifications`
- ⏭️ `next() or remove() checks modCount`
- ↔️ `ListIterator can traverse forward/backward and modify the list safely using iterator methods`

---

## ⚖️ Difference Between ArrayList & Vector

| Feature           | ArrayList               | Vector          |
|------------------|-----------------------|----------------|
| 🛡️ `Thread Safety`      | ❌ `Not synchronized`    | ✅ `Synchronized` |
| ⚡ `Performance`       | `Faster`                | `Slower`         |
| 🕰️ `Legacy`            | `Modern`                | `Legacy`         |
| 🔼 `Growth factor`     | `1.5×`                  | `2×`             |

---

## 📋 Cloning

Cloning means making a copy of the ArrayList.

- 🆕 `The new ArrayList has the same elements, but it’s a different object`

``` java
 ArrayList<String> original = new ArrayList<>();
original.add("A");
original.add("B");

ArrayList<String> copy = (ArrayList<String>) original.clone();

System.out.println(original); // [A, B]
System.out.println(copy);     // [A, B]

// They are different objects
System.out.println(original == copy); // false

```

### Important:

- 📝 `The clone is shallow:`
  - 📂 `The list object is new`
  - 🔗 `The elements are not copied; both lists point to the same objects`
  - ⚠️ `If elements are mutable (like another ArrayList or object), changing them in one list affects the other`
