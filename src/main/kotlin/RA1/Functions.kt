package org.example.RA1

/*
Create a function that takes two numbers and returns their sum.

Create another function that takes a name and greets the user (without returning anything).

Create a function with default parameters that displays a generic greeting if no name is passed.

Call the functions using positional and named arguments.

Try defining a function with an expression body (a single line with =).

Declare a function that uses the Unit return type.

Write an infix function that performs a simple operation between two numbers.

Create an extension function that extends the behaviour of a basic type (for example, an extra method for String or Int).
 */


fun sum(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun greet(name: String){
    println("Hello $name")
}

fun greet2(name: String ="John Smith") {
    println("Hello $name")
}

fun greetArrow(name: String) = println("Hello $name")


infix fun Int.substract(num1: Int): Int {
    return this - num1
}

fun String.myOwnMethod() {
    println(this)
}


fun main() {
//    greet(name = "Wazaa")
    greetArrow("Yoloo")

    val whatever = 2 substract 1
    println(whatever)

    "Prueba".myOwnMethod()
}


