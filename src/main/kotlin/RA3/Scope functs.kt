package org.example.RA3

/*
Requirements
• Create examples for each scope function in which their use is justified:
◦ apply to configure objects (returns the receiver).
◦ also to perform side effects (logging) without changing the object.
◦ run to execute a block with this and obtain a result (transformation).
◦ with to group operations on an object and return a result.
• For each case, explain why you chose one or the other.
Steps
1. Implement a case of initialising an HttpRequest (or StringBuilder) with
apply.
2. Implement a pipeline where also records the intermediate state without breaking the chain.
3. Use run to convert an object into something else (e.g. parsing).
4. Use with to group several operations and return a summary.
 */

fun main() {
    val request = StringBuilder().apply {
        append("GET /users HTTP/1.1\n")
        append("Host: example.com\n")
        append("Accept: application/json\n")
    }

    println(request)


    val processedData = mutableListOf(1, 2, 3, 4)
        .also { println("Before filtering: $it") }
        .filter { it % 2 == 0 }
        .also { println("After filtering: $it") }

    println("Final list : $processedData")


    val numberString = "42"
    val number = numberString.run {
        toInt()
    }

    println("New type: ${number::class}")


    val user = User(id= 1, name = "Alice", active = true, score = 85)

    val summary = with(user) {
        println("Processing user $name")
        val status = if (active) "Active" else "Inactive"
        "User $name: $status, Score: $score"
    }

    println(summary)
}