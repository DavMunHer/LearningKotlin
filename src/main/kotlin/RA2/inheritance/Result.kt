package org.example.RA2.inheritance

/*
What to create:
1. sealed class Result<out T> with subclasses:
• Success<T>(value: T)
• Error(message: String, code: Int = 0)
• Loading (singleton object)
2. A set of functions that return Result<T> (simulates a call that may
be loading, failing, or returning data).
3. Use when to handle the three cases without else (ensure exhaustive compilation).

This example is basically something similar to the enum classes in Java, but much more powerful,
since each of the different options can be a class, therefore, storing its own data and so on...
 */


sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val message: String, val code: Int = 0): Result<Nothing>()
    object Loading: Result<Nothing>()
}

fun getSuccess(): Result<String> {
    return Result.Success("{name: 'John Smith', age: 20}")
}

fun getError(): Result<String> {
    return Result.Error("Could not receive data", code = 500)
}

fun handleResult(r: Result<String>) {
    when (r) {
        is Result.Success -> {
            val data = r.value
            println("Success! Data=$data")
        }
        is Result.Error -> {
            println("Error (code: ${r.code}): ${r.message}")
        }
        Result.Loading -> {
            println("Loading... Please wait")
        }
    }
}


fun main() {
    val r1 = getSuccess()
    val r2 = getError()

    handleResult(r1)
}



