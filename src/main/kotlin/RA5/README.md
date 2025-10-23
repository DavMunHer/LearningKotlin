## 🧩 1. CONTAINERS

### 🔸 Collection Types: Mutable and Immutable

``` kotlin
val immutableList = listOf(1, 2, 3) // Cannot be modified
val mutableList = mutableListOf(1, 2, 3) // Can be modified

mutableList.add(4) // ✅
immutableList[0] // ✅ can be read
// immutableList.add(4) ❌ error
```

👉 **Immutable** (read-only) objects do not allow their contents to be modified.
👉 **Mutable** objects do (add, remove, update).

---

### 🔸 Creating containers: factory, build, and copy functions

``` Kotlin
// Factory functions
val list = listOf("A", "B", "C")
val set = setOf(1, 2, 2, 3) // remove duplicates
val map = mapOf("a" to 1, "b" to 2)

// buildList: creation with a mutable block
val buildList = buildList {
add("one")
add("two")
}

// copy
val copy = list.toMutableList()
```

👉 `buildList` is useful for building complex lists.
👉 `.toMutableList()` or `.toList()` are used to **clone** lists.

---

### 🔸 Indexing and Addition/Assignment Operator

```kotlin
val list = mutableListOf(10, 20, 30)
println(list[1]) // access: 20

list[1] = 25 // assignment
list += 40 // add element
println(list) // [10, 25, 30, 40]
```

👉 `[]` allows **accessing or modifying** by index.
👉 `+=` adds elements to a mutable collection.

---

### 🔸 Pair class and `to` infix function

```kotlin
val pair = Pair("name", "Alex")
val pair2 = "age" to 25 // equivalent

println(pair.first) // "name"
println(pair.second) // "Alex"
```

👉 `Pair` groups **two values**.
👉 `to` is an **infix function** for easily creating pairs.

---

### 🔸 Creating maps from pairs

```kotlin
val map = mapOf("one" to 1, "two" to 2, "three" to 3)
println(map["two"]) // 2
```

👉 Each `to` creates a `Pair`, and the pairs form the map.

---

## ⚙️ 2. FUNCTIONAL API

### 🔸 Mapping Operations

```kotlin
val numbers = listOf(1, 2, 3)
val doubles = numbers.map { it * 2 } // [2, 4, 6]
```

👉 `map` transforms each element in the collection.

---

### 🔸 Filtering Operations

```kotlin
val pairs = numbers.filter { it % 2 == 0 } // [2]
```

👉 `filter` selects elements based on a condition.

---

### 🔸 Reduce and Fold Operations

```kotlin
val suma = numeros.reduce { acc, n -> acc + n } // 6
val sumaConInicial = numeros.fold(10) { acc, n -> acc + n } // 16
```

👉 `reduce` combines elements together.
👉 `fold` allows an initial value for accumulation.

---

### 🔸 Collection Predicates

```kotlin
val list = listOf(1, 2, 3, 4)
println(list. all { it > 0 }) // true
println(list. any { it == 3 }) // true
println(list. none { it < 0 }) // true
println(list. count { it % 2 == 0 }) // 2
println(list. find { it > 2 }) // 3
```

👉 Evaluate conditions on elements.

---

### 🔸 Partition Operations

```kotlin
val (even, odd) = list. partition { it % 2 == 0 }
println(even) // [2, 4]
println(odd) // [1, 3]
```

👉 Splits into two lists based on a predicate.

---

### 🔸 Grouping: `groupBy` and `groupingBy`

```kotlin
val words = listOf("one", "two", "three", "four")
val byLength = words. groupBy { it. length }
// {3 = [one, two], 4 = [three], 6 = [four]}
```

👉 `groupBy` returns a `Map<K, List<V>>`.

---

### 🔸 Transformation on maps: `associate`, `associateWith`, `associateBy`

```kotlin
val list = listOf("one", "two", "three")

val map1 = list.associate { it to it.length } // "one"→3
val map2 = list.associateWith { it.length } // same as above
val map3 = list.associateBy { it.first() } // 'u'→"one", 'd'→"two"
```

---

### 🔸 Replace elements: `replaceAll` and `fill`

```kotlin
val list = mutableListOf(1, 2, 3)
list.replaceAll { it * 2 } // [2, 4, 6]

list.fill(0) // [0, 0, 0]
```

---

### 🔸 Handling special cases: `ifEmpty`

```kotlin
val list = emptyList<Int>()
val result = list.ifEmpty { listOf(1, 2, 3) }
println(result) // [1, 2, 3]
```

---

### 🔸 Chunking collections: `chunked` and `windowed`

```kotlin
val list = (1..6).toList()

println(list.chunked(2)) // [[1,2], [3,4], [5,6]]
println(list.windowed(3)) // [[1,2,3], [2,3,4], [3,4,5], [4,5,6]]
```

👉 `chunked` splits into chunks.
👉 `windowed` creates “sliding windows.”

---

### 🔸 Combining collections: `zip`

```kotlin
val names = listOf("Ana", "Luis", "Eva")
val ages = listOf(20, 25, 30)

val combined = names. zip(ages)
// [(Ana, 20), (Luis, 25), (Eva, 30)]
```

---

### 🔸 Processing nested collections: `flatMap` and `flatten`

```kotlin
val lists = listOf(listOf(1, 2), listOf(3, 4))
println(lists. flatten()) // [1, 2, 3, 4]

val result = lists. flatMap { it. map { n -> n * 2 } }
// [2,4,6,8]
```

👉 `flatten` flattens lists.
👉 `flatMap` transforms and flattens.

---

## 🔄 3. SEQUENCES

### 🔸 Creating sequences

```kotlin
val sequence = sequenceOf(1, 2, 3)
val longSequence = generateSequence(1) { it + 1 } // infinite
```

---

### 🔸 Intermediate and terminal operations

```kotlin
val result = generateSequence(1) { it + 1 }
.filter { it % 2 == 0 } // intermediate
.map { it * 3 } // intermediate
.take(5) // intermediate
.toList() // terminal
println(result) // [6, 12, 18, 24, 30]
```

👉 Intermediate: `map`, `filter`, `take`, etc.
👉 Terminal: `toList`, `sum`, `count`, etc.

---

### 🔸 Lazy vs. eager execution

```kotlin
val list = listOf(1, 2, 3, 4, 5)
list.map { println("List $it"); it * 2 } // print everything immediately
sequenceOf(1, 2, 3, 4, 5)
.map { println("Sequence $it"); it * 2 }
.first() // only process up to the first
```

👉 **Lists** process everything (**eager/impatient** execution).
👉 **Sequences** process only what is necessary (**lazy** execution).

---

# Activity Guide: Collections, Functional API, and Sequences in Kotlin

**Overall Objective:** Design and complete a series of hands-on activities that require you to deliberately apply the concepts of containers (mutable and immutable), the Kotlin Functional API, and sequences. Each activity describes what to do, the constraints, examples of expected input/output, and evaluation criteria. **It does not include solutions** — only detailed instructions and hints where appropriate.

---

## Activity 1 — Basic Inventory (Containers and Basic Operations)

**Description:** Create a small product inventory structure that stores name, category, and quantity. You must implement operations to query, add, and update quantities.

**Requirements and Steps:**

1. Define a `Product(val name: String, val category: String, val quantity: Int)` data class.

2. Create two inventory representations:

- `inventoryReadOnly: List<Product>` (immutable)

- `inventoryMutable: MutableList<Product>` (mutable)

3. Implement the following functions (signature and behavior):

- `fun findByName(inventory: List<Product>, name: String): Product?` — search by name.

- `fun addOrIncrease(inventory: MutableList<Product>, product: Product)` — if the product exists (with the same name), increase its quantity; if not, add it.

- `fun totalByCategory(inventory: List<Product>, category: String): Int` — sums quantities by category.

4. Use the indexing operator to read/update items in the mutable version when appropriate.

**Restrictions:**

- Do not modify the immutable list; for operations that require modification, return a new list.

- Avoid unnecessary lookups (consider index operations when using the mutable list).

**Deliverable:**

- Documented function signatures and behavior.

- A set of tests (mini-cases) showing inputs and expected outputs.

**Example of expected test (do not implement):**

- Initial inventory: `[Product("pencil","office",5), Product("notebook","office",3)]`

- `addOrIncrease(inventoryMutable, Product("pencil","office",2))` → then: pencil quantity = 7

- `totalByCategory(inventoryReadOnly, "office")` → 8

---

## Activity 2 — Converter and Filter (map/filter/fold)

**Description:** You will work with a list of strings representing transactions: each transaction is in the format `"<type>:<amount>"` where `type` is `"ing"` or `"expense"` and `amount` is an integer. You must parse them, filter them, and obtain statistics.

**Requirements and steps:**

1. Create a `parse(transactions: List<String>): List<Pair<String, Int>>` function that converts the list of `String` to `Pair(type, amount)`. Use `map` and the infix `to` function in transformations where appropriate.

2. Implement `filterExpenses(parsed: List<Pair<String, Int>>): List<Pair<String, Int>>` that returns only expenses.

3. Implement `sumByType(parsed: List<Pair<String, Int>>, type: String): Int` that uses `fold` or `reduce` to sum amounts by type.

4. Add a `balance(parsed: List<Pair<String, Int>>): Int` function that calculates income minus expenses.

**Restrictions:**

- `parse` must handle malformed inputs by returning a list without those elements (no exceptions thrown).

- Use functional API functions (`map`, `filter`, `fold`, `reduce`) to resolve each part.

**Deliverable:**

- Clear function definition and tests with sample data showing `parse`, `filterExpenses`, and `balance`.

**Example of expected test:**

- Input: `["ing:100", "spending:30", "spending:20", "ing:50"]`

- `sumByType(parsed, "ing")` → 150

- `balance(parsed)` → 100

---

## Activity 3 — Grouping and associating (groupBy, associate\*, and associateBy)

**Description:** Given a list of users with `data class User(val id: Int, val name: String, val age: Int, val role: String)`, generate several views: group by role, build maps by id and name.

**Requirements and steps:**

1. Implement `groupByRole(users: List<User>): Map<String, List<User>> using `groupBy`.

2. Implement `countByRole(users: List<User>): Map<String, Int>` using `groupingBy` and reduction operations.

3. Implement `mapById(users: List<User>): Map<Int, User>` using `associateBy`.

4. Implement `mapNameToAge(users: List<User>): Map<String, Int>` using `associateWith` or `associate`.

**Restrictions:**

- Consider duplicate names when constructing maps by name and decide on a policy (document it).

**Deliverable:**

- Function contracts and tests that demonstrate the expected format of maps.

**Case studies to include in tests:**

- Users with duplicate roles.

- Users with duplicate names (explain the chosen policy in the comments).

---

## Activity 4 — Windows and Chunks (chunked, windowed) — Time Series Analysis

**Description:** Work with a list of integer values ​​representing daily metrics. You must create windows to calculate moving averages and detect peaks.

**Requirements and Steps:**

1. Implement `rollingAverage(values: List<Int>, windowSize: Int): List<Double>` using `windowed` to produce the average of each window.

2. Implement `chunkMax(values: List<Int>, chunkSize: Int): List<Int>` using `chunked` to return the maximum of each chunk.

3. Implement `findPeaks(values: List<Int>, windowSize: Int): List<Int>` that returns indices where the central value of a window is greater than its neighbors (use `windowed` with `partialWindows = false`).

**Constraints:**

- For `windowed`, correctly handle cases where `values.size < windowSize`.

**Deliverable:**

- Contracts and tests with a small dataset where you can manually identify the moving average and peaks.

---

## Activity 5 — Partition, Find, and Count Operations (partition, find, count)

**Description:** Given a list of integers, perform simple analyses: split even/odd numbers, count primes in the collection, and find the first number that satisfies a compound condition.

**Requirements and steps:**

1. `fun splitEvenOdd(values: List<Int>): Pair<List<Int>, List<Int>>` using `partition`.

2. `fun firstDivisibleBy(values: List<Int>, divisor: Int): Int?` using `find`.

3. `fun countPrimes(values: List<Int>): Int` using `count` and a helper `isPrime` function (define its contract; the implementation of `isPrime` is up to you).

**Constraints:**

- Document the approximate complexity of `isPrime` and why it might affect `countPrimes`.

**Deliverable:**

- Test cases including small and large numbers, and a performance commentary.

---

## Activity 6 — Advanced Transformations: flatMap, flatten, and zip

**Description:** Process a list of playlists, where each playlist is a `List<String>` of songs. You must flatten, transform, and combine data.

**Requirements and steps:**

1. `fun allSongs(playlists: List<List<String>>): List<String>` uses `flatten`.

2. `fun actionableList(playlists: List<List<String>>): List<String>` uses `flatMap` to add prefixes to each song indicating its playlist (e.g., `"[playlist1] - Song"`).

3. `fun pairSongsWithDuration(songs: List<String>, durations: List<Int>): List<Pair<String, Int>>` uses `zip`. Consider what happens if the lists have different sizes.

**Restrictions:**

- Document the behavior when lists are not the same length when using `zip`.

**Deliverable:**

- Input examples and expected output.

---

## Activity 7 — Mutable vs. Immutable: Replacement and Fill (replaceAll, fill)

**Description:** Explore operations that modify mutable collections in-place.

**Requirements and Steps:**

1. Create a mutable list of integers and apply `replaceAll` to transform its values ​​(e.g., multiply by 10).

2. Use `fill` to set a default value for the entire list.

3. Describe when using these operations is preferable to creating a new collection with `map`.

**Restrictions:**

- You must show a (brief) theoretical memory or performance comparison between modifying in-place and creating a new collection.

**Deliverable:**

- Test fragments (without solutions) that demonstrate the expected before/after results.

---

## Activity 8 — Handling voids: ifEmpty and safe behavior

**Description:** Build functions that return alternative collections when the input is empty.

**Requirements and steps:**

1. `fun ensureNotEmpty(list: List<Int>): List<Int>` that uses `ifEmpty` to return a default list when the list is empty.

2. Design a flow that chains `ifEmpty` with other functional API operations.

**Constraints:**

- Document why `ifEmpty` can avoid `NullPointerException` and improve expressiveness.

**Deliverable:**

- Tests that demonstrate the difference between using `ifEmpty` and manual checks

## Activity 9 — Sequences: Lazy vs. Eager (Practical Comparison)

**Description:** Create examples that demonstrate the difference between working with standard collections and `Sequence` in terms of lazy execution.

**Requirements and Steps:**

1. Build a list of 1..1000 and apply `map` and `filter` chained together; measure (conceptually) how many transformations are applied per element.

2. Repeat the same chaining but convert the list to `asSequence()` before applying the transformations and explain the difference in the order and number of evaluations per element.

3. Use an infinite sequence with `generateSequence` and demonstrate (with `take`) how `Sequence` allows you to operate on infinite series without running out of memory.

**Restrictions:**

- Don't ask the user to measure execution times if they don't have the tools; ask them to reasonably describe the difference in the number of evaluations.

**Deliverable:**

- A brief report with conclusions: when to use `Sequence` and when not to?

---

## Activity 10 — Integrative Project (Mini-Application)

**Description:** Integrate several concepts into a mini-application: an activity log processor that accepts logs (user, action, timestamp, metadata).

**Requirements and Steps:**

1. Design the `data class Log(val userId: Int, val action: String, val timestamp: Long, val meta: Map<String, String>)`.

2. Implement functions that use `map`, `filter`, `groupBy`, `associateBy`, `flatMap`, and `sequence` to obtain:

- Number of actions per user in a time interval.

- Users who performed a specific action more than *N* times.

- An `action -> list of userIds` map for actions in the last 24 hours.

3. Add a test route where a large (mock) log source is processed using sequences to avoid loading everything into memory.

**Restrictions:**

- Document clearly.

I created the guide as a canvas document titled **"Kotlin Activities Guide — Collections, Functional API, and Sequences"**. It contains 10 detailed activities, evaluation criteria, hints, and optional extensions — all without worked-out solutions, as you requested.

Open it from the side panel to see the sample activities and tests. When you complete an activity, send me your code (or the tests) and I'll give you timely feedback and suggestions for improvement. Which activity would you like to start with?

You:

# Activity Guide: Collections, Functional API, and Sequences in Kotlin

**Overall Objective:** Design and complete a series of practical activities that require you to deliberately apply the concepts of containers (mutable and immutable), the Kotlin Functional API, and sequences. Each activity describes what to do, the constraints, examples of expected input/output, and evaluation criteria. **No solutions included** — only detailed instructions and hints where appropriate.

---

## Activity 1 — Basic Inventory (Containers and Basic Operations)

**Description:** Create a small product inventory structure that stores the name, category, and quantity. You must implement operations to query, add, and update quantities.

**Requirements and steps:**

1. Define a `Product(val name: String, val category: String, val quantity: Int)` data class.

2. Create two inventory representations:

- `inventoryReadOnly: List<Product>` (immutable)

- `inventoryMutable: MutableList<Product>` (mutable)

3. Implement the following functions (signature and behavior):

- `fun findByName(inventory: List<Product>, name: String): Product?` — searches by name.

- `fun addOrIncrease(inventory: MutableList<Product>, product: Product)` — if the product exists (with the same name), increases its quantity; if not, adds it.

- `fun totalByCategory(inventory: List<Product>, category: String): Int` — adds quantities by category.

4. Use the indexing operator to read/update elements in the mutable version when appropriate.

**Restrictions:**

- Do not modify the immutable list; for operations that require modification, return a new list.

- Avoid unnecessary lookups (consider index operations when using the mutable list).

**Deliverable:**

- Documented function signatures and behavior.

- A set of tests (mini-cases) showing expected inputs and outputs.

**Example of expected test (do not implement):**

- Initial inventory: `[Product("pencil","office",5), Product("notebook","office",3)]`

- `addOrIncrease(inventoryMutable, Product("pencil","office",2))` → then: pencil quantity = 7

- `totalByCategory(inventoryReadOnly, "office")` → 8

---

## Activity 2 — Converter and filter (map/filter/fold)

**Description:** You will work with a list of and strings representing transactions: each transaction is in the format `"<type>:<amount>"` where `type` is `"ing"` or `"expense"` and `amount` is an integer. You must parse, filter, and obtain statistics.

**Requirements and steps:**

1. Create a `parse(transactions: List<String>): List<Pair<String, Int>>` function that converts the list of `String` to `Pair(type, amount)`. Use `map` and the infix `to` function in transformations where appropriate.

2. Implement `filterExpenses(parsed: List<Pair<String, Int>>): List<Pair<String, Int>>` that returns only expenses.

3. Implement `sumByType(parsed: List<Pair<String, Int>>, type: String): Int` that uses `fold` or `reduce` to sum amounts by type.

4. Add a `balance(parsed: List<Pair<String, Int>>): Int` function that calculates income minus expenses.

**Restrictions:**

- `parse` must handle malformed inputs by returning a list without those elements (do not throw an exception).

- Use Functional API functions (`map`, `filter`, `fold`, `reduce`) to resolve each part.

**Deliverable:**

- Clear function definition and tests with sample data showing `parse`, `filterExpenses`, and `balance`.

**Example of expected test:**

- Input: `["ing:100", "spending:30", "spending:20", "ing:50"]`

- `sumByType(parsed, "ing")` → 150

- `balance(parsed)` → 100

---

## Activity 3 — Grouping and Association (groupBy, associate\*, and associateBy)

**Description:** Given a list of users with `data class User(val id: Int, val name: String, val age: Int, val role: String)`, generate several views: group by role, build maps by id and name.

**Requirements and steps:**

1. Implement `groupByRole(users: List<User>): Map<String, List<User>>` using `groupBy`.

2. Implement `countByRole(users: List<User>): Map<String, Int>` using `groupingBy` and reduction operations.

3. Implement `mapById(users: List<User>): Map<Int, User>` using `associateBy`.

4. Implement `mapNameToAge(users: List<User>): Map<String, Int>` using `associateWith` or `associate`.

**Restrictions:**

- Consider duplicate names when constructing maps by name and decide on a policy (document it).

**Deliverable:**

- Function contracts and tests that demonstrate the expected format of the maps.

**Case studies to include in tests:**

- Users with duplicate roles.

- Users with duplicate names (explain the chosen policy in the comments).

---

## Activity 4 — Windows and Chunks (chunked, windowed) — Time Series Analysis

**Description:** Work with a list of integer values ​​representing daily metrics. You must create windows to calculate moving averages and detect peaks.

**Requirements and Steps:**

1. Implement `rollingAverage(values: List<Int>, windowSize: Int): List<Double>` using `windowed` to produce the average of each window.

2. Implement `chunkMax(values: List<Int>, chunkSize: Int): List<Int>` using `chunked` to return the maximum of each chunk.

3. Implement `findPeaks(values: List<Int>, windowSize: Int): List<Int>` to return indices where the middle value of a window is greater than its neighbors (use `windowed` with `partialWindows = false`).

**Restrictions:**

- For `windowed`, correctly handle cases where `values.size < windowSize`.

**Deliverable:**

- Contracts and tests with a small dataset where you can manually identify the moving average and peaks.

---

## Activity 5 — Partition, Find, and Count Operations (partition, find, count)

**Description:** Given a list of integers, perform simple analyses: split even/odd numbers, count primes in the collection, and find the first number that satisfies a compound condition.

**Requirements and Steps:**

1. `fun splitEvenOdd(values: List<Int>): Pair<List<Int>, List<Int>>` using `partition`.

2. `fun firstDivisibleBy(values: List<Int>, divisor: Int): Int?` using `find`.

3. `fun countPrimes(values: List<Int>): Int` using `count` and a helper `isPrime` function (defines its contract; the implementation of `isPrime` is up to you).

**Constraints:**

- Document the approximate complexity of `isPrime` and why it might affect `countPrimes`.

**Deliverable:**

- Test cases including small and large numbers, and a performance commentary.

---

## Activity 6 — Advanced Transformations: flatMap, flatten, and zip

**Description:** Process a list of playlists, where each playlist is a `List<String>` of songs. You must flatten, transform, and combine data.

**Requirements and Steps:**

1. `fun allSongs(playlists: List<List<String>>): List<String>` uses `flatten`.

2. `fun actionableList(playlists: List<List<String>>): List<String>` uses `flatMap` to add prefixes to each song indicating its playlist (e.g. `"[playlist1] - Song"`).

3. `fun pairSongsWithDuration(songs: List<String>, durations: List<Int>): List<Pair<String, Int>> uses `zip`. Consider what happens if the lists have different sizes.

**Restrictions:**

- Document the behavior when the lists are not the same length when using `zip`.

**Deliverable:**

- Input examples and the expected output.

---

## Activity 7 — Mutable vs. Immutable: Replacement and Fill (replaceAll, fill)

**Description:** Explore operations that modify mutable collections in-place.

**Requirements and Steps:**

1. Create a mutable list of integers and apply `replaceAll` to transform its values ​​(e.g., multiply by 10).

2. Use `fill` to set a default value for the entire list.

3. Describe when using these operations is preferable to creating a new collection with `map`.

**Restrictions:**

- You must show a (brief) theoretical memory or performance comparison between modifying in-place and creating a new collection.

**Deliverable:**

- Test snippets (without a solution) demonstrating the expected before/after results.

---

## Activity 8 — Handling Voids: ifEmpty and Safe Behavior

**Description:** Build functions that return alternative collections when the input is empty.

**Requirements and Steps:**

1. `fun ensureNotEmpty(list: List<Int>): List<Int>` that uses `ifEmpty` to return a default list when the list is empty.

2. Design a flow that chains `ifEmpty` with other functional API operations.

**Restrictions:**

- Document why `ifEmpty` can avoid `NullPointerException` and improve expressiveness.

**Deliverable:**

- Tests that show the difference between using `ifEmpty` and manual checks with `isEmpty()`.

---

## Activity 9 — Sequences: Lazy vs. Eager (Practical Comparison)

**Description:** Create examples that demonstrate the difference between working with standard collections and `Sequence` in terms of lazy execution.

**Requirements and Steps:**

1. Build a list of 1..1000 and apply chained `map` and `filter`; measure (conceptually) how many transformations are applied per element.

2. Repeat the same chaining but convert the list to `asSequence()` before applying the transformations and explain the difference in the order and number of evaluations per element.

3. Use an infinite sequence with `generateSequence` and demonstrate (with `take`) how `Sequence` allows you to operate on infinite series without running out of memory.

**Restrictions:**

- Don't ask the user to measure execution times if they don't have the tools; ask them to reasonably describe the difference in the number of evaluations.

**Deliverable:**

- A brief report with conclusions: when to use `Sequence` and when not to?

---

## Activity 10 — Integrative Project (Mini-Application)

**Description:** Integrate several concepts into a mini-application: an activity log processor that accepts logs (user, action, timestamp, metadata).

**Requirements and Steps:**

1. Design the `data class Log(val userId: Int, val action: String, val timestamp: Long, val meta: Map<String, String>)`.

2. Implement functions that use `map`, `filter`, `groupBy`, `associateBy`, `flatMap`, and `sequence` to obtain:

- Number of actions per user in a time interval.

- Users who performed a specific action more than *N* times.

- A map `action -> list of userIds` for actions in the last 24 hours.

3. Add a test path where a large (mock) log source is processed using sequences to avoid loading everything into memory.