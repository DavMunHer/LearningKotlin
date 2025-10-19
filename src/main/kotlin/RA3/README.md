## 🧠 Functional Programming Paradigm

The functional paradigm is based on **treating functions as values**, **avoiding mutable states**, and **reducing side effects**.

---

### 🔹 Functions Treated as Values ​​(Function Types)

#### 📦 1. Storage in Variables

Functions can be stored in variables, just like numbers or strings.

```kotlin
val add: (Int, Int) -> Int = { a, b -> a + b }
println(add(2, 3)) // 5
```

👉 Here `add` is a **function variable**, which stores a lambda that adds two integers.

---

#### 📩 2. Input Parameters / Call Arguments

We can **pass functions as parameters** to other functions.

```kotlin
fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
return operation(a, b)
}

println(operate(4, 5, { x, y -> x * y })) // 20
```

👉 `operate` takes a function as an argument, allowing you to reuse its logic with different behaviors.

---

#### 🎯 3. Function Return Values

A function can **return another function**.

```kotlin
fun createMultiplier(factor: Int): (Int) -> Int {
return { num -> num * factor }
}

val byTwo = createMultiplier(2)
println(byTwo(5)) // 10
```

👉 `createMultiplier` returns a **lambda function** that multiplies by a given factor.

---

### 🔹 Immutability (favored)

In functional programming, it is preferred that values ​​**not change** once created.

```kotlin
val list = listOf(1, 2, 3)
val newList = list + 4 // creates a new list
println(list) // [1, 2, 3]
println(newList) // [1, 2, 3, 4]
```

👉 `listOf` creates an **immutable** list; the original list is not modified, but a new one is created.

---

### 🔹 Function Purity

A pure function **always returns the same result** with the same arguments and **does not change the external state**.

```kotlin
fun square(x: Int) = x * x // pure

var counter = 0
fun increment(): Int { // impure
counter++
return counter
}
```

👉 `square` is pure.
👉 `increment` is impure because it modifies an external variable.

---

## ⚙️ Lambda Expressions

### 🔹 Basic Syntax

A lambda is an anonymous function.

```kotlin
val greet = { name: String -> "Hello, $name" }
println(greet("Kotlin")) // Hello, Kotlin
```

👉 `{ parameters -> body }`
If there is only one parameter, you can omit its name and use `it`.

```kotlin
val duplicate = { it * 2 }
println(duplicate(3)) // 6
```

---

## 🧩 Function and Member References

Instead of using lambdas, we can directly reference existing functions.

```kotlin
fun print(msg: String) = println(msg)

val ref = ::print
ref("Hello from reference") // Hello from reference

val names = listOf("Ana", "Luis", "Eva")
names.forEach(::println)
```

👉 `::` gets a reference to a function or property.

---

## 🧠 Functional interfaces (SAM interfaces)

A **Single Abstract Method interface** has a single abstract method and can be implemented with a lambda.

```kotlin
fun runRunnable(r: Runnable) = r. run()

runRunnable(Runnable { println("Running Runnable with lambda") })
```

👉 Kotlin allows you to use lambdas in **Java SAM interfaces** such as `Runnable`, `Comparator`, etc.
You can also create your own SAM interfaces:

```kotlin
fun interface Operation {
fun apply(a: Int, b: Int): Int
}

val sum = Operation { a, b -> a + b }
println(sum. apply(3, 4)) // 7
```

---

## 🧭 Lambdas with *receiver*

A **lambda with receiver** has implicit access to an object (`this`) within the lambda.
Used in **scope functions**.

---

### 🔹 `run`

Runs a block with `this` as the listener and returns the block's result.

```kotlin
val message = "Kotlin".run {
this.uppercase()
}
println(message) // KOTLIN
```

---

### 🔹 `with`

Same as `run`, but not an extension (the object is passed explicitly).

```kotlin
val sb = StringBuilder()
val result = with(sb) {
append("Hello, ")
append("world")
toString()
}
println(result) // Hello, world
```

---

### 🔹 `apply`

Returns the **modified** object; useful for initialization.

```kotlin
val person = Person().apply {
name = "Ana"
age = 25
}
```

---

### 🔹 `also`

Similar to `apply`, but uses `it` instead of `this`. Ideal for executing actions **without modifying the context**.

```kotlin
val list = mutableListOf(1, 2, 3).also {
println("Original list: $it")
it.add(4)
}
println(list) // [1, 2, 3, 4]
```

---

## 🌱 Quick summary

| Concept | Key | Example |
| --- | --- | --- |
| Functions as values ​​| They are treated as variables or parameters | `val f = { x: Int -> x + 1 }` |
| Immutability | Do not modify state | `new val = list + 4` |
| Pure function | No side effects | `square fun(x) = x * x` |
| Lambda | Anonymous function | `{ x -> x * 2 }` |
| Function reference | Reuse existing functions | `list.forEach(::println)` |
| SAM interface | Implement with lambda | `Runnable { ... }` |
| Lambdas with receiver | Implicit `this` access | `apply`, `run`, `with`, `also` |

---

# Practical Activity Guide (no solutions) — Functional Programming in Kotlin

---

# Activity 1 — Mathematical Operations Library (Fundamentals: Functions as Values)

**Objective:** Design a small “library” where operations (addition, subtraction, multiplication, division, exponentiation) are **values** that you can store, pass as parameters, and return from functions.

**Requirements**

- Create function types/aliases for operations with `Int` or `Double`.

- Implement a collection (map) that maps operation names (`"sum"`, `"mul"`, etc.) to functions.

- Implement a function `applyOperation(name, a, b)` that looks up the operation by name and applies it.

- Implement a `createCompoundOperation(op1, op2)` function that returns a new function that applies `op1` and then `op2` (simple composition).

**Suggested Steps**

1. Define a type alias for the operation signature.

2. Create some lambda functions and store them in variables.

3. Fill a `Map<String, OperationType>` with these functions.

4. Implement `applyOperation`, handling non-existent operations.

5. Implement `createCompoundOperation` that returns a function (function type as return).

**Acceptance Criteria**

- `applyOperation("sum", 2, 3)` produces `5`.

- The composition produces the expected effect (e.g., composing multiply by 2 and add by 3: input 4 → (4*2)+3 = 11).

- Don't use mutable global variables for the map (please ensure immutability).

**Hints (not solutions)**

- Use `typealias` for clarity.

- Return `Result` or `Either` (or use `null`) if the operation doesn't exist; decide on a strategy and document why.

- Avoid `var` unless you justify its necessity.

---

# Activity 2 — Pure Data Transformations (Immutability and Purity)

**Goal:** Create a series of pure functions that transform an immutable `User` collection, returning new collections without mutating the original ones.

**Requirements**

- Define a data class `User(val id: Int, val name: String, val active: Boolean, val score: Int)`.

- Implement pure functions (no side effects) that:

- Filter active users.

- Increase each user's `score` based on a function passed as a parameter.

- Sort by `score` and return the top N in a new list.

- Avoid mutations: never change existing objects; return new objects/collections.

**Steps**

1. Create an initial immutable list of users.

2. Implement `filterActives(users)`.

3. Implement `increaseScore(users, increaseFunction)` where `increaseFunction` is a `(Int) -> Int` function.

4. Implement `topNPorScore(users, n)`.

**Acceptance Criteria**

- The original list remains intact after applying the functions.

- All functions are deterministic and pure (same input → same output).

- You can combine functions without unexpected effects.

**Tips**

- Use `map`, `filter`, `sortedByDescending`.

- To avoid object mutation, create new `Users` with `copy(...)`.

---

# Activity 3 — Design a SAM interface and use it with lambdas

**Objective:** Define your own `fun interface` (SAM) and use it with lambdas from Kotlin.

**Requirements**

- Define a `Validator<T>` fun interface with an `isValid(value: T): Boolean` method.

- Implement validators for: basic email, numeric range, and minimum string length.

- Create a `filtrarValidos(items, validator)` function that returns valid items.

- Show how to use lambdas to instantiate these validators.

**Steps**

1. Declare the `fun interface`.

2. Implement validators as lambda variables (not classes).

3. Use `filtrarValidos` with a list of examples.

**Acceptance Criteria**

- You can create `Validator` instances with a single lambda.

- `filtrarValidos` works for various generic types (`String`, `Int`).

**Tips**

- `fun interface` allows you to create the implementation by passing a lambda directly: `val v = Validator { ... }`.

- Keep the validation logic pure (no logging or effects).

---

# Activity 4 — Lambdas with a receiver and building a small DSL

**Objective:** Practice using lambdas with a receiver by designing a small builder (DSL) to build a `Pizza` or `Query`.

**Requirements**

- Design a `Pizza` class with properties: `size`, `toppings` (list), `sauce`.

- Create a `pizza { ... }` function that uses a lambda with a receiver to configure the pizza (`this` will be the builder).

- The lambda will allow calls like `size = "M"`, `topping("pepperoni")`, or `addTopping("olive")`.

**Steps**

1. Design a `PizzaBuilder` with a methodInternal mutable objects and properties.

2. Implement the `pizza(block: PizzaBuilder.() -> Unit): Pizza` function, which creates the builder, executes the block, and returns the immutable `Pizza`.

3. Show examples of using the DSL.

**Acceptance Criteria**

- The `pizza { size = "L"; addTopping("cheese") }` call produces a correct `Pizza` object.

- The DSL uses implicit `this` within the block (a lambda with a receiver).

**Hints**

- The builder can use `apply` internally for initialization.

- Ensure the final `Pizza` object is immutable (`val` properties).

---

# Activity 5 — Scope functions in real-world context

**Objective:** Compare `run`, `with`, `apply`, and `also` in various initialization/usage scenarios.

**Requirements**

- Create examples for each scope function that justify its use:

- `apply` to configure objects (returns the receiver).

- `also` to perform side effects (logging) without changing the object.

- `run` to execute a block with `this` and obtain a result (transformation).

- `with` to group operations on an object and return a result.

- For each case, explain why you chose one or the other.

**Steps**

1. Implement an initialization case for an `HttpRequest` (or `StringBuilder`) with `apply`.

2. Implement a pipeline where `also` records the intermediate state without breaking the chain.

3. Use `run` to convert an object to something else (e.g., parsing).

4. Use `with` to group multiple operations and return a summary.

**Acceptance Criteria**

- The examples make it clear when to use each function.

- There are no unexpected mutations beyond what you justify.

- The blocks use `this` or `it` correctly as appropriate.

**Hints**

- Think of `apply` = setting and returning the object; `also` = returning the object after doing something with `it`.

- `run` and `with` are useful when you want to return a different result to the receiver.

---

# Activity 6 — Final Project: Data Pipeline Mini-App (Integration)

**Objective:** Integrate all the concepts into a mini-project: read (mock) a list of inputs, validate them, transform them purely, and execute controlled actions (side effects).

**Requirements**

- Mock input: list of `RawItem` (can be strings).

- Step 1: `parse` (pure) → `Item?` (nullable if invalid).

- Step 2: `validate` using the `fun interfaces` of Activity 3.

- Step 3: Transform using functions as values ​​(map with passed functions).

- Step 4: Construct a `Report` with lambdas and receiver (DSL).

- Step 5: Record summary with `also` (secondary action) and send (mock) with a separate impure function.

**Steps**

1. Design types: `RawItem`, `Item`, `Report`.

2. Implement pure functions `parse(raw): Item?` and `transform(item): Item`.

3. Create reusable validators via `fun interfaces`.

4. Chain everything together in a functional pipeline (no mutating `for`).

5. Finally, use `also` for logging and a separate function for `submit` — keep the impurity localized.

**Acceptance Criteria**

- The flow is readable, composable, and allows for swapping parts (e.g., another validator).

- Pure functions are testable in isolation.

- Only the `submit` function performs external effects; the rest remains pure.

**Hints**

- Think of `sequence` of operations: `raws.mapNotNull(::parse).filter(::isValid).map(::transform)...`

- Keep prints/logs in `also` and the final action outside the pure pipeline.

---

# Extra Challenges (Optional)

1. Add pure memoization for an expensive operation (returns a memoized function).

2. Implement a small library that combines validators with `and`/`or` (validator composition) using functions as values.

3. Export the `Report` using a lambda with a receiver that generates JSON (without using external libraries).