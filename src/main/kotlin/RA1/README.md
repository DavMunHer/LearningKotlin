## 🧩 **1\. Data Types**

Kotlin has basic types similar to Java, but **everything is an object**.

```kotlin
val integer: Int = 42
val decimal: Double = 3.14
val character: Char = 'A'
val text: String = "Hello Kotlin"
val boolean: Boolean = true
```

You can also let **Kotlin infer the type**:

```kotlin
val number = 10 // Int
val message = "Hello" // String
```

---

## 📦 **2\. Variables, Expressions, and Literals**

- `val`: immutable (like `final` in Java)

- `var`: mutable

```kotlin
val name = "Ana"
var age = 20

age = 21 // allowed
// name = "Luis" ❌ error: val cannot be reassigned
```

**Expressions and Literals:**

```kotlin
val sum = 3 + 5 // expression
val text = "Hello" // String literal
val number = 10 // Int literal
```

---

## 💬 **3\. Console input and output**

```kotlin
fun main() {
print("Enter your name: ")
val name = readLine() // read a line from the console
println("Hello, $name!") // output
}
```

---

## 🔀 **4\. Control Structures**

### `if`

```kotlin
val age = 18
if (age >= 18) {
println("Older")
} else {
println("Younger")
}

// can also be an expression
val message = if (age >= 18) "Older" else "Younger"
```

### `when`

```kotlin
val day = 3
val dayName = when (day) {
1 -> "Monday"
2 -> "Tuesday"
3 -> "Wednesday"
4 -> "Thursday"
5 -> "Friday"
else -> "Weekend"
}
println(dayName)
```

---

## 🔁 **5\. Loops**

### `while`

```kotlin
var i = 1
while (i <= 3) { 
println("i = $i") 
i++
}
```

### `do..while`

```kotlin
var j = 1
do { 
println("j = $j") 
j++
} while (j <= 3)
```

### `for`

```kotlin
for (k in 1..5) { // includes 5 
println(k)
}

for (letter in "Kotlin") { 
println(letter)
}
```

---

## ⚙️ **6\. Functions**

### 🧱 Block body

```kotlin
fun add(a: Int, b: Int): Int {
val result = a + b
return result
}
```

### ➡️ Expression body

```kotlin
fun subtract(a: Int, b: Int): Int = a - b
```

---

### 🧩 Parameters with default values

```kotlin
fun greet(name: String = "Unknown") {
println("Hello, $name")
}

greet() // Hello, Unknown
greet("Ana") // Hello, Ana
```

---

### 📍 Positional and named arguments

```kotlin
fun showPerson(name: String, age: Int) {
println("$name is $age")
}

showPerson("Carlos", 25) // positional
showPerson(age = 30, name = "Laura") // named
```

---

### 🧠 Return value inference (expression body only)

```kotlin
fun multiply(a: Int, b: Int) = a * b // Kotlin infers that it returns Int
```

---

### 🚫 `Unit` type (equivalent to `void` in Java)

```kotlin
fun showMessage(): Unit {
println("Hello from a function that doesn't return anything")
}

// You can also omit `Unit`
fun showMessage2() {
println("Another way")
}
```

---

### 🔗 Infix Functions

They are used with the `infix` keyword and allow for a more natural syntax.

```kotlin
infix fun Int.add(b: Int): Int = this + b

fun main() {
val result = 5 add 3 // instead of 5.add(3)
println(result)
}
```

---

### 🌐 Extension Functions

They allow you to add functions to existing classes without modifying them.

```kotlin
fun String.repeatTimes(n: Int): String {
return this.repeat(n)
}

fun main() {
println("Hello ".repeatTimes(3)) // Hello Hello Hello
}
```

---

# 🧭 Practical Activities Guide in Kotlin

Each activity has a **main objective**, a **step-by-step description**, and the **concepts you should apply**.

You can complete them all in a single `Main.kt` file or create one file per activity.

---

## 🟢 **Activity 1: Data Types, Variables, and Expressions**

### 🎯 Objective

Become familiar with basic Kotlin types, the difference between `val` and `var`, and how to use simple expressions.

### 🧩 Instructions

1. Create several variables that represent:

- Your name, age, height, and whether you are of legal age or not.

2. Use **explicit types** in some and **inferred types** in others.

3. Evaluate an expression that determines whether you are of legal age and save it in a Boolean variable.

4. Display all values ​​in the console with a formatted message.

### 💡 Applied Concepts

- Data types (`Int`, `Double`, `Boolean`, `String`)

- Variables (`val`, `var`)

- Expressions and literals

- String concatenation or interpolation (`"Hello, $name"`)

---

## 🟡 **Activity 2: Console Input and Output**

### 🎯 Objective

Practice reading user input and displaying it formatted on the screen.

### 🧩 Instructions

1. Ask the user to enter their name and age.

2. Store the data in variables.

3. Display a custom message with that information.

4. Calculate how old the user will be in 5 years and display it as well.

### 💡 Applied Concepts
icados

- `readLine()`

- Type conversion (`toInt()`, `toDouble()`, etc.)

- String interpolation

---

## 🟠 **Activity 3: Control structures (`if`, `when`)**

### 🎯 Objective

Learn to make decisions based on conditions and values.

### 🧩 Instructions

1. Ask the user for a numerical grade (from 0 to 10).

2. Use an `if` or `when` statement to determine:

- Whether the student has **failed**, **passed**, **excellent**, or **excellent**.

3. Display a different message depending on the case.

4. (Bonus) Use `when` to assign a day of the week text based on a number from 1 to 7.

### 💡 Applied Concepts

- `if` and `when` conditionals

- Code blocks and conditional expressions

---

## 🔵 **Activity 4: Loops (`while`, `do..while`, `for`)**

### 🎯 Objective

Repeat actions and iterate through sequences.

### 🧩 Instructions

1. Create a `for` loop that prints the numbers from 1 to 10.

2. Create a `while` loop that prompts the user for a password until they enter the correct one.

3. Use `do..while` to display a menu of options (e.g., “1. Say hello,” “2. Exit”) until they choose to exit.

### 💡 Applied Concepts

- `for`, `while`, `do..while` loops

- Conditions and repetition

- Data entry in loops

---

## 🟣 **Activity 5: Functions**

### 🎯 Objective

Organize code into reusable functions and understand the different types of functions.

### 🧩 Instructions

1. Create a function that receives two numbers and returns their sum.

2. Create another function that receives a name and greets the user (without returning anything).

3. Create a function with **default parameters** that displays a generic greeting if no name is passed.

4. Call functions using **positional** and **named** arguments.

5. Try defining a function with an **expression body** (a single line with `=`).

6. Declare a function that uses the `Unit` return type.

7. Write an infix function that performs a simple operation between two numbers.

8. Create an extension function that extends the behavior of a base type (e.g., an extra method for `String` or `Int`).

### 💡 Applied Concepts

- Function Declarations

- Block and Expression Body

- Parameters with Default Values

- Named and Positional Arguments

- `Unit` Type

- Infix Functions

- Extension Functions

---

## 🔴 **Activity 6: Integration Project – Mini User Registration**

### 🎯 Objective

Combine all concepts into a single program.

### 🧩 Instructions

1. Display a main menu with options:

- 1️⃣ Add user

- 2️⃣ Show users

- 3️⃣ Calculate average age

- 4️⃣ Exit

2. Create a mutable list to store name and age pairs.

3. Implement each action in a **different function**.

4. Use a `do..while` loop to keep the menu active until you choose "Exit".

5. Use `if` or `when` to decide which function to run.

6. Within the functions, use console input and custom messages.

7. Add an extension function that calculates the average of the recorded ages.

8. Include an infix function that compares ages between two users (e.g., `"John" isOlderThan"Pedro"`).

### 💡 Applied Concepts

- All of the above: variables, types, input/output, loops, control structures, functions, etc.

- Mutable lists and collections (`mutableListOf`)

---

## 🧩 **Bonus Activity (Optional): Secret Number Game**

### 🎯 Objective

Practice control flow, loops, and functions.

### 🧩 Instructions

1. Generate a random number between 1 and 100.

2. Ask the user to guess the number.

3. Indicate whether their guess is **greater** or **less** than the secret number.

4. Repeat until the user guesses correctly and display how many guesses it takes.

5. Use functions to break down the logic (e.g., `readGuess()`, `evaluateGuess()`, `showResult()`).

### 💡 Applied Concepts

- While Loops

- Conditionals

- Functions with Returns

- Basic Types and Operators

---