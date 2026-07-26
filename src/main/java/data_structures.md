# Data Structures in BananaTracker

## ArrayList
`ArrayList` is a resizable-array implementation of the `List` interface. It is part of the Java Collections Framework.

### Syntax
```java
ArrayList<Type> listName = new ArrayList<>();
```

### Usage in BananaTracker
Inside our current implementation of **BananaTracker**, `ArrayList` is used in two primary ways:
1.  **User Storage**: In `BananaTracker.java`, a static `ArrayList<User>` named `users` stores all registered users in the system.
2.  **Transaction Tracking**: In `User.java`, each `User` object maintains an instance-level `ArrayList<Transaction>` called `transactions` to store their personal transaction history.

### Why do we use ArrayList?
We chose `ArrayList` for the current version of BananaTracker for several reasons:
1. **Dynamic Resizing**: Since we don't know the number of users or transactions beforehand, `ArrayList` handles growing the storage automatically, which is much simpler than managing standard arrays.
2. **Order Preservation**: It maintains the insertion order, which is critical for displaying transaction history in the order it occurred.
3. **Random Access**: It provides O(1) time complexity for accessing elements by index, making it efficient to retrieve specific users or transactions if their position is known.
4. **Simplicity**: For a prototype or small-scale application, `ArrayList` is easy to implement and provides a good balance of performance and functionality.

### Five Rules of ArrayList
1.  **Dynamic Resizing**: Unlike standard arrays, `ArrayList` grows automatically when elements are added.
2.  **Ordered Collection**: It maintains the insertion order of elements.
3.  **Random Access**: It implements the `RandomAccess` interface, allowing fast (O(1)) retrieval of elements via an index.
4.  **Reference Types Only**: It can only store objects (reference types), not primitives (e.g., use `Integer` instead of `int`).
5.  **Not Thread-Safe**: By default, `ArrayList` is not synchronized. If multiple threads access it concurrently, external synchronization is required.

### Three Trade-offs
1.  **Performance (Insertion/Deletion)**: Fast at the end (O(1) amortized), but slow (O(n)) when inserting or removing from the middle as elements must be shifted.
2.  **Memory Overhead**: It consumes more memory than a standard array due to the overhead of maintaining the capacity and the object wrappers for primitives.
3.  **Contiguous Memory**: While it provides fast access, it requires a contiguous block of memory, which can lead to reallocation and copying as the list grows.

### Two Failure Modes
1.  **ConcurrentModificationException**: Occurs if the list is modified structurally (add/remove) while iterating over it using a `for-each` loop or `Iterator`.
2.  **IndexOutOfBoundsException**: Thrown when attempting to access an index that is negative or greater than or equal to the current `size()`.

## Future Scope: Better Data Structures
As BananaTracker scales, we can consider more specialized data structures to improve performance:

1. **HashMap for User Lookups**:
    - **Current**: Finding a user by email in `ArrayList<User>` takes O(n) time.
    - **Future**: Using a `HashMap<String, User>` where the email is the key would allow O(1) average time complexity for sign-in and duplicate email checks.

2. **LinkedList for Transactions (if applicable)**:
    - **Scenario**: If the application frequently required adding transactions to the *beginning* of the list (e.g., showing newest first without sorting).
    - **Advantage**: `LinkedList` provides O(1) insertion at both ends.

3. **Sorted Sets / Priority Queues**:
    - **Scenario**: If we need to always keep transactions sorted by date or amount automatically.
    - **Advantage**: `TreeSet` or `PriorityQueue` could maintain order based on a custom comparator.

4. **Persistent Database**:
    - **Scenario**: For production use.
    - **Advantage**: Moving from in-memory collections to a SQL (e.g., PostgreSQL) or NoSQL database for durability and complex querying.

## Quick Interview FAQs

**Q1: What is the difference between `ArrayList` and `LinkedList`?**
**A:** `ArrayList` uses a dynamic array and is better for search/access (O(1)), while `LinkedList` uses a doubly-linked list and is better for frequent insertions/deletions at the ends (O(1)).

**Q2: How does `ArrayList` grow internally?**
**A:** When the capacity is exceeded, it creates a new, larger array (typically 1.5x the old size) and copies all existing elements to the new array.

**Q3: Can `ArrayList` store null values?**
**A:** Yes, `ArrayList` allows null elements to be added.

**Q4: Why is `ArrayList` preferred over a standard array in most Java applications?**
**A:** Because it provides dynamic resizing, a rich set of built-in methods (like `add`, `remove`, `contains`), and integrates seamlessly with the Collections API, reducing the amount of boilerplate code.

**Q5: Is `ArrayList` thread-safe? How can we make it thread-safe?**
**A:** No, it is not thread-safe. You can make it thread-safe by using `Collections.synchronizedList(new ArrayList<>())` or by using `CopyOnWriteArrayList` from the `java.util.concurrent` package.
