package org.example.RA1

/*
Basic usage of input and output in Kotlin
 */

fun main() {
    print("Input your name: ")
    val name : String = readln()

    print("Input your age: ")
    val age : Int = readln().toInt()

    println("Your name is $name and your age is $age")

}