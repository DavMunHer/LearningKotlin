## 🧩 1. Declaring Generic Functions and Classes (and Interfaces)

**Concept:**
**Generic functions** and **classes** allow you to work with types that are specified *later*, rather than fixing them when defining the function or class.
This provides **flexibility** and **reusability**.

### 🔹 Example: Generic Function

``` kotlin
fun <T> printElement(element: T) {
println("The element is: $element")
}

printElement(42) // Int
printElement("Hello") // String
```

👉 `T` is a type parameter. It is replaced with the actual type on each call.

---

### 🔹 Example: Generic Class

```kotlin
class Box<T>(val content: T) {
fun get(): T = content
}

val boxInt = Box(10)
val boxString = Box("Text")
```

👉 The `Box<T>` class can contain **any type**.
There's no need to define a separate class for each data type.

---

### 🔹 Example: Generic Interface

```kotlin
interface Repository<T> {
fun save(item: T)
fun getAll(): List<T>
}

class MemoryRepository<T> : Repository<T> {
private val data = mutableListOf<T>()
override fun save(item: T) = data.add(item)
override fun getAll(): List<T> = data
}
```

👉 Here, the type `T` can be `User`, `Product`, etc.
This way, the repository can be reused.

---

## 🌀 2. Erasure (*type erasure*) and reification (*reified*) of type parameters

**Concept:**
In the **JVM**, generic types are **erased at runtime** (*erasure*).
That is, the program **doesn't know what type `T` was** when the code runs.

---

### 🔹 Erasure Example

```kotlin
fun <T> isListOfStrings(list: List<T>): Boolean {
return list is List<String> // ❌ Error: Cannot check for instance of erased type
}
```

👉 The concrete type (`String`) cannot be checked at runtime because `T` has been **erased**.

---

### 🔹 Example with `reified` (type recreation)

When using **inline functions**, we can **recreate the generic type** with `reified`.

```kotlin
inline fun <reified T> isInstanceOf(obj: Any): Boolean {
return obj is T
}

println(isInstanceOf<String>("Hello")) // true
println(isInstanceOf<Int>("Hello")) // false
```

👉 `reified` allows you to **maintain type information** at runtime.

It only works in **inline functions**.

---

## ⚖️ 3. Declaration-site and use-site variance

**Variance** controls **how generic types with inheritance behave**.

---

### 🔹 Example without variance (typical error)

```kotlin
open class Animal
class Dog : Animal()

fun printAnimals(animals: List<Animal>) {
for (a in animals) println(a)
}

val dogs: List<Dog> = listOf(Dog())
// printAnimals(dogs) ❌ Error: List<Dog> is not List<Animal>
```

👉 Although `Dog` inherits from `Animal`, `List<Dog>` is **not** `List<Animal>`.

---

### 🔹 Declaration-site variance

`out` (covariance) and `in` (contravariance) are used **in the definition** of the generic type.

```kotlin
interface Producer<out T> { // out = covariant
fun get(): T
}

interface Consumer<in T> { // in = contravariant
fun process(item: T)
}
```

- `out` → **only produces (returns)** `T`, never consumes it.

Allows subtyping (`Producer<Dog>` can be used as `Producer<Animal>`).

- `in` → **only consumes (receives)** `T`, never returns it.

Allows supertyping (`Consumer<Animal>` can receive `Consumer<Dog>`).

---

### 🔹 Use-site variance

Used directly when **using** a generic type, not when declaring it.

```kotlin
fun printAll(animals: List<out Animal>) {
for (a in animals) println(a)
}
```

👉 Here, `out` applies **to the use** of the parameter (`use-site variance`), not to its definition.

---

## 🏷️ 4. Type aliases (`typealias`)

**Concept:**
They allow you to create an **alternative name** (more readable or shorter) for a complex or generic type.

### 🔹 Simple example

```kotlin
typealias Integers = List<Int>

val numbers: Integers = listOf(1, 2, 3)
```

👉 `Integers` is just an **alias**, not a new type.

---

### 🔹 Most useful example

```kotlin
typealias Callback<T> = (result: T) -> Unit

fun loadData(callback: Callback<String>) {
callback("Loaded Data")
}

loadData { println(it) }
```

👉 `Callback<T>` is just a cleaner way of writing `(T) -> Unit`.

---

## 🧠 General summary

| Concept | What it does | Keyword / example |
| --- | --- | --- |
| **Generics** | Reuse code with variable types | `<T>` |
| **Erasure** | JVM forgets generic types at runtime | `T` is erased |
| **Reified** | Allows the actual type to be used at runtime | `inline fun <reified T>` |
| **Variance** | Control how generic types are inherited | `out`, `in` |
| **Type aliases** | Create clearer names for types | `typealias` |

---

# Activity A — Generic classes and functions (fundamentals)

**Objective:** Design a small, reusable generic collection and helper functions that work with it.

**What to Deliver**

- A generic `Container<T>` class (minimum public API specified below).

- At least 3 generic utility functions that operate on `Container<T>`.

- A `Main.kt` file with usage examples (automated tests are not required, but examples demonstrating the uses are).

**Restrictions / Requirements**

- `Container<T>` must expose at least: `add(item: T)`, `remove(item: T): Boolean`, `isEmpty(): Boolean`, `getAll(): List<T>`.

- Don't use external libraries; use only the Kotlin stdlib.

- Consider immutability when it makes sense (briefly explain in the comments whether you're making it mutable or immutable).

**Suggested Steps**

1. Design the API: Decide whether `Container` will be mutable or immutable and why.

2. Implement the class with an internal backing (list, set, etc.), but **do not** publish the details.

3. Create generic functions:

- E.g., `filter(cont: Container<T>, pred: (T)->Boolean): Container<T>`

- E.g., `map(cont: Container<T>, transform: (T)->R): Container<R>`

- E.g., `count(cont: Container<T>, pred: (T)->Boolean): Int`

4. Create usage examples: `Container<Int>`, `Container<String>`, `Container<MyDataClass>`.

**Test/Acceptance Cases**

- `add` and `remove` work for different types.

- `map` produces a `Container` of a different type (e.g., `Int` -> `String`).

- `filter` returns only elements that meet the condition.

**Hints (no solution provided)**

- To `map`, you need the function to accept and return a different type, `R`.

- If you make `Container` immutable, your helper functions will return new `Containers` instead of mutating it.

---

# Activity B — Erasure vs. reified (tests and utilities)

**Goal:** Demonstrate the problem with *type erasure* and use `inline` + `reified` to retrieve type information at runtime.

**What to deliver**

- A generic function that deliberately attempts to type-check a collection at runtime (sample problem).

- An inline + reified version that can perform this check.

- A short document (README.md or comments) explaining why the first fails and why the second works.

**Restrictions / Requirements**

- Don't use advanced reflection beyond what Kotlin/Java allows (avoid solutions with `javaClass` to circumvent the goal).

- You must include examples showing a parameterized list/collection (e.g., `List<String>`, `List<Any>`).

**Suggested Steps**

1. Implement a function `isListOfTypeX(list: List<*>) : Boolean` that attempts to check if all elements are of a certain type **without** using reified. Observe and document what happens.

2. Implement an inline fun <reified T> isTypeCollection(collection: Collection<*>): Boolean that does the check with `is T` and returns true/false.

3. Add examples with heterogeneous and homogeneous lists.

**Test/Acceptance Cases**

- The non-reified version should not be able to correctly evaluate `is List<String>` (explain the error or limitation).

- The reified version should return true for a List<String> when you call `isTypeCollection<String>(...)`.

**Hints**

- Remember that reified only works in inline functions.

- Consider checking individual elements of the collection as an alternative — this demonstrates the concept without fooling the runtime.

---

# Activity C — Variance: Declaration-site and Use-site

**Objective:** Understand and apply `out` and `in` both in the definition of types (declaration-site) and in their use (use-site), building producers/consumers that respect variance.

**What to deliver**

- Two interfaces: `Provider<T>` and `Processor<T>`, implemented to exhibit covariance/contravariance.

- A practical example: a function that receives `List<out Animal>` as a parameter (use-site) and another that receives `Processor<in Dog>` (declaration-site in the interface).

- Written explanation (comments or README) of why `out` or `in` are used in each case and what errors this prevents.

**Restrictions / Requirements**

- Create a small class hierarchy: `Animal` ← `Dog` ← `Bulldog` (or similar).

- `Supplier<T>` must expose a method to get `T`, but not to receive it.

- `Processor<T>` must expose a method to process/receive `T`, but not to return it.

**Suggested Steps**

1. Define `interface Supplier<out T>` with `fun get(): T`.

2. Define `interface Processor<in T>` with `fun process(item: T)`.

3. Create concrete implementations: `DogSupplier`, `AnimalProcessor`.

4. Write functions that accept:

- `fun listAnimals(aAnimals: List<out Animal>) — use-site example.

- `fun train(processor: Processor<Dog>)` — and show how a `Processor<Animal>` may or may not fit depending on the variance declaration.

5. Try compiling intentionally flawed examples to see compiler messages and document what signals the compiler gives you.

**Test/Acceptance Cases**

- A `Provider<Dog>` must be assignable to a variable of type `Provider<Animal>` if you use `out`.

- A `Processor<Animal>` must be able to process `Dog` if `Processor` was declared `in`.

- Show a call that compiled before and now fails without the variance, explaining why it is unsafe.

**Hints**

- Ask yourself: Does this type "produce" (only returns) T, or "consume" (only receive) T? If it produces → `out`. If it consumes → `in`.

- For use-site purposes, think about specific situations where you don't want to modify the interface definition but you do want to modify the contract for a specific call.

---

# Activity D — Type Aliases and Documentation

**Goal:** Improve the readability and maintainability of complex types by creating aliases, and apply aliases in combinations with generics.

**What to deliver**

- At least 3 useful typealiases applied to the project: for example, for generic callbacks, composite types (`Map<String, List<Int>>`), or suspended functions.

- A mini-module that uses these aliases in function signatures and documentation (KDoc).

**Restrictions / Requirements**

- Include at least one generic alias: `typealias Callback<T> = (T) -> Unit`.

- Document with a brief KDoc why each alias exists and when to use it.

**Suggested Steps**

1. Identify a couple of complex signatures in your other exercises (A–C) that would benefit from an alias.

2. Declare the aliases and refactor the signatures to use them.

3. Add KDoc to each alias, explaining its purpose.

**Test / Acceptance Cases**

- Refactored signatures must compile exactly as before (aliases don't change the type, just the name).

- Documentation must clearly explain the semantics of the alias.

**Tips**

- A good alias improves the intent of the code (e.g., `UserId = String` makes the API more readable).

- Don't overuse aliases to hide types; They should clarify, not confuse.

---

# Activity E — Integrative Project (Final Task)

**Objective:** To combine what has been learned into a small project: a generic mini-repository with producers/consumers, type checking with `reified`, and aliases for callbacks.

**What to deliver**

- Project with:

- Generic `Repository<T>` (use variance if appropriate).

- Provider and Processor integrated with the repository.

- Inline `<reified T> searchByType(repos: Repository<Any>): List<T>` function that filters by type at runtime.

- Use of `typealias` for callbacks/handlers.

- README explaining design decisions (mutability, where you put `out`/`in`, why `reified` is necessary where you used it).

**Restrictions/Requirements**

- Must compile in Kotlin/JVM (current stable version).

- Use manual tests in `main` demonstrating integration (lists, filters, processing).

**Suggested Steps**

1. Design the generic repository and its public API.

2. Decide where to apply variance (whether repository methods only return or also accept T).

3. Implement `lookupByType` using `reified` to demonstrate type filtering.

4. Add `typealiases` for save/read callbacks.

5. Document and show examples in `main`.

**Test/Acceptance Cases**

- `lookupByType<Dog>(repository)` returns only instances of `Dog`.

- You can have a `Repository<Animal>` and use it with `Provider<Dog>` or `Processor<Animal>` if your variance allows.

- Signatures with `typealias` are readable and used correctly.

**Hints**

- If your repository has `save(item: T)` methods, think twice before declaring `out T` — that would prevent saving.

- For `searchByType`, the idea is to iterate/filter the items by checking `is T` from the `reified` function.

---

# Suggested evaluation and checklist (to correct yourself)

- Each activity includes a brief documentation/summary of decisions.

- Implementations compile, and the examples in `main` show correct behavior.

- You used `reified` only in inlined functions and documented why.

- You applied `out`/`in` when the type contract was exclusively producer or consumer; you documented previous errors.

- `typealias` improves readability and is not merely obfuscation.

- You added at least two “intentionally flawed” cases and explained why the compiler rejects them (screenshot or copy/paste the compiler message)

---

# Optional Extensions (Challenge)

- Implement a serializable version of `Container<T>` and document how variance/erasure affects serialization.

- Write unit tests (JUnit or kotest) that verify `lookupByType` and `map`/`filter` semantics.

- Experiment with `star-projections` (`List<*>`) and document when they are useful.