## 🧩 1. Property Delegation

**Concept:**
Allows you to delegate the access logic (`get`/`set`) of a property to another object. Kotlin already includes standard delegates like `lazy` and `observable`, or you can create your own.

**Example:**

```kotlin
import kotlin.reflect.KProperty

class LogDelegate { 
private var value: String = "" 
operator fun getValue(thisRef: Any?, property: KProperty<*>): String { 
println("Reading '${property.name}' = $value") 
return value 
} 

operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: String) { 
println("Assigning '${property.name}' = $newValue") 
value = newValue 
}
}

class User { 
var name: String by LogDelegate()
}

fun main() { 
val u = User() 
u.name = "Alice" 
println(u.name)
}
```

🧠 *Here the property `name` delegates the management of its value to `LogDelegate`. This pattern is used, for example, for logging, persistence, or validation.*

---

## 👀 2. DSL (Domain-Specific Language) as a design pattern

**Concept:**
An **internal DSL** allows you to use the Kotlin language with a natural language-like syntax to describe complex configurations or structures.

Example of a simple DSL for building menus:

```kotlin
class Menu {
private val items = mutableListOf<String>()
fun item(name: String) = items.add(name)
fun show() = items.forEach { println("🍽 $it") }
}

fun menu(block: Menu.() -> Unit): Menu {
val m = Menu()
m.block()
return m
}

fun main() {
val myMenu = menu {
item("Hamburger")
item("French Fries")
item("Coke")
}
myMenu.show()
}
```

💡 *The `{ ... }` block runs with a `Menu` receiver, which gives a clean DSL-like syntax.*

---

## 🧱 3. Builder Functions (Type-Safe) builders)

**Concept:**
These are functions that **build complex objects**, ensuring types at compile time, using **receiver lambdas**.

Example (HTML structure):

```kotlin
class Tag(val name: String) { 
private val children = mutableListOf<Tag>() 
fun tag(name: String, block: Tag.() -> Unit) { 
val child = Tag(name) 
child.block() 
children.add(child) 
} 

fun render(indent: String = ""): String { 
val content = children.joinToString("\n") { it.render("$indent ") } 
return "$indent<$name>\n$content\n$indent</$name>" 
}
}

fun html(block: Tag.() -> Unit): Tag { 
val root = Tag("html") 
root.block() 
return root
}

fun main() { 
val doc = html { 
tag("body") { 
tag("h1") {} 
tag("p") {} 
}
}
println(doc.render())
}
```

🧩 *Here we use a **type-safe builder**: you can only nest `Tag` within `Tag`, ensuring structural consistency.*

---

## 🪄 4. Lambdas with receiver (implicit `this`)

**Concept:**
These are lambda functions where you can access the members of a receiving object without having to explicitly name it.

They are used in builders, `apply`, `run`, DSLs, etc.

Example:

```kotlin
class Config {
var host: String = ""
var port: Int = 0
}

fun configureServer(block: Config.() -> Unit): Config {
val cfg = Config()
cfg.block() // Here `this` is Config
return cfg
}

fun main() {
val server = configureServer {
host = "localhost"
port = 8080
}
println("${server.host}:${server.port}")
}
```

🧠 *Clean Syntax: you don't have to write `cfg.host`, just write `host`.*

---

## ⚡ 5. `invoke` Convention

**Concept:**
Allows an object to behave like a function. If you define `operator fun invoke(...)`, you can call the object with parentheses.

Example:

```kotlin
class Multiplier(private val factor: Int) {
operator fun invoke(x: Int): Int = x * factor
}

fun main() {
val double = Multiplier(2)
println(double(5)) // equivalent to double.invoke(5)
}
```

💡 *Widely used to build clean APIs or dynamic configurations.*

---

## 📚 6. Examples of real-life DSLs in Kotlin

### a) `kotlinx.html`
**It's a DSL for generating HTML in a safe and structured way:**

```kotlin
import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun main() {
val page = createHTML().html {
head {
title { +"My Page" Kotlinx.html" }
}
body {
h1 { +"Welcome" }
p { +"Generated with Kotlinx.html" }
}
}
println(page)
}
```

➡️ *Each HTML tag is a function with a listener, and the `+` operator inserts text.*

---

### b) `Exposed`

**JetBrains DSL for safe and fluent SQL.**

```kotlin
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 50)
    val age = integer("age")
    override val primaryKey = PrimaryKey(id)
}

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        SchemaUtils.create(Users)
        Users.insert {
            it[name] = "Alice"
            it[age] = 25
        }
        for (u in Users.selectAll()) {
            println("${u[Users.name]} - ${u[Users.age]}")
        }
    }
}
```

🧱 *This is an example of a DSL for building type-safe SQL queries without using strings.*

---

## 📈 7. Relationship to Learning Objectives

| Objective | Relationship to Concepts Covered |
| --- | --- |
| **Observer Pattern** | It can be implemented using observable delegates (`Delegates.observable`). |
| **Property Delegation** | Allows extending behaviors without inheritance. |
| **Pattern Builder** | Achieved with builder functions + receiver lambdas. |
| **Builder Functions** | Allows you to create secure hierarchical structures. |
| **Clean Syntax** | Kotlin offers receiver and invoke lambdas for expressiveness. |
| **DSL vs. API** | A DSL uses the language to *describe*; an API to *invoke functions*. |
| **Internal DSLs** | They are created by combining builders, receiver lambdas, and invokes. |
| **Real Examples** | `kotlinx.html`, `Exposed`, `Gradle Kotlin DSL`. |

---
# Activity 1 — Property Delegation and Basic Observer

**Objective:** Implement custom property delegates and apply the Observer pattern to react to state changes.

**Prerequisites:** Familiarity with property delegates in Kotlin (concept `getValue`/`setValue`), `Delegates.observable` (concept), classes, and interfaces in Kotlin.

**Steps (what to do):**

1. Design an `Observer<T>` interface that receives notifications of changes to a `T` value.

2. Create a `Subject<T>` class that maintains a list of `Observer<T>` and allows subscribing/unsubscribing and notifications.

3. Implement an `ObservableDelegate<T>` property delegate that:

- Internally stores the value.

- When the value changes (in `setValue`), call `Subject` to notify observers.

4. Use that delegate in a `UserSettings` class with multiple properties (e.g., `theme`, `volume`, `language`) and register observers that log or print changes.

5. Add a variant that validates values ​​(e.g., `volume` must be between 0 and 100) and handle invalid assignments (reject or normalize them).

**Restrictions:** Don't use `Delegates.observable` directly: implement your own delegate to understand the mechanism.

**Conceptual hints:**

- The delegate requires `operator fun getValue(...)` and `operator fun setValue(...)`.

- The Observer pattern separates the "subject" (the one who changes) from the "observers" (those who react).

**Checkpoints/proofs you should be able to demonstrate:**

- Changing `theme` notifies all observers.

- Validating `volume` prevents out-of-range states.

- You can add and remove observers dynamically.

**Deliverable:** A mini README describing the API you designed and usage examples (not including the implementation).

**Evaluation criteria (self-assessment):**

- ✅ (3 pts) Delegate works and notifies.

- ✅ (2 pts) Observer management (add/remove).

- ✅ (2 pts) Validation works.

- ✅ (1 pt) Clear documentation.

---

# Activity 2 — Simple DSL: Menu Builder (Builders and Receiver Lambdas)

**Objective:** Design a type-safe internal DSL to build hierarchical menus using receiver lambdas and builders.

**Prerequisites:** Receiver lambdas (`A.() -> Unit`), extension functions, builders, and object composition.

**Steps (what to do):**

1. Define classes representing `Menu`, `Section`, and `MenuItem`.

2. Implement receiver builder functions such as `menu { ... }`, `section("name") { ... }` and `item("label") { ... }`.

3. Ensure that only allowed `MenuItems` or subsections can be created within the `section` block (think API-wise so it's difficult to make mistakes).

4. Design the API to generate a text (or JSON) representation of the final menu.

5. Add optional capabilities: mark items as `disabled`, add shortcuts, or descriptions.

**Restrictions:** Don't use complex reflections; the focus is on API design and ergonomics.

**Conceptual hints:**

- Use lambdas with receivers to allow `section { item(...) }` without prefixes.

- For additional security, consider sealed interfaces or scope markers (concept) that limit what can be done within each block.

**Checkpoints:**

- Create a menu with at least two sections and multiple items.

- Render it to text and JSON (this can be a `toJson()` method you build manually).

- Try using the API incorrectly and see if the compiler helps (e.g., calling `menu.item(...)` outside of a `section` — how do you prevent this?).

**Deliverable:** DSL documentation (example usage) and a test case demonstrating the structure

nerada.

**Assessment Criteria:**

- ✅ (3 pts) Clear and fluid API.

- ✅ (2 pts) Basic type security (if applicable).

- ✅ (2 pts) Correct representation (text/JSON).

---

# Activity 3 — `invoke` and DSL for configurations (`invoke` convention + delegates)

**Objective:** Create a clean API where objects act as functions (using `operator fun invoke`) and use property delegation for configurable values.

**Prerequisites:** Familiarity with `operator fun invoke`, property delegates, and builders.

**Steps (what to do):**

1. Design a `ConfigBlock` class that can be called as a function to add sub-configurations: e.g. `server { ... }` or `database { ... }` using a `config` object.

2. Implement `operator fun invoke(block: ConfigBlock.() -> Unit)` in a `Config` container.

3. Use delegates for configuration properties that:

- Can have default values.

- Support lazy loading if the configuration requires computation.

4. Add the ability to bind properties to external variables (e.g., loading `host` from an environment variable) using a special `EnvDelegate` delegate.

5. Create usage examples like:

```nginx
config {
server {
host = "localhost"
port = 8080
}
database {
url = env("DB_URL")
}
}
```

— but don't show the final implementation here.

**Conceptual Hints:**

- `invoke` makes usage more visually natural (`obj { }`).

- A delegate can encapsulate the logic of reading environment variables.

**Checkpoints:**

- Be able to create the configuration structure in code.

- Delegates provide defaults and/or environment reading.

- `invoke` offers natural syntax.

**Deliverable:** Usage examples and a document explaining how each delegate gets its value (source: default, env, lazy, etc.).

**Evaluation Criteria:**

- ✅ (3 pts) Clean syntax with `invoke`.

- ✅ (3 pts) Delegates work for defaults/env.

- ✅ (1 pt) Documentation.

---

# Activity 4 — Design a *Exposed* Mini-DSL (Type-Safe SQL)

**Objective:** Understand the design of *type-safe* DSLs for queries (such as Exposed), focusing on the API without actually using a DB.

**Prerequisites:** Level classes, operators and functions, basic knowledge of SQL.

**Steps (what to do):**

1. Define a `Table` abstraction with columns (`Column<T>`).

2. Design functions to create columns in a `Users : Table()` object (e.g., `val name = varchar("name", 50)`).

3. Sketch a small API for building select/insert/update functions that is *type-safe*: `select { where { Users.age greater 18 } }` or similar.

4. Do not implement execution against a DB; Instead, implement a renderer that transforms the query into a valid SQL string.

5. Ensure the system prevents (or hinders) common compile-time errors, for example:

- Do not allow comparing columns of incompatible types.

- Simple syntax validation at the type level.

**Conceptual Hints:**

- Columns can be objects that implement operators (`compareTo`, `plus`, etc.) or methods (`greater`, `like`).

- The renderer traverses the query structure and produces SQL.

**Checkpoints:**

- You can define `Users` with columns.

- Construct a `select` query with `where` that renders correct SQL.

- Try writing invalid queries to see what the compiler detects (e.g., `name > 5` should fail).

**Deliverable:** Several example queries and the SQL they render.

**Assessment Criteria:**

- ✅ (4 pts) Expressive and type-safe API.

- ✅ (3 pts) Consistent renderer.

- ✅ (1 pt) Documentation and examples.

---

# Activity 5 — Integration Project: Server-side DSL with Endpoints + Observer + Exposed-like Queries

**Objective:** Combine all the concepts into a mini project: a DSL to define a mock HTTP server (you don't need to implement it), with configurations, endpoints, observable delegates, and queries to an Exposed-like persistence layer (renderer only).

**Prerequisites:** All of the above.

**Steps (what to do):**

1. Design the desired DSL syntax (specify how a server, endpoints, handlers, and middleware are declared). Expected usage example (signature only):

```arduino
server {
config {
port = 8080
}
route("/users") {
get {
//handler
}
post { /* handler */ }
}
}
```

2. Integrate configuration delegates for `port`, `host`, and `logLevel`. Remove or update configuration at runtime and have observers react (dynamic logging).

3. Define how a handler can construct queries using the SQL mini-APIfrom exercise 4 and render them (do not execute them).

4. Design how routes can be used with receiver lambdas and how a `Route` object could have `operator fun invoke` to simplify the syntax.

5. Document how you would test this DSL without a real network (e.g., unit tests that verify the generated structure and the rendered SQL).

**Conceptual Hints:**

- Keep responsibilities separate: `Config` vs. `Route` vs. `QueryBuilder`.

- Observers can be used for hot-reloading of logging or metrics.

**Checkpoints:**

- An example of a complete DSL server and its internal representation (structures).

- Test showing that changing `logLevel` triggers an observer that modifies logging behavior (mock).

**Deliverable:** Repo/package with the defined API, usage examples, and a set of tests that validate the structure (no network implementation).

**Assessment Criteria:**

- ✅ (4 pts) Consistent integration between modules (config, routes, queries).

- ✅ (3 pts) Basic test coverage (structures and renderers).

- ✅ (1 pt) Good documentation.

---

# Activity 6 — Exploration and Comparison: kotlinx.html, Exposed, and Gradle Kotlin DSL (Guided Reading)

**Objective:** Become familiar with real-world DSLs: read their design, identify patterns, and compare API decisions.

**Prerequisites:** Have completed previous activities or have experience with builders.

**Steps (what to do):**

1. Choose `kotlinx.html` and `Exposed`. For each:

- Find its README and main documentation (no need to run).

- Analyze the public API: How do they use lambdas with receivers? Where do they use `invoke`? What delegates/properties do they use?

2. Bonus: Open the Gradle Kotlin DSL examples (build.gradle.kts) and observe how the configurations are designed (e.g., dependencies { implementation("...") }). Identify similarities with your own DSLs.

3. Write down at least 5 design decisions that would help improve your previous projects (e.g., naming, scope markers, type safety).

**Conceptual clues:**

- Look for patterns: builders, receivers, scope control (@DslMarker in Kotlin), extensibility through extension functions.

**Checkpoints:**

- A 1–2-page comparison document with conclusions and suggested improvements for your DSL.

**Deliverable:** Comparison document and a list of 3 changes to be applied to your integration project based on what you've read.

---

# Optional evaluation activities and extensions

- **Refactor:** Add `@DslMarker` to avoid scope leaks in your builders.

- **Testing:** Write unit tests that validate the DSL's ergonomics (not just the output).

- **Performance:** If you implement renderers, measure generation times on large structures.

- **Production:** Try integrating your mini-DSL with Ktor to see interoperability (just as an exercise, not to be published).

---

# Teaching tips and how to work with activities

- Work iteratively: first define the **usable API** (examples of "how I want to write it"), then implement it internally.

- Write tests first (TDD) that describe the desired behavior of the DSL — this forces you to design the API from the user's perspective.

- Use `@DslMarker` to avoid errors from the recipient.

- Avoid overly generic solutions at the outset; prioritize ergonomics and type safety.

- Document each public API with usage examples; your documentation will be the best proof that the DSL is user-friendly.