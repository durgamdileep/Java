# 📘 LinkedList in Java – Internal Working

`LinkedList` implements the `List` and `Deque` interfaces.

- 🔗 Stores data as nodes, where each node contains:
    - `data` (the element)
    - `next` reference (pointer to next node)
    - `prev` reference (pointer to previous node, for doubly linked list)

- ✅ Maintains insertion order.
- ⚡ Efficient insertion/deletion at start/middle/end.
- ❌ Slower random access (no direct indexing).

## 🧱 Structure

- Internally uses a doubly linked list (`Node<E>`).
- Each Node contains:

``` java
 class Node<E> {
    E item;       // Data
    Node<E> next; // Pointer to next node
    Node<E> prev; // Pointer to previous node
}

```

- `LinkedList` maintains:
    - `first` → points to head node
    - `last` → points to tail node
    - `size` → number of elements

## 📝 add(E e) – Append at End

**Steps:**

1. Create a new Node with `item = e`, `prev = last`, `next = null`.
2. If list is empty:
    - `first = last = newNode`
3. Else:
    - `last.next = newNode`
    - `last = newNode`
4. Increment `size`.

**Time Complexity:** `O(1)` – constant time for end insertion.

## 📝 addFirst(E e) / addLast(E e)

- `addFirst(E e)` → insert at head:
    - `newNode.next = first`
    - `first.prev = newNode`
    - `first = newNode`
- `addLast(E e)` → same as `add(E e)`

**Time Complexity:** `O(1)`

## 📝 add(index, element) – Insert in Middle

**Steps:**

- 🔎 Validate index (`0 <= index <= size`).
- Traverse list to node at index (from head if `index < size/2`, else from tail).
- Create `newNode` and adjust references:
``` java
prevNode.next = newNode
newNode.prev = prevNode
newNode.next = currentNode
currentNode.prev = newNode
```
- Increment `size`.



**Time Complexity:** `O(n)` in worst case (traversal).

## 📝 remove(index) – Remove Node

**Steps:**

- 🔎 Validate index.
- Traverse to node at index.
- Adjust references:

``` java
node.prev.next = node.next
node.next.prev = node.prev
node.next = node.prev = null
```
- Decrement `size`.

**Time Complexity:** `O(n)` (traversal), `O(1)` if removing first/last node.

## 📝 get(index) – Access Node

- Traverse from head if `index < size/2`, else tail.

**Why size/2 matters**

- Suppose the list has `size = 10` and you want `get(index)`.
- The note says:
    - Traverse from head if `index < size/2`, else tail.
    - `size/2 = 5`
    - If `index < 5` (like 0,1,2,3,4), it’s faster to start from head and move forward.
    - If `index ≥ 5` (like 5,6,7,8,9), it’s faster to start from tail and move backward.

⚠️ No direct array indexing → `O(n)` time.

## 🏷️ Time Complexity

| 🛠️ Operation      | ⚡ Best / Average Case | 🐢 Worst Case |
|------------------|---------------------|---------------|
| `get(index)`      | O(n)                | O(n)          |
| `add(E e)`        | O(1)                | O(1)          |
| `add(index, el)`  | O(1) if at head/tail| O(n)          |
| `remove(index)`   | O(1) if head/tail   | O(n)          |
| `addFirst()`      | O(1)                | O(1)          |
| `addLast()`       | O(1)                | O(1)          |
| `removeFirst()`   | O(1)                | O(1)          |
| `removeLast()`    | O(1)                | O(1)          |
| `contains(el)`    | O(n)                | O(n)          |

## 🔄 Memory Usage

- Each node stores 3 references: `prev`, `next`, `item`
- ✅ More memory overhead than `ArrayList`
- ⚡ Advantage: no resizing needed, unlike dynamic array

## ⚡ Advanced Features

1️⃣ Implements `Deque` Interface

- Can be used as queue or deque:
    - `offerFirst()`, `offerLast()`
    - `pollFirst()`, `pollLast()`
    - `peekFirst()`, `peekLast()`

2️⃣ Iterator / `ListIterator`

- Supports fail-fast iterators using `modCount`
- `ListIterator` can traverse forward/backward

3️⃣ Cloning & Serialization

- `clone()` → shallow copy of nodes (elements are not cloned)
- Implements `Serializable` → can be saved/restored

4️⃣ Efficient Insert/Delete

- No shifting elements needed (unlike `ArrayList`)
- ✅ O(1) insertion/deletion at head or tail

## 🧪 Edge Case Examples

| Cases                     | Scenario                        | Action                     | Time Complexity |
|----------------------------|--------------------------------|----------------------------|----------------|
| 1                          | Add 5 elements at end           | Update last pointer        | O(1)           |
| 2                          | Add element at index 2          | Traverse to index, update references | O(n) |
| 3                          | Remove first element            | Update first pointer       | O(1)           |
| 4                          | Remove last element             | Update last pointer        | O(1)           |
| 5                          | Access element at index 7       | Traverse from head/tail    | O(n)           |

## 💡 Key Points

- ✅ Doubly linked list → fast insertion/deletion at any position.
- ⚠️ Slow random access → no direct indexing.
- 🔄 Memory overhead → 3 references per node.
- ⚡ Iterators are fail-fast.
- 🧠 Implements `Deque`, `Queue`, and `List` interfaces
